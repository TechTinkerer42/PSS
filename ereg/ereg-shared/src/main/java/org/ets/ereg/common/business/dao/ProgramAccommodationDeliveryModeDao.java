package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;

public interface ProgramAccommodationDeliveryModeDao extends Dao<ProgramAccommodationDeliveryMode> {

	List<AccommodationType> getAllAccommodations(String programCode,String deliveryModeCode);	
	List<DeliveryModeType> getAccommodationDeliveryMethods(String programCode);
	ProgramAccommodationDeliveryMode getProgramAccommodationDeliveryMode(String programCode, String deliveryModeCode, String accommodationTypeCode);
	
}
