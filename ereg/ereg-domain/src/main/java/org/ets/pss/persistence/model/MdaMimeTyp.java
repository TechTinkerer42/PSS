package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the MDA_MIME_TYP database table.
 * 
 */
@Entity
@Table(name="MDA_MIME_TYP")
public class MdaMimeTyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MDA_MIME_TYP_MIMETYPID_GENERATOR", sequenceName="SEQ_MDA_MIME_TYP")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MDA_MIME_TYP_MIMETYPID_GENERATOR")
	@Column(name="MIME_TYP_ID", nullable=false)
	private long mimeTypId;

	@Column(name="CREATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal createdByAdminUsrId;

	@Column(name="CREATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal createdByCustomerId;

	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name="DATE_UPDATED")
	private Timestamp dateUpdated;

	@Column(name="FLE_EXTNSN_DSC", length=50)
	private String fleExtnsnDsc;

	@Column(name="MIME_TYP_DSC", length=100)
	private String mimeTypDsc;

	@Column(name="MIME_TYP_NAM", nullable=false, length=50)
	private String mimeTypNam;

	@Column(name="UPDATED_BY_ADMIN_USR_ID", precision=19)
	private BigDecimal updatedByAdminUsrId;

	@Column(name="UPDATED_BY_CUSTOMER_ID", precision=19)
	private BigDecimal updatedByCustomerId;

	//bi-directional many-to-one association to Doc
	@OneToMany(mappedBy="mdaMimeTyp")
	private Set<Doc> docs;

	public MdaMimeTyp() {
	}

	public long getMimeTypId() {
		return this.mimeTypId;
	}

	public void setMimeTypId(long mimeTypId) {
		this.mimeTypId = mimeTypId;
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

	public String getFleExtnsnDsc() {
		return this.fleExtnsnDsc;
	}

	public void setFleExtnsnDsc(String fleExtnsnDsc) {
		this.fleExtnsnDsc = fleExtnsnDsc;
	}

	public String getMimeTypDsc() {
		return this.mimeTypDsc;
	}

	public void setMimeTypDsc(String mimeTypDsc) {
		this.mimeTypDsc = mimeTypDsc;
	}

	public String getMimeTypNam() {
		return this.mimeTypNam;
	}

	public void setMimeTypNam(String mimeTypNam) {
		this.mimeTypNam = mimeTypNam;
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

	public Set<Doc> getDocs() {
		return this.docs;
	}

	public void setDocs(Set<Doc> docs) {
		this.docs = docs;
	}

	public Doc addDoc(Doc doc) {
		getDocs().add(doc);
		doc.setMdaMimeTyp(this);

		return doc;
	}

	public Doc removeDoc(Doc doc) {
		getDocs().remove(doc);
		doc.setMdaMimeTyp(null);

		return doc;
	}

}