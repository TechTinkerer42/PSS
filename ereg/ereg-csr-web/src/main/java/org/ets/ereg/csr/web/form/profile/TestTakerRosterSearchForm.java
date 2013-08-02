package org.ets.ereg.csr.web.form.profile;

import org.ets.ereg.common.web.form.AbstractSearchCriteriaForm;

public class TestTakerRosterSearchForm extends AbstractSearchCriteriaForm {

	private Long agencyId;
	private Long adminId;
	private Integer testCenterId;
	private String appointmentDayFrom;
	private String appointmentMonthFrom;
	private String appointmentYearFrom;
	private String appointmentDayTo;
	private String appointmentMonthTo;
	private String appointmentYearTo;
	private String[] selectedCheckInStatusCodes = {"CHI","CNT","NOS","NCI"};
	private String[] selectedDeliveryModeType = {"PBT","CBT"};
	private Long testId;
	private String testFormCode;
	private String [] selectedAccomodations = {"Yes","No"};




	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getTestFormCode() {
		return testFormCode;
	}

	public void setTestFormCode(String testFormCode) {
		this.testFormCode = testFormCode;
	}

	public Integer getTestCenterId() {
		return testCenterId;
	}

	public void setTestCenterId(Integer testCenterId) {
		this.testCenterId = testCenterId;
	}

	public String getAppointmentDayFrom() {
		return appointmentDayFrom;
	}

	public void setAppointmentDayFrom(String appointmentDayFrom) {
		this.appointmentDayFrom = appointmentDayFrom;
	}

	public String getAppointmentMonthFrom() {
		return appointmentMonthFrom;
	}

	public void setAppointmentMonthFrom(String appointmentMonthFrom) {
		this.appointmentMonthFrom = appointmentMonthFrom;
	}

	public String getAppointmentYearFrom() {
		return appointmentYearFrom;
	}

	public void setAppointmentYearFrom(String appointmentYearFrom) {
		this.appointmentYearFrom = appointmentYearFrom;
	}

	public String getAppointmentDayTo() {
		return appointmentDayTo;
	}

	public void setAppointmentDayTo(String appointmentDayTo) {
		this.appointmentDayTo = appointmentDayTo;
	}

	public String getAppointmentMonthTo() {
		return appointmentMonthTo;
	}

	public void setAppointmentMonthTo(String appointmentMonthTo) {
		this.appointmentMonthTo = appointmentMonthTo;
	}

	public String getAppointmentYearTo() {
		return appointmentYearTo;
	}

	public void setAppointmentYearTo(String appointmentYearTo) {
		this.appointmentYearTo = appointmentYearTo;
	}

	public String[] getSelectedCheckInStatusCodes() {
		return selectedCheckInStatusCodes;
	}

	public void setSelectedCheckInStatusCodes(String[] selectedCheckInStatusCodes) {
		if(selectedCheckInStatusCodes != null)
		{
			this.selectedCheckInStatusCodes = selectedCheckInStatusCodes.clone();
		}
	}

	public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String[] getSelectedDeliveryModeType() {
		return selectedDeliveryModeType;
	}

	public void setSelectedDeliveryModeType(String[] selectedDeliveryModeType) {
		if(selectedDeliveryModeType != null)
		{
			this.selectedDeliveryModeType = selectedDeliveryModeType.clone();
		}
	}

	public String[] getSelectedAccomodations() {
		return selectedAccomodations;
	}

	public void setSelectedAccomodations(String[] selectedAccomodations) {
		if(selectedAccomodations != null)
		{
			this.selectedAccomodations = selectedAccomodations.clone();
		}
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}



}
