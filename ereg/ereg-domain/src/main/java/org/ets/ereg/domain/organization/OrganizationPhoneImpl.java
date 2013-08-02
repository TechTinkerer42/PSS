package org.ets.ereg.domain.organization;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.ETSPhoneImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationPhone;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationPhoneId;

@Entity
@Table(name = "ORG_PHONE")
public class OrganizationPhoneImpl implements OrganizationPhone, Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	private OrganizationPhoneId organizationPhoneId = new OrganizationPhoneId();
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=OrganizationImpl.class, optional=false)
	@JoinColumn(name = "ORG_ID_NO", nullable = false, insertable = false, updatable = false)
	private Organization organization; 
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=ETSPhoneImpl.class, optional=false)
	@JoinColumn(name = "PHONE_ID", nullable = false, insertable = false, updatable = false)
	private ETSPhone phone; 
	
	@Override
	public OrganizationPhoneId getId() {
		return organizationPhoneId;
	}

	@Override
	public void setId(OrganizationPhoneId id) {
		this.organizationPhoneId = id;
	}

	@Override
	public Organization getOrganization() {
		return organization;
	}

	@Override
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public ETSPhone getPhone() {
		return phone;
	}

	@Override
	public void setPhone(ETSPhone phone) {
		this.phone = phone;
	}

}
