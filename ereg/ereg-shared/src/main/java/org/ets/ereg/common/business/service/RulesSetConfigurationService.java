package org.ets.ereg.common.business.service;

import java.util.List;

import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.ets.ereg.common.business.rule.querycomposer.AbstractDatabaseQueryRule;
import org.ets.ereg.common.business.vo.CodeValuePairVo;
import org.ets.ereg.common.business.vo.ConfigurationsVo;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;

public interface RulesSetConfigurationService {
	
	public List<AbstractDatabaseQueryRule> getRules(Long eRegHierId,String code,Long adminRoleId); 
	
	public String getConfigurationValue(Long eregHierarchyId,String ruleSetTypeCd, String ruleCd,Long adminRoleId);
	
	public List<EregHierarchy> getEregHierachys(String pgmCode);
	
	public AdminRole getAdminRole(String adminRoleCd);
	
	public EregHierarchy getEregHierarchy(Long hierarchy);
	
	public RuleSetConfiguration getRuleSetConfiguration(Long programHierarchyId, String ruleSetTypeCode,Long adminRoleId);
	
	public List<RuleSet> getRuleSetByCode(String ruleSetCd);
	
	public List<CodeValuePairVo> getRuleSetConfigurationParameterValue(String ruleCode);
	
	public List<EregHierarchy> getChildren(Long eregHierarchyId);
	
	public RuleSetConfiguration saveRuleSetConfiguration(ConfigurationsVo configVo);
	
	public EregHierarchyRuleSetConfiguration getDefaultHierarchy(String ruleSetCd);
	
	public List<RoleEnum>  getEligibleRoles(Long programHierarchyId,String ruleSetTypeCode);


}
