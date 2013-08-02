package org.ets.ereg.scheduling.vo;

import java.util.List;

public class TestCenterSearchResultEntry {
	private Long id;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String stateName;
	private String stateAbbreviation;
	private String countryName;
	private String countryAbbreviation;
	private String postalCode;
	private String phoneCountryCode;
	private String phoneNumber;
	private String phoneExtension;
	private Double latitudeDegree;
	private Double longitudeDegree;
	private Double distanceMile;
	private List<String> deliveryModes;
	private String schedulingType;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressLine1() {
		return addressLine1;
	}
	
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	public String getAddressLine2() {
		return addressLine2;
	}
	
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	public String getAddressLine3() {
		return addressLine3;
	}
	
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getStateName() {
		return stateName;
	}
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}
	
	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneExtension() {
		return phoneExtension;
	}
	
	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}
	
	public Double getLatitudeDegree() {
		return latitudeDegree;
	}
	
	public void setLatitudeDegree(Double latitudeDegree) {
		this.latitudeDegree = latitudeDegree;
	}
	
	public Double getLongitudeDegree() {
		return longitudeDegree;
	}
	
	public void setLongitudeDegree(Double longitudeDegree) {
		this.longitudeDegree = longitudeDegree;
	}
	
	public Double getDistanceMile() {
		return distanceMile;
	}
	
	public void setDistanceMile(Double distanceMile) {
		this.distanceMile = distanceMile;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}

	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}

	public List<String> getDeliveryModes() {
		return deliveryModes;
	}

	public void setDeliveryModes(List<String> deliveryModes) {
		this.deliveryModes = deliveryModes;
	}

	public String getSchedulingType() {
		return schedulingType;
	}

	public void setSchedulingType(String schedulingType) {
		this.schedulingType = schedulingType;
	}
	
}
