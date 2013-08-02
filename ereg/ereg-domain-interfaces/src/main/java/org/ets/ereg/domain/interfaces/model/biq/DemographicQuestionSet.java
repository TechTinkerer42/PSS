package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;

public interface DemographicQuestionSet extends Serializable {

	public Long getSetId();
	public void setSetId(Long setId);
	
	public String getSetDesc();
	public void setSetDesc(String setDesc);
}
