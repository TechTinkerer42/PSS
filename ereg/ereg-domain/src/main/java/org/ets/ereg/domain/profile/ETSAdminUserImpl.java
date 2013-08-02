package org.ets.ereg.domain.profile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUserImpl;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestionImpl;
import org.ets.ereg.domain.common.GenderImpl;
import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.profile.admin.AdminUserPhoneImpl;
import org.ets.ereg.domain.profile.admin.TestCenterAdminImpl;
import org.hibernate.annotations.Type;

    @Entity
    @Table(name="ETS_ADM_USR")
    public class ETSAdminUserImpl extends AdminUserImpl implements ETSAdminUser, Serializable {

        /**
         * Default constructor for setting the name attribute for AdminUserImpl class as it's not a nullable field.
         */
        public ETSAdminUserImpl() {
            super.setName(this.firstName + " " + this.middleName + " " + this.lastName);
        }
        private static final long serialVersionUID = 1L;

        @Column(name="FIRST_NAME", nullable = true)
        private String firstName;

        @Column(name="MID_NAM", nullable = true)
        private String middleName;

        @Column(name="LAST_NAME", nullable = true)
        private String lastName;

        @Temporal(TemporalType.DATE)
        @Column(name="BRTH_DTE")
        private Date dateOfBirth;

        @ManyToOne(fetch = FetchType.EAGER, targetEntity = GenderImpl.class, optional=true)
        @JoinColumn(name="GNDR_CDE")
        private Gender gender;

        @OneToMany(mappedBy="etsAdminUser", targetEntity=TestCenterAdminImpl.class)
        private List<TestCenterAdmin> testCenters;

        @ManyToOne(fetch = FetchType.EAGER, targetEntity=ChallengeQuestionImpl.class)
        @JoinColumn(name="CHALLENGE_QUESTION_ID")
        private ChallengeQuestion challengeQuestion;

        @OneToOne(mappedBy="etsAdminUser", fetch = FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=AdminUserPhoneImpl.class)
        private AdminUserPhone adminUserPhone;

        @Column(name="CHALLENGE_ANSWER")
        private String challengeAnswer;

        @Column(name="ADM_IDFN_TXT")
        private String adminIdentificationStr;

        @Column(name="LDAP_GUID_ID")
        private String ldapGUIDID;
        
        @Column(name="INTRNL_USR_FLG",  nullable = true)
        @Type(type = "yes_no")
        private Boolean internalUserFlag;        

        @Transient
        private ETSPhone etsPhone;

        @Transient
        private String passwordConfirm;

        @Transient
        private String oldPassword;


        @Override
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @Override
        public String getFirstName() {
            return firstName;
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
        public void setLastName(String lastName) {
            this.lastName = lastName;

        }

        @Override
        public String getLastName() {
           return lastName;
        }

        @Override
        public List<TestCenterAdmin> getTestCenters() {
            return testCenters;
        }

        @Override
        public void setTestCenters(List<TestCenterAdmin> testCenters) {
            this.testCenters = testCenters;
        }

        @Override
        public String getPasswordConfirm() {
            return passwordConfirm;
        }

        @Override
        public void setPasswordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
        }

        @Override
        public Date getDateOfBirth() {
            return dateOfBirth != null ? (Date) dateOfBirth.clone(): null;
        }

        @Override
        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth != null ? (Date) dateOfBirth.clone(): null;
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
        public String getOldPassword() {
            return oldPassword;
        }

        @Override
        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        @Override
        public ChallengeQuestion getChallengeQuestion() {
            return challengeQuestion;
        }

        @Override
        public void setChallengeQuestion(ChallengeQuestion challengeQuestion) {
            this.challengeQuestion = challengeQuestion;
        }

        @Override
        public String getChallengeAnswer() {
            return challengeAnswer;
        }

        @Override
        public void setChallengeAnswer(String challengeAnswer) {
            this.challengeAnswer = challengeAnswer;
        }

        @Override
        public ETSPhone getEtsPhone() {
            return etsPhone;
        }

        @Override
        public void setEtsPhone(ETSPhone etsPhone) {
            this.etsPhone = etsPhone;
        }

        @Override
        public AdminUserPhone getAdminUserPhone() {
            return adminUserPhone;
        }

        @Override
        public void setAdminUserPhone(AdminUserPhone adminUserPhone) {
            this.adminUserPhone = adminUserPhone;
        }

        @Override
        public String getAdminIdentificationStr() {
            return adminIdentificationStr;
        }

        @Override
        public void setAdminIdentificationStr(String adminIdentificationStr) {
            this.adminIdentificationStr = adminIdentificationStr;
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
		public Boolean getInternalUserFlag() {
			return internalUserFlag;
		}
        @Override
		public void setInternalUserFlag(Boolean internalUserFlag) {
			this.internalUserFlag = internalUserFlag;
		}

		/*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(firstName).append(middleName).append(lastName).append(dateOfBirth).append(gender)
                    .toHashCode();
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof ETSAdminUserImpl) {
                final ETSAdminUserImpl other = (ETSAdminUserImpl) obj;
                return new EqualsBuilder().append(firstName, other.firstName)
                        .append(middleName, other.middleName).append(lastName, other.lastName)
                        .append(dateOfBirth, other.dateOfBirth)
                        .append(gender, other.gender).isEquals();
            } else {
                return false;
            }
        }
}