package org.ets.ereg.commerce.order.service.workflow;

import javax.annotation.Resource;

import org.broadleafcommerce.core.checkout.service.workflow.CheckoutContext;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.commerce.order.vo.ShoppingCartTransformer;
import org.ets.ereg.commerce.order.vo.ShoppingCartVO;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ETSOrderConfirmationEmailActivity extends BaseActivity{
	
	private static Logger logger = LoggerFactory.getLogger(ETSOrderConfirmationEmailActivity.class);	
		

	@Resource(name = "etsOrderService")
	private ETSOrderService orderService;		
	
	@Resource(name = "shoppingCartTransformer")
	private ShoppingCartTransformer shoppingCartTransformer;
	
	
	
	@Override
	public ProcessContext execute(ProcessContext context) throws Exception{		
		try{
		logger.debug("inside send order confirmation email activity");
				
		CheckoutSeed seed = ((CheckoutContext) context).getSeedData();
		
	    Order order = seed.getOrder();
	    ShoppingCartVO cartVO =shoppingCartTransformer.transform(order );
	    
		orderService.sendOrderConfirmationMail(cartVO);	  
		
		logger.debug("sent order confirmation email successfully for Order Number ",order.getOrderNumber());
		}catch(Exception e){
			logger.error("Error Occurred in sending out email {}",e);
		}
		return context;
	}
	

}
