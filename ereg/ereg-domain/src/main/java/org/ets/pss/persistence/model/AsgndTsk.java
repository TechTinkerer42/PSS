package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ASGND_TSK database table.
 * 
 */
@Entity
@Table(name="ASGND_TSK")
public class AsgndTsk implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AsgndTskPK id;

	@Column(name="APNTMT_DT", nullable=false)
	private Date apntmtDt;

	@Column(name="APPT_DTE_STR", length=20)
	private String apptDteStr;

	@Column(name="APPT_TM_ENT_FLG", nullable=false, length=1)
	private String apptTmEntFlg;

	@Column(name="BKNG_ID", nullable=false)
	private BigDecimal bkngId;

	@Column(name="BKNG_STS_TYP_CDE", length=5)
	private String bkngStsTypCde;

	@Column(length=2000)
	private String cmnt;


	@Temporal(TemporalType.DATE)
	@Column(name="CUST_BRTH_DT")
	private Date custBrthDt;

	@Column(name="CUST_FST_NAM", length=30)
	private String custFstNam;

	@Column(name="CUST_LST_NAM", length=50)
	private String custLstNam;

	@Column(name="CUST_MID_NAM", length=50)
	private String custMidNam;

	@Temporal(TemporalType.DATE)
	@Column(name="DRAFT_DTE")
	private Date draftDte;

	@Column(name="ETS_APNTMT_ID", nullable=false, length=16)
	private String etsApntmtId;

	@Column(name="EVNT_OTCM_STS_TYP_CDE", length=4)
	private String evntOtcmStsTypCde;

	@Column(name="EXTRNL_APNTMT_ID", length=36)
	private String extrnlApntmtId;

	@Column(name="REGN_SYS_ID", length=3)
	private String regnSysId;

	@Temporal(TemporalType.DATE)
	@Column(name="SBMT_DTE")
	private Date sbmtDte;

	@Column(name="TST_CNTR_ID_NO")
	private BigDecimal tstCntrIdNo;

	@Column(name="TST_DURN")
	private BigDecimal tstDurn;

	@Temporal(TemporalType.DATE)
	@Column(name="TST_LNCH_TM")
	private Date tstLnchTm;

	@Column(name="TST_PKG_ID", length=23)
	private String tstPkgId;

	@Column(name="TST_TKR_TST_ID", nullable=false)
	private BigDecimal tstTkrTstId;


	//bi-directional many-to-one association to DlvyMde
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DLVY_MDE_CDE")
	private DlvyMde dlvyMde;

	//bi-directional many-to-one association to DocStsTyp
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TSK_STS_TYP_CDE")
	private TaskStatusType docStsTyp;

	//bi-directional many-to-one association to EtsCust
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID", nullable=false, insertable=false, updatable=false)
	private EtsCust etsCust;

	//bi-directional many-to-one association to Lang
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LANG_CDE")
	private Lang lang;

	//bi-directional many-to-one association to Tsk
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TSK_ID", nullable=false, insertable=false, updatable=false)
	private Task tsk;

	//bi-directional many-to-one association to Tst
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TST_ID")
	private Test tst;

	//bi-directional many-to-one association to AsgndTskDoc

	@OneToMany(mappedBy="asgndTsk", cascade = {CascadeType.ALL},orphanRemoval = true)
	private Set<CustCrAsgndTskDoc> custCrAsgndTskDocs;
	

	public AsgndTsk() {
	}

	public AsgndTskPK getId() {
		return this.id;
	}

	public void setId(AsgndTskPK id) {
		this.id = id;
	}

	public Date getApntmtDt() {
		return this.apntmtDt;
	}

	public void setApntmtDt(Date apntmtDt) {
		this.apntmtDt = apntmtDt;
	}

	public String getApptDteStr() {
		return this.apptDteStr;
	}

	public void setApptDteStr(String apptDteStr) {
		this.apptDteStr = apptDteStr;
	}

	public String getApptTmEntFlg() {
		return this.apptTmEntFlg;
	}

	public void setApptTmEntFlg(String apptTmEntFlg) {
		this.apptTmEntFlg = apptTmEntFlg;
	}

	public BigDecimal getBkngId() {
		return this.bkngId;
	}

	public void setBkngId(BigDecimal bkngId) {
		this.bkngId = bkngId;
	}

	public String getBkngStsTypCde() {
		return this.bkngStsTypCde;
	}

	public void setBkngStsTypCde(String bkngStsTypCde) {
		this.bkngStsTypCde = bkngStsTypCde;
	}

	public String getCmnt() {
		return this.cmnt;
	}

	public void setCmnt(String cmnt) {
		this.cmnt = cmnt;
	}


	public Date getCustBrthDt() {
		return this.custBrthDt;
	}

	public void setCustBrthDt(Date custBrthDt) {
		this.custBrthDt = custBrthDt;
	}

	public String getCustFstNam() {
		return this.custFstNam;
	}

	public void setCustFstNam(String custFstNam) {
		this.custFstNam = custFstNam;
	}

	public String getCustLstNam() {
		return this.custLstNam;
	}

	public void setCustLstNam(String custLstNam) {
		this.custLstNam = custLstNam;
	}

	public String getCustMidNam() {
		return this.custMidNam;
	}

	public void setCustMidNam(String custMidNam) {
		this.custMidNam = custMidNam;
	}

	public Date getDraftDte() {
		return this.draftDte;
	}

	public void setDraftDte(Date draftDte) {
		this.draftDte = draftDte;
	}

	public String getEtsApntmtId() {
		return this.etsApntmtId;
	}

	public void setEtsApntmtId(String etsApntmtId) {
		this.etsApntmtId = etsApntmtId;
	}

	public String getEvntOtcmStsTypCde() {
		return this.evntOtcmStsTypCde;
	}

	public void setEvntOtcmStsTypCde(String evntOtcmStsTypCde) {
		this.evntOtcmStsTypCde = evntOtcmStsTypCde;
	}

	public String getExtrnlApntmtId() {
		return this.extrnlApntmtId;
	}

	public void setExtrnlApntmtId(String extrnlApntmtId) {
		this.extrnlApntmtId = extrnlApntmtId;
	}

	public String getRegnSysId() {
		return this.regnSysId;
	}

	public void setRegnSysId(String regnSysId) {
		this.regnSysId = regnSysId;
	}

	public Date getSbmtDte() {
		return this.sbmtDte;
	}

	public void setSbmtDte(Date sbmtDte) {
		this.sbmtDte = sbmtDte;
	}

	public BigDecimal getTstCntrIdNo() {
		return this.tstCntrIdNo;
	}

	public void setTstCntrIdNo(BigDecimal tstCntrIdNo) {
		this.tstCntrIdNo = tstCntrIdNo;
	}

	public BigDecimal getTstDurn() {
		return this.tstDurn;
	}

	public void setTstDurn(BigDecimal tstDurn) {
		this.tstDurn = tstDurn;
	}

	public Date getTstLnchTm() {
		return this.tstLnchTm;
	}

	public void setTstLnchTm(Date tstLnchTm) {
		this.tstLnchTm = tstLnchTm;
	}

	public String getTstPkgId() {
		return this.tstPkgId;
	}

	public void setTstPkgId(String tstPkgId) {
		this.tstPkgId = tstPkgId;
	}

	public BigDecimal getTstTkrTstId() {
		return this.tstTkrTstId;
	}

	public void setTstTkrTstId(BigDecimal tstTkrTstId) {
		this.tstTkrTstId = tstTkrTstId;
	}

	public DlvyMde getDlvyMde() {
		return this.dlvyMde;
	}

	public void setDlvyMde(DlvyMde dlvyMde) {
		this.dlvyMde = dlvyMde;
	}

	public TaskStatusType getDocStsTyp() {
		return this.docStsTyp;
	}

	public void setDocStsTyp(TaskStatusType docStsTyp) {
		this.docStsTyp = docStsTyp;
	}

	public EtsCust getEtsCust() {
		return this.etsCust;
	}

	public void setEtsCust(EtsCust etsCust) {
		this.etsCust = etsCust;
	}

	public Lang getLang() {
		return this.lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}

	public Task getTsk() {
		return this.tsk;
	}

	public void setTsk(Task tsk) {
		this.tsk = tsk;
	}

	public Test getTst() {
		return this.tst;
	}

	public void setTst(Test tst) {
		this.tst = tst;
	}



	public Set<CustCrAsgndTskDoc> getCustCrAsgndTskDocs() {
		return custCrAsgndTskDocs;
	}

	public void setCustCrAsgndTskDocs(Set<CustCrAsgndTskDoc> custCrAsgndTskDocs) {
		this.custCrAsgndTskDocs = custCrAsgndTskDocs;
	}

}