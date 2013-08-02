package org.ets.ereg.domain.interfaces.model.rule;

import java.io.Serializable;
import java.util.Set;

public interface Rule extends Serializable{

	   
    public String getRuleCode();
    
    public void setRuleCode(String ruleCode);
    
    public String getRuleName();
    
    public void setRuleName(String ruleName);

    public String getRuleDescription();
    
    public void setRuleDescription(String ruleDescription);
  
    public Set<RuleAllowableParameter> getRuleAllowableParameters();
    
    public void setRuleAllowableParameters(Set<RuleAllowableParameter> ruleAllowableParameters);
    public Set<RuleSet> getRuleSets();
    
    public void setRuleSets(Set<RuleSet> ruleSets);

}
