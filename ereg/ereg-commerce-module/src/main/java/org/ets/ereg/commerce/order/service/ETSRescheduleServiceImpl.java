package org.ets.ereg.commerce.order.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.ets.ereg.commerce.order.dto.RescheduleTestRequestDTO;
import org.ets.ereg.domain.interfaces.commerce.order.ETSRescheduleTestItemRequest;
import org.ets.ereg.domain.interfaces.commerce.order.RescheduleTestRequest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.springframework.stereotype.Service;

@Service("etsRescheduleService")
public class ETSRescheduleServiceImpl implements ETSRescheduleService {
	
	@Resource(name = "etsOrderService")
	protected ETSOrderService orderService;
	
	public boolean eligibleForRescheduleWithinBattery(FulfillmentGroupItem origFGI, BatterySubscription sub) {
		//TODO implement
		
		// is Test within battery?
		// if so, is the test no longer available in the battery? 		
		
		return true;
	}
	
	@Override
	public Order reschedule(Order cart, FulfillmentGroupItem origFGI, Booking newBooking, Seat newSeat) 
			throws AddToCartException {
		
		//TODO call Patrick's service to determine if new test can be rescheduled.
		
		RescheduleTestRequest rescheduleTestRequest = new RescheduleTestRequestDTO();
		rescheduleTestRequest.setOrderId(cart.getId());
		rescheduleTestRequest.setPriceOrder(true);
		
		ETSRescheduleTestItemRequest itemRequest = new ETSRescheduleTestItemRequest();
		itemRequest.setBooking(newBooking);
		itemRequest.setSeat(newSeat);
		itemRequest.setOriginalTestItem(origFGI);
		
		//Construct the request from the attributes of the original order item: such as product, sku, and quantity
		TestDiscreteOrderItem origTest = (TestDiscreteOrderItem) origFGI.getOrderItem();
		itemRequest.setQuantity(origTest.getQuantity());
		itemRequest.setProductId(origTest.getProduct().getId());
		itemRequest.setSkuId(origTest.getSku().getId());
		itemRequest.getItemAttributes().putAll(origTest.getAdditionalAttributes());
		
		itemRequest.setOverrideRetailPrice(new Money(BigDecimal.ZERO));
		itemRequest.setOverrideSalePrice(new Money(BigDecimal.ZERO));
		
		rescheduleTestRequest.setRescheduleTestItemRequest(itemRequest);
		
		return orderService.addRescheduleTest(rescheduleTestRequest);
		
	}

}
