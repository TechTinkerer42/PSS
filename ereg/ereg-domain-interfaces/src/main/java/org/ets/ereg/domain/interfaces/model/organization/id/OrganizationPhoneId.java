package org.ets.ereg.domain.interfaces.model.organization.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrganizationPhoneId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ORG_ID_NO", nullable = false)
	private Long organizationId;
	
	@Column(name = "PHONE_ID", nullable = false)
	private Long phoneId;
	
	public long getOrganizationId() {
		return organizationId;
	}
	
	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
	
	public Long getPhoneId() {
		return phoneId;
	}
	
	public void setPhoneId(Long phoneId) {
		this.phoneId = phoneId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(organizationId).append(phoneId)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrganizationPhoneId) {
			final OrganizationPhoneId other = (OrganizationPhoneId) obj;
			return new EqualsBuilder().append(phoneId, other.phoneId)
					.append(organizationId, other.organizationId).isEquals();
		} else {
			return false;
		}
	}
}
