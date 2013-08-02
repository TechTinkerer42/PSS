package org.ets.ereg.common.business.scheduling.service;

import java.util.Set;

import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public interface SchedulingPersistenceService {	
		//Set<Booking> createBooking(Booking booking,Seat seat,ETSCustomer customer);
		//Booking createBooking(Booking booking,Seat seat, TestTakerTest testTakerTest,ETSCustomer customer);
		Booking createBooking(Booking booking,Seat seat);
		TestTakerTest getTestTakerTestByBookingId(Long bookingId);
	    boolean updateBookingStatus(Long bookingId,String bookingStatus);
	    Booking updateBooking(Booking booking);
}
