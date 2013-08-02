package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;
import java.util.Set;

import org.ets.ereg.domain.interfaces.model.i18n.InternationalContent;

public interface DemographicQuestion extends Serializable {

	public Long getDemographicQuestionNo();
	public void setDemographicQuestionNo(Long demographicQuestionNo);	
	
	public DemographicQuestionRespType getDemographicQuestionRespTypCode();
	public void setDemographicQuestionRespTypCode(DemographicQuestionRespType demographicQuestionRespTypCode);	
	
	public String getDemographicQuestionName();
	public void setDemographicQuestionName(String demographicQuestionName);
		
	public InternationalContent getInternationalContentId();	
	public void setInternationalContentId(InternationalContent internationalContentId);
	
	public Set<DemographicResponse> getDemographicResps();
	public void setDemographicResps(Set<DemographicResponse> demographicResps);	

}
