package org.ets.ereg.domain.interfaces.model.organization;

import java.io.Serializable;

import org.broadleafcommerce.profile.core.domain.Phone;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationPhoneId;

public interface OrganizationPhone extends Serializable{
	OrganizationPhoneId getId();
	void setId(OrganizationPhoneId id);
	Organization getOrganization();
	void setOrganization(Organization organization);
	ETSPhone getPhone();
	void setPhone(ETSPhone phone);
}
