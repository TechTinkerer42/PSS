package org.ets.ereg.scheduling.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ets.ereg.common.business.service.RulesService;
import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;
import org.ets.ereg.common.business.vo.RulesResponseVo;
import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingRequestValidationException;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.AgencyProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.SchedulingType;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterProgram;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatWithoutHoldRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.domain.scheduling.AgencyProgramImpl;
import org.ets.ereg.domain.scheduling.SchedulingTypeImpl;
import org.ets.ereg.domain.scheduling.TestCenterImpl;
import org.ets.ereg.domain.scheduling.TestCenterProgramImpl;
import org.ets.ereg.domain.test.TestImpl;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.ets.ereg.scheduling.request.HoldSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReleaseSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReserveSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReserveSeatWithoutHoldRequestImpl;
import org.ets.ereg.scheduling.request.TCFindSeatRequestImpl;
import org.ets.ereg.scheduling.response.AvailabilityStatusImpl;
import org.ets.ereg.scheduling.response.HeldSeatImpl;
import org.ets.ereg.scheduling.response.SeatImpl;
import org.ets.ereg.scheduling.service.impl.MockSchedulingServiceImpl;
import org.ets.ereg.scheduling.service.impl.SchedulingServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;


@RunWith(JMock.class)
public class TestSchedulingService {

	private static Logger logger = LoggerFactory.getLogger(TestSchedulingService.class);

	private static Mockery mockingContext = new Mockery();

	private static SchedulingServiceImpl schedulingServiceImpl = new SchedulingServiceImpl();
	private static RulesService<RetakeRulesRequestVo> mockReTakeTestRulesService;
    private static TestCenterService mockTestCenterService;
    private static MockSchedulingServiceImpl mockSchedulingService;
	private Seat seat;


	@BeforeClass
	public static void setup() {
		setupMockObjects();
		schedulingServiceImpl.setReTakeTestRulesService(mockReTakeTestRulesService);
		schedulingServiceImpl.setTestCenterService(mockTestCenterService);
		schedulingServiceImpl.setMockSchedulingService(mockSchedulingService);
		schedulingServiceImpl.setUseMockSchedulingService("yes");
	}

	@SuppressWarnings("unchecked")
	private static void setupMockObjects() {

		mockReTakeTestRulesService =mockingContext.mock(RulesService.class);
		mockTestCenterService = mockingContext.mock(TestCenterService.class);
		mockSchedulingService = new MockSchedulingServiceImpl();
		mockSchedulingService.setTestCenterService(mockTestCenterService);
	}



	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		DateTime dt = new DateTime();
		if (dt.getDayOfWeek() >= DateTimeConstants.FRIDAY) {
	        dt = dt.plusWeeks(1);
	    }
	    dt = dt.withDayOfWeek(DateTimeConstants.FRIDAY);
		seat = new SeatImpl(1);
		seat.setAvailabilityStatus(new AvailabilityStatusImpl());
		seat.setLocalStartDateTime(dt.toGregorianCalendar());
		seat.setDeliveryModeCode("PBT");
		seat.setTestVariation(getTestVariation(5l, "PBT", "EN"));

		TestCenter testCenter = getTestCenter();

		seat.setTestCenter(testCenter);
	}

	private TestVariation getTestVariation(Long testId, String deliveryMode, String languageType){
		TestVariation testVariation = new TestVariationImpl();
		TestVarianceId id = new TestVarianceId();
		id.setDeliveryModeCode(deliveryMode);
		id.setLanguageCode(languageType);
		id.setTestId(testId);
	    testVariation.setId(id);

	    ProgramType programType = new ProgramTypeImpl();
        programType.setCode("HSE");
        programType.setDescription("HSET TEST");

		org.ets.ereg.domain.interfaces.model.test.Test test = new TestImpl();
		test.setTestId(testId);
		test.setProgramType(programType);
		testVariation.setTest(test);
		return testVariation;

	}
	@Ignore
	@Test
	public void testFindSeat() {
		final TestCenter testCenter = getTestCenter();
		final FindSeatRequest request = new TCFindSeatRequestImpl();

		ETSCustomer customer = new ETSCustomerImpl();
		customer.setId(1L);
		request.setCustomer(customer);

		((TCFindSeatRequest)request).setTestCenterId(getTestCenter().getId());

		request.setRequesterRoleTypeCode("TCA");

		request.setDeliveryModeCode("PBT");
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTime(new Date());
		request.setSearchFromDate(fromDate);
		Calendar toDate = Calendar.getInstance();
		toDate.add(Calendar.DATE, 1);
		toDate.setTime(new Date());
		request.setSearchToDate(toDate);
		request.setTestVariation(getTestVariation(5l, "PBT", "EN"));


		final RetakeRulesRequestVo retakeRulesRequestVo = new RetakeRulesRequestVo();
		retakeRulesRequestVo.setCustomerId(1L);
		retakeRulesRequestVo.setDeliveryTypeCode("PBT");
		retakeRulesRequestVo.setTestId(5l);
		retakeRulesRequestVo.setRuleStartDate(new Timestamp(request.getSearchToDate().getTimeInMillis()));
		retakeRulesRequestVo.setRuleEndDate(new Timestamp(request.getSearchToDate().getTimeInMillis()));

		final List<RulesResponseVo> rulesResponseVO = new ArrayList<RulesResponseVo>();


		mockingContext.checking(new Expectations() {
			{
				 oneOf(mockTestCenterService).readTestCenterById(((TCFindSeatRequest)request).getTestCenterIds().get(0));
				 will(returnValue(testCenter));
				 oneOf(mockTestCenterService).readTestCenterById(((TCFindSeatRequest)request).getTestCenterIds().get(0));
				 will(returnValue(testCenter));			
				 oneOf(mockReTakeTestRulesService).applyRules(retakeRulesRequestVo);
				 will(returnValue(rulesResponseVO));

		    } });


		FindSeatResponse response = schedulingServiceImpl.findSeat(request);



		if (!(fromDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fromDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			assertTrue(response.getSeats().size() > 1);
        }

		mockingContext.assertIsSatisfied();

	}

	private TestCenter getTestCenter(){

		TestCenter testCenter = new TestCenterImpl();
		testCenter.setId(1L);

		Set<TestCenterProgram> testCenterPrograms = new HashSet<TestCenterProgram>();

		TestCenterProgram testCenterProgram = new TestCenterProgramImpl();

		ProgramType programType = new ProgramTypeImpl();
		programType.setCode("HSE");
		programType.setDescription("computer based test");

		testCenterProgram.setProgram(programType);

		AgencyProgram agencyProgram = new AgencyProgramImpl();
		SchedulingType schedulingType = new SchedulingTypeImpl();
		schedulingType.setCode("CANDM");

		agencyProgram.setSchedulingType(schedulingType);

		testCenterProgram.setAgencyProgram(agencyProgram);


		testCenterPrograms.add(testCenterProgram);

		testCenter.setTestCenterPrograms(testCenterPrograms);


		return testCenter;
	}
	@Ignore
	@Test
	public void testHoldSeat() {
		HoldSeatRequest request = new HoldSeatRequestImpl();

		ETSCustomer customer = new ETSCustomerImpl();
		customer.setId(1L);
		request.setCustomer(customer);

		request.setRequesterRoleTypeCode("TCA");


		request.setSeat(seat);
		HoldSeatResponse response = schedulingServiceImpl.holdSeat(request);
		assertTrue(response.isSuccessful());
		assertNotNull(response.getHeldSeat());
		assertNotNull(response.getHeldSeat().getHoldCode());
	}



	@Test
	public void testReleaseSeat() {
		ReleaseSeatRequest request = new ReleaseSeatRequestImpl();
		HeldSeat heldSeat = new HeldSeatImpl();
		heldSeat.setTestVariation(getTestVariation(5l, "PBT", "EN"));
		request.setHeldSeat(heldSeat);
		try{
			ReleaseSeatResponse response = schedulingServiceImpl.releaseSeat(request);
			assertTrue(response.isSuccessful());
		}catch(SchedulingRequestValidationException se){

			if(se.hasErrors()){
				for(ObjectError error:se.getAllErrors()){
					logger.debug("error code: "+error.getCode());
				}
			}
		}
	}

	@Test
	public void testReserveSeat() {
		ReserveSeatRequest request = new ReserveSeatRequestImpl();
		HeldSeat heldSeat = new HeldSeatImpl();
		heldSeat.setTestVariation(getTestVariation(5l, "PBT", "EN"));
		request.setHeldSeat(heldSeat);
		try{
			ReserveSeatResponse response = schedulingServiceImpl.reserveSeat(request);
			assertTrue(response.isSuccessful());
		}catch(SchedulingRequestValidationException se){

			if(se.hasErrors()){
				for(ObjectError error:se.getAllErrors()){
					logger.debug("error code: "+error.getCode());
				}
			}
		}

	}

	@Ignore
	public void testReserveWithoutHoldSeat() {
		ReserveSeatWithoutHoldRequest request = new ReserveSeatWithoutHoldRequestImpl();
		request.setSeat(seat);
		ReserveSeatResponse response = schedulingServiceImpl.reserveSeatWithoutHold(request);
		assertTrue(response.isSuccessful());
	}

}
