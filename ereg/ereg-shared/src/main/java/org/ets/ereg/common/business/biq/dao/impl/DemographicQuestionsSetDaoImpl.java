package org.ets.ereg.common.business.biq.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.common.business.biq.dao.DemographicQuestionsSetDao;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.biq.ProgramDemographicQuestionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("demographicQuestionsSetDao")
public class DemographicQuestionsSetDaoImpl extends AbstractDaoImpl<ProgramDemographicQuestionSet> implements
		DemographicQuestionsSetDao {

	private static Logger logger = LoggerFactory.getLogger(DemographicQuestionsSetDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean doesDemographicQuestionSetExists(String programCode,
			String setTypeCode) {
		
		logger.debug("Inside DemographicQuestionsSetDaoImpl:doesDemographicQuestionSetExists()");
		
		Query query = entityManager.createNamedQuery("DemographicQuestions.doesDemographicQstnSetExists");
		query.setParameter("programCode", programCode);
		query.setParameter("typeCode", setTypeCode);
		
		List<ProgramDemographicQuestionSet> dmgrphQstnSetList= (List<ProgramDemographicQuestionSet>)query.getResultList();		
		
		if(dmgrphQstnSetList.size() > 0){
			return true;
		}
		
		return false;
	}

	@Override
	public Class<ProgramDemographicQuestionSet> getEntityClass() {		
		return ProgramDemographicQuestionSet.class;
	}

}
