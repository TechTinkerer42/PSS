package org.ets.ereg.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.broadleafcommerce.profile.core.domain.PhoneImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationContact;
import org.ets.ereg.domain.organization.OrganizationContactImpl;

@Entity
@Table(name="ETS_PHONE")
public class ETSPhoneImpl extends PhoneImpl implements ETSPhone {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = PhoneTypeImpl.class, optional=true)
	@JoinColumn(name="PHONE_TYP_CDE")
	private PhoneType phoneType;
	
	@Column(name="TEL_EXTNSN_NO")
    private String phoneExtension; //extension
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = ETSCountryImpl.class, optional=true)
	@JoinColumn(name="CNTRY_CDE")
	private ETSCountry country;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity=OrganizationContactImpl.class, optional=true)
	@JoinColumn(name = "PHONE_ID", nullable=true)
	private OrganizationContact organizationContact;
	
	
	@Override
	public PhoneType getPhoneType() {
		return phoneType;
	}
	@Override
	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}
	@Override
	public ETSCountry getCountry() {
		return country;
	}
	@Override
	public void setCountry(ETSCountry country) {
		this.country = country;
	}

	@Override
	public String getPhoneExtension() {
		return phoneExtension;
	}

	@Override
	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}
	
	@Override
	public OrganizationContact getOrganizationContact() {
		return organizationContact;
	}
	
	@Override
	public void setOrganizationContact(OrganizationContact organizationContact) {
		this.organizationContact = organizationContact;
	}
	
	
}
