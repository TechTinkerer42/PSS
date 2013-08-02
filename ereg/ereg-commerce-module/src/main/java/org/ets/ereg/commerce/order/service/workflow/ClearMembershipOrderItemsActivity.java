package org.ets.ereg.commerce.order.service.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.order.service.OrderItemService;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.dto.MembershipOrderItemRequestDTO;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem;

public class ClearMembershipOrderItemsActivity extends BaseActivity {

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "blFulfillmentGroupService")
    protected FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;

    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
        CartOperationRequest request = ((CartOperationContext) context).getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
        if (!(orderItemRequestDTO instanceof MembershipOrderItemRequestDTO)) {
            Order order = request.getOrder();

            List<OrderItem> orderItemsToBeRemoved = new ArrayList<OrderItem>();
            for (OrderItem oi : order.getOrderItems()) {
                if (oi instanceof MembershipDiscreteOrderItem) {
                    ETSDiscreteOrderItem eOrderItem = (ETSDiscreteOrderItem) oi;
                    if(eOrderItem.getRefOrderItemId()!=null){
                        orderItemsToBeRemoved.add(eOrderItem);
                    }
                }
            }

            for (OrderItem orderItem : orderItemsToBeRemoved) {
                //Remove the OrderItem from FulfillmentGroups
                fulfillmentGroupService.removeOrderItemFromFullfillmentGroups(order, orderItem);

                // Remove the OrderItem from the Order
                OrderItem itemFromOrder = order.getOrderItems().remove(order.getOrderItems().indexOf(orderItem));

                // Delete the OrderItem from the database
                itemFromOrder.setOrder(null);
                orderItemService.delete(itemFromOrder);
            }

            order = orderService.save(order, false);

            request.setOrder(order);
        }
        return context;
    }
}
