package org.ets.ereg.domain.interfaces.model.profile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.Gender;

public interface ETSAdminUser extends AdminUser,Serializable {
	public void setFirstName(String firstName);
	public String getFirstName();
	public void setLastName(String lastName);
    public String getLastName();
    public List<TestCenterAdmin> getTestCenters();
    public void setTestCenters(List<TestCenterAdmin> testCenters);
    public String getMiddleName();
    public void setMiddleName(String middleName);
    String getPasswordConfirm();
    void setPasswordConfirm(String passwordConfirm);
    Date getDateOfBirth();
    void setDateOfBirth(Date dateOfBirth);
    Gender getGender();
    void setGender(Gender gender);
    String getOldPassword();
    void setOldPassword(String oldPassword);
    ChallengeQuestion getChallengeQuestion();
    void setChallengeQuestion(ChallengeQuestion challengeQuestion);
    String getChallengeAnswer();
    void setChallengeAnswer(String challengeAnswer);
    ETSPhone getEtsPhone();
    void setEtsPhone(ETSPhone etsPhone);
    AdminUserPhone getAdminUserPhone();
    void setAdminUserPhone(AdminUserPhone adminUserPhone);
    String getAdminIdentificationStr();
    void setAdminIdentificationStr(String adminIdentificationStr);
	String getLdapGUIDID();
	void setLdapGUIDID(String ldapGUIDID);
	Boolean getInternalUserFlag();
	void setInternalUserFlag(Boolean internalUserFlag);

}