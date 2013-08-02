package org.ets.ereg.profile.vo;

import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;


public class ProfileVO {
	private ETSCustomer customer;
	private ETSAddress address;
	private ETSPhone primaryPhone;
	private ETSPhone alternatePhone;
	private List<DemographicQuestionVO> demographicQuestions=new ArrayList<DemographicQuestionVO>();
	private String custLinkageKey;
	

	public List<DemographicQuestionVO> getDemographicQuestions() {
		return demographicQuestions;
	}

	public void setDemographicQuestions(
			List<DemographicQuestionVO> demographicQuestions) {
		this.demographicQuestions = demographicQuestions;
	}

	public ETSCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ETSCustomer customer) {
		this.customer = customer;
	}

	public ETSAddress getAddress() {
		return address;
	}

	public void setAddress(ETSAddress address) {
		this.address = address;
	}

	public ETSPhone getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(ETSPhone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public ETSPhone getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(ETSPhone alternatePhone) {
		this.alternatePhone = alternatePhone;
	}
	
	public String getCustLinkageKey() {
		return custLinkageKey;
	}

	public void setCustLinkageKey(String custLinkageKey) {
		this.custLinkageKey = custLinkageKey;
	}

}
