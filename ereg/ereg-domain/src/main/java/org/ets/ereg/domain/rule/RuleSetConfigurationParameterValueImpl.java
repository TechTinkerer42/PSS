package org.ets.ereg.domain.rule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationParameterValue;

@Entity
@Table(name="RUL_SET_CNFGN_PARM_VAL")
public class RuleSetConfigurationParameterValueImpl  implements RuleSetConfigurationParameterValue {
	
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(generator = "RuleSetConfigurationParameterValueId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "RuleSetConfigurationParameterValueId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "RuleSetConfigurationParameterValueImpl", allocationSize = 20)
	@Column(name="RUL_SET_CNFGN_PARM_VAL_ID")
	private Long ruleSetConfigurationParameterValueIdentifier;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleSetConfigurationDetailImpl.class)
	@JoinColumn(name="RUL_SET_CNFGN_DTL_ID")
	private RuleSetConfigurationDetail ruleSetConfigurationDetail;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleAllowableParameterImpl.class)
	@JoinColumns( { 
    @JoinColumn(name="RUL_CDE", referencedColumnName="RUL_CDE"), 
    @JoinColumn(name="RUL_PARM_CDE", referencedColumnName="RUL_PARM_CDE") } )
	private RuleAllowableParameter ruleAllowableParameter;    
	@Column(name="RUL_PARM_VAL_TXT", nullable=false, length=2000)
	private String ruleParameterValueText;
	@Column(name="PARM_VAL_SEQ_NO")
	private Long parameterValueSequenceNumber;
    @Override
    public Long getRuleSetConfigurationParameterValueIdentifier() {
        return this.ruleSetConfigurationParameterValueIdentifier;
    }
    @Override
    public void setRuleSetConfigurationParameterValueIdentifier(Long ruleSetConfigurationParameterValueIdentifier) {
        this.ruleSetConfigurationParameterValueIdentifier = ruleSetConfigurationParameterValueIdentifier;
    }
    @Override
    public RuleSetConfigurationDetail getRuleSetConfigurationDetail() {
        return this.ruleSetConfigurationDetail;
    }
    @Override
    public void setRuleSetConfigurationDetail(RuleSetConfigurationDetail ruleSetConfigurationDetail) {
        this.ruleSetConfigurationDetail = ruleSetConfigurationDetail;
    }
    @Override
    public RuleAllowableParameter getRuleAllowableParameter() {
        return this.ruleAllowableParameter;
    }
    @Override
    public void setRuleAllowableParameter(RuleAllowableParameter ruleAllowableParameter) {
        this.ruleAllowableParameter = ruleAllowableParameter;
    }

    @Override
    public String getRuleParameterValueText() {
        return this.ruleParameterValueText;
    }
    @Override
    public void setRuleParameterValueText(String ruleParameterValueText) {
        this.ruleParameterValueText = ruleParameterValueText;
    }
    @Override
    public Long getParameterValueSequenceNumber() {
        return this.parameterValueSequenceNumber;
    }
    @Override
    public void setParameterValueSequenceNumber(Long parameterValueSequenceNumber) {
        this.parameterValueSequenceNumber = parameterValueSequenceNumber;
    }




}
