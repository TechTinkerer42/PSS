package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public interface ETSRescheduleService {

	Order reschedule(Order cart, FulfillmentGroupItem origFGI, Booking newBooking, Seat newSeat) throws AddToCartException;
	
}
