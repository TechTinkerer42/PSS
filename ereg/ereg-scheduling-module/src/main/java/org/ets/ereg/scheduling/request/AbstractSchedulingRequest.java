package org.ets.ereg.scheduling.request;

import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingOperation;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingRequest;




public abstract class AbstractSchedulingRequest implements SchedulingRequest {

	//requesterRoleTypeCode, customer, testId and duration are required fields of all requests
	private String requesterRoleTypeCode;
	private ETSCustomer customer;

	//below 4 fields are populated in SSA requests converter.
	//these fields are not expected to send by requester.
	private String transactionID;
	private String requestSystemId;
	private String source;
	private String alternateForm;
	private TestVariation testVariation;

	@Override
	public String getRequesterRoleTypeCode() {
		return requesterRoleTypeCode;
	}

	@Override
	public void setRequesterRoleTypeCode(String requesterRoleTypeCode) {
		this.requesterRoleTypeCode = requesterRoleTypeCode;
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
    public abstract SchedulingOperation getOperation();

	@Override
    public TestVariation getTestVariation() {
        return testVariation;
    }

	@Override
    public void setTestVariation(TestVariation testVariation) {
        this.testVariation = testVariation;
    }



}