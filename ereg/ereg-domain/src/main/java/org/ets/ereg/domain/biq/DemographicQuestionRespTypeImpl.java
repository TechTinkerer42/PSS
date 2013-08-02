package org.ets.ereg.domain.biq;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionRespType;
import org.hibernate.annotations.Type;

@Entity
@Table(name="DMGRPH_QSTN_RSP_TYP")
public class DemographicQuestionRespTypeImpl implements DemographicQuestionRespType,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DMGRPH_QSTN_RSP_TYP_CDE", nullable = false, length = 2)
	private String code;

	@Column(name = "DMGRPH_QSTN_RSP_TYP_DSC", nullable = false, length = 70)
	private String description;
	
	@Column(name="DMGRPH_QSTN_RSP_TYP_SEQ",length=4)
	private Long displaySequence;
	
	@Column(name="DMGRPH_QSTN_RSP_TYP_DSPLY_SEQ")
	private Long demographicQstnRespTypDsplySeq;
	
	@Column(name="DSPLY_DTA_FLG")
	@Type(type="yes_no")
    private Boolean isDisplayable;

	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Long getDisplaySequence() {
		return displaySequence;
	}

	@Override
	public void setDisplaySequence(Long displaySequence) {
		this.displaySequence = displaySequence;
	}

	@Override
	public Boolean isDisplayable() {
			return isDisplayable;
	}

	@Override
	public void setDisplayable(Boolean isDisplayable) {
		
		this.isDisplayable = isDisplayable;
	}

	@Override
	public Long getDemographicQstnRespTypDsplySeq() {
		return demographicQstnRespTypDsplySeq;
	}

	@Override
	public void getDemographicQstnRespTypDsplySeq(
			Long demographicQstnRespTypDsplySeq) {
		this.demographicQstnRespTypDsplySeq = demographicQstnRespTypDsplySeq;
	}

}
