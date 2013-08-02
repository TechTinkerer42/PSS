package org.ets.ereg.common.business.service;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;


public interface AccommodationsService {

	List<AccommodationType> getAllAccommodations(String programCode,
			String deliveryModeCode);
	List<DeliveryModeType> getAccommodationDeliveryMethods(String programCode);
}
