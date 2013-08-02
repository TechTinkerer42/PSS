package org.ets.ereg.scheduling.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.TestVariationDao;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatWithoutHoldRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.ets.ereg.scheduling.request.HoldSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReleaseSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReserveSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReserveSeatWithoutHoldRequestImpl;
import org.ets.ereg.scheduling.request.TCFindSeatRequestImpl;
import org.ets.ereg.scheduling.response.AvailabilityStatusImpl;
import org.ets.ereg.scheduling.response.HeldSeatImpl;
import org.ets.ereg.scheduling.response.SeatImpl;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestMockSchedulingService {

	@Resource(name = "mockSchedulingService")
	private SchedulingService mockSchedulingService;

	@Resource(name = "testCenterService")
	private TestCenterService testCenterService;

	@Resource(name = "testVariationDao")
	private TestVariationDao testVariationDao;

	private Seat seat;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	    //DateFormat dateFormat = new SimpleDateFormat("yyyymmdd HH:mm:ss");
		DateTime dt = new DateTime();
		if (dt.getDayOfWeek() >= DateTimeConstants.FRIDAY) {
	        dt = dt.plusWeeks(1);
	    }
	    dt = dt.withDayOfWeek(DateTimeConstants.FRIDAY);
		seat = new SeatImpl(1);
		seat.setAvailabilityStatus(new AvailabilityStatusImpl());
		seat.setLocalStartDateTime(dt.toGregorianCalendar());
		seat.setDeliveryModeCode("PBT");
		seat.setTestVariation(getTestVariation());
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
	public void testFindSeat() {
		TCFindSeatRequest request = new TCFindSeatRequestImpl();
		request.setTestCenterId(52L);
		request.setDeliveryModeCode("PBT");
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTime(new Date());
		request.setSearchFromDate(fromDate);
		Calendar toDate = Calendar.getInstance();
		toDate.add(Calendar.DATE, 1);
		toDate.setTime(new Date());
		request.setSearchToDate(toDate);
		request.setTestVariation(getTestVariation());
		FindSeatResponse response = mockSchedulingService.findSeat(request);
		if (!(fromDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fromDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			assertTrue(response.getSeats().size() > 1);
        }

	}

	@Test
	public void testHoldSeatOnSuccess() {
		HoldSeatRequest request = new HoldSeatRequestImpl();
		request.setSeat(seat);
		HoldSeatResponse response = mockSchedulingService.holdSeat(request);
		assertTrue(response.isSuccessful());
		assertNotNull(response.getHeldSeat());
		assertNotNull(response.getHeldSeat().getHoldCode());
	}

	@Test
	public void testHoldSeatOnMondayFail() {
		HoldSeatRequest request = new HoldSeatRequestImpl();
		Calendar calDate = seat.getLocalStartDateTime();
		calDate.add(Calendar.DATE, 3);
		seat.setLocalStartDateTime(calDate);
		request.setSeat(seat);
		HoldSeatResponse response = mockSchedulingService.holdSeat(request);
		assertFalse(response.isSuccessful());
	}

	@Test
	public void testReleaseSeat() {
		ReleaseSeatRequest request = new ReleaseSeatRequestImpl();
		request.setHeldSeat(new HeldSeatImpl());
		ReleaseSeatResponse response = mockSchedulingService.releaseSeat(request);
		assertTrue(response.isSuccessful());
	}

	@Test
	public void testReserveSeat() {
		ReserveSeatRequest request = new ReserveSeatRequestImpl();
		request.setHeldSeat(new HeldSeatImpl());
		ReserveSeatResponse response = mockSchedulingService.reserveSeat(request);
		assertTrue(response.isSuccessful());
	}

	@Test
	public void testReserveWithoutHoldSeat() {
		ReserveSeatWithoutHoldRequest request = new ReserveSeatWithoutHoldRequestImpl();
		request.setSeat(seat);
		ReserveSeatResponse response = mockSchedulingService.reserveSeatWithoutHold(request);
		assertTrue(response.isSuccessful());
	}

}
