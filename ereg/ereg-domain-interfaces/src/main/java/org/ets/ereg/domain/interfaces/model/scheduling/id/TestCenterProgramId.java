package org.ets.ereg.domain.interfaces.model.scheduling.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TestCenterProgramId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TST_CNTR_ID_NO", nullable = false)
	private Long testCenterId;
	
	@Column(name = "PGM_CDE", nullable = false, length=5)
	private String programCode;
	
	public Long getTestCenterId() {
		return testCenterId;
	}
	
	public void setTestCenterIdId(Long testCenterId) {
		this.testCenterId = testCenterId;
	}
	
	public String getProgramCode() {
		return programCode;
	}
	
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(testCenterId).append(programCode)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TestCenterProgramId) {
			final TestCenterProgramId other = (TestCenterProgramId) obj;
			return new EqualsBuilder().append(programCode, other.programCode)
					.append(testCenterId, other.testCenterId).isEquals();
		} else {
			return false;
		}
	}
}
