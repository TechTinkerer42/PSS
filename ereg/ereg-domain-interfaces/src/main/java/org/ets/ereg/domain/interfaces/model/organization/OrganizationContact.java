package org.ets.ereg.domain.interfaces.model.organization;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.ETSPhone;

public interface OrganizationContact extends Serializable{
	Long getId();
	void setId(Long id);
	Organization getOrganization();
	void setOrganization(Organization organization);
	public void setFirstName(String firstName);
	public String getFirstName();
	public String getMiddleName();
	public void setMiddleName(String middleName);
	public void setLastName(String lastName);
    public String getLastName();
    public void setTitleName(String titleName);
    public String getTitleName();
    public String getEmailAddress();
    public void setEmailAddress(String emailAddress);
	ETSPhone getETSPhone();
	void setETSPhone(ETSPhone etsPhone);
}
