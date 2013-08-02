package org.ets.ereg.web.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.helpers.CustomerContext;
import org.ets.ereg.common.helpers.ThreadContext;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

public class LoginUtil {
	

	private static Logger log = LoggerFactory.getLogger(LoginUtil.class);
	
	//customize username password and post url
	public static Model getAuthAttributes(HttpServletRequest request,Model model,ApplicationConfigurationService applicationService) {
		
		String authMechanism = applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM);
		if(ApplicationConfiguration.AUTH_MECHANISM_OAM.equals(applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM))){
			//TODO Below ApplicationConfiguration constants would change and column might be added to the database
			
			/*
			 * If we use applicationConfigurationBusinessService in place of applicationService, then we need to add the same service in 
			 * LoginController and pass to LoginUtil in place of currently passed ApplicationConfigurationService 
			 * 
			 * @Resource(name = "applicationConfigurationBusinessService")
				private ApplicationConfigurationBusinessService applicationConfigurationBusinessService;
			 * 
			 */
			
			String submitURL= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_URL);
			String username= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_USENAME);
			String password= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_PASSWORD);
			String lockpage= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_LOCKPAGE);
			String lockpageValue= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_LOCKPAGE_VALUE);
			String expiredpwdpage= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_EXPIREDPWDPAGE);			
			String expiredpwdpageValue= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_EXPIREDPWDPAGE_VALUE);
			String forgotUserName= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FORGET_USERNAME_EREG);			
			String forgotPassword= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FORGET_PASSWORD_EREG);
			
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
		
		//TODO What to do if there is any mechanism in the config table which is not DATABASE or OAM

		if(ApplicationConfiguration.AUTH_MECHANISM_DATABASE.equals(applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM))){
			String submitURL= request.getContextPath() +applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_URL);
			String username= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_USENAME);
			String password= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_PASSWORD);
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			model.addAttribute("actionValue", submitURL);
		}
		model.addAttribute("authMechanism",authMechanism);
		/*
		String submitURL= request.getContextPath() +applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_URL);
		String username= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_USENAME);
		String password= applicationService.findApplicationConfigurationValue(ApplicationConfiguration.FIELDNAME_PASSWORD);
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		model.addAttribute("actionValue", submitURL);
		*/ 
		return model;
	}

	
	public static void switchCustomerContext(HttpServletRequest request, String tenantCode, List<String> customerPrograms) {
		
		if( null == tenantCode ) {
			CustomerContext cctx = (CustomerContext) request.getSession().getAttribute(Constant.Context.CUSTOMER_CONTEXT);
			if( null != cctx && null != cctx.getCurrentProgramCode() ) {
				ThreadContext.setCustomerContext(cctx);
				return;
			}else {
				tenantCode = ApplicationMappings.DEFAULT_SITE_CDE;
				log.debug("Set default tenant to MPA");
			}
		}
		
		String programCode = ApplicationMappings.UriProgramMappings.uriToPrograms.get(tenantCode);
		
		log.info("Site code requested : " + tenantCode + " Program Code : " + programCode );
		
		
		CustomerContext ctx = new CustomerContext();
		
		
		if( null != customerPrograms) {
			Map<String, String> availableSiteDescriptions = new HashMap<String, String>();
			ctx.setSiteDescriptions(availableSiteDescriptions);
			//@TODO replace entrySet() call with call to db.
			for( String et : customerPrograms ) {
				ctx.getAvailablePrograms().put(et, null );
				availableSiteDescriptions.put(ApplicationMappings.ProgramToUriMappings.programsToUri.get(et), 
						ApplicationMappings.SiteDescriptionMappings.siteDescriptions.get(ApplicationMappings.ProgramToUriMappings.programsToUri.get(et)));
				log.debug(et + ": " + ApplicationMappings.SiteDescriptionMappings.siteDescriptions.get(et));
			}
		}
		
		
		
		ctx.setCurrentProgramCode(programCode);
		request.getSession().setAttribute(Constant.Context.CUSTOMER_CONTEXT, ctx);
		
		ctx.setCurrentProgramShortDescription(ApplicationMappings.ProgramDescriptionMappings.programShortDescriptions.get(ctx.getCurrentProgramCode()));
		
		ThreadContext.setCustomerContext((CustomerContext)((HttpServletRequest)request).getSession().getAttribute(Constant.Context.CUSTOMER_CONTEXT));
		
	}
	
	public static boolean isCustomerContextInitialized(HttpServletRequest request) {
		boolean ret = true;
		
		CustomerContext cctx = (CustomerContext) request.getSession().getAttribute(Constant.Context.CUSTOMER_CONTEXT);
		if( null == cctx || cctx.getAvailablePrograms().isEmpty()) {
			ret = false;
		}
		return ret;
		
	}
}
