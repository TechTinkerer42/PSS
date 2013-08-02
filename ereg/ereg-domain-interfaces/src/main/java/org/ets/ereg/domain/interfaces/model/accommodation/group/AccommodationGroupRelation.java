package org.ets.ereg.domain.interfaces.model.accommodation.group;

import org.ets.ereg.domain.interfaces.model.accommodation.group.id.AccommodationGroupRelationId;

public interface AccommodationGroupRelation {

	AccommodationGroupRelationId getAccommodationGroupRelation();

	void setAccommodationGroupRelation(
			AccommodationGroupRelationId accommodationGroupRelation);

	AccommodationGroup getParentAccommodationGroup();

	void setParentAccommodationGroup(AccommodationGroup parentAccommodationGroup);

	AccommodationGroup getChildAccommodationGroup();

	void setChildAccommodationGroup(AccommodationGroup childAccommodationGroup);

	Integer getAccommodationGroupRelDispSequence();

	void setAccommodationGroupRelDispSequence(
			Integer accommodationGroupRelDispSequence);

	Boolean getIsDisplayable();

	void setIsDisplayable(Boolean isDisplayable);

}
