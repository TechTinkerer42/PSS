package org.ets.ereg.scheduling.booking.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.util.GenerateUniqueCode;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.util.DateTimeUtil;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.form.FormImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.booking.EventOutComeStatus;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.model.test.TestStatusType;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.scheduling.booking.dao.BookingAccommodationDao;
import org.ets.ereg.scheduling.booking.dao.BookingDao;
import org.ets.ereg.scheduling.booking.service.BookingService;
import org.ets.ereg.scheduling.exception.SubFormNotFoundException;
import org.ets.ereg.scheduling.exception.SubFormSelectionNeededException;
import org.ets.ereg.scheduling.testtakertest.dao.TestTakerTestDao;
import org.ets.ereg.scheduling.util.BookingStatusEnum;
import org.ets.ereg.scheduling.util.TestStatusEnum;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.scheduling.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	private static Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
	private static final int MAX_RESULT = 100;

	@Resource(name = "bookingDao")
	private BookingDao bookingDao;

	@Resource(name = "testTakerTestDao")
	private TestTakerTestDao testTakerTestDao;

	@Resource(name = "bookingAccommodationDao")
    private BookingAccommodationDao bookingAccommodationDao;

    @Resource(name="generateUniqueCode")
    private GenerateUniqueCode generateUniqueCode;

    @Resource(name = "referenceEntityService")
    private ReferenceService referenceService;
    
    @Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;

	@Override
	public List<TestVO> getAvailableTests(Long testTakerId, String programCode) {
		List<TestVO> tests = new ArrayList<TestVO>();
		List<Test> availableTests = bookingDao.getAvailableTest(testTakerId, programCode);
		List<Test> allTests = bookingDao.getAllTest(programCode);
		List<Test> heldTests = bookingDao.getHeldTest(testTakerId, programCode);
		for (Test test : allTests) {
			if (heldTests.contains(test)) {
				tests.add(new TestVO(test, false, TestVO.TEST_HELD));
			} else if (availableTests.contains(test)) {
				tests.add(new TestVO(test, false, TestVO.TEST_AVAILABLE));
			} else {
				tests.add(new TestVO(test, true, TestVO.TEST_SCHEDULED));
			}
		}
		Collections.sort(tests, new Comparator<TestVO>() {
			@Override
			public int compare(TestVO o1, TestVO o2) {
				if (o1.getStatus() != o2.getStatus()) {
					return o1.getStatus() - o2.getStatus();
				} else {
					return o1.getTest().getTestName().compareTo(o2.getTest().getTestName());
				}
			}
		});
		return tests;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public Booking saveBooking(Booking booking){
    	return bookingDao.save(booking);
    }
	@Override
	public List<Form> getAvailableTestForms(Long testTakerId, Long testId, Date testDate) {
		return bookingDao.getAvailableForms(testTakerId, testId, testDate);
	}

	@Override
	public List<Form> getAvailableTestFormsByTestId(Long testId) {
		return bookingDao.getAvailableTestFormsByTestId(testId);
	}

	@Override
	public List<Booking> getFutureBookingsByCustomerId(Long testTakerId, String programCode) {
		return bookingDao.getFutureBookingByProduct(programCode, testTakerId);
	}

	@Override
	public List<Booking> getFutureBookingsByCustomerId(Long testTakerId, String programCode,SearchParameters searchParams) {
		return bookingDao.getFutureBookingByProduct(programCode, testTakerId,searchParams);
	}

	@Override
	public TestTakerTest saveTestTakerTest(TestTakerTest testTakerTest) {
		return testTakerTestDao.saveTestTakerTest(testTakerTest);
	}

	@Override
	public Form getSubForm(Long parentFormID, Long testId, String langCode, String delvryMode)
			throws SubFormNotFoundException {
		Form form = bookingDao.getSubForm(parentFormID, testId, langCode, delvryMode);
		if (null == form) {
			throw new SubFormNotFoundException();
		} else {
			return form;
		}
	}

	@Override
	public Test getTestById(String programCode, Long testId) {
		return bookingDao.getAllTestById(programCode, testId);
	}

	@Override
	public Form getFormById(Long formId) {
		return bookingDao.getAllFormById(formId).get(0);
	}

	@Override
	public Booking getBookingById(Long id) {
		return bookingDao.getBookingById(id);
	}

    public boolean checkAppointmentNumber(String appointmentNumber) {
        return bookingDao.checkAppointmentNumber(appointmentNumber);
    }

    @Override
    public String getAppointmentNumber(String testType){
       String appointmentNumber = null;
       while(true){
           appointmentNumber = generateUniqueCode.generateAppointmentNumber(testType);
           if(!checkAppointmentNumber(appointmentNumber)){
               break;
           }
       }
       return appointmentNumber;
    }

	@Override
	public AppointmentVO processAppointment(AppointmentVO appointment)
			throws SubFormNotFoundException, SubFormSelectionNeededException {
		Booking booking = appointment.getBooking();

		Date testDateTime =  booking.getAppointmentDateTime();

		// setting test time
		if (!appointment.getTestTimeHour().isEmpty() && !appointment.getTestTimeMinute().isEmpty()) {
			testDateTime = DateTimeUtil.getDateTime(testDateTime,
					appointment.getTestTimeHour(),
					appointment.getTestTimeMinute(),
					appointment.getTestTimeAMPM());
			booking.setAppointmentDateTime(testDateTime);
		}

		// setting test center
		if (null != booking.getTestCenter()) {
			appointment.setTestCenter(String.valueOf(booking.getTestCenter().getId()));
			booking.setTimeZoneOffsetQuantity(DateTimeUtil.getTimeZoneOffset(
					booking.getTestCenter().getGlobalTimeZone().getCode(), 
					booking.getAppointmentDateTime()));
		}

		// setting base form id
		if (null != appointment.getBaseForm()) {
			appointment.setBaseFormId(appointment.getBaseForm().getFormID());
		}

		// setting ETS appointment Id
		if (null != booking.getTestVariation().getDeliveryModeType()) {
			booking.setEtsApptID(getAppointmentNumber(booking.getTestVariation().getDeliveryModeType().getCode()));
		}

		// setting delivery mode
		booking.getTestVariation().setDeliveryModeType(booking.getTestVariation().getDeliveryModeType());

		// setting event outcome status
		//Liz 3/8/13 Do not set the status here

		// setting sub form
		Form form = null;
		List<Form> subForms = Collections.emptyList();
		List<String> accommodationCodes = null;
		if (null != booking.getBookingAccommodations()) {
			accommodationCodes = new ArrayList<String>();
			for (BookingAccommodation accommodation : booking.getBookingAccommodations()) {
				AccommodationType accommodationType = accommodation.getAccommodationType();
				accommodationCodes.add(accommodationType.getCode());
			}
		}

		if(appointment.getBooking().getTestVariation().getDeliveryModeType().getCode().equals(DeliveryModeType.PBT)){
			try {
				subForms = getSubForms(
						appointment.getBaseForm().getFormID(),
						booking.getTestVariation().getId().getTestId(),
						booking.getTestVariation().getId().getLanguageCode(),
						booking.getTestVariation().getId().getDeliveryModeCode(),
						accommodationCodes);
				switch (subForms.size()) {
				// if subForms.size() is 0, a SubFormNotFoundException will be thrown.
				case 1:
					form = subForms.get(0);
					break;
				case 2:
					if (null == subForms.get(0).getAccommodation() && null != subForms.get(1).getAccommodation()) {
						form = subForms.get(1);
					} else if (null != subForms.get(0).getAccommodation() && null == subForms.get(1).getAccommodation()) {
						form = subForms.get(0);
					} else {
						log.error("getSubForms(): Two sub forms returned; either both have accomodations or both don't.");
						form = getZombieForm(booking);
						throw new SubFormNotFoundException();
					}
					break;
				default:
					form = getZombieForm(booking);
					for (Iterator<Form> it = subForms.iterator(); it.hasNext();) {
						if (null == it.next().getAccommodation()) {
							it.remove();
							break;
						}
					}
					throw new SubFormSelectionNeededException(subForms);
				}
			} catch (SubFormNotFoundException e) {
				form = getZombieForm(booking);
				throw new SubFormNotFoundException();
			} finally {
				booking.setForm(form);
			}
		}

		return appointment;
	}

	private Form getZombieForm(Booking booking) {
		Form form = new FormImpl();
		form.setTest(booking.getTestVariation().getTest());
        form.setLangCode(booking.getTestVariation().getLanguageType());
        form.setDlvyMode(booking.getTestVariation().getDeliveryModeType());
		/*form.setTestVariation(booking.getTestVariation());*/
		return form;
	}

	@Override
	public AppointmentVO cloneAppointment(Booking oldBooking) {
		AppointmentVO appointment = new AppointmentVO();
		Booking newBooking = new BookingImpl();

		newBooking.setAppointmentDateTime(oldBooking.getAppointmentDateTime());
		newBooking.setComments(oldBooking.getComments());
		newBooking.setDuration(oldBooking.getDuration());
		newBooking.setEtsApptID(oldBooking.getEtsApptID());
		newBooking.setEvntOutComeStatus(oldBooking.getEvntOutComeStatus());
		newBooking.setExtrnlAppointmentID(oldBooking.getExtrnlAppointmentID());
		newBooking.setForm(oldBooking.getForm());
		newBooking.setTestVariation(oldBooking.getTestVariation());

		newBooking.setTestCenter(oldBooking.getTestCenter());
		newBooking.setTestTakerTestId(oldBooking.getTestTakerTestId());
		newBooking.setTstLnchTime(oldBooking.getTstLnchTime());
		newBooking.setTstPackageId(oldBooking.getTstPackageId());
		newBooking.setBookingAccommodations(oldBooking.getBookingAccommodations());

		appointment.setBooking(newBooking);
		appointment.setAgency(String.valueOf(newBooking.getTestCenter().getAgency(
				ProgramContextHolder.getProgramCode()).getId()));
		appointment.setTestId(newBooking.getTestVariation().getId().getTestId());
		if(newBooking.getForm() != null){
			appointment.setBaseFormId(newBooking.getForm().getParentFormID().getFormID());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(newBooking.getAppointmentDateTime());

		appointment.setTestTimeHour(processTime(calendar.get(Calendar.HOUR)));
		appointment.setTestTimeMinute(processTime(calendar.get(Calendar.MINUTE)));
		appointment.setTestTimeAMPM(calendar.get(Calendar.AM_PM) == 0 ? "am" : "pm");

		return appointment;
	}

	private String processTime(int t) {
		String time = String.valueOf(t);
		if ("0".equalsIgnoreCase(time)) {
			time = "00";
		}
		return time;
	}

	@Override
	public List<Form> getSubForms(Long parentFormID, Long testId,
			String langCode, String delvryMode,
			List<String> accommodations)
			throws SubFormNotFoundException {
		List<Form> subForms = Collections.emptyList();
		if (null == accommodations || accommodations.isEmpty()) {
			subForms = new ArrayList<Form>();
			subForms.add(bookingDao.getSubForm(parentFormID, testId, langCode, delvryMode));
		} else {
			subForms = bookingDao.getSubForms(parentFormID, testId, langCode, delvryMode, accommodations);
		}
		return subForms;
	}

	@Override
	public List<Test> getAllTests(String programCode) {
		return bookingDao.getAllTest(programCode);
	}

    @Override
    public BookingAccommodation saveBookingAccommodation(
            BookingAccommodation bookingAccommodation) {
        return bookingAccommodationDao.save(bookingAccommodation);
    }
	@Override
	public List<Form> getAvailTestFormsIncCurrent(Long bookingId) {
		Booking booking=bookingDao.getBookingById(bookingId);
		Form parentForm=booking.getForm().getParentFormID();
		Long customerId=booking.getTestTakerTestId().getCustomer().getId();
		Long testId=booking.getTestVariation().getId().getTestId();
		Date testDate=booking.getAppointmentDateTime();
		List<Form> forms=getAvailableTestForms(customerId,testId,testDate);
		if(EventOutComeStatus.CHECKED_IN.equals(booking.getEvntOutComeStatus().getCode()))
		{
			forms.add(parentForm);
		}
		return forms;
	}
    @Override
    public List<Booking> getFutureBookingsByCustomerId(Long customerIdLong,
            String productCode, int maxResult) {
        return bookingDao.getBookingByCustomerId(customerIdLong, productCode, maxResult);
    }
	@Override
	public List<Booking> getBookingsWithAccommodation(Long customerIdLong,
			String accommodationTypeCode, Date fromDate, Date toDate) {

		return bookingDao.getBookingsWithAccommodation(customerIdLong, accommodationTypeCode, fromDate, toDate);
	}

	@Override
    public List<Booking> saveAppointments(List<AppointmentVO> appointments, ETSCustomer customer) {
        List<Booking> persistedBookings = new ArrayList<Booking>();
        TestTakerTest testTakerTest = null;
        Booking booking = null;
        for (AppointmentVO appointment : appointments) {
//            testTakerTest = new TestTakerTestImpl();
            booking = appointment.getBooking();
            booking.setRegistrationSystemId(getRegistrationSystemId());
            if(!customer.isNameUpdated()){
                prepareSnapshot(booking, customer);
            }
//            booking.setTestTakerTestId(testTakerTest);
//            testTakerTest.getBookings().add(booking);
//            testTakerTest.setCustomer(customer);
//            testTakerTest.setTestStatusCode(referenceService.getEntityByPrimaryKey(
//                    TestStatusType.class, TestStatusEnum.SCHEDULED.toString()));

            //before saving we need to remove the accommodations as they are missing the booking ID and we need to save before we can add it
            Set<BookingAccommodation> accommodations = booking.getBookingAccommodations();
            booking.setBookingAccommodations(null);
            testTakerTest = prepareTestTakerTest(booking, customer);
            testTakerTest.getBookings().add(booking);
            testTakerTest = saveTestTakerTest(testTakerTest);

            Iterator<Booking> it = testTakerTest.getBookings().iterator();
            if (it.hasNext()) {
                Booking persistedBooking = it.next();
                if (BookingStatusEnum.RESERVED.toString().equalsIgnoreCase(
                        persistedBooking.getBookingStatusType().getCode())) {
                    if(accommodations!=null && accommodations.size() > 0){
                        saveBookingAccommodation(persistedBooking, appointment.getTestId(), customer.getId(), accommodations);
                    }
                    persistedBookings.add(persistedBooking);
                }
            }
        }

        return persistedBookings;
    }

	@Override
	public TestTakerTest prepareTestTakerTest(Booking booking, Customer customer) {
		TestTakerTest testTakerTest = new TestTakerTestImpl();
        booking.setTestTakerTestId(testTakerTest);
        //testTakerTest.getBookings().add(booking);
        testTakerTest.setCustomer(customer);
        testTakerTest.setTestStatusCode(referenceService.getEntityByPrimaryKey(
                TestStatusType.class, TestStatusEnum.SCHEDULED.toString()));

        return testTakerTest;
	}

	@Override
	public void prepareBooking(Booking booking, Customer customer) {
        booking.setRegistrationSystemId(getRegistrationSystemId());
        ETSCustomer etsCustomer = (ETSCustomer) customer;
        if(!etsCustomer.isNameUpdated()){
            prepareSnapshot(booking, etsCustomer);
        }
	}

	private void prepareSnapshot(Booking booking, ETSCustomer customer) {
        booking.setCustFirstName(customer.getFirstName());
        booking.setCustMidtname(customer.getMiddleInitial());
        booking.setCustLastName(customer.getLastName());
        booking.setCustDOB(customer.getDateOfBirth());
    }

	private void saveBookingAccommodation(Booking booking, Long testId, Long customerId, Set<BookingAccommodation> accommodations) {
        for(BookingAccommodation accommodation: accommodations) {
            accommodation.getBookingAccommodationId().setBookingId(booking.getId());
            accommodation.setBooking(booking);
        }
        booking.setBookingAccommodations(accommodations);
    }


	@Override
    public Booking updateAppointment(List<AppointmentVO> appointments, ETSCustomer customer) {
        Booking updatedBooking = null;

        AppointmentVO newAppointment = appointments.get(0);
        AppointmentVO oldAppointment = appointments.get(1);
        if (null != customer) {
            prepareSnapshot(newAppointment.getBooking(), customer);
        }
        newAppointment.getBooking().setBookingStatusType(
                referenceService.getEntityByPrimaryKey(
                BookingStatusType.class,
                BookingStatusEnum.RESERVED.toString()));
        newAppointment.getBooking().setRegistrationSystemId(getRegistrationSystemId());
        oldAppointment.getBooking().setBookingStatusType(
                referenceService.getEntityByPrimaryKey(
                BookingStatusType.class,
                BookingStatusEnum.CANCELED.toString()));

        TestTakerTest testTakerTest = oldAppointment.getBooking().getTestTakerTestId();
        testTakerTest.getBookings().add(newAppointment.getBooking());


        //before saving we need to remove the accommodations as they are missing the booking ID and we need to save before we can add it
        Set<BookingAccommodation> reservedAccommodations = Collections.emptySet();
        Iterator<Booking> it = testTakerTest.getBookings().iterator();
        while (it.hasNext()) {
            Booking booking = it.next();
            if (BookingStatusEnum.RESERVED.toString().equalsIgnoreCase(
                    booking.getBookingStatusType().getCode())) {
                reservedAccommodations = booking.getBookingAccommodations();
                booking.setBookingAccommodations(null);
                break;
            }

        }
        testTakerTest = saveTestTakerTest(testTakerTest);

        Iterator<Booking> it2 = testTakerTest.getBookings().iterator();
        while (it2.hasNext()) {
            Booking persistedBooking = it2.next();
            if (BookingStatusEnum.RESERVED.toString().equalsIgnoreCase(
                    persistedBooking.getBookingStatusType().getCode())) {
                updatedBooking = persistedBooking;
                saveBookingAccommodation(persistedBooking, newAppointment.getTestId(), customer.getId(), reservedAccommodations);
                break;
            }
        }

        return updatedBooking;
    }

	@Override
	public void saveBookingWithNewName(ETSCustomer customer) {

        List<Booking> bookingList = getFutureBookingsByCustomerId(customer.getId(), ProgramContextHolder
                .getProgramCode(), MAX_RESULT);
        List<AppointmentVO> appointments = new ArrayList<AppointmentVO>();
        AppointmentVO appointment = null;
        for (Booking booking : bookingList) {
            appointment = new AppointmentVO();
            if(booking.getEvntOutComeStatus().getCode().equalsIgnoreCase(EventOutComeStatus.NOT_CHECKED_IN)){
                saveNewCustomerName(booking, customer);
                appointment.setBooking(booking);
                appointments.add(appointment);
            }
        }
        saveAppointments(appointments, customer);

    }

    private void saveNewCustomerName(Booking booking, ETSCustomer customer) {
        booking.setCustFirstName(customer.getFirstName());
        booking.setCustLastName(customer.getLastName());
        booking.setCustMidtname(customer.getMiddleInitial());

    }

    public String getRegistrationSystemId(){
        return applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.REG_SYS_ID);

    }


}
