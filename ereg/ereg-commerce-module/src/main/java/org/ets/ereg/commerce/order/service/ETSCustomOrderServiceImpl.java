package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderServiceImpl;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.WorkflowException;
import org.ets.ereg.commerce.order.dto.BatteryOrderItemRequestDTO;
import org.ets.ereg.commerce.order.dto.MembershipOrderItemRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a extension of BLC OrderServiceImpl to override any specific behavior
 * @author ZABDUL
 *
 */
@Service("etsCustomOrderService")
class ETSCustomOrderServiceImpl extends OrderServiceImpl {

    @Override
    @Transactional(value = "blTransactionManager", rollbackFor = { AddToCartException.class })
    public Order addItemWithPriceOverrides(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException {
        if(orderItemRequestDTO instanceof BatteryOrderItemRequestDTO || orderItemRequestDTO instanceof MembershipOrderItemRequestDTO){
            try {
                CartOperationRequest cartOpRequest = new CartOperationRequest(findOrderById(orderId), orderItemRequestDTO, priceOrder);
                CartOperationContext context = (CartOperationContext) addItemWorkflow.doActivities(cartOpRequest);
                return context.getSeedData().getOrder();
            } catch (WorkflowException e) {
                throw new AddToCartException("Could not add to cart", getCartOperationExceptionRootCause(e));
            }
        }
        return super.addItemWithPriceOverrides(orderId, orderItemRequestDTO, priceOrder);
    }
}
