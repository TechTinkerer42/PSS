package org.ets.ereg.domain.interfaces.model.rule;

import java.io.Serializable;
import java.util.Set;


public interface RuleSet extends Serializable {
	public RuleSetId getId();

	public void setId(RuleSetId id);

    public Rule getRule();

    public void setRule(Rule rule);

	public RuleSetType getRuleSetType();

	public void setRuleSetType(RuleSetType ruleSetType);

	public Set<RuleSetConfigurationDetail> getRuleSetConfigurationDetails();

	public void setRuleSetConfigurationDetails(Set<RuleSetConfigurationDetail> ruleSetConfigurationDetails);
}
