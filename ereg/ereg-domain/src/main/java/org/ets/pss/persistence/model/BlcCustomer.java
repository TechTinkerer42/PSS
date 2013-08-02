package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the BLC_CUSTOMER database table.
 * 
 */
@Entity
@Table(name="BLC_CUSTOMER")
public class BlcCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="BLC_CUSTOMER_CUSTOMERID_GENERATOR", sequenceName="SEQ_BLC_CUSTOMER")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BLC_CUSTOMER_CUSTOMERID_GENERATOR")
	@Column(name="CUSTOMER_ID", nullable=false, precision=19)
	private long customerId;

	@Column(name="CHALLENGE_ANSWER", length=255)
	private String challengeAnswer;

	@Column(name="CHALLENGE_QUESTION_ID", precision=19)
	private BigDecimal challengeQuestionId;

	@Column(name="CREATED_BY", precision=19)
	private BigDecimal createdBy;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(precision=1)
	private BigDecimal deactivated;

	@Column(name="EMAIL_ADDRESS", length=100)
	private String emailAddress;

	@Column(name="FIRST_NAME", length=30)
	private String firstName;

	@Column(name="IS_REGISTERED", precision=1)
	private BigDecimal isRegistered;

	@Column(name="LAST_NAME", length=50)
	private String lastName;

	@Column(name="LOCALE_CODE", length=255)
	private String localeCode;

	@Column(length=255)
	private String password;

	@Column(name="PASSWORD_CHANGE_REQUIRED", precision=1)
	private BigDecimal passwordChangeRequired;

	@Column(name="RECEIVE_EMAIL", precision=1)
	private BigDecimal receiveEmail;

	@Column(name="UPDATED_BY", precision=19)
	private BigDecimal updatedBy;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	@Column(name="USER_NAME", length=255)
	private String userName;

	//bi-directional one-to-one association to EtsCust
	@OneToOne(mappedBy="blcCustomer", fetch=FetchType.LAZY)
	private EtsCust etsCust;

	public BlcCustomer() {
	}

	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getChallengeAnswer() {
		return this.challengeAnswer;
	}

	public void setChallengeAnswer(String challengeAnswer) {
		this.challengeAnswer = challengeAnswer;
	}

	public BigDecimal getChallengeQuestionId() {
		return this.challengeQuestionId;
	}

	public void setChallengeQuestionId(BigDecimal challengeQuestionId) {
		this.challengeQuestionId = challengeQuestionId;
	}

	public BigDecimal getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getCreatedByAdminUsrId() {
		return this.createdByAdminUsrId;
	}

	public void setCreatedByAdminUsrId(BigDecimal createdByAdminUsrId) {
		this.createdByAdminUsrId = createdByAdminUsrId;
	}

	public BigDecimal getCreatedByCustomerId() {
		return this.createdByCustomerId;
	}

	public void setCreatedByCustomerId(BigDecimal createdByCustomerId) {
		this.createdByCustomerId = createdByCustomerId;
	}

	public Timestamp getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public BigDecimal getDeactivated() {
		return this.deactivated;
	}

	public void setDeactivated(BigDecimal deactivated) {
		this.deactivated = deactivated;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public BigDecimal getIsRegistered() {
		return this.isRegistered;
	}

	public void setIsRegistered(BigDecimal isRegistered) {
		this.isRegistered = isRegistered;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocaleCode() {
		return this.localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getPasswordChangeRequired() {
		return this.passwordChangeRequired;
	}

	public void setPasswordChangeRequired(BigDecimal passwordChangeRequired) {
		this.passwordChangeRequired = passwordChangeRequired;
	}

	public BigDecimal getReceiveEmail() {
		return this.receiveEmail;
	}

	public void setReceiveEmail(BigDecimal receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	public BigDecimal getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public BigDecimal getUpdatedByAdminUsrId() {
		return this.updatedByAdminUsrId;
	}

	public void setUpdatedByAdminUsrId(BigDecimal updatedByAdminUsrId) {
		this.updatedByAdminUsrId = updatedByAdminUsrId;
	}

	public BigDecimal getUpdatedByCustomerId() {
		return this.updatedByCustomerId;
	}

	public void setUpdatedByCustomerId(BigDecimal updatedByCustomerId) {
		this.updatedByCustomerId = updatedByCustomerId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public EtsCust getEtsCust() {
		return this.etsCust;
	}

	public void setEtsCust(EtsCust etsCust) {
		this.etsCust = etsCust;
	}

}