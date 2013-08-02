package org.ets.ereg.scheduling.response;

import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;

public class ReserveSeatResponseImpl extends AbstractSchedulingResponse
		implements ReserveSeatResponse {

	private ReservedSeat reservedSeat;
	private boolean holdExpired;
	
	@Override
	public ReservedSeat getReservedSeat() {
		return reservedSeat;
	}

	@Override
	public void setReservedSeat(ReservedSeat reservedSeat) {
		this.reservedSeat = reservedSeat;
	}

	@Override
	public boolean isHoldExpired() {
		return holdExpired;
	}

	@Override
	public void setHoldExpired(boolean holdExpired) {
		this.holdExpired = holdExpired;
	}

}
