package org.ets.ereg.domain.interfaces.model.scheduling;

import java.io.Serializable;
import java.util.Set;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.test.TestStatusType;

public interface TestTakerTest extends Serializable {
	
	void setTestTakerTestId(Long TTTid);
	Long getTestTakerTestId();
	
	void setCustomer(Customer customer);
	Customer getCustomer();
	
	void setTestStatusCode(TestStatusType testStatusCode);
	TestStatusType getTestStatusCode();
	
	void setTTScoreStatusTypeCode(String ttScoreStatusTypeCode);
	String getTTScoreStatusTypeCode();
	
	void setTTScoreStatusReasonCode(String ttScoreStatusReasonCode);
	String getTTScoreStatusReasonCode();
	
	void setBookings(Set<Booking> bookings);
	Set<Booking> getBookings();
}
