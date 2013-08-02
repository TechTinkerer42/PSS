package org.ets.ereg.domain.interfaces.model.common;

import org.broadleafcommerce.profile.core.domain.Phone;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationContact;

public interface ETSPhone extends Phone {
	public static final String PrimaryPhoneName = "primary";
	public static final String AlternatePhoneName = "alternate";
	
	public PhoneType getPhoneType();
	public void setPhoneType(PhoneType phoneType);
	public String getPhoneExtension();
	public void setPhoneExtension(String phoneExtension);
	public ETSCountry getCountry();
	public void setCountry(ETSCountry country);
	public OrganizationContact getOrganizationContact();
	public void setOrganizationContact(OrganizationContact orgContact);
}
