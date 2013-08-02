package org.ets.ereg.commerce.order.service;

import com.broadleafcommerce.vendor.cybersource.service.payment.CybersourceSilentPostPaymentService;
import com.broadleafcommerce.vendor.cybersource.service.payment.type.CybersourceCheckingAccountType;
import com.broadleafcommerce.vendor.cybersource.service.payment.type.CybersourceCreditCardTypeEnum;
import com.broadleafcommerce.vendor.cybersource.service.payment.type.MessageConstants;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.time.SystemTime;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItemImpl;
import org.broadleafcommerce.core.payment.service.PaymentContext;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.module.PaymentModule;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoAdditionalFieldType;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.StateService;
import org.ets.ereg.commerce.order.service.type.ETSCybersourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class ETSCybersourceHOPPaymentModule implements PaymentModule {
	private static Logger log = LoggerFactory.getLogger(ETSCybersourceHOPPaymentModule.class);
    private CybersourceSilentPostPaymentService paymentService;

    private StateService stateService;

    private CountryService countryService;

    @Override
    public PaymentResponseItem authorize(PaymentContext paymentContext) throws PaymentException {
        return parseCommonResponse(paymentContext);
    }

    @Override
    public PaymentResponseItem reverseAuthorize(PaymentContext paymentContext) throws PaymentException {
        throw new PaymentException("The reverseAuthorize method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem debit(PaymentContext paymentContext) throws PaymentException {
        throw new PaymentException("The debit method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem authorizeAndDebit(PaymentContext paymentContext) throws PaymentException {
        return parseCommonResponse(paymentContext);
    }

    @Override
    public PaymentResponseItem credit(PaymentContext paymentContext) throws PaymentException {
        throw new PaymentException("The credit method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem voidPayment(PaymentContext paymentContext) throws PaymentException {
        throw new PaymentException("The voidPayment method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem balance(PaymentContext paymentContext) throws PaymentException {
        throw new PaymentException("The balance method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem partialPayment(PaymentContext paymentContext) throws PaymentException {
        throw new PaymentException("The partialPayment method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");    }

    @Override
    public Boolean isValidCandidate(PaymentInfoType paymentType) {
        return PaymentInfoType.CREDIT_CARD.equals(paymentType) || PaymentInfoType.ELECTRONIC_CHECK.equals(paymentType);
    }

    @Override
    public PaymentResponseItem processReverseAuthorize(PaymentContext paymentContext, Money amountToReverseAuthorize, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processReverseAuthorize method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem processAuthorize(PaymentContext paymentContext, Money amountToAuthorize, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processAuthorize method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem processDebit(PaymentContext paymentContext, Money amountToDebit, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processDebit method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem processAuthorizeAndDebit(PaymentContext paymentContext, Money amountToDebit, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processAuthorizeAndDebit method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem processCredit(PaymentContext paymentContext, Money amountToCredit, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processCredit method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem processVoidPayment(PaymentContext paymentContext, Money amountToVoid, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processVoidPayment method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem processBalance(PaymentContext paymentContext, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processBalance method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    @Override
    public PaymentResponseItem processPartialPayment(PaymentContext paymentContext, Money amountToDebit, PaymentResponseItem responseItem) throws PaymentException {
        throw new PaymentException("The processPartialPayment method is not supported by this org.ets.ereg.commerce.order.service.ETSCybersourceHOPPaymentModule");
    }

    protected PaymentResponseItem parseCommonResponse(PaymentContext paymentContext) {
        PaymentInfo paymentInfo = paymentContext.getPaymentInfo();

        Assert.isTrue(paymentInfo.getRequestParameterMap() != null && !paymentInfo.getRequestParameterMap().isEmpty(), "Must set the Request Parameter Map on the PaymentInfo instance.");

        PaymentResponseItem responseItem = buildBasicResponse(paymentContext);

        setBillingInfo(paymentContext);
        setShippingInfo(paymentContext);
        setPaymentInfoAdditionalFields(paymentContext);
        setCustomerPaymentInfo(paymentContext);

        return responseItem;
    }

    protected String getVal(String[] values) {
        return paymentService.getParameterValue(values);
    }

    protected PaymentResponseItem buildBasicResponse(PaymentContext paymentContext) {
        PaymentInfo paymentInfo = paymentContext.getPaymentInfo();
        PaymentResponseItem responseItem = new PaymentResponseItemImpl();
        Map<String, String[]> paramMap = paymentInfo.getRequestParameterMap();

        responseItem.setProcessorResponseCode(getVal(paramMap.get(MessageConstants.REASONCODE)));
        responseItem.setProcessorResponseText(getVal(paramMap.get(ETSCybersourceConstants.REASONMESSAGE)));
        responseItem.setTransactionSuccess(paymentService.isValidTransaction(paymentInfo.getRequestParameterMap()));
        responseItem.setTransactionTimestamp(SystemTime.asDate());
        responseItem.setTransactionAmount(new Money(getVal(paramMap.get(MessageConstants.AMOUNT))));
        responseItem.setTransactionId(getVal(paramMap.get(MessageConstants.REQUESTID)));
        //responseItem.setAvsCode(getVal(paramMap.get(MessageConstants.CCAUTHREPLY_AVSCODE)));
        //responseItem.setCvvCode(getVal(paramMap.get(MessageConstants.CCAUTHREPLY_CVCODE)));
        
        log.info("Transaction Success: " + responseItem.getTransactionSuccess());
        log.info("Transaction Amount: " + responseItem.getTransactionAmount());

        setPaymentResponseAdditionalFields(paymentContext, responseItem);

        return responseItem;
    }

    protected void setPaymentInfoAdditionalFields(PaymentContext paymentContext) {
        Map<String, String[]> paramMap = paymentContext.getPaymentInfo().getRequestParameterMap();

        PaymentInfo paymentInfo = getActualPaymentInfo(paymentContext);

        if (paymentInfo != null) {
            Map<String, String> additionalFields = new HashMap<String, String>();
            additionalFields.put(PaymentInfoAdditionalFieldType.REQUEST_ID.getType(), getVal(paramMap.get(MessageConstants.REQUESTID)));
            if (getVal(paramMap.get(MessageConstants.PAYSUBSCRIPTIONCREATEREPLY_SUBSCRIPTIONID)) != null) {
                additionalFields.put(PaymentInfoAdditionalFieldType.SUBSCRIPTION_ID.getType(), getVal(paramMap.get(MessageConstants.PAYSUBSCRIPTIONCREATEREPLY_SUBSCRIPTIONID)));
            }
            additionalFields.put(PaymentInfoAdditionalFieldType.NAME_ON_CARD.getType(), getVal(paramMap.get(MessageConstants.BILLTO_FIRSTNAME)) + " " + getVal(paramMap.get(MessageConstants.BILLTO_LASTNAME)));


            if (PaymentInfoType.CREDIT_CARD.equals(paymentService.getTransactionPaymentInfoType(paramMap))) {
                if (getVal(paramMap.get(MessageConstants.CCAUTHREPLY_AUTHORIZATIONCODE)) != null) {
                    additionalFields.put(PaymentInfoAdditionalFieldType.AUTH_CODE.getType(), getVal(paramMap.get(MessageConstants.CCAUTHREPLY_AUTHORIZATIONCODE)));
                }
                if (CybersourceCreditCardTypeEnum.getInstance(getVal(paramMap.get(MessageConstants.CARD_CARDTYPE))) != null) {
                    additionalFields.put(PaymentInfoAdditionalFieldType.CARD_TYPE.getType(), CybersourceCreditCardTypeEnum.getInstance(getVal(paramMap.get(MessageConstants.CARD_CARDTYPE))).getFriendlyType());
                }
                additionalFields.put(PaymentInfoAdditionalFieldType.EXP_MONTH.getType(), getVal(paramMap.get(MessageConstants.CARD_EXPIRATIONMONTH)));
                additionalFields.put(PaymentInfoAdditionalFieldType.EXP_YEAR.getType(), getVal(paramMap.get(MessageConstants.CARD_EXPIRATIONYEAR)));
                additionalFields.put(PaymentInfoAdditionalFieldType.LAST_FOUR.getType(), getVal(paramMap.get(ETSCybersourceConstants.CC_LASTFOUR)));
            } else if (PaymentInfoType.ELECTRONIC_CHECK.equals(paymentService.getTransactionPaymentInfoType(paramMap))) {
                additionalFields.put(PaymentInfoAdditionalFieldType.LAST_FOUR.getType(), getVal(paramMap.get(MessageConstants.CHECK_ACCOUNTNUMBER)));
                if (CybersourceCreditCardTypeEnum.getInstance(getVal(paramMap.get(MessageConstants.CHECK_ACCOUNTTYPE))) != null) {
                    additionalFields.put(PaymentInfoAdditionalFieldType.ACCOUNT_TYPE.getType(), CybersourceCheckingAccountType.getInstance(getVal(paramMap.get(MessageConstants.CHECK_ACCOUNTTYPE))).getFriendlyType());
                }
            }
            paymentInfo.setAdditionalFields(additionalFields);
        }
    }

    protected void setPaymentResponseAdditionalFields(PaymentContext paymentContext, PaymentResponseItem responseItem) {
        Map<String, String[]> paramMap = paymentContext.getPaymentInfo().getRequestParameterMap();

        Map<String, String> map = new HashMap<String, String>();
        map.put(MessageConstants.SIGNEDDATAPUBLICSIGNATURE, getVal(paramMap.get(MessageConstants.SIGNEDDATAPUBLICSIGNATURE)));
        map.put(MessageConstants.DECISION, getVal(paramMap.get(MessageConstants.DECISION)));
        map.put(MessageConstants.DECISION_PUBLICSIGNATURE, getVal(paramMap.get(MessageConstants.DECISION_PUBLICSIGNATURE)));
        map.put(MessageConstants.ORDERAMOUNT_PUBLICSIGNATURE, getVal(paramMap.get(MessageConstants.ORDERAMOUNT_PUBLICSIGNATURE)));
        map.put(MessageConstants.ORDERPAGE_TRANSACTIONTYPE, getVal(paramMap.get(MessageConstants.ORDERPAGE_TRANSACTIONTYPE)));
        map.put(MessageConstants.ORDERPAGE_REQUESTTOKEN, getVal(paramMap.get(MessageConstants.ORDERPAGE_REQUESTTOKEN)));
        map.put(MessageConstants.ORDERPAGE_SERIALNUMBER, getVal(paramMap.get(MessageConstants.ORDERPAGE_SERIALNUMBER)));
        map.put(MessageConstants.ORDERCURRENCY, getVal(paramMap.get(MessageConstants.ORDERCURRENCY)));
        map.put(MessageConstants.ORDERCURRENCY_PUBLICSIGNATURE, getVal(paramMap.get(MessageConstants.ORDERCURRENCY_PUBLICSIGNATURE)));
        map.put(MessageConstants.ORDERNUMBER, getVal(paramMap.get(MessageConstants.ORDERNUMBER)));
        map.put(MessageConstants.ORDERNUMBER_PUBLICSIGNATURE, getVal(paramMap.get(MessageConstants.ORDERCURRENCY_PUBLICSIGNATURE)));
        map.put(MessageConstants.RECONCILIATIONID, getVal(paramMap.get(MessageConstants.RECONCILIATIONID)));
        map.put(MessageConstants.PAYMENTOPTION, getVal(paramMap.get(MessageConstants.PAYMENTOPTION)));
        if (PaymentInfoType.CREDIT_CARD.equals(paymentService.getTransactionPaymentInfoType(paramMap))) {
            map.put(MessageConstants.CCAUTHREPLY_REASONCODE, getVal(paramMap.get(MessageConstants.CCAUTHREPLY_REASONCODE)));
            map.put(MessageConstants.CCAUTHREPLY_PROCESSORRESPONSE, getVal(paramMap.get(MessageConstants.CCAUTHREPLY_PROCESSORRESPONSE)));
            map.put(MessageConstants.CCAUTHREPLY_AMOUNT, getVal(paramMap.get(MessageConstants.CCAUTHREPLY_AMOUNT)));
            map.put(MessageConstants.CCAUTHREPLY_AUTHORIZATIONCODE, getVal(paramMap.get(MessageConstants.CCAUTHREPLY_AUTHORIZATIONCODE)));
            map.put(MessageConstants.CCAUTHREPLY_AUTHORIZEDDATETIME, getVal(paramMap.get(MessageConstants.CCAUTHREPLY_AUTHORIZEDDATETIME)));
        }

        responseItem.setAdditionalFields(map);
    }

    protected void setBillingInfo(PaymentContext paymentContext) {
        PaymentInfo paymentInfo = getActualPaymentInfo(paymentContext);

        if (paymentInfo != null ) {
            Map<String, String[]> paramMap = paymentContext.getPaymentInfo().getRequestParameterMap();
            Address billingAddress = paymentService.populateBillingAddress(paramMap);
            paymentInfo.setAddress(billingAddress);
        }
    }


    protected void setShippingInfo(PaymentContext paymentContext) {
        PaymentInfo paymentInfo = paymentContext.getPaymentInfo();
        if (paymentInfo != null && paymentInfo.getOrder().getFulfillmentGroups().size() == 1) {
            Map<String, String[]> paramMap = paymentContext.getPaymentInfo().getRequestParameterMap();
            // If you pass the shipping address to Cybersource HOP/SOP, there has to be an existing fulfillment group on the order.
            // This must be done because of pricing considerations.
            // The fulfillment group must be constructed when adding to the cart or sometime before calling the gateway. This depends on UX.
            // This default implementation assumes one fulfillment group per order because Cybersource only supports a single shipping address.
            // Override this method if necessary.
            FulfillmentGroup fulfillmentGroup = paymentInfo.getOrder().getFulfillmentGroups().get(0);
            if (fulfillmentGroup != null) {
                Address shippingAddress = paymentService.populateShippingAddress(paramMap);
                if (shippingAddress != null) {
                    fulfillmentGroup.setAddress(shippingAddress);
                }
            }

        }
    }

    protected void setCustomerPaymentInfo(PaymentContext paymentContext) {
        PaymentInfo paymentInfo = paymentContext.getPaymentInfo();
        Map<String, String[]> paramMap = paymentInfo.getRequestParameterMap();
        Customer customer = paymentInfo.getOrder().getCustomer();
        paymentService.saveCustomerPayment(paramMap, customer);
    }

    protected PaymentInfo getActualPaymentInfo(PaymentContext paymentContext) {
        //Note that you cannot perform operations on paymentContext.getPaymentInfo() directly because that is a copy of the actual payment on the order.
        //In order to persist custom attributes to the credit card payment info on the order we must look it up first.
        PaymentInfo paymentInfo = null;
        for (PaymentInfo pi : paymentContext.getPaymentInfo().getOrder().getPaymentInfos()) {
            if (paymentContext.getPaymentInfo().getType().equals(pi.getType())) {
                paymentInfo = pi;
            }
        }
        return paymentInfo;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public CountryService getCountryService() {
        return countryService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public CybersourceSilentPostPaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(CybersourceSilentPostPaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
