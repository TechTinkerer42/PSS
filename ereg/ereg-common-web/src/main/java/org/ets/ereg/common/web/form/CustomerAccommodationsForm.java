package org.ets.ereg.common.web.form;

import java.util.List;

import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.common.business.util.ProgramContextHolder;

public class CustomerAccommodationsForm {

	public static final String TEST_ID = "testId";
	public static final String DELIVERY_MODE = "deliveryMode";
	public static final String ACCOMMODATION_STATUS = "accommodationStatus";

	private String accommodationTypeCode;

	private Long customerId;

	private String programCode;

	private Long testId;

	private String deliveryMode;

	private AccommodationStatus accommodationStatus;

	private List<CustomerAccomodationFormEntry> accommodations;

	private List<String> otherTestCodes;

	private boolean bulkEdit;

	public CustomerAccommodationsForm(){
		programCode = ProgramContextHolder.getProgramCode();
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}



	public AccommodationStatus getAccommodationStatus() {
		return accommodationStatus;
	}

	public void setAccommodationStatus(AccommodationStatus accommodationStatus) {
		this.accommodationStatus = accommodationStatus;
	}

	public List<CustomerAccomodationFormEntry> getAccommodations() {
		return accommodations;
	}

	public void setAccommodations(List<CustomerAccomodationFormEntry> accommodations) {
		this.accommodations = accommodations;
	}

	public List<String> getOtherTestCodes() {
		return otherTestCodes;
	}

	public void setOtherTestCodes(List<String> otherTestCodes) {
		this.otherTestCodes = otherTestCodes;
	}


	public void transfereFromFormDateOfExpiration(){
		for(CustomerAccomodationFormEntry accommodation: accommodations){
			accommodation.transfereFromFieldsToDate();
		}
	}

	public void transfereToFormDateOfExpiration(){
		for(CustomerAccomodationFormEntry accommodation: accommodations){
			accommodation.transfereDateToFields();
		}
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public boolean isBulkEdit() {
		return bulkEdit;
	}

	public void setBulkEdit(boolean bulkEdit) {
		this.bulkEdit = bulkEdit;
	}

	public String getAccommodationTypeCode() {
		return accommodationTypeCode;
	}

	public void setAccommodationTypeCode(String accommodationTypeCode) {
		this.accommodationTypeCode = accommodationTypeCode;
	}
}
