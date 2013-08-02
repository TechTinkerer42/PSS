package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the CR_BLB database table.
 * 
 */
@Entity
@Table(name="CR_BLB")
public class CrBlb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CR_BLB_CRBLBID_GENERATOR", sequenceName="SEQ_CR_BLB")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CR_BLB_CRBLBID_GENERATOR")
	@Column(name="CR_BLB_ID", nullable=false)
	private long crBlbId;

	@Column(name="BAT_FLE_NO", length=20)
	private String batFleNo;

	@Lob
	@Column(name="CR_BLB")
	private byte[] crBlb;

	@Column(name="CR_PRT_ID", nullable=false)
	private BigDecimal crPrtId;

	@Column(name="CR_SEQ", nullable=false)
	private BigDecimal crSeq;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="IMG_DOC_ID", length=8)
	private String imgDocId;

	@Column(name="IMG_FLE_NO", length=16)
	private String imgFleNo;

	@Column(name="MDA_FRMT_TYP_CDE", nullable=false, length=5)
	private String mdaFrmtTypCde;

	@Column(name="MDA_TYP_CDE", nullable=false, length=5)
	private String mdaTypCde;

	@Column(name="PGM_CDE", nullable=false, length=5)
	private String pgmCde;

	@Column(name="RSP_BLB_LCTN_ADR", length=500)
	private String rspBlbLctnAdr;

	@Column(name="RSP_LNGTH_NO")
	private BigDecimal rspLngthNo;

	@Column(name="RSP_SRC_LCTN_NAM", length=100)
	private String rspSrcLctnNam;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;
	
	@Column(name="MDA_ID", length=500)
	private String mediaId;
	
	//bi-directional many-to-one association to CustCr
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CUSTOMER_ID", referencedColumnName="CUSTOMER_ID"),
		@JoinColumn(name="PRMPT_ID", referencedColumnName="PRMPT_ID"),
		@JoinColumn(name="TSK_ID", referencedColumnName="TSK_ID")
		})
	private CustCr custCr;

	public CrBlb() {
	}
	
	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return this.mediaId;
	}

	/**
	 * @param mediaId the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public long getCrBlbId() {
		return this.crBlbId;
	}

	public void setCrBlbId(long crBlbId) {
		this.crBlbId = crBlbId;
	}

	public String getBatFleNo() {
		return this.batFleNo;
	}

	public void setBatFleNo(String batFleNo) {
		this.batFleNo = batFleNo;
	}

	public byte[] getCrBlb() {
		return this.crBlb;
	}

	public void setCrBlb(byte[] crBlb) {
		this.crBlb = crBlb;
	}

	public BigDecimal getCrPrtId() {
		return this.crPrtId;
	}

	public void setCrPrtId(BigDecimal crPrtId) {
		this.crPrtId = crPrtId;
	}

	public BigDecimal getCrSeq() {
		return this.crSeq;
	}

	public void setCrSeq(BigDecimal crSeq) {
		this.crSeq = crSeq;
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

	public String getImgDocId() {
		return this.imgDocId;
	}

	public void setImgDocId(String imgDocId) {
		this.imgDocId = imgDocId;
	}

	public String getImgFleNo() {
		return this.imgFleNo;
	}

	public void setImgFleNo(String imgFleNo) {
		this.imgFleNo = imgFleNo;
	}

	public String getMdaFrmtTypCde() {
		return this.mdaFrmtTypCde;
	}

	public void setMdaFrmtTypCde(String mdaFrmtTypCde) {
		this.mdaFrmtTypCde = mdaFrmtTypCde;
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

	public String getRspBlbLctnAdr() {
		return this.rspBlbLctnAdr;
	}

	public void setRspBlbLctnAdr(String rspBlbLctnAdr) {
		this.rspBlbLctnAdr = rspBlbLctnAdr;
	}

	public BigDecimal getRspLngthNo() {
		return this.rspLngthNo;
	}

	public void setRspLngthNo(BigDecimal rspLngthNo) {
		this.rspLngthNo = rspLngthNo;
	}

	public String getRspSrcLctnNam() {
		return this.rspSrcLctnNam;
	}

	public void setRspSrcLctnNam(String rspSrcLctnNam) {
		this.rspSrcLctnNam = rspSrcLctnNam;
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

	public CustCr getCustCr() {
		return this.custCr;
	}

	public void setCustCr(CustCr custCr) {
		this.custCr = custCr;
	}

}