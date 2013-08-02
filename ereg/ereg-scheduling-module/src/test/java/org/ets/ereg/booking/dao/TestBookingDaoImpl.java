package org.ets.ereg.booking.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.booking.BookingStatusTypeImpl;
import org.ets.ereg.domain.booking.EventOutComeStatusImpl;
import org.ets.ereg.domain.booking.HeldBookingImpl;
import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.booking.EventOutComeStatus;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.TestStatusType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.domain.test.TestStatusTypeImpl;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.ets.ereg.scheduling.booking.dao.BookingDao;
import org.ets.ereg.scheduling.booking.dao.HeldBookingDao;
import org.ets.ereg.scheduling.testtakertest.dao.TestTakerTestDao;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })

public class TestBookingDaoImpl {

	private static final int BOOKING_COUNT = 3;

    @Resource(name="bookingDao")
	private BookingDao bookingdao;

    @Resource(name="testTakerTestDao")
	private TestTakerTestDao testTakerTestdao;

    @Resource(name="heldBookingDao")
    private HeldBookingDao heldBookingDao;


    @Ignore
	@Test
	public void testGetFutureBookingByProductPass()
	{
		List<Booking> futureBooking = bookingdao.getFutureBookingByProduct("HSE", new Long(1));
		Assert.assertEquals(1, futureBooking.size());
	}

    @Ignore
	@Test
	public void testGetFutureBookingByProductFail()
	{
		List<Booking> futureBooking = bookingdao.getFutureBookingByProduct("ABCD", new Long(1));
		Assert.assertEquals(0, futureBooking.size());
	}


    @Ignore
	@Test
	public void testGetFutureBookingByProductDisplayCountPass()
	{
		SearchParameters searchParams =new  SearchParameters();
		searchParams.setPageSize(2);
		List<Booking> futureBooking = bookingdao.getFutureBookingByProduct("HSE", new Long(2),searchParams);
		Assert.assertTrue(futureBooking.size()==2);

	}

    @Ignore
	@Test
	public void testGetFutureBookingByProductSortedByDateAscPass()
	{
		SearchParameters searchParams =new  SearchParameters();
		searchParams.setPageSize(2);
		List<Booking> futureBooking = bookingdao.getFutureBookingByProduct("HSE", new Long(2),searchParams);

		Date date= new Date(919627495); //21 Feb 1999
		for(Booking appt:futureBooking){
			Assert.assertTrue(appt.getAppointmentDateTime().equals(date) || appt.getAppointmentDateTime().after(date));
			date=appt.getAppointmentDateTime();
		}

	}


    @Ignore
	@Test
	public void testGetFutureBookingByProductPageSizePass()
	{
		SearchParameters searchParams =new  SearchParameters();
		searchParams.setPageSize(1);
		List<Booking> futureBooking = bookingdao.getFutureBookingByProduct("HSE", new Long(2));
		List<Booking> futureBookingDisplay = bookingdao.getFutureBookingByProduct("HSE", new Long(2),searchParams);
		Assert.assertTrue((futureBookingDisplay.size())<(futureBooking.size()));

	}

	@Test
	public void testGetAvailableForms()
	{
		List<Form> futureBooking = bookingdao.getAvailableForms(new Long(1),1l,new Date());
		Assert.assertEquals(1, futureBooking.size());
	}

	@Test
	public void testGetAvailableTest()
	{
		List<org.ets.ereg.domain.interfaces.model.test.Test> remainingTest = bookingdao.getAvailableTest(new Long(1),"HSET1");
		Assert.assertEquals(0, remainingTest.size());
	}
	@Test
	public void testGetAllTest()
	{
		List<org.ets.ereg.domain.interfaces.model.test.Test> allTest = bookingdao.getAllTest("HSET1");
		Assert.assertEquals(0, allTest.size());
	}
	@Test
	public void testGetSubForm()
	{
		Form subForm = bookingdao.getSubForm(new Long(1),1l,"EN", "PBT");
		Assert.assertNull(subForm);
	}
	@Test
	public void testGetAllTestById()
	{
		org.ets.ereg.domain.interfaces.model.test.Test test = bookingdao.getAllTestById("HSE",1l);
		Assert.assertNotNull(test);
	}
	@Test
	public void testGetAllFormById()
	{
		List<Form> forms = bookingdao.getAllFormById(new Long(1));
		Assert.assertEquals(1, forms.size());
	}
	@Test
	public void testGetBookingById()
	{
		Booking bkng = bookingdao.getBookingById(new Long(1));
		Assert.assertNotNull(bkng);
	}

   @Test
    public void testGetBookingByProductPass()
    {
        List<Booking> futureBooking = bookingdao.getBookingByCustomerId( new Long(1), "HSE", BOOKING_COUNT);
        Assert.assertNotNull(futureBooking);
        Assert.assertTrue(futureBooking.size() <= BOOKING_COUNT);
    }

    @Test
    public void testGetByProductFail()
    {
        List<Booking> futureBooking = bookingdao.getBookingByCustomerId(new Long(1), "ABCD", BOOKING_COUNT);
        Assert.assertNotNull(futureBooking);
        Assert.assertEquals(0, futureBooking.size());
    }

    @Test
    public void testGetBookingByProductDisplayCountPass()
    {
        List<Booking> futureBooking = bookingdao.getBookingByCustomerId(new Long(2), "HSE", BOOKING_COUNT);
        Assert.assertNotNull(futureBooking);
        Assert.assertTrue(futureBooking.size() <= BOOKING_COUNT);

    }

    @Test
    public void testBookingAccomodation(){

        List<Booking> futureBooking = bookingdao.getBookingByCustomerId(new Long(1), "HSE", BOOKING_COUNT);
        Assert.assertNotNull(futureBooking);
        Assert.assertEquals(futureBooking.size(), 1);
        Set<BookingAccommodation> bookingAccommodations = futureBooking.get(0).getBookingAccommodations();
        Assert.assertNotNull(bookingAccommodations);
        Assert.assertEquals(2, bookingAccommodations.size());
        for (BookingAccommodation bookingAccommodation : bookingAccommodations) {
            Assert.assertNotNull(bookingAccommodation.getAccommodationType().getCode());
            Assert.assertNotNull(bookingAccommodation.getAccommodationTypeValue().getAccommodationTypeValueIdentifier());
            Assert.assertNotNull(bookingAccommodation.getApprovedDate());
            Assert.assertNotNull(bookingAccommodation.getExpirationDate());
            Assert.assertNotNull(bookingAccommodation.getBooking());
            Assert.assertNotNull(bookingAccommodation.getOthrAcmdtnTypTxt());
            Assert.assertEquals("Test", bookingAccommodation.getOthrAcmdtnTypTxt());


        }
    }

    @Test
	public void testGetBookingsWithAccommodation(){

		List<Booking> bookings = bookingdao.getBookingsWithAccommodation(1L, "2",
				DateHandler.getCurrentDate(),
				DateHandler.convertToDate(2015, Calendar.DECEMBER, 31));

		Assert.assertFalse(bookings.isEmpty());
	}

    @Test
    @Transactional(propagation=Propagation.REQUIRED)
   	public void testUpdateBookingStatus(){
   	 	boolean isUpdated = bookingdao.updateBookingStatus(2L, "RSRVD");
   		Assert.assertTrue(isUpdated);
   	}

    @Test
   	public void testGetTestTakerTestByBookingId(){
   	 	TestTakerTest testTakerTest = bookingdao.getTestTakerTestByBookingId(2L);
   		Assert.assertNotNull(testTakerTest.getTestTakerTestId());
   	}

    @Test
    public void createBooking(){

    	Booking booking = new BookingImpl();
    	booking.setAppointmentDateTime(new Date());
    	booking.setTimeZoneOffsetQuantity(-5f);
    	booking.setTestVariation(getTestVariation());

    	BookingStatusType bookingStatusType = new BookingStatusTypeImpl();
    	bookingStatusType.setCode("RSRVD");
    	bookingStatusType.setDescription("Held Booking");
    	bookingStatusType.setDisplayable(true);
    	bookingStatusType.setDisplaySequence(1L);
    	booking.setBookingStatusType(bookingStatusType);

    	booking.setCustDOB(Calendar.getInstance().getTime());
    	booking.setCustFirstName("Gopal");
    	booking.setCustMidtname("Krishna");
    	booking.setCustLastName("Golla");

    	DeliveryModeType deliveryModeType = new DeliveryModeTypeImpl();
    	deliveryModeType.setCode("PBT");
    	deliveryModeType.setDescription("Paper Based Test");
    	deliveryModeType.setDisplayable(true);
    	deliveryModeType.setDisplaySequence(1L);
    	//booking.setDeliveryModeType(deliveryModeType);

    	/*TestId testId = new TestId();
    	testId.setProgramCode("HSE");
    	testId.setTestCode("MATH");
    	org.ets.ereg.domain.interfaces.model.test.Test test = new TestImpl();
    	test.setId(testId);
    	booking.setTest(test);*/

    	LanguageType lang = new LanguageTypeImpl();
    	lang.setCode("EN");
    	//booking.setLangCode(lang);

    	TestVarianceId TestVarianceId = new TestVarianceId();
    	TestVarianceId.setDeliveryModeCode(deliveryModeType.getCode());
    	TestVarianceId.setLanguageCode("EN");
    	TestVarianceId.setTestId(5l);
    	TestVariation testVariation = new TestVariationImpl();
    	testVariation.setDeliveryModeType(deliveryModeType);
    	testVariation.setLanguageType(lang);
    	testVariation.setId(TestVarianceId);
    	booking.setDuration(10L);
    	EventOutComeStatus eventOutComeStatus = new EventOutComeStatusImpl();
    	eventOutComeStatus.setCode("NCI");
    	eventOutComeStatus.setDescription("Paper Based Test");
    	eventOutComeStatus.setDisplayable(true);
    	eventOutComeStatus.setDisplaySequence(1L);

    	booking.setEvntOutComeStatus(eventOutComeStatus);

    	booking.setRegistrationSystemId("ABCD");

    	List<Booking> bookings = bookingdao.getBookingsWithAccommodation(1L, "2",
				DateHandler.getCurrentDate(),
				DateHandler.convertToDate(2015, Calendar.DECEMBER, 31));

    	booking.setTestCenter(bookings.get(0).getTestCenter());



    	TestTakerTest testTakerTest = new TestTakerTestImpl();
    	Customer customer = new CustomerImpl();
    	customer.setId(1L);

    	testTakerTest.setCustomer(customer);
    	Set<Booking> bookingsSet = new HashSet<Booking>();
    	bookingsSet.add(booking);
    	testTakerTest.setBookings(bookingsSet);

    	TestStatusType testStatusType = new TestStatusTypeImpl();
    	testStatusType.setCode("SCDL");
    	testStatusType.setDescription("Scheduled");
    	testStatusType.setDisplayable(true);
    	testStatusType.setDisplaySequence(1L);
    	testTakerTest.setTestStatusCode(testStatusType);

    	booking.setTestTakerTestId(testTakerTest);


    	HeldBooking heldBooking = new HeldBookingImpl();

    	heldBooking.setBooking(booking);
    	heldBooking.setHoldDuration("20");
    	heldBooking.setHoldSeatId("123");
    	heldBooking.setHoldSourceDesc("STN");
    	heldBooking.setLabCode("LAB");
    	heldBooking.setSeatCode("HSE");
    	heldBooking.setSiteCode("STN");

    	TestTakerTest pTestTaker = testTakerTestdao.save(booking.getTestTakerTestId());

    	//attach test taker to booking and save
    	booking.setTestTakerTestId(pTestTaker);
    	Booking persistedBooking = bookingdao.save(booking);

    	heldBooking.setBooking(persistedBooking);
    	heldBookingDao.save(heldBooking);
    }
    private TestVariation getTestVariation(){
		TestVariation testVariation = new TestVariationImpl();
		TestVarianceId id = new TestVarianceId();
		id.setDeliveryModeCode("PBT");
		id.setLanguageCode("EN");
		id.setTestId(1L);
		testVariation.setId(id);

		return testVariation;

	}
    
    @Test
    public void testIsEligibleForReschedule() {
    	Booking booking = bookingdao.getBookingById(6L);
    	Boolean isEligible = bookingdao.isEligibleForReschedule(booking);
    	Assert.assertEquals(Boolean.FALSE, isEligible);
    	booking = bookingdao.getBookingById(5L);
    	isEligible = bookingdao.isEligibleForReschedule(booking);
    	Assert.assertEquals(Boolean.TRUE, isEligible);
	}
    
    
    @Test
    public void testGetRescheduleCountForBooking() {
    	Booking booking = bookingdao.getBookingById(5L);
    	Long count = bookingdao.getRescheduleCountForBooking(booking);
    	Assert.assertEquals(new Long(2), count);
    }

}
