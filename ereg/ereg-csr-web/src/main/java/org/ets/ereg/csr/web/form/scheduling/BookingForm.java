package org.ets.ereg.csr.web.form.scheduling;

public class BookingForm {
	public Long bookingId;
	public String appointmentNumber;
	public String candidateName;
	public String eventOutComeStatusCd;
	public boolean eligibilityFlag;
	public Long parentBaseFormId;
	public String baseFormLangCd;
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getAppointmentNumber() {
		return appointmentNumber;
	}
	public void setAppointmentNumber(String appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getEventOutComeStatusCd() {
		return eventOutComeStatusCd;
	}
	public void setEventOutComeStatusCd(String eventOutComeStatusCd) {
		this.eventOutComeStatusCd = eventOutComeStatusCd;
	}
	public boolean isEligibilityFlag() {
		return eligibilityFlag;
	}
	public void setEligibilityFlag(boolean eligibilityFlag) {
		this.eligibilityFlag = eligibilityFlag;
	}
	public Long getParentBaseFormId() {
		return parentBaseFormId;
	}
	public void setParentBaseFormId(Long parentBaseFormId) {
		this.parentBaseFormId = parentBaseFormId;
	}
	public String getBaseFormLangCd() {
		return baseFormLangCd;
	}
	public void setBaseFormLangCd(String baseFormLangCd) {
		this.baseFormLangCd = baseFormLangCd;
	}

}
