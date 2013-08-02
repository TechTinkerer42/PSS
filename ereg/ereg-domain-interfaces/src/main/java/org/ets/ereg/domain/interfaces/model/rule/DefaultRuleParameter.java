package org.ets.ereg.domain.interfaces.model.rule;

import java.io.Serializable;

public interface DefaultRuleParameter extends Serializable{
	 public Long getDefaultRuleParameterIdentifier();
	    
	 public void setDefaultRuleParameterIdentifier(Long defaultRuleParameterIdentifier);
	   
	 public RuleAllowableParameter getRuleAllowableParameter();
	    
	 public void setRuleAllowableParameter(RuleAllowableParameter ruleAllowableParameter);
	  
	 public RuleSetType getRuleSetType();
	    
	 public void setRuleSetType(RuleSetType ruleSetType);
		
	 public String getDefaultValue();
		
	 public void setDefaultValue(String defaultValue);
	    


}
