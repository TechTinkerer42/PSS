package org.ets.ereg.shared.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ProgramAccommodationDeliveryModeDao;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
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
public class TestProgramAccommodationDeliveryModeDaoImpl {

	private static Logger logger = LoggerFactory.getLogger(TestProgramAccommodationDeliveryModeDaoImpl.class);
	
	@Resource(name="programAccommodationDeliveryModeDao")
	private ProgramAccommodationDeliveryModeDao programAccommodationDeliveryModeDao;
	
	@Test
	public void testGetAllAccommodations(){		
		List<AccommodationType> accommodationTypes =  programAccommodationDeliveryModeDao.getAllAccommodations("HSE","PBT");
		for(AccommodationType accommodationType:accommodationTypes ){
			logger.debug("Accommodation: {}",accommodationType.getDescription());
			logger.debug("Accommodation Group: {}",accommodationType.getAccommodationTypeGroup().getAccommodationTypeGroupDescription());
			List<AccommodationTypeValue> typeValues = accommodationType.getAccommodationTypeValues();			
			for(AccommodationTypeValue typeValue:typeValues){
				logger.debug("Accommodation Type Value: {}",typeValue.getValue());
			}					
		}
		
		Assert.notEmpty(accommodationTypes);
	}
	
	@Test
	public void testGetAccommodationDeliveryMethods(){
		List<DeliveryModeType> deliveryMethods = programAccommodationDeliveryModeDao.getAccommodationDeliveryMethods("HSE");
		for(DeliveryModeType deliveryMethod: deliveryMethods){			
			logger.debug("Delviery Method: {}",deliveryMethod.getDescription());
		}
		Assert.notEmpty(deliveryMethods);
	}
	
	@Test
	public void testGetProgramAccommodationDeliveryMode(){
		ProgramAccommodationDeliveryMode programAccommodationDeliveryMode = programAccommodationDeliveryModeDao.getProgramAccommodationDeliveryMode("HSE", "PBT", "1");		
		Assert.notNull(programAccommodationDeliveryMode);
	}
}
