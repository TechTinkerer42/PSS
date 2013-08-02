package org.ets.ereg.common.business.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.RulesSetConfigurationDao;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.vo.ConfigurationsVo;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationParameterValue;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetType;
import org.springframework.stereotype.Service;
@Service("ruleSetConfigurationAssembler")
public class RuleSetConfigurationAssembler {
	@Resource(name = "rulesSetConfigurationDao")
	private RulesSetConfigurationDao rulesSetConfigurationDao;
	
	@Resource(name = "referenceEntityService")
	private ReferenceService referenceService;
	
private Set<RuleSetConfigurationParameterValue> addRuleSetConfigurationParameterValues(RuleSetConfigurationDetail ruleSetConfgDtl,ConfigurationsVo configVo){
		
		Set<RuleSetConfigurationParameterValue> ruleSetConfigParmVals= new HashSet<RuleSetConfigurationParameterValue>();
		long paramSeqNo=1;
		String[]rules=configVo.getRules();
		String[]paramNames=configVo.getParamNames();
		String[]paramValues=configVo.getParamValues();
		Set<RuleAllowableParameter> ruleAllowParams=ruleSetConfgDtl.getRuleSet().getRule().getRuleAllowableParameters();
		for(RuleAllowableParameter ruleAllowParam:ruleAllowParams){
			RuleSetConfigurationParameterValue rsConfigParamVal= rulesSetConfigurationDao.createRuleSetConfigurationParameterValue();
			rsConfigParamVal.setRuleSetConfigurationDetail(ruleSetConfgDtl);
			//rsConfigParamVal.setRuleSetConfigurationDetail(ruleSetConfgDtl);
			rsConfigParamVal.setRuleAllowableParameter(ruleAllowParam);
			rsConfigParamVal.setParameterValueSequenceNumber(paramSeqNo);	
			
			for(String rule:rules){
				int index=0;
				for(String paramName:paramNames){
					String ruleCd=rsConfigParamVal.getRuleSetConfigurationDetail().getRuleSet().getRule().getRuleCode();
					String paramCd=rsConfigParamVal.getRuleAllowableParameter().getRuleParameterCd();
					if(rule.equals(ruleCd) && paramCd.equals(paramName)){
						rsConfigParamVal.setRuleParameterValueText(paramValues[index]);
					}
					index++;
				}
			}
			ruleSetConfigParmVals.add(rsConfigParamVal);
		}
		return ruleSetConfigParmVals;
	}
	
	

	
	private RuleSetConfiguration removeRuleSetConfigurationDetails(RuleSetConfiguration rsConfig,ConfigurationsVo configVo){
		String[]rules=configVo.getRules();
		Iterator<RuleSetConfigurationDetail> details=rsConfig.getRuleSetConfigurationDetails().iterator();
		while(details.hasNext()){
			boolean detailFound=false;
			RuleSetConfigurationDetail detail=(RuleSetConfigurationDetail)details.next();
			if(rules!=null){
			for(String rule:rules){
				if(configVo.getRuleSetType().equals(detail.getRuleSet().getRuleSetType().getCode()) 
						&& rule.equals(detail.getRuleSet().getRule().getRuleCode())){
					detailFound=true;
					
				}
			}
			}
			if(!detailFound){
				details.remove();
			}
			
		}
		return rsConfig;
			
	}
	
	
	
	
	private RuleSetConfiguration addRuleSetConfigurationDetails(RuleSetConfiguration rsConfig,ConfigurationsVo configVo){
		String[]rules=configVo.getRules();
		
		if(rules!=null){
		for(String rule:rules){
			boolean detailFound=false;
			Iterator<RuleSetConfigurationDetail> details=rsConfig.getRuleSetConfigurationDetails().iterator();
			while(details.hasNext()){
				RuleSetConfigurationDetail detail=(RuleSetConfigurationDetail)details.next();
				if(configVo.getRuleSetType().equals(detail.getRuleSet().getRuleSetType().getCode()) 
						&& rule.equals(detail.getRuleSet().getRule().getRuleCode())){
					detailFound=true;
					
				}
			}
			if(!detailFound){	
				RuleSetConfigurationDetail rSDetail=addRuleSetConfigurationDetail(rule,configVo.getRuleSetType(),configVo,rsConfig);
				rsConfig.getRuleSetConfigurationDetails().add(rSDetail);
				rSDetail.setRuleSetConfiguration(rsConfig);	
			}
		}
		}
		return rsConfig;	
	}
	
	private boolean checkRSConfigType(ConfigurationsVo configVo,RuleSetConfigurationDetail detail
			,String paramName){
		if("rules".equals(configVo.getRuleSetType())
				||detail.getRuleSet().getRule().getRuleCode().equals(paramName)){
			return true;
		}
		return false;
	}
	

	private RuleSetConfiguration editRuleSetConfigurationParameterValues(RuleSetConfiguration rsConfig,ConfigurationsVo configVo){
		Set<RuleSetConfigurationDetail> details=rsConfig.getRuleSetConfigurationDetails();
		String[]paramNames=configVo.getParamNames();
		String[]paramValues=configVo.getParamValues();
		int index=0;
		if(paramNames!=null){
		for(String paramName:paramNames){
			for(RuleSetConfigurationDetail detail:details){
				if(configVo.getRuleSetType().equals(detail.getRuleSet().getRuleSetType().getCode())){
					Set<RuleSetConfigurationParameterValue> rsConfParamVals=detail.getRuleSetConfigurationParameterValues();
					for(RuleSetConfigurationParameterValue rsConfParamVal:rsConfParamVals){
						if(paramName.equals(rsConfParamVal.getRuleAllowableParameter().getRuleParameterCd())){
							rsConfParamVal.setRuleParameterValueText(paramValues[index]);
						}
					}
				}
		}
		index++;
	}
		}
		return rsConfig;
		
	}
	
	
	
	private RuleSetConfigurationDetail addRuleSetConfigurationDetail(String ruleCd,String ruleSetTypeCd,ConfigurationsVo configVo,RuleSetConfiguration rsConfig){
		RuleSetConfigurationDetail rsConfigDetail=rulesSetConfigurationDao.createRuleSetConfigurationDetail();
		RuleSet ruleSet= rulesSetConfigurationDao.getRuleSet(ruleCd, ruleSetTypeCd);
		rsConfigDetail.setRuleSet(ruleSet);
		rsConfigDetail.setRuleSetConfiguration(rsConfig);
	
		Set<RuleSetConfigurationParameterValue> ruleSetParamVal=addRuleSetConfigurationParameterValues(rsConfigDetail,configVo);
		
		rsConfigDetail.setRuleSetConfigurationParameterValues(ruleSetParamVal);
		
		return rsConfigDetail;
	}
	
	public RuleSetConfiguration addRuleSetConfiguration(ConfigurationsVo configVo){
		RuleSetConfiguration rsConfig=rulesSetConfigurationDao.createRuleSetConfiguration();
		configVo.setCreateFlag(true);
		String[]ruleCodes=configVo.getRules();
		for(String ruleCd:ruleCodes){
			RuleSetConfigurationDetail rsConfigDetail= addRuleSetConfigurationDetail(ruleCd,configVo.getRuleSetType(), configVo,rsConfig);
			
			rsConfigDetail.setRuleSetConfiguration(rsConfig);
   			rsConfig.getRuleSetConfigurationDetails().add(rsConfigDetail);
		}
	
		RuleSetType ruleSetType =referenceService.getEntityByPrimaryKey(RuleSetType.class, configVo.getRuleSetType());
		rsConfig.setRuleSetType(ruleSetType);
		return rsConfig;
	}
	
	public static boolean checkRSConfig(RuleSetConfiguration rsConfig, Long hierarchyId,Long adminRoleId){
		Set<EregHierarchyRuleSetConfiguration> eregHierDRConfSet=rsConfig.getEregHierarchyRuleSetConfigurations();
		for(EregHierarchyRuleSetConfiguration eregHierDRConf:eregHierDRConfSet){
			Long hierId=eregHierDRConf.getId().getEregHierarchyIdentifier();
			Long roleId=0L;
			if(eregHierDRConf.getAdminRole()!=null)
				roleId=eregHierDRConf.getAdminRole().getId();
			if(hierId.equals(hierarchyId)
					&& roleId.equals(adminRoleId)){
				return true;
				 
			}
		}
		return false;
	}
	
	public RuleSetConfiguration assemble(ConfigurationsVo configVo){
	RuleSetConfiguration rsConfig = null;
	Long config=rulesSetConfigurationDao.getRuleSetConfigurationIdTobeApplied(configVo.getHierarchy(),configVo.getRuleSetType(),configVo.getAdminId());
	//When Hierarchy Not defined Yet
	if(config!=null)
		rsConfig=rulesSetConfigurationDao.findByPrimaryKey(RuleSetConfiguration.class, config);
	if(config==null||!checkRSConfig(rsConfig,configVo.getHierarchy(),configVo.getAdminId())) {
		rsConfig=addRuleSetConfiguration(configVo);		
	}
	else {
		if(configVo.getRules()==null)
			return rsConfig;
		
		rsConfig=removeRuleSetConfigurationDetails(rsConfig,configVo);
		rsConfig=addRuleSetConfigurationDetails(rsConfig,configVo);
		rsConfig=editRuleSetConfigurationParameterValues(rsConfig,configVo);
	}
	return rsConfig;
	}

 

}
