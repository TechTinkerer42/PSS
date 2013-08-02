package org.ets.ereg.csr.web.controller.appointment;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.broadleafcommerce.core.order.domain.NullOrderImpl;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.order.CartState;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.common.vo.FindSeatInfo;
import org.ets.ereg.common.web.controller.AbstractAppointmentController;
import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.ets.ereg.csr.web.util.AppointmentUtil;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.csr.web.validator.appointment.AppointmentFormValidator;
import org.ets.ereg.domain.form.FormImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.exception.SubFormSelectionNeededException;
import org.ets.ereg.scheduling.util.SchedulingTypeEnum;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.scheduling.vo.TestVO;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/secure/tcaScheduling")
@SessionAttributes(AppointmentController.APPOINTMENT_FORM)
public class AppointmentController extends AbstractAppointmentController {

	private static Logger log = LoggerFactory.getLogger(AppointmentController.class);

    private static final String DATEFORMAT_YYYYMMDD = "yyyyMMdd";
//    private static final String DATEFORMAT_YYYYMMDD_HHMMSS = "yyyyMMdd HH:mm:ss";
    private static final String DATEFORMAT_YYYYMMDD_HHMMA = "yyyyMMdd hh:mm a";
    private static final String DATEFORMAT_MMDDYYYY = "MM-dd-yyyy";

    @Resource(name = "etsOrderService")
    private ETSOrderService etsOrderService;

	@Resource(name = "tcaAppointmentValidator")
	private AppointmentFormValidator appointmentValidator;

	public static final String getTCATestCenters() {
		return "appointment/tcaTestCenters";
	}

	public static final String getPersonalInfoView() {
		return "appointment/personalInfo";
	}

	public static final String getAppointmentInfoView() {
		return "appointment/appointmentInfo";
	}

	public static final String getAvailableTestFormsPanel() {
		return "appointment/availableTestForms";
	}

	public static final String getAddAnotherAppointmentPanel() {
		return "appointment/appointmentInfoPanel";
	}

	private String getSubFormSelectionView() {
		return "appointment/subFormSelection";
	}

	public static final String getReviewAppointmentView() {
		return "appointment/reviewAppointment";
	}

	public static final String getAppointmentScheduledView() {
		return "appointment/appointmentScheduled";
	}

	public static final String getViewAppointmentView() {
		return "appointment/viewAppointment";
	}

	public static final String getNoPrivilegeView() {
		return "error/customErrorPage";
	}

	public static final String getRescheduleAppointmentView() {
		return "appointment/rescheduleAppointment";
	}

	public static final String getAppointmentRescheduledView() {
		return "appointment/appointmentRescheduled";
	}

	public static final String getCancelAppointmentView() {
		return "appointment/cancelAppointment";
	}

	public static final String getAppointmentCancelledView() {
		return "appointment/appointmentCanceled";
	}

	public static final String getTestCenterOptions() {
		return "appointment/testCenterOptions";
	}

	public static final String redirectPersonalInfoView() {
		return "redirect:/secure/tcaScheduling/newappointment";
	}

	public static final String redirectAppointmentInfoView() {
		return "redirect:/secure/tcaScheduling/new/info";
	}

	private String redirectSubFormSelectionView() {
		return "redirect:/secure/tcaScheduling/new/accommodation";
	}

	public static final String redirectReviewAppointmentView() {
		return "redirect:/secure/tcaScheduling/new/review";
	}

	public static final String redirectTestTakerSummary() {
		return "redirect:/secure/testtaker/view/";
	}

	private String redirectResumeScheduling() {
		return "redirect:/secure/tcaScheduling/resumeappointment";
	}

	private String redirectBeforeSelfServeScheduling() {
		return "redirect:/secure/appointment/beforeSchedule";
	}

	/* getters and setters for testing */
	public ProfileBusinessService getProfileBusinessService() {
		return profileBusinessService;
	}

	public void setProfileBusinessService(ProfileBusinessService profileBusinessService) {
		this.profileBusinessService = profileBusinessService;
	}

	public AppointmentFormValidator getAppointmentValidator() {
		return appointmentValidator;
	}

	public void setAppointmentValidator(AppointmentFormValidator appointmentValidator) {
		this.appointmentValidator = appointmentValidator;
	}

	public static final String getActiveAccommodations() {
		return "appointment/activeAccommodations";
	}

	public static final String getAppointmentData() {
		return "appointment/appointmentData";
	}

	public static final String getAgencyOptionsView() {
		return "appointment/agencyOptions";
	}



	@RequestMapping(value = "/tcaTestCenters", method = RequestMethod.GET)
	public String getTCATestCenters(
			ModelMap model, HttpServletRequest request,Principal principal,
			@LoggedInUser ERegUser user) {
		//System.out.println(loggedInUser.getFirstName());
		model.addAttribute(getTestCenters(user));
		return getTestCenterOptions();
		//return "test";
	}

	@RequestMapping(value = "/new", method = { RequestMethod.POST, RequestMethod.GET })
	public String newAppointment(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "customerId") Long customerId,
			@RequestParam(value = "testCenterId") Long testCenterId,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors) {

		String programCode = ProgramContextHolder.getProgramCode();
		TestCenter testCenter = bookingBusinessService.getTestCenterById(testCenterId);

		//check if TCA and testCenter is self scheduling
		//TODO redirect to error page
		if(testCenter.getSchedulingType(programCode).getCode().equals(SchedulingTypeEnum.CANDIDATE_MODEL.getCode())
			&& request.isUserInRole(RoleEnum.ROLE_TEST_CENTER_ADMIN.getCode())){
				return getNoPrivilegeView();
		}

		appointmentForm.setTestCenter(testCenter);
		appointmentForm.setTestCenterAddress(bookingBusinessService.getTestCenterAddress(testCenter));
		appointmentForm.setAgencyId(testCenter.getAgency(programCode).getId());

		ProfileVO profile = null;
		if (customerId != null) {
			profile = profileBusinessService.readProfileById(customerId);
		}
		if (null == profile) {
			return "redirect:/profile/notfound";
		} else {
			appointmentForm.setProfile(profile);
		}

		model.addAttribute("customerId", customerId);
		model.addAttribute("testCenterId", testCenterId);

		appointmentForm.setAppointmentCount(appointmentForm.getAppointments().size() - 1);

		if (!appointmentForm.getGetTestsFlag()) {
			appointmentForm.setTests(bookingBusinessService.getAvailableTests(
					appointmentForm.getProfile().getCustomer().getId(), programCode));
			appointmentForm.setGetTestsFlag(true);
		}

		if(!request.isUserInRole(RoleEnum.ROLE_TEST_CENTER_ADMIN.getCode())){ // only CSR and TCA can come

			String currentCustomerUserName = (String)request.getSession().getAttribute(Constant.CUSTOMER_USER_NAME);
			if(!profile.getCustomer().getUsername().equals(currentCustomerUserName) && CartState.getCart() != null && !(CartState.getCart() instanceof NullOrderImpl)){
				try {
					etsOrderService.clearCart(CartState.getCart());
					log.debug("Named Cart Removed for {}", currentCustomerUserName);
				} catch (RemoveFromCartException e) {
					log.error("Error while removing cart on logout or session timeout", currentCustomerUserName);
				} catch (PricingException e) {
					log.error("Error while removing cart on logout or session timeout", currentCustomerUserName);
				}

			}

		}

		if(testCenter.getSchedulingType(ProgramContextHolder.getProgramCode()).getCode().equals(SchedulingTypeEnum.CANDIDATE_MODEL.getCode())){

			request.getSession().setAttribute(Constant.CUSTOMER_USER_NAME, profile.getCustomer().getUsername());
			return redirectBeforeSelfServeScheduling() + "?testCenterId="+testCenterId;

		}else{
			return getPersonalInfoView();
		}
	}

	@RequestMapping(value = "/personal", method = RequestMethod.POST)
	public String processPersonalInfo() {
		return redirectAppointmentInfoView();
	}

	@RequestMapping(value = "/new/info", method = RequestMethod.GET)
	public String displayAppointmentInfoView(
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		getRemainingAppointments(appointmentForm.getAppointments());
		appointmentForm.setAppointmentCount(appointmentForm.getAppointments().size() - 1);

		appointmentForm.setAccomodationFlag(
				customerAccommodationService.hasApprovedActiveAccommodations(
						appointmentForm.getProfile().getCustomer().getId(),
						DateHandler.getCurrentDate()));

		return getAppointmentInfoView();
	}

	@RequestMapping(value = "/findSeatByAdmin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, FindSeatInfo> findSeatByAdmin(
			HttpServletRequest request,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
	        @RequestParam(value = "testId") Long testId,
            @RequestParam(value = "testStartDate") String testStartDate,
            @RequestParam(value = "testEndDate") String testEndDate,
            @RequestParam("languageCode") String languageCode,
            @RequestParam(value = "deliveryMode") String deliveryMode,
            @LoggedInUser ERegUser user) {
		boolean isCSR = user.hasCSRRole() ? true : false;
		return super.findSeat(request, appointmentForm, testId, testStartDate, testEndDate, deliveryMode, appointmentForm.getProfile().getCustomer().getId(), languageCode, isCSR);
    }

	@RequestMapping(value = "/new/info/getAvailableTestForm", method = RequestMethod.GET)
	public String getAvailableTestForm(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("testId") Long testId,
			@RequestParam("testDate") String testDate, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors) {
		Date dateTestDate = null;
		List<Form> availableTestForms = null;
		try {
			dateTestDate = new SimpleDateFormat(DATEFORMAT_MMDDYYYY).parse(testDate);
			availableTestForms = bookingBusinessService.getAvailableTestForms(
					appointmentForm.getProfile().getCustomer().getId(),
					testId, dateTestDate);
			Collections.sort(availableTestForms, new Comparator<Form>() {
				@Override
				public int compare(Form o1, Form o2) {
					return o1.getFormID().compareTo(o2.getFormID());
				}
			});
		} catch (ParseException e) {
			log.error("Error when parsing test date for get forms: {}", e.toString());
		}
		model.addAttribute("availableTestForms", availableTestForms);
		return getAvailableTestFormsPanel();
	}

	@RequestMapping(value = "/new/info/addAnotherAppointment", method = RequestMethod.GET)
	public String addAnotherAppointment(
			Model model,
			@RequestParam Integer appointmentCount,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		model.addAttribute("index", appointmentCount);
		return getAddAnotherAppointmentPanel();
	}

	@Override
    @RequestMapping(value = "/new/info/removeAppointment", method = RequestMethod.GET)
	@ResponseBody
	public String removeAppointment(
			@RequestParam Integer index,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return super.removeAppointment(index, appointmentForm);
	}

	@RequestMapping(value = "/new/info/getTestCenters", method = RequestMethod.GET)
	public String getTestCentersByAgency(@RequestParam Long agencyId, Model model) {
		List<TestCenter> testCenters = super.getTestCentersByAgency(agencyId);
		model.addAttribute(TEST_CENTERS, testCenters);
		return getTestCenterOptions();
	}

	@RequestMapping(value = "/new/info/changeTestCenter", method = RequestMethod.GET)
	@ResponseBody
	public String changeTestCenter(
			@RequestParam("testCenterId") Long testCenterId,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			@LoggedInUser ERegUser user) {
		Long oldAgencyId = appointmentForm.getAgencyId();
		TestCenter testCenter = bookingBusinessService.getTestCenterById(testCenterId);
		String address = bookingBusinessService.getTestCenterAddress(testCenter);
		appointmentForm.setTestCenter(testCenter);
		appointmentForm.setAgencyId(testCenter.getAgency(ProgramContextHolder.getProgramCode()).getId());
		appointmentForm.setTestCenterAddress(address);
		String json = null;
		if (user.hasCSRRole()) {
			appointmentForm.getAppointments().clear();
			appointmentForm.getAppointments().add(new AppointmentVO());
			String schedulingTypeCode = testCenter.getSchedulingType(ProgramContextHolder.getProgramCode()).getCode();
			if (appointmentForm.getAgencyId().equals(oldAgencyId)){
				json = "{\"action\":\"refresh\"}";
			}else if(schedulingTypeCode.equalsIgnoreCase(SchedulingTypeEnum.TCA_MODEL.getCode())) {
				json = "{\"action\":\"redirectToTCA\"}";
			}else if(schedulingTypeCode.equalsIgnoreCase(SchedulingTypeEnum.CANDIDATE_MODEL.getCode())) {
				json = "{\"action\":\"redirectToCM\"}";
			}
		} else if (user.hasTCARole()) {
			appointmentForm.getAppointments().clear();
			appointmentForm.getAppointments().add(new AppointmentVO());
			json = "{\"action\":\"refresh\"}";
		} else {
			json = "{\"action\":\"error\"}";
		}
		return json;
	}

	/*
	 * When go back to personal info screen, submit the form so that the
	 * appointment info is stored in the session.
	 */
	@RequestMapping(value = "/new/info", params = "goBack", method = RequestMethod.POST)
	public String goBackToPersonalInfoView(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors) {
		getRemainingAppointments(appointmentForm.getAppointments());
		appointmentForm.setAppointmentCount(appointmentForm.getAppointments().size() - 1);

		String encodeUrl = generateBase64StringBusinessService.encodeBase64String(
				request.getUserPrincipal().getName() + Constant.ENCODING_DELIMITTER
				+ "?customerId=" + appointmentForm.getProfile().getCustomer().getId()
				+ "&testCenterId="+ appointmentForm.getAppointments().get(0).getBooking().getTestCenter().getId());

		return "redirect:/secure/tcaScheduling/newappointment/"
				+ Constant.URL_DECODE_IDENTIFIER + encodeUrl;
	}

	@RequestMapping(value = "/new/info", method = RequestMethod.POST)
	public String processAppointmentInfoView(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors) {
		getRemainingAppointments(appointmentForm.getAppointments());
		appointmentForm.setAppointmentCount(appointmentForm.getAppointments().size() - 1);

		for(AppointmentVO appointment: appointmentForm.getAppointments()){
			appointment.getBooking().setTestCenter(appointmentForm.getTestCenter());
			if(appointment.getBooking().getTestVariation().getId().getDeliveryModeCode().equals(DeliveryModeType.CBT)){
				appointment.setBaseForm(null);
				appointment.getBooking().setForm(null);
			}
		}

		appointmentValidator.validateAppointmentInfo(appointmentForm, errors);
		if (errors.hasErrors()) {
			return getAppointmentInfoView();
		} else {
			List<Integer> indices = new ArrayList<Integer>();
			boolean isSubFormSelectionNeeded = bookingBusinessService
					.processAppointments(appointmentForm.getAppointments(),
							indices);
			for (Integer index : indices) {
				errors.rejectValue("appointments[" + index + "].booking.form",
						"schedulenewappointment.subFormMissing");
			}
			if (errors.hasErrors()) {
				return getAppointmentInfoView();
			} else if (isSubFormSelectionNeeded) {
				return redirectSubFormSelectionView();
			} else {
				return redirectReviewAppointmentView();
			}
		}
	}

	@RequestMapping(value = "new/accommodation", method = RequestMethod.GET)
	public String displaySubFormSelectionView(
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return getSubFormSelectionView();
	}

	@RequestMapping(value = "new/accommodation", method = RequestMethod.POST)
	public String processSubFormSelection(
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return redirectReviewAppointmentView();
	}

	@RequestMapping(value = "/new/review", method = RequestMethod.GET)
	public String displayReviewAppointmentView(
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return getReviewAppointmentView();
	}

	@RequestMapping(value = "/new/review", method = RequestMethod.POST)
	public String processReviewAppointmentView(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors, SessionStatus status) {
        request.getSession().setAttribute("viewProfileBackUrl", request.getRequestURI()); // TODO find out what this does

        List<Booking> bookings = bookingBusinessService.saveAppointments(
				appointmentForm.getAppointments(), appointmentForm.getProfile());
		bookingBusinessService.sendAppointmentScheduledMail(
				bookings, appointmentForm.getProfile());
		model.addAttribute("bookings", bookings);
		status.setComplete();
		return getAppointmentScheduledView();
	}

	@RequestMapping(value = "/new/review", params = "goBack", method = RequestMethod.POST)
	public String goBackToAppointmentInfoView(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors, SessionStatus status) {
		getRemainingAppointments(appointmentForm.getAppointments());
		appointmentForm.setAppointmentCount(appointmentForm.getAppointments().size() - 1);

		return redirectAppointmentInfoView();
	}

	/* View Appointment Info */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewAppointmentInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("bookingId") Long bookingId,
			@ModelAttribute(TEST_CENTERS) List<TestCenter> testCenters,
			Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors, SessionStatus status) {
		Booking booking = bookingBusinessService.getBookingById(bookingId);

		Long customerId = booking.getTestTakerTestId().getCustomer().getId();
		ProfileVO profile = profileBusinessService.readProfileById(customerId);
		appointmentForm.setProfile(profile);

		model.addAttribute("booking", booking);

		model.addAttribute("hasRescheduleAccess", AppointmentUtil
				.getPrivilegeForApptUpdate(request, testCenters, booking));

		appointmentForm.setAccomodationFlag(customerAccommodationService.hasApprovedActiveAccommodations(
				customerId, DateHandler.getCurrentDate()));

		model.addAttribute("accommodationsChanged", bookingBusinessService.isAccommodationsChanged(booking));

		return getViewAppointmentView();
	}

	/* Reschedule an Appointment */
	@RequestMapping(value = "/reschedule", method = RequestMethod.GET)
	public String diplayEditAppointmentInfoView(HttpServletRequest request,
			@RequestParam("bookingId") Long bookingId, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			@ModelAttribute(TEST_CENTERS) List<TestCenter> testCenters,
			BindingResult errors, Principal principal) {
		Booking oldBooking = bookingBusinessService.getBookingById(bookingId);

		if (!AppointmentUtil.getPrivilegeForApptUpdate(request, testCenters, oldBooking)) {
			return getNoPrivilegeView();
		}

		Long customerId = oldBooking.getTestTakerTestId().getCustomer().getId();
		ProfileVO profile = profileBusinessService.readProfileById(customerId);
		appointmentForm.setProfile(profile);

		AppointmentVO newAppointment = bookingBusinessService.cloneAppointment(oldBooking);
		appointmentForm.getAppointments().set(0, newAppointment);

		appointmentForm.getAppointments().add(new AppointmentVO());
		appointmentForm.getAppointments().get(1).setBooking(oldBooking);
		if(oldBooking.getForm() != null){
			appointmentForm.getAppointments().get(1).setBaseForm(oldBooking.getForm().getParentFormID());
		}else{
			appointmentForm.getAppointments().get(1).setBaseForm(null);
		}

		// TODO need to review the query and this logic
		appointmentForm.setTests(bookingBusinessService.getAvailableTests(
				customerId, ProgramContextHolder.getProgramCode()));
		for (Iterator<TestVO> it = appointmentForm.getTests().iterator(); it.hasNext();) {
			TestVO test = it.next();
			if (test.getTest().getTestId().equals(oldBooking.getTestVariation().getTest().getTestId())) {
				appointmentForm.setReschedulingTest(test);
				it.remove();
				break;
			}
		}
		appointmentForm.getTests().remove(appointmentForm.getReschedulingTest());
		
		appointmentForm.setTestCenter(newAppointment.getBooking().getTestCenter());

		model.addAttribute("origTestCenter", newAppointment.getBooking().getTestCenter());

		appointmentForm.setAccomodationFlag(
				customerAccommodationService.hasApprovedActiveAccommodations(
						appointmentForm.getProfile().getCustomer().getId(),
						DateHandler.getCurrentDate()));

		model.addAttribute("accommodationsChanged", bookingBusinessService.isAccommodationsChanged(oldBooking));
		model.addAttribute("deliveryMode", oldBooking.getTestVariation().getDeliveryModeType().getDescription());
		model.addAttribute("index", 0);

		return getRescheduleAppointmentView();
	}



	@RequestMapping(value = "/reschedule", method = RequestMethod.POST)
	public String processEidtedAppointmentInfo(HttpServletRequest request,
			@RequestParam("bookingId") Long bookingId, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors, SessionStatus status) {
		
		String MODEL_BOOKING = "booking";
		
		Booking newBooking = appointmentForm.getAppointments().get(0).getBooking();
		Booking oldBooking = appointmentForm.getAppointments().get(1).getBooking();
		
		AppointmentVO tempAppointment = appointmentForm.getAppointments().get(0);

		if(tempAppointment.getBooking().getTestVariation().getDeliveryModeType().getCode().equals(DeliveryModeType.CBT)){
			tempAppointment.setBaseForm(null);
			tempAppointment.getBooking().setForm(null);
		}
		try {
			if (!bookingBusinessService.processEditedAppointment(
					appointmentForm.getAppointments().get(0))) {
				errors.rejectValue("appointments[0].booking.form", "schedulenewappointment.subFormMissing");
			}
		} catch (SubFormSelectionNeededException e) {
			// TODO Deal with the case that multiple sub forms with accommodation returned.
		}

		appointmentValidator.validateAppointment(appointmentForm.getAppointments().get(0), errors);

		if (errors.hasErrors()) {
			return getRescheduleAppointmentView();
		} else {
			
			
			
			if(!oldBooking.getTestVariation().getId().getDeliveryModeCode().equals(newBooking.getTestVariation().getId().getDeliveryModeCode())
					|| !oldBooking.getTestVariation().getId().getLanguageCode().equals(newBooking.getTestVariation().getId().getLanguageCode())
					|| !(oldBooking.getAppointmentDateTime().getTime() == newBooking.getAppointmentDateTime().getTime())){

				Booking rescheduledBooking = bookingBusinessService.updateAppointment(
						appointmentForm.getAppointments(), appointmentForm.getProfile());
				bookingBusinessService.sendAppointmentRescheduledMail(
						appointmentForm.getAppointments().get(1).getBooking(),
						appointmentForm.getAppointments().get(0).getBooking(),
						appointmentForm.getProfile());
				model.addAttribute(MODEL_BOOKING, rescheduledBooking);
					
			}else{
				
				oldBooking.setForm(newBooking.getForm());
				oldBooking.setComments(newBooking.getComments());
				
				bookingBusinessService.saveBooking(oldBooking);
				model.addAttribute(MODEL_BOOKING, oldBooking);
			}
			
			status.setComplete();
 			return getAppointmentRescheduledView();
		}
	}

	/* cancel a Single Booking */
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String getCancelAppointmentView(HttpServletRequest request,
			@RequestParam("bookingId") Long bookingId, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			@ModelAttribute(TEST_CENTERS) List<TestCenter> testCenters,
			BindingResult errors, Principal principal) {
		Booking booking = bookingBusinessService.getBookingById(bookingId);
		if (!AppointmentUtil.getPrivilegeForApptUpdate(request, testCenters, booking)) {
			return getNoPrivilegeView();
		}
		ETSCustomer customer = (ETSCustomer) booking.getTestTakerTestId().getCustomer();
		appointmentForm.setProfile(new ProfileVO());
		appointmentForm.getProfile().setCustomer(customer);
		appointmentForm.getAppointments().get(0).setBooking(booking);
		return getCancelAppointmentView();
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancelAppointment(HttpServletRequest request,
			@RequestParam("bookingId") String bookingId, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors, SessionStatus status) {
		Booking booking = appointmentForm.getAppointments().get(0).getBooking();
		appointmentForm.setAppointmentNumber(booking.getEtsApptID());
		bookingBusinessService.cancelAppointment(booking);
		bookingBusinessService.sendAppointmentCanceledMail(
				booking, appointmentForm.getProfile());
		status.setComplete();

		return getAppointmentCancelledView();
	}

	@ModelAttribute(APPOINTMENT_FORM)
	public AppointmentForm initAppointmentForm() {
		AppointmentForm appointmentForm = new AppointmentForm();
		appointmentForm.setAppointments(new AutoPopulatingList<AppointmentVO>(AppointmentVO.class));
		appointmentForm.getAppointments().add(new AppointmentVO());
		return appointmentForm;
	}

	@RequestMapping(value = "/getAllAgencies", method = RequestMethod.GET)
	public String getAllAgencies(Model model) {
		model.addAttribute("agencies", bookingBusinessService.getAllAgencies(true));
		return getAgencyOptionsView();
	}

	@RequestMapping(value = "/getForms", method = RequestMethod.GET)
	public String getForms(
			@RequestParam("testId") Long testId,
			@RequestParam("testDate") String testDate,
			Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		
		Date dateTestDate = null;
		if (!testDate.isEmpty()) {
			try {
				dateTestDate = new SimpleDateFormat(DATEFORMAT_MMDDYYYY).parse(testDate);
			} catch (ParseException e) {
				log.debug("Error when parsing test date: {}", e.toString());
			}
		}
		
		List<Form> availableTestForms = bookingBusinessService.getAvailableTestForms(
				appointmentForm.getProfile().getCustomer().getId(), testId, dateTestDate);
		Collections.sort(availableTestForms, new Comparator<Form>() {
			@Override
			public int compare(Form o1, Form o2) {
				return o1.getFormID().compareTo(o2.getFormID());
			}
		});
		
		model.addAttribute("availableTestForms", availableTestForms);
		
		return getAppointmentData();
	}

	@RequestMapping(value = "/saveForm", method = RequestMethod.GET)
	public String saveForms(
		@RequestParam("bookingId") Long bookingId,
		@RequestParam("formId") Long parentFormId,
		Model model){
		
		Booking booking = bookingBusinessService.getBookingById(bookingId);
		
		/*Form childForm  = bookingBusinessService.getSubForm(parentFormId, 
							booking.getTestVariation().getTest().getTestId(), 
							booking.getTestVariation().getLanguageType().getCode(), 
							booking.getTestVariation().getDeliveryModeType().getCode());*/
		
		Form parentForm = new FormImpl();
		parentForm.setFormID(parentFormId);
		booking.setForm(parentForm);
		bookingBusinessService.saveBooking(booking);
		
		return getAppointmentData();
	}
	
	@RequestMapping(value = "/saveComment", method = RequestMethod.GET)
	public String saveComment(
		@RequestParam("bookingId") Long bookingId,
		@RequestParam("comment") String comment,
		Model model){
		
		Booking booking = bookingBusinessService.getBookingById(bookingId);
		booking.setComments(booking.getComments()+ '\n' + comment);
		bookingBusinessService.saveBooking(booking);
		
		return getAppointmentData();
	}


	
	@ModelAttribute("agencies")
	public List<Agency> getAgencies() {
		return bookingBusinessService.getAllAgencies(true);
	}

	@ModelAttribute(TEST_CENTERS)
	public List<TestCenter> getTestCenters(@LoggedInUser ERegUser user) {
		List<TestCenter> testCenters = new ArrayList<TestCenter>();
		if (!user.hasCSRRole() && user.hasTCARole()) {
			ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(user.getUsername());
			for (TestCenterAdmin admin : adminUser.getTestCenters()) {
				testCenters.add(admin.getTestCenter());
			}
		}
		return testCenters;
	}

	private String getDeliveryModeDescription(String deliveryMode) {
		return referenceEntityBusinessService.getEntityByPrimaryKey(
				DeliveryModeType.class, deliveryMode).getDescription();
	}

	@RequestMapping(value = "/new/info/getActiveAccommodations", method = RequestMethod.GET)
	public String getActiveAccommodations(
			@RequestParam("testId") Long testId,
			@RequestParam("testDate") String testDate,
			@RequestParam("deliveryMode") String deliveryMode,
			@RequestParam("index") int index, Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors) {
		bookingBusinessService.getActiveAccommodations(
				appointmentForm.getAppointments().get(index).getBooking(),
				appointmentForm.getProfile().getCustomer().getId(), testId, deliveryMode, testDate);
		model.addAttribute("deliveryMode", getDeliveryModeDescription(deliveryMode));
		model.addAttribute("index", index);
		return getActiveAccommodations();
	}

	@RequestMapping(value = "/new/info/getAppointmentData", method = RequestMethod.GET)
	public String getAppointmentData(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("index") int index,
			@RequestParam("testId") Long testId,
			@RequestParam("testDate") String testDate,
			@RequestParam("deliveryMode") String deliveryMode,
			@RequestParam("testCenterId") Long testCenterId,
			Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		model.addAttribute("languages", bookingBusinessService.getLanguageTypesByTest(testId));
		model.addAttribute("deliveryModes",	bookingBusinessService.getDeliveryModesByTestCenterTest(testCenterId, testId));
		model.addAttribute("index", index);

		String programCode = ProgramContextHolder.getProgramCode();  

		Date dateTestDate = null;
		if (!testDate.isEmpty()) {
			try {
				dateTestDate = new SimpleDateFormat(DATEFORMAT_MMDDYYYY).parse(testDate);
			} catch (ParseException e) {
				log.debug("Error when parsing test date: {}", e.toString());
			}
		}

		if (dateTestDate != null) {
			List<Form> availableTestForms = bookingBusinessService.getAvailableTestForms(
					appointmentForm.getProfile().getCustomer().getId(), testId, dateTestDate);
			Collections.sort(availableTestForms, new Comparator<Form>() {
				@Override
				public int compare(Form o1, Form o2) {
					return o1.getFormID().compareTo(o2.getFormID());
				}
			});
			model.addAttribute("availableTestForms", availableTestForms);

			if (!deliveryMode.isEmpty()) {
				List<CustomerAccommodationTest> activeAccommodations =
						customerAccommodationService.getAllActiveAccommodationsByDeliveryMode(
								appointmentForm.getProfile().getCustomer().getId(),
								programCode, testId, deliveryMode, dateTestDate);

				Booking booking = appointmentForm.getAppointments().get(index).getBooking();
				booking.setBookingAccommodations(bookingBusinessService.convertToBookingAccomodations(
						booking, activeAccommodations));

				model.addAttribute("deliveryMode", getDeliveryModeDescription(deliveryMode));
			}
		}

		return getAppointmentData();
	}

	@RequestMapping(value = "/cancelAppointment", method = RequestMethod.GET)
	public String cancelScheduling(
			HttpServletRequest request,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			SessionStatus status) {
		status.setComplete();
		String encodedCustomerId = generateBase64StringBusinessService.encodeBase64String(
				request.getUserPrincipal().getName() + Constant.ENCODING_DELIMITTER
				+ "?customerId=" + appointmentForm.getProfile().getCustomer().getId());
		return redirectTestTakerSummary() + Constant.URL_DECODE_IDENTIFIER + encodedCustomerId;
	}

	@RequestMapping(value= "/holdSeatByAdmin", method = RequestMethod.GET)
    @ResponseBody
    public boolean holdSeatByAdmin(HttpSession session,
            @RequestParam("holdSeatDate") String holdSeatDateString,
            @RequestParam("holdSeatTime") String holdSeatTimeString,
            @RequestParam("deliveryMode") String deliveryMode,
            @RequestParam("bookingIndex") int bookingIndex,
            @RequestParam("dateTimeIndex") int dateTimeIndex,
            @LoggedInUser ERegUser user) {
	    Date holdSeatDate = null;
        Date holdSeatTime = null;
        boolean holdSeat = false;
        DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT_YYYYMMDD);
        AppointmentForm appointmentForm = (AppointmentForm) session.getAttribute("appointmentForm");
        Long customerId = appointmentForm.getProfile().getCustomer().getId();
        try {
            if(DeliveryModeType.CBT.equalsIgnoreCase(deliveryMode)){
				return super.holdSeat(session, customerId, dateTimeIndex,
						bookingIndex, deliveryMode, appointmentForm, user);
            }
            holdSeatDate = dateFormat.parse(holdSeatDateString);
            dateFormat = new SimpleDateFormat(DATEFORMAT_YYYYMMDD_HHMMA);
            holdSeatTime  = dateFormat.parse(holdSeatDateString + " " + holdSeatTimeString);
            SortedSet<Seat> seats = getSeatsFromSession(session, holdSeatDate, "findSeatAdminPBTResponse");;
            Seat seat = getPBTSeat(seats, holdSeatTime);
            appointmentForm.getAppointments().get(bookingIndex).getBooking().setTestVariation(seat.getTestVariation());
            HeldSeat heldSeat = bookingBusinessService.holdSeat(customerId, seat);
            if(heldSeat == null){
                return false;
            }

            //List<AppointmentVO> appointments = appointmentForm.getAppointments();
            //bookingBusinessService.saveHeldBooking(appointments, customerId, bookingIndex, heldSeat);
            holdSeat = true;
         } catch (ParseException e) {
            log.error("Error parsing string date {} with error message {} ", holdSeatDate, e.getMessage());
         }
        return holdSeat;
    }

	private SortedSet<Seat> getSeatsFromSession(HttpSession session, Date holdSeatDate, String sessionKey) {
	    FindSeatResponse findSeatResponse = (FindSeatResponse) session.getAttribute(sessionKey);
	    SortedSet<Seat> seats = findSeatResponse.getSeats(holdSeatDate);
	    return seats;
    }

    private Seat getPBTSeat(SortedSet<Seat> seats, Date holdSeatTime) {
	    Seat seat = null;
	    Calendar localDateTimeCal = Calendar.getInstance();
        localDateTimeCal.setTime(holdSeatTime);
        for (Seat singleSeat : seats) {
            singleSeat.setLocalStartDateTime(localDateTimeCal);
            seat = singleSeat;
            break;
        }
        return seat;
    }

    //This method is used when the user leaves the scheduling flow to update their profile
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editProfile(
			HttpServletRequest request,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return super.editProfile(request, appointmentForm, redirectResumeScheduling());
	}

	//This method is used when the user comes back to the scheduling flow from updating their profile
	@RequestMapping(value = "/resumeappointment", method = RequestMethod.GET)
	public String resumeAppointment(
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return super.resumeAppointment(appointmentForm, getPersonalInfoView());
	}

}
