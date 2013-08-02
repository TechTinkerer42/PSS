package org.ets.ereg.domain.profile;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.ets.ereg.domain.common.CustomerTypeImpl;
import org.ets.ereg.domain.common.EthnicityTypeImpl;
import org.ets.ereg.domain.common.GenderImpl;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.common.MilitaryStatusTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.CustomerProgramInterest;
import org.ets.ereg.domain.interfaces.model.common.CustomerType;
import org.ets.ereg.domain.interfaces.model.common.EthnicityType;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.hibernate.annotations.Type;



@Entity
@Table(name="ETS_CUST")
public class ETSCustomerImpl extends CustomerImpl implements ETSCustomer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="SRC_ID")
	private String sourceId;

	@Column(name="MID_NAM")
    private String middleInitial;
		
	@Temporal(TemporalType.DATE)
	@Column(name="BRTH_DTE")
	private Date dateOfBirth;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRMS_ACEPTNC_DTM")
	private Date aggrementTimeStamp;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = EthnicityTypeImpl.class, optional=true)
	@JoinColumn(name = "ETNCTY_CDE")
	private EthnicityType ethnicityType;
	
	@Column(name="TAX_EXMPT_FLG")
	@Type(type="yes_no")
	private Boolean taxExmptFlag = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = LanguageTypeImpl.class, optional=true)
	@JoinColumn(name = "PRMRY_SPKNG_LANG_CDE")
	private LanguageType prmrySpkngLang;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = LanguageTypeImpl.class, optional=true)
	@JoinColumn(name="PRFRD_TST_TAKING_LANG_CDE")
	private LanguageType prfrdTstTakingLang;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = MilitaryStatusTypeImpl.class, optional=true)
	@JoinColumn(name="MIL_STS_CDE")
    private MilitaryStatusType militaryStatus;

	@Transient
	private Boolean militaryMemberShip;

	@Column(name="SSN_LST_4",length=4)
	private String socialSecurity;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = GenderImpl.class, optional=true)
	@JoinColumn(name="GNDR_CDE")
	private Gender gender;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = CustomerTypeImpl.class, optional=false)
	@JoinColumn(name = "CUST_TYP_CDE")
	private CustomerType customerType;

	@OneToMany(mappedBy="linkageType",targetEntity=CustomerLinkageImpl.class)
	private List<CustomerLinkage> customerLinkages;
	@OneToMany(mappedBy="programType",targetEntity=CustomerProgramInterestImpl.class)
	private List<CustomerProgramInterest> customerProgramIntrst;

	@Column(name="LDAP_GUID_ID")
	private String ldapGUIDID;

    @Column(name="INTRNL_USR_FLG",  nullable = true, length = 1)
    @Type(type = "yes_no")
    private Boolean internalUserFlag; 
    
	@Transient
	private boolean nameUpdated = false;
	
	@Transient
	private boolean hasEligibleBatterySubscription = false;
	
	@Transient
	private boolean hasBatteryInCart = false;

	@Override
	public MilitaryStatusType getMilitaryStatus() {
		return militaryStatus;
	}
	@Override
	public void setMilitaryStatus(MilitaryStatusType militaryStatus) {
		this.militaryStatus = militaryStatus;
	}
	@Override
	public EthnicityType getEthnicityType() {
		return ethnicityType;
	}
	@Override
	public void setEthnicityType(EthnicityType ethnicityType) {
		this.ethnicityType = ethnicityType;
	}
	@Override
	public LanguageType getPrmrySpkngLang() {
		return prmrySpkngLang;
	}
	@Override
	public void setPrmrySpkngLang(LanguageType prmrySpkngLang) {
		this.prmrySpkngLang = prmrySpkngLang;
	}
	@Override
	public LanguageType getPrfrdTstTakingLang() {
		return prfrdTstTakingLang;
	}
	@Override
	public void setPrfrdTstTakingLang(LanguageType prfrdTstTakingLang) {
		this.prfrdTstTakingLang = prfrdTstTakingLang;
	}

	@Override
	public CustomerType getCustomerType() {
		return customerType;
	}
	@Override
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String getMiddleInitial() {
		return middleInitial;
	}

	@Override
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	@Override
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public void setSourceId(String sourceId) {
		this.sourceId=sourceId;
	}

	@Override
	public String getSourceId() {
		// TODO Auto-generated method stub
		return sourceId;
	}
	@Override
	public Date getAggrementTimeStamp() {
		return aggrementTimeStamp;
	}
	@Override
	public void setAggrementTimeStamp(Date aggrementTimeStamp) {
		this.aggrementTimeStamp = aggrementTimeStamp;
	}

	@Override
	public void setMilitaryMemberShip(Boolean militaryMemberShip) {
		this.militaryMemberShip = militaryMemberShip;
	}

	@Override
	public String getSocialSecurity() {
		return socialSecurity;
	}
	@Override
	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	@Override
	public List<CustomerLinkage> getCustomerLinkages() {
		return customerLinkages;
	}

	@Override
	public void setCustomerLinkages(List<CustomerLinkage> customerLinkages) {
		this.customerLinkages = customerLinkages;
	}
	@Override
	public List<CustomerProgramInterest> getCustomerProgramIntrst() {
		return customerProgramIntrst;
	}
	@Override
	public void setCustomerProgramIntrst(
			List<CustomerProgramInterest> customerProgramIntrst) {
		this.customerProgramIntrst = customerProgramIntrst;
	}

	@Override
	public void setTaxExmptFlag(Boolean taxExmptFlag) {
		this.taxExmptFlag = taxExmptFlag;
	}
	public Boolean getMilitaryMemberShip() {
		return militaryMemberShip;
	}
	public Boolean getTaxExmptFlag() {
		return taxExmptFlag;
	}

	@Override
	public String getLdapGUIDID() {
		return ldapGUIDID;
	}
	@Override
	public void setLdapGUIDID(String ldapGUIDID) {
		this.ldapGUIDID = ldapGUIDID;
	}

	@Override
    public boolean isNameUpdated() {
        return nameUpdated;
    }
	@Override
    public void setNameUpdated(boolean nameUpdated) {
        this.nameUpdated = nameUpdated;
    }
	@Override
	public Boolean getInternalUserFlag() {
		return internalUserFlag;
	}
	@Override
	public void setInternalUserFlag(Boolean internalUserFlag) {
		this.internalUserFlag = internalUserFlag;
	}
	
	@Override
	public boolean isHasEligibleBatterySubscription() {
		return hasEligibleBatterySubscription;
	}
	
	@Override
	public void setHasEligibleBatterySubscription(
			boolean hasEligibleBatterySubscription) {
		this.hasEligibleBatterySubscription = hasEligibleBatterySubscription;
	}
	
	@Override
	public boolean isHasBatteryInCart() {
		return hasBatteryInCart;
	}
	
	@Override
	public void setHasBatteryInCart(boolean hasBatteryInCart) {
		this.hasBatteryInCart = hasBatteryInCart;
	}
	
	
	
	
}
