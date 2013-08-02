package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.biq.id.CustomerDemographicResponseId;

public interface CustomerDemographicResponse extends Serializable {
	
	
	public CustomerDemographicResponseId getCustomerDemographicRespId();
	public void setCustomerDemographicRespId(CustomerDemographicResponseId customerDemographicRespId);
	
	public String getFreeFormTxtResp();
	public void setFreeFormTxtResp(String freeFormTxtResp);
	
	public DemographicResponse getDemograpicRespNo();
	public void setDemograpicRespNo(DemographicResponse demograpicRespNo);

}
