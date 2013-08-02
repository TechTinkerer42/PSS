package org.ets.ereg.domain.organization;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationHierarchy;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationRelationshipType;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationRelationshipId;

@Entity
@Table(name = "ORG_REL_HIER")
public class OrganizationHierarchyImpl implements OrganizationHierarchy {
	private static final long serialVersionUID = 1L;
	

	
	@EmbeddedId
	private OrganizationRelationshipId organizationRelationshipId = new OrganizationRelationshipId();
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=OrganizationImpl.class, optional=false)
	@JoinColumn(name = "ORG_ID_1_NO", nullable = false, insertable = false, updatable = false)
	private Organization organization1; 
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=OrganizationImpl.class, optional=false)
	@JoinColumn(name = "ORG_ID_2_NO", nullable = false, insertable = false, updatable = false)
	private Organization organization2;  
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = OrganizationRoleTypeImpl.class, optional=false)
	@JoinColumn(name="ORG_REL_TYP_CDE", nullable=false)
	private OrganizationRelationshipType relationshipType;
	
	@Override
	public OrganizationRelationshipId getId() {
		return organizationRelationshipId;
	}

	@Override
	public void setId(
			OrganizationRelationshipId organizationRelationshipId) {
		this.organizationRelationshipId = organizationRelationshipId;
	}

	public Organization getOrganization1() {
		return organization1;
	}

	public void setOrganization1(Organization organization1) {
		this.organization1 = organization1;
	}

	public Organization getOrganization2() {
		return organization2;
	}

	public void setOrganization2(Organization organization2) {
		this.organization2 = organization2;
	}

	public OrganizationRelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(OrganizationRelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}
	
	

}
