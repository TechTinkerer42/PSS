package org.ets.ereg.shared.service;

import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.business.dao.ProgramAccommodationDeliveryModeDao;
import org.ets.ereg.common.business.service.impl.AccommodationsServiceImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.util.Assert;

@RunWith(JMock.class)
public class TestAccommodationsServiceImpl {

	private static Mockery mockingContext = new Mockery();
	private static ProgramAccommodationDeliveryModeDao mockedProgramAccommodationDeliveryModeDao;
	private static AccommodationsServiceImpl accommodationsServiceImpl = new AccommodationsServiceImpl();
	@BeforeClass
	public static void setup() {
		setupMockObjects();
		accommodationsServiceImpl.setProgramAccommodationDeliveryModeDao(
							mockedProgramAccommodationDeliveryModeDao);
	}
	
	private static void setupMockObjects() {
		mockedProgramAccommodationDeliveryModeDao = mockingContext.mock(ProgramAccommodationDeliveryModeDao.class);
		
	}
	
	@Test
	public void testGetAllAccommodations(){
		final List<AccommodationType> accommodationTypes = new ArrayList<AccommodationType>();
				
		mockingContext.checking(new Expectations() {
			{
	             one(mockedProgramAccommodationDeliveryModeDao).getAllAccommodations("HSE","CBT");
	             will(returnValue(accommodationTypes));
	           }
			});
		
		List<AccommodationType> accommodationTypes1 = new ArrayList<AccommodationType>();
		accommodationTypes1 = accommodationsServiceImpl.getAllAccommodations("HSE","CBT");
		
		Assert.notNull(accommodationTypes1);
		mockingContext.assertIsSatisfied();
	}
	
	@Test
	public void testGetAccommodationDeliveryMethods(){
		final List<DeliveryModeType> deliveryMethods = new ArrayList<DeliveryModeType>();
				
		mockingContext.checking(new Expectations() {
			{
	             one(mockedProgramAccommodationDeliveryModeDao).getAccommodationDeliveryMethods("HSE");
	             will(returnValue(deliveryMethods));
	           }
			});
		
		List<DeliveryModeType> deliveryMethods1 = accommodationsServiceImpl.getAccommodationDeliveryMethods("HSE");
		
		Assert.notNull(deliveryMethods1);
		mockingContext.assertIsSatisfied();
	}
}
