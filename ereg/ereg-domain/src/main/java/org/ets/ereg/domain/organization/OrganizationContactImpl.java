package org.ets.ereg.domain.organization;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.common.ETSPhoneImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationContact;

@Entity
@Table(name = "ORG_CNTCT")
public class OrganizationContactImpl implements OrganizationContact {
	
	private static final long serialVersionUID = 1L;
		
    @Id
    @GeneratedValue(generator = "OrganizationContactId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "OrganizationContactId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "OrganizationContactImpl", allocationSize = 50)
    @Column(name = "ORG_CNTCT_ID")
    protected Long id;
    
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=OrganizationImpl.class)
	@JoinColumn(name = "ORG_ID_NO")
	private Organization organization;
	
	@Column(name="FIRST_NAME", nullable=false, length=30)
	private String firstName;
	
	@Column(name="MID_NAM", nullable=true, length=50)
	private String middleName;
	
	@Column(name="LAST_NAME", nullable=false, length=50)
	private String lastName;
	
	@Column(name="TTL_NAM", nullable=true, length=50)
	private String titleName;
	
	@Column(name="EMAIL_ADDRESS", nullable=true, length=70)
	private String emailAddress;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity=ETSPhoneImpl.class, optional=true, cascade={CascadeType.ALL})
	@JoinColumn(name = "PHONE_ID", nullable=true)
	private ETSPhone etsPhone;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getMiddleName() {
		return middleName;
	}

	@Override
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getTitleName() {
		return titleName;
	}

	@Override
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public ETSPhone getETSPhone() {
		return etsPhone;
	}

	@Override
	public void setETSPhone(ETSPhone etsPhone) {
		this.etsPhone = etsPhone;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrganizationContactImpl) {
			final OrganizationContactImpl other = (OrganizationContactImpl) obj;
			return new EqualsBuilder().append(id, other.id).isEquals();
		} else {
			return false;
		}
	}

}
