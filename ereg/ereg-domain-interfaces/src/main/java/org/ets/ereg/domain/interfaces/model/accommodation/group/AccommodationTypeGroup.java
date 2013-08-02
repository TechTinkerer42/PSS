package org.ets.ereg.domain.interfaces.model.accommodation.group;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.group.id.AccommodationTypeGroupId;

public interface AccommodationTypeGroup {

	AccommodationTypeGroupId getAccommodationTypeGroupId();

	void setAccommodationTypeGroupId(
			AccommodationTypeGroupId accommodationTypeGroupId);

	AccommodationGroup getAccommodationGroup();

	void setAccommodationGroup(AccommodationGroup accommodationGroup);

	AccommodationType getAccommodationType();

	void setAccommodationType(AccommodationType accommodationType);

	String getAccommodationTypeGroupDescription();

	void setAccommodationTypeGroupDescription(
			String accommodationTypeGroupDescription);

	Integer getAccommodationTypeGroupDisplaySequence();

	void setAccommodationTypeGroupDisplaySequence(
			Integer accommodationTypeGroupDisplaySequence);

	Boolean getIsDisplayable();

	void setIsDisplayable(Boolean displayable);

}
