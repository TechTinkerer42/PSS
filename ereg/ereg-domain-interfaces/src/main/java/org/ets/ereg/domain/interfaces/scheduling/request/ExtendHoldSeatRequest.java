package org.ets.ereg.domain.interfaces.scheduling.request;



public interface ExtendHoldSeatRequest extends SchedulingRequest{

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

	String getSchedulingTestCode();
	void setSchedulingTestCode(String schedulingTestCode);

	Long getTestId();
    void setTestId(Long testId);

}
