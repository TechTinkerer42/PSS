package org.ets.ereg.domain.organization;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.LinkageTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationLinkage;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationLinkageId;

@Entity
@Table(name = "ORG_LNKG")
public class OrganizationLinkageImpl implements OrganizationLinkage {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrganizationLinkageId organizationLinkageId = new OrganizationLinkageId();
	
	@Column(name="LNKG_KY", nullable=false, length=100)
	private String linkageKey;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=OrganizationImpl.class)
	@JoinColumn(name = "ORG_ID_NO", nullable = false, insertable = false, updatable = false)
	private Organization organization; 
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=LinkageTypeImpl.class)
	@JoinColumn(name = "LNKG_TYP_CDE", nullable = false, insertable = false, updatable = false)
	private LinkageType linkageType;
	
	@Override
	public OrganizationLinkageId getId() {
		return organizationLinkageId;
	}

	@Override
	public void setId(OrganizationLinkageId id) {
		this.organizationLinkageId=id;
	}

	@Override
	public String getLinkageKey() {
		return linkageKey;
	}

	@Override
	public void setLinkageKey(String linkageKey) {
		this.linkageKey = linkageKey;
	}

	@Override
	public Organization getOrganization() {
		return organization;
	}

	@Override
	public void setCustomer(Organization organization) {
		this.organization = organization;
	}

	@Override
	public LinkageType getLinkageType() {
		return linkageType;
	}

	@Override
	public void setLinkageType(LinkageType linkageType) {
		this.linkageType = linkageType;
	}

}
