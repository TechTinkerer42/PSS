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
package org.ets.ereg.domain.interfaces.scheduling.request;

import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;


public interface CancelSeatRequest extends SchedulingRequest {

	ReservedSeat getReservedSeat();
	void setReservedSeat(ReservedSeat reservedSeat);
	
	String getCancelReasonCode();
	void setCancelReasonCode(String cancelReasonCode);

}

