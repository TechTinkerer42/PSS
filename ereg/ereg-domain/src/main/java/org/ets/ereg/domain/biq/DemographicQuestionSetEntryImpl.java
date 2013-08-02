package org.ets.ereg.domain.biq;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestion;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSet;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetEntry;
import org.ets.ereg.domain.interfaces.model.biq.id.DemographicQuestionSetEntryId;
import org.hibernate.annotations.Type;

@Entity
@Table(name="DMGRPH_QSTN_SET_ENTRY")
public class DemographicQuestionSetEntryImpl implements
		DemographicQuestionSetEntry, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "setId", column = @Column(name = "SET_ID", nullable = false, length=5)),
			@AttributeOverride(name = "demographicQuestionNo", column = @Column(name = "DMGRPH_QSTN_NO", nullable = false, length=9))
	})	
	private DemographicQuestionSetEntryId id = new DemographicQuestionSetEntryId();
	
	@Column(name="RSP_REQ_FLG",length=1,nullable=false)
	@Type(type="yes_no")
	private Boolean respReqFlg;	
	
	@Column(name="QSTN_DSPLY_SEQ")
	private Long questionDisplaySeqNo;
	
	@Column(name="ADMIN_DSPLY_SEQ",nullable=false)
	private Long adminDisplaySeqNo;
	
	@Override
	public Long getAdminDisplaySeqNo() {
		return adminDisplaySeqNo;
	}

	@Override
	public void setAdminDisplaySeqNo(Long adminDisplaySeqNo) {
		this.adminDisplaySeqNo = adminDisplaySeqNo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicQuestionSetImpl.class)
	@JoinColumn(name = "SET_ID", nullable = false, insertable = false, updatable = false)
	private DemographicQuestionSet demographicQstnSet;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicQuestionImpl.class)
	@JoinColumn(name = "DMGRPH_QSTN_NO", nullable = false, insertable = false, updatable = false)
	private DemographicQuestion demographicQstn;

	@Override
	public DemographicQuestionSetEntryId getId() {
		return id;
	}

	@Override
	public void setId(DemographicQuestionSetEntryId id) {
		this.id = id;
	}

	@Override
	public Boolean getRespReqFlg() {
		return respReqFlg;
	}

	@Override
	public void setRespReqFlg(Boolean respReqFlg) {
		this.respReqFlg = respReqFlg;
	}

	@Override
	public Long getQuestionDisplaySeqNo() {
		return questionDisplaySeqNo;
	}

	@Override
	public void setQuestionDisplaySeqNo(Long questionDisplaySeqNo) {
		this.questionDisplaySeqNo = questionDisplaySeqNo;
	}

	@Override
	public DemographicQuestion getDemographicQstn() {
		return demographicQstn;
	}

	@Override
	public void setDemographicQstn(DemographicQuestion demographicQstn) {
		this.demographicQstn = demographicQstn;
	}

}
