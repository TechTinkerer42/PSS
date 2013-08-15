package org.ets.ereg.profile.domain.service.common;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerRole;
import org.broadleafcommerce.profile.core.domain.CustomerRoleImpl;
import org.broadleafcommerce.profile.core.domain.Role;
import org.broadleafcommerce.profile.core.service.CustomerServiceImpl;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.CustomerProgramInterest;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.model.dao.ETSCustomerDao;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
@Service("etsCustomerService")
public class ETSCustomerServiceImpl extends CustomerServiceImpl implements
		ETSCustomerService, InitializingBean {
	
    @Resource(name="etsCustomerDao")
    private ETSCustomerDao customerDao;
    
	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;
	
	@Resource(name="blMailSender")
	private JavaMailSenderImpl javaMailSenderImpl;
	
	@Autowired
	private MessageSource messageSource;
	
	private boolean isEmailEnabled;

	@Override
	public List<CustomerVO> searchCustomerByCriteria(ETSCustomer customer,ETSAddress address, ETSPhone phone,SearchParameters searchParams,String linkDispSeq,Long adminId,Long apptNumber) {
		return customerDao.searchCustomerByCriteria(customer,address,phone, searchParams,linkDispSeq,adminId,apptNumber);
	}
	@Override
	public Long getCountForCustomersSearch(ETSCustomer customer,ETSAddress address, ETSPhone phone, String linkDispSeq,Long adminId,Long apptNumber){
		return customerDao.getCountForCustomersSearch(customer,address,phone,linkDispSeq,adminId,apptNumber);
	}
	@Override
	public DupCheckResponseObject checkDuplicateProfile(ProfileVO profile) {
		return customerDao.checkDuplicateProfile(profile);
	}
	@Override
	public CustomerProgramInterest addCustomerProgramInterest(ETSCustomer customer,
			ProgramType programType) {
		return customerDao.addCustomerProgramInterest(customer, programType);
	}
	
	public List<String> getCustomerProgramInterests( Long customerId ){
		
		return customerDao.getCustomerProgramInterests(customerId);
	
	}
	
	@Override
	public CustomerLinkage addCustomerLinkage(ETSCustomer customer,
			LinkageType linkageType, String linkageKey) {
		return customerDao.addCustomerLinkage(customer, linkageType, linkageKey);
	}
	@Override
	public Boolean hasDuplicateProfiles(ProfileVO profile,boolean currentloggedCust)
	{
		return customerDao.hasDuplicateProfiles(profile, currentloggedCust);
	}
	
	@Override
	public List<CustomerVO> getDuplicateProfiles(ProfileVO profile,boolean currentloggedCust)
	{
		return customerDao.getDuplicateProfiles(profile, currentloggedCust);
	}
	@Override
	public CustomerLinkage getCustomerLinkage(Long custid, String linkageType) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerLinkage(custid, linkageType);
	}
	@Override
	public boolean checkForCustomerLinkageKey(String linkageKey) {
		// TODO Auto-generated method stub
		return customerDao.checkForCustomerLinkageKey(linkageKey);
	}
	@Override
	public String getGuidID(ETSCustomer customer, LinkageType linkageType) {
		return customerDao.getGuidID(customer, linkageType);
	}
	
	@Override
	public Customer registerCustomer(Customer customer, String password,
			String passwordConfirm) {
        customer.setRegistered(true);

        // When unencodedPassword is set the save() will encode it
        if (customer.getId() == null) {
            customer.setId(findNextCustomerId());
        }
        customer.setUnencodedPassword(password);
        Customer retCustomer = saveCustomer(customer);
        createRegisteredCustomerRoles(retCustomer);
        
        HashMap<String, Object> vars = new HashMap<String, Object>();
        vars.put("customer", retCustomer);
        if(isEmailEnabled){
        	emailService.sendTemplateEmail(customer.getEmailAddress(), getRegistrationEmailInfo(), vars);
        }
        notifyPostRegisterListeners(retCustomer);
        return retCustomer;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		isEmailEnabled = Boolean.valueOf(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.MAIL_ENABLED));
		javaMailSenderImpl.setHost(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_HOST));
		javaMailSenderImpl.setPort(Integer.parseInt(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_PORT)));
		javaMailSenderImpl.setProtocol(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_PROTOCOL));
		javaMailSenderImpl.setUsername(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_USERNAME));
		javaMailSenderImpl.setPassword(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_PASSWORD));
		String mailProperties = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.MAIL_PROPERTIES);
		if(null != mailProperties && mailProperties.length() > 0){
			for(String property: mailProperties.split(",")){
				int index = property.indexOf("=");
				if(index > 0){
					String key = property.substring(0, index);
					String value = property.substring(index+1);
					javaMailSenderImpl.getJavaMailProperties().setProperty(key, value);
				}
			}
		}
		String fromAddress = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.MAIL_FROM);
		registrationEmailInfo.setFromAddress(fromAddress);
	}
	
	@Override
	public ETSCustomer findCustomerByUsernameAndInternalFlag(String username, Boolean internalUserFlag) {
		return customerDao.findCustomerByUsernameAndInternalFlag(username, internalUserFlag);
	}
	@Override
    protected void createRegisteredCustomerRoles(Customer customer) {
        Role role = roleDao.readRoleByName("ROLE_CUSTOMER");
        CustomerRole customerRole = new CustomerRoleImpl();
        customerRole.setRole(role);
        customerRole.setCustomer(customer);
        roleDao.addRoleToCustomer(customerRole);
    }
	@Override
	public ETSCustomer getCustomerByEmail(String email){
        return customerDao.getCustomerByEmail(email);
    }

	
}
