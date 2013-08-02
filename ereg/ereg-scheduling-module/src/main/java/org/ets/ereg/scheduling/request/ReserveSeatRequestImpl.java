package org.ets.ereg.scheduling.request;

import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingOperation;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("reserveSeatRequest")
@Scope("prototype")
public class ReserveSeatRequestImpl extends AbstractSchedulingRequest implements
		ReserveSeatRequest {

	private HeldSeat heldSeat;
	
	@Override
	public HeldSeat getHeldSeat() {
		return heldSeat;
	}

	@Override
	public void setHeldSeat(HeldSeat heldSeat) {
		this.heldSeat = heldSeat;
	}

	@Override
	public SchedulingOperation getOperation() {
		
		return SchedulingOperation.RESERVE_SEAT;
	}
}
