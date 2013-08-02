package org.ets.ereg.common.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ProgramAccommodationDeliveryModeDao;
import org.ets.ereg.common.business.service.AccommodationsService;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("accommodationsService")
public class AccommodationsServiceImpl implements AccommodationsService {

	@Resource(name="programAccommodationDeliveryModeDao")
	private ProgramAccommodationDeliveryModeDao programAccommodationDeliveryModeDao;	
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
	public List<DeliveryModeType> getAccommodationDeliveryMethods(
			String programCode) {		
		return programAccommodationDeliveryModeDao.getAccommodationDeliveryMethods(programCode);
	}

	@Override
	public List<AccommodationType> getAllAccommodations(String programCode,
			String deliveryModeCode) {		
		return programAccommodationDeliveryModeDao.getAllAccommodations(programCode, deliveryModeCode);
		
	}

	public ProgramAccommodationDeliveryModeDao getProgramAccommodationDeliveryModeDao() {
		return programAccommodationDeliveryModeDao;
	}

	public void setProgramAccommodationDeliveryModeDao(
			ProgramAccommodationDeliveryModeDao programAccommodationDeliveryModeDao) {
		this.programAccommodationDeliveryModeDao = programAccommodationDeliveryModeDao;
	}	

}
