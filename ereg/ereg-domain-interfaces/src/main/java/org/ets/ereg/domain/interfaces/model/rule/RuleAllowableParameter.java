package org.ets.ereg.domain.interfaces.model.rule;

import java.io.Serializable;
import java.util.Set;

public interface RuleAllowableParameter extends Serializable {
    
    public Rule getRule();
    
    public void setRule(Rule rule);
   
    public RuleParameter getRuleParameter();
    
    public void setRuleParameter(RuleParameter ruleParameter);
    
    public Set<RuleSetConfigurationParameterValue> getRuleSetConfigurationParameterValues();
    
    public void setRuleSetConfigurationParameterValues(Set<RuleSetConfigurationParameterValue> ruleSetConfigurationParameterValues) ;
    
	public String getRuleParameterCd();
	
	public void setRuleParameterCd(String ruleParameterCd);

}
