package org.ets.ereg.common.business.biq.dao;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.biq.ProgramDemographicQuestionSet;

public interface DemographicQuestionsSetDao extends Dao<ProgramDemographicQuestionSet>{
	
	boolean doesDemographicQuestionSetExists(String programCode,String setTypeCode);

}
