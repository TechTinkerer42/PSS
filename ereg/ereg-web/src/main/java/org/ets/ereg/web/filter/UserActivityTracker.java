/*
 * UserActivityTracker.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.common.helpers.ThreadContext;
import org.ets.ereg.common.helpers.ThreadLocalFacade;
import org.ets.ereg.common.helpers.UserContext;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.web.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UserActivityTracker
 *
 * @version	1.0 May 15, 2013
 * @author 	Venkat Shanmugam
 * 
 * @history
 * May 15, 2013 VS Initial Creation
 *
 **/
public class UserActivityTracker implements Filter {

	

	private static Logger log = LoggerFactory.getLogger(UserActivityTracker.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		//Make sure a default context is initialized.
		LoginUtil.switchCustomerContext((HttpServletRequest)request, null, null);
		
		ThreadContext.setUserContext((UserContext)((HttpServletRequest)request).getSession().getAttribute(Constant.Context.USER_CONTEXT));
		
		if( log.isDebugEnabled() ) { // High frequency access.
			log.debug("Program Code/Uri : " + ThreadLocalFacade.getProgramCode() + " / " + ((HttpServletRequest)request).getRequestURI());
		}
		
		chain.doFilter(request, response);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}



}
