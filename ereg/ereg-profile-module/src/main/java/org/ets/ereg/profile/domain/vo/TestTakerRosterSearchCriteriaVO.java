/**
 *
 */
package org.ets.ereg.profile.domain.vo;

import org.ets.ereg.common.vo.AbstractSearchCriteriaVO;


/**
 *
 *
 * @author Mangesh Pardesi
 *
 * @version 1.0
 *
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */
public class TestTakerRosterSearchCriteriaVO extends
		AbstractSearchCriteriaVO {

	private Long agencyId;
	private Long adminId;
	private Integer testCenterId;
	private String appointmentDayFrom;
	private String appointmentMonthFrom;
	private String appointmentYearFrom;
	private String appointmentDayTo;
	private String appointmentMonthTo;
	private String appointmentYearTo;
	private String[] selectedCheckInStatusCodes;
	private String testId;
	private String[] selectedDeliveryModeType;
	private String testFormCode;
	private String [] selectedAccomodations;



	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
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

	public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
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

	public String getTestFormCode() {
		return testFormCode;
	}

	public void setTestFormCode(String testFormCode) {
		this.testFormCode = testFormCode;
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
