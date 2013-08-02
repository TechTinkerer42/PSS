package org.ets.ereg.domain.rule;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.rule.Rule;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetId;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetType;


@Entity
@Table(name="RUL_SET")
public class RuleSetImpl  implements RuleSet {
	
	private static final long serialVersionUID = 1L;
	@EmbeddedId    
	@AttributeOverrides( {
    @AttributeOverride(name="ruleCode", column=@Column(name="RUL_CDE", nullable=false, length=20) ), 
    @AttributeOverride(name="ruleSetTypeCode", column=@Column(name="RUL_SET_TYP_CDE", nullable=false, length=10) ) } )
	private RuleSetId id;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleImpl.class)
	@JoinColumn(name="RUL_CDE", nullable=false, insertable=false, updatable=false)
	private Rule rule;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleSetTypeImpl.class)
    @JoinColumn(name="RUL_SET_TYP_CDE", referencedColumnName="RUL_SET_TYP_CDE", nullable=false, insertable=false, updatable=false) 
	private RuleSetType ruleSetType;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="ruleSet",targetEntity=RuleSetConfigurationDetailImpl.class)
	private Set<RuleSetConfigurationDetail> ruleSetConfigurationDetails = new HashSet<RuleSetConfigurationDetail>(0);

   @Override
    public RuleSetId getId() {
        return this.id;
    }
   @Override
    public void setId(RuleSetId id) {
        this.id = id;
    }
   @Override
    public Rule getRule() {
        return this.rule;
    }
   @Override
    public void setRule(Rule rule) {
        this.rule = rule;
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
   public int hashCode() {
       return new HashCodeBuilder().append(id.getRuleCode())
    		   .append(id.getRuleSetTypeCode()).toHashCode();
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(final Object obj) {
       if (obj instanceof RuleSetImpl) {
           final RuleSetImpl other = (RuleSetImpl) obj;
           return new EqualsBuilder().append(id.getRuleCode(), other.id.getRuleCode())
        		   .append(id.getRuleSetTypeCode(), other.id.getRuleSetTypeCode())
               .isEquals();
       } else {
           return false;
       }
   }



}
