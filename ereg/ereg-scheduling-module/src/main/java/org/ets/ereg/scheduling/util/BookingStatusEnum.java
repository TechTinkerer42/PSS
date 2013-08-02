package org.ets.ereg.scheduling.util;

public enum BookingStatusEnum {
	
	RESERVED ("RSRVD"),
	CANCELED ("CNCL"),	
	HELD ("HELD"),
	PENDING_CANCELLATION("PCNCL"),
	RELEASED("RLSD");
	
	private String code;
	
	private BookingStatusEnum (String code) {
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
