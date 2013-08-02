package org.ets.ereg.domain.interfaces.model.rule;

import java.util.Set;

import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;


public interface RuleSetConfiguration  extends java.io.Serializable {
	
    public Long getRuleSetConfigurationIdentifier();
    
    public void setRuleSetConfigurationIdentifier(Long ruleSetConfigurationIdentifier);
    
    public RuleSetType getRuleSetType();
    
    public void setRuleSetType(RuleSetType ruleSetType);
    
    public Set<RuleSetConfigurationDetail> getRuleSetConfigurationDetails();
    
    public void setRuleSetConfigurationDetails(Set<RuleSetConfigurationDetail> ruleSetConfigurationDetails);
    
    public Set<EregHierarchyRuleSetConfiguration> getEregHierarchyRuleSetConfigurations();
    
    public void setEregHierarchyRuleSetConfigurations(Set<EregHierarchyRuleSetConfiguration> eregHierarchyRuleSetConfigurations);



}
