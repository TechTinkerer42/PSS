package org.ets.ereg.domain.interfaces.model.accommodation;

import org.ets.ereg.domain.interfaces.model.accommodation.id.ProgramAccommodationDeliveryModeId;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;

public interface ProgramAccommodationDeliveryMode {

	ProgramAccommodationDeliveryModeId getProgramAccommodationDeliveryModeId();

	void setProgramAccommodationDeliveryModeId(
			ProgramAccommodationDeliveryModeId programAccommodationDeliveryModeId);

	ProgramType getProgramCode();

	void setProgramCode(ProgramType programCode);

	DeliveryModeType getDeliveryModeType();

	void setDeliveryModeType(DeliveryModeType deliveryModeType);

	AccommodationType getAccommodationType();

	void setAccommodationType(AccommodationType accommodationType);

}
