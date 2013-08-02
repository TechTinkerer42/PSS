package org.ets.ereg.csr.web.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.EregUtils;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;

import org.springframework.ui.Model;

public class AdminLoginUtil {
	
	@Resource(name="eregUtils")
	public EregUtils eregUtil;

	/**
    *
    * Method set the model for sign-in
    *
    * @param request
    * @param model
    * @return
    */
   public static Model setAuthAttributes(HttpServletRequest request,Model model,ApplicationConfigurationService applicationService){
	   
	   String authMechanism = applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM);
	   if(!ApplicationConfiguration.AUTH_MECHANISM_OAM.equals(applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM)))
	   {
	       String submitURL= request.getContextPath() +applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_URL);
	       String username= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_USENAME);
	       String password= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_PASSWORD);
	       model.addAttribute("username", username);
	       model.addAttribute("password", password);
	       model.addAttribute("actionValue", submitURL);
	   }
	   else
	   {
			String submitURL= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_URL);
			String username= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_USENAME);
			String password= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_PASSWORD);
			String lockpage= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_LOCKPAGE);
			String lockpageValue= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_LOCKPAGE_VALUE);
			String expiredpwdpage= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_EXPIREDPWDPAGE);			
			String expiredpwdpageValue= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_EXPIREDPWDPAGE_VALUE_CSR);
			String forgotUserName= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FORGET_USERNAME_CSR);			
			String forgotPassword= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FORGET_PASSWORD_CSR);
			
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			model.addAttribute("lockpage", lockpage);
			model.addAttribute("lockpageValue", lockpageValue);
			model.addAttribute("expiredpwdpage", expiredpwdpage);			
			model.addAttribute("expiredpwdpageValue", expiredpwdpageValue);
			model.addAttribute("forgotUserName", forgotUserName);			
			model.addAttribute("forgotPassword", forgotPassword);
			model.addAttribute("actionValue", submitURL);
	   }
	   model.addAttribute("authMechanism",authMechanism);
       return model;
   }

}
