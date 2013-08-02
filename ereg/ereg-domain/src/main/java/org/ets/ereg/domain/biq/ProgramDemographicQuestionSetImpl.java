package org.ets.ereg.domain.biq;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSet;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetType;
import org.ets.ereg.domain.interfaces.model.biq.ProgramDemographicQuestionSet;
import org.ets.ereg.domain.interfaces.model.biq.id.ProgramDemographicQuestionSetId;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.hibernate.annotations.Type;

@Entity
@Table(name="PGM_DMGRPH_QSTN_SET")
public class ProgramDemographicQuestionSetImpl implements
		ProgramDemographicQuestionSet, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "setId", column = @Column(name = "SET_ID", nullable = false, length=5)),
			@AttributeOverride(name = "programCode", column = @Column(name = "PGM_CDE", nullable = false, length=5)),
			@AttributeOverride(name = "effectiveDate", column = @Column(name = "EFF_DT", nullable = false))
	})	
	private ProgramDemographicQuestionSetId programDemographicQuestionSetId=new ProgramDemographicQuestionSetId();

	@ManyToOne(fetch = FetchType.EAGER,targetEntity=ProgramTypeImpl.class)
	@JoinColumn(name = "PGM_CDE", nullable = false, insertable = false, updatable = false)
	private ProgramType programCode;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicQuestionSetImpl.class)
	@JoinColumn(name = "SET_ID", nullable = false, insertable = false, updatable = false)
	private DemographicQuestionSet setId;
			
	@Temporal(TemporalType.DATE)
	@Column(name="EXPRTN_DT", nullable=false)
	private Date expirationDate;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicQuestionSetTypeImpl.class,optional=true)
	@JoinColumn(name = "DMGRPH_QSTN_SET_TYP_CDE")
	private DemographicQuestionSetType demographicQstnSetType;
	
	@Override
	public DemographicQuestionSetType getDemographicQstnSetType() {
		return demographicQstnSetType;
	}

	@Override
	public void setDemographicQstnSetType(
			DemographicQuestionSetType demographicQstnSetType) {
		this.demographicQstnSetType = demographicQstnSetType;
	}

	@Type(type="yes_no")
	@Column(name="DTE_USG_TYP_FLG", nullable=false)
    private Boolean dateUsageTypFlg;	
	
	public ProgramDemographicQuestionSetId getProgramDemographicQuestionSetId() {
		return programDemographicQuestionSetId;
	}

	public void setProgramDemographicQuestionSetId(
			ProgramDemographicQuestionSetId programDemographicQuestionSetId) {
		this.programDemographicQuestionSetId = programDemographicQuestionSetId;
	}

	public Boolean getDateUsageTypFlg() {
		return dateUsageTypFlg;
	}

	public void setDateUsageTypFlg(Boolean dateUsageTypFlg) {
		this.dateUsageTypFlg = dateUsageTypFlg;
	}

	@Override
	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}	

}
