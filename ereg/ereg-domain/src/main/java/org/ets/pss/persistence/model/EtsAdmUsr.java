package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ETS_ADM_USR database table.
 * 
 */
@Entity
@Table(name="ETS_ADM_USR")
public class EtsAdmUsr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ETS_ADM_USR_ADMINUSERID_GENERATOR", sequenceName="SEQ_ETS_ADM_USR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ETS_ADM_USR_ADMINUSERID_GENERATOR")
	@Column(name="ADMIN_USER_ID", nullable=false, precision=19)
	private long adminUserId;

	@Column(name="ADM_IDFN_TXT", length=50)
	private String admIdfnTxt;

	@Temporal(TemporalType.DATE)
	@Column(name="BRTH_DTE")
	private Date brthDte;

	@Column(name="CHALLENGE_ANSWER", length=255)
	private String challengeAnswer;

	@Column(name="CHALLENGE_QUESTION_ID", precision=19)
	private BigDecimal challengeQuestionId;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="FIRST_NAME", length=30)
	private String firstName;

	@Column(name="GNDR_CDE", length=5)
	private String gndrCde;

	@Column(name="INTRNL_USR_FLG", nullable=false, length=1)
	private String intrnlUsrFlg;

	@Column(name="LAST_NAME", length=50)
	private String lastName;

	@Column(name="LDAP_GUID_ID", length=40)
	private String ldapGuidId;

	@Column(name="MID_NAM", length=50)
	private String midNam;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to CrCmnt
	@OneToMany(mappedBy="etsAdmUsr")
	private Set<CrCmnt> crCmnts;

	//bi-directional one-to-one association to BlcAdminUser
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADMIN_USER_ID", nullable=false, insertable=false, updatable=false)
	private BlcAdminUser blcAdminUser;

	public EtsAdmUsr() {
	}

	public long getAdminUserId() {
		return this.adminUserId;
	}

	public void setAdminUserId(long adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getAdmIdfnTxt() {
		return this.admIdfnTxt;
	}

	public void setAdmIdfnTxt(String admIdfnTxt) {
		this.admIdfnTxt = admIdfnTxt;
	}

	public Date getBrthDte() {
		return this.brthDte;
	}

	public void setBrthDte(Date brthDte) {
		this.brthDte = brthDte;
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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGndrCde() {
		return this.gndrCde;
	}

	public void setGndrCde(String gndrCde) {
		this.gndrCde = gndrCde;
	}

	public String getIntrnlUsrFlg() {
		return this.intrnlUsrFlg;
	}

	public void setIntrnlUsrFlg(String intrnlUsrFlg) {
		this.intrnlUsrFlg = intrnlUsrFlg;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLdapGuidId() {
		return this.ldapGuidId;
	}

	public void setLdapGuidId(String ldapGuidId) {
		this.ldapGuidId = ldapGuidId;
	}

	public String getMidNam() {
		return this.midNam;
	}

	public void setMidNam(String midNam) {
		this.midNam = midNam;
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

	public Set<CrCmnt> getCrCmnts() {
		return this.crCmnts;
	}

	public void setCrCmnts(Set<CrCmnt> crCmnts) {
		this.crCmnts = crCmnts;
	}

	public CrCmnt addCrCmnt(CrCmnt crCmnt) {
		getCrCmnts().add(crCmnt);
		crCmnt.setEtsAdmUsr(this);

		return crCmnt;
	}

	public CrCmnt removeCrCmnt(CrCmnt crCmnt) {
		getCrCmnts().remove(crCmnt);
		crCmnt.setEtsAdmUsr(null);

		return crCmnt;
	}

	public BlcAdminUser getBlcAdminUser() {
		return this.blcAdminUser;
	}

	public void setBlcAdminUser(BlcAdminUser blcAdminUser) {
		this.blcAdminUser = blcAdminUser;
	}

}