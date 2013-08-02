package org.ets.ereg.csr.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


@Component("auditFilter")
public class AuditFilter extends GenericFilterBean  {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(AuditFilter.class);
    
    @Resource(name="etsAdminUserBusinessService")
    private ETSAdminUserBusinessService etsAdminUserBusinessService;
    
    public static final String adminUserRequestAttributeName = "adminUser";

    @Override
    public void doFilter(ServletRequest baseRequest, ServletResponse baseResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) baseRequest;
        HttpServletResponse response = (HttpServletResponse) baseResponse;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (authentication instanceof UsernamePasswordAuthenticationToken)) {
        	Object principal=authentication.getPrincipal();
        	if(principal!=null){
        		ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(((User)principal).getUsername());
        		request.setAttribute(adminUserRequestAttributeName, adminUser);
        	}
        	
        	
        	
        }
        
       
        chain.doFilter(request, response);
    }



   
}
