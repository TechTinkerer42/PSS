package org.ets.ereg.commerce.order.service.call;

import java.util.List;

import org.broadleafcommerce.core.order.service.call.DiscreteOrderItemRequest;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;

public class ETSDiscreteOrderItemRequest extends DiscreteOrderItemRequest {

    private ETSDiscreteOrderItem refOrderItemId;
    private List<ETSDiscreteOrderItem> refOrderItemIds;
    
    public ETSDiscreteOrderItemRequest() {
		super();
	}

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
