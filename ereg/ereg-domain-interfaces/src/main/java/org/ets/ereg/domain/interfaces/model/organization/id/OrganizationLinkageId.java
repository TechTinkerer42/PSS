package org.ets.ereg.domain.interfaces.model.organization.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrganizationLinkageId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ORG_ID_NO", nullable = false)
	private long organizationId;
	
	@Column(name = "LNKG_TYP_CDE", nullable = false)
	private String linkTypeCode;
	
	public long getOrganizationId() {
		return organizationId;
	}
	
	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getLinkTypeCode() {
		return linkTypeCode;
	}
	
	public void setLinkTypeCode(String linkTypeCode) {
		this.linkTypeCode = linkTypeCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(organizationId).append(linkTypeCode)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrganizationLinkageId) {
			final OrganizationLinkageId other = (OrganizationLinkageId) obj;
			return new EqualsBuilder().append(linkTypeCode, other.linkTypeCode)
					.append(organizationId, other.organizationId).isEquals();
		} else {
			return false;
		}
	}
}
