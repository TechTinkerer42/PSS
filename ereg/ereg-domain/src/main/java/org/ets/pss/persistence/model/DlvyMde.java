package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DLVY_MDE database table.
 * 
 */
@Entity
@Table(name="DLVY_MDE")
public class DlvyMde implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DLVY_MDE_CDE", nullable=false, length=5)
	private String dlvyMdeCde;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="DLVY_MDE_DSC", nullable=false, length=100)
	private String dlvyMdeDsc;

	@Column(name="DLVY_MDE_DSPLY_SEQ")
	private BigDecimal dlvyMdeDsplySeq;

	@Column(name="DSPLY_DTA_FLG", nullable=false, length=1)
	private String dsplyDtaFlg;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to Adm
	@OneToMany(mappedBy="dlvyMde")
	private Set<Adm> adms;

	//bi-directional many-to-one association to AsgndTsk
	@OneToMany(mappedBy="dlvyMde")
	private Set<AsgndTsk> asgndTsks;

	public DlvyMde() {
	}

	public String getDlvyMdeCde() {
		return this.dlvyMdeCde;
	}

	public void setDlvyMdeCde(String dlvyMdeCde) {
		this.dlvyMdeCde = dlvyMdeCde;
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

	public String getDlvyMdeDsc() {
		return this.dlvyMdeDsc;
	}

	public void setDlvyMdeDsc(String dlvyMdeDsc) {
		this.dlvyMdeDsc = dlvyMdeDsc;
	}

	public BigDecimal getDlvyMdeDsplySeq() {
		return this.dlvyMdeDsplySeq;
	}

	public void setDlvyMdeDsplySeq(BigDecimal dlvyMdeDsplySeq) {
		this.dlvyMdeDsplySeq = dlvyMdeDsplySeq;
	}

	public String getDsplyDtaFlg() {
		return this.dsplyDtaFlg;
	}

	public void setDsplyDtaFlg(String dsplyDtaFlg) {
		this.dsplyDtaFlg = dsplyDtaFlg;
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
		adm.setDlvyMde(this);

		return adm;
	}

	public Adm removeAdm(Adm adm) {
		getAdms().remove(adm);
		adm.setDlvyMde(null);

		return adm;
	}

	public Set<AsgndTsk> getAsgndTsks() {
		return this.asgndTsks;
	}

	public void setAsgndTsks(Set<AsgndTsk> asgndTsks) {
		this.asgndTsks = asgndTsks;
	}

	public AsgndTsk addAsgndTsk(AsgndTsk asgndTsk) {
		getAsgndTsks().add(asgndTsk);
		asgndTsk.setDlvyMde(this);

		return asgndTsk;
	}

	public AsgndTsk removeAsgndTsk(AsgndTsk asgndTsk) {
		getAsgndTsks().remove(asgndTsk);
		asgndTsk.setDlvyMde(null);

		return asgndTsk;
	}

}