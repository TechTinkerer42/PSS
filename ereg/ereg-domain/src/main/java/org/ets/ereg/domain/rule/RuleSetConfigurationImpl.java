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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ets.ereg.domain.hierarchy.EregHierarchyRuleSetConfigurationImpl;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetType;

@Entity
@Table(name="RUL_SET_CNFGN")
public class RuleSetConfigurationImpl  implements RuleSetConfiguration {
	
	private static final long serialVersionUID = 1L;
    @Id 
    @Column(name="RUL_SET_CNFGN_ID")
    @GeneratedValue(generator = "RuleSetConfigurationId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "RuleSetConfigurationId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "RuleSetConfigurationImpl", allocationSize = 20)
	private Long ruleSetConfigurationIdentifier;


	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleSetTypeImpl.class)
	    
    @JoinColumn(name="RUL_SET_TYP_CDE", referencedColumnName="RUL_SET_TYP_CDE")
	private RuleSetType ruleSetType;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="ruleSetConfiguration",targetEntity=RuleSetConfigurationDetailImpl.class,orphanRemoval=true ,cascade=CascadeType.ALL)
	private Set<RuleSetConfigurationDetail> ruleSetConfigurationDetails = new HashSet<RuleSetConfigurationDetail>(0);


	@OneToMany(fetch=FetchType.EAGER, mappedBy="ruleSetConfiguration",targetEntity=EregHierarchyRuleSetConfigurationImpl.class)
	private Set<EregHierarchyRuleSetConfiguration> eregHierarchyRuleSetConfigurations = new HashSet<EregHierarchyRuleSetConfiguration>(0);

	@Override
    public Long getRuleSetConfigurationIdentifier() {
        return this.ruleSetConfigurationIdentifier;
    }
	@Override
    public void setRuleSetConfigurationIdentifier(Long ruleSetConfigurationIdentifier) {
        this.ruleSetConfigurationIdentifier = ruleSetConfigurationIdentifier;
    }
	@Override
    public RuleSetType getRuleSetType() {
        return this.ruleSetType;
    }
	@Override
    public void setRuleSetType(RuleSetType ruleSetType) {
        this.ruleSetType = ruleSetType;
    }
	@Override
    public Set<RuleSetConfigurationDetail> getRuleSetConfigurationDetails() {
        return this.ruleSetConfigurationDetails;
    }
	@Override
    public void setRuleSetConfigurationDetails(Set<RuleSetConfigurationDetail> ruleSetConfigurationDetails) {
        this.ruleSetConfigurationDetails = ruleSetConfigurationDetails;
    }
	@Override
    public Set<EregHierarchyRuleSetConfiguration> getEregHierarchyRuleSetConfigurations() {
        return this.eregHierarchyRuleSetConfigurations;
    }
	@Override
    public void setEregHierarchyRuleSetConfigurations(Set<EregHierarchyRuleSetConfiguration> eregHierarchyRuleSetConfigurations) {
        this.eregHierarchyRuleSetConfigurations = eregHierarchyRuleSetConfigurations;
    }



}
