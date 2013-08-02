package org.ets.ereg.session.facade.scheduling.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.util.ERegMessageResourceUtil;
import org.ets.ereg.common.vo.FindSeatInfo;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.exception.SubFormNotFoundException;
import org.ets.ereg.scheduling.exception.SubFormSelectionNeededException;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.scheduling.vo.TestVO;
import org.springframework.web.servlet.support.RequestContext;

public interface BookingBusinessService {

	List<TestVO> getAvailableTests(Long testTakerId, String programCode);

	List<Form> getAvailableTestForms(Long testTakerId, Long testId, Date testDate);

	List<Form> getAvailableTestFormsByTestCode(Long testId);

	List<Booking> getFutureBookingsForCustomer(Long testTakerId, String programCode);

	List<Booking> getFutureBookingsForCustomer(Long testTakerId, String programCode,SearchParameters searchParams);

	List<TestTakerTest> saveTestTakerTests(List<TestTakerTest> testTakerTests);

	void sendAppointmentScheduledMail(List<Booking> bookings, ProfileVO profile);

	void sendAppointmentRescheduledMail(Booking oldBooking, Booking newBooking, ProfileVO profile);

	void sendAppointmentCanceledMail(Booking booking, ProfileVO profile);

	TestTakerTest updateTestTakerTest(TestTakerTest testTakerTest);

	Form getSubForm(Long parentFormID, Long testId, String langCode, String delvryMode) throws SubFormNotFoundException;

	List<Form> getSubForms(Long parentFormID, Long testId, String langCode, String delvryMode, List<String> accommodations) throws SubFormNotFoundException;

	boolean processAppointments(List<AppointmentVO> appointments, List<Integer> indices);

	List<Booking> saveAppointments(List<AppointmentVO> appointments, ProfileVO profile);

	Boolean processEditedAppointment(AppointmentVO appointment) throws SubFormSelectionNeededException;

	Booking updateAppointment(List<AppointmentVO> appointments, ProfileVO profile);

	Test getTestById(String programCode, Long testId);

	Form getFormById(Long formId);

	Booking getBookingById(Long id);

	TestCenter getTestCenterById(Long id);

	List<Agency> getAllAgencies(boolean includeActiveOnly);

	Agency getAgencyById(Long agencyId);

    String getAppointmentNumber(String string);

    AppointmentVO cloneAppointment(Booking oldBooking);

    List<Test> getAllTests(String programCode);

    List<Booking> getFutureBookingsForCustomer(Long customerIdLong,
            String programCode, int maxResult);

    Set<BookingAccommodation> convertToBookingAccomodations(Booking booking,
			List<CustomerAccommodationTest> customerAccommodationTestList);

	void cancelAppointment(Booking booking);

	boolean areEqual(Collection<BookingAccommodation> bookingAccommodations, Collection<CustomerAccommodationTest> customerAccommodationTests);

    Map<String, FindSeatInfo> findSeatForSpecificDates(
            FindSeatRequest findSeatrequest, FindSeatResponse findSeatResponse, RequestContext requestContext, ERegMessageResourceUtil eregMessageResource);

	List<DeliveryModeType> getDeliveryModesByTestCenterTest(Long testCenterId, Long testId);

	List<LanguageType> getLanguageTypesByTest(Long testId);
    FindSeatResponse getFindSeatResponse(TCFindSeatRequest request);

    TCFindSeatRequest createFindSeatRequest(Long customerId, Long testId,
            String testStartDate, String testEndDate, String programCode,
            String deliveryMode, Long testCenterId, String languageCode, boolean isCSR);

	Order getCartForCustomer(Customer customer);
	
	Order getNamedCartForCustomer(String cartName, Customer customer);

	List<Order> addTests(List<AppointmentVO> appointments, Customer customer) throws AddToCartException;

	List<Order> addTestsToNamedCart(String cartName, List<AppointmentVO> appointments, Customer customer) throws AddToCartException;

    HeldSeat holdSeat(Long customerId, Seat seat);

    void saveHeldBooking(List<AppointmentVO> appointments, Long customerId,
            int bookingIndex, HeldSeat heldSeat);

    //ReleaseSeatResponse releaseHeldBooking(Long customerId, HeldSeat heldSeat);

    ReleaseSeatResponse releaseHeldBooking(Long customerId,
            HeldBooking oldHeldBooking);

	String getTestCenterAddress(TestCenter testCenter);

	void getActiveAccommodations(Booking booking, Long customerId,
			Long testId, String deliveryMode, String testDate);

	Boolean isAccommodationsChanged(Booking booking);

	List<TestCenter> getTestCentersByAgency(Long agencyId);
	
	Booking saveBooking(Booking booking);

}