package org.ets.ereg.profile.biq.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.ets.ereg.common.business.biq.dao.DemographicQuestionSetEntryDao;
import org.ets.ereg.common.business.biq.dao.DemographicQuestionsSetDao;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.business.vo.biq.DemographicResponseVO;
import org.ets.ereg.common.business.vo.biq.DmgrphQstnTriggerVO;
import org.ets.ereg.domain.interfaces.model.biq.CustomerDemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestion;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionRespType;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetEntry;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionTrigger;
import org.ets.ereg.domain.interfaces.model.biq.DemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.id.CustomerDemographicResponseId;
import org.ets.ereg.domain.interfaces.model.i18n.Translation;
import org.ets.ereg.profile.biq.dao.CustomerDemographicResponseDao;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("profileDemographicQuestionService")
public class ProfileDemographicQuestionServiceImpl implements
		ProfileDemographicQuestionService {
	
	private static Logger logger = LoggerFactory.getLogger(ProfileDemographicQuestionServiceImpl.class);
	
	@Resource(name="demographicQuestionsSetDao")
	private DemographicQuestionsSetDao demographicQuestionSetDao;
	
	@Resource(name="demographicQuestionSetEntryDao")
	private DemographicQuestionSetEntryDao demographicQuestionSetEntryDao;
	
	@Resource(name="customerDemographicResponseDao")
	private CustomerDemographicResponseDao customerDemographicResponseDao;
	
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
	public List<DemographicQuestionVO> getDemographicQuestions(Long customerId,
			String programCode,String languageCode,String setTypeCode, Boolean isCustomerSort) {
		
		List<DemographicQuestionSetEntry> demographicQstnSetEntryList = demographicQuestionSetEntryDao.
									getDemographicQuestions(programCode, setTypeCode, isCustomerSort);
		
		List<DemographicQuestionVO> demographicQuestionVO = 
						toDemographicQuestionVO(demographicQstnSetEntryList,isCustomerSort,languageCode,
								programCode,customerId);		
		
		setGotTriggeredFlag(demographicQuestionVO);
		
		return demographicQuestionVO;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public void saveProfileDemographicResponses(Long customerId,List<DemographicQuestionVO> demographicQuestions){				
		
		if(demographicQuestions.size()>0){
			
			Long setId = demographicQuestions.get(0).getSetId();
			
			List<CustomerDemographicResponse> customerDemographicResponses = new ArrayList<CustomerDemographicResponse>();			
			CustomerDemographicResponse customerDmgrphResp =null;
			String[] selectedResponseIds=null;
			CustomerDemographicResponseId customerDmgrphRespId=null;			
			
			for(DemographicQuestionVO demographicQuestion:demographicQuestions){
				if(demographicQuestion.isAnswered()){
					selectedResponseIds=demographicQuestion.getSelectedResponseIds();					
					for(String selectedResponseId:selectedResponseIds){						
						customerDmgrphResp = customerDemographicResponseDao.create();
						if(DemographicQuestionRespType.respType.FREEFORM.
								getResponseType().equals(demographicQuestion.getResponseType())){
							customerDmgrphResp.setFreeFormTxtResp(demographicQuestion.getFreeFormAnswer());
						}
						
						customerDmgrphRespId = customerDmgrphResp.getCustomerDemographicRespId();				
						customerDmgrphRespId.setCustomerId(customerId);
						customerDmgrphRespId.setDemographicQstnNo(demographicQuestion.getQstnNo());
						
						if(StringUtils.isNotBlank(selectedResponseId)){
							customerDmgrphRespId.setDemographicRespNo(Long.valueOf(selectedResponseId));
						}else{
							logger.debug("Selected response id is either blank or null for question {}",demographicQuestion.getQstnNo());
						}						
						customerDemographicResponses.add(customerDmgrphResp);						
					}
				}
				
			}
			
			if(customerDemographicResponses.size()>0){
				customerDemographicResponseDao.deleteCustomerDemographicResponsesForCurrentSet(customerId,setId);
				customerDemographicResponseDao.saveCustomerDemographicResponse(customerDemographicResponses);
				
			}else{				
				logger.debug("No customer demographic responses to save for customer {}",customerId);
			}
		}		
		else{
			logger.debug("No customer demographic response to save for customer id {}",customerId);
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
	public boolean doesProfileDemographicQuestionSetExists(String programCode,String setTypeCode) {
		
		return demographicQuestionSetDao.doesDemographicQuestionSetExists(programCode, setTypeCode);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
	public boolean areProfileRequiredDQAnswered(Long customerId,
			String programCode) {		
		
		logger.debug("inside areProfileDQAnswered()");		
		List<DemographicQuestionVO> demographicQstnVOList = getDemographicQuestions(customerId, programCode, null, "PRFL", true);	
		Set<Long> supposedToBeAnsweredButNot = new HashSet<Long>();
		
		for(DemographicQuestionVO demographicQstnVO:demographicQstnVOList){
			if(demographicQstnVO.isResponseRequired()){
				if(demographicQstnVO.getIndependent()){
					//it is independent but not answered..
					if(!demographicQstnVO.isAnswered()){
						supposedToBeAnsweredButNot.add(demographicQstnVO.getQstnNo());
					}
				}else if(!demographicQstnVO.getIndependent() &&
							(demographicQstnVO.isGotTriggered() && !demographicQstnVO.isAnswered())){					
						supposedToBeAnsweredButNot.add(demographicQstnVO.getQstnNo());					
				}
			}
		}
		for(Long question:supposedToBeAnsweredButNot){
			logger.debug("Mandatory question not answered is {}",question);
		}
		return  (supposedToBeAnsweredButNot.size()==0);
	}
	


	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
	public boolean areProfileDQAnswered(Long customerId, String programCode) {
		
		logger.debug("inside areProfileDQAnswered()");		
		List<DemographicQuestionVO> demographicQstnVOList = getDemographicQuestions(customerId, programCode, null, "PRFL", true);	
		Set<Long> supposedToBeAnsweredButNot = new HashSet<Long>();
		
		for(DemographicQuestionVO demographicQstnVO:demographicQstnVOList){
			if(demographicQstnVO.getIndependent()){
				//it is independent but not answered..
				if(!demographicQstnVO.isAnswered()){
					supposedToBeAnsweredButNot.add(demographicQstnVO.getQstnNo());
				}
			}else if(!demographicQstnVO.getIndependent() 
					&& (demographicQstnVO.isGotTriggered() && !demographicQstnVO.isAnswered())){				
					supposedToBeAnsweredButNot.add(demographicQstnVO.getQstnNo());				
			}
		}
		for(Long question:supposedToBeAnsweredButNot){
			logger.debug("optional question not answered is {}",question);
		}
		return  (supposedToBeAnsweredButNot.size()==0);
		
		
	}
	
	public void setGotTriggeredFlag(List<DemographicQuestionVO> demographicQstnVOList){
		
		List<DemographicResponseVO> demographicRespVOList = null;
		List<DmgrphQstnTriggerVO> demographicQstnTriggerVOList = null;
		
		String[] selcetedResponses = null;	
		
		for(DemographicQuestionVO demographicQstnVO:demographicQstnVOList){			
			if(!demographicQstnVO.getIndependent()){
				
				for(DemographicQuestionVO demographicQstnVO1:demographicQstnVOList){
					
					if(demographicQstnVO.getQstnNo()!=demographicQstnVO1.getQstnNo()){
						demographicRespVOList = demographicQstnVO1.getDemographicResponses();						
						demographicQstnTriggerVOList = demographicQstnVO1.getDependentQuestionVO();
						
						for(DmgrphQstnTriggerVO demographicQstnTriggerVO:demographicQstnTriggerVOList){
							
							if(demographicQstnTriggerVO.getDependentQstnNo().equals(demographicQstnVO.getQstnNo())){
								
								for(DemographicResponseVO demographicRespVO:demographicRespVOList){
									
									//findout which response can trigger this question
									if(demographicRespVO.getRespNo().equals(demographicQstnTriggerVO.getTriggerRespNo())){
										
										//get all selected response for the triggering question
										selcetedResponses = demographicQstnVO1.getSelectedResponseIds();
										if(null!=selcetedResponses && selcetedResponses.length>0){
											for(String selectedResponse:selcetedResponses){												
												if(StringUtils.isNotBlank(selectedResponse) && 
														(Long.valueOf(selectedResponse).equals(demographicQstnTriggerVO.getTriggerRespNo()))){
													
														Object[] debugArgs = new Object[]{demographicQstnVO.getQstnNo(),
																demographicQstnTriggerVO.getTriggerRespNo(),demographicQstnVO1.getQstnNo()};
														logger.debug("This question {} is triggered by response {} for question {} ",debugArgs);
														//response triggered this question but not answered...
														demographicQstnVO.setGotTriggered(true);
														if(!demographicQstnVO.isAnswered()){
															logger.debug("dependent question {} triggered but not answered",demographicQstnVO.getQstnNo());														
														}else{
															logger.debug("dependent question {} triggered and answered",demographicQstnVO.getQstnNo());
														}														
													
												}
											}
										}
										
									
									}
								}
								
							}
						}
					}
				}
			}
		
			
			
		}
		
	
	}
	
	
	private List<DemographicQuestionVO> toDemographicQuestionVO(
								List<DemographicQuestionSetEntry> demographicQstnSetEntryList,
								boolean isCustomerSort,String languageCode,
								String programCode,Long customerId){
		
		List<DemographicQuestionVO> demographicQuestionVOList = Collections.emptyList();
		
		if(demographicQstnSetEntryList.size()>0){			
			
			demographicQuestionVOList = new  ArrayList<DemographicQuestionVO>();
			List<DemographicResponseVO> demographicResponseVOList = Collections.emptyList();;
			List<DmgrphQstnTriggerVO> dmgrphQstnTriggerVOList = Collections.emptyList();;
			
			Set<Long> triggeredQstnsList = new HashSet<Long>();
			
			DemographicResponseVO  demographicResponseVO = null;
			Set<DemographicResponse> dmgrphQstnResps =Collections.emptySet();
			Translation translationTxtObj=null;
			DemographicQuestion dmgrphQstn=null;
			DemographicQuestionVO dmgrphQstnVO=null;
					
			
			Long setId = demographicQstnSetEntryList.get(0).getId().getSetId();
			
			for(DemographicQuestionSetEntry demographicQstnSetEntry:demographicQstnSetEntryList){
				
				demographicResponseVOList = new  ArrayList<DemographicResponseVO>();
				dmgrphQstnTriggerVOList = new ArrayList<DmgrphQstnTriggerVO>();
				
				dmgrphQstnVO = new DemographicQuestionVO();				
				dmgrphQstnVO.setSetId(demographicQstnSetEntry.getId().getSetId());
				
				//set display sequence no based on isCustomerSort flag
				if(isCustomerSort){
					dmgrphQstnVO.setSeqNo(demographicQstnSetEntry.getQuestionDisplaySeqNo());
				}else{
					dmgrphQstnVO.setSeqNo(demographicQstnSetEntry.getAdminDisplaySeqNo());
				}
				
				dmgrphQstnVO.setQstnNo(demographicQstnSetEntry.getId().getDemographicQuestionNo());
				dmgrphQstnVO.setResponseRequired(demographicQstnSetEntry.getRespReqFlg());
				
				
				//populate demographic question
				dmgrphQstn = demographicQstnSetEntry.getDemographicQstn();				
				dmgrphQstnVO.setResponseType(dmgrphQstn.getDemographicQuestionRespTypCode().getCode());
				
				//populate question text to display
				
				if(StringUtils.isNotBlank(languageCode)){
					translationTxtObj = demographicQuestionSetEntryDao.getTranslationText(languageCode,
										dmgrphQstn.getInternationalContentId().getInternationalContentId());				
					dmgrphQstnVO.setDisplayText(translationTxtObj.getTranslationTxt());
				}
											
				//build demographic question response VO				
				dmgrphQstnResps =  dmgrphQstn.getDemographicResps();				
								
				for(DemographicResponse dmgrphQstnResp:dmgrphQstnResps){					
					demographicResponseVO = toDemographicResponseVO(dmgrphQstnResp,
														languageCode,dmgrphQstnTriggerVOList,triggeredQstnsList);					
					demographicResponseVOList.add(demographicResponseVO);
				}			
			
				dmgrphQstnVO.setDemographicResponses(demographicResponseVOList);
				dmgrphQstnVO.setDependentQuestionVO(dmgrphQstnTriggerVOList);
				demographicQuestionVOList.add(dmgrphQstnVO);
				
			}
			// pull customer demographics			
			if(customerId!=null && setId!=null){
				getCustomerDemographics(customerId, setId, demographicQuestionVOList);
			}
			
			setIsTriggeredQuestionFlag(demographicQuestionVOList,triggeredQstnsList);
			logger.debug("No of questions present in set {} are {}",setId,demographicQuestionVOList.size());
			logger.debug("No of dependent questions present in set {} are {}",setId,triggeredQstnsList.size());			
			
		}else{
			logger.debug("No demographic question set entries found for program code {}",programCode);
		}
		
		return demographicQuestionVOList;
	}
	
	private void getCustomerDemographics(Long customerId,Long setId,List<DemographicQuestionVO> demographicQuestionVOList ){
			
				//use setId to filter questions belonging to current set only
				List<CustomerDemographicResponse> customerDemgraphicResps =
									 customerDemographicResponseDao.getCustomerDemographicResponses(customerId,setId);
				
				if(customerDemgraphicResps.size()>0){
					
					Map<Long,List<String>> custDemographicRespMap = toCustomerDemographicRespVO(customerDemgraphicResps);
					
					//set customer selected responses..	
					Long tmpDmgrphQstnNo=null;
					List<String> tmpSelectedRespIds = null;
					for (DemographicQuestionVO tmpDmgrphQstn : demographicQuestionVOList){
						tmpDmgrphQstnNo=tmpDmgrphQstn.getQstnNo();
						
						if(custDemographicRespMap.containsKey(tmpDmgrphQstnNo)){
							tmpSelectedRespIds = custDemographicRespMap.get(tmpDmgrphQstnNo);
							
							tmpDmgrphQstn.setSelectedResponseIds(tmpSelectedRespIds.toArray(new String[tmpSelectedRespIds.size()]));
									
						}
						
					}
				}
					
	}
	
	
	
	private void setIsTriggeredQuestionFlag(List<DemographicQuestionVO> demographicQuestionVOList,
							Set<Long> triggeredQstnsList){
		
		for(DemographicQuestionVO dmrphQstnVO : demographicQuestionVOList){
			
			if(triggeredQstnsList.contains(dmrphQstnVO.getQstnNo())){
				
				dmrphQstnVO.setIndependent(false);
			}else{
				dmrphQstnVO.setIndependent(true);
			}
		}
		
	}
	
	private Map<Long,List<String>> toCustomerDemographicRespVO(List<CustomerDemographicResponse> customerDemgraphicResps){			
			
			Long custQstnNo=null;						
			List<String> tmpSelectedResposnes=null;
			Map<Long,List<String>> custSelectedResps = new HashMap<Long,List<String>>();
			for(CustomerDemographicResponse customerDemgraphicResp:customerDemgraphicResps){
				
				custQstnNo = customerDemgraphicResp.getCustomerDemographicRespId().getDemographicQstnNo();
				
				if(custSelectedResps.containsKey(custQstnNo)){
					tmpSelectedResposnes = custSelectedResps.get(custQstnNo);
					tmpSelectedResposnes.add(String.valueOf(customerDemgraphicResp.getCustomerDemographicRespId().getDemographicRespNo()));
					custSelectedResps.put(custQstnNo, tmpSelectedResposnes);
				}else{
					List<String> selectedResposnes = new ArrayList<String>();
					selectedResposnes.add(String.valueOf(customerDemgraphicResp.getCustomerDemographicRespId().getDemographicRespNo()));
					custSelectedResps.put(custQstnNo, selectedResposnes);
				}
			}
		
			return custSelectedResps;
		
	}
	
	
	private DemographicResponseVO toDemographicResponseVO(DemographicResponse demographicResponse,
			String languageCode,
			List<DmgrphQstnTriggerVO> dmgrphQstnTriggerVOList,
			Set<Long> triggeredQstnsList){
	
		DemographicResponseVO dmgrphRespVO = new DemographicResponseVO();	
		dmgrphRespVO.setRespNo(demographicResponse.getId().getDemographicRespNo());
		dmgrphRespVO.setSeqNo(demographicResponse.getDemographicValidRespSeqNo());
	
		//populate question text
		if(StringUtils.isNotBlank(languageCode)){
			Translation translationTxtObj = demographicQuestionSetEntryDao.getTranslationText(languageCode,
				demographicResponse.getInternationalContentId().getInternationalContentId());
			
			dmgrphRespVO.setPossibleResponse(translationTxtObj.getTranslationTxt());
		}
		
		Set<DemographicQuestionTrigger> dmgrphQstnTriggers = demographicResponse.getDemographicQstnTriggers();
		
		
		DmgrphQstnTriggerVO dmgrphQstnTriggerVO = null;
		
		for(DemographicQuestionTrigger dmgrphQstnTrigger:dmgrphQstnTriggers){			
			dmgrphQstnTriggerVO = toDmgrphQstnTriggerVO(dmgrphQstnTrigger,triggeredQstnsList);
			dmgrphQstnTriggerVOList.add(dmgrphQstnTriggerVO);
		}
		
		return dmgrphRespVO;
	}	
	
	private DmgrphQstnTriggerVO toDmgrphQstnTriggerVO(DemographicQuestionTrigger dmgrphQstnTrigger,Set<Long> triggeredQstnsList){
		
		DmgrphQstnTriggerVO dmgrphQstnTriggerVO = new DmgrphQstnTriggerVO();
		
		Long dependentQstnNo = dmgrphQstnTrigger.getDemographicQstnTriggerId().getDemographicQstnNo();
		dmgrphQstnTriggerVO.setDependentQstnNo(dependentQstnNo);
		dmgrphQstnTriggerVO.setTriggerRespNo(dmgrphQstnTrigger.getDemographicQstnTriggerId()
						.getTriggeringDemographicRespNo());
		
		triggeredQstnsList.add(dependentQstnNo);
		return dmgrphQstnTriggerVO;
	}

	public DemographicQuestionsSetDao getDemographicQuestionSetDao() {
		return demographicQuestionSetDao;
	}

	public void setDemographicQuestionSetDao(
			DemographicQuestionsSetDao demographicQuestionSetDao) {
		this.demographicQuestionSetDao = demographicQuestionSetDao;
	}

	public DemographicQuestionSetEntryDao getDemographicQuestionSetEntryDao() {
		return demographicQuestionSetEntryDao;
	}

	public void setDemographicQuestionSetEntryDao(
			DemographicQuestionSetEntryDao demographicQuestionSetEntryDao) {
		this.demographicQuestionSetEntryDao = demographicQuestionSetEntryDao;
	}

	public CustomerDemographicResponseDao getCustomerDemographicResponseDao() {
		return customerDemographicResponseDao;
	}

	public void setCustomerDemographicResponseDao(
			CustomerDemographicResponseDao customerDemographicResponseDao) {
		this.customerDemographicResponseDao = customerDemographicResponseDao;
	}

	

}
