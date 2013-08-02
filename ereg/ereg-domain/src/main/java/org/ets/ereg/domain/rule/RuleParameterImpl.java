package org.ets.ereg.domain.rule;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleParameter;


@Entity
@Table(name="RUL_PARM")
public class RuleParameterImpl  implements RuleParameter {
	
	private static final long serialVersionUID = 1L;
	@Id     
	@Column(name="RUL_PARM_CDE")
	private String ruleParameterCd;  
	@Column(name="DSC", length=175)
	private String description;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="ruleParameter",targetEntity=RuleAllowableParameterImpl.class)
	private Set<RuleAllowableParameter> ruleAllowableParameters = new HashSet<RuleAllowableParameter>(0);


	@Override
    public String getDescription() {
        return this.description;
    }
	@Override
    public void setDescription(String description) {
        this.description = description;
    }
	@Override
    public Set<RuleAllowableParameter> getRuleAllowableParameters() {
        return this.ruleAllowableParameters;
    }
	@Override
    public void setRuleAllowableParameters(Set<RuleAllowableParameter> ruleAllowableParameters) {
        this.ruleAllowableParameters = ruleAllowableParameters;
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
