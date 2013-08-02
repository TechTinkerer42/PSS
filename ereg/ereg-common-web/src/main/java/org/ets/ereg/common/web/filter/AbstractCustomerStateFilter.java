package org.ets.ereg.common.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.core.security.CustomerAuthenticatedFromCookieEvent;
import org.broadleafcommerce.profile.web.core.security.CustomerLoggedInEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


public abstract class AbstractCustomerStateFilter extends GenericFilterBean implements ApplicationEventPublisherAware, Ordered {
	private static Logger log = LoggerFactory.getLogger(AbstractCustomerStateFilter.class);


    public static final String BLC_RULE_MAP_PARAM = "blRuleMap";

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

    private ApplicationEventPublisher eventPublisher;

    private static String customerRequestAttributeName = "customer";
    public static final String ANONYMOUS_CUSTOMER_SESSION_ATTRIBUTE_NAME="_blc_anonymousCustomer";
    private static final String LAST_PUBLISHED_EVENT_SESSION_ATTRIBUTED_NAME="_blc_lastPublishedEvent";

    @Override
    public void doFilter(ServletRequest baseRequest, ServletResponse baseResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) baseRequest;
        HttpServletResponse response = (HttpServletResponse) baseResponse;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = null;
        if ((authentication != null) && !(authentication instanceof AnonymousAuthenticationToken)) {
            String userName = resolveUserName(request);
            
            //For CSR Web until customer is selected, we don't need to execute this filter
            //if(StringUtils.isEmpty(userName))
            	//return;
            
            customer = (Customer) request.getAttribute(customerRequestAttributeName);
            if (userName != null && (customer == null || !userName.equals(customer.getUsername()))) {
                // can only get here if the authenticated user does not match the user in session
                customer = customerService.readCustomerByUsername(userName);
                if (log.isDebugEnabled() && customer != null) {
                    log.debug("Customer found by username {} ", userName);
                }
            }
            if (customer != null) {
                ApplicationEvent lastPublishedEvent = (ApplicationEvent) request.getSession(true).getAttribute(LAST_PUBLISHED_EVENT_SESSION_ATTRIBUTED_NAME);
                if (authentication instanceof RememberMeAuthenticationToken) {
                    // set transient property of customer
                    customer.setCookied(true);
                    boolean publishRememberMeEvent = true;
                    if (lastPublishedEvent != null && lastPublishedEvent instanceof CustomerAuthenticatedFromCookieEvent) {
                        CustomerAuthenticatedFromCookieEvent cookieEvent = (CustomerAuthenticatedFromCookieEvent) lastPublishedEvent;
                        if (userName.equals(cookieEvent.getCustomer().getUsername())) {
                            publishRememberMeEvent = false;
                        }
                    }
                    if (publishRememberMeEvent) {
                        CustomerAuthenticatedFromCookieEvent cookieEvent = new CustomerAuthenticatedFromCookieEvent(customer, this.getClass().getName()); 
                        eventPublisher.publishEvent(cookieEvent);
                        request.getSession().setAttribute(LAST_PUBLISHED_EVENT_SESSION_ATTRIBUTED_NAME, cookieEvent);
                    }                       
                } else if (authentication instanceof UsernamePasswordAuthenticationToken || authentication instanceof PreAuthenticatedAuthenticationToken) {
                    customer.setLoggedIn(true);
                    boolean publishLoggedInEvent = true;
                    if (lastPublishedEvent != null && lastPublishedEvent instanceof CustomerLoggedInEvent) {
                        CustomerLoggedInEvent loggedInEvent = (CustomerLoggedInEvent) lastPublishedEvent;
                        if (userName.equals(loggedInEvent.getCustomer().getUsername())) {
                            publishLoggedInEvent= false;
                        }
                    }
                    if (publishLoggedInEvent) {
                        CustomerLoggedInEvent loggedInEvent = new CustomerLoggedInEvent(customer, this.getClass().getName()); 
                        eventPublisher.publishEvent(loggedInEvent);
                        request.getSession().setAttribute(LAST_PUBLISHED_EVENT_SESSION_ATTRIBUTED_NAME, loggedInEvent);
                    }                        
                }  else {
                    customer = null;
                }
                    
            }
        }

        if (customer == null) {
            // This is an anonymous customer.
            // TODO: Handle a custom cookie (different than remember me) that is just for anonymous users.  
            // This can be used to remember their cart from a previous visit.
            // Cookie logic probably needs to be configurable - with TCS as the exception.

            customer = resolveAnonymousCustomer(request);
        }
        request.setAttribute(customerRequestAttributeName, customer);

        // Setup customer for content rule processing
        Map<String,Object> ruleMap = (Map<String, Object>) request.getAttribute(BLC_RULE_MAP_PARAM);
        if (ruleMap == null) {
            ruleMap = new HashMap<String,Object>();
        }
        ruleMap.put("customer", customer);
        request.setAttribute(BLC_RULE_MAP_PARAM, ruleMap);

        chain.doFilter(request, response);
    }

    protected abstract String resolveUserName(HttpServletRequest request);

	/**
     * Implementors can subclass to change how anonymous customers are created.
     * @param request
     * @return
     */
    public Customer resolveAnonymousCustomer(HttpServletRequest request) {
        Customer customer;
        customer = (Customer) request.getSession(true).getAttribute(getAnonymousCustomerAttributeName());
        if (customer == null) { 
            customer = customerService.createNewCustomer();
            customer.setAnonymous(true);
            request.getSession().setAttribute(getAnonymousCustomerAttributeName(), customer);
        }
        return customer;
    }

    /**
     * Returns the session attribute to store the anonymous customer.
     * Some implementations may wish to have a different anonymous customer instance (and as a result a different cart). 
     * @return
     */
    public String getAnonymousCustomerAttributeName() {
        return ANONYMOUS_CUSTOMER_SESSION_ATTRIBUTE_NAME;
    }

    @Override
    public int getOrder() {
        //FilterChainOrder has been dropped from Spring Security 3
        //return FilterChainOrder.REMEMBER_ME_FILTER+1;
        return 1501;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public static String getCustomerRequestAttributeName() {
        return customerRequestAttributeName;
    }

    public static void setCustomerRequestAttributeName(
            String customerRequestAttributeName) {
    	AbstractCustomerStateFilter.customerRequestAttributeName = customerRequestAttributeName;
    }
	


}
