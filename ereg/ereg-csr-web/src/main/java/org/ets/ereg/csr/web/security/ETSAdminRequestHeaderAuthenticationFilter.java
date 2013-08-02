package org.ets.ereg.csr.web.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.security.user.ERegUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

public class ETSAdminRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter{
	private static Logger logger = LoggerFactory.getLogger(ETSAdminRequestHeaderAuthenticationFilter.class);
	@Resource(name="adminLoginSuccessHandler")
	AdminLoginSuccessHandler adminLoginSuccessHandler;
	
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
	{
		ERegUser eregUser = (ERegUser) authResult.getPrincipal();
		logger.info("In the successfulAuthentication with user {}",eregUser);
		adminLoginSuccessHandler.onLogin(eregUser);
		super.successfulAuthentication(request, response, authResult);		
	}
}
