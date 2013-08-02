package org.ets.ereg.session.facade.scheduling.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.order.domain.NullOrderImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.dto.AddTestRequestDTO;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.service.TestVariationService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.business.util.RandomUtil;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.common.util.DateTimeUtil;
import org.ets.ereg.common.util.ERegMessageResourceUtil;
import org.ets.ereg.common.vo.FindSeatInfo;
import org.ets.ereg.domain.booking.BookingAccommodationImpl;
import org.ets.ereg.domain.booking.HeldBookingImpl;
import org.ets.ereg.domain.interfaces.commerce.order.AddTestRequest;
import org.ets.ereg.domain.interfaces.commerce.order.ETSTestItemRequest;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.booking.EventOutComeStatus;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.booking.id.BookingAccommodationId;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterDeliveryMode;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.model.test.TestStatusType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.AvailabilityStatus;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.NonAvailabilityReason;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.profile.accommodation.service.CustomerAccommodationService;
import org.ets.ereg.profile.service.ProfileService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.booking.service.BookingService;
import org.ets.ereg.scheduling.exception.SubFormNotFoundException;
import org.ets.ereg.scheduling.exception.SubFormSelectionNeededException;
import org.ets.ereg.scheduling.request.HoldSeatRequestImpl;
import org.ets.ereg.scheduling.request.ReleaseSeatRequestImpl;
import org.ets.ereg.scheduling.request.TCFindSeatRequestImpl;
import org.ets.ereg.scheduling.response.HeldSeatImpl;
import org.ets.ereg.scheduling.service.AgencyService;
import org.ets.ereg.scheduling.service.TestCenterService;
import org.ets.ereg.scheduling.util.BookingStatusEnum;
import org.ets.ereg.scheduling.util.TestStatusEnum;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.scheduling.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.RequestContext;

@Service("bookingBusinessService")
public class BookingBusinessServiceImpl implements BookingBusinessService {

    private static Logger log = LoggerFactory.getLogger(BookingBusinessServiceImpl.class);
    private static final String DATEFORMAT_HHMMSS = "HH:mm:ss";
    private static final String DATEFORMAT_MMDDYYY = "MM/dd/yyyy";

    @Resource(name = "bookingService")
    private BookingService bookingService;

    @Resource(name = "testCenterService")
    private TestCenterService testCenterService;

    @Resource(name = "testVariationService")
    private TestVariationService testVariationService;

    @Resource(name = "agencyService")
    private AgencyService agencyService;

    @Resource(name = "referenceEntityService")
    private ReferenceService referenceService;

    @Resource(name="blEmailService")
    protected EmailService emailService;

    @Resource(name="blAppointmentScheduledEmailInfo")
    protected EmailInfo appointmentScheduledEmailInfo;

    @Resource(name="blAppointmentRescheduledEmailInfo")
    protected EmailInfo appointmentRescheduledEmailInfo;

    @Resource(name="blAppointmentCanceledEmailInfo")
    protected EmailInfo appointmentCanceledEmailInfo;


    @Resource(name="customerAccommodationService")
    protected CustomerAccommodationService customerAccommodationService;

    @Resource(name="schedulingService")
    private SchedulingService schedulingService;

    @Resource(name="profileService")
    private ProfileService profileService;

    @Resource(name = "etsOrderService")
    private ETSOrderService etsOrderService;

    @Resource(name = "blCatalogService")
    private CatalogService catalogService;

    @Resource(name = "blFulfillmentGroupService")
    private FulfillmentGroupService fulfillmentGroupService;

    @Override
    public List<TestVO> getAvailableTests(Long testTakerId, String programCode) {
        return bookingService.getAvailableTests(testTakerId, programCode);
    }

    @Override
    public List<Form> getAvailableTestForms(Long testTakerId, Long testId, Date testDate) {
        return bookingService.getAvailableTestForms(testTakerId, testId, testDate);
    }

    @Override
    public List<Form> getAvailableTestFormsByTestCode(Long testId) {
        return bookingService.getAvailableTestFormsByTestId(testId);
    }

    @Override
    public List<Booking> getFutureBookingsForCustomer(Long testTakerId, String programCode) {
        return bookingService.getFutureBookingsByCustomerId(testTakerId, programCode);
    }

    @Override
    public List<Booking> getFutureBookingsForCustomer(Long testTakerId, String programCode,SearchParameters searchParams){
        return bookingService.getFutureBookingsByCustomerId(testTakerId, programCode,searchParams);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public List<TestTakerTest> saveTestTakerTests(List<TestTakerTest> testTakerTests) {
        List<TestTakerTest> persistedTestTakerTests = new ArrayList<TestTakerTest>();
        for (TestTakerTest testTakerTest : testTakerTests) {
            persistedTestTakerTests.add(bookingService.saveTestTakerTest(testTakerTest));
        }
        return persistedTestTakerTests;
    }

    @Override
    public void sendAppointmentScheduledMail(List<Booking> bookings, ProfileVO profile) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("bookings", bookings);
        props.put("customer", profile.getCustomer());

        emailService.sendTemplateEmail(profile.getCustomer().getEmailAddress(), appointmentScheduledEmailInfo, props);
    }

    @Override
    public void sendAppointmentRescheduledMail(Booking oldBooking, Booking newBooking, ProfileVO profile) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("oldBooking", oldBooking);
        props.put("newBooking", newBooking);
        props.put("customer", profile.getCustomer());

        emailService.sendTemplateEmail(profile.getCustomer().getEmailAddress(), appointmentRescheduledEmailInfo, props);
    }

    @Override
    public void sendAppointmentCanceledMail(Booking booking, ProfileVO profile) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("booking", booking);
        props.put("customer", profile.getCustomer());

        emailService.sendTemplateEmail(profile.getCustomer().getEmailAddress(), appointmentCanceledEmailInfo, props);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public TestTakerTest updateTestTakerTest(TestTakerTest testTakerTest) {
        return bookingService.saveTestTakerTest(testTakerTest);
    }

    @Override
    public Form getSubForm(Long parentFormID, Long testId, String langCode, String delvryMode) throws SubFormNotFoundException {
        return bookingService.getSubForm(parentFormID, testId, langCode, delvryMode);
    }

    @Override
    public Test getTestById(String programCode, Long testId) {
        return bookingService.getTestById(programCode, testId);
    }

    @Override
    public Form getFormById(Long formId) {
        return bookingService.getFormById(formId);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingService.getBookingById(id);
    }

    @Override
    public TestCenter getTestCenterById(Long id) {
        return testCenterService.readTestCenterById(id);
    }

    @Override
    public List<Agency> getAllAgencies(boolean includeActiveOnly) {
        return agencyService.getAllAgencies(includeActiveOnly);
    }

    @Override
    public Agency getAgencyById(Long agencyId) {
        return agencyService.getAgencyById(agencyId);
    }

    @Override
    public String getAppointmentNumber(String dlvyMode) {
        return bookingService.getAppointmentNumber(dlvyMode);
    }



    @Override
    public boolean processAppointments(List<AppointmentVO> appointments, List<Integer> indices) {
        boolean isSubFormSelectionNeeded = false;
        for (int i = 0; i < appointments.size(); i++) {
            AppointmentVO appointment = appointments.get(i);
            appointment.getBooking().setBookingStatusType(
                    referenceService.getEntityByPrimaryKey(
                            BookingStatusType.class,
                            BookingStatusEnum.RESERVED.toString()));
            try {
                EventOutComeStatus evntOutComeStatus=referenceService.getEntityByPrimaryKey(EventOutComeStatus.class, EventOutComeStatus.NOT_CHECKED_IN);
                appointment.getBooking().setEvntOutComeStatus(evntOutComeStatus);
                bookingService.processAppointment(appointment);
            } catch (SubFormNotFoundException e) {
                indices.add(i);
            } catch (SubFormSelectionNeededException e) {
                isSubFormSelectionNeeded = true;
                appointment.setSubForms(e.getSubForms());
            }
        }
        return isSubFormSelectionNeeded;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public List<Booking> saveAppointments(List<AppointmentVO> appointments, ProfileVO profile) {

        return bookingService.saveAppointments(appointments, profile.getCustomer());
    }

    @Override
    public Set<BookingAccommodation> convertToBookingAccomodations(Booking booking,
            List<CustomerAccommodationTest> customerAccommodationTestList) {
        Set<BookingAccommodation> bookingAccommodations = new HashSet<BookingAccommodation>(customerAccommodationTestList.size());
        for (CustomerAccommodationTest customerAccommodationTest : customerAccommodationTestList) {
            BookingAccommodation bookingAccommodation = new BookingAccommodationImpl();
            bookingAccommodation.setAccommodationType(customerAccommodationTest.getProgramAccommodationDeliveryMode().getAccommodationType());
            bookingAccommodation.setAccommodationTypeValue(customerAccommodationTest.getAccommodationTypeValue());
            bookingAccommodation.setApprovedDate(customerAccommodationTest.getApprovedDate());
            bookingAccommodation.setExpirationDate(customerAccommodationTest.getExpiryDate());
            bookingAccommodation.setOthrAcmdtnTypTxt(customerAccommodationTest.getOtherAccommodationTypeText());
            BookingAccommodationId bookingAccommodationId = new BookingAccommodationId();
            if(booking.getId() != null) {
                bookingAccommodationId.setBookingId(booking.getId());
                bookingAccommodation.setBooking(booking);
            }
            bookingAccommodationId.setAccommodationTypeCode(customerAccommodationTest.getProgramAccommodationDeliveryMode().getAccommodationType().getCode());
            bookingAccommodation.setBookingAccommodationId(bookingAccommodationId);
            bookingAccommodations.add(bookingAccommodation);
        }
        return bookingAccommodations;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean areEqual(Collection<BookingAccommodation> bookingAccommodations, Collection<CustomerAccommodationTest> customerAccommodationTests){

        Boolean accommodationsChanged = new Boolean(false);

        if(customerAccommodationTests.size()  == bookingAccommodations.size()){

            for(BookingAccommodation bookingAccommodation : bookingAccommodations){
                boolean baExistInActiveAccommodations = false;
                for(CustomerAccommodationTest activeAccommodation : customerAccommodationTests){

                    if(bookingAccommodation.getAccommodationType().getCode().equals(activeAccommodation.getAccommodationType().getCode())){
                        baExistInActiveAccommodations = true;
                        break;
                    }
                }
                if(baExistInActiveAccommodations == false){
                    accommodationsChanged = true;
                    break;
                }
            }
        }else{
            accommodationsChanged = true;
        }

        return accommodationsChanged;

    }


    @Override
    public Boolean processEditedAppointment(AppointmentVO appointment) throws SubFormSelectionNeededException {
        Boolean processSucceded = true;

        try {
            bookingService.processAppointment(appointment);
        } catch (SubFormNotFoundException e) {
            processSucceded = false;
        }

        return processSucceded;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public Booking updateAppointment(List<AppointmentVO> appointments, ProfileVO profile) {

        return bookingService.updateAppointment(appointments, profile.getCustomer());
    }

    @Override
    public AppointmentVO cloneAppointment(Booking oldBooking) {
        return bookingService.cloneAppointment(oldBooking);
    }

    @Override
    public List<Form> getSubForms(Long parentFormID, Long testId,
            String langCode, String delvryMode,
            List<String> accommodations)
            throws SubFormNotFoundException {
        return bookingService.getSubForms(parentFormID, testId, langCode, delvryMode, accommodations);
    }

    @Override
    public List<Test> getAllTests(String programCode) {
        return bookingService.getAllTests(programCode);
    }

    @Override
    public List<Booking> getFutureBookingsForCustomer(Long customerIdLong,
            String productCode, int maxResult) {
        return bookingService.getFutureBookingsByCustomerId(customerIdLong, productCode, maxResult);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void cancelAppointment(Booking booking) {
        booking.setBookingStatusType(referenceService.getEntityByPrimaryKey(
                BookingStatusType.class, BookingStatusEnum.CANCELED.toString()));
        TestTakerTest testTakerTest = booking.getTestTakerTestId();
        testTakerTest.setTestStatusCode(referenceService.getEntityByPrimaryKey(
                TestStatusType.class, TestStatusEnum.CANCELED.toString()));
        bookingService.saveTestTakerTest(testTakerTest);
    }

    @Override
    public Map<String, FindSeatInfo> findSeatForSpecificDates(FindSeatRequest findSeatRequest, FindSeatResponse findSeatResponse, RequestContext requestContext, ERegMessageResourceUtil eregMessageResource) {
        return convertResponseToMap(findSeatRequest,findSeatResponse, requestContext, eregMessageResource);

    }

    private Map<String, FindSeatInfo> convertResponseToMap(FindSeatRequest request, FindSeatResponse response, RequestContext requestContext, ERegMessageResourceUtil eregMessageResource) {
        Map<String, FindSeatInfo> mapResponse = new TreeMap<String, FindSeatInfo>();
        FindSeatInfo seatInfo = null;
        SortedSet<Seat> sortedSet = null;
        SortedMap<String, Integer> sortedTime = null;
        Calendar calSeat = null;
        String dateString = null;
        DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT_HHMMSS);
        for (Calendar testDate = request.getSearchFromDate(); !testDate.after(request.getSearchToDate()); testDate.add(Calendar.DATE, 1)) {
            seatInfo = new FindSeatInfo();
            AvailabilityStatus availabilityStatus = response.getAvailabilityStatus(testDate.getTime());
            if(availabilityStatus != null && availabilityStatus.isAvailable()){
                sortedSet = response.getSeats(testDate.getTime());
                if(sortedSet == null){
                    seatInfo.setNonAvailabilityText(eregMessageResource.getMessage(NonAvailabilityReason.SEAT_NOT_AVAILABLE.getI18nCode(), requestContext.getLocale()));
                } else {
                    sortedTime = new TreeMap<String, Integer>();
                    for (Seat seat : sortedSet) {
                        calSeat = seat.getLocalStartDateTime();
                        dateString = dateFormat.format(calSeat.getTime());
                        sortedTime.put(dateString, seat.getId());
                    }
                    seatInfo.setTimeMap(sortedTime);
                }
            } else if (availabilityStatus != null && availabilityStatus.getNonAvailabilityReason()!=null){
                seatInfo.setNonAvailabilityText(eregMessageResource.getMessage(availabilityStatus.getNonAvailabilityReason().getI18nCode(), requestContext.getLocale()));
            }

            mapResponse.put(DateHandler.convertDateToString(testDate.getTime()), seatInfo);
        }
        return mapResponse;
    }

    @Override
    public FindSeatResponse getFindSeatResponse(TCFindSeatRequest request) {
        FindSeatResponse response =schedulingService.findSeat(request);
        if(response == null || !response.isSuccessful()){
            response = null;
        }
        return response;
    }

    @Override
    public List<LanguageType> getLanguageTypesByTest(Long testId) {
        return testVariationService.getAllLanguageTypesForTest(ProgramContextHolder.getProgramCode(), testId);
    }

    @Override
    public List<DeliveryModeType> getDeliveryModesByTestCenterTest(Long testCenterId, Long testId) {
        List<DeliveryModeType> availableDeliveryModes = new ArrayList<DeliveryModeType>();

        List<DeliveryModeType> testDeliveryModes =
                testVariationService.getAllDeliveryModeTypesForTest(ProgramContextHolder.getProgramCode(), testId);
        Set<TestCenterDeliveryMode> testCenterDeliveryModes =
                testCenterService.readTestCenterById(testCenterId).getTestCenterDeliveryModes();

        for (TestCenterDeliveryMode tcdm : testCenterDeliveryModes) {
            if (testDeliveryModes.contains(tcdm.getDeliveryMode())) {
                availableDeliveryModes.add(tcdm.getDeliveryMode());
            }
        }

        Collections.sort(availableDeliveryModes, new Comparator<DeliveryModeType>() {
            @Override
            public int compare(DeliveryModeType o1, DeliveryModeType o2) {
                return o1.getDisplaySequence().compareTo(o2.getDisplaySequence());
            }
        });

        return availableDeliveryModes;
    }

    @Override
    public Order getCartForCustomer(Customer customer) {
        Order cart = etsOrderService.findCartForCustomer(customer);

        if (cart == null || cart instanceof NullOrderImpl) {
            cart = etsOrderService.createNewCartForCustomer(customer);
        }

        return cart;
    }


    @Override
    public Order getNamedCartForCustomer(String cartName, Customer customer) {

        Order wishlist = etsOrderService.findNamedOrderForCustomer(cartName, customer);

        if (wishlist == null) {
            wishlist = etsOrderService.createNamedOrderForCustomer(cartName, customer);
        }

        return wishlist;
    }


    @Override
    public List<Order> addTests(List<AppointmentVO> appointments, Customer customer) throws AddToCartException {
        List<AddTestRequest> addTestRequests = new ArrayList<AddTestRequest>();
        Order cart = getCartForCustomer(customer);
        Long oderId = cart.getId();
        for (AppointmentVO appointment : appointments) {
            AddTestRequest request = new AddTestRequestDTO();
            request.setOrderId(oderId);
            // TODO set the actual price order
            request.setPriceOrder(true);

            Booking booking = appointment.getBooking();

            String deliveryModeTypeCode = booking.getTestVariation().getDeliveryModeType().getCode();
			Form form = null;
			if (deliveryModeTypeCode.equalsIgnoreCase(DeliveryModeType.PBT)) {
				List<Form> baseForms = getAvailableTestForms(customer.getId(),
						booking.getTestVariation().getTest().getTestId(), booking
								.getAppointmentDateTime());
				try {
					if( baseForms.size() > 0 ) {
						form = getSubForm(baseForms.get(RandomUtil.getRandomIntegerBetween(0, baseForms.size() - 1)).getFormID(),
								booking.getTestVariation().getTest().getTestId(),
								booking.getTestVariation().getLanguageType().getCode(),
								booking.getTestVariation().getDeliveryModeType().getCode());
					}
				} catch (SubFormNotFoundException e) {
					log.error("sub form not found: {}", e.toString());
				}
			}
            booking.setForm(form);

            booking.setEtsApptID(bookingService.getAppointmentNumber(booking.getTestVariation().getDeliveryModeType().getCode()));
            booking.setEvntOutComeStatus(referenceService.getEntityByPrimaryKey(EventOutComeStatus.class, EventOutComeStatus.NOT_CHECKED_IN));
            booking.setBookingStatusType(referenceService.getEntityByPrimaryKey(BookingStatusType.class, BookingStatusType.HELD));

            bookingService.prepareTestTakerTest(booking, customer);
            bookingService.prepareBooking(booking, customer);

            ETSTestItemRequest testItemRequest = new ETSTestItemRequest();
            testItemRequest.setBooking(booking);
            testItemRequest.setQuantity(1);
            List<Product> products = catalogService.findProductsByName(booking.getTestVariation().getTest().getTestName());
            if (!products.isEmpty()) {
                Sku defaultSku = products.get(0).getDefaultSku();
                testItemRequest.setSkuId(defaultSku.getId());
                testItemRequest.setProductId(defaultSku.getDefaultProduct().getId());
            }
            testItemRequest.getItemAttributes().put("TEST_TYPE", booking.getTestVariation().getDeliveryModeType().getDescription());

            request.setTestItemRequest(testItemRequest);
            addTestRequests.add(request);
        }
        return etsOrderService.bulkAddTest(addTestRequests);
    }


    //Move Common logic of addTestsToNamedCart and addTests to new function
    @Override
    public List<Order> addTestsToNamedCart(String cartName, List<AppointmentVO> appointments, Customer customer) throws AddToCartException {
        List<AddTestRequest> addTestRequests = new ArrayList<AddTestRequest>();
        Order cart = getNamedCartForCustomer(cartName, customer);
        Long oderId = cart.getId();
        for (AppointmentVO appointment : appointments) {
            AddTestRequest request = new AddTestRequestDTO();
            request.setOrderId(oderId);
            // TODO set the actual price order
            request.setPriceOrder(true);

            Booking booking = appointment.getBooking();

            booking.setForm(null);
            booking.setEtsApptID(bookingService.getAppointmentNumber(booking.getTestVariation().getDeliveryModeType().getCode()));
            booking.setEvntOutComeStatus(referenceService.getEntityByPrimaryKey(EventOutComeStatus.class, EventOutComeStatus.NOT_CHECKED_IN));
            booking.setBookingStatusType(referenceService.getEntityByPrimaryKey(BookingStatusType.class, BookingStatusType.HELD));

            bookingService.prepareTestTakerTest(booking, customer);
            bookingService.prepareBooking(booking, customer);

            ETSTestItemRequest testItemRequest = new ETSTestItemRequest();
            testItemRequest.setBooking(booking);
            testItemRequest.setQuantity(1);
            List<Product> products = catalogService.findProductsByName(booking.getTestVariation().getTest().getTestName());
            if (!products.isEmpty()) {
                Sku defaultSku = products.get(0).getDefaultSku();
                testItemRequest.setSkuId(defaultSku.getId());
                testItemRequest.setProductId(defaultSku.getDefaultProduct().getId());
            }
            testItemRequest.getItemAttributes().put("TEST_TYPE", booking.getTestVariation().getDeliveryModeType().getDescription());

            request.setTestItemRequest(testItemRequest);
            addTestRequests.add(request);
        }
        return etsOrderService.bulkAddTest(addTestRequests);
    }

    @Override
    public TCFindSeatRequest createFindSeatRequest(Long customerId, Long testId, String testStartDate, String testEndDate, String programCode, String deliveryMode, Long testCenterId, String languageCode, boolean isCSR) {
        TCFindSeatRequest findSeatRequest = new TCFindSeatRequestImpl();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Date startDate = null, endDate = null;
        DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT_MMDDYYY);
        TestCenter testCenter = testCenterService.readTestCenterById(testCenterId);
        try {
            startDate = dateFormat.parse(testStartDate);
            endDate = dateFormat.parse(testEndDate);
            if(endDate.before(startDate)){
                return findSeatRequest;
            }
            startCal.setTimeZone(TimeZone.getTimeZone(testCenter.getGlobalTimeZone().getCode()));
            startCal.setTime(startDate);
            endCal.setTime(endDate);
            log.info("find seat starting date: {}, end date: {}.", startCal.getTime(), endCal.getTime());
        } catch (ParseException e) {
            log.error("Error while parsing string dates: start & end dates {} with error log: ", testStartDate + " & " + testEndDate, e.getMessage());
        }

        List<CustomerAccommodationTest> customerAccommodationTests = customerAccommodationService.getAllActiveAccommodationsByDeliveryMode(
				customerId, programCode, testId, deliveryMode, startCal.getTime());
        if (customerAccommodationTests.size() > 0 && isCSR) {
        	findSeatRequest.setHasAccommodations(true);
        	findSeatRequest.setCustomerAccommodations(customerAccommodationTests);
        } else {
        	findSeatRequest.setHasAccommodations(false);
        }
        findSeatRequest.setTestVariation(getTestVariation(testId, deliveryMode, languageCode, programCode));
        findSeatRequest.setDeliveryModeCode(deliveryMode);
        findSeatRequest.setApplyStatckingLogic(Boolean.TRUE);
        findSeatRequest.setSearchFromDate(startCal);
        findSeatRequest.setSearchToDate(endCal);
        findSeatRequest.setCustomer(getCustomer(customerId));
        findSeatRequest.setTestCenterId(testCenterId);
        return findSeatRequest;
    }

    private TestVariation getTestVariation(Long testId, String deliveryMode,
            String languageCode, String programCode) {
        return testVariationService.getTestVariationForTest(testId, deliveryMode, languageCode, programCode);
    }

    private ETSCustomer getCustomer(Long customerId) {
        ETSCustomer customer = profileService.readProfileById(customerId).getCustomer();
        return customer;

    }

    @Override
    public HeldSeat holdSeat(Long customerId, Seat seat) {
        HoldSeatRequest holdSeatRequest = createHoldSeatRequest(customerId, seat);
        HoldSeatResponse holdSeatResponse = schedulingService.holdSeat(holdSeatRequest);
        if(!holdSeatResponse.isSuccessful()){
            return null;
        }
        return holdSeatResponse.getHeldSeat();
    }

    private HoldSeatRequest createHoldSeatRequest(Long customerId, Seat seat) {
        HoldSeatRequest holdSeatRequest = new HoldSeatRequestImpl();
        holdSeatRequest.setSeat(seat);
        holdSeatRequest.setCustomer(getCustomer(customerId));
        return holdSeatRequest;
    }

    @Override
    public void saveHeldBooking(List<AppointmentVO> appointments, Long customerId, int bookingIndex, HeldSeat heldSeat){
    	  AppointmentVO appointment = appointments.get(bookingIndex);
          Booking booking = appointment.getBooking();
          booking.setAppointmentDateTime(heldSeat.getLocalStartDateTime().getTime());
          booking.setAppointmentDateString(heldSeat.getStrLocalStartTime());
          booking.setTimeZoneOffsetQuantity(DateTimeUtil.getTimeZoneOffset(
        		  heldSeat.getTestCenter().getGlobalTimeZone().getCode(), 
        		  heldSeat.getLocalStartDateTime().getTime()));
          booking.setTestCenter(heldSeat.getTestCenter());
          booking.setTestVariation(heldSeat.getTestVariation());
		  HeldBooking oldHeldBooking = booking.getHeldBooking();
          HeldBooking heldBooking = new HeldBookingImpl();
          heldBooking.setHoldSeatId(heldSeat.getHoldCode());
          heldBooking.setBooking(booking);
          heldBooking.setHoldDuration(heldSeat.getHoldDuration());
          heldBooking.setHoldSourceDesc(heldSeat.getHoldSource());
          heldBooking.setLabCode(heldSeat.getLabCode());
          heldBooking.setSeatCode(heldSeat.getSeatCode());
          heldBooking.setSiteCode(heldSeat.getSiteCode());
          booking.setHeldBooking(heldBooking);
          if(oldHeldBooking != null){
              releaseHeldBooking(customerId, oldHeldBooking);
          }
    }

    @Override
    public ReleaseSeatResponse releaseHeldBooking(Long customerId, HeldBooking oldHeldBooking) {
        ReleaseSeatResponse releaseSeatResponse = null;
        ReleaseSeatRequest releaseSeatRequest = createReleaseSeatRequest(customerId, oldHeldBooking);
        releaseSeatResponse = schedulingService.releaseSeat(releaseSeatRequest);

        return releaseSeatResponse;

    }

    private ReleaseSeatRequest createReleaseSeatRequest(Long customerId, HeldBooking oldHeldBooking) {
        ReleaseSeatRequest releaseSeatRequest = new ReleaseSeatRequestImpl();
        releaseSeatRequest.setCustomer(getCustomer(customerId));
        HeldSeat heldSeat = new HeldSeatImpl();
        heldSeat.setHoldDuration(oldHeldBooking.getHoldDuration());
        heldSeat.setHoldCode(oldHeldBooking.getHoldSeatId());
        heldSeat.setHoldSource(oldHeldBooking.getHoldSourceDesc());
        heldSeat.setLabCode(oldHeldBooking.getLabCode());
        heldSeat.setSeatCode(oldHeldBooking.getSeatCode());
        heldSeat.setSiteCode(oldHeldBooking.getSiteCode());
        heldSeat.setTestVariation(oldHeldBooking.getBooking().getTestVariation());
        heldSeat.setTestCenter(oldHeldBooking.getBooking().getTestCenter());
        releaseSeatRequest.setHeldSeat(heldSeat);
        return releaseSeatRequest;
    }

    @Override
    public String getTestCenterAddress(TestCenter testCenter) {
    	return testCenterService.getAddress(testCenter.getFirstAddress());
    }

    @Override
    public void getActiveAccommodations(Booking booking, Long customerId, Long testId, String deliveryMode, String testDate) {
    	Date dteTestDate = null;
    	try {
			dteTestDate = new SimpleDateFormat(DATEFORMAT_MMDDYYY).parse(testDate.replaceAll("-", "/"));
		} catch (ParseException e) {
			log.error("Error when parsing test date for get approved accommodations: {}", e.toString());
		}
    	List<CustomerAccommodationTest> activeAccommodations =
    			customerAccommodationService.getAllActiveAccommodationsByDeliveryMode(
    					customerId, ProgramContextHolder.getProgramCode(), testId, deliveryMode, dteTestDate);
		booking.setBookingAccommodations(convertToBookingAccomodations(booking, activeAccommodations));
    }

    @Override
    public Boolean isAccommodationsChanged(Booking booking) {
		List<CustomerAccommodationTest> activeAccommodations = customerAccommodationService
				.getAllActiveAccommodationsByDeliveryMode(
						booking.getTestTakerTestId().getCustomer().getId(),
						booking.getTestVariation().getTest().getProgramType().getCode(),
						booking.getTestVariation().getTest().getTestId(),
						booking.getTestVariation().getDeliveryModeType().getCode(),
						booking.getAppointmentDateTime());

		Set<BookingAccommodation> bookingAccommodations = booking.getBookingAccommodations();

		return areEqual(bookingAccommodations, activeAccommodations);
	}

    @Override
    public List<TestCenter> getTestCentersByAgency(Long agencyId) {
    	return testCenterService.getTestCentersByAgency(agencyId);
    }
    
    @Override
    public Booking saveBooking(Booking booking){
    	return bookingService.saveBooking(booking);
    }
}