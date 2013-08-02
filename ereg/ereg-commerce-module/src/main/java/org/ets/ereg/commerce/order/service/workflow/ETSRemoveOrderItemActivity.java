package org.ets.ereg.commerce.order.service.workflow;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderItemService;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;

/**
 * This class has the same functionality as the Broadleaf RemoveOrderItemActivity
 * but modified to support Dependent Items from a Cart Rule
 */
public class ETSRemoveOrderItemActivity extends BaseActivity {

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;

    public ProcessContext execute(ProcessContext context) throws Exception {
        CartOperationRequest request = ((CartOperationContext) context).getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Find the OrderItem from the database based on its ID
        Order order = request.getOrder();

        OrderItem orderItem = orderItemService.readOrderItemById(orderItemRequestDTO.getOrderItemId());

        //You cannot delete an order item that is dependent on another order item
        // You must explicitly delete its parent
        if (orderItem instanceof ETSDiscreteOrderItem) {
            ETSDiscreteOrderItem doi = (ETSDiscreteOrderItem) orderItem;
            if(doi.getRefOrderItemId()!=null){
                request.setOrder(order);
                return context;
            }
        }

        // Remove the OrderItem from the Order
        OrderItem itemFromOrder = order.getOrderItems().remove(order.getOrderItems().indexOf(orderItem));

        // Delete the OrderItem from the database
        itemFromOrder.setOrder(null);
        orderItemService.delete(itemFromOrder);

        order = orderService.save(order, false);

        request.setOrder(order);
        return context;
    }

}

