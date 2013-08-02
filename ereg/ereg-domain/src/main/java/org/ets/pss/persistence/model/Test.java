package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the TST database table.
 * 
 */
@Entity
@Table(name="TST")
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TST_ID", nullable=false)
	private long testId;

	@Column(name="SECRET_CDE", nullable=true, length=50)
	private String candidateAccessCode;	

	@Column(name="DSPLY_DTA_FLG", nullable=false, length=1)
	private String dsplyDtaFlg;

	@Column(name="SCHDLG_DURN", nullable=false)
	private BigDecimal schdlgDurn;

	@Column(name="TST_DSPLY_SEQ")
	private Integer displaySequence;

	@Column(name="TST_DURN", nullable=false)
	private BigDecimal tstDurn;

	@Column(name="TST_NAM", length=50)
	private String tstNam;


	//bi-directional many-to-one association to AsgndTsk
	@OneToMany(mappedBy="tst")
	private Set<AsgndTsk> asgndTsks;

	//bi-directional many-to-one association to Frm
	@OneToMany(mappedBy="tst")
	private Set<Frm> frms;

	//bi-directional many-to-one association to Pkg
	@OneToMany(mappedBy="tst")
	private Set<Pkg> pkgs;

	//bi-directional many-to-one association to Tsk
	@OneToMany(mappedBy="test")
	private Set<Task> tasks;

	//bi-directional many-to-one association to Pgm
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PGM_CDE", nullable=false)
	private Pgm pgm;

	public long getTestId() {
		return testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	public String getCandidateAccessCode() {
		return candidateAccessCode;
	}

	public void setCandidateAccessCode(String candidateAccessCode) {
		this.candidateAccessCode = candidateAccessCode;
	}

	public String getDsplyDtaFlg() {
		return dsplyDtaFlg;
	}

	public void setDsplyDtaFlg(String dsplyDtaFlg) {
		this.dsplyDtaFlg = dsplyDtaFlg;
	}

	public BigDecimal getSchdlgDurn() {
		return schdlgDurn;
	}

	public void setSchdlgDurn(BigDecimal schdlgDurn) {
		this.schdlgDurn = schdlgDurn;
	}

	

	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public BigDecimal getTstDurn() {
		return tstDurn;
	}

	public void setTstDurn(BigDecimal tstDurn) {
		this.tstDurn = tstDurn;
	}

	public String getTstNam() {
		return tstNam;
	}

	public void setTstNam(String tstNam) {
		this.tstNam = tstNam;
	}

	public Set<AsgndTsk> getAsgndTsks() {
		return asgndTsks;
	}

	public void setAsgndTsks(Set<AsgndTsk> asgndTsks) {
		this.asgndTsks = asgndTsks;
	}

	public Set<Frm> getFrms() {
		return frms;
	}

	public void setFrms(Set<Frm> frms) {
		this.frms = frms;
	}

	public Set<Pkg> getPkgs() {
		return pkgs;
	}

	public void setPkgs(Set<Pkg> pkgs) {
		this.pkgs = pkgs;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Pgm getPgm() {
		return pgm;
	}

	public void setPgm(Pgm pgm) {
		this.pgm = pgm;
	}
	
	

}