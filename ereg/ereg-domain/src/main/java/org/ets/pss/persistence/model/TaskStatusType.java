package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DOC_STS_TYP database table.
 * 
 */
@Entity
@Table(name="TSK_STS_TYP")
public class TaskStatusType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOC_STS_TYP_DOCSTSTYPCDE_GENERATOR", sequenceName="SEQ_DOC_STS_TYP")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOC_STS_TYP_DOCSTSTYPCDE_GENERATOR")
	@Column(name="TSK_STS_TYP_CDE", nullable=false, length=5)
	private String docStsTypCde;

	@Column(name="TSK_STS_TYP_DSC", nullable=false, length=175)
	private String docStsTypDsc;

	@Column(name="TSK_STS_TYP_DSPLY_SEQ")
	private BigDecimal docStsTypDsplySeq;

	@Column(name="DSPLY_DTA_FLG", nullable=false, length=1)
	private String dsplyDtaFlg;

	//bi-directional many-to-one association to AsgndTsk
	@OneToMany(mappedBy="docStsTyp")
	private Set<AsgndTsk> asgndTsks;

	public TaskStatusType() {
	}

	public String getDocStsTypCde() {
		return this.docStsTypCde;
	}

	public void setDocStsTypCde(String docStsTypCde) {
		this.docStsTypCde = docStsTypCde;
	}

	public String getDocStsTypDsc() {
		return this.docStsTypDsc;
	}

	public void setDocStsTypDsc(String docStsTypDsc) {
		this.docStsTypDsc = docStsTypDsc;
	}

	public BigDecimal getDocStsTypDsplySeq() {
		return this.docStsTypDsplySeq;
	}

	public void setDocStsTypDsplySeq(BigDecimal docStsTypDsplySeq) {
		this.docStsTypDsplySeq = docStsTypDsplySeq;
	}

	public String getDsplyDtaFlg() {
		return this.dsplyDtaFlg;
	}

	public void setDsplyDtaFlg(String dsplyDtaFlg) {
		this.dsplyDtaFlg = dsplyDtaFlg;
	}


	public Set<AsgndTsk> getAsgndTsks() {
		return this.asgndTsks;
	}

	public void setAsgndTsks(Set<AsgndTsk> asgndTsks) {
		this.asgndTsks = asgndTsks;
	}

	public AsgndTsk addAsgndTsk(AsgndTsk asgndTsk) {
		getAsgndTsks().add(asgndTsk);
		asgndTsk.setDocStsTyp(this);

		return asgndTsk;
	}

	public AsgndTsk removeAsgndTsk(AsgndTsk asgndTsk) {
		getAsgndTsks().remove(asgndTsk);
		asgndTsk.setDocStsTyp(null);

		return asgndTsk;
	}

}