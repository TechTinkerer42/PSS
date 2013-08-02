package org.ets.ereg.scheduling.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.domain.interfaces.scheduling.response.AvailabilityStatus;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.NonAvailabilityReason;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public class FindSeatResponseImpl extends AbstractSchedulingResponse implements
		FindSeatResponse, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,SortedSet<Seat>> seatsMap = new HashMap<String, SortedSet<Seat>>();

	@Override
	public List<Seat> getSeats(){
		List<Seat> seatsList = new ArrayList<Seat>();
		if(!seatsMap.isEmpty()){

			Set<Entry<String,SortedSet<Seat>>> seatEntrySet = seatsMap.entrySet();

			for(Entry<String,SortedSet<Seat>> seatEntry: seatEntrySet){

				seatsList.addAll(seatEntry.getValue());
			}
		}
		return seatsList;
	}

	@Override
	public SortedSet<Seat> getSeats(Date startDate) {
		String startDateYYYYMMDD = DateHandler.convertDateToString(startDate);
		return seatsMap.get(startDateYYYYMMDD);
	}

	@Override
	public SortedSet<Seat> getSeats(String strStartDate) {
		return seatsMap.get(strStartDate);
	}

	@Override
	public Map<String, SortedSet<Seat>> addSeat(Seat seat) {

		Date startDateTime  = seat.getLocalStartDateTime().getTime();
		String startDateYYYYMMDD = DateHandler.convertDateToString(startDateTime);
		SortedSet<Seat> sortedSeatSet = null;

		if(seatsMap.containsKey(startDateYYYYMMDD)){
			sortedSeatSet = seatsMap.get(startDateYYYYMMDD);
			sortedSeatSet.add(seat);
		}else{
			sortedSeatSet = new TreeSet<Seat>(new SeatComparator());
			sortedSeatSet.add(seat);
			seatsMap.put(startDateYYYYMMDD, sortedSeatSet);
		}		
		return seatsMap;
	}

	@Override
	public AvailabilityStatus getAvailabilityStatus(Date startDate) {
		String startDateYYYYMMDD = DateHandler.convertDateToString(startDate);
		return getAvailabilityStatusForDate(startDateYYYYMMDD);
	}

	@Override
	public AvailabilityStatus getAvailabilityStatus(String startDate){
		return getAvailabilityStatusForDate(startDate);
	}

	private AvailabilityStatus getAvailabilityStatusForDate(String startDateYYYYMMDD){

		AvailabilityStatus availabilityStatus=null;
		SortedSet<Seat> sortedSeatSet = seatsMap.get(startDateYYYYMMDD);
		if(sortedSeatSet!=null && !sortedSeatSet.isEmpty()){
			Iterator<Seat> seatIterator = sortedSeatSet.iterator();
			if(seatIterator.hasNext()){
				return seatIterator.next().getAvailabilityStatus();
			}
		}else{
			List<NonAvailabilityReason> nonAvailabilityReasons = new ArrayList<NonAvailabilityReason>();
			nonAvailabilityReasons.add(NonAvailabilityReason.SEAT_NOT_AVAILABLE);
			availabilityStatus = new AvailabilityStatusImpl();
			availabilityStatus.setNonAvailabilityReasons(nonAvailabilityReasons);
		}
		return availabilityStatus;
	}

	@Override
	public Seat getSeatById(int id){

		if(!seatsMap.isEmpty()){

			Set<Entry<String,SortedSet<Seat>>> seatEntrySet = seatsMap.entrySet();
			SortedSet<Seat> seats=null;
			Iterator<Seat> seatsIterator=null;
			Seat seat=null;
			for(Entry<String,SortedSet<Seat>> seatEntry: seatEntrySet){
				seats =seatEntry.getValue();
				seatsIterator = seats.iterator();
				while(seatsIterator.hasNext()){

					seat = seatsIterator.next();
					if(id == seat.getId()){
						return seat;
					}
				}
			}
		}

		return null;
	}
	
	@Override
	public int getTestDatesCount(){
		return seatsMap.size();
	}

}
