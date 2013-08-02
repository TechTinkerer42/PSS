package org.ets.ereg.domain.interfaces.model.program.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class ProgramColumnRuleId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="PGM_CDE", nullable=false)
	private String programCode;
	
	@Column(name="ENTY_NAM", nullable=false)
	private String entityName;
	
	@Column(name="PRPRTY_NAM", nullable=false)
	private String propertyName;

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(programCode)
					.append(entityName)
					.append(propertyName).hashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		
		if (obj instanceof ProgramColumnRuleId) {
			final ProgramColumnRuleId other = (ProgramColumnRuleId) obj;
			return new EqualsBuilder().append(programCode, other.programCode)
					.append(entityName, other.entityName)
					.append(propertyName,other.propertyName).isEquals();					
		} else {
			return false;
		}
	}


}
