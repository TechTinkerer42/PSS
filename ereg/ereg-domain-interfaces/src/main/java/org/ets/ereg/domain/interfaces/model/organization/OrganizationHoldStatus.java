package org.ets.ereg.domain.interfaces.model.organization;

import java.io.Serializable;
import java.util.Date;

import org.ets.ereg.domain.interfaces.model.common.HoldType;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationHoldStatusId;

public interface OrganizationHoldStatus extends Serializable{
	OrganizationHoldStatusId getId();
	void setId(OrganizationHoldStatusId id);
	Date getHoldDate();
	void setHoldDate(Date holdDate);
	Organization getOrganization();
	void setOrganization(Organization organization);
	HoldType getHoldType();
	void setHoldType(HoldType holdType);
}
