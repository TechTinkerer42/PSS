package org.ets.ereg.commerce.order.service;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.broadleafcommerce.core.payment.service.type.PaymentInfoAdditionalFieldType;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerPayment;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.service.PhoneService;
import org.ets.ereg.commerce.order.service.type.ETSCybersourceConstants;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.util.Constant;
import org.ets.ereg.domain.interfaces.model.common.AddressType;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.broadleafcommerce.vendor.cybersource.service.payment.CybersourceSilentPostPaymentServiceImpl;
import com.broadleafcommerce.vendor.cybersource.service.payment.request.CybersourceSignatureRequest;
import com.broadleafcommerce.vendor.cybersource.service.payment.type.CybersourceCheckingAccountType;
import com.broadleafcommerce.vendor.cybersource.service.payment.type.CybersourceCreditCardTypeEnum;
import com.broadleafcommerce.vendor.cybersource.service.payment.type.MessageConstants;

@Component
public class ETSCybersourceHOPPaymentServiceImpl extends CybersourceSilentPostPaymentServiceImpl implements InitializingBean {

    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

    private static final Logger LOG = LoggerFactory.getLogger(ETSCybersourceHOPPaymentServiceImpl.class);

    protected ReferenceService referenceService;

    protected PhoneService phoneService;

    protected String hopSignedFields;

    protected String hopProject;

    protected String hopInvoiceHeader;

    protected String hopSuccessUrl;

    protected String hopRejectUrl;

    protected String hopErrorUrl;

    protected String hopTimeoutUrl;
    
    @Value("${contextRoot}")
    protected String contextRoot;
    
	public String getContextRoot() {
		return contextRoot;
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        hopSignedFields = getApplicationConfigurationvalue(Constant.HOP_SIGNED_FIELDS);
        hopProject = getApplicationConfigurationvalue(Constant.HOP_PROJECT);
        hopInvoiceHeader = getApplicationConfigurationvalue(Constant.HOP_INVOICE_HEADER);
        hopSuccessUrl =  getContextRoot() + getApplicationConfigurationvalue(Constant.HOP_SUCESS_URL);
        hopRejectUrl = getContextRoot() + getApplicationConfigurationvalue(Constant.HOP_REJECT_URL);
        hopErrorUrl = getContextRoot() + getApplicationConfigurationvalue(Constant.HOP_ERROR_URL);
        hopTimeoutUrl = getContextRoot() + getApplicationConfigurationvalue(Constant.HOP_TIMEOUT_URL);

    }

    private String getApplicationConfigurationvalue(String key) {
        return applicationConfigurationService.findApplicationConfigurationValue(key);
    }

    @Override
    protected void attachBasicFields(CybersourceSignatureRequest signatureRequest, Map<String, String> fields) {
        super.attachBasicFields(signatureRequest, fields);
        
        //Cybersource requires upper case currency
        fields.put(MessageConstants.CURRENCY, signatureRequest.getAmount().getCurrency().getCurrencyCode().toUpperCase());
    	
        
        //ETS CPS does not allow "transaction type" to be signed and sent.
        fields.remove(MessageConstants.ORDERPAGE_TRANSACTIONTYPE);
        String orderPageSignature = fields.get(MessageConstants.MERCHANTID) +  fields.get(MessageConstants.AMOUNT) +
                fields.get(MessageConstants.CURRENCY) + fields.get(MessageConstants.ORDERPAGE_TIMESTAMP);
        fields.put(MessageConstants.ORDERPAGE_SIGNATUREPUBLIC, getPublicDigest(orderPageSignature));

        //Add ETS specific fields
        fields.put(ETSCybersourceConstants.MERCHANT_SUCCESS_URL, Boolean.TRUE.equals(getUseRelativeUrls()) ? getRequestedServerPrefix() + getHopSuccessUrl() : getHopSuccessUrl());
        fields.put(ETSCybersourceConstants.MERCHANT_ERROR_URL, Boolean.TRUE.equals(getUseRelativeUrls()) ? getRequestedServerPrefix() + getHopErrorUrl() : getHopErrorUrl());
        fields.put(ETSCybersourceConstants.MERCHANT_REJECT_URL, Boolean.TRUE.equals(getUseRelativeUrls()) ? getRequestedServerPrefix() + getHopRejectUrl() : getHopRejectUrl());
        fields.put(ETSCybersourceConstants.MERCHANT_TIMEOUT_URL, Boolean.TRUE.equals(getUseRelativeUrls()) ? getRequestedServerPrefix() + getHopTimeoutUrl() : getHopTimeoutUrl());

        fields.put(ETSCybersourceConstants.ORIGINATINGSYSTEM_CODE, getMerchantId());
        fields.put(ETSCybersourceConstants.ORDERPAGE_SIGNEDFIELDS, getHopSignedFields());
        fields.put(ETSCybersourceConstants.PROJECT, getHopProject());
        fields.put(ETSCybersourceConstants.MODEOFRECEIPT, ETSCybersourceConstants.MODEOFRECEIPT_DEFAULT);
        fields.put(ETSCybersourceConstants.INVOICEHEADER_MERCHANTDESCRIPTOR, getHopInvoiceHeader());
    }

    //ETS CPS verification algorithm is different than the BLC verification algorithm
    protected boolean verifyTransactionSignature(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        //ETS CPS uses the "transactionSignature" field;
        String transactionSignature = map.get(MessageConstants.TRANSACTIONSIGNATURE);
        if (transactionSignature == null) {
        	LOG.error("Signature verification failed due to missing signature.");
            return false;
        }
        String transactionSignatureFields = map.get(MessageConstants.SIGNEDFIELDS);
        if (transactionSignatureFields == null) {
        	LOG.error("Signature verification failed due to missing signature fields.");
            return false;
        }
        StringTokenizer tokenizer = new StringTokenizer(transactionSignatureFields, ",", false);
        StringBuilder data = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            String key = tokenizer.nextToken();
            data.append(map.get(key));
        }
        
        String digestString = getPublicDigest(data.toString());
        boolean publicDigest = digestString.equals(transactionSignature);
        if(!publicDigest) {
        	LOG.error("Signature verification failed due to getPublicDigest call");
        	LOG.info("digestString: " + digestString);
        	LOG.info("transactionSignature: " + transactionSignature);
        }
        return publicDigest;
    }


    @Override
    public void saveCustomerPayment(Map<String, String[]> paramMap, Customer customer) {
        if (paramMap != null && customer != null && customer.getId() != null && customerProfileNotEmpty(paramMap) && billingAddressNotEmpty(paramMap)){
            customer = customerService.readCustomerById(customer.getId());
            String paymentToken = getParameterValue(paramMap.get(MessageConstants.PAYSUBSCRIPTIONCREATEREPLY_SUBSCRIPTIONID));
            CustomerPayment payment = customerPaymentService.readCustomerPaymentByToken(paymentToken);
            if (payment == null) {
                payment = customerPaymentService.create();
                payment.setCustomer(customer);
                payment.setPaymentToken(paymentToken);
            } else if (!payment.getCustomer().getId().equals(customer.getId())) {
                // the customer does not match the token, do not change the token
                return;
            }

            Map<String, String> additionalFields = new HashMap<String, String>();
            additionalFields.put(PaymentInfoAdditionalFieldType.NAME_ON_CARD.getType(), getParameterValue(paramMap.get(MessageConstants.BILLTO_FIRSTNAME)) + " " + getParameterValue(paramMap.get(MessageConstants.BILLTO_LASTNAME)));
            additionalFields.put(PaymentInfoAdditionalFieldType.SUBSCRIPTION_TITLE.getType(), getParameterValue(paramMap.get(MessageConstants.SUBSCRIPTION_TITLE)));
            additionalFields.put(PaymentInfoAdditionalFieldType.PAYMENT_TYPE.getType(), getTransactionPaymentInfoType(paramMap).getType());

            if (PaymentInfoType.CREDIT_CARD.equals(getTransactionPaymentInfoType(paramMap))) {
                if (CybersourceCreditCardTypeEnum.getInstance(getParameterValue(paramMap.get(MessageConstants.CARD_CARDTYPE))) != null) {
                    additionalFields.put(PaymentInfoAdditionalFieldType.CARD_TYPE.getType(), CybersourceCreditCardTypeEnum.getInstance(getParameterValue(paramMap.get(MessageConstants.CARD_CARDTYPE))).getFriendlyType());
                }
                additionalFields.put(PaymentInfoAdditionalFieldType.EXP_MONTH.getType(), getParameterValue(paramMap.get(MessageConstants.CARD_EXPIRATIONMONTH)));
                additionalFields.put(PaymentInfoAdditionalFieldType.EXP_YEAR.getType(), getParameterValue(paramMap.get(MessageConstants.CARD_EXPIRATIONYEAR)));
                additionalFields.put(PaymentInfoAdditionalFieldType.LAST_FOUR.getType(), getParameterValue(paramMap.get(MessageConstants.CARD_ACCOUNTNUMBER)));
            } else if (PaymentInfoType.ELECTRONIC_CHECK.equals(getTransactionPaymentInfoType(paramMap))) {
                additionalFields.put(PaymentInfoAdditionalFieldType.LAST_FOUR.getType(), getParameterValue(paramMap.get(MessageConstants.CHECK_ACCOUNTNUMBER)));
                if (CybersourceCreditCardTypeEnum.getInstance(getParameterValue(paramMap.get(MessageConstants.CHECK_ACCOUNTTYPE))) != null) {
                    additionalFields.put(PaymentInfoAdditionalFieldType.ACCOUNT_TYPE.getType(), CybersourceCheckingAccountType.getInstance(getParameterValue(paramMap.get(MessageConstants.CHECK_ACCOUNTTYPE))).getFriendlyType());
                }
            }

            payment.setAdditionalFields(additionalFields);
            //update the billing address if the subscription exists
            payment.setBillingAddress(populateBillingAddress(paramMap));
            customerPaymentService.saveCustomerPayment(payment);
        }
    }

    @Override
    public Address populateBillingAddress(Map<String, String[]> paramMap) {
        ETSAddress billingAddress = null;
        if (paramMap != null) {
            // if a saved address id is returned, try and lookup that addresss
            if (getParameterValue(paramMap.get(MessageConstants.BILLTO_ADDRESSID)) != null) {
                try {
                    billingAddress = (ETSAddress) addressService.readAddressById(Long.parseLong(getParameterValue(paramMap.get(MessageConstants.BILLTO_ADDRESSID))));
                } catch (NumberFormatException e) {
                    LOG.warn("Returned bill to address id was not a long");
                }
            }
            if (billingAddress == null && billingAddressNotEmpty(paramMap)) {
                billingAddress = (ETSAddress) addressService.create();
                billingAddress.setFirstName(getParameterValue(paramMap.get(MessageConstants.BILLTO_FIRSTNAME)));
                billingAddress.setLastName(getParameterValue(paramMap.get(MessageConstants.BILLTO_LASTNAME)));
                billingAddress.setCompanyName(getParameterValue(paramMap.get(MessageConstants.BILLTO_COMPANY)));
                billingAddress.setAddressLine1(getParameterValue(paramMap.get(MessageConstants.BILLTO_STREET1)));
                billingAddress.setAddressLine2(getParameterValue(paramMap.get(MessageConstants.BILLTO_STREET2)));
                billingAddress.setCity(getParameterValue(paramMap.get(MessageConstants.BILLTO_CITY)));
                if (getParameterValue(paramMap.get(MessageConstants.BILLTO_STATE)) != null && stateService.findStateByAbbreviation(getParameterValue(paramMap.get(MessageConstants.BILLTO_STATE))) != null ) {
                    billingAddress.setState(stateService.findStateByAbbreviation(getParameterValue(paramMap.get(MessageConstants.BILLTO_STATE))));
                }
                //Cybersource sends back country codes in lowercase, since this may be inconsistent with BLC we'll try both lower and upper case.
                String country = getParameterValue(paramMap.get(MessageConstants.BILLTO_COUNTRY));
                Country c = countryService.findCountryByAbbreviation(country.toUpperCase());
                if (c == null) {
                    c = countryService.findCountryByAbbreviation(country.toLowerCase());
                }
                billingAddress.setCountry(c);
                billingAddress.setPostalCode(getParameterValue(paramMap.get(MessageConstants.BILLTO_POSTALCODE)));

                String primaryPhone = getParameterValue(paramMap.get(MessageConstants.BILLTO_PHONENUMBER));
                if (primaryPhone != null) {
                    Phone phonePrimary = (ETSPhone) phoneService.create();
                    phonePrimary.setPhoneNumber(primaryPhone);
                    billingAddress.setPhonePrimary(phonePrimary);
                }

                billingAddress.setEmailAddress(getParameterValue(paramMap.get(MessageConstants.BILLTO_EMAIL)));

                AddressType addressType = referenceService.getEntityByPrimaryKey(AddressType.class, AddressType.HomeAddress);
                billingAddress.setAddressType(addressType);

                billingAddress = (ETSAddress) addressService.saveAddress(billingAddress);
            }
        }
        return billingAddress;
    }

    @Override
    public Address populateShippingAddress(Map<String, String[]> paramMap) {
        ETSAddress shippingAddress = null;
        if (paramMap != null) {
            // if a saved address id is returned, try and lookup that addresss
            if (getParameterValue(paramMap.get(MessageConstants.SHIPTO_ADDRESSID)) != null) {
                try {
                    shippingAddress = (ETSAddress) addressService.readAddressById(Long.parseLong(getParameterValue(paramMap.get(MessageConstants.SHIPTO_ADDRESSID))));
                } catch (NumberFormatException e) {
                    LOG.warn("Returned ship to address id was not a long");
                }
            }
            if (shippingAddress == null && shippingAddressNotEmpty(paramMap)) {
                shippingAddress =  (ETSAddress)addressService.create();
                shippingAddress.setFirstName(getParameterValue(paramMap.get(MessageConstants.SHIPTO_FIRSTNAME)));
                shippingAddress.setLastName(getParameterValue(paramMap.get(MessageConstants.SHIPTO_LASTNAME)));
                shippingAddress.setCompanyName(getParameterValue(paramMap.get(MessageConstants.SHIPTO_COMPANY)));
                shippingAddress.setAddressLine1(getParameterValue(paramMap.get(MessageConstants.SHIPTO_STREET1)));
                shippingAddress.setAddressLine2(getParameterValue(paramMap.get(MessageConstants.SHIPTO_STREET2)));
                shippingAddress.setCity(getParameterValue(paramMap.get(MessageConstants.SHIPTO_CITY)));
                if (getParameterValue(paramMap.get(MessageConstants.SHIPTO_STATE)) != null && stateService.findStateByAbbreviation(getParameterValue(paramMap.get(MessageConstants.SHIPTO_STATE))) != null ) {
                    shippingAddress.setState(stateService.findStateByAbbreviation(getParameterValue(paramMap.get(MessageConstants.SHIPTO_STATE))));
                }
                //Cybersource sends back country codes in lowercase, since this may be inconsistent with BLC we'll try both lower and upper case.
                String country = getParameterValue(paramMap.get(MessageConstants.SHIPTO_COUNTRY));
                Country c = countryService.findCountryByAbbreviation(country.toUpperCase());
                if (c == null) {
                    c = countryService.findCountryByAbbreviation(country.toLowerCase());
                }
                shippingAddress.setCountry(c);
                shippingAddress.setPostalCode(getParameterValue(paramMap.get(MessageConstants.SHIPTO_POSTALCODE)));

                String primaryPhone = getParameterValue(paramMap.get(MessageConstants.SHIPTO_PHONENUMBER));
                if (primaryPhone != null) {
                    Phone phonePrimary = (ETSPhone) phoneService.create();
                    phonePrimary.setPhoneNumber(primaryPhone);
                    shippingAddress.setPhonePrimary(phonePrimary);
                }

                AddressType addressType = referenceService.getEntityByPrimaryKey(AddressType.class, AddressType.HomeAddress);
                shippingAddress.setAddressType(addressType);

                shippingAddress = (ETSAddress) addressService.saveAddress(shippingAddress);
            }
        }
        return shippingAddress;
    }

    public ReferenceService getReferenceService() {
        return referenceService;
    }

    public void setReferenceService(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    public PhoneService getPhoneService() {
        return phoneService;
    }

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    public String getHopSignedFields() {
        return hopSignedFields;
    }

    public void setHopSignedFields(String hopSignedFields) {
        this.hopSignedFields = hopSignedFields;
    }

    public String getHopProject() {
        return hopProject;
    }

    public void setHopProject(String hopProject) {
        this.hopProject = hopProject;
    }

    public String getHopInvoiceHeader() {
        return hopInvoiceHeader;
    }

    public void setHopInvoiceHeader(String hopInvoiceHeader) {
        this.hopInvoiceHeader = hopInvoiceHeader;
    }

    public String getHopSuccessUrl() {
        return hopSuccessUrl;
    }

    public void setHopSuccessUrl(String hopSuccessUrl) {
        this.hopSuccessUrl = hopSuccessUrl;
    }

    public String getHopRejectUrl() {
        return hopRejectUrl;
    }

    public void setHopRejectUrl(String hopRejectUrl) {
        this.hopRejectUrl = hopRejectUrl;
    }

    public String getHopErrorUrl() {
        return hopErrorUrl;
    }

    public void setHopErrorUrl(String hopErrorUrl) {
        this.hopErrorUrl = hopErrorUrl;
    }

    public String getHopTimeoutUrl() {
        return hopTimeoutUrl;
    }

    public void setHopTimeoutUrl(String hopTimeoutUrl) {
        this.hopTimeoutUrl = hopTimeoutUrl;
    }

}
