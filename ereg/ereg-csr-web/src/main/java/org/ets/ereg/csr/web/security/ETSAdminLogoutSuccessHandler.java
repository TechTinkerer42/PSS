package org.ets.ereg.csr.web.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.profile.service.CustomerLogonHistoryService;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.ets.ereg.security.user.ERegUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;



public class ETSAdminLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	private static Logger LOG = LoggerFactory.getLogger(ETSAdminLogoutSuccessHandler.class);
	public static final String SIGIN="/signin";
	private static final String HOME_URL="/secure/home";
	
	@Resource(name="customerLogonHistoryService")
	protected CustomerLogonHistoryService custLogonHistService;
	
    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

	@Resource(name = "extendHoldSeatStore")
	private ExtendHoldSeatStore extendHoldSeatStore;
	
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
		
		Object principal = authentication.getPrincipal();
		ERegUser loggedInERegUser = (ERegUser) principal;
		
		extendHoldSeatStore.removeExtendHoldsForCurrentSession();
		
		String authMechanism = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM);
		if(null!=authentication){
			if(ApplicationConfiguration.AUTH_MECHANISM_OAM.equals(authMechanism)){
				if(loggedInERegUser.isInternalUser()){
					String internalRedirectLink=applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.CSR_WEB_INTERNAL_LOGOUT_URL);
					response.sendRedirect(internalRedirectLink);
				}else{
					String externalRedirectLink=applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.CSR_WEB_EXTERNAL_LOGOUT_URL);
					response.sendRedirect(externalRedirectLink);
				}
				
			}else if(ApplicationConfiguration.AUTH_MECHANISM_DATABASE.equals(authMechanism)){
				setDefaultTargetUrl(HOME_URL); 
				
			}
		}
		 super.onLogoutSuccess(request, response, authentication);
		
	}

}
