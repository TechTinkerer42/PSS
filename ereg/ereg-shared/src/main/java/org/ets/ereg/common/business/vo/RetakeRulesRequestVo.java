package org.ets.ereg.common.business.vo;


import java.sql.Timestamp;

public class RetakeRulesRequestVo {

	private Long customerId;
	private Timestamp ruleStartDate;
	private Timestamp ruleEndDate;
    private Long testId;
	private String deliveryTypeCode;
	private Long eregHierarchyId;
	private Long adminRoleId;
	private String ruleSetType;

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Timestamp getRuleStartDate() {
		return ruleStartDate;
	}
	public void setRuleStartDate(Timestamp ruleStartDate) {
		this.ruleStartDate = ruleStartDate;
	}
	public Timestamp getRuleEndDate() {
		return ruleEndDate;
	}
	public void setRuleEndDate(Timestamp ruleEndDate) {
		this.ruleEndDate = ruleEndDate;
	}
    public Long getTestId() {
        return testId;
	}
    public void setTestId(Long testId) {
        this.testId = testId;
	}
	public String getDeliveryTypeCode() {
		return deliveryTypeCode;
	}
	public void setDeliveryTypeCode(String deliveryTypeCode) {
		this.deliveryTypeCode = deliveryTypeCode;
	}
	public Long getEregHierarchyId() {
		return eregHierarchyId;
	}
	public void setEregHierarchyId(Long eregHierarchyId) {
		this.eregHierarchyId = eregHierarchyId;
	}
	public Long getAdminRoleId() {
		return adminRoleId;
	}
	public void setAdminRoleId(Long adminRoleId) {
		this.adminRoleId = adminRoleId;
	}
	public String getRuleSetType() {
		return ruleSetType;
	}
	public void setRuleSetType(String ruleSetType) {
		this.ruleSetType = ruleSetType;
	}

	@Override
	public boolean equals(Object object){
		if(object instanceof RetakeRulesRequestVo){
			RetakeRulesRequestVo retakesRetakeRulesRequestVo = (RetakeRulesRequestVo) object;
			if(((customerId == null && retakesRetakeRulesRequestVo.getCustomerId() == null)
					|| (customerId != null && customerId.equals(retakesRetakeRulesRequestVo.getCustomerId())))
					&& ((ruleStartDate == null && retakesRetakeRulesRequestVo.getRuleStartDate() == null)
							|| (ruleStartDate != null && ruleStartDate.equals(retakesRetakeRulesRequestVo.getRuleStartDate())))
					&& ((ruleEndDate == null && retakesRetakeRulesRequestVo.getRuleEndDate() == null)
							|| (ruleEndDate != null && ruleEndDate.equals(retakesRetakeRulesRequestVo.getRuleEndDate())))
					&& (((testId == null || testId.equals(Long.valueOf(0))) && retakesRetakeRulesRequestVo.getTestId().equals(Long.valueOf(0)))
							|| ((testId != null && testId > 0) && testId.equals(retakesRetakeRulesRequestVo.getTestId())))
					&& ((deliveryTypeCode == null && retakesRetakeRulesRequestVo.getDeliveryTypeCode() == null)
							|| (deliveryTypeCode != null && deliveryTypeCode.equals(retakesRetakeRulesRequestVo.getDeliveryTypeCode())))
					&& ((eregHierarchyId == null && retakesRetakeRulesRequestVo.getEregHierarchyId() == null)
							|| (eregHierarchyId != null && eregHierarchyId.equals(retakesRetakeRulesRequestVo.getEregHierarchyId())))
					&& ((adminRoleId == null && retakesRetakeRulesRequestVo.getAdminRoleId() == null)
							|| (adminRoleId != null && adminRoleId.equals(retakesRetakeRulesRequestVo.getAdminRoleId())))
					&& ((ruleSetType == null && retakesRetakeRulesRequestVo.getRuleSetType() == null)
							|| (ruleSetType != null && ruleSetType.equals(retakesRetakeRulesRequestVo.getRuleSetType())))){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((ruleStartDate == null) ? 0 : ruleStartDate.hashCode());
        result = prime * result + ((ruleEndDate == null) ? 0 : ruleEndDate.hashCode());
        result = prime * result + ((testId == null) ? 0 : testId.intValue());
        result = prime * result + ((deliveryTypeCode == null) ? 0 : deliveryTypeCode.hashCode());
        result = prime * result + ((eregHierarchyId == null) ? 0 : eregHierarchyId.hashCode());
        result = prime * result + ((adminRoleId == null) ? 0 : adminRoleId.hashCode());
        result = prime * result + ((ruleSetType == null) ? 0 : ruleSetType.hashCode());
        return result;

	}

}
