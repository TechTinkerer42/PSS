package org.ets.ereg.domain.interfaces.model.organization.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class OrganizationAddressId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "ORG_ID_NO", nullable = false)
	private Long organizationId;
	
	@Column(name = "ADDRESS_ID", nullable = false)
	private Long addressId;
	
	public long getOrganizationId() {
		return organizationId;
	}
	
	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
	
	public Long getAddressId() {
		return addressId;
	}
	
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(organizationId).append(addressId)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrganizationAddressId) {
			final OrganizationAddressId other = (OrganizationAddressId) obj;
			return new EqualsBuilder().append(addressId, other.addressId)
					.append(organizationId, other.organizationId).isEquals();
		} else {
			return false;
		}
	}
}
