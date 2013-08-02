package org.ets.ereg.commerce.order.service.call;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;

public class RescheduleOrderItemRequest extends ETSDiscreteOrderItemRequest {
	
	private FulfillmentGroupItem originalTestItem;

	public FulfillmentGroupItem getOriginalTestItem() {
		return originalTestItem;
	}

	public void setOriginalTestItem(FulfillmentGroupItem originalTestItem) {
		this.originalTestItem = originalTestItem;
	}

}
