package org.ets.ereg.profile.accommodation.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ets.ereg.common.business.dao.AccommodationTypeValueDao;
import org.ets.ereg.common.business.dao.ProgramAccommodationDeliveryModeDao;
import org.ets.ereg.common.business.dao.TestDao;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.domain.accommodation.AccommodationTypeImpl;
import org.ets.ereg.domain.accommodation.AccommodationTypeValueImpl;
import org.ets.ereg.domain.accommodation.CustomerAccommodationTestImpl;
import org.ets.ereg.domain.accommodation.ProgramAccommodationDeliveryModeImpl;
import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.accommodation.id.CustomerAccommodationTestId;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.test.TestImpl;
import org.ets.ereg.profile.accommodation.dao.CustomerAccommodationTestDao;
import org.ets.ereg.profile.accommodation.vo.AccommodationVO;
import org.ets.ereg.profile.accommodation.vo.CustomerAccommodationVO;
import org.ets.ereg.profile.accommodation.vo.CustomerDeliveryMethodAccommodationsVO;
import org.ets.ereg.profile.accommodation.vo.CustomerProgramAccommodationsVO;
import org.ets.ereg.profile.accommodation.vo.CustomerTestAccommodationsVO;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@RunWith(JMock.class)
public class TestCustomerAccommodationServiceImpl {

	private static Logger logger = LoggerFactory.getLogger(TestCustomerAccommodationServiceImpl.class);

	private static Mockery mockingContext = new Mockery();

	private static CustomerAccommodationTestDao mockedCustomerAccommodationTestDao;
	private static AccommodationTypeValueDao mockedAccommodationTypeValueDao;
	private static TestDao mockedTestDao;
	private static ProgramAccommodationDeliveryModeDao mockedProgramAccommodationDeliveryModeDao;

	private static CustomerAccommodationServiceImpl customerAccommodationServiceImpl = new CustomerAccommodationServiceImpl();

	private final Map<String,DeliveryModeType> deliveryModeCodesMap = createDeliveryModes();
	private final Map<String,AccommodationType> accommodationTypesMap = createAccommodationTypes();
	private final Map<String,ProgramAccommodationDeliveryMode> programAccommodationDeliveryModesMap = createProgramAccommodationDeliveryModes();
	private final Map<Long,org.ets.ereg.domain.interfaces.model.test.Test> programTestsMap = createTests();

	@BeforeClass
	public static void setup() {
		setupMockObjects();
		customerAccommodationServiceImpl.
				setCustomerAccommodationTestDao(mockedCustomerAccommodationTestDao);
		customerAccommodationServiceImpl.setAccommodationTypeValueDao(mockedAccommodationTypeValueDao);
		customerAccommodationServiceImpl.setTestDao(mockedTestDao);
		customerAccommodationServiceImpl.setProgramAccommodationDeliveryModeDao(mockedProgramAccommodationDeliveryModeDao);
	}

	private static void setupMockObjects() {
		mockedCustomerAccommodationTestDao = mockingContext.mock(CustomerAccommodationTestDao.class);
		mockedAccommodationTypeValueDao = mockingContext.mock(AccommodationTypeValueDao.class);
		mockedTestDao = mockingContext.mock(TestDao.class);
		mockedProgramAccommodationDeliveryModeDao = mockingContext.mock(ProgramAccommodationDeliveryModeDao.class);
	}

	@Test
	public void testGetAllAccommodations(){

		final List<CustomerAccommodationTest> customerAccommodationTest = new ArrayList<CustomerAccommodationTest>();
		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).getAllAccommodations(1L);
				 will(returnValue(customerAccommodationTest));
		    } });

		List<CustomerAccommodationTest> customerAccommodationsTest =  customerAccommodationServiceImpl.getAllAccommodations(1L);
		Assert.notNull(customerAccommodationsTest);
		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testGetActiveAccommodations(){

		final List<CustomerAccommodationTest> customerAccommodationTest = new ArrayList<CustomerAccommodationTest>();
		final Date expDate = DateHandler.getCurrentDate();
		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).getActiveAccommodations(1L,expDate);
				 will(returnValue(customerAccommodationTest));
		    } });

		List<CustomerAccommodationTest> customerAccommodationsTest = customerAccommodationServiceImpl
																	.getActiveAccommodations(1L,expDate);
		Assert.notNull(customerAccommodationsTest);
		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testGetActiveAccmodationsForTest(){
		final List<CustomerAccommodationTest> customerAccommodationTest = new ArrayList<CustomerAccommodationTest>();
		final Date expDate = DateHandler.getCurrentDate();
		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).getActiveAccommodationsForTest(1L, 4l,"HSE",expDate);
				 will(returnValue(customerAccommodationTest));
		    } });

		List<CustomerAccommodationTest> customerAccommodationsTest = customerAccommodationServiceImpl
																	.getActiveAccommodationsForTest(1L, 4l,"HSE",expDate);
		Assert.notNull(customerAccommodationsTest);
		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testGetTestsWithoutAccommodations(){
		final List<org.ets.ereg.domain.interfaces.model.test.Test> testsList = new ArrayList<org.ets.ereg.domain.interfaces.model.test.Test>();

		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).getTestsWithoutAccommodations(1L, "HSE","CBT");
				 will(returnValue(testsList));
		    } });

		List<org.ets.ereg.domain.interfaces.model.test.Test> testWithoutAccommodations = customerAccommodationServiceImpl
																	.getTestsWithoutAccommodations(1L, "HSE","CBT");
		Assert.notNull(testWithoutAccommodations);
		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testGetAllNotApprovedAccommodations(){
		final List<AccommodationType> accommodations = new ArrayList<AccommodationType>();

		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).getAllNotApprovedAccommodations(1L, "HSE","CBT");
				 will(returnValue(accommodations));
		    } });

		List<AccommodationType> notApprovedAccommodations = customerAccommodationServiceImpl
																	.getAllNotApprovedAccommodations(1L, "HSE","CBT");
		Assert.notNull(notApprovedAccommodations);
		mockingContext.assertIsSatisfied();
	}



	@Test
	public void testSaveCustomerAccommodation(){

		final CustomerAccommodationTest customerAccommodationTest = createCustomerAccommodation();

		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).save(customerAccommodationTest);
				 will(returnValue(null));
		    } });

		customerAccommodationServiceImpl.saveCustomerAccommodation(customerAccommodationTest);

		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testDeleteCustomerAccommodation(){
		final CustomerAccommodationTest customerAccommodationTest = createCustomerAccommodation();
		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).delete(customerAccommodationTest);
				 will(returnValue(null));
		    } });

		customerAccommodationServiceImpl.deleteCustomerAccommodation(customerAccommodationTest);

		mockingContext.assertIsSatisfied();

		final CustomerAccommodationTest customerAccommodationTest1 = new CustomerAccommodationTestImpl();
		final CustomerAccommodationTestId customerAccommodationTestId = customerAccommodationTest1.getCustomerAccommodationTestId();

		customerAccommodationTestId.setCustomerId(1L);
		customerAccommodationTestId.setProgramCode("HSE");
		customerAccommodationTestId.setTestId(4l);
		customerAccommodationTestId.setAccommodationTypeCode("95");

		final CustomerAccommodationTest customerAccommodationTest2 = createCustomerAccommodation();

		mockingContext.checking(new Expectations() {
		{
				 oneOf(mockedCustomerAccommodationTestDao).create();
				 will(returnValue(customerAccommodationTest1));

				 oneOf(mockedCustomerAccommodationTestDao).findByPrimaryKey(CustomerAccommodationTest.class, customerAccommodationTestId);
				 will(returnValue(customerAccommodationTest2));

				 oneOf(mockedCustomerAccommodationTestDao).delete(customerAccommodationTest2);
				 will(returnValue(null));
		 }});

		customerAccommodationServiceImpl.deleteCustomerAccommodation(1L, "HSE", 4l,"95","CBT");
		mockingContext.assertIsSatisfied();

	}


	@Test
	public void testHasApprovedActiveAccommodations(){

		CustomerAccommodationTest customerAccommodationTest = createCustomerAccommodation();
		final List<CustomerAccommodationTest> customerAccommodationTests = new ArrayList<CustomerAccommodationTest>();
		customerAccommodationTests.add(customerAccommodationTest);

		final Date expDate = DateHandler.getCurrentDate();

		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).getActiveAccommodations(1L,expDate);
				 will(returnValue(customerAccommodationTests));
		    } });

		boolean hasApprovedAccommmodations = customerAccommodationServiceImpl.hasApprovedActiveAccommodations(1L,expDate);
		Assert.isTrue(hasApprovedAccommmodations);
		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testGetAccommodations(){

		final List<CustomerAccommodationTest> customerAccommodationTests = createCustomerAccommodations();

		mockingContext.checking(new Expectations() {
			{
				 one(mockedCustomerAccommodationTestDao).getAllAccommodations(1L,"HSE",
						 		null,null,null,null);
				 will(returnValue(customerAccommodationTests));
		    } });

		CustomerProgramAccommodationsVO customerProgramAccommodationsVO = customerAccommodationServiceImpl
					.getAllAccommodations(1L,"HSE",
					 		null,null,null,null);

		List<CustomerTestAccommodationsVO> customerTestAccommodationsVO = customerProgramAccommodationsVO.getCustomerTestAccommodations();

		for(CustomerTestAccommodationsVO customerTestAccommodation:customerTestAccommodationsVO){

			logger.debug("Test Name: {}",customerTestAccommodation.getTestTitle());
			logger.debug("No Of Accommodations for Test: {}",customerTestAccommodation.getTestAccommodationCount());

			List<CustomerDeliveryMethodAccommodationsVO> customerDeliveryMethodAccommodationsVO= customerTestAccommodation.getCustomerDeliveryMethodAccommodations();

			for(CustomerDeliveryMethodAccommodationsVO customerDeliveryMethodAccommodation:customerDeliveryMethodAccommodationsVO){
				logger.debug("Delivery Method: {}",customerDeliveryMethodAccommodation.getDeliveryMethod());
				List<CustomerAccommodationVO> customerAccommodationVOs = customerDeliveryMethodAccommodation.getCustomerAccommodations();

				for(CustomerAccommodationVO customerAccommodation:customerAccommodationVOs){

					logger.debug("Accommodation: {}",customerAccommodation.getAccommodation().getDescription());
				}
			}
		}
		mockingContext.assertIsSatisfied();

	}


	@Test
	public void testGetAccommodation(){

		final CustomerAccommodationTest customerAccommodationTest = createCustomerAccommodations().get(0);

			mockingContext.checking(new Expectations() {
			{
				 oneOf(mockedCustomerAccommodationTestDao).getCustomerProgramAccommodationTest(1L, "HSE", 1l, "1", "PBT");
				 will(returnValue(customerAccommodationTest));
		    } });

		CustomerAccommodationTest customerAccommodationTest1 = customerAccommodationServiceImpl.getAccommodation(1L, "HSE", 1l, "1", "PBT");
		Assert.notNull(customerAccommodationTest1.getCustomerAccommodationTestId().getCustomerId());
		mockingContext.assertIsSatisfied();
	}

	@Test
	public void testGetCustomerAccommodation(){
		final CustomerAccommodationTest customerAccommodationTest = createCustomerAccommodations().get(0);
		mockingContext.checking(new Expectations() {
		{
			 oneOf(mockedCustomerAccommodationTestDao).getCustomerProgramAccommodationTest(1L, "HSE", 1l, "1", "PBT");
			 will(returnValue(customerAccommodationTest));
	    } });

		CustomerProgramAccommodationsVO customerProgramAccommodationsVO = customerAccommodationServiceImpl.getCustomerAccommodation(1L, "HSE", 1l, "1", "PBT");
		Assert.notNull(customerProgramAccommodationsVO.getCustomerTestAccommodations().get(0).getTestTitle());
		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testAddAccommodations(){

		final List<CustomerAccommodationVO> customerAccommodationVOs = createCustomerAccommodationsVO();
		final CustomerAccommodationTest customerAccommodationTest = new CustomerAccommodationTestImpl();

		for(final CustomerAccommodationVO customerAccommodationVO:customerAccommodationVOs){
			mockingContext.checking(new Expectations() {
				{
					final org.ets.ereg.domain.interfaces.model.test.Test test = programTestsMap.get(customerAccommodationVO.getTestId());
					oneOf(mockedTestDao).getTest(customerAccommodationVO.getTestId());
					 will(returnValue(test));

					 final ProgramAccommodationDeliveryMode programAccommodationDeliveryMode = programAccommodationDeliveryModesMap.get(customerAccommodationVO.getAccommodation().getAccommodationTypeCode());
					oneOf(mockedProgramAccommodationDeliveryModeDao).getProgramAccommodationDeliveryMode(customerAccommodationVO.getProgramCode(),
							customerAccommodationVO.getDeliveryMethodCode(), customerAccommodationVO.getAccommodation().getAccommodationTypeCode());
					 will(returnValue(programAccommodationDeliveryMode));

					oneOf(mockedCustomerAccommodationTestDao).create();
					 will(returnValue(customerAccommodationTest));

					if(customerAccommodationVO.getAccommodation().getValueId()!=null){
						final AccommodationTypeValue accommodationTypeValue = new AccommodationTypeValueImpl();
						accommodationTypeValue.setAccommodationTypeValueIdentifier(customerAccommodationVO.getAccommodation().getValueId());
						accommodationTypeValue.setValue(customerAccommodationVO.getAccommodation().getValueText());

						oneOf(mockedAccommodationTypeValueDao).findByPrimaryKey(customerAccommodationVO.getAccommodation().getValueId());
						will(returnValue(accommodationTypeValue));
					}

					oneOf(mockedCustomerAccommodationTestDao).save(customerAccommodationTest);
					 will(returnValue(null));

			  } });
		}
		customerAccommodationServiceImpl.addAccommodations(customerAccommodationVOs);
		mockingContext.assertIsSatisfied();
	}


	@Test
	public void testUpdateAccommodations(){

		final List<CustomerAccommodationVO> customerAccommodationVOs = createCustomerAccommodationsVO();
		final CustomerAccommodationTest customerAccommodationTest = new CustomerAccommodationTestImpl();

		for(final CustomerAccommodationVO customerAccommodationVO:customerAccommodationVOs){
			mockingContext.checking(new Expectations() {
				{
					final org.ets.ereg.domain.interfaces.model.test.Test test = programTestsMap.get(customerAccommodationVO.getTestId());
					oneOf(mockedTestDao).getTest(customerAccommodationVO.getTestId());
					 will(returnValue(test));

					 final ProgramAccommodationDeliveryMode programAccommodationDeliveryMode = programAccommodationDeliveryModesMap.get(customerAccommodationVO.getAccommodation().getAccommodationTypeCode());
					oneOf(mockedProgramAccommodationDeliveryModeDao).getProgramAccommodationDeliveryMode(customerAccommodationVO.getProgramCode(),
							customerAccommodationVO.getDeliveryMethodCode(), customerAccommodationVO.getAccommodation().getAccommodationTypeCode());
					 will(returnValue(programAccommodationDeliveryMode));

					oneOf(mockedCustomerAccommodationTestDao).create();
					 will(returnValue(customerAccommodationTest));

					if(customerAccommodationVO.getAccommodation().getValueId()!=null){
						final AccommodationTypeValue accommodationTypeValue = new AccommodationTypeValueImpl();
						accommodationTypeValue.setAccommodationTypeValueIdentifier(customerAccommodationVO.getAccommodation().getValueId());
						accommodationTypeValue.setValue(customerAccommodationVO.getAccommodation().getValueText());

						oneOf(mockedAccommodationTypeValueDao).findByPrimaryKey(customerAccommodationVO.getAccommodation().getValueId());
						will(returnValue(accommodationTypeValue));
					}

					oneOf(mockedCustomerAccommodationTestDao).save(customerAccommodationTest);
					 will(returnValue(null));

			  } });
		}
		customerAccommodationServiceImpl.updateAccommodations(customerAccommodationVOs);
		mockingContext.assertIsSatisfied();
	}


	private List<CustomerAccommodationVO> createCustomerAccommodationsVO(){

		CustomerAccommodationVO customerAccommodationVO = null;
		List<CustomerAccommodationVO> customerAccommodationVOs = new ArrayList<CustomerAccommodationVO>();

		AccommodationVO  accommodationVO =null;
		String[] accommodationTypeCodes = new String[]{"1","2","3"};
		String[] deliveryModeCodes = new String[]{"PBT","PBT","CBT"};
		Long[] testIds= new Long[]{1l,2l,2l};
		Long[] accommodationTypeVal= new Long[]{1L,2L,null,null};

		for(int i=0;i<accommodationTypeCodes.length;i++){
			customerAccommodationVO = new CustomerAccommodationVO();
			customerAccommodationVO.setCustomerId(1L);
			customerAccommodationVO.setProgramCode("HSE");
			customerAccommodationVO.setTestId(testIds[i]);

			if(i==0 || i==1){
				accommodationVO = new AccommodationVO();
				accommodationVO.setAccommodationTypeCode(accommodationTypeCodes[i]);
				accommodationVO.setValueId(accommodationTypeVal[i]);
			}

			if(i==2){
				accommodationVO.setOtherValueText("Other Accommodation");
			}
			customerAccommodationVO.setAccommodation(accommodationVO);
			customerAccommodationVO.setDeliveryMethodCode(deliveryModeCodes[i]);

			customerAccommodationVOs.add(customerAccommodationVO);
		}
		return customerAccommodationVOs;
	}

	private List<CustomerAccommodationTest> createCustomerAccommodations(){

		String[] accommodationTypeCodes = new String[]{"1","1","2","2","3","4","2","3"};
		String[] deliveryModeCodes = new String[]{"PBT","PBT","CBT","CBT","CBT","PBT","CBT","CBT"};
		Long[] testIds= new Long[]{1l,2l,2l,6l,4l,3l,3l,3l};
		Long[] accommodationTypeVal= new Long[]{1L,2L,null,null,null,null,null};

		CustomerAccommodationTest customerAccommodationTest=null;
		CustomerAccommodationTestId customerAccommodationTestId=null;
		List<CustomerAccommodationTest> customerAccommodationTestList = new ArrayList<CustomerAccommodationTest>();
		AccommodationTypeValue accommodationTypeValue =null;

		for(int i=0;i<accommodationTypeCodes.length;i++){
			customerAccommodationTest= new CustomerAccommodationTestImpl();
			customerAccommodationTestId = customerAccommodationTest.getCustomerAccommodationTestId();
			customerAccommodationTestId.setAccommodationTypeCode(accommodationTypeCodes[i]);
			customerAccommodationTestId.setCustomerId(1L);
			customerAccommodationTestId.setDeliveryModeCode(deliveryModeCodes[i]);
			customerAccommodationTestId.setTestId(testIds[i]);
			customerAccommodationTestId.setProgramCode("HSE");
			customerAccommodationTest.setApprovedDate(DateHandler.getCurrentDate());
			customerAccommodationTest.setExpiryDate(DateHandler.convertToDate(2020,Calendar.DECEMBER,31));
			customerAccommodationTest.setProgramAccommodationDeliveryMode(programAccommodationDeliveryModesMap.get(accommodationTypeCodes[i]));
			customerAccommodationTest.setTest(programTestsMap.get(testIds[i]));
			customerAccommodationTestList.add(customerAccommodationTest);

			if(i==0 || i==1){
				accommodationTypeValue = new AccommodationTypeValueImpl();
				accommodationTypeValue.setAccommodationType(accommodationTypesMap.get(accommodationTypeCodes[i]));
				accommodationTypeValue.setAccommodationTypeValueDisplaySequence(i+1);
				accommodationTypeValue.setAccommodationTypeValueIdentifier(accommodationTypeVal[i]);
				accommodationTypeValue.setValue("50%");
				customerAccommodationTest.setAccommodationTypeValue(accommodationTypeValue);
			}

			if(i==4){
				customerAccommodationTest.setOtherAccommodationTypeText("Other Accommodation");
			}
		}

		return customerAccommodationTestList;
	}

	private Map<String,ProgramAccommodationDeliveryMode> createProgramAccommodationDeliveryModes(){

		String[] deliveryModeCodes = new String[]{"PBT","CBT","CBT","PBT","PBT","PBT"};
		String[] accommodationTypeCodes = new String[]{"1","2","3","4","5","6",};
		ProgramAccommodationDeliveryMode programAccommodationDeliveryMode=null;
		String programCode="HSE";

		Map<String,ProgramAccommodationDeliveryMode> programAccommodationDeliveryModeMap= new HashMap<String,ProgramAccommodationDeliveryMode>();

		for(int i=0;i<deliveryModeCodes.length;i++){
			programAccommodationDeliveryMode = new ProgramAccommodationDeliveryModeImpl();
			programAccommodationDeliveryMode.getProgramAccommodationDeliveryModeId().setDeliveryModeCode(deliveryModeCodes[i]);
			programAccommodationDeliveryMode.getProgramAccommodationDeliveryModeId().setAccommodationTypeCode(accommodationTypeCodes[i]);
			programAccommodationDeliveryMode.getProgramAccommodationDeliveryModeId().setProgramCode(programCode);

			programAccommodationDeliveryMode.setDeliveryModeType(deliveryModeCodesMap.get(deliveryModeCodes[i]));
			programAccommodationDeliveryMode.setAccommodationType(accommodationTypesMap.get(accommodationTypeCodes[i]));
			programAccommodationDeliveryMode.setProgramCode(getProgramType());

			programAccommodationDeliveryModeMap.put(accommodationTypeCodes[i], programAccommodationDeliveryMode);
		}

		return programAccommodationDeliveryModeMap;
	}


	private Map<String,AccommodationType> createAccommodationTypes(){

		String[] accommodationTypeCodes = new String[]{"1","2","3","4","5","6"};
		String[] accommodationTypeDesc = new String[]{"Extended Time","Color Contrast","Large Font/Magnification",
																"Audio Version","Braille","Large Print"};

		Map<String,AccommodationType> accommodationTypeMap = new HashMap<String, AccommodationType>();

		AccommodationType accommodationType =null;
		for(int i=0;i<accommodationTypeCodes.length;i++){
			accommodationType = new AccommodationTypeImpl();
			accommodationType.setCode(accommodationTypeCodes[i]);
			accommodationType.setDescription(accommodationTypeDesc[i]);
			accommodationType.setDisplayable(true);
			accommodationType.setDisplaySequence(Long.valueOf(i+1));
			accommodationTypeMap.put(accommodationTypeCodes[i], accommodationType);
		}

		return accommodationTypeMap;
	}

    private ProgramType getProgramType(){

    	ProgramType programType = new ProgramTypeImpl();
    	programType.setCode("HSE");
    	programType.setDescription("High School Eqvilency Test");
    	programType.setDisplayable(true);
    	programType.setDisplaySequence(9L);

    	return programType;
	}

	private Map<String,DeliveryModeType> createDeliveryModes(){

		String[] deliveryModeCodes = new String[]{"PBT","CBT"};
		String[] deliveryModeDesc = new String[]{"Paper Based Test","Computer Based Test"};
		Map<String,DeliveryModeType> deliveryModesMap = new HashMap<String, DeliveryModeType>();

		DeliveryModeType deliveryModeType =null;
		for(int i=0;i<deliveryModeCodes.length;i++){
			deliveryModeType = new DeliveryModeTypeImpl();
			deliveryModeType.setCode(deliveryModeCodes[i]);
			deliveryModeType.setDescription(deliveryModeDesc[i]);
			deliveryModeType.setDisplayable(true);
			deliveryModeType.setDisplaySequence(Long.valueOf(i+1));
			deliveryModesMap.put(deliveryModeCodes[i], deliveryModeType);
		}

		return deliveryModesMap;
	}
	private Map<Long,org.ets.ereg.domain.interfaces.model.test.Test> createTests(){

	    Long[] testIds= new Long[]{1l,2l,6l,4l,3l,5l};
		String[] testNames =  new String[]{"ELA-Reading","ELA-Writing (MC Section)","ELA-Writing (CR Section)",
				"Social Studies","Science","Mathematics"};

		String programCode="HSE";
		Map<Long,org.ets.ereg.domain.interfaces.model.test.Test> testsMap= new HashMap<Long,org.ets.ereg.domain.interfaces.model.test.Test>();

		org.ets.ereg.domain.interfaces.model.test.Test test=null;

		for(int i=0;i<testIds.length;i++){
			test = new TestImpl();
			//testId = new TestId();
			//testId.setProgramCode(programCode);
			test.setTestId(testIds[i]);
			//test.setId(testId);
			test.setTestDispSeq(""+i);
			test.setDisplayDataFlag("Y");
			test.setTestName(testNames[i]);
			testsMap.put(testIds[i], test);

		}
		return testsMap;
	}
	private CustomerAccommodationTest createCustomerAccommodation(){

		CustomerAccommodationTest customerAccommodationTest =  new CustomerAccommodationTestImpl();
		CustomerAccommodationTestId customerAccommodationTestId =  customerAccommodationTest.getCustomerAccommodationTestId();

		customerAccommodationTestId.setCustomerId(1L);
		customerAccommodationTestId.setProgramCode("HSE");
		customerAccommodationTestId.setTestId(4l);
		customerAccommodationTestId.setAccommodationTypeCode("2");
		customerAccommodationTestId.setDeliveryModeCode("CBT");

		customerAccommodationTest.setExpiryDate(DateHandler.convertToDate(2014, 1, 1));

		customerAccommodationTest.setApprovedDate(DateHandler.getCurrentDate());

		AccommodationTypeValue accommodationTypeValue = new AccommodationTypeValueImpl();

		accommodationTypeValue.setAccommodationTypeValueDisplaySequence(1);
		accommodationTypeValue.setAccommodationTypeValueIdentifier(1L);

		customerAccommodationTest.setAccommodationTypeValue(accommodationTypeValue);

		return customerAccommodationTest;

	}




}
