package org.ets.ereg.commerce.order.service.type;

import org.broadleafcommerce.core.order.service.type.OrderItemType;

public class ETSOrderItemType extends OrderItemType {

    private static final long serialVersionUID = 1L;
	
	public static final OrderItemType TEST = new OrderItemType("org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem", "Test Order Item");
    public static final OrderItemType MEMBERSHIP = new OrderItemType("org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem", "Membership Order Item");
    public static final OrderItemType BATTERY = new OrderItemType("org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem", "Battery Order Item");
    public static final OrderItemType RESCHEDULE = new OrderItemType("org.ets.ereg.domain.interfaces.model.order.RescheduleTestDiscreteOrderItem", "Reschedule Test Order Item");
    
}
