package org.ets.ereg.domain.interfaces.scheduling.response;


public interface ExtendHoldSeatResponse extends SchedulingResponse {

	String getHoldCode();
	void setHoldCode(String holdCode);

	String getHoldSource();
	void setHoldSource(String holdSource);

	String getHoldDuration();
	void setHoldDuration(String holdDuration);

	String getEtsAppointmentId();
	void setEtsAppointmentId(String etsAppointmentId);

	String getSiteCode();
	void setSiteCode(String siteCode);
}
