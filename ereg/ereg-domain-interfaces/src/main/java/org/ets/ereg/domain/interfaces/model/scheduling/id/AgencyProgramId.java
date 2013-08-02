package org.ets.ereg.domain.interfaces.model.scheduling.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class AgencyProgramId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "AGNCY_ID_NO", nullable = false)
	private Long agencyId;

	@Column(name = "PGM_CDE", nullable = false, length = 5)
	private String programCode;

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(agencyId).append(programCode).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AgencyProgramId) {
			AgencyProgramId other = (AgencyProgramId) obj;
			return new EqualsBuilder().append(agencyId, other.getAgencyId())
					.append(programCode, other.getProgramCode()).isEquals();
		} else {
			return false;
		}
	}
	
}
