package org.ets.ereg.csr.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.broadleafcommerce.core.web.order.security.CartStateRequestProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component("estAdminCartStateFilter")
/**
 * <p>
 * This filter should be configured after the BroadleafCommerce CustomerStateFilter listener from Spring Security.
 * Retrieves the cart for the current BroadleafCommerce Customer based using the authenticated user OR creates an empty non-modifiable cart and
 * stores it in the request.
 * </p>
 *
 * @author bpolster
 */
public class ETSAdminCartStateFilter extends GenericFilterBean implements  Ordered {

    /** Logger for this class and subclasses */
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Resource(name = "etsAdminCartStateRequestProcessor")
    protected CartStateRequestProcessor cartStateProcessor;

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {        
        cartStateProcessor.process(new ServletWebRequest((HttpServletRequest) request, (HttpServletResponse)response));
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        //FilterChainOrder has been dropped from Spring Security 3
        //return FilterChainOrder.REMEMBER_ME_FILTER+1;
        return 1502;
    }

}