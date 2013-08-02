package org.ets.ereg.scheduling.response;

import org.ets.ereg.domain.interfaces.scheduling.response.ExtendHoldSeatResponse;

public class ExtendHoldSeatResponseImpl implements ExtendHoldSeatResponse {

	private String holdCode;
	private String holdSource;
	private String holdDuration;
	private String etsAppointmentId;
	private String siteCode;
	private boolean successful;
	private Long testId;

	@Override
	public boolean isSuccessful() {
		return successful;
	}

	@Override
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	@Override
    public String getHoldCode() {
		return holdCode;
	}

	@Override
    public void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
	}

	@Override
    public String getHoldSource() {
		return holdSource;
	}

	@Override
    public void setHoldSource(String holdSource) {
		this.holdSource = holdSource;
	}

	@Override
    public String getHoldDuration() {
		return holdDuration;
	}

	@Override
    public void setHoldDuration(String holdDuration) {
		this.holdDuration = holdDuration;
	}

	@Override
    public String getEtsAppointmentId() {
		return etsAppointmentId;
	}

	@Override
    public void setEtsAppointmentId(String etsAppointmentId) {
		this.etsAppointmentId = etsAppointmentId;
	}

	@Override
    public String getSiteCode() {
		return siteCode;
	}

	@Override
    public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}
}
