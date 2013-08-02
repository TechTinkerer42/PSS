package org.ets.ereg.domain.biq;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ets.ereg.domain.i18n.InternationalContentImpl;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestion;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionTrigger;
import org.ets.ereg.domain.interfaces.model.biq.DemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.id.DemographicResponseId;
import org.ets.ereg.domain.interfaces.model.i18n.InternationalContent;
import org.hibernate.annotations.Type;

@Entity
@Table(name="DMGRPH_RSP")
public class DemographicResponseImpl implements DemographicResponse,
		Serializable {

	/**
	 * 
	 */
	 private static final long serialVersionUID = 1L;
	
	 @EmbeddedId
	 @AttributeOverrides({
			@AttributeOverride(name = "demographicQuestionNo", column = @Column(name = "DMGRPH_QSTN_NO", nullable = false, length=9)),
			@AttributeOverride(name = "demographicRespNo", column = @Column(name = "DMGRPH_RSP_NO", nullable = false, length=9))
	 })	
	 private DemographicResponseId demographicRespId=new DemographicResponseId();
	 
	 @JoinColumn(name="INTL_CNTNT_ID")
	 @ManyToOne(fetch = FetchType.EAGER, targetEntity = InternationalContentImpl.class, optional=true)
	 private InternationalContent internationalContentId;
	
	 @ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicQuestionImpl.class)
	 @JoinColumn(name = "DMGRPH_QSTN_NO", nullable = false, insertable = false, updatable = false)
	 private DemographicQuestion demographicQstnNo;
	 
	 
	 public DemographicQuestion getDemographicQstnNo() {
		return demographicQstnNo;
	 }

	 public void setDemographicQstnNo(DemographicQuestion demographicQstnNo) {
		this.demographicQstnNo = demographicQstnNo;
	 }

	@Column(name="DMGRPH_VLD_RSP_SEQ",nullable=false,length=5)
	@OrderBy("demographicValidRespSeqNo ASC")
	private Long demographicValidRespSeqNo;
	 
	 @Column(name="TST_ADM_VLD_RSP_FRFM_FLG",nullable=false,length=1)
	 @Type(type="yes_no")
	 private Boolean testAdmValidRespFrmFlg;
	 
	 @Column(name="TST_ADM_VLD_RSP_FRFM_O_FLG",nullable=false,length=1)
	 @Type(type="yes_no")
	 private Boolean testAdmValidRespFrmOrgFlg;
	 
	 @Temporal(TemporalType.DATE)
	 @Column(name="DMGRPH_VLD_RSP_EFF_DT")
	 private Date  demographicValidRespEffDate;
	 
	 @Temporal(TemporalType.DATE)
	 @Column(name="DMGRPH_VLD_RSP_EXPRTN_DT")
	 private Date demographicValidRespExprtnDate;
			 	
	@Column(name="RSP_LBL",nullable=false,length=5)
	private String respLable;
	 
	@Column(name="DMGRPH_VLD_RSP_ACTV_FLG",nullable=false,length=1)
	@Type(type="yes_no")
	private Boolean demographicValidRespActFlg;
	
	@OneToMany(mappedBy="demograpicRespNo", targetEntity = DemographicQuestionTriggerImpl.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DemographicQuestionTrigger> demographicQstnTriggers;
	 
	@Override
	public Set<DemographicQuestionTrigger> getDemographicQstnTriggers() {
		return demographicQstnTriggers;
	}

	@Override 
	public void setDemographicQstnTriggers(
			Set<DemographicQuestionTrigger> demographicQstnTriggers) {
		this.demographicQstnTriggers = demographicQstnTriggers;
	}

	@Override
	public DemographicResponseId getId() {
		return demographicRespId;
	}

	@Override
	public void setId(DemographicResponseId demographicRespId) {
		this.demographicRespId = demographicRespId;
	}

	@Override
	public Long getDemographicValidRespSeqNo() {
		return demographicValidRespSeqNo;
	}

	@Override
	public void setDemographicValidRespSeqNo(Long demographicValidRespSeqNo) {
		this.demographicValidRespSeqNo = demographicValidRespSeqNo;
	}

	@Override
	public Boolean getTestAdmValidRespFrmFlg() {
		return testAdmValidRespFrmFlg;
	}

	@Override
	public void setTestAdmValidRespFrmFlg(Boolean testAdmValidRespFrmFlg) {
		this.testAdmValidRespFrmFlg = testAdmValidRespFrmFlg;
	}

	@Override
	public Boolean getTestAdmValidRespFrmOrgFlg() {
		return testAdmValidRespFrmOrgFlg;
	}

	@Override
	public void setTestAdmValidRespFrmOrgFlg(Boolean testAdmValidRespFrmOrgFlg) {
		this.testAdmValidRespFrmOrgFlg = testAdmValidRespFrmOrgFlg;
	}

	@Override
	public Date getDemographicValidRespEffDate() {
		return demographicValidRespEffDate;
	}

	@Override
	public void setDemographicValidRespEffDate(Date demographicValidRespEffDate) {
		this.demographicValidRespEffDate = demographicValidRespEffDate;
	}

	@Override
	public Date getDemographicValidRespExprtnDate() {
		return demographicValidRespExprtnDate;
	}

	@Override
	public void setDemographicValidRespExprtnDate(
			Date demographicValidRespExprtnDate) {
		this.demographicValidRespExprtnDate = demographicValidRespExprtnDate;
	}

	@Override
	public String getRespLable() {
		return respLable;
	}

	@Override
	public void setRespLable(String respLable) {
		this.respLable = respLable;
	}

	@Override
	public Boolean getDemographicValidRespActFlg() {
		return demographicValidRespActFlg;
	}

	@Override
	public void setDemographicValidRespActFlg(Boolean demographicValidRespActFlg) {
		this.demographicValidRespActFlg = demographicValidRespActFlg;
	}
	
	@Override 
	public InternationalContent getInternationalContentId() {
		return internationalContentId;
	}

	@Override 
	public void setInternationalContentId(InternationalContent internationalContentId) {
		this.internationalContentId = internationalContentId;
	}
	
	public void addDemographicQstnTrigger(DemographicQuestionTrigger dmgrphQstnTrigger) {
		dmgrphQstnTrigger.setDemograpicRespNo(this);
		demographicQstnTriggers.add(dmgrphQstnTrigger);
	}

}
