package org.ets.ereg.commerce.order.dto;

import java.util.List;

import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;

public class ETSDiscreteOrderItemRequestDTO extends OrderItemRequestDTO{

    private ETSDiscreteOrderItem refOrderItemId;
    
    private List<ETSDiscreteOrderItem> refOrderItemIds;

	public ETSDiscreteOrderItem getRefOrderItemId() {
		return refOrderItemId;
	}

	public void setRefOrderItemId(ETSDiscreteOrderItem refOrderItemId) {
		this.refOrderItemId = refOrderItemId;
	}

	public List<ETSDiscreteOrderItem> getRefOrderItemIds() {
		return refOrderItemIds;
	}

	public void setRefOrderItemIds(List<ETSDiscreteOrderItem> refOrderItemIds) {
		this.refOrderItemIds = refOrderItemIds;
	}

}
