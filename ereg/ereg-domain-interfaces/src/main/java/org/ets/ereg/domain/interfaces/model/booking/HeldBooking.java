package org.ets.ereg.domain.interfaces.model.booking;

import org.ets.ereg.domain.interfaces.model.booking.Booking;

public interface HeldBooking {

	Long getId();
	void setId(Long id);
	
	String getHoldSeatId(); 
	void setHoldSeatId(String holdSeatId);
	
	String getHoldSourceDesc();
	void setHoldSourceDesc(String holdSourceDesc);
	
	String getHoldDuration();
	void setHoldDuration(String holdDuration);
	
	String getSiteCode();
	void setSiteCode(String siteCode);
	
	String getLabCode();
	void setLabCode(String labCode);
	
	String getSeatCode();
	void setSeatCode(String seatCode);
	
	Booking getBooking();
	void setBooking(Booking booking);
	
}
