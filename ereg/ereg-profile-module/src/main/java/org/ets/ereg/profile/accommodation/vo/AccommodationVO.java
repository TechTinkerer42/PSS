package org.ets.ereg.profile.accommodation.vo;

public class AccommodationVO {
	private String accommodationTypeCode;
	private String description;
	private Long valueId;
	private String valueText;
	private String otherValueText;
	
	public String getAccommodationTypeCode() {
		return accommodationTypeCode;
	}
	
	public void setAccommodationTypeCode(String accommodationTypeCode) {
		this.accommodationTypeCode = accommodationTypeCode;
	}
	
	public Long getValueId() {
		return valueId;
	}
	
	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}
	
	public String getOtherValueText() {
		return otherValueText;
	}
	
	public void setOtherValueText(String otherValueText) {
		this.otherValueText = otherValueText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}
}
