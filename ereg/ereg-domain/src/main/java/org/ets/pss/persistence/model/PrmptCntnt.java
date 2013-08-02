package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the PRMPT_CNTNT database table.
 * 
 */
@Entity
@Table(name="PRMPT_CNTNT")
public class PrmptCntnt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRMPT_CNTNT_PRMPTID_GENERATOR", sequenceName="SEQ_PRMPT_CNTNT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRMPT_CNTNT_PRMPTID_GENERATOR")
	@Column(name="PRMPT_ID", nullable=false)
	private long prmptId;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="MDA_TYP_CDE", nullable=false, length=5)
	private String mdaTypCde;

	@Column(name="PGM_CDE", nullable=false, length=5)
	private String pgmCde;

	@Lob
	@Column(name="PRMPT_CNTNT", nullable=false)
	private byte[] prmptCntnt;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional one-to-one association to Prmpt
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRMPT_ID", nullable=false, insertable=false, updatable=false)
	private Prompt prmpt;

	public PrmptCntnt() {
	}

	public long getPrmptId() {
		return this.prmptId;
	}

	public void setPrmptId(long prmptId) {
		this.prmptId = prmptId;
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

	public String getMdaTypCde() {
		return this.mdaTypCde;
	}

	public void setMdaTypCde(String mdaTypCde) {
		this.mdaTypCde = mdaTypCde;
	}

	public String getPgmCde() {
		return this.pgmCde;
	}

	public void setPgmCde(String pgmCde) {
		this.pgmCde = pgmCde;
	}

	public byte[] getPrmptCntnt() {
		return this.prmptCntnt;
	}

	public void setPrmptCntnt(byte[] prmptCntnt) {
		this.prmptCntnt = prmptCntnt;
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

	public Prompt getPrmpt() {
		return this.prmpt;
	}

	public void setPrmpt(Prompt prmpt) {
		this.prmpt = prmpt;
	}

}