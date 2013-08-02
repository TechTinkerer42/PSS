package org.ets.ereg.scheduling.booking.dao;

import java.util.Date;
import java.util.List;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.Test;

public interface BookingDao extends Dao<Booking>{

	List<Booking> getFutureBookingByProduct(String productId, Long customerId);

	List<Form> getAvailableForms(Long customerId, Long testId, Date suppliedDate);

	List<Form> getAvailableTestFormsByTestId(Long testId);
	List<Test> getAvailableTest(Long customerId, String programCode);
	List<Test> getAllTest(String programCode);
	Form getSubForm(Long formID, Long testId,String langCode, String delvryMode);
	List<Form> getSubForms(Long parentFormId, Long testId, String langCode, String delvryMode, List<String> accommodations);
	public Test getAllTestById(String programCode, Long testId);
	public List<Form> getAllFormById(Long formID);
	public Booking getBookingById(Long id);
	public boolean checkAppointmentNumber(String appointmentNumber);
	public List<Booking> getFutureBookingByProduct(String productCode, Long customerId, SearchParameters searchParams );

    List<Booking> getBookingByCustomerId(Long customerId, String productCode,
            int maxResult);

    List<Booking> getBookingsWithAccommodation(Long customerIdLong,String accommodationTypeCode,Date fromDate,Date toDate);
    TestTakerTest getTestTakerTestByBookingId(Long bookingId);
    boolean updateBookingStatus(Long bookingId,String bookingStatus);

	List<Test> getHeldTest(Long customerId, String programCode);
	
	Long getRescheduleCountForBooking(Booking booking);

	Boolean isEligibleForReschedule(Booking booking);
}
