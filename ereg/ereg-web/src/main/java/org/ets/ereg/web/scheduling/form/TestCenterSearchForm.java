package org.ets.ereg.web.scheduling.form;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;

public class TestCenterSearchForm {
	public static final String COUNTRY = "country";
	public static final String CITY_STATE_OR_ZIPCODE = "cityStateOrZipCode";
	public static final String DISTANCE = "distance";
	public static final String TEST_CENTER_NAME = "testCenterName";
	public static final String TESTTYPES = "testTypes";
	public static final String LATITUDE_DEGREE = "latitudeDegree";
	public static final String LONGITUDE_DEGREE = "longitudeDegree";
	public static final String PROGRAM_CODE = "programCode";
	
	private Country country;
	private String cityStateOrZipCode;
	private String testCenterName;
	private Integer distance;
	private Double latitudeDegree;
	private Double longitudeDegree;
	private List<DeliveryModeType> testTypes;
	private String programCode;
	
	private int offset;
	
	private int count;
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public String getCityStateOrZipCode() {
		return cityStateOrZipCode;
	}
	
	public void setCityStateOrZipCode(String cityStateOrZipCode) {
		this.cityStateOrZipCode = cityStateOrZipCode;
	}
	
	public String getTestCenterName() {
		return testCenterName;
	}
	
	public void setTestCenterName(String testCenterName) {
		this.testCenterName = testCenterName;
	}
	
	public Integer getDistance() {
		return distance;
	}
	
	public void setDistance(Integer distance) {
		this.distance = distance;
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

	public List<DeliveryModeType> getTestTypes() {
		return testTypes;
	}

	public void setTestTypes(List<DeliveryModeType> testTypes) {
		this.testTypes = testTypes;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
}
