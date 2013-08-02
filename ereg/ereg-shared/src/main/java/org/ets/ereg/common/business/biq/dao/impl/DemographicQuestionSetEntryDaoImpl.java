package org.ets.ereg.common.business.biq.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.common.business.biq.dao.DemographicQuestionSetEntryDao;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetEntry;
import org.ets.ereg.domain.interfaces.model.i18n.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("demographicQuestionSetEntryDao")
public class DemographicQuestionSetEntryDaoImpl extends AbstractDaoImpl<DemographicQuestionSetEntry> implements DemographicQuestionSetEntryDao {

	private static Logger logger = LoggerFactory.getLogger(DemographicQuestionSetEntryDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DemographicQuestionSetEntry> getDemographicQuestions(
			String programCode, String settypeCode, boolean isCustomerSort) {
		
		logger.debug("Inside DemographicQuestionSetEntryDaoImpl:getDemographicQuestions()");
		
		Query dmgrphQuery = null;
		List<DemographicQuestionSetEntry> list= null;
		
		if(isCustomerSort){
			dmgrphQuery = entityManager.createNamedQuery("DemographicQuestions.getDemographicQuestionsOrderByQstnDsplySeq");
		}else{
			dmgrphQuery = entityManager.createNamedQuery("DemographicQuestions.getDemographicQuestionsOrderByAdminDsplySeq");
		}
		
		dmgrphQuery.setParameter("programCode", programCode);
		dmgrphQuery.setParameter("typeCode", settypeCode);	
		
		list = (List<DemographicQuestionSetEntry>) dmgrphQuery.getResultList();
		
		return list;
	}

	@Override
	public Translation getTranslationText(String languageCode, Long i18nId) {
		
		Query  query = entityManager.createNamedQuery("DemographicQuestions.getTranslationText");
		query.setParameter("languageCode", languageCode);
		query.setParameter("internationalConentId", i18nId);
		
		Object obj = query.getSingleResult();
		
		if(obj!=null){
			return (Translation)obj;
		}
		
		return null;
	}

	@Override
	public Class<DemographicQuestionSetEntry> getEntityClass() {		
		return DemographicQuestionSetEntry.class;
	}	

	
}
