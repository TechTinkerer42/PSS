package org.ets.ereg.domain.interfaces.model.accommodation;

public interface AccommodationTypeValue {

	Long getAccommodationTypeValueIdentifier();

	void setAccommodationTypeValueIdentifier(
			Long accommodationTypeValueIdentifierNumber);

	AccommodationType getAccommodationType();

	void setAccommodationType(AccommodationType accommodationType);

	String getLabel();

	void setLabel(String label);

	String getValue();

	void setValue(String value);

	String getSchedulingServiceAccommodationValue();

	void setSchedulingServiceAccommodationValue(
			String schedulingServiceAccommodationValue);

	String getAccommodationTypeValueUIText();

	void setAccommodationTypeValueUIText(String accommodationTypeValueUIText);

	Integer getAccommodationTypeValueDisplaySequence();

	void setAccommodationTypeValueDisplaySequence(
			Integer accommodationTypeValueDisplaySequence);

	Boolean getIsDisplayable();

	void setIsDisplayable(Boolean isDisplayable);

}
