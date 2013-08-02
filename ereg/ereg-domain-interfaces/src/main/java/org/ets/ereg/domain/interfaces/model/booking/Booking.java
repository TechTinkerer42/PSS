package org.ets.ereg.domain.interfaces.model.booking;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;

public interface Booking extends Serializable {

	void setId(Long id);
	Long getId();

	void setTestTakerTestId (TestTakerTest tttId);
	TestTakerTest getTestTakerTestId ();

    TestVariation getTestVariation();
    void setTestVariation(TestVariation testVariation);

	void setForm(Form form);
	Form getForm();

	void setAppointmentDateTime(Date apptDate);
	Date getAppointmentDateTime();

	void setExtrnlAppointmentID(String extrnlApptID);
	String getExtrnlAppointmentID();

	void setBookingStatusType(BookingStatusType bookingStatusType);
	BookingStatusType getBookingStatusType();

	void setTestCenter(TestCenter testCenter);
	TestCenter getTestCenter();

	void setEvntOutComeStatus(EventOutComeStatus evntOutComeStatus);
	EventOutComeStatus getEvntOutComeStatus();

	void setCustFirstName(String firstName);
	String getCustFirstName();

	void setCustMidtname(String midName);
	String getCustMidName();

	void setCustLastName(String lastName);
	String getCustLastName();

	void setCustDOB(Date DOB);
	Date getCustDOB();

	void setTstLnchTime(Date tstLnchTime);
	Date getTstLnchTime();

	void setTstPackageId(String tstPackageId);
	String getTstPackageId();

	void setDuration(Long duration);
	Long getDuration();

	void setComments(String strComments);
	String getComments();
    String getEtsApptID();
    void setEtsApptID(String etsApptID);

    Set<BookingAccommodation> getBookingAccommodations();
    void setBookingAccommodations(
            Set<BookingAccommodation> bookingAccommodations);
    String getRegistrationSystemId();
    void setRegistrationSystemId(String registrationSystemId);

	HeldBooking getHeldBooking();
	void setHeldBooking(HeldBooking heldBooking);

	String getAppointmentDateString();
	void setAppointmentDateString(String appointmentDateString);
	
	Float getTimeZoneOffsetQuantity();
	void setTimeZoneOffsetQuantity(Float timeZoneOffsetQuantity);
	
	//TODO Uncomment when implemented correctly
	/*
	TestDiscreteOrderItem getTestDiscreteOrderItem();
	void setTestDiscreteOrderItem(TestDiscreteOrderItem testDiscreteOrderItem);
*/

}
