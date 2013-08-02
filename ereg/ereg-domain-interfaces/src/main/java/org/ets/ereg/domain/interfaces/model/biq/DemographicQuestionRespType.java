package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface DemographicQuestionRespType extends ReferenceEntityInterface,
		Serializable {

	 public enum respType{
		 DROPDOWN("S"),
		 RADIO("R"),
		 MULTIPLE("M"),
		 FREEFORM("F");
		 
		 private respType(String type){
			 responseType=type;
		 }
		 
		 private String responseType;
	
		 public String getResponseType() {
			return responseType;
		 }
	 }
	 
	 public Long getDemographicQstnRespTypDsplySeq();
	 public void getDemographicQstnRespTypDsplySeq(Long demographicQstnRespTypDsplySeq);
	
}
