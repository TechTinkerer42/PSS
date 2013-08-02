/*
 * SSASchedulingServiceTest.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of
 * Educational Testing Service. ("Confidential Information").
 *
 */
package org.ets.ereg.scheduling.spi.ssa.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CountryImpl;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.CustomerAddressImpl;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.domain.StateImpl;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.domain.common.GenderImpl;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.CancelSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ExtendHoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.ets.ereg.scheduling.request.CancelSeatRequestImpl;
import org.ets.ereg.scheduling.request.ExtendHoldSeatRequestImpl;
import org.ets.ereg.scheduling.request.HoldSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReleaseSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReserveSeatRequestImpl;
import org.ets.ereg.scheduling.request.TCFindSeatRequestImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * SSASchedulingServiceTest
 *
 * @version	1.0 Mar 1, 2011
 * @author 	Venkat Shanmugam
 *
 * @history
 * Mar 1, 2011 VS Initial Creation
 *
 **/
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ets-applicationContext-test.xml"})
public class SSASchedulingServiceTest{

	@Resource(name="ssaSchedulingService")
	private SchedulingService schedulingService;

	@Before
	public void initialize() {
	}

	@Test
	public void testFindSeat(){
		TCFindSeatRequest findSeatRequest = new TCFindSeatRequestImpl();

		findSeatRequest.setTransactionID(""+System.currentTimeMillis());
		findSeatRequest.setDuration((short)150);

		Long testId = 902L;
		findSeatRequest.setTestCenterId(1L);
		findSeatRequest.setTestVariation(getTestVariation(testId));

		//findSeatRequest.setTestSiteId("STN13304I");
		findSeatRequest.setRequesterRoleTypeCode("candidate");
		Calendar caln = Calendar.getInstance();
		caln.set(Calendar.DAY_OF_MONTH, 01);
		findSeatRequest.setSearchFromDate(caln);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 90);
		findSeatRequest.setSearchToDate(cal);

		FindSeatResponse findSeatResponse = schedulingService.findSeat(findSeatRequest);

		Assert.assertNotNull(findSeatResponse.getSeats());
		Assert.assertTrue(findSeatResponse.getSeats().size() > 0);
	}

	@Ignore
	public void testHoldSeat(){

		FindSeatRequest findSeatRequest = new TCFindSeatRequestImpl();

		findSeatRequest.setTransactionID(""+System.currentTimeMillis());
		findSeatRequest.setDuration((short)150);

		Long testId = 40l;
		//testId.setProgramCode("GAT");
		//testId.setTestCode("040");

		findSeatRequest.setTestVariation(getTestVariation(testId));
		//findSeatRequest.setTestSiteId("STN13304I");
		findSeatRequest.setRequesterRoleTypeCode("candidate");
		findSeatRequest.setSearchFromDate(Calendar.getInstance());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 90);
		findSeatRequest.setSearchToDate(cal);

		FindSeatResponse findSeatResponse = schedulingService.findSeat(findSeatRequest);


		Assert.assertNotNull(findSeatResponse);
		Assert.assertTrue(findSeatResponse.getTestDatesCount() > 0);

		HoldSeatRequest holdSeatRequest = new HoldSeatRequestImpl();
		holdSeatRequest.setTransactionID(findSeatRequest.getTransactionID());
		holdSeatRequest.setSeat(findSeatResponse.getSeats().get(0));
		holdSeatRequest.setRequesterRoleTypeCode("candidate");


		HoldSeatResponse holdSeatResponse = schedulingService.holdSeat(holdSeatRequest);
		Assert.assertTrue(holdSeatResponse.isSuccessful());

	}


	@Ignore
	public void testExtendHoldSeat(){

		TCFindSeatRequestImpl findSeatRequest = new TCFindSeatRequestImpl();

		findSeatRequest.setTransactionID(""+System.currentTimeMillis());
		findSeatRequest.setDuration((short)150);

		/*TestId testId = new TestId();
		testId.setProgramCode("HSE");
		testId.setTestCode("900");*/
		Long testId = 900l;
		findSeatRequest.setTestVariation(getTestVariation(testId));

		findSeatRequest.setTestCenterId(54L);
		findSeatRequest.setRequesterRoleTypeCode("candidate");
		findSeatRequest.setSearchFromDate(Calendar.getInstance());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 90);
		findSeatRequest.setSearchToDate(cal);

		FindSeatResponse findSeatResponse = schedulingService.findSeat(findSeatRequest);


		Assert.assertNotNull(findSeatResponse);
		Assert.assertTrue(findSeatResponse.getTestDatesCount() > 0);

		HoldSeatRequest holdSeatRequest = new HoldSeatRequestImpl();


		holdSeatRequest.setTransactionID(findSeatRequest.getTransactionID());
		holdSeatRequest.setSeat(findSeatResponse.getSeats().get(0));
		holdSeatRequest.setRequesterRoleTypeCode("candidate");


		HoldSeatResponse holdSeatResponse = schedulingService.holdSeat(holdSeatRequest);
		Assert.assertTrue(holdSeatResponse.isSuccessful());


		ExtendHoldSeatRequest extendHoldSeatRequest = new ExtendHoldSeatRequestImpl();

		extendHoldSeatRequest.setTransactionID(findSeatRequest.getTransactionID());
		extendHoldSeatRequest.setRequesterRoleTypeCode("candidate");

		extendHoldSeatRequest.setTestId(holdSeatResponse.getHeldSeat().getTestVariation().getTest().getTestId());
		extendHoldSeatRequest.setSiteCode(holdSeatResponse.getHeldSeat().getSiteCode());
		extendHoldSeatRequest.setHoldDuration(holdSeatResponse.getHeldSeat().getHoldDuration());

		extendHoldSeatRequest.setHoldCode(holdSeatResponse.getHeldSeat().getHoldCode());
		extendHoldSeatRequest.setHoldSource(holdSeatResponse.getHeldSeat().getHoldSource());

		ExtendHoldSeatResponse extendHoldSeatResponse = schedulingService.extendHold(extendHoldSeatRequest);
		Assert.assertTrue(extendHoldSeatResponse.isSuccessful());

	}



	@Ignore
	public void testReleaseSeat(){

		FindSeatRequest findSeatRequest = new TCFindSeatRequestImpl();

		findSeatRequest.setTransactionID(""+System.currentTimeMillis());
		findSeatRequest.setDuration((short)150);

		/*TestId testId = new TestId();
		testId.setProgramCode("HSE");
		testId.setTestCode("900");*/

		Long testId = 900l;
		findSeatRequest.setTestVariation(getTestVariation(testId));

		//findSeatRequest.setTestSiteId("STN13304I");
		findSeatRequest.setRequesterRoleTypeCode("candidate");
		findSeatRequest.setSearchFromDate(Calendar.getInstance());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 90);
		findSeatRequest.setSearchToDate(cal);

		FindSeatResponse findSeatResponse = schedulingService.findSeat(findSeatRequest);


		Assert.assertNotNull(findSeatResponse);
		Assert.assertTrue(findSeatResponse.getTestDatesCount() > 0);

		HoldSeatRequest holdSeatRequest = new HoldSeatRequestImpl();


		holdSeatRequest.setTransactionID(findSeatRequest.getTransactionID());
		holdSeatRequest.setSeat(findSeatResponse.getSeats().get(0));
		holdSeatRequest.setRequesterRoleTypeCode("candidate");


		HoldSeatResponse holdSeatResponse = schedulingService.holdSeat(holdSeatRequest);
		Assert.assertTrue(holdSeatResponse.isSuccessful());

		ReleaseSeatRequest releaseSeatRequest = new ReleaseSeatRequestImpl();

		releaseSeatRequest.setTransactionID(holdSeatRequest.getTransactionID());

		releaseSeatRequest.setHeldSeat(holdSeatResponse.getHeldSeat());


		ReleaseSeatResponse releaseSeatResponse = schedulingService.releaseSeat(releaseSeatRequest);
		Assert.assertTrue(releaseSeatResponse.isSuccessful());


	}

	@Ignore
	public void testReserveSeat(){


		FindSeatRequest findSeatRequest = new TCFindSeatRequestImpl();

		findSeatRequest.setTransactionID(""+System.currentTimeMillis());
		findSeatRequest.setDuration((short)150);

		/*TestId testId = new TestId();
		testId.setProgramCode("HST");
		testId.setTestCode("2101");*/
		Long testId = 2101l;
		findSeatRequest.setTestVariation(getTestVariation(testId));

		//findSeatRequest.setTestSiteId("STN13304I");
		findSeatRequest.setRequesterRoleTypeCode("candidate");
		findSeatRequest.setSearchFromDate(Calendar.getInstance());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 90);
		findSeatRequest.setSearchToDate(cal);

		FindSeatResponse findSeatResponse = schedulingService.findSeat(findSeatRequest);


		Assert.assertNotNull(findSeatResponse);
		Assert.assertTrue(findSeatResponse.getTestDatesCount() > 0);

		HoldSeatRequest holdSeatRequest = new HoldSeatRequestImpl();


		holdSeatRequest.setTransactionID(findSeatRequest.getTransactionID());
		holdSeatRequest.setSeat(findSeatResponse.getSeats().get(0));
		holdSeatRequest.setRequesterRoleTypeCode("candidate");


		HoldSeatResponse holdSeatResponse = schedulingService.holdSeat(holdSeatRequest);
		Assert.assertTrue(holdSeatResponse.isSuccessful());

		HeldSeat heldSeat = holdSeatResponse.getHeldSeat();
		heldSeat.setEtsAppointmentId(""+getClientAppointmentId());//"1234598715222");

		ReserveSeatRequest reserveSeatRequest = new ReserveSeatRequestImpl();


		reserveSeatRequest.setRequestSystemId(findSeatRequest.getRequestSystemId());
		reserveSeatRequest.setHeldSeat(heldSeat);

		reserveSeatRequest.setRequesterRoleTypeCode(findSeatRequest.getRequesterRoleTypeCode());

		reserveSeatRequest.setCustomer(getETSCustomer());
		ReserveSeatResponse reserveSeatResponse = schedulingService.reserveSeat(reserveSeatRequest);
		Assert.assertTrue(reserveSeatResponse.isSuccessful());
	}


	@Ignore
	public void testCancelSeat(){


		FindSeatRequest findSeatRequest = new TCFindSeatRequestImpl();

		findSeatRequest.setTransactionID(""+System.currentTimeMillis());
		findSeatRequest.setDuration((short)150);

		/*TestId testId = new TestId();
		testId.setProgramCode("GAT");
		testId.setTestCode("040");*/
		Long testId = 40l;
		findSeatRequest.setTestVariation(getTestVariation(testId));

		//findSeatRequest.setTestSiteId("STN13304I");
		findSeatRequest.setRequesterRoleTypeCode("candidate");
		findSeatRequest.setSearchFromDate(Calendar.getInstance());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 90);
		findSeatRequest.setSearchToDate(cal);

		FindSeatResponse findSeatResponse = schedulingService.findSeat(findSeatRequest);


		Assert.assertNotNull(findSeatResponse);
		Assert.assertTrue(findSeatResponse.getTestDatesCount() > 0);

		HoldSeatRequest holdSeatRequest = new HoldSeatRequestImpl();


		holdSeatRequest.setTransactionID(findSeatRequest.getTransactionID());
		holdSeatRequest.setSeat(findSeatResponse.getSeats().get(0));
		holdSeatRequest.setRequesterRoleTypeCode("candidate");


		HoldSeatResponse holdSeatResponse = schedulingService.holdSeat(holdSeatRequest);
		Assert.assertTrue(holdSeatResponse.isSuccessful());

		HeldSeat heldSeat = holdSeatResponse.getHeldSeat();
		heldSeat.setEtsAppointmentId("1234598715255");

		ReserveSeatRequest reserveSeatRequest = new ReserveSeatRequestImpl();


		reserveSeatRequest.setRequestSystemId(findSeatRequest.getRequestSystemId());
		reserveSeatRequest.setHeldSeat(heldSeat);

		reserveSeatRequest.setRequesterRoleTypeCode(findSeatRequest.getRequesterRoleTypeCode());

		reserveSeatRequest.setCustomer(getETSCustomer());
		ReserveSeatResponse reserveSeatResponse = schedulingService.reserveSeat(reserveSeatRequest);
		Assert.assertTrue(reserveSeatResponse.isSuccessful());

		CancelSeatRequest cancelSeatRequest = new CancelSeatRequestImpl();

		cancelSeatRequest.setRequestSystemId(reserveSeatRequest.getRequestSystemId());
		cancelSeatRequest.setReservedSeat(reserveSeatResponse.getReservedSeat());
		cancelSeatRequest.setRequesterRoleTypeCode(reserveSeatRequest.getRequesterRoleTypeCode());
		cancelSeatRequest.setCustomer(getETSCustomer());
		CancelSeatResponse cancelSeatResponse = schedulingService.cancelSeat(cancelSeatRequest);

		Assert.assertTrue(cancelSeatResponse.isSuccessful());

	}

	private ETSCustomer getETSCustomer(){

		ETSCustomer etsCustomer = new ETSCustomerImpl();
		etsCustomer.setId(1L);
		etsCustomer.setFirstName("Gopal");
		etsCustomer.setMiddleInitial("K");
		etsCustomer.setLastName("Golla");

		Gender g = new GenderImpl();
		g.setCode("M");

		etsCustomer.setGender(g);
		Calendar calendar = Calendar.getInstance();
		calendar.set(1980, Calendar.MARCH, 20);
		etsCustomer.setDateOfBirth(calendar.getTime());

		CustomerAddress custAdd = new CustomerAddressImpl();
		custAdd.setAddressName("Primary");
		custAdd.setId(12345678L);
		custAdd.setCustomer(etsCustomer);

		List<CustomerAddress> custAdds = new ArrayList<CustomerAddress>();
		custAdds.add(custAdd);

		etsCustomer.setCustomerAddresses(custAdds);

		Address  address = new AddressImpl();
		address.setId(1L);
		address.setAddressLine1("1726 woodbridge");
		address.setAddressLine2("1st floor");

		custAdd.setAddress(address);

		address.setCity("Avenel");
		address.setPostalCode("07001");

		Phone phone = new PhoneImpl();

		phone.setActive(true);
		phone.setDefault(true);
		phone.setPhoneNumber("1234567890");
		phone.setId(1L);

		address.setPhonePrimary(phone);
		address.setEmailAddress("ggolla@ets.org");

		State state = new StateImpl();
		state.setAbbreviation("NJ");
		state.setName("New Jersey");

		Country country = new CountryImpl();
		country.setAbbreviation("USA");
		country.setName("United States of America");

		state.setCountry(country);

		address.setState(state);

		return etsCustomer;
	}

	private int getClientAppointmentId(){
		java.util.Random r = new Random();
		return r.nextInt(Integer.MAX_VALUE)+1;
	}

	private TestVariation getTestVariation(Long TestId){
		TestVariation testVariation = new TestVariationImpl();
		TestVarianceId id = new TestVarianceId();
		id.setDeliveryModeCode("PBT");
		id.setLanguageCode("EN");
		id.setTestId(TestId);
		testVariation.setId(id);

		return testVariation;

	}

}
