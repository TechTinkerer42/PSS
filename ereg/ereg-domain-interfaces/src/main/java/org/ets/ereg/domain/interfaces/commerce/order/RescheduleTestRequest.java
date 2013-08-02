package org.ets.ereg.domain.interfaces.commerce.order;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;

public interface RescheduleTestRequest {
	
	Long getOrderId();
	void setOrderId(Long orderId);
	
	boolean isPriceOrder();
	void setPriceOrder(boolean priceOrder);
	
	ETSRescheduleTestItemRequest getRescheduleTestItemRequest();
	void setRescheduleTestItemRequest(ETSRescheduleTestItemRequest testItemRequest);  
	
}
