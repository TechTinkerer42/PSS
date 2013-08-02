package org.ets.ereg.scheduling.vo;

import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;

public class ExtendHoldSeatInfo {
	private ExtendHoldSeatRequest extendHoldSeatRequest;
	private long firstHoldTime = System.currentTimeMillis();
	private long lastHoldTime = System.currentTimeMillis();
	
	public ExtendHoldSeatInfo(ExtendHoldSeatRequest extendHoldSeatRequest){
		this.extendHoldSeatRequest = extendHoldSeatRequest;
	}

	public ExtendHoldSeatRequest getExtendHoldSeatRequest() {
		return extendHoldSeatRequest;
	}
	public void setExtendHoldSeatRequest(ExtendHoldSeatRequest extendHoldSeatRequest) {
		this.extendHoldSeatRequest = extendHoldSeatRequest;
	}

	public long getLastHoldTime() {
		return lastHoldTime;
	}

	public void setLastHoldTime(long lastHoldTime) {
		this.lastHoldTime = lastHoldTime;
	}

	public long getFirstHoldTime() {
		return firstHoldTime;
	}
}
