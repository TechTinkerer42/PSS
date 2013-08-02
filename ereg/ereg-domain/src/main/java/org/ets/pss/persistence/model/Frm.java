package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the FRM database table.
 * 
 */
@Entity
@Table(name="FRM")
public class Frm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FRM_FRMID_GENERATOR", sequenceName="SEQ_FRM")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FRM_FRMID_GENERATOR")
	@Column(name="FRM_ID", nullable=false)
	private long frmId;

	@Column(name="ACMDTN_TYP_CDE", length=5)
	private String acmdtnTypCde;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="DLVY_MDE_CDE", length=5)
	private String dlvyMdeCde;

	@Temporal(TemporalType.DATE)
	@Column(name="EFF_DTE", nullable=false)
	private Date effDte;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPRTN_DTE")
	private Date exprtnDte;

	@Column(name="FRM_CDE", length=20)
	private String frmCde;

	@Column(name="FRM_DSC", nullable=false, length=175)
	private String frmDsc;

	@Column(name="LANG_CDE", length=5)
	private String langCde;




	//bi-directional many-to-one association to Frm
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARNT_FRM_ID")
	private Frm frm;

	//bi-directional many-to-one association to Frm
	@OneToMany(mappedBy="frm")
	private Set<Frm> frms;

	//bi-directional many-to-one association to FrmTyp
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FRM_TYP_CDE")
	private FrmTyp frmTyp;

	//bi-directional many-to-one association to Pkg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="PKG_NO", referencedColumnName="PKG_NO"),
		@JoinColumn(name="TST_ID", referencedColumnName="TST_ID")
		//@JoinColumn(name="TST_ID", referencedColumnName="TST_ID", nullable=false)
		})
	private Pkg pkg;

	//bi-directional many-to-one association to Tst
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TST_ID", nullable=false,insertable=false,updatable=false)
	private Test tst;

	//bi-directional many-to-one association to Prmpt
	@OneToMany(mappedBy="frm")
	private Set<Prompt> prmpts;

	//bi-directional many-to-one association to Tsk
	@OneToMany(mappedBy="frm")
	private Set<Task> tsks;

	public Frm() {
	}

	public long getFrmId() {
		return this.frmId;
	}

	public void setFrmId(long frmId) {
		this.frmId = frmId;
	}

	public String getAcmdtnTypCde() {
		return this.acmdtnTypCde;
	}

	public void setAcmdtnTypCde(String acmdtnTypCde) {
		this.acmdtnTypCde = acmdtnTypCde;
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

	public String getDlvyMdeCde() {
		return this.dlvyMdeCde;
	}

	public void setDlvyMdeCde(String dlvyMdeCde) {
		this.dlvyMdeCde = dlvyMdeCde;
	}

	public Date getEffDte() {
		return this.effDte;
	}

	public void setEffDte(Date effDte) {
		this.effDte = effDte;
	}

	public Date getExprtnDte() {
		return this.exprtnDte;
	}

	public void setExprtnDte(Date exprtnDte) {
		this.exprtnDte = exprtnDte;
	}

	public String getFrmCde() {
		return this.frmCde;
	}

	public void setFrmCde(String frmCde) {
		this.frmCde = frmCde;
	}

	public String getFrmDsc() {
		return this.frmDsc;
	}

	public void setFrmDsc(String frmDsc) {
		this.frmDsc = frmDsc;
	}

	public String getLangCde() {
		return this.langCde;
	}

	public void setLangCde(String langCde) {
		this.langCde = langCde;
	}

	public Frm getFrm() {
		return this.frm;
	}

	public void setFrm(Frm frm) {
		this.frm = frm;
	}

	public Set<Frm> getFrms() {
		return this.frms;
	}

	public void setFrms(Set<Frm> frms) {
		this.frms = frms;
	}

	public Frm addFrm(Frm frm) {
		getFrms().add(frm);
		frm.setFrm(this);

		return frm;
	}

	public Frm removeFrm(Frm frm) {
		getFrms().remove(frm);
		frm.setFrm(null);

		return frm;
	}

	public FrmTyp getFrmTyp() {
		return this.frmTyp;
	}

	public void setFrmTyp(FrmTyp frmTyp) {
		this.frmTyp = frmTyp;
	}

	public Pkg getPkg() {
		return this.pkg;
	}

	public void setPkg(Pkg pkg) {
		this.pkg = pkg;
	}

	public Test getTst() {
		return this.tst;
	}

	public void setTst(Test tst) {
		this.tst = tst;
	}

	public Set<Prompt> getPrmpts() {
		return this.prmpts;
	}

	public void setPrmpts(Set<Prompt> prmpts) {
		this.prmpts = prmpts;
	}


	public Set<Task> getTsks() {
		return this.tsks;
	}

	public void setTsks(Set<Task> tsks) {
		this.tsks = tsks;
	}

}