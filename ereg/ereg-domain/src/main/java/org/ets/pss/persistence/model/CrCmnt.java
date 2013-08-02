package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the CR_CMNT database table.
 * 
 */
@Entity
@Table(name="CR_CMNT")
public class CrCmnt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CrCmntPK id;

	@Column(name="CR_CMNT_TXT", nullable=false, length=2000)
	private String crCmntTxt;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional one-to-one association to CustCr
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CUSTOMER_ID", referencedColumnName="CUSTOMER_ID", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="PRMPT_ID", referencedColumnName="PRMPT_ID", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TSK_ID", referencedColumnName="TSK_ID", nullable=false, insertable=false, updatable=false)
		})
	private CustCr custCr;

	//bi-directional many-to-one association to EtsAdmUsr
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CMNT_BY_ADMIN_USR_ID")
	private EtsAdmUsr etsAdmUsr;

	public CrCmnt() {
	}

	public CrCmntPK getId() {
		return this.id;
	}

	public void setId(CrCmntPK id) {
		this.id = id;
	}

	public String getCrCmntTxt() {
		return this.crCmntTxt;
	}

	public void setCrCmntTxt(String crCmntTxt) {
		this.crCmntTxt = crCmntTxt;
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

	public CustCr getCustCr() {
		return this.custCr;
	}

	public void setCustCr(CustCr custCr) {
		this.custCr = custCr;
	}

	public EtsAdmUsr getEtsAdmUsr() {
		return this.etsAdmUsr;
	}

	public void setEtsAdmUsr(EtsAdmUsr etsAdmUsr) {
		this.etsAdmUsr = etsAdmUsr;
	}

}