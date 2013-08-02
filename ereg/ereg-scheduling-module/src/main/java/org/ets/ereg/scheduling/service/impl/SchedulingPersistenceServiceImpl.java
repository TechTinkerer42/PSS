package org.ets.ereg.scheduling.service.impl;

import javax.annotation.Resource;

import org.ets.ereg.common.business.scheduling.service.SchedulingPersistenceService;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.scheduling.booking.dao.BookingDao;
import org.ets.ereg.scheduling.booking.dao.HeldBookingDao;
import org.ets.ereg.scheduling.testtakertest.dao.TestTakerTestDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("schedulingPersistenceService")
public class SchedulingPersistenceServiceImpl implements
		SchedulingPersistenceService {


	@Resource(name = "bookingDao")
	private BookingDao bookingDao;

	@Resource(name = "testTakerTestDao")
	private TestTakerTestDao testTakerTestDao;
    
    @Resource(name = "heldBookingDao")
    private HeldBookingDao heldBookingDao;

    
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
	public Booking createBooking(Booking booking,Seat seat) {
    	//save TestTakerTest first    	
    	TestTakerTest testTakerTest = testTakerTestDao.save(booking.getTestTakerTestId());
    	
    	//attach test taker to booking and save
    	booking.setTestTakerTestId(testTakerTest);
    	HeldBooking heldBooking = booking.getHeldBooking();
    	booking.setHeldBooking(null);
    	Booking persistedBooking = bookingDao.save(booking);
    	 
    	//attach booking to the held booking and save held booking    	
        heldBooking.setBooking(persistedBooking);      
        HeldBooking pHeldBooking = heldBookingDao.save(heldBooking);
        persistedBooking.setHeldBooking(pHeldBooking);        
    	return  persistedBooking;	
	}

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
	public TestTakerTest getTestTakerTestByBookingId(Long bookingId) {
		return bookingDao.getTestTakerTestByBookingId(bookingId);
	}

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public boolean updateBookingStatus(Long bookingId,
			String bookingStatus) {		
		return bookingDao.updateBookingStatus(bookingId, bookingStatus);
	}
    
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public Booking updateBooking(Booking booking) {
		return bookingDao.save(booking);	
	}

	public void setBookingDao(BookingDao bookingDao) {
		this.bookingDao = bookingDao;
	}

	public void setTestTakerTestDao(TestTakerTestDao testTakerTestDao) {
		this.testTakerTestDao = testTakerTestDao;
	}

	public void setHeldBookingDao(HeldBookingDao heldBookingDao) {
		this.heldBookingDao = heldBookingDao;
	}

}