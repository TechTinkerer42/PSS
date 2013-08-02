package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the PRMPT database table.
 * 
 */
@Entity
@Table(name="PRMPT")
public class Prompt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRMPT_ID", nullable=false)
	private long promptId;


	@Column(name="PGM_CDE", nullable=false, length=5)
	private String programCode;

	@Column(name="PRMPT_ACSN_ID", nullable=false, length=8)
	private String promptAssociationId;

	@Column(name="PRMPT_CLSTR_ID", nullable=false)
	private Long pomptClstrId;

	@Column(name="PRMPT_NAM", length=256)
	private String promptNam;
	
	@Column(name="TTL_NAM_1", length=2000)
	private String title;
	
	@Column(name="INSTR_TXT", nullable=true)
	private String instructions;	

	@Column(name="PRMPT_SEQ")
	private Long displaySequence;

	@Column(name="PRMPT_VRSN_NO", nullable=false)
	private Long promptVersion;

	//bi-directional many-to-one association to CustCr
	@OneToMany(mappedBy="prmpt")
	private Set<CustCr> custCrs;

	//bi-directional many-to-one association to Frm
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FRM_ID")
	private Frm frm;

	//bi-directional one-to-one association to PrmptCntnt
	@OneToOne(mappedBy="prmpt", fetch=FetchType.LAZY)
	private PrmptCntnt promptContent;

	@Column(name="STP_ID", nullable=false)
	private Long stepId;
	
	@Column(name="TSK_ID", nullable=false)
	private Long taskId;	

	@Column(name="ACTIVITY", nullable=true)
	private String activity;	
	
	@Column(name="MDA_TYP_CDE", nullable=true)
	private String media;	
	
	
	/**
	 * @return the media
	 */
	public String getMedia() {
		return media;
	}

	/**
	 * @param media the media to set
	 */
	public void setMedia(String media) {
		this.media = media;
	}

	public long getPromptId() {
		return promptId;
	}

	public void setPromptId(long promptId) {
		this.promptId = promptId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getPromptAssociationId() {
		return promptAssociationId;
	}

	public void setPromptAssociationId(String promptAssociationId) {
		this.promptAssociationId = promptAssociationId;
	}

	public Long getPomptClstrId() {
		return pomptClstrId;
	}

	public void setPomptClstrId(Long pomptClstrId) {
		this.pomptClstrId = pomptClstrId;
	}

	public String getPromptNam() {
		return promptNam;
	}

	public void setPromptNam(String promptNam) {
		this.promptNam = promptNam;
	}

	public Long getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Long displaySequence) {
		this.displaySequence = displaySequence;
	}

	public Long getPromptVersion() {
		return promptVersion;
	}

	public void setPromptVersion(Long promptVersion) {
		this.promptVersion = promptVersion;
	}

	public Set<CustCr> getCustCrs() {
		return custCrs;
	}

	public void setCustCrs(Set<CustCr> custCrs) {
		this.custCrs = custCrs;
	}

	public Frm getFrm() {
		return frm;
	}

	public void setFrm(Frm frm) {
		this.frm = frm;
	}

	public PrmptCntnt getPromptContent() {
		return promptContent;
	}

	public void setPromptContent(PrmptCntnt promptContent) {
		this.promptContent = promptContent;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	
	
}