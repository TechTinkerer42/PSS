package org.ets.ereg.scheduling.response;

import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public class ReservedSeatImpl extends HeldSeatImpl implements ReservedSeat {

	public ReservedSeatImpl() {
		super();		
	}
	
	public ReservedSeatImpl(Seat seat) {
		super(seat);		
	}

	private String etsReservationID;

	@Override
	public String getEtsReservationID() {		
		return etsReservationID;
	}

	@Override
	public void setEtsReservationID(String etsReservationID) {
		this.etsReservationID = etsReservationID;		
	}


}
