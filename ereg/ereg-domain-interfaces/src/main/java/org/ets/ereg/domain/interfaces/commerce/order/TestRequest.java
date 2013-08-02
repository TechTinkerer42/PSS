package org.ets.ereg.domain.interfaces.commerce.order;


public interface TestRequest {
	
	Long getOrderId();
	void setOrderId(Long orderId);
	
	boolean isPriceOrder();
	void setPriceOrder(boolean priceOrder);
	
	ETSTestItemRequest getTestItemRequest();
	void setTestItemRequest(ETSTestItemRequest testItemRequest);  
	  
}
