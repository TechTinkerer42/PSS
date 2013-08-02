package org.ets.ereg.domain.organization;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.HoldTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.HoldType;
import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationHoldStatus;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationHoldStatusId;

@Entity
@Table(name = "ORG_HLD_STS")
public class OrganizationHoldStatusImpl implements OrganizationHoldStatus,
		Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "holdTypeCode", column = @Column(name = "HLD_TYP_CDE", nullable = false, length = 5)),
			@AttributeOverride(name = "organizationId", column = @Column(name = "ORG_ID_NO", nullable = false)) })
	private OrganizationHoldStatusId organizationHoldStatusId = new OrganizationHoldStatusId();
	
	@Column(name="HLD_DTE", nullable=false)
	private Date holdDate;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=OrganizationImpl.class)
	@JoinColumn(name = "ORG_ID_NO", nullable = false, insertable = false, updatable = false)
	private Organization organization; 
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=HoldTypeImpl.class)
	@JoinColumn(name = "HLD_TYP_CDE", nullable = false, insertable = false, updatable = false)
	private HoldType holdType;
	

	@Override
	public OrganizationHoldStatusId getId() {
		return organizationHoldStatusId;
	}

	@Override
	public void setId(OrganizationHoldStatusId id) {
		organizationHoldStatusId = id;
	}

	@Override
	public Date getHoldDate() {
		return holdDate;
	}

	@Override
	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
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
	public HoldType getHoldType() {
		return holdType;
	}

	@Override
	public void setHoldType(HoldType holdType) {
		this.holdType  = holdType;
	}

}
