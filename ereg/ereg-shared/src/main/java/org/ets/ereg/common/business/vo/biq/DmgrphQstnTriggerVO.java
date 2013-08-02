package org.ets.ereg.common.business.vo.biq;


public class DmgrphQstnTriggerVO {
	private Long dependentQstnNo;
	private Long triggerRespNo;
	
	public Long getDependentQstnNo() {
		return dependentQstnNo;
	}
	public void setDependentQstnNo(Long dependentQstnNo) {
		this.dependentQstnNo = dependentQstnNo;
	}
	public Long getTriggerRespNo() {
		return triggerRespNo;
	}
	public void setTriggerRespNo(Long triggerRespNo) {
		this.triggerRespNo = triggerRespNo;
	}
	public String getTriggerArrayElement(){
		return "["+triggerRespNo+","+dependentQstnNo+"]";
	}	
	
}
