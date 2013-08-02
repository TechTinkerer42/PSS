package org.ets.ereg.scheduling.util;

public enum TestStatusEnum {
	
	SCHEDULED ("SCDL"),
	CANCELED ("CNCL");
	
	private String code;
	
	private TestStatusEnum (String code) {
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
