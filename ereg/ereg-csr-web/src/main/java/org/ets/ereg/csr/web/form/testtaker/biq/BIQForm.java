package org.ets.ereg.csr.web.form.testtaker.biq;


import java.util.List;

import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.vo.ProfileVO;



public class BIQForm {


	//private long testTakerCustomerId;
	private ProfileVO profile;
	
//	private List<DemographicQuestionVO> demographicQuestions;


	/*public ETSCustomer getTestTaker() {
		return testTaker;
	}

	public void setTestTaker(ETSCustomer testTaker) {
		this.testTaker = testTaker;
	}*/

/*	public List<DemographicQuestionVO> getDemographicQuestions() {
		return demographicQuestions;
	}

	public void setDemographicQuestions(
			List<DemographicQuestionVO> demographicQuestions) {
		this.demographicQuestions = demographicQuestions;
	}*/

	public ProfileVO getProfile() {
		return profile;
	}

	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}

	


}
