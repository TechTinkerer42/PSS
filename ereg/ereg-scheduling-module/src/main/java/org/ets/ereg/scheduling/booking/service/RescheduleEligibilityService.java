package org.ets.ereg.scheduling.booking.service;

import org.ets.ereg.domain.interfaces.model.booking.Booking;

public interface RescheduleEligibilityService {
	
	Boolean isEligibleForReschedule(Booking booking);
	
	Long getRescheduleCountForTest(Booking booking);

}
