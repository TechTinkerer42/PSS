package org.ets.ereg.scheduling.validator;

import java.util.List;

import junit.framework.Assert;

import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.MapFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.ets.ereg.scheduling.request.CancelSeatRequestImpl;
import org.ets.ereg.scheduling.request.HoldSeatRequestImpl;
import org.ets.ereg.scheduling.request.MapFindSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReleaseSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReserveSeatRequestImpl;
import org.ets.ereg.scheduling.request.TCFindSeatRequestImpl;
import org.ets.ereg.scheduling.response.HeldSeatImpl;
import org.ets.ereg.scheduling.response.ReservedSeatImpl;
import org.ets.ereg.scheduling.response.SeatImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@RunWith(JUnit4.class)
public class TestSchedulingRequestsValidators {

	private static Validator findSeatRequestValidator;
	private static Validator holdSeatRequestValidator;
	private static Validator reservSeatRequestValidator;
	private static Validator releaseSeatRequestValidator;
	private static Validator cancelSeatRequestValidator;

	private static Validator seatValidator;
	private static Validator heldSeatValidator;
	private static Validator reservedSeatValidator;

	private static Logger logger = LoggerFactory.getLogger(TestSchedulingRequestsValidators.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		findSeatRequestValidator = new FindSeatRequestValidator();
		seatValidator = new SeatValidator();
		holdSeatRequestValidator = new HoldSeatRequestValidator(seatValidator);

		heldSeatValidator = new HeldSeatValidator();
		reservSeatRequestValidator = new ReservSeatRequestValidator(heldSeatValidator);
		releaseSeatRequestValidator = new ReleaseSeatRequestValidator(heldSeatValidator);

		reservedSeatValidator = new ReservedSeatValidator();
		cancelSeatRequestValidator = new CancelSeatRequestValidator(reservedSeatValidator);
	}

//	@Test
//	public void testIsValidFindSeatRequest(){
//
//
//		FindSeatRequest findSeatRequest = new TCFindSeatRequestImpl();
//
//		TestId testId = new TestId();
//		testId.setProgramCode(ProgramType.HSET_PROGRAM_CODE);
//		testId.setTestCode("MATH");
//
//		findSeatRequest.setTestId(testId);
//		Assert.assertTrue(findSeatRequestValidator.supports(FindSeatRequest.class));
//
//		BindException errors = new BindException(findSeatRequest, "findSeatRequest");
//
//
//		ValidationUtils.invokeValidator(findSeatRequestValidator, findSeatRequest, errors);
//
//		Assert.assertTrue(errors.hasErrors());
//		List<ObjectError> objErrors = errors.getAllErrors();
//
//		for(ObjectError objError:objErrors){
//			logger.debug("error code: {} ",objError.getCode());
//		}
//
//	}


	@Test
	public void testIsValidMapFindSeatRequest(){


		MapFindSeatRequest mapFindSeatRequest = new MapFindSeatRequestImpl();

		Assert.assertTrue(findSeatRequestValidator.supports(MapFindSeatRequest.class));

		BindException errors = new BindException(mapFindSeatRequest, "mapFindSeatRequest");

		ValidationUtils.invokeValidator(findSeatRequestValidator, mapFindSeatRequest, errors);

		Assert.assertTrue(errors.hasErrors());
		List<ObjectError> objErrors = errors.getAllErrors();


		for(ObjectError objError:objErrors){
			logger.debug("error code: {} ",objError.getCode());
		}
	}

	@Test
	public void testIsValidTCFindSeatRequest(){


		TCFindSeatRequest tcFindSeatRequest = new TCFindSeatRequestImpl();

		BindException errors = new BindException(tcFindSeatRequest, "tcFindSeatRequest");

		Assert.assertTrue(findSeatRequestValidator.supports(TCFindSeatRequest.class));

		ValidationUtils.invokeValidator(findSeatRequestValidator, tcFindSeatRequest, errors);

		Assert.assertTrue(errors.hasErrors());
		List<ObjectError> objErrors = errors.getAllErrors();

		for(ObjectError objError:objErrors){
			logger.debug("error code: {} ",objError.getCode());
		}

	}

	@Test
	public void testIsValidHoldSeatRequest(){

		HoldSeatRequest holdSeatRequest = new HoldSeatRequestImpl();
		Seat seat = new SeatImpl(0);
		holdSeatRequest.setSeat(seat);
		seat.setTestVariation(getTestVariation());
		Assert.assertTrue(holdSeatRequestValidator.supports(HoldSeatRequest.class));

		BindException errors1 = new BindException(holdSeatRequest, "holdSeatRequest");


		ValidationUtils.invokeValidator(holdSeatRequestValidator, holdSeatRequest, errors1);

		Assert.assertTrue(errors1.hasErrors());
		List<ObjectError> objErrors1 = errors1.getAllErrors();


		for(ObjectError objError:objErrors1){
			logger.debug("error code: {} ",objError.getCode());
		}
	}


	@Test
	public void testIsValidReleaseSeatRequest(){

		ReleaseSeatRequest releaseSeatRequest = new ReleaseSeatRequestImpl();
		Seat seat = new SeatImpl(0);
		seat.setDeliveryModeCode("CBT");
		seat.setLabCode("1");
		seat.setSiteCode("2");
		seat.setSeatCode("3");
		seat.setTestVariation(getTestVariation());


		HeldSeat heldSeat = new HeldSeatImpl(seat);

		releaseSeatRequest.setHeldSeat(heldSeat);


		Assert.assertTrue(releaseSeatRequestValidator.supports(ReleaseSeatRequest.class));

		BindException errors = new BindException(releaseSeatRequest, "releaseSeatRequest");


		ValidationUtils.invokeValidator(releaseSeatRequestValidator, releaseSeatRequest, errors);

		Assert.assertTrue(errors.hasErrors());
		List<ObjectError> objErrors = errors.getAllErrors();

		for(ObjectError objError:objErrors){
			logger.debug("error code: {} ",objError.getCode());
		}
	}


	@Test
	public void testIsValidReservSeatRequest(){

		ReserveSeatRequest reserveSeatRequest = new ReserveSeatRequestImpl();
		HeldSeat heldSeat = new HeldSeatImpl();
		heldSeat.setTestVariation(getTestVariation());
		reserveSeatRequest.setHeldSeat(heldSeat);


		Assert.assertTrue(reservSeatRequestValidator.supports(ReserveSeatRequest.class));

		BindException errors = new BindException(reserveSeatRequest, "reserveSeatRequest");


		ValidationUtils.invokeValidator(reservSeatRequestValidator, reserveSeatRequest, errors);

		Assert.assertTrue(errors.hasErrors());
		List<ObjectError> objErrors = errors.getAllErrors();

		for(ObjectError objError:objErrors){
			logger.debug("error code: {} ",objError.getCode());
		}
	}

	@Test
	public void testIsValidCancelSeatRequest(){

		CancelSeatRequest cancelSeatRequest = new CancelSeatRequestImpl();
		ReservedSeat reservedSeat = new ReservedSeatImpl();
		reservedSeat.setTestVariation(getTestVariation());
		cancelSeatRequest.setReservedSeat(reservedSeat);


		Assert.assertTrue(cancelSeatRequestValidator.supports(CancelSeatRequest.class));

		BindException errors = new BindException(cancelSeatRequest, "cancelSeatRequest");


		ValidationUtils.invokeValidator(cancelSeatRequestValidator, cancelSeatRequest, errors);

		Assert.assertTrue(errors.hasErrors());
		List<ObjectError> objErrors = errors.getAllErrors();

		for(ObjectError objError:objErrors){
			logger.debug("error code: {} ",objError.getCode());
		}
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
}
