package org.ets.ereg.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.web.profile.form.ProfileForm;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


@Component("auditFilter")
public class AuditFilter extends GenericFilterBean  {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static String customerRequestAttributeName = "customer";

    @Override
    public void doFilter(ServletRequest baseRequest, ServletResponse baseResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) baseRequest;
        HttpServletResponse response = (HttpServletResponse) baseResponse;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ETSCustomer customer= null;
        if ((authentication != null) && (authentication instanceof AnonymousAuthenticationToken)) {
        	HttpSession session=request.getSession();
        	ProfileForm profForm=(ProfileForm)session.getAttribute("profileForm");
        	if(profForm!=null){
        		customer=profForm.getProfile().getCustomer();
        		request.setAttribute(customerRequestAttributeName, customer);
        	}
        	 
        }
       
        chain.doFilter(request, response);
    }



   
}