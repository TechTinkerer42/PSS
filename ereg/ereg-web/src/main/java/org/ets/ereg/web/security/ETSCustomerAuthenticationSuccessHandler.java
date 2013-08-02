package org.ets.ereg.web.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.web.order.security.BroadleafAuthenticationSuccessHandler;
import org.ets.ereg.security.user.ERegUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

public class ETSCustomerAuthenticationSuccessHandler extends BroadleafAuthenticationSuccessHandler {
	private static Logger LOG = LoggerFactory.getLogger(ETSCustomerAuthenticationSuccessHandler.class);
	
	@Resource(name="customerLoginSuccessHandler")
	CustomerLoginSuccessHandler customerLoginSuccessHandler;
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    try{
       
       	super.onAuthenticationSuccess(request, response, authentication);
       	
       	ERegUser eregUser = (ERegUser) authentication.getPrincipal();
       	
       	customerLoginSuccessHandler.onLogin(eregUser, request);
    	
    }catch(Exception e){
    	//Logging it ,So it doesnt get thrown to jsp
    	LOG.error("Exception:",e);
    }
      
	}	
}
