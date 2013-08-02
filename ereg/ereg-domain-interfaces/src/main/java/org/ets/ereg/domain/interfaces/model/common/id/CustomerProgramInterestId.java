package org.ets.ereg.domain.interfaces.model.common.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomerProgramInterestId implements Serializable {
	@Column(name = "CUSTOMER_ID")
	private long customerId;
	@Column(name = "PGM_CDE", nullable = false, length = 5)
	private String programCode;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(customerId).append(programCode)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof CustomerProgramInterestId) {
			final CustomerProgramInterestId other = (CustomerProgramInterestId) obj;
			return new EqualsBuilder().append(programCode, other.programCode)
					.append(customerId, other.customerId).isEquals();
		} else {
			return false;
		}
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

}
