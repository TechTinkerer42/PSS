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
 * Created on Apr 12, 2012
 */
package org.ets.ereg.domain.interfaces.scheduling.response;

import java.util.Calendar;

public interface HeldSeat extends Seat {

	String getHoldCode();
	void setHoldCode(String holdCode);

	String getHoldSource();
	void setHoldSource(String holdSource);
	
	Calendar getHoldExpiration();
	void setHoldExpiration(Calendar holdExpiration);
	
	String getHoldDuration();
	void setHoldDuration(String holdDuration);	
	
	String getEtsAppointmentId();
	void setEtsAppointmentId(String etsAppointmentId);
}

