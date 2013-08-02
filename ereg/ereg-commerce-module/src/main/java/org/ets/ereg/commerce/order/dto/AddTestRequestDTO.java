package org.ets.ereg.commerce.order.dto;

import org.ets.ereg.domain.interfaces.commerce.order.AddTestRequest;
import org.ets.ereg.domain.interfaces.commerce.order.ETSTestItemRequest;

public class AddTestRequestDTO implements AddTestRequest {

	private Long orderId;
	private boolean priceOrder;
	private ETSTestItemRequest testItemRequest;
	
	@Override
	public Long getOrderId() {
		return orderId;
	}
	
	@Override
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	@Override
	public boolean isPriceOrder() {
		return priceOrder;
	}
	
	@Override
	public void setPriceOrder(boolean priceOrder) {
		this.priceOrder = priceOrder;
	}
	
	@Override
	public ETSTestItemRequest getTestItemRequest() {
		return testItemRequest;
	}
	
	@Override
	public void setTestItemRequest(ETSTestItemRequest testItemRequest) {
		this.testItemRequest = testItemRequest;
	}
	
}
