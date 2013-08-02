package org.ets.ereg.commerce.order.service.type;

import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;

public class ETSFulfillmentGroupStatusType extends FulfillmentGroupStatusType {

	private static final long serialVersionUID = 1L;

	public static final FulfillmentGroupStatusType CANCELLED = new FulfillmentGroupStatusType("CANCELLED", "Cancelled");
	public static final FulfillmentGroupStatusType RESCHEDULED = new FulfillmentGroupStatusType("RESCHEDULED", "Rescheduled");
	
}
