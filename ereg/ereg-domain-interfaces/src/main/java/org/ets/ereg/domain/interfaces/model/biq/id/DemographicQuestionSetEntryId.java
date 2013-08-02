package org.ets.ereg.domain.interfaces.model.biq.id;

import java.io.Serializable;
import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DemographicQuestionSetEntryId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="SET_ID",nullable=false,length=5)
	private Long setId;
	
	@Column(name="DMGRPH_QSTN_NO",nullable=false,length=9)
	private Long demographicQuestionNo;
	
	
	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getDemographicQuestionNo() {
		return demographicQuestionNo;
	}

	public void setDemographicQuestionNo(Long demographicQuestionNo) {
		this.demographicQuestionNo = demographicQuestionNo;
	}

	
	@Override
	public int hashCode(){
		
		return new HashCodeBuilder().append(setId).append(demographicQuestionNo).hashCode();
		
	}
	
	
	@Override
	public boolean equals(final Object obj){
		
		if(obj instanceof DemographicQuestionSetEntryId){
			final DemographicQuestionSetEntryId other = (DemographicQuestionSetEntryId)obj;
			return new EqualsBuilder().append(setId,other.setId)
					.append(demographicQuestionNo, other.demographicQuestionNo).isEquals();			
		}else{
			
			return false;
		}
		
	}
	
	
	

}
