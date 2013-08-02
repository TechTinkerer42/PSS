package org.ets.ereg.domain.rule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.rule.DefaultRuleParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetType;

@Entity
@Table(name="RUL_PARM_DFLT")
public class DefaultRuleParameterImpl implements DefaultRuleParameter {
	
	private static final long serialVersionUID = 1L;
	@Id 	    
	@Column(name="DFLT_RUL_PARM_ID")
	private Long defaultRuleParameterIdentifier;
	
	@OneToOne(targetEntity=RuleAllowableParameterImpl.class)
	@JoinColumns( { 
    @JoinColumn(name="RUL_CDE", referencedColumnName="RUL_CDE"), 
    @JoinColumn(name="RUL_PARM_CDE", referencedColumnName="RUL_PARM_CDE") } )
	private RuleAllowableParameter ruleAllowableParameter;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleSetTypeImpl.class)
    @JoinColumn(name="RUL_SET_TYP_CDE", referencedColumnName="RUL_SET_TYP_CDE") 
    private RuleSetType ruleSetType;
	
	@Column(name="DFLT_VAL")
	private String defaultValue;

	@Override
	public Long getDefaultRuleParameterIdentifier() {
		return defaultRuleParameterIdentifier;
	}

	@Override
	public void setDefaultRuleParameterIdentifier(Long defaultRuleParameterIdentifier) {
		this.defaultRuleParameterIdentifier=defaultRuleParameterIdentifier;
	}

	@Override
	public RuleAllowableParameter getRuleAllowableParameter() {
		return ruleAllowableParameter;
	}

	@Override
	public void setRuleAllowableParameter(RuleAllowableParameter ruleAllowableParameter) {
		this.ruleAllowableParameter=ruleAllowableParameter;

	}

	@Override
	public RuleSetType getRuleSetType() {
		return ruleSetType;
	}

	@Override
	public void setRuleSetType(RuleSetType ruleSetType) {
		this.ruleSetType=ruleSetType;

	}
	@Override
	public String getDefaultValue() {
		return defaultValue;
	}
	@Override
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
