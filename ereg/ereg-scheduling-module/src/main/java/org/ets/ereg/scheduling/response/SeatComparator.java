package org.ets.ereg.scheduling.response;

import java.util.Comparator;

import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public class SeatComparator implements Comparator<Seat> {

	@Override
	public int compare(Seat seat1, Seat seat2) {
		
		return seat1.getLocalStartDateTime().compareTo(seat2.getLocalStartDateTime());
	}

}
