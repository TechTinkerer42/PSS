package org.ets.ereg.web.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.web.order.CartState;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.commerce.order.util.OrderUtil;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.util.Constant;
import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;
import org.ets.ereg.domain.interfaces.model.profile.LogoutReasonType;
import org.ets.ereg.profile.service.CustomerLogonHistoryService;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.ets.ereg.session.facade.profile.service.ETSCustomerBusinessService;
import org.ets.ereg.web.util.ETSWebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;



public class ETSCustomerLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	private static Logger LOG = LoggerFactory
			.getLogger(ETSCustomerLogoutSuccessHandler.class);
	
	@Resource(name="customerLogonHistoryService")
	protected CustomerLogonHistoryService custLogonHistService;
	
	@Resource(name = "etsOrderService")
	private ETSOrderService orderService;	
	
	@Resource(name = "referenceEntityService")
    private ReferenceService referenceService;
	
	@Resource(name = "extendHoldSeatStore")
	private ExtendHoldSeatStore extendHoldSeatStore;
	
	
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
		CustomerLogonHistory loggedInUser;
		try {
			if(null!=authentication){
				
				extendHoldSeatStore.removeExtendHoldsForCurrentSession();
				
				LOG.debug("In custom Logout user: {}",authentication.getName());
				LOG.debug("In custom Logout:{}",request.getRequestedSessionId());
				loggedInUser= custLogonHistService.getCustomerLogonHistory(authentication.getName(), request.getRequestedSessionId());
				if(loggedInUser!=null){
					
					// Clear Cart
					
					LOG.info("Clearing Cart on log out");
					orderService.clearCart(orderService.findCartForCustomer(loggedInUser.getCustomer()));	
					
					
					// Customer Logon History Update
					LOG.info("Saving Logon History log out");
					updateCustomerLogonHistory(loggedInUser,custLogonHistService);
					
				}
				 setDefaultTargetUrl(ETSWebConstants.SIGIN); 
			}
			super.onLogoutSuccess(request, response, authentication);
		} catch (Exception e) {
			LOG.error("Exception in clearing the cart:", e);
		}
		
	}

	private void updateCustomerLogonHistory(CustomerLogonHistory loggedInUser,
			CustomerLogonHistoryService custLogonHistService2) {			
		loggedInUser.setLogoutReason(referenceService.getEntityByPrimaryKey(LogoutReasonType.class, LogoutReasonType.RSN_LOGOUT));
		loggedInUser.setLogoutTimestamp(new java.util.Date());
		custLogonHistService.save(loggedInUser);
		
	}

}
