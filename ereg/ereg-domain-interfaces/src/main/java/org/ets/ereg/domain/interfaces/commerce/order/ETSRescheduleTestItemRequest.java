package org.ets.ereg.domain.interfaces.commerce.order;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public class ETSRescheduleTestItemRequest extends OrderItemRequestDTO {

	private Seat seat;
	private Booking booking;
	private FulfillmentGroupItem originalTestItem;
	
	public Seat getSeat(){
		return seat;
	}
	  
	public void setSeat(Seat seat){
		this.seat = seat;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	} 
	  
	public FulfillmentGroupItem getOriginalTestItem() {
		return originalTestItem;
	}

	public void setOriginalTestItem(FulfillmentGroupItem originalTestItem) {
		this.originalTestItem = originalTestItem;
	}
}



