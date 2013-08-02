package org.ets.ereg.domain.order.type;

import org.broadleafcommerce.core.order.service.type.FulfillmentType;

public class ETSFulfillmentType extends FulfillmentType 
{
	private static final long serialVersionUID = 1L;
	public static final FulfillmentType TEST = new FulfillmentType("TEST", "Test");
	public static final FulfillmentType MEMBERSHIP_FEE = new FulfillmentType("MEMBERSHIP_FEE", "Membership_Fee");
    public static final FulfillmentType BATTERY_SUBSCRIPTION = new FulfillmentType("BATTERY_SUBSCRIPTION", "Battery_Subscription");
    public static final FulfillmentType RESCHEDULE_FEE = new FulfillmentType("RESCHEDULE_FEE", "Reschedule_Fee");

    public static FulfillmentType getInstance(final String type) {
        return FulfillmentType.getInstance(type);
    }

}
