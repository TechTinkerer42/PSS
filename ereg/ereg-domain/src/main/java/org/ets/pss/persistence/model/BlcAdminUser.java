package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the BLC_ADMIN_USER database table.
 * 
 */
@Entity
@Table(name="BLC_ADMIN_USER")
public class BlcAdminUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BLC_ADMIN_USER_ADMINUSERID_GENERATOR", sequenceName="SEQ_BLC_ADMIN_USER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BLC_ADMIN_USER_ADMINUSERID_GENERATOR")
	@Column(name="ADMIN_USER_ID", nullable=false, precision=19)
	private long adminUserId;

	@Column(name="ACTIVE_STATUS_FLAG", precision=1)
	private BigDecimal activeStatusFlag;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(nullable=false, length=255)
	private String email;

	@Column(nullable=false, length=255)
	private String login;

	@Column(nullable=false, length=255)
	private String name;

	@Column(nullable=false, length=255)
	private String password;

	@Column(name="PHONE_NUMBER", length=20)
	private String phoneNumber;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional one-to-one association to EtsAdmUsr
	@OneToOne(mappedBy="blcAdminUser", fetch=FetchType.LAZY)
	private EtsAdmUsr etsAdmUsr;

	public BlcAdminUser() {
	}

	public long getAdminUserId() {
		return this.adminUserId;
	}

	public void setAdminUserId(long adminUserId) {
		this.adminUserId = adminUserId;
	}

	public BigDecimal getActiveStatusFlag() {
		return this.activeStatusFlag;
	}

	public void setActiveStatusFlag(BigDecimal activeStatusFlag) {
		this.activeStatusFlag = activeStatusFlag;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public EtsAdmUsr getEtsAdmUsr() {
		return this.etsAdmUsr;
	}

	public void setEtsAdmUsr(EtsAdmUsr etsAdmUsr) {
		this.etsAdmUsr = etsAdmUsr;
	}

}