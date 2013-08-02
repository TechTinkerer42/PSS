package org.ets.ereg.profile.accommodation.vo;

import java.util.Date;

import org.ets.ereg.common.util.DateHandler;


public class CustomerAccommodationVO {
	private Long customerId;
	private String programCode;
	private Long testId;
	private String deliveryMethodCode;
	private AccommodationVO accommodation;
	private Date expirationDate;
	private Date approvalDate;

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

    public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public AccommodationVO getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(AccommodationVO accommodation) {
		this.accommodation = accommodation;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public boolean isActiveAccommodation(){
		return (DateHandler.getCurrentDate().compareTo(expirationDate)<=0);
	}

	public String getDeliveryMethodCode() {
		return deliveryMethodCode;
	}

	public void setDeliveryMethodCode(String deliveryMethodCode) {
		this.deliveryMethodCode = deliveryMethodCode;
	}
}
