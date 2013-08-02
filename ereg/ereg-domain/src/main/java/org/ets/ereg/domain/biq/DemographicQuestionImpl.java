package org.ets.ereg.domain.biq;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.ets.ereg.domain.i18n.InternationalContentImpl;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestion;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionRespType;
import org.ets.ereg.domain.interfaces.model.biq.DemographicResponse;
import org.ets.ereg.domain.interfaces.model.i18n.InternationalContent;

@Entity
@Table(name="DMGRPH_QSTN")
public class DemographicQuestionImpl implements DemographicQuestion,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="DMGRPH_QSTN_NO",nullable=false,length=9)
	private Long demographicQuestionNo;	
		
	@JoinColumn(name="DMGRPH_QSTN_RSP_TYP_CDE")
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = DemographicQuestionRespTypeImpl.class, optional=true)	
	private DemographicQuestionRespType demographicQuestionRespTypCode;	
	
	@Column(name="DMGRPH_QSTN_NAM",nullable=false,length=20)
	private String  demographicQuestionName;
	
	@JoinColumn(name="INTL_CNTNT_ID")
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = InternationalContentImpl.class, optional=true)
	private InternationalContent internationalContentId;
		
	@OneToMany(mappedBy="demographicQstnNo", targetEntity= DemographicResponseImpl.class,fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@OrderBy("demographicValidRespSeqNo ASC")
	private Set<DemographicResponse> demographicResps;
	

	@Override
	public Long getDemographicQuestionNo() {	
		return demographicQuestionNo;
	}

	@Override
	public void setDemographicQuestionNo(Long demographicQuestionNo) {
		this.demographicQuestionNo = demographicQuestionNo;		
	}
	
	@Override
	public DemographicQuestionRespType getDemographicQuestionRespTypCode() {
		return demographicQuestionRespTypCode;
	}

	@Override
	public void setDemographicQuestionRespTypCode(
			DemographicQuestionRespType demographicQuestionRespTypCode) {
		this.demographicQuestionRespTypCode = demographicQuestionRespTypCode;
	}

	@Override
	public String getDemographicQuestionName() {
		return demographicQuestionName;
	}

	@Override
	public void setDemographicQuestionName(String demographicQuestionName) {
		this.demographicQuestionName = demographicQuestionName;
	}

	@Override
	public InternationalContent getInternationalContentId() {
		return internationalContentId;
	}

	@Override
	public void setInternationalContentId(InternationalContent internationalContentId) {
		this.internationalContentId = internationalContentId;
	}
	
	@Override
	public Set<DemographicResponse> getDemographicResps() {
		return demographicResps;
	}

	@Override
	public void setDemographicResps(Set<DemographicResponse> demographicResps) {
		this.demographicResps = demographicResps;
	}
	
	public void addDemographicResponse(DemographicResponse dmgrphResp) {
		dmgrphResp.setDemographicQstnNo(this);
		demographicResps.add(dmgrphResp);
	}

}
