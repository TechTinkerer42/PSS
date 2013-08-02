package org.ets.ereg.common.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.ets.ereg.common.business.dao.RulesSetConfigurationDao;
import org.ets.ereg.common.business.rule.querycomposer.AbstractDatabaseQueryRule;
import org.ets.ereg.common.business.service.RulesSetConfigurationService;
import org.ets.ereg.common.business.util.RuleAssembler;
import org.ets.ereg.common.business.vo.CodeValuePairVo;
import org.ets.ereg.common.business.vo.ConfigurationsVo;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.id.EregHierarchyRuleSetConfigurationId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("rulesSetConfigurationService")
public class RulesSetConfigurationServiceImpl implements RulesSetConfigurationService {
	@Resource(name = "ruleSetConfigurationAssembler")
	private RuleSetConfigurationAssembler ruleSetConfigurationAssembler;
	
	@Resource(name = "rulesSetConfigurationDao")
	private RulesSetConfigurationDao rulesSetConfigurationDao;
	private RuleSetConfiguration getRuleSetConfiguartion(Long ruleSetConfiguationId){
		return rulesSetConfigurationDao.findByPrimaryKey(RuleSetConfiguration.class,ruleSetConfiguationId);
	}
	
	private Long getRuleSetConfigurationIdTobeApplied(Long programHierarchyId, String ruleSetTypeCode,Long adminRoleId){
		Long ruleSetConfidId=rulesSetConfigurationDao.getRuleSetConfigurationIdTobeApplied(programHierarchyId,ruleSetTypeCode,adminRoleId);
		return ruleSetConfidId;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<AbstractDatabaseQueryRule> getRules(Long programHierarchyId, String ruleSetTypeCode,Long adminRoleId) {
		Long ruleSetConfigId=getRuleSetConfigurationIdTobeApplied(programHierarchyId,ruleSetTypeCode,adminRoleId);
		RuleSetConfiguration ruleSetConfig=getRuleSetConfiguartion(ruleSetConfigId);
		List<AbstractDatabaseQueryRule> rules=RuleAssembler.assemble(ruleSetConfig);
		return rules;
	}
	@Override
	public List<CodeValuePairVo> getRuleSetConfigurationParameterValue(String ruleCode){
		return rulesSetConfigurationDao.getRuleSetConfigurationParameterValue(ruleCode);
	}
	@Override
	public List<RuleSet> getRuleSetByCode(String ruleSetCd){
		return rulesSetConfigurationDao.getRuleSetByCode(ruleSetCd);
		
	}
	
	@Override
	public EregHierarchyRuleSetConfiguration getDefaultHierarchy(String ruleSetCd){
		return rulesSetConfigurationDao.getDefaultHierarchy(ruleSetCd);
	}
	@Override
	public String getConfigurationValue(Long eregHierarchyId,String ruleSetTypeCd, String ruleCd,Long adminRoleId){
		
		return rulesSetConfigurationDao.getConfigurationValue(eregHierarchyId, ruleSetTypeCd, ruleCd,adminRoleId);
	}
	@Override
	public RuleSetConfiguration getRuleSetConfiguration(Long programHierarchyId, String ruleSetTypeCode,Long adminRoleId){
		Long ruleSetConfigId=rulesSetConfigurationDao.getRuleSetConfigurationIdTobeApplied(programHierarchyId,ruleSetTypeCode,adminRoleId);
		if(ruleSetConfigId==null)
			return null;
		RuleSetConfiguration ruleSetConfig=getRuleSetConfiguartion(ruleSetConfigId);
		return ruleSetConfig;
	}
	@Override
	public EregHierarchy getEregHierarchy(Long hierarchy){
		return rulesSetConfigurationDao.getEregHierarchy(hierarchy);
	}
	@Override
	public List<EregHierarchy> getEregHierachys(String pgmCode){
		List<EregHierarchy> results=rulesSetConfigurationDao.getEregHierachys(pgmCode);
		return results;
	}
	
	public AdminRole getAdminRole(String adminRoleCd){
		return rulesSetConfigurationDao.getAdminRole(adminRoleCd);
	}
	
	private EregHierarchyRuleSetConfiguration saveEregHierarchyRuleSetConfiguration(RuleSetConfiguration rsConfig,ConfigurationsVo configVo,AdminRole admin){
		EregHierarchyRuleSetConfiguration eregHierRSConfig=rulesSetConfigurationDao.createEregHierarchyRuleSetConfiguration();
		rsConfig.setRuleSetConfigurationIdentifier(rsConfig.getRuleSetConfigurationIdentifier());
		EregHierarchyRuleSetConfigurationId id = new EregHierarchyRuleSetConfigurationId();
		id.setEregHierarchyIdentifier(configVo.getHierarchy());
		id.setRuleSetConfigurationIdentifier(rsConfig.getRuleSetConfigurationIdentifier());
		eregHierRSConfig.setId(id);
		eregHierRSConfig.setRuleSetConfiguration(rsConfig);
		eregHierRSConfig.setAdminRole(admin);
		rsConfig.getEregHierarchyRuleSetConfigurations().add(eregHierRSConfig);
		return rulesSetConfigurationDao.saveHierarchyRuleSetConfiguration(eregHierRSConfig);
	
	}
	


	@Override
	public List<EregHierarchy> getChildren(Long eregHierarchyId){
		return rulesSetConfigurationDao.getChildren(eregHierarchyId);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public RuleSetConfiguration saveRuleSetConfiguration(ConfigurationsVo configVo){
		Long adminId=0L;
		AdminRole admin=null;
		if(!StringUtils.isEmpty(configVo.getRoleName())){
			admin=rulesSetConfigurationDao.getAdminRole(configVo.getRoleName());
			adminId=admin.getId();
			configVo.setAdminId(adminId);
		}	
		RuleSetConfiguration rsConfig=ruleSetConfigurationAssembler.assemble(configVo);
		rsConfig=rulesSetConfigurationDao.save(rsConfig);
		if(configVo.getRules()==null){
			rulesSetConfigurationDao.delete(rsConfig);
			return null;
		}
		if(configVo.isCreateFlag()){
			saveEregHierarchyRuleSetConfiguration(rsConfig,configVo,admin);
		}
		return rsConfig;
	}
	@Override
	public List<RoleEnum> getEligibleRoles(Long programHierarchyId,String ruleSetTypeCode){
		return rulesSetConfigurationDao.getEligibleRoles(programHierarchyId,ruleSetTypeCode);
	}

}
