package org.ets.ereg.scheduling.request;

import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingOperation;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("cancelSeatRequest")
@Scope("prototype")
public class CancelSeatRequestImpl extends AbstractSchedulingRequest implements
		CancelSeatRequest {

	private ReservedSeat reservedSeat;
	private String cancelReasonCode;
	
	@Override
	public String getCancelReasonCode() {
		return cancelReasonCode;
	}

	@Override
	public void setCancelReasonCode(String cancelReasonCode) {
		this.cancelReasonCode = cancelReasonCode;
	}

	@Override
	public SchedulingOperation getOperation() {
		return SchedulingOperation.CANCEL_SEAT;
	}

	@Override
	public ReservedSeat getReservedSeat() {
		return reservedSeat;
	}

	@Override
	public void setReservedSeat(ReservedSeat reservedSeat) {
		this.reservedSeat = reservedSeat;
	}

}
