package org.ets.ereg.domain.interfaces.model.rule;

import java.io.Serializable;
import java.util.Set;

public interface RuleParameter extends Serializable {
	  public String getRuleParameterCd();
	  public void setRuleParameterCd(String ruleParameterCd);
	  public String getDescription();
	  public void setDescription(String description);
	  public Set<RuleAllowableParameter> getRuleAllowableParameters();
	  public void setRuleAllowableParameters(Set<RuleAllowableParameter> ruleAllowableParameters);

}
