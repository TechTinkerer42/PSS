package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.ets.ereg.domain.interfaces.model.biq.id.DemographicResponseId;
import org.ets.ereg.domain.interfaces.model.i18n.InternationalContent;

public interface DemographicResponse extends Serializable {

	 public DemographicResponseId getId();
	 public void setId(DemographicResponseId demographicValidRespId);
	 
	 public Long getDemographicValidRespSeqNo();
	 public void setDemographicValidRespSeqNo(Long demographicValidRespSeqNo);
	 
	 public Boolean getTestAdmValidRespFrmFlg();
	 public void setTestAdmValidRespFrmFlg(Boolean testAdmValidRespFrmFlg);
	 
	 public Boolean getTestAdmValidRespFrmOrgFlg();
	 public void setTestAdmValidRespFrmOrgFlg(Boolean testAdmValidRespFrmOrgFlg);
	 
	 public Date getDemographicValidRespEffDate();		 
	 public void setDemographicValidRespEffDate(Date demographicValidRespEffDate);
	 
	 public Date getDemographicValidRespExprtnDate();		 
	 public void setDemographicValidRespExprtnDate(Date demographicValidRespExprtnDate);	
	
	 public String getRespLable();
	 public void setRespLable(String respLable);
	 
	 public Boolean getDemographicValidRespActFlg();
	 public void setDemographicValidRespActFlg(Boolean demographicValidRespActFlg);
	 
	 public DemographicQuestion getDemographicQstnNo();
	 public void setDemographicQstnNo(DemographicQuestion demographicQstnNo);
	 
	 public Set<DemographicQuestionTrigger> getDemographicQstnTriggers();
	 public void setDemographicQstnTriggers(Set<DemographicQuestionTrigger> demographicQstnTriggers);

	 public InternationalContent getInternationalContentId();	
	 public void setInternationalContentId(InternationalContent internationalContentId);
	

}
