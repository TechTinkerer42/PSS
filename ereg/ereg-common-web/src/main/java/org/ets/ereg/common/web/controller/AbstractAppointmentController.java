package org.ets.ereg.common.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.ets.ereg.common.business.service.TestVariationService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.exception.ERegRuntimeException;
import org.ets.ereg.common.util.ERegMessageResourceUtil;
import org.ets.ereg.common.vo.FindSeatInfo;
import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.profile.accommodation.service.CustomerAccommodationService;
import org.ets.ereg.scheduling.util.SchedulingTypeEnum;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContext;

public abstract class AbstractAppointmentController extends AbstractController {

    protected static Logger log;

    public static final String APPOINTMENT_FORM = "appointmentForm";
    public static final String INDEX = "index";
    public static final String TEST_CENTERS = "testCenters";

    @Resource(name = "referenceEntityBusinessService")
    protected ReferenceBusinessService referenceEntityBusinessService;

    @Resource(name = "profileBusinessService")
    protected ProfileBusinessService profileBusinessService;

    @Resource(name = "bookingBusinessService")
    protected BookingBusinessService bookingBusinessService;

    @Resource(name = "etsAdminUserBusinessService")
    protected ETSAdminUserBusinessService etsAdminUserBusinessService; // TODO check necessity

    @Resource(name = "generateBase64StringBusinessService")
    protected GenerateBase64StringBusinessService generateBase64StringBusinessService; // TODO check necessity

    @Resource(name = "customerAccommodationService")
    protected CustomerAccommodationService customerAccommodationService; // TODO check necessity

    @Resource(name = "eregMessageResource")
    private ERegMessageResourceUtil eregMessageResource;

    @Resource(name = "testVariationService")
    private TestVariationService testVariationService;


    protected void getRemainingAppointments(AutoPopulatingList<AppointmentVO> appointments) {
        for (Iterator<AppointmentVO> it = appointments.iterator(); it.hasNext();) {
            if (it.next().getDeletedFlag()) {
                it.remove();
            }
        }
    }

    protected String removeAppointment(
            @RequestParam(INDEX) Integer index,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
        if (index < appointmentForm.getAppointments().size()) {
            appointmentForm.getAppointments().get(index).setDeletedFlag(true);
        }
        return "OK";
    }

    protected String editProfile(
            HttpServletRequest request,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
            String redirectResumeScheduling) {
        request.getSession().setAttribute(
                Constant.AFTER_UPDATE_PROFILE_URL_SESSION_ATTR, redirectResumeScheduling);

        String encodeUrl = generateBase64StringBusinessService.encodeBase64String(
                request.getUserPrincipal().getName() + Constant.ENCODING_DELIMITTER +
                "?customerId=" + appointmentForm.getProfile().getCustomer().getId());

        return "redirect:/secure/profile/editCustomerProfile/" + Constant.URL_DECODE_IDENTIFIER + encodeUrl;
    }

    public String resumeAppointment(
            @ModelAttribute(APPOINTMENT_FORM)AppointmentForm appointmentForm,
            String getPersonalInfoView) {
        appointmentForm.setProfile(profileBusinessService.readProfileById(
                appointmentForm.getProfile().getCustomer().getId()));
        return getPersonalInfoView;
    }

    public Map<String, FindSeatInfo> findSeat(HttpServletRequest request, AppointmentForm appointmentForm,
           Long testId, String testStartDate, String testEndDate, String deliveryMode, Long customerId, String languageCode, boolean isCSR) {
       Map<String, FindSeatInfo> resultMap = null;
       boolean isTCAModel = Boolean.FALSE;
       String programCode = ProgramContextHolder.getProgramCode();
       if (appointmentForm.getTestCenter().getSchedulingType(programCode).getCode().equals(SchedulingTypeEnum.TCA_MODEL.getCode())) {
           isTCAModel = Boolean.TRUE;
       }

       TCFindSeatRequest findSeatrequest = createFindSeatRequest(customerId, testId, testStartDate, testEndDate, ProgramContextHolder.getProgramCode(), deliveryMode, appointmentForm.getTestCenter().getId(), languageCode, isCSR);
       FindSeatResponse findSeatResponse = getFindSeatResponse(request.getSession(), findSeatrequest, isTCAModel, deliveryMode);
       RequestContext requestContext = new RequestContext(request);
       resultMap =  bookingBusinessService.findSeatForSpecificDates(findSeatrequest, findSeatResponse, requestContext, eregMessageResource);

       return resultMap;
    }

    public FindSeatResponse getFindSeatResponse(HttpSession session, TCFindSeatRequest findSeatrequest, boolean isTCAModel, String deliveryMode) {
        FindSeatResponse findSeatResponse = null;
        findSeatResponse = bookingBusinessService.getFindSeatResponse(findSeatrequest);
        if(isTCAModel && DeliveryModeType.PBT.equalsIgnoreCase(deliveryMode)) {
            checkAndSetResponseInSession(session, findSeatResponse, "findSeatAdminPBTResponse");
        } else {
            checkAndSetResponseInSession(session, findSeatResponse, "findSeatResponse");
        }
        return findSeatResponse;
    }

    private void checkAndSetResponseInSession(HttpSession session, FindSeatResponse findSeatResponse, String responseSession) {
    	session.setAttribute(responseSession, findSeatResponse);
    }

    public TCFindSeatRequest createFindSeatRequest(Long customerId, Long testId,
                String testStartDate, String testEndDate, String programCode,
                String deliveryMode, Long testCenterId, String languageCode, boolean isCSR) {
         TCFindSeatRequest findSeatRequest = null;
            if((customerId !=null && customerId != 0)
                    || testId > 0
                    || StringUtils.isNotEmpty(testStartDate) || StringUtils.isNotBlank(testStartDate)
                    || StringUtils.isNotEmpty(testEndDate) || StringUtils.isNotBlank(testEndDate)
                    || StringUtils.isNotEmpty(programCode) || StringUtils.isNotBlank(programCode)
                    || StringUtils.isNotEmpty(deliveryMode) || StringUtils.isNotBlank(deliveryMode)
                    || StringUtils.isNotEmpty(languageCode) || StringUtils.isNotBlank(languageCode)){
                findSeatRequest = bookingBusinessService.createFindSeatRequest(customerId, testId, testStartDate, testEndDate, programCode, deliveryMode, testCenterId, languageCode, isCSR);
            } else {
                throw new ERegRuntimeException("Start date and End date required" );
            }

            return findSeatRequest;
        }

/*    public boolean holdSeat(HttpSession session, Long customerId,
            @RequestParam(value = "dateTimeIndex") int dateTimeIndex,
            @RequestParam(value = "bookingIndex") int bookingIndex,
            @RequestParam(value = "testId") Long testId,
            @RequestParam(value = "deliveryMode") String deliveryMode,
            @RequestParam("languageCode") String languageCode,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
            ERegUser user) {

        boolean holdSeat = false;
        FindSeatResponse findSeatResponse = (FindSeatResponse) session.getAttribute("findSeatResponse");
        Seat seat = findSeatResponse.getSeatById(dateTimeIndex);
        if(seat == null){
            return false;
        }
        seat.setTestCenter(appointmentForm.getTestCenter());
        TestVariation testVariation = testVariationService.getTestVariationForTest(testId, deliveryMode, languageCode, ProgramContextHolder.getProgramCode());
        seat.setTestVariation(testVariation);
        seat.setDeliveryModeCode(deliveryMode);
        HeldSeat heldSeat = bookingBusinessService.holdSeat(customerId, seat);
        if(heldSeat != null){
            List<AppointmentVO> appointments = appointmentForm.getAppointments();
            bookingBusinessService.saveHeldBooking(appointments, customerId, bookingIndex, heldSeat);
            holdSeat = true;
            if (seat.getCustomerAccommodations() != null && seat.getCustomerAccommodations().size() > 0 && user.hasCSRRole()) {
            	Booking booking = appointmentForm.getAppointments().get(bookingIndex).getBooking();
            	booking.setBookingAccommodations(bookingBusinessService
            			.convertToBookingAccomodations(booking, seat.getCustomerAccommodations()));
            }
        } else {
            holdSeat = false;
        }

        return holdSeat;

    }*/

    public boolean holdSeat(HttpSession session, Long customerId,
            int dateTimeIndex, int bookingIndex, String deliveryMode,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm, ERegUser user) {

        boolean holdSeat = false;
        FindSeatResponse findSeatResponse = (FindSeatResponse) session.getAttribute("findSeatResponse");
        Seat seat = findSeatResponse.getSeatById(dateTimeIndex);
        if(seat == null){
            return false;
        }
        seat.setTestCenter(appointmentForm.getTestCenter());
        seat.setDeliveryModeCode(seat.getTestVariation().getDeliveryModeType().getCode());
        HeldSeat heldSeat = bookingBusinessService.holdSeat(customerId, seat);
        if(heldSeat != null){
            List<AppointmentVO> appointments = appointmentForm.getAppointments();
            bookingBusinessService.saveHeldBooking(appointments, customerId, bookingIndex, heldSeat);
            holdSeat = true;
            if (seat.getCustomerAccommodations() != null && seat.getCustomerAccommodations().size() > 0 && user.hasCSRRole()) {
                Booking booking = appointmentForm.getAppointments().get(bookingIndex).getBooking();
                booking.setBookingAccommodations(bookingBusinessService
                        .convertToBookingAccomodations(booking, seat.getCustomerAccommodations()));
            }
        } else {
            holdSeat = false;
        }

        return holdSeat;

    }

    public List<TestCenter> getTestCentersByAgency(Long agencyId) {
    	return bookingBusinessService.getTestCentersByAgency(agencyId);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        // Test Title
        binder.registerCustomEditor(Test.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Test value = null;
                if (!text.isEmpty()) {
                    value = bookingBusinessService.getTestById(ProgramContextHolder.getProgramCode(), Long.parseLong(text));
                }
                setValue(value);
            }
            @Override
            public String getAsText() {
                Test test = (Test) getValue();
                if (null != test && null != test.getTestId()) {
                    return test.getTestId().toString();
                } else {
                    return "";
                }
            }
        });

        // Test Date
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            @Override
            public void setAsText(String text) {
                Date testDate = null;
                try {
                    testDate = dateFormat.parse(text);
                } catch (ParseException e) {
                    log.error("Error parsing date: {}, message: {}", text, e.toString());
                }
                setValue(testDate);
            }
            @Override
            public String getAsText() {
                Date testDate = (Date) getValue();
                return null == testDate ? "" : dateFormat.format(testDate);
            }
        });

        binder.registerCustomEditor(Calendar.class, new PropertyEditorSupport() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            @Override
            public void setAsText(String text) {
                Date testDate = null;
                Calendar testCal = Calendar.getInstance();
                try {
                    testDate = dateFormat.parse(text);
                    testCal.setTime(testDate);
                } catch (ParseException e) {
                    log.error("Error parsing date: {}, message: {}", text, e.toString());
                }
                setValue(testCal);
            }
            @Override
            public String getAsText() {
                Calendar testCal = (Calendar) getValue();
                return null == testCal ? "" : dateFormat.format(testCal.getTime());
            }
        });

        // Test Center
        binder.registerCustomEditor(TestCenter.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                TestCenter testCenter = null;
                if (!text.isEmpty()) {
                    testCenter = bookingBusinessService.getTestCenterById(Long.parseLong(text));
                }
                setValue(testCenter);
            }
        });

        // Test Language
        binder.registerCustomEditor(LanguageType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                LanguageType value = null;
                if (!text.isEmpty()) {
                    value = referenceEntityBusinessService.getEntityByPrimaryKey(LanguageType.class, text);
                }
                setValue(value);
            }
        });

        // Test Form
        binder.registerCustomEditor(Form.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Form value = null;
                if (!text.isEmpty()) {
                    value = bookingBusinessService.getFormById(Long.parseLong(text));
                }
                setValue(value);
            }
        });

        // Test Type
        binder.registerCustomEditor(DeliveryModeType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                DeliveryModeType value = null;
                if (!text.isEmpty()) {
                    value = referenceEntityBusinessService.getEntityByPrimaryKey(DeliveryModeType.class, text);
                }
                setValue(value);
            }
            @Override
            public String getAsText() {
                DeliveryModeType deliveryModeType = (DeliveryModeType) getValue();
                if (null != deliveryModeType && null != deliveryModeType.getCode()) {
                    return deliveryModeType.getCode();
                } else {
                    return "";
                }
            }
        });
    }

}
