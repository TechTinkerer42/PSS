package org.ets.ereg.csr.core.web.order.security;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.broadleafcommerce.common.web.BroadleafWebRequestProcessor;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.call.UpdateCartResponse;
import org.broadleafcommerce.core.web.order.security.CartStateFilter;
import org.broadleafcommerce.core.web.order.security.CartStateRequestProcessor;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.security.CustomerStateRequestProcessor;
import org.ets.ereg.common.web.util.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * Genericized version of the CartStateFilter. This was made to facilitate reuse between Servlet Filters, Portlet Filters and Spring MVC interceptors. Spring has an easy way of converting HttpRequests and PortletRequests into WebRequests via <br />
 * new ServletWebRequest(httpServletRequest); new PortletWebRequest(portletRequest); <br />
 * For the interceptor pattern, you can simply implement a WebRequestInterceptor to invoke from there.
 * 
 * @author Phillip Verheyden
 * @see {@link CartStateFilter}
 * @see {@link BroadleafWebRequestProcessor}
 * @see {@link ServletWebRequest}
 * @see {@link org.springframework.web.portlet.context.PortletWebRequest}
 */
@Component("etsAdminCartStateRequestProcessor")
public class ETSAdminCartStateRequestProcessor extends CartStateRequestProcessor {

    /** Logger for this class and subclasses */
    protected final Logger LOG = LoggerFactory.getLogger(ETSAdminCartStateRequestProcessor.class);



    @Override
    public void process(WebRequest request) {
        Customer customer = (Customer) request.getAttribute(CustomerStateRequestProcessor.getCustomerRequestAttributeName(), WebRequest.SCOPE_REQUEST);

        if (customer != null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Looking up cart for customer " + customer.getId());
            }
            
            Order cart = request.getAttribute(Constant.CART_NAME, WebRequest.SCOPE_SESSION) != null ? orderService.findNamedOrderForCustomer((String)request.getAttribute(Constant.CART_NAME, WebRequest.SCOPE_SESSION), customer) : null;

            if (cart == null) {
                cart = orderService.getNullOrder();
            } else {
                try {
                    updateCartService.validateCart(cart);
                } catch (IllegalArgumentException e) {
                    if (copyCartWhenSpecifiedStateChanges) {
                        UpdateCartResponse updateCartResponse = updateCartService.copyCartToCurrentContext(cart);
                        request.setAttribute("updateCartResponse", updateCartResponse, WebRequest.SCOPE_REQUEST);
                    } else {
                        orderService.cancelOrder(cart);
                        if(request.getUserPrincipal() != null){
                        	cart = orderService.createNamedOrderForCustomer(request.getUserPrincipal().getName(),customer);
                        }
                    }
                }
            }

            request.setAttribute(getCartRequestAttributeName(), cart, WebRequest.SCOPE_REQUEST);

            // Setup cart for content rule processing
            Map<String, Object> ruleMap = (Map<String, Object>) request.getAttribute(BLC_RULE_MAP_PARAM, WebRequest.SCOPE_REQUEST);
            if (ruleMap == null) {
                ruleMap = new HashMap<String, Object>();
            }
            ruleMap.put("cart", cart);
            request.setAttribute(BLC_RULE_MAP_PARAM, ruleMap, WebRequest.SCOPE_REQUEST);
        }

    }

}
