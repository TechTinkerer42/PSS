package org.ets.ereg.domain.interfaces.model.rule;

import java.util.Set;


public interface RuleSetConfigurationDetail  extends java.io.Serializable {
   
    public Long getRuleSetConfigurationDetailIdentifier();
    
    public void setRuleSetConfigurationDetailIdentifier(Long ruleSetConfigurationDetailIdentifier);
    
    public RuleSetConfiguration getRuleSetConfiguration();
    
    public void setRuleSetConfiguration(RuleSetConfiguration ruleSetConfiguration);
   
    public RuleSet getRuleSet();
    
    public void setRuleSet(RuleSet ruleSet);
    
    public Set<RuleSetConfigurationParameterValue> getRuleSetConfigurationParameterValues();
    
    public void setRuleSetConfigurationParameterValues(Set<RuleSetConfigurationParameterValue> ruleSetConfigurationParameterValues);


}
