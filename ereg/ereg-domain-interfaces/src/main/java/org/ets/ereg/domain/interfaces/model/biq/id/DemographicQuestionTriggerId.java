package org.ets.ereg.domain.interfaces.model.biq.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DemographicQuestionTriggerId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="TRIGGERING_DMGRPH_QSTN_NO",nullable=false)
	private Long triggeringDemographicQstnNo;
	
	@Column(name="TRIGGERING_DMGRPH_RSP_NO",nullable=false)
	private Long triggeringDemographicRespNo;
	
	@Column(name="SET_ID",nullable=false)
	private Long setId;
	
	@Column(name="DMGRPH_QSTN_NO",nullable=false)
	private Long demographicQstnNo;
	
	
	public Long getTriggeringDemographicQstnNo() {
		return triggeringDemographicQstnNo;
	}

	public void setTriggeringDemographicQstnNo(Long triggeringDemographicQstnNo) {
		this.triggeringDemographicQstnNo = triggeringDemographicQstnNo;
	}

	public Long getTriggeringDemographicRespNo() {
		return triggeringDemographicRespNo;
	}

	public void setTriggeringDemographicRespNo(Long triggeringDemographicRespNo) {
		this.triggeringDemographicRespNo = triggeringDemographicRespNo;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getDemographicQstnNo() {
		return demographicQstnNo;
	}

	public void setDemographicQstnNo(Long demographicQstnNo) {
		this.demographicQstnNo = demographicQstnNo;
	}

	@Override
	public int hashCode(){
		
		return new HashCodeBuilder().append(triggeringDemographicQstnNo)
					.append(triggeringDemographicRespNo)
					.append(setId).append(demographicQstnNo).hashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		
		if (obj instanceof DemographicQuestionTriggerId) {
			
			final DemographicQuestionTriggerId other = (DemographicQuestionTriggerId) obj;
			return new EqualsBuilder().append(demographicQstnNo, other.demographicQstnNo)
					.append(triggeringDemographicRespNo, other.triggeringDemographicRespNo)
					.append(triggeringDemographicQstnNo, other.triggeringDemographicQstnNo)
					.append(setId, other.setId)
					.isEquals();					
			
		} else {
			return false;
		}
	}
	
	
	

}
