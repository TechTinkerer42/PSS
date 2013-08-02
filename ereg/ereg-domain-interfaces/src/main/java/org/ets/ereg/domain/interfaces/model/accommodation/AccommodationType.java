package org.ets.ereg.domain.interfaces.model.accommodation;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.accommodation.group.AccommodationTypeGroup;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface AccommodationType extends ReferenceEntityInterface {
	public static final String ACCOMMODATION_TYPE_CODE_OTHER = "90";

	List<AccommodationTypeValue> getAccommodationTypeValues();

	void setAccommodationTypeValues(
			List<AccommodationTypeValue> accommodationTypeValues);

	AccommodationTypeGroup getAccommodationTypeGroup();

	void setAccommodationTypeGroup(AccommodationTypeGroup accommodationTypeGroup);

}
