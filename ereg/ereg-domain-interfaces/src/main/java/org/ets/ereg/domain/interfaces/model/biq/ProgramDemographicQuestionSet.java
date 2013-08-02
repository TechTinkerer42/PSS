package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;
import java.util.Date;

import org.ets.ereg.domain.interfaces.model.biq.id.ProgramDemographicQuestionSetId;

public interface ProgramDemographicQuestionSet extends Serializable {	

	public Date getExpirationDate();
	public void setExpirationDate(Date expirationDate);
	
	public ProgramDemographicQuestionSetId getProgramDemographicQuestionSetId();
	public void setProgramDemographicQuestionSetId(ProgramDemographicQuestionSetId programDemographicQuestionSetId);

	public Boolean getDateUsageTypFlg();
	public void setDateUsageTypFlg(Boolean dateUsageTypFlg);

	public DemographicQuestionSetType getDemographicQstnSetType();
	public void setDemographicQstnSetType(DemographicQuestionSetType demographicQstnSetType);
	
}
