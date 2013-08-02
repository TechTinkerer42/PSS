package org.ets.ereg.scheduling.util;

public enum SchedulingTypeEnum {

	TCA_MODEL ("TCAM"),
	CANDIDATE_MODEL ("CANDM");
	
	private String code;
	
	private SchedulingTypeEnum (String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return code;
	}
	
}
