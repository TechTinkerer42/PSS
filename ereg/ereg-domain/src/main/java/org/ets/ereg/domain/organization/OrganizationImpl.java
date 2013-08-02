package org.ets.ereg.domain.organization;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import org.ets.ereg.domain.hierarchy.EregHierarchyImpl;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationAddress;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationContact;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationPhone;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationRoleType;
import org.hibernate.annotations.Type;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ORG")
public class OrganizationImpl implements Organization {

    private static final long serialVersionUID = 1L;    

    @Id
    @GeneratedValue(generator = "OrganizationId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "OrganizationId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "OrganizationImpl", allocationSize = 50)
    @Column(name = "ORG_ID_NO")
    protected Long id;
   
    @OneToOne(fetch=FetchType.LAZY,targetEntity=EregHierarchyImpl.class)
    @JoinColumn(name="EREG_HIER_ID")
    private EregHierarchy eregHierarchy;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = OrganizationRoleTypeImpl.class, optional=false)
	@JoinColumn(name="ORG_RLE_TYP_CDE", nullable=false)
	private OrganizationRoleType roleType;
    
    @Column(name="ORG_NAM", nullable=true, length=80)
    protected String name;
     
    @Column(name="WEB_URL", nullable=true, length=100)
    protected String webUrl;
    
    @Column(name = "ACTV_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
    @Type(type="yes_no")
    private Boolean isActive;
    
    @OneToMany(mappedBy="organization", targetEntity=OrganizationAddressImpl.class, fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    private Set<OrganizationAddress> organizationAddresses;
    
    @OneToMany(mappedBy="organization", targetEntity=OrganizationContactImpl.class, fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    private Set<OrganizationContact> organizationContacts;
    
    @OneToMany(mappedBy="organization", targetEntity=OrganizationPhoneImpl.class, fetch=FetchType.EAGER)
    private Set<OrganizationPhone> organizationPhones;
    
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public OrganizationRoleType getRoleType(){
		return roleType;
	}
	
	@Override
	public void setRoleType(OrganizationRoleType roleType){
		this.roleType = roleType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getWebUrl() {
		return webUrl;
	}

	@Override
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	@Override
	public Boolean isActive() {
		return isActive;
	}

	@Override
	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public Set<OrganizationAddress> getOrganizationAddresses() {
		return organizationAddresses;
	}

	@Override
	public void setOrganizationAddresses(Set<OrganizationAddress> orgAddresses) {
		this.organizationAddresses = orgAddresses ;
	}
	
	@Override
	public Set<OrganizationContact> getOrganizationContacts() {
		return organizationContacts;
	}
	
	@Override
	public void setOrganizationContacts(Set<OrganizationContact> orgContacts) {
		this.organizationContacts = orgContacts ;
	}
	
	@Override
	public Set<OrganizationPhone> getOrganizationPhones() {
		return organizationPhones;
	}
	
	@Override
	public EregHierarchy getEregHierarchy() {
		return eregHierarchy;
	}
	@Override
	public void setEregHierarchy(EregHierarchy eregHierarchy) {
		this.eregHierarchy = eregHierarchy;
	}

}
