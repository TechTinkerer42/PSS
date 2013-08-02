package org.ets.ereg.scheduling.request;

import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatWithoutHoldRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingOperation;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public class ReserveSeatWithoutHoldRequestImpl extends
		AbstractSchedulingRequest implements ReserveSeatWithoutHoldRequest {

	private Seat seat;
	
	@Override
	public Seat getSeat() {
		return seat;
	}

	@Override
	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	@Override
	public SchedulingOperation getOperation() {		
		return SchedulingOperation.RESERVE_SEAT_WITHOUT_HOLD;
	}

}
