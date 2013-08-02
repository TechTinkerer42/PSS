package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the LANG database table.
 * 
 */
@Entity
@Table(name="LANG")
public class Lang implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LANG_CDE", nullable=false, length=5)
	private String langCde;

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

	@Column(name="LANG_DSC", nullable=false, length=175)
	private String langDsc;

	@Column(name="LANG_DSPLY_SEQ")
	private BigDecimal langDsplySeq;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to AsgndTsk
	@OneToMany(mappedBy="lang")
	private Set<AsgndTsk> asgndTsks;

	public Lang() {
	}

	public String getLangCde() {
		return this.langCde;
	}

	public void setLangCde(String langCde) {
		this.langCde = langCde;
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

	public String getLangDsc() {
		return this.langDsc;
	}

	public void setLangDsc(String langDsc) {
		this.langDsc = langDsc;
	}

	public BigDecimal getLangDsplySeq() {
		return this.langDsplySeq;
	}

	public void setLangDsplySeq(BigDecimal langDsplySeq) {
		this.langDsplySeq = langDsplySeq;
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
		asgndTsk.setLang(this);

		return asgndTsk;
	}

	public AsgndTsk removeAsgndTsk(AsgndTsk asgndTsk) {
		getAsgndTsks().remove(asgndTsk);
		asgndTsk.setLang(null);

		return asgndTsk;
	}

}