package org.ets.ereg.common.business.vo;

public class ConfigurationsVo {
	
	private String ruleSetType;
	private Long hierarchy;
	private String roleName;
	private String []rules;
	private String []paramNames;
	private String []paramValues;
	private String ruleSelectedType;
	private Long adminId=0L;
	private boolean createFlag;
	public String[] getRules() {
		return rules;
	}
	public void setRules(String[] rules) {
		if(rules != null){
			this.rules = rules.clone();
		}
	}
	public String[] getParamNames() {
		return paramNames;
	}
	public void setParamNames(String[] paramNames) {
		if(paramNames != null){
			this.paramNames = paramNames.clone();
		}
	}
	public String[] getParamValues() {
		return paramValues;
	}
	public void setParamValues(String[] paramValues) {
		if(paramValues != null){
			this.paramValues = paramValues.clone();
		}
	}
	public String getRuleSetType() {
		return ruleSetType;
	}
	public void setRuleSetType(String ruleSetType) {
		this.ruleSetType = ruleSetType;
	}
	public String getRuleSelectedType() {
		return ruleSelectedType;
	}
	public void setRuleSelectedType(String ruleSelectedType) {
		this.ruleSelectedType = ruleSelectedType;
	}
	public Long getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(Long hierarchy) {
		this.hierarchy = hierarchy;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public boolean isCreateFlag() {
		return createFlag;
	}
	public void setCreateFlag(boolean createFlag) {
		this.createFlag = createFlag;
	}

}
