package org.ets.ereg.commerce.order.util;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;

public class OrderUtil {
	
	public static  int getTotalQuantity(Order order){
        int orderItemsCount = 0;
        if (null!=order && null!=order.getFulfillmentGroups()  && !order.getFulfillmentGroups().isEmpty() ) {
        	
            for (OrderItem orderItem : order.getOrderItems()) {
            	if(orderItem instanceof ETSDiscreteOrderItem){
            		if(((ETSDiscreteOrderItem)orderItem).canCountTowardsItemCount()){
            			orderItemsCount += orderItem.getQuantity();
            		}
            	}else{
                    orderItemsCount += orderItem.getQuantity();
            	}
            }
        }
      
    	return orderItemsCount;
    }
	
	/*public static List<OrderItem> getTestsFromCart(Order cart) {
		List<OrderItem> testItemsInCart = new ArrayList<OrderItem>();
		if(null!=cart && !cart.getOrderItems().isEmpty()){ 
			 for (OrderItem oi : cart.getOrderItems()) {
	             if (oi instanceof ETSDiscreteOrderItem) {
	                 ETSDiscreteOrderItem eOrderItem = (ETSDiscreteOrderItem) oi;
	                 if(null==eOrderItem.getRefOrderItemId()){
	                     testItemsInCart.add(eOrderItem);
	                 }
	             }
	         }
		}
		 return testItemsInCart;
	}*/
	
/*	public static void clearCart(Customer customer,
			ETSOrderService etsOrderService, Order cart) throws RemoveFromCartException, PricingException {
			if(null!=cart){
				List<OrderItem> orderItemsToBeRemoved=getTestsFromCart(cart);
				for (OrderItem orderItem : orderItemsToBeRemoved) {  
						cart = etsOrderService.removeItem(cart.getId(), orderItem.getId(), false);	
						cart=etsOrderService.save(cart, true);
						
				}
				etsOrderService.cancelOrder(cart);
				
			}
	}*/

}
