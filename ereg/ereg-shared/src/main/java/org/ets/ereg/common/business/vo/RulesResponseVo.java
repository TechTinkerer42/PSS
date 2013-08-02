package org.ets.ereg.common.business.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RulesResponseVo {
	private Timestamp ruleAppliedDate;
	private List<String> conflictRuleCode = new ArrayList<String>();
	
	public Timestamp getRuleAppliedDate() {
		return ruleAppliedDate;
	}
	public void setRuleAppliedDate(Timestamp ruleAppliedDate) {
		this.ruleAppliedDate = ruleAppliedDate;
	}
	public List<String> getConflictRuleCode() {
		return conflictRuleCode;
	}
	public void setConflictRuleCode(List<String> conflictRuleCode) {
		this.conflictRuleCode = conflictRuleCode;
	}
	@Override
	public String toString() {
		return "RulesResponseVo [ruleAppliedDate=" + ruleAppliedDate
				+ ", conflictRuleCode=" + conflictRuleCode + "]";
	}
	
	
	public String getFormattedAppliedDate(){
		return  new SimpleDateFormat("MM/dd/yyyy").format(ruleAppliedDate);
	}
	
	
	
}
