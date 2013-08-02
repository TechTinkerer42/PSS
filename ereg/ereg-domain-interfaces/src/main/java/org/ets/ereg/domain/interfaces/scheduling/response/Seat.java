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

import java.util.Calendar;
import java.util.List;

import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;


public interface Seat {

	TestCenter getTestCenter();
	void setTestCenter(TestCenter testCenter);

	Calendar getLocalStartDateTime();
	void setLocalStartDateTime(Calendar localStartDateTime);

	Distance getApproxDistance();
	void setApproxDistance(Distance approxDistance);

	AvailabilityStatus getAvailabilityStatus();
	void setAvailabilityStatus(AvailabilityStatus availabilityStatus);

	String getDeliveryModeCode();
	void setDeliveryModeCode(String deliveryModeCode);

	int getId();

	String getSiteCode();
	void setSiteCode(String siteCode);

	String getLabCode();
	void setLabCode(String labCode);

	String getSeatCode();
	void setSeatCode(String seatCode);

	String getStrLocalStartTime();
	void setStrLocalStartTime(String strLocalStartTime);

    TestVariation getTestVariation();
    void setTestVariation(TestVariation testVariation);

    boolean isHasAccommodations();
	void setHasAccommodations(boolean hasAccommodations);

	List<CustomerAccommodationTest> getCustomerAccommodations();
	void setCustomerAccommodations(
			List<CustomerAccommodationTest> customerAccommodations);

	Short getDuration();
	void setDuration(Short duration);

	String getSchedulingTestCode();
	void setSchedulingTestCode(String schedulingTestCode);

}

