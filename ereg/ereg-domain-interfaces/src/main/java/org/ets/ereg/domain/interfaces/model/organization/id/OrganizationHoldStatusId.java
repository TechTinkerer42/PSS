package org.ets.ereg.domain.interfaces.model.organization.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrganizationHoldStatusId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ORG_ID_NO", nullable = false)
	private long organizationId;
	
	@Column(name = "HLD_TYP_CDE", nullable = false)
	private String holdTypeCode;
	
	public long getOrganizationId() {
		return organizationId;
	}
	
	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getHoldTypeCode() {
		return holdTypeCode;
	}
	
	public void setHoldTypeCode(String holdTypeCode) {
		this.holdTypeCode = holdTypeCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(organizationId).append(holdTypeCode)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrganizationHoldStatusId) {
			final OrganizationHoldStatusId other = (OrganizationHoldStatusId) obj;
			return new EqualsBuilder().append(holdTypeCode, other.holdTypeCode)
					.append(organizationId, other.organizationId).isEquals();
		} else {
			return false;
		}
	}
}
