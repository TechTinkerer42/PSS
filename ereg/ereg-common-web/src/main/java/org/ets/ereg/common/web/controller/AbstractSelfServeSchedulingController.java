package org.ets.ereg.common.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.helpers.ThreadLocalFacade;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.common.util.ERegMessageResourceUtil;
import org.ets.ereg.common.vo.FindSeatInfo;
import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.ets.ereg.common.web.scheduling.validator.AppointmentFormValidator;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.ets.ereg.domain.interfaces.model.catalog.TestSku;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.scheduling.service.AgencyService;
import org.ets.ereg.scheduling.util.SchedulingTypeEnum;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
//TODO remove web side implementation
//TODO remove web side implementation

public abstract class AbstractSelfServeSchedulingController extends
		AbstractAppointmentController {

    @Resource(name = "eregMessageResource")
    private ERegMessageResourceUtil eregMessageResource;

    @Resource(name = "appointmentValidator")
    private AppointmentFormValidator appointmentFormValidator;

    @Resource(name = "etsBatterySubscriptionService")
    private ETSBatterySubscriptionService etsBatterySubscriptionService;

    @Resource(name = "agencyService")
    private AgencyService agencyService;
	//TODO check if protected can be converted into private after
	//all is moved.
    protected String getPersonalInfoView() {
        return "scheduling/personalInfo";
    }

    protected String redirectAppointmentInfoView() {
        return "redirect:/secure/appointment/new/info";
    }

    protected String getAppointmentInfoView() {
        return "scheduling/appointmentInfo";
    }

    private String getAppointmentReference() {
        return "scheduling/appointmentReferenceData";
    }

	private String getAddAnotherAppointmentPanel() {
		return "scheduling/appointmentInfoPanel";
	}

	private String getHoldSeatResultView() {
		return "scheduling/holdSeatResult";
	}

    private String redirectCartView() {
        return "redirect:/secure/cart/view";
    }

    @ModelAttribute(APPOINTMENT_FORM)
    public AppointmentForm initAppointmentForm() {
        AppointmentForm appointmentForm = new AppointmentForm();
        appointmentForm.setAppointments(new AutoPopulatingList<AppointmentVO>(AppointmentVO.class));
        appointmentForm.getAppointments().add(new AppointmentVO());
        return appointmentForm;
    }

    @RequestMapping(value= "/new", method = RequestMethod.GET)
    public String diplayPersonalInfoView(
            HttpServletRequest request,
            @RequestParam(value = "testCenterId") Long testCenterId,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {

        appointmentForm.setProfile(profileBusinessService.readProfileByUsername(
        		CustomerState.getCustomer().getUsername()));


        // Get test center address
        TestCenter testCenter = bookingBusinessService.getTestCenterById(testCenterId);
        appointmentForm.setTestCenter(testCenter);
        appointmentForm.setTestCenterAddress(bookingBusinessService.getTestCenterAddress(testCenter));
        // Skip the personal info step if the customer is newly registered
        Boolean isNewCustomer = (Boolean) request.getSession().getAttribute(Constant.IS_NEW_CUSTOMER);
        request.getSession().removeAttribute(Constant.IS_NEW_CUSTOMER);
        String returnString = isNewCustomer!=null && isNewCustomer ? redirectAppointmentInfoView() : getPersonalInfoView();

        return returnString;
    }

    @RequestMapping(value= "/findSeat", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, FindSeatInfo> findSeatForSpecificDate(HttpServletRequest request,
            @RequestParam("testId") Long testId,
            @RequestParam("testStartDate") String testStartDate,
            @RequestParam("testEndDate") String testEndDate,
            @RequestParam("deliveryMode") String deliveryMode,
            @RequestParam("languageCode") String languageCode,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm, @LoggedInUser ERegUser user) {

    	boolean isCSR = user.hasCSRRole() ? true : false;
        return super.findSeat(request, appointmentForm, testId, testStartDate, testEndDate, deliveryMode, CustomerState.getCustomer().getId(), languageCode, isCSR);
    }

    @RequestMapping(value= "/holdSeat", method = RequestMethod.GET)
    public String holdSeat(HttpSession session, Model model,
            @RequestParam(value = "dateTimeIndex") int dateTimeIndex,
            @RequestParam(value = "bookingIndex") int bookingIndex,
            @RequestParam(value = "deliveryMode") String deliveryMode,
            @RequestParam("testId") Long testId,
            @RequestParam("testDate") String testDate,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
            @LoggedInUser ERegUser user) {

    	boolean holdSeatSuccess = super.holdSeat(session, CustomerState.getCustomer().getId(),
				dateTimeIndex, bookingIndex, deliveryMode, appointmentForm, user);

    	if (holdSeatSuccess) {
    		Date dateTestDate = null;
    		if (appointmentForm.getAccomodationFlag()) {
	    		List<CustomerAccommodationTest> activeAccommodations = null;
				try {
					if (user.isCustomer()) {
						dateTestDate = new SimpleDateFormat("yyyyMMdd").parse(testDate);
						activeAccommodations = customerAccommodationService.getAllActiveAccommodationsByDeliveryMode(
							CustomerState.getCustomer().getId(),
			    			ThreadLocalFacade.getProgramCode(), testId, deliveryMode, dateTestDate);
					} else if (user.hasCSRRole()) {
						Seat seat = ((FindSeatResponse) session.getAttribute("findSeatResponse")).getSeatById(dateTimeIndex);
						activeAccommodations = seat.getCustomerAccommodations();
					}
					if (activeAccommodations != null && activeAccommodations.size() > 0) {
						model.addAttribute("accommodations", activeAccommodations);
					}
				} catch (ParseException e) {
					log.error(e.toString());
				}
    		}
    	}

    	model.addAttribute("holdSeatSuccess", holdSeatSuccess);
    	return getHoldSeatResultView();
    }


    @RequestMapping(value= "/new", method = RequestMethod.POST)
    public String processPersonalInfoView(
            HttpServletRequest request,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
        return redirectAppointmentInfoView();
    }

    @RequestMapping(value= "/new/info", method = RequestMethod.GET)
    public String diplayAppointmentInfoView(
            HttpServletRequest request, Model model,
            @RequestParam(value = "testCenterId", required = false) Long testCenterId,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
            @LoggedInUser ERegUser user) {

		getRemainingAppointments(appointmentForm.getAppointments());
		appointmentForm.setAppointmentCount(appointmentForm.getAppointments().size() - 1);

        // Get test center address (needed when request come from cart review page)
        if (testCenterId != null) {
        	TestCenter testCenter = bookingBusinessService.getTestCenterById(testCenterId);
            appointmentForm.setTestCenter(testCenter);
            appointmentForm.setTestCenterAddress(bookingBusinessService.getTestCenterAddress(testCenter));
        }

        if (!appointmentForm.getGetTestsFlag()) {
            appointmentForm.setTests(bookingBusinessService.getAvailableTests(
            		CustomerState.getCustomer().getId(), ThreadLocalFacade.getProgramCode()));
            appointmentForm.setGetTestsFlag(true);
        }

        appointmentForm.setAccomodationFlag(customerAccommodationService.hasApprovedActiveAccommodations(
        		CustomerState.getCustomer().getId(), DateHandler.getCurrentDate()));

        String role = null;
        if (user.hasCSRRole()) {
        	role = "csr";
        } else if (user.isCustomer()) {
        	role = "customer";
        }
        model.addAttribute("role", role);
        
        BatterySubscription batterySubscription =
        		etsBatterySubscriptionService.readOpenBatteryForCustomerAndAgency(
        				CustomerState.getCustomer(),
        				appointmentForm.getTestCenter().getAgency(ProgramContextHolder.getProgramCode()));

	    model.addAttribute("batteryRemainingTestTake", etsBatterySubscriptionService.batteryTestInfo(batterySubscription));
        
        model.addAttribute("batterySubscription",batterySubscription);


        return getAppointmentInfoView();
    }

    @RequestMapping(value= "/new/info/reference", method = RequestMethod.GET)
    public String getAppointmentReferenceData(
            HttpServletRequest request,
            @RequestParam("testId") Long testId,
			@RequestParam(INDEX) int index,
            Model model,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
        model.addAttribute("languages", bookingBusinessService.getLanguageTypesByTest(testId));

        model.addAttribute("deliveryModes", bookingBusinessService.getDeliveryModesByTestCenterTest(
        		appointmentForm.getTestCenter().getId(), testId));

		model.addAttribute(INDEX, index);

		return getAppointmentReference();
    }


	@RequestMapping(value = "/new/info/addAnotherAppointment", method = RequestMethod.GET)
	public String addAnotherAppointment(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(INDEX) Integer index,
			Model model,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
			BindingResult errors) {
		model.addAttribute(INDEX, index);
		return getAddAnotherAppointmentPanel();
	}


	@Override
    @RequestMapping(value = "/new/info/removeAppointment", method = RequestMethod.GET)
	@ResponseBody
	public String removeAppointment(@RequestParam(INDEX) Integer index,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return super.removeAppointment(index, appointmentForm);
	}


    public FindSeatResponse getFindSeatResponse(HttpSession session, TCFindSeatRequest findSeatrequest) {

        FindSeatResponse findSeatResponse = null;
        findSeatResponse = bookingBusinessService.getFindSeatResponse(findSeatrequest);
           if(session.getAttribute("findSeatResponse") == null){
               session.setAttribute("findSeatResponse", findSeatResponse);
           }

        return findSeatResponse;

    }

    @RequestMapping(value= "/new/info", method = RequestMethod.POST)
    public String processAppointmentInfoView(
            HttpServletRequest request,
            AppointmentForm appointmentForm,
            BindingResult errors,
            SessionStatus status,
            @LoggedInUser ERegUser user,
            HttpSession session) {

		getRemainingAppointments(appointmentForm.getAppointments());
		appointmentForm.setAppointmentCount(appointmentForm.getAppointments().size() - 1);

        appointmentFormValidator.validateAppointmentInfo(appointmentForm, errors);
        if (errors.hasErrors()) {
            return getAppointmentInfoView();
        } else {
            try {
            	session.setAttribute(Constant.CART_NAME, user.getUsername());
            	doCartProcessing(user.getUsername(), appointmentForm.getAppointments(),profileBusinessService.readProfileById(CustomerState.getCustomer().getId()).getCustomer());
            } catch (AddToCartException e) {
				log.error("error in add to cart", e);
			} finally {
				status.setComplete();
            }
            return redirectCartView();
        }

    }

    abstract public void doCartProcessing(String cartName, List<AppointmentVO> appointmentVOs,Customer customer ) throws AddToCartException;

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
			}else if (schedulingTypeCode.equalsIgnoreCase(SchedulingTypeEnum.TCA_MODEL.getCode())) {
				json = "{\"action\":\"redirectToTCA\"}";
			} else if (schedulingTypeCode.equalsIgnoreCase(SchedulingTypeEnum.CANDIDATE_MODEL.getCode())) {
				 json = "{\"action\":\"redirectToCM\"}";
			}
		}//TODO remove TCA will never come to this controller since can't do self serve
		/*else if (user.hasTCARole()) {
			json = "{\"action\":\"update\", \"testCenterName\": \"" + testCenter.getName() + "\", \"address\": \"" + address + "\"}";
		} */else {
			json = "{\"action\":\"error\"}";
		}
		return json;
	}

}
