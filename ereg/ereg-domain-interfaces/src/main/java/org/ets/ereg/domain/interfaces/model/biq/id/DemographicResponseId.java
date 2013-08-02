package org.ets.ereg.domain.interfaces.model.biq.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class DemographicResponseId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="DMGRPH_QSTN_NO",nullable=false,length=9)
	private Long demographicQuestionNo;
	
	@Column(name="DMGRPH_RSP_NO",nullable=false,length=9)
	private Long demographicRespNo;
	
	
	public Long getDemographicQuestionNo() {
		return demographicQuestionNo;
	}

	public void setDemographicQuestionNo(Long demographicQuestionNo) {
		this.demographicQuestionNo = demographicQuestionNo;
	}

	public Long getDemographicRespNo() {
		return demographicRespNo;
	}

	public void setDemographicRespNo(Long demographicRespNo) {
		this.demographicRespNo = demographicRespNo;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(demographicQuestionNo)
					.append(demographicRespNo)
					.hashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		
		if (obj instanceof DemographicResponseId) {
			
			final DemographicResponseId other = (DemographicResponseId) obj;
			return new EqualsBuilder().append(demographicQuestionNo, other.demographicQuestionNo)
					.append(demographicRespNo, other.demographicRespNo)
					.isEquals();					
			
		} else {
			return false;
		}
	}

	
}
