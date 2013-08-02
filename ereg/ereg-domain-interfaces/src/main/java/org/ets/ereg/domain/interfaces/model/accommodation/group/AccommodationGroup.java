package org.ets.ereg.domain.interfaces.model.accommodation.group;

public interface AccommodationGroup {

	Long getAccommodationGroupIdentifierNumber();

	void setAccommodationGroupIdentifierNumber(
			Long accommodationGroupIdentifierNumber);

	String getDisplayTypeCode();

	void setDisplayTypeCode(String displayTypeCode);

	Integer getAccommodationGroupSequenceNumber();

	void setAccommodationGroupSequenceNumber(
			Integer accommodationGroupSequenceNumber);

	String getAccommodationGroupDescription();

	void setAccommodationGroupDescription(String accommodationGroupDescription);

	String getAccommodationGroupUIText();

	void setAccommodationGroupUIText(String accommodationGroupUIText);

}
