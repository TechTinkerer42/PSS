package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the PGM database table.
 * 
 */
@Entity
@Table(name="PGM")
public class Pgm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PGM_CDE", nullable=false, length=5)
	private String pgmCde;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="DSPLY_DTA_FLG", nullable=false, length=1)
	private String dsplyDtaFlg;

	@Column(name="EREG_HIER_ID")
	private BigDecimal eregHierId;

	@Column(name="PGM_DSPLY_SEQ")
	private BigDecimal pgmDsplySeq;

	@Column(name="PGM_NAM", length=50)
	private String pgmNam;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to Adm
	@OneToMany(mappedBy="pgm")
	private Set<Adm> adms;

	//bi-directional many-to-one association to Tst
	@OneToMany(mappedBy="pgm")
	private Set<Test> tsts;

	public Pgm() {
	}

	public String getPgmCde() {
		return this.pgmCde;
	}

	public void setPgmCde(String pgmCde) {
		this.pgmCde = pgmCde;
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

	public String getDsplyDtaFlg() {
		return this.dsplyDtaFlg;
	}

	public void setDsplyDtaFlg(String dsplyDtaFlg) {
		this.dsplyDtaFlg = dsplyDtaFlg;
	}

	public BigDecimal getEregHierId() {
		return this.eregHierId;
	}

	public void setEregHierId(BigDecimal eregHierId) {
		this.eregHierId = eregHierId;
	}

	public BigDecimal getPgmDsplySeq() {
		return this.pgmDsplySeq;
	}

	public void setPgmDsplySeq(BigDecimal pgmDsplySeq) {
		this.pgmDsplySeq = pgmDsplySeq;
	}

	public String getPgmNam() {
		return this.pgmNam;
	}

	public void setPgmNam(String pgmNam) {
		this.pgmNam = pgmNam;
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

	public Set<Adm> getAdms() {
		return this.adms;
	}

	public void setAdms(Set<Adm> adms) {
		this.adms = adms;
	}

	public Adm addAdm(Adm adm) {
		getAdms().add(adm);
		adm.setPgm(this);

		return adm;
	}

	public Adm removeAdm(Adm adm) {
		getAdms().remove(adm);
		adm.setPgm(null);

		return adm;
	}

	public Set<Test> getTsts() {
		return this.tsts;
	}

	public void setTsts(Set<Test> tsts) {
		this.tsts = tsts;
	}

	public Test addTst(Test tst) {
		getTsts().add(tst);
		tst.setPgm(this);

		return tst;
	}

	public Test removeTst(Test tst) {
		getTsts().remove(tst);
		tst.setPgm(null);

		return tst;
	}

}