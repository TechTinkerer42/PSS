package org.ets.ereg.domain.interfaces.commerce.order;

import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

/**
 * ETSTestItemRequest extends broadlef OrderItemRequestDTO
 * @author GGOLLA
 *
 */
public class ETSTestItemRequest extends OrderItemRequestDTO {

  private Seat seat;
  private Booking booking;
  
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

}
