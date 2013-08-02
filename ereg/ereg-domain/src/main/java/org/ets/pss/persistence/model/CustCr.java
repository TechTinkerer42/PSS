package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the CUST_CR database table.
 * 
 */
@Entity
@Table(name="CUST_CR")
public class CustCr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CustCrPK id;

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

	//bi-directional many-to-one association to CrBlb
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE },mappedBy="custCr")
	private Set<CrBlb> crBlbs;

	//bi-directional one-to-one association to CrCmnt
	/*@OneToOne(mappedBy="custCr", fetch=FetchType.LAZY)
	private CrCmnt crCmnt;*/

	//bi-directional many-to-one association to EtsCust
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID", nullable=false, insertable=false, updatable=false)
	private EtsCust etsCust;

	//bi-directional many-to-one association to Prmpt
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRMPT_ID", nullable=false, insertable=false, updatable=false)
	private Prompt prmpt;

	//bi-directional many-to-one association to Tsk
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TSK_ID", nullable=false, insertable=false, updatable=false)
	private Task tsk;

	//bi-directional many-to-one association to CustCrAsgndTskDoc
	@OneToMany(mappedBy="custCr")
	private Set<CustCrAsgndTskDoc> custCrAsgndTskDocs;

	public CustCr() {
	}

	public CustCrPK getId() {
		return this.id;
	}

	public void setId(CustCrPK id) {
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

	public Set<CrBlb> getCrBlbs() {
		return this.crBlbs;
	}

	public void setCrBlbs(Set<CrBlb> crBlbs) {
		this.crBlbs = crBlbs;
	}

	public CrBlb addCrBlb(CrBlb crBlb) {
		getCrBlbs().add(crBlb);
		crBlb.setCustCr(this);

		return crBlb;
	}

	public CrBlb removeCrBlb(CrBlb crBlb) {
		getCrBlbs().remove(crBlb);
		crBlb.setCustCr(null);

		return crBlb;
	}

/*	public CrCmnt getCrCmnt() {
		return this.crCmnt;
	}

	public void setCrCmnt(CrCmnt crCmnt) {
		this.crCmnt = crCmnt;
	}*/

	public EtsCust getEtsCust() {
		return this.etsCust;
	}

	public void setEtsCust(EtsCust etsCust) {
		this.etsCust = etsCust;
	}

	public Prompt getPrmpt() {
		return this.prmpt;
	}

	public void setPrmpt(Prompt prmpt) {
		this.prmpt = prmpt;
	}

	public Task getTsk() {
		return this.tsk;
	}

	public void setTsk(Task tsk) {
		this.tsk = tsk;
	}

	public Set<CustCrAsgndTskDoc> getCustCrAsgndTskDocs() {
		return this.custCrAsgndTskDocs;
	}

	public void setCustCrAsgndTskDocs(Set<CustCrAsgndTskDoc> custCrAsgndTskDocs) {
		this.custCrAsgndTskDocs = custCrAsgndTskDocs;
	}

	public CustCrAsgndTskDoc addCustCrAsgndTskDoc(CustCrAsgndTskDoc custCrAsgndTskDoc) {
		getCustCrAsgndTskDocs().add(custCrAsgndTskDoc);
		custCrAsgndTskDoc.setCustCr(this);

		return custCrAsgndTskDoc;
	}

	public CustCrAsgndTskDoc removeCustCrAsgndTskDoc(CustCrAsgndTskDoc custCrAsgndTskDoc) {
		getCustCrAsgndTskDocs().remove(custCrAsgndTskDoc);
		custCrAsgndTskDoc.setCustCr(null);

		return custCrAsgndTskDoc;
	}

}