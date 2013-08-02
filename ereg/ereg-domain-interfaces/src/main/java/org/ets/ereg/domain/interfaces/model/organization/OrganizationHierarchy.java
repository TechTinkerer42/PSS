package org.ets.ereg.domain.interfaces.model.organization;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationRelationshipId;

public interface OrganizationHierarchy extends Serializable {
	OrganizationRelationshipId getId();
	void setId(OrganizationRelationshipId organizationRelationshipId);
	Organization getOrganization1();
	void setOrganization1(Organization organization1);
	Organization getOrganization2();
	void setOrganization2(Organization organization2);
}
