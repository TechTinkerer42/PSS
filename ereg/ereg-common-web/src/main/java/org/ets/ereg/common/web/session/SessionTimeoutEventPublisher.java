package org.ets.ereg.common.web.session;

import javax.servlet.http.HttpSessionEvent;

import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;
import org.ets.ereg.domain.interfaces.model.profile.LogoutReasonType;
import org.ets.ereg.profile.service.CustomerLogonHistoryService;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.session.HttpSessionEventPublisher;
/*import org.ets.ereg.commerce.order.util.OrderUtil;*/

public class SessionTimeoutEventPublisher extends HttpSessionEventPublisher		
{
	
	/*@Resource(name = "etsOrderService")
	private ETSOrderService etsOrderService;*/
	
	
    private ReferenceService referenceService;
	
	
	private static Logger LOG = LoggerFactory
			.getLogger(SessionTimeoutEventPublisher.class);	
	

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOG.debug("session created");
		super.sessionCreated(event);

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		 ApplicationContext applicationContext;
		CustomerLogonHistoryService custLogonHistService;
		
		
		//ETSOrderService etsOrderService;
		LOG.debug("session Destroyed");

		
		applicationContext = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getSession().getServletContext());
		CustomerLogonHistory loggedInUser;
		try{
			
			if(null!=applicationContext){
					LOG.debug("applicationContext is not null");
				
					custLogonHistService=(CustomerLogonHistoryService)applicationContext.getBean("customerLogonHistoryService");
					
					referenceService=(ReferenceService)applicationContext.getBean("referenceEntityService");
					
					//etsOrderService=(ETSOrderService)applicationContext.getBean("etsOrderService");	
					
					
					if (null != event.getSession().getId()) {
						
						ExtendHoldSeatStore extendHoldSeatStore = (ExtendHoldSeatStore) applicationContext.getBean("extendHoldSeatStore");
						if(extendHoldSeatStore != null){
							extendHoldSeatStore.removeExtendHoldsForSession(event.getSession().getId());
						}

						LOG.debug("Getting the loggedIn user history");
						loggedInUser = custLogonHistService.getCustomerLogonHistory(event
								.getSession().getId());				
						
						if(loggedInUser!=null){
							//  ToDo Clear Cart
							//LOG.info("Clearing Cart on session time out");
							/* Following are teh approaches tried to clear cart
							
							etsOrderService.clearCart(CartState.getCart()); -- This throws null pointer exception
							 etsOrderService.clearCart(etsOrderService.findCartForCustomer(loggedInUser.getCustomer())); -- this throws lazy initialization error for getOrderItems, no session or session closed. However order Id is correctly 
							// We can try out creating a method which gives all the orderItems given the orderId. We can get the Order Id using  findCartForCustomer which populates 
							
							*/
							
							// Customer Logon History Update
							LOG.info("Updating customerLogon on session time out");
							updateCustomerLogonHistory(loggedInUser,custLogonHistService);
												
						}
						
					}else
					LOG.debug("sessionid was null" );
			
			}else
				LOG.debug("applicationContext is NULL");
		
		
		LOG.debug("sessionid : {}", event.getSession().getId());
		
		super.sessionDestroyed(event);
		}catch(Exception e){
			LOG.error("exception occurred",e);
		}
	

		
	}

	

	private void updateCustomerLogonHistory(CustomerLogonHistory loggedInUser,
		CustomerLogonHistoryService custLogonHistService) {
		loggedInUser.setLogoutReason(referenceService.getEntityByPrimaryKey(LogoutReasonType.class, LogoutReasonType.RSN_SESSION_TIMEOUT));		
		loggedInUser.setLogoutTimestamp(new java.util.Date());
		custLogonHistService.save(loggedInUser);
		LOG.debug("logging out: {}", loggedInUser.getBrowserName());	
		
	}
}


	 
	

