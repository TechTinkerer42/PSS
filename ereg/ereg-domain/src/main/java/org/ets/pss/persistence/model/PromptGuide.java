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
@Table(name="PRMPT_GUIDES")
public class PromptGuide implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GUIDE_ID", nullable=false)
	private Long guideId;
	
	
	@Column(name="PRMPT_ID", nullable=false)
	private Long promptId;

	@Column(name="PRMPT_TXT", nullable=true)
	private String promptText;
	
	@Column(name="DSP_SEQ_NO", nullable=true)
	private Integer displaySequence;

	public Long getGuideId() {
		return guideId;
	}

	public void setGuideId(Long guideId) {
		this.guideId = guideId;
	}

	public Long getPromptId() {
		return promptId;
	}

	public void setPromptId(Long promptId) {
		this.promptId = promptId;
	}

	public String getPromptText() {
		return promptText;
	}

	public void setPromptText(String promptText) {
		this.promptText = promptText;
	}

	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}
	
	
	
	
}