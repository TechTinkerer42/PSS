package org.ets.ereg.domain.interfaces.model.biq.id;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ProgramDemographicQuestionSetId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="SET_ID",nullable=false,length=5)
	private Long setId;
	
	@Column(name="PGM_CDE",nullable=false,length=5)
	private String programCode;
	
	@Temporal(TemporalType.DATE)
	@Column(name="EFF_DT",nullable=false)
	private Date effectiveDate;
	
	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	@Override
	public int hashCode(){
		
		return new HashCodeBuilder().append(setId)
				.append(programCode)
				.append(effectiveDate).hashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		
		if(obj instanceof ProgramDemographicQuestionSetId){
			
			final ProgramDemographicQuestionSetId other = (ProgramDemographicQuestionSetId) obj;
			
			return new EqualsBuilder().append(setId, other.setId)
					.append(programCode, other.programCode)
					.append(effectiveDate, other.effectiveDate).isEquals();
			
		}else{
			return false;
		}
		
	}

}
