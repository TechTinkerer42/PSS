package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.biq.id.DemographicQuestionSetEntryId;

public interface DemographicQuestionSetEntry extends Serializable {
	
	public DemographicQuestionSetEntryId getId();
	public void setId(DemographicQuestionSetEntryId id);
	
	public Boolean getRespReqFlg();
	public void setRespReqFlg(Boolean respReqFlg);
	
	public Long getQuestionDisplaySeqNo();
	public void setQuestionDisplaySeqNo(Long questionDisplaySeqNo);
		
	public Long getAdminDisplaySeqNo();	
	public void setAdminDisplaySeqNo(Long adminDisplaySeqNo) ;
	
	public DemographicQuestion getDemographicQstn();
	public void setDemographicQstn(DemographicQuestion demographicQstn);

}
