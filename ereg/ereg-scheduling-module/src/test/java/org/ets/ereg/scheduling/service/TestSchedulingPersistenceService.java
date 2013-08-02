package org.ets.ereg.scheduling.service;

import java.util.HashSet;
import java.util.Set;

import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.booking.HeldBookingImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.scheduling.booking.dao.BookingDao;
import org.ets.ereg.scheduling.booking.dao.HeldBookingDao;
import org.ets.ereg.scheduling.response.SeatImpl;
import org.ets.ereg.scheduling.service.impl.SchedulingPersistenceServiceImpl;
import org.ets.ereg.scheduling.testtakertest.dao.TestTakerTestDao;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.util.Assert;

@RunWith(JMock.class)
public class TestSchedulingPersistenceService {
	
	private static Mockery mockingContext = new Mockery();

	private static BookingDao mockBookingDao;	
	private static TestTakerTestDao mockTestTakerTestDao;	
    private static HeldBookingDao mockHeldBookingDao;
    
    private static SchedulingPersistenceServiceImpl schedulingPersistenceService = new SchedulingPersistenceServiceImpl();
	
	@BeforeClass
	public static void setup() {
		setupMockObjects();
		schedulingPersistenceService.setBookingDao(mockBookingDao);
		schedulingPersistenceService.setHeldBookingDao(mockHeldBookingDao);
		schedulingPersistenceService.setTestTakerTestDao(mockTestTakerTestDao);		
	}
	
	private static void setupMockObjects() {	
		mockBookingDao = mockingContext.mock(BookingDao.class);
		mockTestTakerTestDao = mockingContext.mock(TestTakerTestDao.class);
		mockHeldBookingDao = mockingContext.mock(HeldBookingDao.class);
	}
	
	@Test
	public void testCreateBooking(){
		
		Seat seat = new SeatImpl(0);
		
		final HeldBooking heldBooking = new HeldBookingImpl();
		
		final Booking booking = new BookingImpl();
		booking.setId(1L);
		booking.setHeldBooking(heldBooking);
	
		Set<Booking> bookings = new HashSet<Booking>();
		bookings.add(booking);
		
		final TestTakerTest testTakerTest = new TestTakerTestImpl();
		testTakerTest.setTestTakerTestId(1L);
		testTakerTest.setBookings(bookings);
		
		booking.setTestTakerTestId(testTakerTest);
		
		mockingContext.checking(new Expectations() {
			{	
				 oneOf(mockTestTakerTestDao).save(testTakerTest);
				 will(returnValue(testTakerTest));
				 oneOf(mockBookingDao).save(booking);
				 will(returnValue(booking));				 
				 oneOf(mockHeldBookingDao).save(heldBooking);
				 will(returnValue(heldBooking));
		    } });
		
		Booking persistedBooking = schedulingPersistenceService.createBooking(booking, seat);
		Assert.notNull(persistedBooking);
		mockingContext.assertIsSatisfied();
	}
	
	@Test
	public void testGetTestTakerTestByBookingId() {
		
		final HeldBooking heldBooking = new HeldBookingImpl();
		
		final Booking booking = new BookingImpl();
		booking.setId(1L);
		booking.setHeldBooking(heldBooking);
	
		Set<Booking> bookings = new HashSet<Booking>();
		bookings.add(booking);
		
		final TestTakerTest testTakerTest = new TestTakerTestImpl();
		testTakerTest.setTestTakerTestId(1L);
		testTakerTest.setBookings(bookings);
		
		booking.setTestTakerTestId(testTakerTest);
		
		mockingContext.checking(new Expectations() {
			{	
				
				 oneOf(mockBookingDao).getTestTakerTestByBookingId(1L);
				 will(returnValue(testTakerTest));				 
				 
		    } });
		
		TestTakerTest testTakerTest1 = schedulingPersistenceService.getTestTakerTestByBookingId(1L);
		Assert.notNull(testTakerTest1);
		mockingContext.assertIsSatisfied();
		
	}

   
	@Test
	public void testUpdateBookingStatus(){
		
		mockingContext.checking(new Expectations() {
			{	
				
				 oneOf(mockBookingDao).updateBookingStatus(1L, BookingStatusType.RELEASED);
				 will(returnValue(true));				 
				 
		    } });
		
		Assert.isTrue(schedulingPersistenceService.updateBookingStatus(1L, BookingStatusType.RELEASED));
		mockingContext.assertIsSatisfied();
		
	}
    
	@Test
	public void testUpdateBooking() {
	
		
		final HeldBooking heldBooking = new HeldBookingImpl();
		
		final Booking booking = new BookingImpl();
		booking.setId(1L);
		booking.setHeldBooking(heldBooking);
	
		Set<Booking> bookings = new HashSet<Booking>();
		bookings.add(booking);
		
		final TestTakerTest testTakerTest = new TestTakerTestImpl();
		testTakerTest.setTestTakerTestId(1L);
		testTakerTest.setBookings(bookings);
		
		booking.setTestTakerTestId(testTakerTest);
		
		mockingContext.checking(new Expectations() {
			{	
			
				 oneOf(mockBookingDao).save(booking);
				 will(returnValue(booking));				 
				
		    } });
		
		Booking updatedBooking = schedulingPersistenceService.updateBooking(booking);
		Assert.notNull(updatedBooking);
		mockingContext.assertIsSatisfied();
	}

}
