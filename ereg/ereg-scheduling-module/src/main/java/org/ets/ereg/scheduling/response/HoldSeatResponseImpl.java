package org.ets.ereg.scheduling.response;

import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;

public class HoldSeatResponseImpl extends AbstractSchedulingResponse implements
		HoldSeatResponse {

	private HeldSeat heldSeat;
	
	@Override
	public HeldSeat getHeldSeat() {
		return heldSeat;
	}

	@Override
	public void setHeldSeat(HeldSeat heldSeat) {
		this.heldSeat = heldSeat;
	}

}
