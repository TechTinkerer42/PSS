package org.ets.ereg.domain.organization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationRelationshipType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ORG_REL_TYP")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "etsStandardElements")
public class OrganizationRelationshipTypeImpl implements OrganizationRelationshipType,
		Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "ORG_REL_TYP_CDE", nullable = false, length = 5)
	private String code;

	@Column(name = "ORG_REL_TYP_DSC", nullable = false, length = 175)
	private String description;

	//@Override
	public String getCode() {
		return code;
	}

	//@Override
	public void setCode(String code) {
		this.code = code;
	}

	//@Override
	public String getDescription() {
		return description;
	}

	//@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(code).append(description)
				.toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrganizationRelationshipTypeImpl) {
			final OrganizationRelationshipTypeImpl other = (OrganizationRelationshipTypeImpl) obj;
			return new EqualsBuilder().append(code, other.code)
					.append(description, other.description).isEquals();
		} else {
			return false;
		}
	}
}
