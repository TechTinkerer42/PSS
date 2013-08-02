package org.ets.ereg.domain.biq;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetEntry;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionTrigger;
import org.ets.ereg.domain.interfaces.model.biq.DemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.id.DemographicQuestionTriggerId;

@Entity
@Table(name="DMGRPH_QSTN_TRGR")
public class DemographicQuestionTriggerImpl implements
		DemographicQuestionTrigger, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "triggeringDemographicQstnNo", column = @Column(name = "TRIGGERING_DMGRPH_QSTN_NO", nullable = false, length=9)),
			@AttributeOverride(name = "triggeringDemographicRespNo", column = @Column(name = "TRIGGERING_DMGRPH_RSP_NO", nullable = false, length=9)),
			@AttributeOverride(name = "setId", column = @Column(name = "SET_ID", nullable = false, length=5)),
			@AttributeOverride(name = "demographicQstnNo", column = @Column(name = "DMGRPH_QSTN_NO", nullable = false, length=9))
			
	})	
	private DemographicQuestionTriggerId demographicQstnTriggerId = new DemographicQuestionTriggerId();
	
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicResponseImpl.class)
	@JoinColumns({@JoinColumn(name="TRIGGERING_DMGRPH_QSTN_NO", referencedColumnName="DMGRPH_QSTN_NO",nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "TRIGGERING_DMGRPH_RSP_NO",referencedColumnName="DMGRPH_RSP_NO", nullable = false, insertable = false, updatable = false)})
	private DemographicResponse demograpicRespNo;
	

	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicQuestionSetEntryImpl.class)
	@JoinColumns({@JoinColumn(name = "DMGRPH_QSTN_NO", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "SET_ID", nullable = false, insertable = false, updatable = false)})
	private DemographicQuestionSetEntry demographicQstnSetEntry;
	

	@Override
	public DemographicResponse getDemograpicRespNo() {
		return demograpicRespNo;
	}
	
	@Override
	public void setDemograpicRespNo(DemographicResponse demograpicRespNo) {
		this.demograpicRespNo = demograpicRespNo;
	}
	
	@Override
	public DemographicQuestionTriggerId getDemographicQstnTriggerId() {
		return demographicQstnTriggerId;
	}

	@Override
	public void setDemographicQstnTriggerId(
			DemographicQuestionTriggerId demographicQstnTriggerId) {
		this.demographicQstnTriggerId = demographicQstnTriggerId;
	}

}
