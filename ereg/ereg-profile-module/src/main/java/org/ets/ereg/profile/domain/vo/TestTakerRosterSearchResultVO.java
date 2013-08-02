/**
 * 
 */
package org.ets.ereg.profile.domain.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ets.ereg.common.business.annotation.SelectColumn;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;

public class TestTakerRosterSearchResultVO {
	
	
	@SelectColumn(order = 0)
	private BigDecimal customerId;
	@SelectColumn(order = 1)
	private String firstName;
	@SelectColumn(order = 2)
	private String lastName;
	@SelectColumn(order = 3)
	private Timestamp dateOfBirth;
	@SelectColumn(order = 4)
	private String phoneNumber;
	@SelectColumn(order = 5)
	private String emailAddress;
	@SelectColumn(order = 6)
	private String testName;
	@SelectColumn(order = 7)
	private BigDecimal bookingId;
	@SelectColumn(order = 8)
	private Timestamp appointmentDate;
	@SelectColumn(order = 9)
	private String appointmentNumber;
	@SelectColumn(order = 10)
	private String deliveryModeDesc;
	@SelectColumn(order = 11)
	private String formDesc;
	@SelectColumn(order = 12)
	private String eventOtcmSatusTypDesc;
	@SelectColumn(order = 13)
	private String accomodation;
	
	private List<String> listAccomodationDesc = new ArrayList<String>();
	
	private String encodedCustId;

	public String getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(String accomodation) {
		this.accomodation = accomodation;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public BigDecimal getBookingId() {
		return bookingId;
	}

	public void setBookingId(BigDecimal bookingId) {
		this.bookingId = bookingId;
	}

	public Timestamp getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Timestamp appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getDeliveryModeDesc() {
		return deliveryModeDesc;
	}

	public void setDeliveryModeDesc(String deliveryModeDesc) {
		this.deliveryModeDesc = deliveryModeDesc;
	}

	public String getFormDesc() {
		return formDesc;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public String getFormattedDateOfBirth() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MMM dd, yyyy");
		if (dateOfBirth != null) {
			return dateFormat.format(dateOfBirth);
		} else {
			return "";
		}
	}

	public String getFormattedAppointmentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MMM dd, yyyy, HH:ss aaa");
		if (appointmentDate != null) {
			return dateFormat.format(appointmentDate);
		} else {
			return "";
		}
	}

	public String getEventOtcmSatusTypDesc() {
		return eventOtcmSatusTypDesc;
	}

	public void setEventOtcmSatusTypDesc(String eventOtcmSatusTypDesc) {
		this.eventOtcmSatusTypDesc = eventOtcmSatusTypDesc;
	}

	public String getAppointmentNumber() {
		return appointmentNumber;
	}

	public String getEncodedCustId() {
		return encodedCustId;
	}

	public void setEncodedCustId(String encodedCustId) {
		this.encodedCustId = encodedCustId;
	}

	public void setAppointmentNumber(String appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}

	public List<String> getListAccomodationDesc() {
		return listAccomodationDesc;
	}

	
	
}
