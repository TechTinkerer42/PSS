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
 * Created on May 15, 2012
 */
package org.ets.ereg.domain.interfaces.scheduling.request;

import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

/**
 *  
 * 
 * @author ZABDUL
 * 
 *  History
 *  -------
 *  May 15, 2012 ZAM Initial version
 */

public interface ReserveSeatWithoutHoldRequest extends SchedulingRequest {
	Seat getSeat();
	void setSeat(Seat seat);
}

