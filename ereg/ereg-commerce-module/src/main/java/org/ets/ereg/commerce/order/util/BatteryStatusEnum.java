package org.ets.ereg.commerce.order.util;

public enum BatteryStatusEnum {
	
	OPEN ("OPEN"),
	CANCELED ("CANCELLED"),
	EXPIRED ("EXPIRED"),
	CONSUMED ("CONSUMED"),
	UNUSED("UNUSED"),
	CLOSED("CLOSED");
	
	private String code;
	
	private BatteryStatusEnum (String code) {
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
