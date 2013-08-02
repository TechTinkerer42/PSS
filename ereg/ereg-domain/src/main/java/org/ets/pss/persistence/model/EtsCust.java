package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ETS_CUST database table.
 * 
 */
@Entity
@Table(name="ETS_CUST")
public class EtsCust implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="ETS_CUST_CUSTOMERID_GENERATOR", sequenceName="SEQ_ETS_CUST")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ETS_CUST_CUSTOMERID_GENERATOR")
	@Column(name="CUSTOMER_ID", nullable=false, precision=19)
	private long customerId;

	@Temporal(TemporalType.DATE)
	@Column(name="BRTH_DTE")
	private Date brthDte;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="CUST_TYP_CDE", nullable=false, length=5)
	private String custTypCde;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="ETNCTY_CDE", length=5)
	private String etnctyCde;

	@Column(name="GNDR_CDE", length=5)
	private String gndrCde;

	@Column(name="INTRNL_USR_FLG", nullable=false, length=1)
	private String intrnlUsrFlg;

	@Column(name="LDAP_GUID_ID")
	private BigDecimal ldapGuidId;

	@Column(name="MID_NAM", length=50)
	private String midNam;

	@Column(name="MIL_STS_CDE", length=5)
	private String milStsCde;

	@Column(name="PRFRD_TST_TAKING_LANG_CDE", length=5)
	private String prfrdTstTakingLangCde;

	@Column(name="PRMRY_SPKNG_LANG_CDE", length=5)
	private String prmrySpkngLangCde;

	@Column(name="SRC_ID")
	private BigDecimal srcId;

	@Column(name="SSN_LST_4", length=4)
	private String ssnLst4;

	@Column(name="TAX_EXMPT_FLG", nullable=false, length=1)
	private String taxExmptFlg;

	@Column(name="TRMS_ACEPTNC_DTM")
	private Timestamp trmsAceptncDtm;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to AsgndTsk
	@OneToMany(mappedBy="etsCust", fetch=FetchType.EAGER)
	private Set<AsgndTsk> asgndTsks;

	//bi-directional many-to-one association to CustCr
	@OneToMany(mappedBy="etsCust")
	private Set<CustCr> custCrs;

	//bi-directional many-to-one association to Doc
	@OneToMany(mappedBy="etsCust")
	private Set<Doc> docs;

	//bi-directional one-to-one association to BlcCustomer
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID", nullable=false, insertable=false, updatable=false)
	private BlcCustomer blcCustomer;

	public EtsCust() {
	}

	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Date getBrthDte() {
		return this.brthDte;
	}

	public void setBrthDte(Date brthDte) {
		this.brthDte = brthDte;
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

	public String getCustTypCde() {
		return this.custTypCde;
	}

	public void setCustTypCde(String custTypCde) {
		this.custTypCde = custTypCde;
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

	public String getEtnctyCde() {
		return this.etnctyCde;
	}

	public void setEtnctyCde(String etnctyCde) {
		this.etnctyCde = etnctyCde;
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

	public BigDecimal getLdapGuidId() {
		return this.ldapGuidId;
	}

	public void setLdapGuidId(BigDecimal ldapGuidId) {
		this.ldapGuidId = ldapGuidId;
	}

	public String getMidNam() {
		return this.midNam;
	}

	public void setMidNam(String midNam) {
		this.midNam = midNam;
	}

	public String getMilStsCde() {
		return this.milStsCde;
	}

	public void setMilStsCde(String milStsCde) {
		this.milStsCde = milStsCde;
	}

	public String getPrfrdTstTakingLangCde() {
		return this.prfrdTstTakingLangCde;
	}

	public void setPrfrdTstTakingLangCde(String prfrdTstTakingLangCde) {
		this.prfrdTstTakingLangCde = prfrdTstTakingLangCde;
	}

	public String getPrmrySpkngLangCde() {
		return this.prmrySpkngLangCde;
	}

	public void setPrmrySpkngLangCde(String prmrySpkngLangCde) {
		this.prmrySpkngLangCde = prmrySpkngLangCde;
	}

	public BigDecimal getSrcId() {
		return this.srcId;
	}

	public void setSrcId(BigDecimal srcId) {
		this.srcId = srcId;
	}

	public String getSsnLst4() {
		return this.ssnLst4;
	}

	public void setSsnLst4(String ssnLst4) {
		this.ssnLst4 = ssnLst4;
	}

	public String getTaxExmptFlg() {
		return this.taxExmptFlg;
	}

	public void setTaxExmptFlg(String taxExmptFlg) {
		this.taxExmptFlg = taxExmptFlg;
	}

	public Timestamp getTrmsAceptncDtm() {
		return this.trmsAceptncDtm;
	}

	public void setTrmsAceptncDtm(Timestamp trmsAceptncDtm) {
		this.trmsAceptncDtm = trmsAceptncDtm;
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

	public Set<AsgndTsk> getAsgndTsks() {
		return this.asgndTsks;
	}

	public void setAsgndTsks(Set<AsgndTsk> asgndTsks) {
		this.asgndTsks = asgndTsks;
	}

	public AsgndTsk addAsgndTsk(AsgndTsk asgndTsk) {
		getAsgndTsks().add(asgndTsk);
		asgndTsk.setEtsCust(this);

		return asgndTsk;
	}

	public AsgndTsk removeAsgndTsk(AsgndTsk asgndTsk) {
		getAsgndTsks().remove(asgndTsk);
		asgndTsk.setEtsCust(null);

		return asgndTsk;
	}

	public Set<CustCr> getCustCrs() {
		return this.custCrs;
	}

	public void setCustCrs(Set<CustCr> custCrs) {
		this.custCrs = custCrs;
	}

	public CustCr addCustCr(CustCr custCr) {
		getCustCrs().add(custCr);
		custCr.setEtsCust(this);

		return custCr;
	}

	public CustCr removeCustCr(CustCr custCr) {
		getCustCrs().remove(custCr);
		custCr.setEtsCust(null);

		return custCr;
	}

	public Set<Doc> getDocs() {
		return this.docs;
	}

	public void setDocs(Set<Doc> docs) {
		this.docs = docs;
	}

	public Doc addDoc(Doc doc) {
		getDocs().add(doc);
		doc.setEtsCust(this);

		return doc;
	}

	public Doc removeDoc(Doc doc) {
		getDocs().remove(doc);
		doc.setEtsCust(null);

		return doc;
	}

	public BlcCustomer getBlcCustomer() {
		return this.blcCustomer;
	}

	public void setBlcCustomer(BlcCustomer blcCustomer) {
		this.blcCustomer = blcCustomer;
	}

}