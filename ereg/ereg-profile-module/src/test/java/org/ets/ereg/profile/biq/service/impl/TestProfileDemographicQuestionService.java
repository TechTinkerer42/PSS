package org.ets.ereg.profile.biq.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.ets.ereg.common.business.biq.dao.DemographicQuestionSetEntryDao;
import org.ets.ereg.common.business.biq.dao.DemographicQuestionsSetDao;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.domain.biq.CustomerDemographicResponseImpl;
import org.ets.ereg.domain.biq.DemographicQuestionImpl;
import org.ets.ereg.domain.biq.DemographicQuestionRespTypeImpl;
import org.ets.ereg.domain.biq.DemographicQuestionSetEntryImpl;
import org.ets.ereg.domain.biq.DemographicQuestionTriggerImpl;
import org.ets.ereg.domain.biq.DemographicResponseImpl;
import org.ets.ereg.domain.i18n.ContentTypeImpl;
import org.ets.ereg.domain.i18n.InternationalContentImpl;
import org.ets.ereg.domain.i18n.TranslationImpl;
import org.ets.ereg.domain.interfaces.model.biq.CustomerDemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestion;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionRespType;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetEntry;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionTrigger;
import org.ets.ereg.domain.interfaces.model.biq.DemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.id.CustomerDemographicResponseId;
import org.ets.ereg.domain.interfaces.model.biq.id.DemographicQuestionSetEntryId;
import org.ets.ereg.domain.interfaces.model.biq.id.DemographicQuestionTriggerId;
import org.ets.ereg.domain.interfaces.model.biq.id.DemographicResponseId;
import org.ets.ereg.domain.interfaces.model.i18n.ContentType;
import org.ets.ereg.domain.interfaces.model.i18n.InternationalContent;
import org.ets.ereg.domain.interfaces.model.i18n.Translation;
import org.ets.ereg.domain.interfaces.model.i18n.id.TranslationId;
import org.ets.ereg.profile.biq.dao.CustomerDemographicResponseDao;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JMock.class)
public class TestProfileDemographicQuestionService {

	private static Logger logger = LoggerFactory.getLogger(TestProfileDemographicQuestionService.class);
	
	private static Mockery mockingContext = new Mockery();
			
	private static DemographicQuestionsSetDao mockedDemographicQuestionSetDao;
	private static DemographicQuestionSetEntryDao mockedDemographicQuestionSetEntryDao;	
	private static CustomerDemographicResponseDao mockedCustomerDemographicResponseDao;
	private static ProfileDemographicQuestionServiceImpl profileDemographicQuestionService = new ProfileDemographicQuestionServiceImpl();
	
	private Map<Long,InternationalContent> i18nEntities = createI18nEntity();
	private Map<Long,Long[]> qstnToRespIds = createQstnRespIdsMap();
	private Map<Long,Long[]> qstnToRespI18nIds = createQstnToRespContentIdMap();
	private Map<Long,DemographicQuestion> dmgrphQstns = createDmgrphQstn();
	private Map<Long,Translation> translationTxtEntities = createTranslationEntity();
	
	@BeforeClass
	public static void setup() {
		setupMockObjects();
		profileDemographicQuestionService.
				setCustomerDemographicResponseDao(mockedCustomerDemographicResponseDao);
		profileDemographicQuestionService.setDemographicQuestionSetDao(mockedDemographicQuestionSetDao);
		
		profileDemographicQuestionService.setDemographicQuestionSetEntryDao(mockedDemographicQuestionSetEntryDao);
		
	}
	private static void setupMockObjects() {
	
		mockedDemographicQuestionSetDao = mockingContext.mock(DemographicQuestionsSetDao.class);
		mockedDemographicQuestionSetEntryDao = mockingContext.mock(DemographicQuestionSetEntryDao.class);
		mockedCustomerDemographicResponseDao = mockingContext.mock(CustomerDemographicResponseDao.class);		
	}
	
	@Test
	public void testSaveProfileDemographicResponses(){
		
		
		final List<DemographicQuestionVO> demographicQuestions = createDmgrphQstnVO();		
		
		final List<CustomerDemographicResponse> customerDemographicResponses = 
				createCustomerDmgrphResponses(502L, new Long[]{1L,2L,3L,5L},
						new Long[]{1L,4L,7L,19L});
	  
		for(DemographicQuestionVO demographicQuestion:demographicQuestions){
			String[] selRespIds = demographicQuestion.getSelectedResponseIds();
			if(null!=selRespIds &&selRespIds.length>0){				
				for(String selRespId:selRespIds){					
					for(CustomerDemographicResponse customerDemographicResponse:customerDemographicResponses){
						if(customerDemographicResponse.getCustomerDemographicRespId().getDemographicRespNo()
									== Long.valueOf(selRespId)){
							final CustomerDemographicResponse customerDmgrphResp =  customerDemographicResponse;
							mockingContext.checking(new Expectations() {
							{
									
								oneOf(mockedCustomerDemographicResponseDao).create();
								will(returnValue(customerDmgrphResp));
							}});
						}
					}
				}
			}
		}
		
		mockingContext.checking(new Expectations() {
			{	
				 one(mockedCustomerDemographicResponseDao).deleteCustomerDemographicResponsesForCurrentSet(502L, 1L);
				 	will(returnValue(null));
				one(mockedCustomerDemographicResponseDao).saveCustomerDemographicResponse(customerDemographicResponses);
          			will(returnValue(null));
				
	            
		    } });
		
		profileDemographicQuestionService.saveProfileDemographicResponses(502L, demographicQuestions);
				
		mockingContext.assertIsSatisfied();
	} 
	
	@Test
	public void testGetDeomographicQuestions(){
		
		final List<DemographicQuestionSetEntry> dmgrphQstnSetEntries = createDmgrphQstnSetEntry();
		
		mockingContext.checking(new Expectations() {
			   {				
	             oneOf(mockedDemographicQuestionSetEntryDao).getDemographicQuestions("HSE","PRFL",true);
	             will(returnValue(dmgrphQstnSetEntries));
	           }
		});
		
		DemographicQuestion dmgrphQuestion = null;
		Set<DemographicResponse> dmgrphQstnResps=null;
		
		for(DemographicQuestionSetEntry dmgrphQstnSetEnt:dmgrphQstnSetEntries){
			//populate demographic question
			dmgrphQuestion = dmgrphQstnSetEnt.getDemographicQstn();
			dmgrphQstnResps= dmgrphQuestion.getDemographicResps();
			
			final Translation translationTxtObj = translationTxtEntities.get(dmgrphQuestion.getInternationalContentId().getInternationalContentId());
			final Long intnlId = dmgrphQuestion.getInternationalContentId().getInternationalContentId();
			
			mockingContext.checking(new Expectations() {
				   {				
		             oneOf(mockedDemographicQuestionSetEntryDao).getTranslationText("EN",intnlId);
		             will(returnValue(translationTxtObj));
		           }
			});			
			
			for(DemographicResponse dmgrphQstnResp:dmgrphQstnResps){
				
				final Long intnlId1 = dmgrphQstnResp.getInternationalContentId().getInternationalContentId();
				final Translation translationTxtObj1 = translationTxtEntities.get(
								dmgrphQstnResp.getInternationalContentId().getInternationalContentId());
				
				mockingContext.checking(new Expectations() {
					   {				
			             oneOf(mockedDemographicQuestionSetEntryDao).getTranslationText("EN",intnlId1);
			             will(returnValue(translationTxtObj1));
			           }
				});	
			}			
			
		}
		
		
		//set customer selected demographic responses		
		final List<CustomerDemographicResponse> custDmgrphResps = 
				createCustomerDmgrphResponses(502L,new Long[]{1L,2L,3L},new Long[]{1L,4L,7L});
		
		

		
	
		mockingContext.checking(new Expectations() {
				   {				
		             oneOf(mockedCustomerDemographicResponseDao).getCustomerDemographicResponses(502L, 1L);
		             will(returnValue(custDmgrphResps));
		           }
		});	
				

		List<DemographicQuestionVO>  dmgrphQstns = profileDemographicQuestionService.getDemographicQuestions(502L, "HSE", "EN", "PRFL", true);
		
		for(DemographicQuestionVO qstnVO : dmgrphQstns){			
			String[] respIds = qstnVO.getSelectedResponseIds();			
			if(respIds!=null && respIds.length>0){
				for(String respId : respIds){
					logger.debug("Selected respons id(s) {} for question {}",respId,qstnVO.getQstnNo());
				}
			}
		}
		
		Assert.assertNotNull(dmgrphQstns);
		mockingContext.assertIsSatisfied();
				
	}
	
	@Test
	public void testDoesProfileDemographicQuestionSetExists(){
		
		final Boolean doesPrflDmgrphQstnSetExists=true;
		
		mockingContext.checking(new Expectations() {
			   {				
	             oneOf(mockedDemographicQuestionSetDao).doesDemographicQuestionSetExists("HSE", "PRFL");
	             will(returnValue(doesPrflDmgrphQstnSetExists));
	           }
		});
		
		Boolean doesSetExists = profileDemographicQuestionService.doesProfileDemographicQuestionSetExists("HSE", "PRFL");
		Assert.assertTrue(doesSetExists);
		
		mockingContext.assertIsSatisfied();
		
	}
	
	@Test
	public void testAreProfileRequiredDQAnsweredToTrue(){
		
		final List<DemographicQuestionSetEntry> dmgrphQstnSetEntries = createDmgrphQstnSetEntry();
		
		mockingContext.checking(new Expectations() {
			   {				
	             oneOf(mockedDemographicQuestionSetEntryDao).getDemographicQuestions("HSE","PRFL",true);
	             will(returnValue(dmgrphQstnSetEntries));
	           }
		});
		
		//set customer selected demographic responses		
		final List<CustomerDemographicResponse> custDmgrphResps = 
				createCustomerDmgrphResponses(502L,new Long[]{1L,2L,3L},new Long[]{1L,4L,7L});
	
		mockingContext.checking(new Expectations() {
				   {				
		             oneOf(mockedCustomerDemographicResponseDao).getCustomerDemographicResponses(502L, 1L);
		             will(returnValue(custDmgrphResps));
		           }
		});	
		
		//answered all mandatory questions should return true
		Boolean areProfileReqDQAnswered = profileDemographicQuestionService.areProfileRequiredDQAnswered(502L, "HSE");		
		Assert.assertTrue(areProfileReqDQAnswered);
		
		mockingContext.assertIsSatisfied();
		
	}
	
	@Test
	public void testAreProfileRequiredDQAnsweredToFalse(){
		
		final List<DemographicQuestionSetEntry> dmgrphQstnSetEntries = createDmgrphQstnSetEntry();
		
		mockingContext.checking(new Expectations() {
			   {				
	             oneOf(mockedDemographicQuestionSetEntryDao).getDemographicQuestions("HSE","PRFL",true);
	             will(returnValue(dmgrphQstnSetEntries));
	           }
		});
		
		//set customer selected demographic responses		
		final List<CustomerDemographicResponse> custDmgrphResps = 
				createCustomerDmgrphResponses(502L,new Long[]{5L,6L},new Long[]{19L,25L});
	
		mockingContext.checking(new Expectations() {
				   {				
		             oneOf(mockedCustomerDemographicResponseDao).getCustomerDemographicResponses(502L, 1L);
		             will(returnValue(custDmgrphResps));
		           }
		});	
		
		//answered all mandatory questions should return true
		Boolean areProfileReqDQAnswered = profileDemographicQuestionService.areProfileRequiredDQAnswered(502L, "HSE");		
		Assert.assertFalse(areProfileReqDQAnswered);
		
		mockingContext.assertIsSatisfied();
		
	}
	
	@Test
	public void testAreProfileDQAnsweredToFalse(){
		
		final List<DemographicQuestionSetEntry> dmgrphQstnSetEntries = createDmgrphQstnSetEntry();
		
		mockingContext.checking(new Expectations() {
			   {				
	             oneOf(mockedDemographicQuestionSetEntryDao).getDemographicQuestions("HSE","PRFL",true);
	             will(returnValue(dmgrphQstnSetEntries));
	           }
		});
		
		//customer answered only mandatory questions..	
		final List<CustomerDemographicResponse> custDmgrphResps = 
				createCustomerDmgrphResponses(502L,new Long[]{1L,2L,3L},new Long[]{1L,4L,7L});
	
		mockingContext.checking(new Expectations() {
				   {				
		             oneOf(mockedCustomerDemographicResponseDao).getCustomerDemographicResponses(502L, 1L);
		             will(returnValue(custDmgrphResps));
		           }
		});	
		
		//optional quetions are not answered ... so method should return false
		Boolean areProfileReqDQAnswered = profileDemographicQuestionService.areProfileDQAnswered(502L, "HSE");		
		Assert.assertFalse(areProfileReqDQAnswered);		
		mockingContext.assertIsSatisfied();
	}
	
	@Test
	public void testAreProfileDQAnsweredToTrue(){
		
		final List<DemographicQuestionSetEntry> dmgrphQstnSetEntries = createDmgrphQstnSetEntry();
		
		mockingContext.checking(new Expectations() {
			   {				
	             oneOf(mockedDemographicQuestionSetEntryDao).getDemographicQuestions("HSE","PRFL",true);
	             will(returnValue(dmgrphQstnSetEntries));
	           }
		});
		
		//customer answered all questions including optional	
		final List<CustomerDemographicResponse> custDmgrphResps = 
				createCustomerDmgrphResponses(503L,new Long[]{1L,2L,3L,5L,6L,7L,8L},
						new Long[]{1L,4L,7L,19L,25L,34L,53L});
	
		mockingContext.checking(new Expectations() {
				   {				
		             oneOf(mockedCustomerDemographicResponseDao).getCustomerDemographicResponses(503L, 1L);
		             will(returnValue(custDmgrphResps));
		           }
		});	
		
		//optional quetions are not answered ... so method should return false
		boolean areProfileReqDQAnswered = profileDemographicQuestionService.areProfileDQAnswered(503L, "HSE");		
		Assert.assertTrue(areProfileReqDQAnswered);
				
		
		mockingContext.assertIsSatisfied();
	}
	
	private List<DemographicQuestionVO> createDmgrphQstnVO(){		
		
	    Map<Long,String[]> qstnToResp = new HashMap<Long, String[]>();
	    qstnToResp.put(1L, new String[]{"1"});
	    qstnToResp.put(2L, new String[]{"4"});
	    qstnToResp.put(3L, new String[]{"7"});
	    qstnToResp.put(5L, new String[]{"19"});
	    
		List<DemographicQuestionVO> dmgrphQstnVOList = new ArrayList<DemographicQuestionVO>();
		DemographicQuestionVO dmgrphQstnVO = null;
		
		Set<Long> keySet = qstnToResp.keySet();
		Iterator<Long> it = keySet.iterator();
		Long qstnNo = null;
		while(it.hasNext()){			
			dmgrphQstnVO = new DemographicQuestionVO();
			qstnNo =it.next();
			dmgrphQstnVO.setSetId(1L);
			dmgrphQstnVO.setQstnNo(qstnNo);
			dmgrphQstnVO.setSeqNo(qstnNo);
			dmgrphQstnVO.setSelectedResponseIds(qstnToResp.get(qstnNo));
			dmgrphQstnVOList.add(dmgrphQstnVO);
		}
		
		return dmgrphQstnVOList;
		
	}
	
	private List<CustomerDemographicResponse> createCustomerDmgrphResponses(Long customerId,
			Long[] qstns,Long[] responses){
		List<CustomerDemographicResponse> custDmgrphResps = new ArrayList<CustomerDemographicResponse>();
		
		CustomerDemographicResponse custDmgrphResp = null;
		CustomerDemographicResponseId custDmgrphId = null;
		for(int i=0;i<qstns.length;i++){
			custDmgrphResp =  new CustomerDemographicResponseImpl();
			custDmgrphId = custDmgrphResp.getCustomerDemographicRespId();
			custDmgrphId.setCustomerId(customerId);
			custDmgrphId.setDemographicQstnNo(qstns[i]);
			custDmgrphId.setDemographicRespNo(responses[i]);
			custDmgrphResps.add(custDmgrphResp);
		}
		return custDmgrphResps;
	}
	
	private Map<Long,DemographicQuestion> createDmgrphQstn(){
		
		Map<Long,DemographicQuestion> dmgrphQstnList = new HashMap<Long,DemographicQuestion>();
		
		String[] qstnNames = new String[]{ "HSET Demo Question1",
				"HSET Demo Question2",
				"HSET Demo Question3",
				"HSET Demo Question4",
				"HSET Demo Question5",
				"HSET Demo Question6",
				"HSET Demo Question7",
				"HSET Demo Question8"};
		
		String[] qstnTypes = new String[]{
				"S",
				"S",
				"S",
				"S",
				"M",
				"S",
				"M",
				"S"
		};
		
		DemographicQuestion dmgrphQstn = null;
		DemographicQuestionRespType dmgrphRespType = null;
		
		for(int i=0;i<qstnNames.length;i++){
			dmgrphQstn = new DemographicQuestionImpl();			
			dmgrphQstn.setDemographicQuestionNo(Long.valueOf(i+1));
			if(qstnTypes[i].equals("S")){
				dmgrphRespType = createDmgrphQstnRespType(qstnTypes[i],"drop down",1L);
			}else if(qstnTypes[i].equals("M")){
				dmgrphRespType = createDmgrphQstnRespType(qstnTypes[i],"Multiple Choice",2L);
			}else if(qstnTypes[i].equals("F")){
				dmgrphRespType = createDmgrphQstnRespType(qstnTypes[i],"Free Form Text",3L);
			}
			dmgrphQstn.setDemographicQuestionRespTypCode(dmgrphRespType);
			dmgrphQstn.setDemographicQuestionName(qstnNames[i]);			
			dmgrphQstn.setInternationalContentId(i18nEntities.get(Long.valueOf(i+1)));
			dmgrphQstn.setDemographicResps(createDmgrphResponses(dmgrphQstn,
						dmgrphQstn.getDemographicQuestionNo(), 
						qstnToRespIds.get(dmgrphQstn.getDemographicQuestionNo()),
						qstnToRespI18nIds.get(dmgrphQstn.getDemographicQuestionNo())));
			
			dmgrphQstnList.put(dmgrphQstn.getDemographicQuestionNo(), dmgrphQstn);
		}
		
		return dmgrphQstnList;

	}	
	
	private Set<DemographicResponse> createDmgrphResponses(DemographicQuestion dmgrphQstn,Long qstnNo,Long[] respIds,Long[] i18nIds){
		
		Set<DemographicResponse> dmgrphRespList = new HashSet<DemographicResponse>();
		DemographicResponse dmgrphResponse=null;
		DemographicResponseId dmgrphRespId = null;
		
		Date effDate = DateHandler.convertToDate(2013,1,1);
		Date expDate = DateHandler.convertToDate(2014,1,1);
		
		for(int i=0;i<respIds.length;i++){
			
			dmgrphResponse = new DemographicResponseImpl();
			dmgrphRespId = dmgrphResponse.getId();
			dmgrphRespId.setDemographicQuestionNo(qstnNo);
			dmgrphRespId.setDemographicRespNo(respIds[i]);
			
			dmgrphResponse.setDemographicQstnNo(dmgrphQstn);
			dmgrphResponse.setDemographicValidRespActFlg(true);
			dmgrphResponse.setDemographicValidRespEffDate(effDate);
			dmgrphResponse.setDemographicValidRespExprtnDate(expDate);
			dmgrphResponse.setRespLable(""+(i+1));
			dmgrphResponse.setTestAdmValidRespFrmFlg(false);
			dmgrphResponse.setTestAdmValidRespFrmOrgFlg(false);
			dmgrphResponse.setDemographicValidRespSeqNo(Long.valueOf(i+1));
			dmgrphResponse.setInternationalContentId(i18nEntities.get(i18nIds[i]));
			
			//question has triggering responses			
			if(i==0){
				Long[] triggeringResps = new Long[]{1L,1L,2L,2L,3L};
				Long[] triggeredQstns = new Long[]{2L,3L,2L,4L,4L};
				dmgrphResponse.setDemographicQstnTriggers(
						createDmgrphQstnTriggers(1L, 1L, triggeringResps, triggeredQstns, dmgrphResponse));
			}else{
				dmgrphResponse.setDemographicQstnTriggers(new HashSet<DemographicQuestionTrigger>());
			}
			dmgrphRespList.add(dmgrphResponse);
			
		}
		return dmgrphRespList;
	}
	
	private Set<DemographicQuestionTrigger> createDmgrphQstnTriggers(
			Long setId, Long triggeringQuestion,
			Long[] triggeringResps,
			Long[] triggeredQstns,DemographicResponse dmgrphResp){			
		
		DemographicQuestionTrigger dmgrphQstnTrgr=null;
		DemographicQuestionTriggerId dmgrphQstnTrgrId=null;
		Set<DemographicQuestionTrigger> dmgrphQstnTrgrList = new HashSet<DemographicQuestionTrigger>();
		for(int i=0;i<triggeredQstns.length;i++){
			dmgrphQstnTrgr = new DemographicQuestionTriggerImpl();
			dmgrphQstnTrgrId =dmgrphQstnTrgr.getDemographicQstnTriggerId();
			dmgrphQstnTrgrId.setSetId(setId);
			dmgrphQstnTrgrId.setTriggeringDemographicQstnNo(triggeringQuestion);
			dmgrphQstnTrgrId.setTriggeringDemographicRespNo(triggeringResps[i]);
			dmgrphQstnTrgrId.setDemographicQstnNo(triggeredQstns[i]);
			dmgrphQstnTrgr.setDemograpicRespNo(dmgrphResp);
			dmgrphQstnTrgrList.add(dmgrphQstnTrgr);
		}
		return dmgrphQstnTrgrList;
		 

	}
	
	private Map<Long,Long[]> createQstnRespIdsMap(){
		
		Map<Long,Long[]> QstnToRespIds = new HashMap<Long,Long[]>();
		
		QstnToRespIds.put(1L, new Long[]{1L,2l,3l});
		QstnToRespIds.put(2L, new Long[]{4L,5l,6l});
		QstnToRespIds.put(3L, new Long[]{17L,8l,9l,10L,11L,12L,13L,14L,15L});
		QstnToRespIds.put(4L, new Long[]{16L,17l,18l});
		QstnToRespIds.put(5L, new Long[]{19L,20l,21l,22L,23L,24L});
		QstnToRespIds.put(6L, new Long[]{25L,26L,27L,28L,29L,30L,31L,32L,33L});
		QstnToRespIds.put(7L, new Long[]{34L,35L,36L,37L,38L,39L,40L,41L,42L,43L,44L,45L,46L,47L,48L,49L,50L});
		QstnToRespIds.put(8L, new Long[]{51L,52L,53L,54L,55L,56L,57L,58L,59L,60L,61L,62L});
		
		return QstnToRespIds;
	}
	
	private Map<Long,Long[]> createQstnToRespContentIdMap(){
		
		Map<Long,Long[]> QstnToRespIds = new HashMap<Long,Long[]>();
		
		QstnToRespIds.put(1L, new Long[]{9l,10L,11L});
		QstnToRespIds.put(2L, new Long[]{12L,13L,14L});
		QstnToRespIds.put(3L, new Long[]{15L,16l,17l,18l,19L,20l,21l,22L,23L});
		QstnToRespIds.put(4L, new Long[]{24L,25l,26l});
		QstnToRespIds.put(5L, new Long[]{27L,28L,29L,30L,31L,32L});
		QstnToRespIds.put(6L, new Long[]{33L,34L,35L,36L,37L,38L,39L,40L,41L});
		QstnToRespIds.put(7L, new Long[]{42L,43L,44L,45L,46L,47L,48L,49L,50L,51L,52L,53L,54L,55L,56L,57L,58L});
		QstnToRespIds.put(8L, new Long[]{59L,60L,61L,62L,63L,64L,65L,66l,67L,68L,69L,70L});
		
		return QstnToRespIds;
	}
	
	
	
	
	
	private List<DemographicQuestionSetEntry> createDmgrphQstnSetEntry(){
	
		List<DemographicQuestionSetEntry> dmgrphQstnSetEntryList = new ArrayList<DemographicQuestionSetEntry>();
		DemographicQuestionSetEntry dmgrphQstnSetEntry = null;
		DemographicQuestionSetEntryId dmgrphQstnSetEntryId = null; 
		Long[] adminDsplySeqNo = new Long[]{1L,2L,3L,4L,6L,8L,5L,7L};
		for(int i=0;i<8;i++){
			dmgrphQstnSetEntry =new DemographicQuestionSetEntryImpl();
			dmgrphQstnSetEntryId= dmgrphQstnSetEntry.getId();
			dmgrphQstnSetEntryId.setSetId(1L);
			dmgrphQstnSetEntryId.setDemographicQuestionNo(Long.valueOf(i+1));
			if(i==0){
				dmgrphQstnSetEntry.setRespReqFlg(true);
			}else{
				dmgrphQstnSetEntry.setRespReqFlg(false);
			}
			dmgrphQstnSetEntry.setQuestionDisplaySeqNo(Long.valueOf(i+1));
			dmgrphQstnSetEntry.setAdminDisplaySeqNo(adminDsplySeqNo[i]);
			dmgrphQstnSetEntry.setDemographicQstn(dmgrphQstns.get(Long.valueOf(i+1)));
			dmgrphQstnSetEntryList.add(dmgrphQstnSetEntry);
		}
		
		return dmgrphQstnSetEntryList;
		
	}
	
	private DemographicQuestionRespType createDmgrphQstnRespType(String code,String description,Long displaySequence){
		DemographicQuestionRespType dmgrphQstnRespType = new DemographicQuestionRespTypeImpl();
		dmgrphQstnRespType.setCode(code);
		dmgrphQstnRespType.setDescription(description);
		dmgrphQstnRespType.setDisplayable(true);
		dmgrphQstnRespType.setDisplaySequence(displaySequence);
		
		return dmgrphQstnRespType;
		
	}
	
	private Map<Long,InternationalContent> createI18nEntity(){
		
		Map<Long,InternationalContent> i18nContentList = new HashMap<Long,InternationalContent>();
		InternationalContent i18nEntity = null;
		
		ContentType contentTypeQstn = new ContentTypeImpl();
		contentTypeQstn.setCode("QSTN");
		contentTypeQstn.setDisplaySequence(1L);
		contentTypeQstn.setDescription("Question");
		
		ContentType contentTypeResp = new ContentTypeImpl();
		contentTypeResp.setCode("RESP");
		contentTypeResp.setDisplaySequence(1L);
		contentTypeResp.setDescription("Response");
		
		for(long i=1;i<=70;i++){
			i18nEntity =new InternationalContentImpl();
			i18nEntity.setInternationalContentId(i);
			if(i<=8){
				i18nEntity.setContentTypeCode(contentTypeQstn);
			}else{
				i18nEntity.setContentTypeCode(contentTypeResp);
			}
			i18nContentList.put(i,i18nEntity);
		}
		return i18nContentList;
	}
	private Map<Long,Translation> createTranslationEntity(){
		
		String[] translationTexts = new String[]{
				"What is your current citizenship status?",
				"Select the state or territory you consider your permanent residence.",
				"How do you describe yourself?",
				"Of what country are you a citizen?",
				"In what geographic region(s) would you prefer to take HSET test? (Select all that apply.)",
				"What is your current work status?",
				"What are your reasons for taking HSET test? (Select all that apply.)",
				"What is the highest level of education you completed?",

				"United States citizen",
				"Resident alien (\"permanent resident\") in the United States",
				"Neither a United States citizen nor a resident alien",

				"New Jersey",
				"New York",
				"New Hampshire",

				"American Indian or Alaskan Native",
				"Asian or Asian American",
				"Black or African American",
				"Mexican, Mexican American, or Chicano",
				"Native Hawaiian or Other Pacific Islander",
				"Puerto Rican",
				"Other Hispanic, Latino, or Latin American",
				"White (non-Hispanic)",
				"Other",

				"Singapore",
				"India",
				"China",

				"Northeast (CT, ME, MA, NH, RI, VT)",
				"Mid-Atlantic (DC, DE, MD, NJ, NY, PA",
				"South (AL, FL, GA, KY, LA ,MS, NC, SC, TN, VA ,WV)",
				"Midwest (IL, IN, IA, KS, MI, MN, MO, NE, ND, OH, SD, WI)",
				"Southwest (AZ, AR, NM, OK, TX)",
				"West (AK, CA, CO, HI, ID, MT, NV, OR, UT, WA, WY)",


				"Employed Full Time",
				"Employed Part Time (Fewer than 20 hours per week)",
				"Unemployed (Seeking employment)",
				"Permanent Disability",
				"Not in the Labor Force (Unemployed by choice)",
				"Not in the Labor Force (Homemake,Caregiver)",
				"Retired",
				"Full-time Student",
				"Part-time Student",

				"Enroll in Technical or Trade Program",
				"Enter a 2-Year College",
				"Enter a 4-Year College",
				"Skill Certification",
				"Job Training",
				"Get First Job",
				"Keep Current Job",
				"Get a Better Job",
				"Employment Requirement",
				"Military Entrance",
				"Military Career",
				"Early Release",
				"Court Order",
				"Public Assistance Requirement",
				"Role Model for Family",
				"Personal Satisfaction",
				"Other",

				"None",
				"K-3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9",
				"10",
				"11",
				"12",
				"12+",

		};
		Map<Long,Translation> translationTxtsList = new HashMap<Long,Translation>();
		
		int translationTxtsCnt = translationTexts.length;
		Translation translationEntity=null;
		TranslationId translationId=null;
		
		for(int i=0;i<translationTxtsCnt;i++){
			translationEntity= new TranslationImpl();
			translationId = translationEntity.getTranslationId();
			translationId.setInternationalConentId(Long.valueOf(i+1));
			translationId.setLanguageCode("EN");
			translationEntity.setTranslationTxt(translationTexts[i]);
			
			translationTxtsList.put(Long.valueOf(i+1),translationEntity);
		}
		return translationTxtsList;
	}
}
