package org.ets.ereg.csr.web.listener;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class HttpSessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent>{

	   private static Logger LOG = LoggerFactory.getLogger(HttpSessionDestroyedEventListener.class);
	   
	   @Resource(name="etsOrderService")
	   private ETSOrderService orderService;
	   
	   
	   @Resource(name="blCustomerService")
	   private CustomerService customerService;
	   
	
		@Resource(name = "extendHoldSeatStore")
		private ExtendHoldSeatStore extendHoldSeatStore;

		public void onApplicationEvent(HttpSessionDestroyedEvent event ) {

			extendHoldSeatStore.removeExtendHoldsForCurrentSession();
			
			String customerUserName = (String)event.getSession().getAttribute(Constant.CUSTOMER_USER_NAME);
			if(!StringUtils.isEmpty(customerUserName)){
				Order order = orderService.findNamedOrderForCustomer(
						(String)event.getSession().getAttribute(org.ets.ereg.common.web.util.Constant.CART_NAME), 
						customerService.readCustomerByUsername(	customerUserName)
					);
				if(order !=null){
					
					try {
						orderService.clearCart(order);
						LOG.debug("Named Cart Removed for {}", customerUserName);
					} catch (RemoveFromCartException e) {
						LOG.error("Error while removing cart on logout or session timeout", customerUserName);
					} catch (PricingException e) {
						LOG.error("Error while removing cart on logout or session timeout", customerUserName);
					}
					
				}
			}

	   }
	
}
