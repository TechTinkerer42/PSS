
package org.ets.ereg.external.service.eias.client.service;

import java.net.URL;
import javax.annotation.Resource;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.service.ETSApplicationUserService;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.external.service.eias.client.exception.EIASException;
import org.ets.ereg.external.service.eias.client.types.AppSysIDDetails;
import org.ets.ereg.external.service.eias.client.types.ResponseObject;
import org.ets.ereg.external.service.eias.client.types.User;
import org.ets.ereg.external.service.eias.client.types.UserVO;
import org.ets.ereg.external.service.eias.client.utils.EIASConstants;
import org.ets.ereg.external.service.eias.client.utils.ObjectConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("EIASWebServiceClient")
public final class EIASWebServiceClientImpl implements EIASWebServiceClient{

	@Resource(name = "ETSApplicationUserService")
	private ETSApplicationUserService appUserService;
	
	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;
	
	private static Logger log = LoggerFactory.getLogger(EIASWebServiceClientImpl.class);
			
    //private static final QName SERVICE_NAME = new QName("http://webservice.eias.ets.org", "EIASWebServiceService");
    
   // To create the new candidate in the eias
    @Override
	public String createUser(ETSCustomer customer){

		EIASWebService port = getClientHandler();
		ObjectConverter oc = new ObjectConverter();
		User userObject = oc.createXMLCandidateObject(customer,getLdapCreateCandDir(),getLdapUserType());
		AppSysIDDetails sysUserCredential = getSystemUserObj();
		ResponseObject registerUserResponse = port.registerUser(userObject, sysUserCredential);
		// Checking the response code, if not 0 then return GUID
		if((registerUserResponse.getRespCode()).equals("0") && (registerUserResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS)){
			return registerUserResponse.getUserADObjGUID();
		}
		else
		{
			log.error("Error in creating Candidate at ldap --->: {}", registerUserResponse.getRespMessage());
			throw new EIASException(registerUserResponse.getRespCode(), registerUserResponse.getRespMessage(), registerUserResponse.getRespResult(), registerUserResponse.getUserADObjGUID(),
					registerUserResponse.getReturnAttribute(), registerUserResponse.getUserIdSuggestions().getItem());
			
		}
	}
    
    // To create the new TCA in eias
    @Override
	public String createUser(ETSAdminUser tca){

		EIASWebService port = getClientHandler();
		ObjectConverter oc = new ObjectConverter();
		User userObject = oc.createXMLTCAObject(tca,getLdapCreateTCADir(),getLdapUserType());
		AppSysIDDetails sysUserCredential = getSystemUserObj();
		ResponseObject registerUserResponse = port.registerUser(userObject, sysUserCredential);
		// Checking the response code, if not 0 then return GUID
		if((registerUserResponse.getRespCode()).equals("0") && (registerUserResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS)){
			return registerUserResponse.getUserADObjGUID();
		}
		else
		{
			log.error("Error in creating CSR/TCA at ldap --->: {}", registerUserResponse.getRespMessage());
			throw new EIASException(registerUserResponse.getRespCode(), registerUserResponse.getRespMessage(), registerUserResponse.getRespResult(), registerUserResponse.getUserADObjGUID(),
					registerUserResponse.getReturnAttribute(), registerUserResponse.getUserIdSuggestions().getItem());
			
		}
	}
    
    // To search if a user exists in the given ldap directory
    @Override
	public boolean searchUser(String userID){

		EIASWebService port = getClientHandler();
		AppSysIDDetails sysUserCredential = getSystemUserObj();
		ResponseObject searchUserResponse = port.searchUser(sysUserCredential, EIASConstants.EIASUSERNAMEATTRIBUTE, userID, null, getLdapSearchDir());
		// 42 is the response Code if the user doesn't exist in the directory		
		if((searchUserResponse.getRespCode()).equals("42") && !(searchUserResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS))
		{
			return true;
		}
		// Checking the response code, if not 0 then return GUID
		else if((searchUserResponse.getRespCode()).equals("0") && (searchUserResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS)){
			return false;
		}
		else
		{
			log.error("Error in Searching user at ldap --->: {}", searchUserResponse.getRespMessage());
			throw new EIASException(searchUserResponse.getRespCode(), searchUserResponse.getRespMessage(), searchUserResponse.getRespResult(), searchUserResponse.getUserADObjGUID(),
					searchUserResponse.getReturnAttribute(), null);
		}

	}

    @Override
	public String modifyUser(ETSCustomer customer){
		EIASWebService port = getClientHandler();
		ObjectConverter oc = new ObjectConverter();
		User userObject = oc.createXMLCandidateObject(customer,getLdapCreateCandDir(),getLdapUserType());
		AppSysIDDetails sysUserCredential = getSystemUserObj();
		ResponseObject modifyUserResponse = port.modifyUser(userObject,sysUserCredential);

		// Checking the response code, if not 0 then return GUID
		if((modifyUserResponse.getRespCode()).equals("0") && (modifyUserResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS)){
			return modifyUserResponse.getUserADObjGUID();
		}
		else{
			log.error("Error in Modifying Candidate at ldap --->: {}", modifyUserResponse.getRespMessage());
			throw new EIASException(modifyUserResponse.getRespCode(), modifyUserResponse.getRespMessage(), modifyUserResponse.getRespResult(), modifyUserResponse.getUserADObjGUID(),
					modifyUserResponse.getReturnAttribute(), modifyUserResponse.getUserIdSuggestions().getItem());
		}
	}

    @Override
	public String modifyUser(ETSAdminUser tca){
		EIASWebService port = getClientHandler();
		ObjectConverter oc = new ObjectConverter();
		User userObject = oc.createXMLTCAObject(tca,getLdapCreateTCADir(),getLdapUserType());
		AppSysIDDetails sysUserCredential = getSystemUserObj();
		ResponseObject modifyUserResponse = port.modifyUser(userObject,sysUserCredential);

		// Checking the response code, if not 0 then return GUID
		if((modifyUserResponse.getRespCode()).equals("0") && (modifyUserResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS)){
			return modifyUserResponse.getUserADObjGUID();
		}
		else{
			log.error("Error in Modifying CSR/TCA at ldap --->: {}", modifyUserResponse.getRespMessage());
			throw new EIASException(modifyUserResponse.getRespCode(), modifyUserResponse.getRespMessage(), modifyUserResponse.getRespResult(), modifyUserResponse.getUserADObjGUID(),
					modifyUserResponse.getReturnAttribute(), modifyUserResponse.getUserIdSuggestions().getItem());
		}
	}
    
    @Override
	public String resetPassword(String userID, String guid,
			String password, String emailNotifyFlag){

		EIASWebService port = getClientHandler();
		AppSysIDDetails sysUserCredential = getSystemUserObj();
		ResponseObject resetPasswordResponse = port.resetPassword(sysUserCredential,userID,guid,password, emailNotifyFlag);

		// Checking the response code, if not 0 then return GUID
		if(!(resetPasswordResponse.getRespCode()).equals("0") && !(resetPasswordResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS)){
			
			log.error("Error in Reset Password at ldap for Candidate --->: {}", resetPasswordResponse.getRespMessage());
			throw new EIASException(resetPasswordResponse.getRespCode(), resetPasswordResponse.getRespMessage(), resetPasswordResponse.getRespResult(), resetPasswordResponse.getUserADObjGUID(),
					resetPasswordResponse.getReturnAttribute(), null);
		}
		return resetPasswordResponse.getUserADObjGUID();
	}

	private EIASWebService getClientHandler()
	{
		URL wsdlURL = null;
		try{
			wsdlURL = new URL(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.WSDL_LOCATION));
		}
		catch(Exception e)
		{
			log.error("Error accessing the WSDL Location URL: {}", e);
		}

		//EIASWebServiceService ss = new EIASWebServiceService(wsdlURL, SERVICE_NAME);
		EIASWebServiceService ss = EIASWebServiceService.getInstance(wsdlURL);
		return ss.getEIASWebService();  
	}
	
	private AppSysIDDetails getSystemUserObj()
	{
		AppSysIDDetails systemUser = new AppSysIDDetails();
		ETSApplicationUser systemUserCredential;
		
		systemUserCredential = appUserService.getUserByPrimaryKey(EIASConstants.EIASSYSTEMUSERID);
		systemUser.setAppSysID(systemUserCredential.getUserName());
		systemUser.setAppSysIDPassword(systemUserCredential.getPassword());

		return systemUser;
	}
	
	private String getLdapCreateCandDir()
	{
		return applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.LDAP_CREATE_CANDIDATE_ROLE);
		
	}
	
	private String getLdapCreateTCADir()
	{
		return applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.LDAP_CREATE_TCA_ROLE);
		
	}
	
	private String getLdapSearchDir()
	{
		return applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.LDAP_SEARCH_USER_DIR);
		
	}
	
	private String getLdapUserType()
	{
		return applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.LDAP_USER_TYPE);
		
	}

	@Override
	public UserVO createUserFromCSR(ETSCustomer customer) {
		EIASWebService port = getClientHandler();
		ObjectConverter oc = new ObjectConverter();
		User userObject = oc.createXMLCandidateObjectCSR(customer,getLdapCreateCandDir(),getLdapUserType());
		AppSysIDDetails sysUserCredential = getSystemUserObj();
		ResponseObject createUserResponse = port.createUser(userObject, sysUserCredential);
		// Checking the response code, if not 0 then return GUID
		if((createUserResponse.getRespCode()).equals("0") && (createUserResponse.getRespResult()).equalsIgnoreCase(EIASConstants.SUCCESS)){
			UserVO userVO = new UserVO();
			userVO.setObjectGuid(createUserResponse.getUserADObjGUID());
			userVO.setUserID(createUserResponse.getReturnAttribute());
			userVO.seteMailID(createUserResponse.getUserObj().getEmailAddress());
			return userVO;
		}
		else
		{
			log.error("Error in creating ldap user through CSR --->: {}",createUserResponse.getRespMessage());
			throw new EIASException(createUserResponse.getRespCode(), createUserResponse.getRespMessage(), createUserResponse.getRespResult(), createUserResponse.getUserADObjGUID(),
					createUserResponse.getReturnAttribute(), createUserResponse.getUserIdSuggestions().getItem());
		}
	}
}
