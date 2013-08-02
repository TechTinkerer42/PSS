package org.ets.ereg.domain.rule;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.rule.Rule;
import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;


@Entity
@Table(name="RUL")
public class RuleImpl implements Rule {
	
	private static final long serialVersionUID = 1L;
 	@Id     
	@Column(name="RUL_CDE", length=20)
	private String ruleCode;
  
	@Column(name="RUL_NAM", nullable=false, length=50)
	private String ruleName;    
	@Column(name="RUL_DSC", nullable=false, length=200)
	private String ruleDescription;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="rule",targetEntity=RuleAllowableParameterImpl.class)
	private Set<RuleAllowableParameter> ruleAllowableParameters = new HashSet<RuleAllowableParameter>(0);
	@OneToMany(fetch=FetchType.LAZY, mappedBy="rule",targetEntity=RuleSetImpl.class)
	private Set<RuleSet> ruleSets = new HashSet<RuleSet>(0);

	@Override
    public String getRuleCode() {
        return this.ruleCode;
    }
    @Override
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }
    @Override
    public String getRuleName() {
        return this.ruleName;
    }
    @Override
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    @Override
    public String getRuleDescription() {
        return this.ruleDescription;
    }
    @Override
    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }
  
    @Override
	public Set<RuleAllowableParameter> getRuleAllowableParameters() {
		return ruleAllowableParameters;
	}
    @Override
    public void setRuleAllowableParameters(Set<RuleAllowableParameter> ruleAllowableParameters) {
        this.ruleAllowableParameters = ruleAllowableParameters;
    }
    @Override
    public Set<RuleSet> getRuleSets() {
        return this.ruleSets;
    }
    @Override
    public void setRuleSets(Set<RuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ruleCode).toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof RuleImpl) {
            final RuleImpl other = (RuleImpl) obj;
            return new EqualsBuilder().append(ruleCode, other.ruleCode)
                .isEquals();
        } else {
            return false;
        }
    }

	



}
