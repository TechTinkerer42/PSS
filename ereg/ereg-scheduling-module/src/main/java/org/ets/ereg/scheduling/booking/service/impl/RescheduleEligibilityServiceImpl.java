package org.ets.ereg.scheduling.booking.service.impl;

import javax.annotation.Resource;

import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.scheduling.booking.dao.BookingDao;
import org.ets.ereg.scheduling.booking.service.RescheduleEligibilityService;
import org.springframework.stereotype.Service;

@Service("rescheduleEligibilityService")
public class RescheduleEligibilityServiceImpl implements
		RescheduleEligibilityService {
	
	@Resource(name = "bookingDao")
	BookingDao bookingDao;

	@Override
	public Boolean isEligibleForReschedule(Booking booking) {
		return bookingDao.isEligibleForReschedule(booking);
	}

	@Override
	public Long getRescheduleCountForTest(Booking booking) {
		return bookingDao.getRescheduleCountForBooking(booking);
	}

}
