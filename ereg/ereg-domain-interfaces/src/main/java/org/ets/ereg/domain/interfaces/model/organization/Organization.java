package org.ets.ereg.domain.interfaces.model.organization;

import java.io.Serializable;
import java.util.Set;

import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;

public interface Organization extends Serializable {
	Long getId();
	void setId(Long id);
	
	OrganizationRoleType getRoleType();
	void setRoleType(OrganizationRoleType roleType);
	
	String getName();
	void setName(String name);

	String getWebUrl();
	void setWebUrl(String webUrl);
	
	Boolean isActive();
	void setActive(Boolean isActive);
	
	public Set<OrganizationAddress> getOrganizationAddresses();
	public void setOrganizationAddresses(Set<OrganizationAddress> orgAddresses);
	
	Set<OrganizationPhone> getOrganizationPhones();
	
	public Set<OrganizationContact> getOrganizationContacts();
	public void setOrganizationContacts(Set<OrganizationContact> orgContacts);
	
	EregHierarchy getEregHierarchy();
	void setEregHierarchy(EregHierarchy eregHierarchy);
}
