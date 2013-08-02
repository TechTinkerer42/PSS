package org.ets.ereg.domain.interfaces.model.organization.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrganizationRelationshipId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ORG_ID_1_NO", nullable = false)
	private Long organization1Id;
	
	@Column(name = "ORG_ID_2_NO", nullable = false)
	private Long organization2Id;
	
	
	
	public Long getOrganization1Id() {
		return organization1Id;
	}

	public void setOrganization1Id(Long organization1Id) {
		this.organization1Id = organization1Id;
	}

	public Long getOrganization2Id() {
		return organization2Id;
	}

	public void setOrganization2Id(Long organization2Id) {
		this.organization2Id = organization2Id;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(organization1Id).append(organization2Id)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrganizationRelationshipId) {
			final OrganizationRelationshipId other = (OrganizationRelationshipId) obj;
			return new EqualsBuilder().append(organization1Id, other.organization1Id)
					.append(organization2Id, other.organization2Id).isEquals();
		} else {
			return false;
		}
	}
}
