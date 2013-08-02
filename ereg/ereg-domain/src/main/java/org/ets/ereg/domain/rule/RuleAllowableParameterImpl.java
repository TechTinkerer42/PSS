package org.ets.ereg.domain.rule;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.rule.Rule;
import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationParameterValue;

@Entity
@Table(name="RUL_ALWBL_PARM")
public class RuleAllowableParameterImpl  implements RuleAllowableParameter {
	
	private static final long serialVersionUID = 1L;
	@Id     
	@Column(name="RUL_PARM_CDE")
	private String ruleParameterCd;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleImpl.class)
	    @JoinColumn(name="RUL_CDE", nullable=false, insertable=false, updatable=false)
	private Rule rule;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleParameterImpl.class)
    @JoinColumn(name="RUL_PARM_CDE", referencedColumnName="RUL_PARM_CDE", nullable=false, insertable=false, updatable=false)     
	private RuleParameter ruleParameter;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="ruleAllowableParameter",targetEntity=RuleSetConfigurationParameterValueImpl.class)
	private Set<RuleSetConfigurationParameterValue> ruleSetConfigurationParameterValues = new HashSet<RuleSetConfigurationParameterValue>(0);

	@Override
    public Rule getRule() {
        return this.rule;
    }
	@Override
    public void setRule(Rule rule) {
        this.rule = rule;
    }
	@Override
    public RuleParameter getRuleParameter() {
        return this.ruleParameter;
    }
	@Override
    public void setRuleParameter(RuleParameter ruleParameter) {
        this.ruleParameter = ruleParameter;
    }
	@Override
    public Set<RuleSetConfigurationParameterValue> getRuleSetConfigurationParameterValues() {
        return this.ruleSetConfigurationParameterValues;
    }
	@Override
    public void setRuleSetConfigurationParameterValues(Set<RuleSetConfigurationParameterValue> ruleSetConfigurationParameterValues) {
        this.ruleSetConfigurationParameterValues = ruleSetConfigurationParameterValues;
    }
	@Override
	public String getRuleParameterCd() {
		return ruleParameterCd;
	}
	@Override
	public void setRuleParameterCd(String ruleParameterCd) {
		this.ruleParameterCd = ruleParameterCd;
	}




}
