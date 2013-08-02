package org.ets.ereg.common.util;

public enum DistanceUnit {
	KM("KM"), MILE("MILE");
	
	private String code;
	
	private DistanceUnit(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString(){
		return code;
	}
}
