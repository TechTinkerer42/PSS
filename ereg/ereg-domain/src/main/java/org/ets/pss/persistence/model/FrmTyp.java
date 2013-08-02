package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the FRM_TYP database table.
 * 
 */
@Entity
@Table(name="FRM_TYP")
public class FrmTyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FRM_TYP_FRMTYPCDE_GENERATOR", sequenceName="SEQ_FRM_TYP")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FRM_TYP_FRMTYPCDE_GENERATOR")
	@Column(name="FRM_TYP_CDE", nullable=false, length=5)
	private String frmTypCde;

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

	@Column(name="FRM_TYP_DSC", nullable=false, length=175)
	private String frmTypDsc;

	@Column(name="FRM_TYP_DSPLY_SEQ")
	private BigDecimal frmTypDsplySeq;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to Frm
	@OneToMany(mappedBy="frmTyp")
	private Set<Frm> frms;

	public FrmTyp() {
	}

	public String getFrmTypCde() {
		return this.frmTypCde;
	}

	public void setFrmTypCde(String frmTypCde) {
		this.frmTypCde = frmTypCde;
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

	public String getFrmTypDsc() {
		return this.frmTypDsc;
	}

	public void setFrmTypDsc(String frmTypDsc) {
		this.frmTypDsc = frmTypDsc;
	}

	public BigDecimal getFrmTypDsplySeq() {
		return this.frmTypDsplySeq;
	}

	public void setFrmTypDsplySeq(BigDecimal frmTypDsplySeq) {
		this.frmTypDsplySeq = frmTypDsplySeq;
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

	public Set<Frm> getFrms() {
		return this.frms;
	}

	public void setFrms(Set<Frm> frms) {
		this.frms = frms;
	}

	public Frm addFrm(Frm frm) {
		getFrms().add(frm);
		frm.setFrmTyp(this);

		return frm;
	}

	public Frm removeFrm(Frm frm) {
		getFrms().remove(frm);
		frm.setFrmTyp(null);

		return frm;
	}

}