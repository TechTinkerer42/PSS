package org.ets.ereg.domain.interfaces.model.order;

import java.util.List;

import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;

public interface ETSDiscreteOrderItem extends DiscreteOrderItem {

     //public Boolean isDependent();
     //public void setDependent(Boolean dependent);

	 ETSDiscreteOrderItem getRefOrderItemId();	
 	 void setRefOrderItemId(ETSDiscreteOrderItem refOrderItemId);

     List<ETSDiscreteOrderItem> getRefOrderItemIds();
	 void setRefOrderItemIds(List<ETSDiscreteOrderItem> refOrderItemIds);
	 boolean canCountTowardsItemCount();
}
