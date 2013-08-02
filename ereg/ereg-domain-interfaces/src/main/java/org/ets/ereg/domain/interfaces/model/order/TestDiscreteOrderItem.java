package org.ets.ereg.domain.interfaces.model.order;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.booking.Booking;

public interface TestDiscreteOrderItem extends ETSDiscreteOrderItem {

	List<Booking> getAllBookings();
	void setAllBookings(List<Booking> allBookings);	
	Booking getFirstBooking();
	boolean getEligibleForBatteryInCart();
	void setEligibleForBatteryInCart(boolean eligibleForBatteryInCart);
	boolean getEligibleForPurchasedBattery();
	void setEligibleForPurchasedBattery(boolean eligibleForPurchasedBattery);

}
