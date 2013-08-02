package org.ets.ereg.profile.accommodation.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.domain.accommodation.CustomerAccommodationTestImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.accommodation.id.CustomerAccommodationTestId;
import org.ets.ereg.profile.accommodation.dao.CustomerAccommodationTestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestCustomerAccommodationDaoImpl {

	private static Logger logger = LoggerFactory.getLogger(TestCustomerAccommodationDaoImpl.class);

	@Resource(name="customerAccommodationTestDao")
	private CustomerAccommodationTestDao customerAccommodationTestDao;

	@Test
	public void testSaveCustomerAccommodation(){

		CustomerAccommodationTest customerAccommodationTest =  customerAccommodationTestDao.create();
		CustomerAccommodationTestId customerAccommodationTestId =  customerAccommodationTest
				                                                     .getCustomerAccommodationTestId();

		customerAccommodationTestId.setCustomerId(1L);
		customerAccommodationTestId.setProgramCode("HSE");
		customerAccommodationTestId.setTestId(4l);
		customerAccommodationTestId.setAccommodationTypeCode("6");
		customerAccommodationTestId.setDeliveryModeCode("PBT");
		customerAccommodationTest.setExpiryDate(DateHandler.convertToDate(2014, 1, 1));
		customerAccommodationTest.setApprovedDate(DateHandler.getCurrentDate());
		customerAccommodationTestDao.save(customerAccommodationTest);
	}

	@Test
	public void testGetAllAccommodations(){
		List<CustomerAccommodationTest> customerAccommodations = customerAccommodationTestDao
				                                                         .getAllAccommodations(1L);
		Assert.notEmpty(customerAccommodations);
		logger.debug("customer accommodations ",customerAccommodations.size());
	}

	@Test
	public void testGetActiveAccommodations(){
		List<CustomerAccommodationTest> customerAccommodations = customerAccommodationTestDao
													.getActiveAccommodations(1L,DateHandler.getCurrentDate());
		Assert.notEmpty(customerAccommodations);
		logger.debug("all active accommodations of customer are {}",customerAccommodations.size());
		customerAccommodations = customerAccommodationTestDao.getActiveAccommodations(
				                      1L,DateHandler.convertToDate(2014,Calendar.DECEMBER,31));
		Assert.isTrue(customerAccommodations.size()==0);
	}

	@Test
	public void testGetActiveAccmodationsForTest(){
		List<CustomerAccommodationTest> customerAccommodations = customerAccommodationTestDao
				               .getActiveAccommodationsForTest(1L,1l,"HSE",DateHandler.getCurrentDate());
		Assert.notEmpty(customerAccommodations);
		logger.debug("all active accommodations for test are {} ",customerAccommodations.size());
	}

	@Test
	public void testGetTestsWithoutAccommodations(){
		List<org.ets.ereg.domain.interfaces.model.test.Test> tests = customerAccommodationTestDao.getTestsWithoutAccommodations(1L, "HSE", "PBT");
		Assert.notEmpty(tests);
		logger.debug("Tests with no accommodations are: {} ",tests.size());
	}

	@Test
	public void testGetAllNotApprovedAccommodations(){
		List<AccommodationType> accommodationTypes = customerAccommodationTestDao
				                            .getAllNotApprovedAccommodations(1L, "HSE", "PBT");

		for(AccommodationType accommodationType:accommodationTypes){

			logger.debug("Not approved accommodationType: {} "+accommodationType.getDescription());
			List<AccommodationTypeValue> accommodationTypeVals = accommodationType.getAccommodationTypeValues();
			for(AccommodationTypeValue accommodationTypeVal:accommodationTypeVals){
				logger.debug("accommodationTypeValue: {}"+
								accommodationTypeVal.getSchedulingServiceAccommodationValue());
				logger.debug("accommodationTypeValue: {} "+accommodationTypeVal.getValue());
			}

		}
		Assert.notEmpty(accommodationTypes);
	}

	@Test
	public void testGetCustomerAccommodations() {

		List<CustomerAccommodationTestImpl> custImpls = customerAccommodationTestDao.getAllAccommodations(1L, "HSE",
												null, null, AccommodationStatus.ACTIVE,null);

		custImpls = customerAccommodationTestDao.getAllAccommodations(1L, "HSE",
				null, null, AccommodationStatus.EXPIRED,null);

		custImpls = customerAccommodationTestDao.getAllAccommodations(1L, "HSE",
				3l, null, null,null);

		custImpls = customerAccommodationTestDao.getAllAccommodations(1L, "HSE",
				3l, "CBT", null,null);

		custImpls = customerAccommodationTestDao.getAllAccommodations(1L, "HSE",
				3l, "CBT", AccommodationStatus.ACTIVE,DateHandler.getCurrentDate());

		custImpls = customerAccommodationTestDao.getAllAccommodations(1L, "HSE",
				null, null, null,null);


		for(CustomerAccommodationTestImpl custImpl:custImpls){
			logger.debug("Test Name: {}",custImpl.getTest().getTestName());
			logger.debug("Delivery Method: {}",custImpl.getProgramAccommodationDeliveryMode().getDeliveryModeType().getDescription());
			logger.debug("Accommodation Name: {}",custImpl.getProgramAccommodationDeliveryMode().getAccommodationType().getDescription());
			logger.debug("Expiry Date: {}",custImpl.getExpiryDate());
		}
	}

	@Test
	public void testGetCustomerProgramAccommodationTest(){
		CustomerAccommodationTest customerAccommodationTest = customerAccommodationTestDao.getCustomerProgramAccommodationTest(
						1L, "HSE", 5l, "6", "PBT");
		Assert.notNull(customerAccommodationTest);

	}

}
