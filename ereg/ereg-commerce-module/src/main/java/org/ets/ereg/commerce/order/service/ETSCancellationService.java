package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.common.money.Money;
import org.ets.ereg.commerce.order.vo.CancelTestVO;

import com.broadleafcommerce.oms.fulfillment.domain.FulfillmentOrderItem;

public interface ETSCancellationService  {

	Money calculateAmountToCancel(FulfillmentOrderItem fulfillmentOrderItem);

	CancelTestVO generateCancellationDetails(Long fulfillmentGroupItemId,
			int quantity);

}
