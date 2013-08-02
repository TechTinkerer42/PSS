package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the CUST_CR_ASGND_TSK_DOC database table.
 * 
 */
@Entity
@Table(name="CUST_CR_ASGND_TSK_DOC")
public class CustCrAsgndTskDoc implements Serializable {


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CustCrAsgndTskDocPK id;

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

	//bi-directional many-to-one association to AsgndTskDoc
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOC_ID", nullable=false, insertable=false, updatable = false)
	private Doc doc;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CUSTOMER_ID", referencedColumnName="CUSTOMER_ID", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TSK_ID", referencedColumnName="TSK_ID", nullable=false, insertable=false, updatable=false)
		})
	private AsgndTsk asgndTsk;

	//bi-directional many-to-one association to CustCr
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CUSTOMER_ID", referencedColumnName="CUSTOMER_ID", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="PRMPT_ID", referencedColumnName="PRMPT_ID", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TSK_ID", referencedColumnName="TSK_ID", nullable=false, insertable=false, updatable=false)
		})
	private CustCr custCr;

	public CustCrAsgndTskDoc() {
	}

	public CustCrAsgndTskDocPK getId() {
		return this.id;
	}

	public void setId(CustCrAsgndTskDocPK id) {
		this.id = id;
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

	public Doc getDoc() {
		return this.doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public CustCr getCustCr() {
		return this.custCr;
	}

	public void setCustCr(CustCr custCr) {
		this.custCr = custCr;
	}
	
	/**
	 * @return the asgndTsk
	 */
	public AsgndTsk getAsgndTsk() {
		return asgndTsk;
	}

	/**
	 * @param asgndTsk the asgndTsk to set
	 */
	public void setAsgndTsk(AsgndTsk asgndTsk) {
		this.asgndTsk = asgndTsk;
	}



}