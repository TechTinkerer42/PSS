package org.ets.ereg.domain.interfaces.model.profile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.CustomerProgramInterest;
import org.ets.ereg.domain.interfaces.model.common.CustomerType;
import org.ets.ereg.domain.interfaces.model.common.EthnicityType;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;


public interface ETSCustomer extends Customer,Serializable {
	void setSourceId(String sourceId);
	String getSourceId();
	String getMiddleInitial();
	void setMiddleInitial(String middleInitial); 
	Date getDateOfBirth();
	void setDateOfBirth(Date dateOfBirth);
	Date getAggrementTimeStamp();
	void setAggrementTimeStamp(Date aggrementTimeStamp); 
	Boolean getMilitaryMemberShip(); 
	void setMilitaryMemberShip(Boolean militaryMemberShip); 
	String getSocialSecurity(); 
	void setSocialSecurity(String socialSecurity); 
	Gender getGender();
	void setGender(Gender gender);
	CustomerType getCustomerType();
	void setCustomerType(CustomerType customerType);
	EthnicityType getEthnicityType();
	void setEthnicityType(EthnicityType ethnicityType);
	MilitaryStatusType getMilitaryStatus();
	void setMilitaryStatus(MilitaryStatusType militaryStatus);
	LanguageType getPrmrySpkngLang();
	void setPrmrySpkngLang(LanguageType prmrySpkngLang);
	LanguageType getPrfrdTstTakingLang();
	void setPrfrdTstTakingLang(LanguageType prfrdTstTakingLang);
	List<CustomerLinkage> getCustomerLinkages();
	void setCustomerLinkages(List<CustomerLinkage> customerLinkages);
	List<CustomerProgramInterest> getCustomerProgramIntrst();
	void setCustomerProgramIntrst(List<CustomerProgramInterest> customerProgramIntrst);
	Boolean getTaxExmptFlag();
	void setTaxExmptFlag(Boolean taxExmptFlag);
	void setLdapGUIDID(String ldapGUIDID);
	String getLdapGUIDID();
    boolean isNameUpdated();
    void setNameUpdated(boolean nameUpdated);
	boolean isHasEligibleBatterySubscription();
	void setHasEligibleBatterySubscription(boolean hasEligibleBatterySubscription);
	boolean isHasBatteryInCart();
	void setHasBatteryInCart(boolean hasBatteryInCart);
    Boolean getInternalUserFlag();
	void setInternalUserFlag(Boolean internalUserFlag);	
}