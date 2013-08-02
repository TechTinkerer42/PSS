package org.ets.ereg.commerce.order.dto;

import org.ets.ereg.domain.interfaces.commerce.order.ETSRescheduleTestItemRequest;
import org.ets.ereg.domain.interfaces.commerce.order.RescheduleTestRequest;

public class RescheduleTestRequestDTO implements RescheduleTestRequest {

	private Long orderId;
	private boolean priceOrder;
	private ETSRescheduleTestItemRequest rescheduleTestItemRequest;
	
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
	public ETSRescheduleTestItemRequest getRescheduleTestItemRequest() {
		return rescheduleTestItemRequest;
	}

	@Override
	public void setRescheduleTestItemRequest(
			ETSRescheduleTestItemRequest rescheduleTestItemRequest) {
		this.rescheduleTestItemRequest = rescheduleTestItemRequest;
	}

}
