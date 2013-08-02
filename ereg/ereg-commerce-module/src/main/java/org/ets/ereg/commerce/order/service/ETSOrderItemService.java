package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.core.order.service.OrderItemService;
import org.ets.ereg.commerce.order.service.call.BatteryOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.ETSDiscreteOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.MembershipOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.RescheduleOrderItemRequest;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.RescheduleTestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;

public interface ETSOrderItemService extends OrderItemService {

    MembershipDiscreteOrderItem createMembershipDiscreteOrderItem(MembershipOrderItemRequest itemRequest);
    BatteryDiscreteOrderItem createBatteryDiscreteOrderItem(BatteryOrderItemRequest itemRequest);
    TestDiscreteOrderItem createTestDiscreteOrderItem(ETSDiscreteOrderItemRequest itemRequest);
    RescheduleTestDiscreteOrderItem createRescheduleTestDiscreteOrderItem(RescheduleOrderItemRequest itemRequest);

}
