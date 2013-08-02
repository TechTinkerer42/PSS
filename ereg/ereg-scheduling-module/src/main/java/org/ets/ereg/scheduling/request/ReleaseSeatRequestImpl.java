package org.ets.ereg.scheduling.request;

import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingOperation;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("releaseSeatRequest")
@Scope("prototype")
public class ReleaseSeatRequestImpl extends AbstractSchedulingRequest implements
		ReleaseSeatRequest {

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
		
		return SchedulingOperation.RELEASE_SEAT;
	}

}
