package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PKG database table.
 * 
 */
@Entity
@Table(name="PKG")
public class Pkg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PkgPK id;

	@Column(name="AD_HOC_WKFLW_STEP_CDE", length=5)
	private String adHocWkflwStepCde;

	@Column(name="CMNT_XML")
	private String cmntXml;

	@Temporal(TemporalType.DATE)
	@Column(name="CRET_DTM", nullable=false)
	private Date cretDtm;

	@Column(name="CRET_LGN_UID", nullable=false, length=32)
	private String cretLgnUid;

	@Column(name="DEL_FLG", nullable=false, length=1)
	private String delFlg;

	@Column(name="FRM_STRC_CNTNT_XML")
	private String frmStrcCntntXml;

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

	@Temporal(TemporalType.DATE)
	@Column(name="NOT_RDY_FOR_USE_EFF_DTE")
	private Date notRdyForUseEffDte;

	@Column(name="NOT_RDY_FOR_USE_RSN_CDE", length=5)
	private String notRdyForUseRsnCde;

	@Column(name="NOT_RDY_FOR_USE_RSN_TXT", length=256)
	private String notRdyForUseRsnTxt;

	@Column(name="NOT_RDY_FOR_USE_RSN_TYP_CDE", length=5)
	private String notRdyForUseRsnTypCde;

	@Column(name="OBJ_WKFLW_CNFGN_ID")
	private BigDecimal objWkflwCnfgnId;

	@Column(name="PKG_DSC", length=100)
	private String pkgDsc;

	@Column(name="PKG_IBT_PKG_CDE", length=23)
	private String pkgIbtPkgCde;

	@Column(name="PKG_NAM", nullable=false, length=50)
	private String pkgNam;

	@Column(name="PKG_STS_CDE", nullable=false, length=5)
	private String pkgStsCde;

	@Column(name="PKG_TYP_CDE", length=20)
	private String pkgTypCde;

	@Column(name="RDY_FOR_USE_FLG", nullable=false, length=1)
	private String rdyForUseFlg;

	@Column(name="STAT_APRVL_CDE", length=5)
	private String statAprvlCde;

	@Column(name="TST_FRM_TMPLT_SET_NO")
	private BigDecimal tstFrmTmpltSetNo;

	@Column(name="WKFLW_STEP_CDE", length=5)
	private String wkflwStepCde;

	//bi-directional many-to-one association to Frm
	@OneToMany(mappedBy="pkg")
	private Set<Frm> frms;

	//bi-directional many-to-one association to Tst
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TST_ID", nullable=false, insertable=false, updatable=false)
	private Test tst;

	public Pkg() {
	}

	public PkgPK getId() {
		return this.id;
	}

	public void setId(PkgPK id) {
		this.id = id;
	}

	public String getAdHocWkflwStepCde() {
		return this.adHocWkflwStepCde;
	}

	public void setAdHocWkflwStepCde(String adHocWkflwStepCde) {
		this.adHocWkflwStepCde = adHocWkflwStepCde;
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

	public String getFrmStrcCntntXml() {
		return this.frmStrcCntntXml;
	}

	public void setFrmStrcCntntXml(String frmStrcCntntXml) {
		this.frmStrcCntntXml = frmStrcCntntXml;
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

	public Date getNotRdyForUseEffDte() {
		return this.notRdyForUseEffDte;
	}

	public void setNotRdyForUseEffDte(Date notRdyForUseEffDte) {
		this.notRdyForUseEffDte = notRdyForUseEffDte;
	}

	public String getNotRdyForUseRsnCde() {
		return this.notRdyForUseRsnCde;
	}

	public void setNotRdyForUseRsnCde(String notRdyForUseRsnCde) {
		this.notRdyForUseRsnCde = notRdyForUseRsnCde;
	}

	public String getNotRdyForUseRsnTxt() {
		return this.notRdyForUseRsnTxt;
	}

	public void setNotRdyForUseRsnTxt(String notRdyForUseRsnTxt) {
		this.notRdyForUseRsnTxt = notRdyForUseRsnTxt;
	}

	public String getNotRdyForUseRsnTypCde() {
		return this.notRdyForUseRsnTypCde;
	}

	public void setNotRdyForUseRsnTypCde(String notRdyForUseRsnTypCde) {
		this.notRdyForUseRsnTypCde = notRdyForUseRsnTypCde;
	}

	public BigDecimal getObjWkflwCnfgnId() {
		return this.objWkflwCnfgnId;
	}

	public void setObjWkflwCnfgnId(BigDecimal objWkflwCnfgnId) {
		this.objWkflwCnfgnId = objWkflwCnfgnId;
	}

	public String getPkgDsc() {
		return this.pkgDsc;
	}

	public void setPkgDsc(String pkgDsc) {
		this.pkgDsc = pkgDsc;
	}

	public String getPkgIbtPkgCde() {
		return this.pkgIbtPkgCde;
	}

	public void setPkgIbtPkgCde(String pkgIbtPkgCde) {
		this.pkgIbtPkgCde = pkgIbtPkgCde;
	}

	public String getPkgNam() {
		return this.pkgNam;
	}

	public void setPkgNam(String pkgNam) {
		this.pkgNam = pkgNam;
	}

	public String getPkgStsCde() {
		return this.pkgStsCde;
	}

	public void setPkgStsCde(String pkgStsCde) {
		this.pkgStsCde = pkgStsCde;
	}

	public String getPkgTypCde() {
		return this.pkgTypCde;
	}

	public void setPkgTypCde(String pkgTypCde) {
		this.pkgTypCde = pkgTypCde;
	}

	public String getRdyForUseFlg() {
		return this.rdyForUseFlg;
	}

	public void setRdyForUseFlg(String rdyForUseFlg) {
		this.rdyForUseFlg = rdyForUseFlg;
	}

	public String getStatAprvlCde() {
		return this.statAprvlCde;
	}

	public void setStatAprvlCde(String statAprvlCde) {
		this.statAprvlCde = statAprvlCde;
	}

	public BigDecimal getTstFrmTmpltSetNo() {
		return this.tstFrmTmpltSetNo;
	}

	public void setTstFrmTmpltSetNo(BigDecimal tstFrmTmpltSetNo) {
		this.tstFrmTmpltSetNo = tstFrmTmpltSetNo;
	}

	public String getWkflwStepCde() {
		return this.wkflwStepCde;
	}

	public void setWkflwStepCde(String wkflwStepCde) {
		this.wkflwStepCde = wkflwStepCde;
	}

	public Set<Frm> getFrms() {
		return this.frms;
	}

	public void setFrms(Set<Frm> frms) {
		this.frms = frms;
	}

	public Frm addFrm(Frm frm) {
		getFrms().add(frm);
		frm.setPkg(this);

		return frm;
	}

	public Frm removeFrm(Frm frm) {
		getFrms().remove(frm);
		frm.setPkg(null);

		return frm;
	}

	public Test getTst() {
		return this.tst;
	}

	public void setTst(Test tst) {
		this.tst = tst;
	}

}