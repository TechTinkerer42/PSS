package org.ets.ereg.shared.dao;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.ets.ereg.common.business.biq.dao.DemographicQuestionSetEntryDao;
import org.ets.ereg.common.business.biq.dao.DemographicQuestionsSetDao;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestion;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetEntry;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionTrigger;
import org.ets.ereg.domain.interfaces.model.biq.DemographicResponse;
import org.ets.ereg.domain.interfaces.model.i18n.Translation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestDemographicQuestionsDaoImpl {
	
	private static Logger logger = LoggerFactory.getLogger(TestDemographicQuestionsDaoImpl.class);
	
	@Resource(name="demographicQuestionSetEntryDao")
	private DemographicQuestionSetEntryDao demographicQuestionSetEntryDao;
	
	@Resource(name="demographicQuestionsSetDao")
	private DemographicQuestionsSetDao demographicQuestionsSetDao;
	
	@Test
	public void testGetDemographicQuestions(){
		
		List<DemographicQuestionSetEntry> demographicQstnsSetEntry= demographicQuestionSetEntryDao.getDemographicQuestions("HSE", "PRFL", true);
		
		Assert.assertNotNull(demographicQstnsSetEntry);		
		
		for(DemographicQuestionSetEntry dmgrphSetEntry : demographicQstnsSetEntry){
			
				logger.debug("demographic question setId: {} ",dmgrphSetEntry.getId().getSetId());
				
				DemographicQuestion	demographicQstn = dmgrphSetEntry.getDemographicQstn();
				
				logger.debug("demographic questionNo: {} ",demographicQstn.getDemographicQuestionName());
				logger.debug("demographic questionName: {}",demographicQstn.getDemographicQuestionNo());				
				logger.debug("international content id {}", demographicQstn.getInternationalContentId().getInternationalContentId());
				
				Set<DemographicResponse> dmgrphResps= demographicQstn.getDemographicResps();
				
				for(DemographicResponse dmgrphResp : dmgrphResps){					
					logger.debug("demographic responseNo: {} ",dmgrphResp.getId().getDemographicRespNo());					
					Set<DemographicQuestionTrigger> dmgrphQstnTrgrs = dmgrphResp.getDemographicQstnTriggers();					
					logger.debug("international content id {}",dmgrphResp.getInternationalContentId().getInternationalContentId());
					
					for(DemographicQuestionTrigger dmgrphQstnTrgr : dmgrphQstnTrgrs){
						logger.debug("triggering demographic questionNo {}",dmgrphQstnTrgr.getDemographicQstnTriggerId().getDemographicQstnNo());
					}
				}		
		}
		
	}
	
	@Test
	public void testDoesDemographicQuestionSetExists(){
		
		boolean isExists = demographicQuestionsSetDao.doesDemographicQuestionSetExists("HSE", "PRFL");		
		Assert.assertTrue(isExists);		
	}
	
	@Test
	public void testGetTranslationText(){
		
		Translation translationText = demographicQuestionSetEntryDao.getTranslationText("EN", 1L);		
		Assert.assertNotNull(translationText);
		
		//translationText = demographicQuestionSetEntryDao.getTranslationText("ES", 2L);		
		//Assert.assertNotNull(translationText);
	}
}
