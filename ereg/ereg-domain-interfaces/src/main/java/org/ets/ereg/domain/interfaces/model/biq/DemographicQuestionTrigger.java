package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.biq.id.DemographicQuestionTriggerId;

public interface DemographicQuestionTrigger extends Serializable {
	
	public DemographicQuestionTriggerId getDemographicQstnTriggerId();
	public void setDemographicQstnTriggerId(DemographicQuestionTriggerId demographicQstnTriggerId);
	
	public DemographicResponse getDemograpicRespNo();
	public void setDemograpicRespNo(DemographicResponse demograpicRespNo);
}
