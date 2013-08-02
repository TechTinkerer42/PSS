package org.ets.ereg.profile.biq.service;

import java.util.List;

import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;

public interface ProfileDemographicQuestionService {
	
	List<DemographicQuestionVO> getDemographicQuestions(Long customerId,String programCode,
									String languageCode, String setTypeCode,Boolean isCustomerSort);
	void saveProfileDemographicResponses(Long customerId,List<DemographicQuestionVO> demographicQuestions);
	boolean doesProfileDemographicQuestionSetExists(String programCode,String setTypeCode);
	boolean areProfileRequiredDQAnswered(Long customerId,String programCode);
	boolean areProfileDQAnswered(Long customerId,String programCode);
	public void setGotTriggeredFlag(List<DemographicQuestionVO> demographicQstnVOList);
	

}
