package org.ets.ereg.scheduling.request;

import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingOperation;


public class ExtendHoldSeatRequestImpl implements ExtendHoldSeatRequest {


	private String requesterRoleTypeCode;
	private ETSCustomer customer;

	private String transactionID;
	private String requestSystemId;
	private String source;
	private String alternateForm;


	private String holdCode;
	private String holdSource;
	private String holdDuration;
	private String etsAppointmentId;
	private Long testId;
	private String siteCode;
	private String schedulingTestCode;
	private TestVariation testVariation;

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


	@Override
    public ETSCustomer getCustomer() {
		return customer;
	}
	@Override
    public void setCustomer(ETSCustomer customer) {
		this.customer = customer;
	}
	@Override
    public String getTransactionID() {
		return transactionID;
	}
	@Override
    public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	@Override
    public String getRequestSystemId() {
		return requestSystemId;
	}
	@Override
    public void setRequestSystemId(String requestSystemId) {
		this.requestSystemId = requestSystemId;
	}
	@Override
    public void setRequesterRoleTypeCode(String requesterRoleTypeCode) {
		this.requesterRoleTypeCode = requesterRoleTypeCode;
	}
	@Override
    public String getSource() {
		return source;
	}
	@Override
    public void setSource(String source) {
		this.source = source;
	}
	@Override
    public String getAlternateForm() {
		return alternateForm;
	}
	@Override
    public void setAlternateForm(String alternateForm) {
		this.alternateForm = alternateForm;
	}


	@Override
	public SchedulingOperation getOperation() {
		return SchedulingOperation.EXTEND_HOLD;
	}
	@Override
    public String getRequesterRoleTypeCode() {
		return requesterRoleTypeCode;
	}
	@Override
	public Long getTestId() {
		return testId;
	}
	@Override
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	@Override
    public String getSchedulingTestCode() {
		return schedulingTestCode;
	}
	@Override
    public void setSchedulingTestCode(String schedulingTestCode) {
		this.schedulingTestCode = schedulingTestCode;
	}
    @Override
    public TestVariation getTestVariation() {
        return testVariation;
    }
    @Override
    public void setTestVariation(TestVariation testVariation) {
        this.testVariation = testVariation;
    }

}
