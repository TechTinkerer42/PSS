package org.ets.ereg.domain.interfaces.model.organization;

import java.io.Serializable;



import org.broadleafcommerce.profile.core.domain.Address;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationAddressId;

public interface OrganizationAddress extends Serializable {
	OrganizationAddressId getId();
	void setId(OrganizationAddressId id);
	Organization getOrganization();
	void setOrganization(Organization organization);
	ETSAddress getAddress();
	void setAddress(ETSAddress address);
}
