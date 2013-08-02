package org.ets.ereg.domain.interfaces.model.order;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;

public interface RescheduleTestDiscreteOrderItem extends TestDiscreteOrderItem {

	FulfillmentGroupItem getOriginalTestItem();
	void setOriginalTestItem(FulfillmentGroupItem originalTestItem);
	
}