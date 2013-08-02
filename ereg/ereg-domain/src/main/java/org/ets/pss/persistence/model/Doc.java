package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Set;
import java.math.BigDecimal;


/**
 * The persistent class for the DOC database table.
 * 
 */
@Entity
@Table(name="DOC")
public class Doc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOC_DOCID_GENERATOR", sequenceName="SEQ_DOC")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOC_DOCID_GENERATOR")
	@Column(name="DOC_ID", unique=true, nullable=false)
	private long docId;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Lob
	@Column(name="DOC_BLB", nullable=false)
	private byte[] docBlb;

	@Column(name="RSP_BLB_LCTN_ADR", length=500)
	private String rspBlbLctnAdr;

	@Column(name="RSP_SRC_LCTN_NAM", length=100)
	private String rspSrcLctnNam;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to EtsCust
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID", nullable=false)
	private EtsCust etsCust;

	//bi-directional many-to-one association to MdaMimeTyp
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MIME_TYP_ID")
	private MdaMimeTyp mdaMimeTyp;

	//bi-directional many-to-one association to AsgndTskDoc
	@OneToMany(mappedBy="doc")
	private Set<CustCrAsgndTskDoc> custCrAsgndTskDocs;
	

	public Doc() {
	}

	public long getDocId() {
		return this.docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
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

	public byte[] getDocBlb() {
		return this.docBlb;
	}

	public void setDocBlb(byte[] docBlb) {
		this.docBlb = docBlb;
	}

	public String getRspBlbLctnAdr() {
		return this.rspBlbLctnAdr;
	}

	public void setRspBlbLctnAdr(String rspBlbLctnAdr) {
		this.rspBlbLctnAdr = rspBlbLctnAdr;
	}

	public String getRspSrcLctnNam() {
		return this.rspSrcLctnNam;
	}

	public void setRspSrcLctnNam(String rspSrcLctnNam) {
		this.rspSrcLctnNam = rspSrcLctnNam;
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

	public EtsCust getEtsCust() {
		return this.etsCust;
	}

	public void setEtsCust(EtsCust etsCust) {
		this.etsCust = etsCust;
	}

	public MdaMimeTyp getMdaMimeTyp() {
		return this.mdaMimeTyp;
	}

	public void setMdaMimeTyp(MdaMimeTyp mdaMimeTyp) {
		this.mdaMimeTyp = mdaMimeTyp;
	}
	
	/**
	 * @return the custCrAsgndTskDocs
	 */
	public Set<CustCrAsgndTskDoc> getCustCrAsgndTskDocs() {
		return custCrAsgndTskDocs;
	}

	/**
	 * @param custCrAsgndTskDocs the custCrAsgndTskDocs to set
	 */
	public void setCustCrAsgndTskDocs(Set<CustCrAsgndTskDoc> custCrAsgndTskDocs) {
		this.custCrAsgndTskDocs = custCrAsgndTskDocs;
	}	

}