package org.ets.ereg.domain.organization;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.ets.ereg.domain.common.ETSAddressImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationAddress;
import org.ets.ereg.domain.interfaces.model.organization.id.OrganizationAddressId;

@Entity
@Table(name = "ORG_ADR")
public class OrganizationAddressImpl implements OrganizationAddress,
		Serializable {
	private static final long serialVersionUID = 1L;
		
	@EmbeddedId    
	@AttributeOverrides( {
    @AttributeOverride(name="orgId", column=@Column(name="ORG_ID_NO", nullable=false) ), 
    @AttributeOverride(name="addressId", column=@Column(name="ADDRESS_ID", nullable=false) ) } )
	private OrganizationAddressId organizationAddressId = new OrganizationAddressId();
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=OrganizationImpl.class, optional=false)
	@JoinColumn(name = "ORG_ID_NO", nullable = false, insertable = false, updatable = false)
	private Organization organization; 
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=ETSAddressImpl.class, optional=false, cascade={CascadeType.ALL})
	@JoinColumn(name = "ADDRESS_ID", nullable = false, insertable = false, updatable = false)
	private ETSAddress address; 
	
	@Override
	public OrganizationAddressId getId() {
		return organizationAddressId;
	}

	@Override
	public void setId(OrganizationAddressId id) {
		this.organizationAddressId = id;
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
	public ETSAddress getAddress() {
		return address;
	}

	@Override
	public void setAddress(ETSAddress address) {
		this.address = address;
	}
}
