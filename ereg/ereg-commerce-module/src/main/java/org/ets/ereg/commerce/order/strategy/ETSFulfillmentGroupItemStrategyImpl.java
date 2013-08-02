package org.ets.ereg.commerce.order.strategy;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.order.strategy.FulfillmentGroupItemStrategyImpl;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;

/**
 * 
 * This Strategy extends Broadleaf's default FulfillmentGroupItemStrategy
 * but overrides certain methods to accommodate Cart Rules and Dependent Products;
 *
 */
public class ETSFulfillmentGroupItemStrategyImpl extends FulfillmentGroupItemStrategyImpl {

	@Override
	public CartOperationRequest onItemRemoved(CartOperationRequest request) {
        Order order = request.getOrder();
        OrderItem orderItem = orderItemService.readOrderItemById(request.getItemRequest().getOrderItemId());
        
        if (orderItem instanceof BundleOrderItem) {
            List<OrderItem> itemsToRemove = new ArrayList<OrderItem>(((BundleOrderItem) orderItem).getDiscreteOrderItems());
            for (OrderItem oi : itemsToRemove) {
                fulfillmentGroupService.removeOrderItemFromFullfillmentGroups(order, oi);
            }
        } else if (orderItem instanceof ETSDiscreteOrderItem) {
        	ETSDiscreteOrderItem eoi = (ETSDiscreteOrderItem) orderItem;
        	//remove all dependent item fulfillment group items
    		for (ETSDiscreteOrderItem dependentItem : eoi.getRefOrderItemIds()) {
    			fulfillmentGroupService.removeOrderItemFromFullfillmentGroups(order, dependentItem);
    		}
    		//remove the actual item
            fulfillmentGroupService.removeOrderItemFromFullfillmentGroups(order, orderItem);
        } else {
            fulfillmentGroupService.removeOrderItemFromFullfillmentGroups(order, orderItem);
        }
        
        return request;
	}

	
}
