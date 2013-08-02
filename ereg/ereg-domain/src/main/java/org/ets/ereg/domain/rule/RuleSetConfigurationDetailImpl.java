package org.ets.ereg.domain.rule;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationParameterValue;


@Entity
@Table(name="RUL_SET_CNFGN_DTL")
public class RuleSetConfigurationDetailImpl implements RuleSetConfigurationDetail {
	
	private static final long serialVersionUID = 1L;

 	@Id 	    
	@Column(name="RUL_SET_CNFGN_DTL_ID")
 	@GeneratedValue(generator = "RuleSetConfigurationDetailId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "RuleSetConfigurationDetailId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "RuleSetConfigurationDetailImpl", allocationSize = 20)
	private Long ruleSetConfigurationDetailIdentifier;

	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleSetConfigurationImpl.class)
	    @JoinColumn(name="RUL_SET_CNFGN_ID", nullable=false)
	private RuleSetConfiguration ruleSetConfiguration;

	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleSetImpl.class)
	@JoinColumns( { 
    @JoinColumn(name="RUL_CDE", referencedColumnName="RUL_CDE"), 
    @JoinColumn(name="RUL_SET_TYP_CDE", referencedColumnName="RUL_SET_TYP_CDE") } )
	private RuleSet ruleSet;


	@OneToMany(fetch=FetchType.LAZY, mappedBy="ruleSetConfigurationDetail",targetEntity=RuleSetConfigurationParameterValueImpl.class,orphanRemoval=true,cascade=CascadeType.ALL)
	private Set<RuleSetConfigurationParameterValue> ruleSetConfigurationParameterValues = new HashSet<RuleSetConfigurationParameterValue>(0);

	@Override
    public Long getRuleSetConfigurationDetailIdentifier() {
        return this.ruleSetConfigurationDetailIdentifier;
    }
	@Override
    public void setRuleSetConfigurationDetailIdentifier(Long ruleSetConfigurationDetailIdentifier) {
        this.ruleSetConfigurationDetailIdentifier = ruleSetConfigurationDetailIdentifier;
    }
	@Override
    public RuleSetConfiguration getRuleSetConfiguration() {
        return this.ruleSetConfiguration;
    }
	@Override
    public void setRuleSetConfiguration(RuleSetConfiguration ruleSetConfiguration) {
        this.ruleSetConfiguration = ruleSetConfiguration;
    }
	@Override
    public RuleSet getRuleSet() {
        return this.ruleSet;
    }
	@Override
    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

	@Override 
    public Set<RuleSetConfigurationParameterValue> getRuleSetConfigurationParameterValues() {
        return this.ruleSetConfigurationParameterValues;
    }
	@Override  
    public void setRuleSetConfigurationParameterValues(Set<RuleSetConfigurationParameterValue> ruleSetConfigurationParameterValues) {
        this.ruleSetConfigurationParameterValues = ruleSetConfigurationParameterValues;
    }




}
