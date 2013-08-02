package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ADM database table.
 * 
 */
@Entity
@Table(name="ADM")
public class Adm implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdmPK id;

	@Column(name="AD_HOC_WKFLW_STEP_CDE", length=5)
	private String adHocWkflwStepCde;

	@Column(name="ADM_DSC", length=500)
	private String admDsc;

	@Temporal(TemporalType.DATE)
	@Column(name="ADM_END_DTE")
	private Date admEndDte;

	@Column(name="ADM_ID", nullable=false)
	private BigDecimal admId;

	@Column(name="ADM_NAM", nullable=false, length=50)
	private String admNam;

	@Temporal(TemporalType.DATE)
	@Column(name="ADM_STRT_DTE", nullable=false)
	private Date admStrtDte;

	@Column(name="ADM_STS_CDE", nullable=false, length=5)
	private String admStsCde;

	@Column(name="ADM_TYP_CDE", nullable=false, length=5)
	private String admTypCde;

	@Column(name="CMNT_XML")
	private String cmntXml;

	@Temporal(TemporalType.DATE)
	@Column(name="CRET_DTM", nullable=false)
	private Date cretDtm;

	@Column(name="CRET_LGN_UID", nullable=false, length=32)
	private String cretLgnUid;

	@Column(name="DEL_FLG", nullable=false, length=1)
	private String delFlg;

	@Column(name="ERR_APRVL_XML")
	private String errAprvlXml;

	@Temporal(TemporalType.DATE)
	@Column(name="LCK_DTM")
	private Date lckDtm;

	@Column(name="LCK_LGN_UID", length=32)
	private String lckLgnUid;

	@Temporal(TemporalType.DATE)
	@Column(name="LST_UPDT_DTM")
	private Date lstUpdtDtm;

	@Column(name="LST_UPDT_LGN_UID", length=32)
	private String lstUpdtLgnUid;

	@Temporal(TemporalType.DATE)
	@Column(name="MGRT_DTE")
	private Date mgrtDte;

	@Column(name="MGRT_FLG", nullable=false, length=1)
	private String mgrtFlg;

	@Column(name="MGRT_SRC_NAM", length=50)
	private String mgrtSrcNam;

	@Column(name="OBJ_WKFLW_CNFGN_ID")
	private BigDecimal objWkflwCnfgnId;

	@Column(name="QC_LCK_REQD_FLG", nullable=false, length=1)
	private String qcLckReqdFlg;

	@Column(name="RDY_FOR_USE_FLG", nullable=false, length=1)
	private String rdyForUseFlg;

	@Column(name="REF_ADM_FLG", nullable=false, length=1)
	private String refAdmFlg;

	@Column(name="WKFLW_STEP_CDE", length=5)
	private String wkflwStepCde;

	//bi-directional many-to-one association to DlvyMde
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DLVY_MDE_CDE")
	private DlvyMde dlvyMde;

	//bi-directional many-to-one association to Pgm
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PGM_CDE", nullable=false, insertable=false, updatable=false)
	private Pgm pgm;

//	//bi-directional many-to-one association to Frm
//	@OneToMany(mappedBy="adm")
//	private Set<Frm> frms;

	public Adm() {
	}

	public AdmPK getId() {
		return this.id;
	}

	public void setId(AdmPK id) {
		this.id = id;
	}

	public String getAdHocWkflwStepCde() {
		return this.adHocWkflwStepCde;
	}

	public void setAdHocWkflwStepCde(String adHocWkflwStepCde) {
		this.adHocWkflwStepCde = adHocWkflwStepCde;
	}

	public String getAdmDsc() {
		return this.admDsc;
	}

	public void setAdmDsc(String admDsc) {
		this.admDsc = admDsc;
	}

	public Date getAdmEndDte() {
		return this.admEndDte;
	}

	public void setAdmEndDte(Date admEndDte) {
		this.admEndDte = admEndDte;
	}

	public BigDecimal getAdmId() {
		return this.admId;
	}

	public void setAdmId(BigDecimal admId) {
		this.admId = admId;
	}

	public String getAdmNam() {
		return this.admNam;
	}

	public void setAdmNam(String admNam) {
		this.admNam = admNam;
	}

	public Date getAdmStrtDte() {
		return this.admStrtDte;
	}

	public void setAdmStrtDte(Date admStrtDte) {
		this.admStrtDte = admStrtDte;
	}

	public String getAdmStsCde() {
		return this.admStsCde;
	}

	public void setAdmStsCde(String admStsCde) {
		this.admStsCde = admStsCde;
	}

	public String getAdmTypCde() {
		return this.admTypCde;
	}

	public void setAdmTypCde(String admTypCde) {
		this.admTypCde = admTypCde;
	}

	public String getCmntXml() {
		return this.cmntXml;
	}

	public void setCmntXml(String cmntXml) {
		this.cmntXml = cmntXml;
	}

	public Date getCretDtm() {
		return this.cretDtm;
	}

	public void setCretDtm(Date cretDtm) {
		this.cretDtm = cretDtm;
	}

	public String getCretLgnUid() {
		return this.cretLgnUid;
	}

	public void setCretLgnUid(String cretLgnUid) {
		this.cretLgnUid = cretLgnUid;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getErrAprvlXml() {
		return this.errAprvlXml;
	}

	public void setErrAprvlXml(String errAprvlXml) {
		this.errAprvlXml = errAprvlXml;
	}

	public Date getLckDtm() {
		return this.lckDtm;
	}

	public void setLckDtm(Date lckDtm) {
		this.lckDtm = lckDtm;
	}

	public String getLckLgnUid() {
		return this.lckLgnUid;
	}

	public void setLckLgnUid(String lckLgnUid) {
		this.lckLgnUid = lckLgnUid;
	}

	public Date getLstUpdtDtm() {
		return this.lstUpdtDtm;
	}

	public void setLstUpdtDtm(Date lstUpdtDtm) {
		this.lstUpdtDtm = lstUpdtDtm;
	}

	public String getLstUpdtLgnUid() {
		return this.lstUpdtLgnUid;
	}

	public void setLstUpdtLgnUid(String lstUpdtLgnUid) {
		this.lstUpdtLgnUid = lstUpdtLgnUid;
	}

	public Date getMgrtDte() {
		return this.mgrtDte;
	}

	public void setMgrtDte(Date mgrtDte) {
		this.mgrtDte = mgrtDte;
	}

	public String getMgrtFlg() {
		return this.mgrtFlg;
	}

	public void setMgrtFlg(String mgrtFlg) {
		this.mgrtFlg = mgrtFlg;
	}

	public String getMgrtSrcNam() {
		return this.mgrtSrcNam;
	}

	public void setMgrtSrcNam(String mgrtSrcNam) {
		this.mgrtSrcNam = mgrtSrcNam;
	}

	public BigDecimal getObjWkflwCnfgnId() {
		return this.objWkflwCnfgnId;
	}

	public void setObjWkflwCnfgnId(BigDecimal objWkflwCnfgnId) {
		this.objWkflwCnfgnId = objWkflwCnfgnId;
	}

	public String getQcLckReqdFlg() {
		return this.qcLckReqdFlg;
	}

	public void setQcLckReqdFlg(String qcLckReqdFlg) {
		this.qcLckReqdFlg = qcLckReqdFlg;
	}

	public String getRdyForUseFlg() {
		return this.rdyForUseFlg;
	}

	public void setRdyForUseFlg(String rdyForUseFlg) {
		this.rdyForUseFlg = rdyForUseFlg;
	}

	public String getRefAdmFlg() {
		return this.refAdmFlg;
	}

	public void setRefAdmFlg(String refAdmFlg) {
		this.refAdmFlg = refAdmFlg;
	}

	public String getWkflwStepCde() {
		return this.wkflwStepCde;
	}

	public void setWkflwStepCde(String wkflwStepCde) {
		this.wkflwStepCde = wkflwStepCde;
	}

	public DlvyMde getDlvyMde() {
		return this.dlvyMde;
	}

	public void setDlvyMde(DlvyMde dlvyMde) {
		this.dlvyMde = dlvyMde;
	}

	public Pgm getPgm() {
		return this.pgm;
	}

	public void setPgm(Pgm pgm) {
		this.pgm = pgm;
	}

//	public Set<Frm> getFrms() {
//		return this.frms;
//	}
//
//	public void setFrms(Set<Frm> frms) {
//		this.frms = frms;
//	}
//
//	public Frm addFrm(Frm frm) {
//		getFrms().add(frm);
//		frm.setAdm(this);
//
//		return frm;
//	}
//
//	public Frm removeFrm(Frm frm) {
//		getFrms().remove(frm);
//		frm.setAdm(null);
//
//		return frm;
//	}

}