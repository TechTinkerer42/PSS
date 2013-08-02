/*
 * --------------------------------------------------------------------------
 * Copyright 2012 Educational Testing Service. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF EDUCATIONAL TESTING SERVICE AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * PURPOSE OTHER THAN THAT WHICH IT IS SPECIFICALLY PROVIDED WITHOUT THE WRITTEN
 * PERMISSION OF THE SAID COMPANY
 * --------------------------------------------------------------------------
 * Created on Apr 10, 2012
 */
package org.ets.ereg.domain.interfaces.scheduling.response;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface FindSeatResponse extends SchedulingResponse {
	/**
	 *
	 * @return
	 * This method returns all the seats with availability status
	 */
	List<Seat> getSeats();

	/**
	 * takes the date and returns all the available seats sorted by start date
	 * @param startDate
	 * @return
	 */
	SortedSet<Seat> getSeats(Date startDate);

	/**
	 * takes the date as string in format yyyyMMdd and returns all the availables
	 * seats sorted by start date
	 * @param startDate
	 * @return
	 */
	SortedSet<Seat> getSeats(String startDate);

	/**
	 * This method takes Seat as parameter and adds to current set of Seats for the date, if exists.
	 * if no current set of seats present for the start date in the map then creates a new set of seats and
	 * adds to the map.
	 * @param seat
	 * @return
	 */
	Map<String,SortedSet<Seat>> addSeat(Seat seat);

	/**
	 * takes the date as parameter and returns seat availability status
	 * @param startDate
	 * @return
	 */
	AvailabilityStatus getAvailabilityStatus(Date startDate);

	/**
	 * takes the date as string in format yyyyMMdd and returns seat availability status
	 * @param startDate
	 * @return
	 */
	AvailabilityStatus getAvailabilityStatus(String startDate);

    Seat getSeatById(int id);
    
    /**
     * This method returns the count of different dates with seats available
     * @return
     */
    int getTestDatesCount();

}

