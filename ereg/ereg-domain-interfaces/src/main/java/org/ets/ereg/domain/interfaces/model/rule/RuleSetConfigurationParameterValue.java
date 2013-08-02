package org.ets.ereg.domain.interfaces.model.rule;

import java.io.Serializable;


public interface RuleSetConfigurationParameterValue extends Serializable {
	 public Long getRuleSetConfigurationParameterValueIdentifier();
	    
	 public void setRuleSetConfigurationParameterValueIdentifier(Long ruleSetConfigurationParameterValueIdentifier);
	   
	 public RuleSetConfigurationDetail getRuleSetConfigurationDetail();
	    
	 public void setRuleSetConfigurationDetail(RuleSetConfigurationDetail ruleSetConfigurationDetail);
	   
	 public RuleAllowableParameter getRuleAllowableParameter();
	    
	 public void setRuleAllowableParameter(RuleAllowableParameter ruleAllowableParameter);
	    
	 public String getRuleParameterValueText();
	    
	 public void setRuleParameterValueText(String ruleParameterValueText);
	 
	 public Long getParameterValueSequenceNumber();
	 
	 public void setParameterValueSequenceNumber(Long parameterValueSequenceNumber);

}
