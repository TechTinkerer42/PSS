package org.ets.ereg.scheduling.booking.service;

import java.util.Date;
import java.util.List;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.scheduling.exception.SubFormNotFoundException;
import org.ets.ereg.scheduling.exception.SubFormSelectionNeededException;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.scheduling.vo.TestVO;

public interface BookingService {

	List<TestVO> getAvailableTests(Long testTakerId, String programCode);

	List<Form> getAvailableTestFormsByTestId(Long testId);

	List<Form> getAvailableTestForms(Long testTakerId, Long testId, Date testDate);

	Form getSubForm(Long parentFormID, Long testId, String langCode, String delvryMode) throws SubFormNotFoundException;

	List<Form> getSubForms(Long parentFormID, Long testId, String langCode, String delvryMode, List<String> accommodations) throws SubFormNotFoundException;

	AppointmentVO processAppointment(AppointmentVO appointment) throws SubFormNotFoundException, SubFormSelectionNeededException;

	AppointmentVO cloneAppointment(Booking oldBooking);

	List<Booking> getFutureBookingsByCustomerId(Long testTakerId, String programCode);
	List<Booking> getFutureBookingsByCustomerId(Long testTakerId, String programCode,SearchParameters searchParams);

	TestTakerTest saveTestTakerTest(TestTakerTest testTakerTest);

	Test getTestById(String programCode, Long testId);

	Form getFormById(Long formId);

	Booking getBookingById(Long id);

    String getAppointmentNumber(String testType);

    List<Test> getAllTests(String programCode);

    Booking saveBooking(Booking booking);

    BookingAccommodation saveBookingAccommodation(BookingAccommodation bookingAccommodation);

	List<Form> getAvailTestFormsIncCurrent(Long bookingId);

    List<Booking> getFutureBookingsByCustomerId(Long customerIdLong,
            String productCode, int maxResult);

    List<Booking> getBookingsWithAccommodation(Long customerIdLong,String accommodationTypeCode,Date fromDate,Date toDate);

    List<Booking> saveAppointments(List<AppointmentVO> appointments,
            ETSCustomer customer);

    Booking updateAppointment(List<AppointmentVO> appointments,
            ETSCustomer customer);

    void saveBookingWithNewName(ETSCustomer customer);

	TestTakerTest prepareTestTakerTest(Booking booking, Customer customer);

	void prepareBooking(Booking booking, Customer customer);



}
