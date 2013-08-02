package org.ets.ereg.common.business.dao;

import java.util.List;

import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.ets.ereg.common.business.vo.CodeValuePairVo;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationParameterValue;


public interface RulesSetConfigurationDao extends Dao<RuleSetConfiguration> {
	public Long getRuleSetConfigurationIdTobeApplied(Long programHierarchyId, String ruleSetTypeCode,Long adminRoleId);
	public String getConfigurationValue(Long eregHierarchyId,String ruleSetTypeCd, String ruleCd,Long adminRoleId);
	public List<RuleSetConfigurationParameterValue> getConfigurations(String ruleSetTypeCd);
    public List<EregHierarchy> getEregHierachys(String pgmCode);
    public AdminRole getAdminRole(String adminRoleCd);
    public EregHierarchy getEregHierarchy(Long hierarchy);
    public List<RuleSet> getRuleSetByCode(String ruleSetCd);
	public List<CodeValuePairVo> getRuleSetConfigurationParameterValue(String ruleCode);
    public List<EregHierarchy> getChildren(Long eregHierarchyId);
    public RuleSetConfiguration createRuleSetConfiguration();
	public RuleSetConfigurationDetail createRuleSetConfigurationDetail();
	public RuleSetConfigurationParameterValue createRuleSetConfigurationParameterValue();
	public RuleSet getRuleSet(String ruleCd,String ruleSetTypeCd);
	public EregHierarchyRuleSetConfiguration createEregHierarchyRuleSetConfiguration();
	public EregHierarchyRuleSetConfiguration saveHierarchyRuleSetConfiguration(EregHierarchyRuleSetConfiguration eregHierRSConfig);
	public EregHierarchyRuleSetConfiguration getDefaultHierarchy(String ruleSetCd);
	public List<RoleEnum>  getEligibleRoles(Long programHierarchyId,String ruleSetTypeCode);
   
}
