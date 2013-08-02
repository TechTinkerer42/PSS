package org.ets.ereg.commerce.order.vo;

import org.ets.ereg.domain.interfaces.model.booking.Booking;
@Deprecated
public class BookingVO {
	Booking booking;

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public BookingVO(Booking booking){
		this.booking=booking;
	}

}
