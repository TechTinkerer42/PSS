package org.ets.ereg.domain.interfaces.model.organization;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationLinkageId;

public interface OrganizationLinkage extends Serializable {
	OrganizationLinkageId getId();
	void setId(OrganizationLinkageId id);
	String getLinkageKey();
	void setLinkageKey(String linkageKey);
	Organization getOrganization();
	void setCustomer(Organization organization);
	LinkageType getLinkageType();
	void setLinkageType(LinkageType linkageType);
}
