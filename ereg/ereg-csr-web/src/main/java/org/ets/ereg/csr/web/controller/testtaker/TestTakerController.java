package org.ets.ereg.csr.web.controller.testtaker;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.exception.ServiceException;
import org.ets.ereg.common.business.util.EregUtils;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.common.util.EIASExceptionMsgMapping;
import org.ets.ereg.csr.web.form.profile.ProfileForm;
import org.ets.ereg.csr.web.form.testtaker.biq.BIQForm;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.csr.web.validator.profile.ProfileFormValidator;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetType;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.external.service.eias.client.exception.EIASException;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.profile.service.ProfileService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.util.SchedulingTypeEnum;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessService;
import org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContext;

@Controller
@RequestMapping("/secure/testtaker")
public class TestTakerController{

	public static final String BIQ_FORM_ATTRIBUTE = "biqForm";
	
	public static final String PROFILE_FORM_ATTRIBUTE = "profileForm";
	
    private static Logger log = LoggerFactory.getLogger(TestTakerController.class);

    @Resource(name = "etsAdminUserBusinessService")
	private ETSAdminUserBusinessService etsAdminUserBusinessService;

    @Resource(name = "bookingBusinessService")
    private BookingBusinessService bookingBusinessService;

    @Resource(name = "profileBusinessService")
    private ProfileBusinessService profileBusinessService;

    @Resource(name = "profileService")
	private ProfileService profileService;

    @Resource(name = "generateBase64StringBusinessService")
    private GenerateBase64StringBusinessService generateBase64StringBusinessService;

    @Resource(name="profileDemographicQuestionService")
	protected ProfileDemographicQuestionService profileDemographicService;

    @Resource(name = "applicationConfigurationBusinessService")
    private ApplicationConfigurationBusinessService applicationConfigurationBusinessService;

    @Resource(name = "etsCustomerService")
	private ETSCustomerService customerService;
    
    @Resource(name="profileFormValidator")
    private ProfileFormValidator profileFormValidator;
    
    @Resource(name="eregUtils")
    private EregUtils eregUtil;

    private String getTestTakerSummaryView() {
        return "testTaker/testTakerSummary";
    }
    
    private String getTestTakerChangePasswordView() {
        return "testTaker/changePassword";
    }
    
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewProfile(HttpServletRequest request, Model model,
            @RequestParam("customerId") Long customerId, SessionStatus status, Principal principal) {
        boolean isCSR = true;
        String candidatePasswordChng ="";
        request.getSession().setAttribute("backUrl", request.getRequestURI());
        /*String username = request.getUserPrincipal().getName();
        String decodedCustomerId = generateBase64StringBusinessService.decodeBase64String(customerId);
        if(decodedCustomerId.contains(username)){
            Long customerIdLong = Long.parseLong(StringUtils.remove(decodedCustomerId, username+Constant.ENCODING_DELIMITTER));
        */    model.addAttribute("customerIdStr", customerId);
            ProfileVO profile = profileBusinessService.readProfileById(customerId);
            if(profile.getCustomer().getId().equals(customerId)){
                String firstName = profile.getCustomer().getFirstName();
                String lastName = profile.getCustomer().getLastName();
                model.addAttribute("firstName", firstName);
                model.addAttribute("lastName", lastName);
				CustomerLinkage custLink=customerService.getCustomerLinkage(profile.getCustomer().getId(), LinkageType.USER_DISP_ID);

				if(custLink!=null){
					model.addAttribute("testTakerId", custLink.getLinkageKey());
				}

                String displayAppointmentCount = applicationConfigurationBusinessService.findApplicationConfigurationValue(Constant.HSET_DEFAULT_APPT_DISPLAY_COUNT);
				List<Booking> futureBookings = bookingBusinessService.getFutureBookingsForCustomer(customerId, ProgramContextHolder.getProgramCode(), Integer.parseInt(displayAppointmentCount));
				log.debug("# of future bookings: {}", futureBookings.size());

				SecurityContextHolderAwareRequestWrapper wrapper = new SecurityContextHolderAwareRequestWrapper(request, "");
				List<TestCenter> testCenters = new ArrayList<TestCenter>();
				List<AppointmentVO> appointments = new ArrayList<AppointmentVO>();
				if (!wrapper.isUserInRole("ROLE_CUSTOMER_SERVICE_REP") &&
						wrapper.isUserInRole("ROLE_TEST_CENTER_ADMIN")) {
					ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(principal.getName());
					List<TestCenterAdmin> testCenterAdmins = adminUser.getTestCenters();
					for (TestCenterAdmin admin : testCenterAdmins) {
						testCenters.add(admin.getTestCenter());
					}
					isCSR = false;
				}

				for (Booking booking : futureBookings) {
					AppointmentVO appointment = new AppointmentVO();
					appointment.setBooking(booking);
					if (!isCSR && !testCenters.contains(booking.getTestCenter())) {
						appointment.setEditableFlag(false);
					}
					appointments.add(appointment);
				}
				model.addAttribute("appointments", appointments);

				candidatePasswordChng = "/secure/testtaker/changePassword";
                
                model.addAttribute("candidatePasswordChng", candidatePasswordChng);
                
                status.setComplete();
                
                //TODO move logic to some util calss and use final variable for scheduling model
                //and thsi logic to login filter and set session variable for canSchedule
                if(wrapper.isUserInRole(RoleEnum.ROLE_TEST_CENTER_ADMIN.getCode()) ){
                	if(!testCenters.isEmpty() && testCenters.get(0).
                			getSchedulingType(ProgramContextHolder.getProgramCode()).getCode().equals( SchedulingTypeEnum.TCA_MODEL.getCode())){
                				request.setAttribute("canScheduleAppointment", true);
                	}
                }else{
                	request.setAttribute("canScheduleAppointment", true);
                }
                
                return getTestTakerSummaryView();
            }
       // }

        return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;

    }

	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String onGetChangePassword(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute(TestTakerController.PROFILE_FORM_ATTRIBUTE) ProfileForm profileForm,
			@RequestParam("customerId") Long customerId,
			SessionStatus status,
			BindingResult errors
			){
			model.addAttribute("customerId", customerId);
			status.setComplete();
			return getTestTakerChangePasswordView();
	}

	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
	public String onPostChangePassword(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute(TestTakerController.PROFILE_FORM_ATTRIBUTE) ProfileForm profileForm,
			@RequestParam("customerId") Long customerId,
			BindingResult errors,
			final RedirectAttributes redirectAttributes) throws ServiceException 
			{
				ProfileVO profile = profileBusinessService.readProfileById(customerId);
				profileForm.setProfile(profile);
				model.addAttribute("customerId", customerId);
				log.debug("Cutsomer name is {}", profileForm.getProfile().getCustomer().getFirstName());
				if(eregUtil.isOAMAuthentication())
				{
					profileFormValidator.validateEIASPassword(profileForm, errors);
				}
				else 
				{
					profileFormValidator.validateNonEIASPassword(profileForm, errors);
				}
				if(errors.hasErrors())
				{
					return getTestTakerChangePasswordView();
				}
				else
				{
					profile.getCustomer().setPassword(profileForm.getPassword());
					try{
						profileBusinessService.changePassword(profile);
					}
					catch(EIASException e)
					{
						redirectAttributes.addFlashAttribute("STATUS_MESSAGE", EIASExceptionMsgMapping.get(e.getResponseCode()));
						return "redirect:/secure/testtaker/changePassword/?customerId="+customerId+"&encodable=false";
					}
					redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Password changed successfuly");
					return "redirect:/secure/testtaker/view/?customerId="+customerId+"&encodable=false";
				}
			}
    
 @RequestMapping(value="/viewBackground", method=RequestMethod.GET)
    public String viewBackground( Model model,HttpServletRequest request,
    		@RequestParam("customerId") Long customerId
            )
    {
    	List<DemographicQuestionVO> dQList=Collections.emptyList();
    	RequestContext requestContext = new RequestContext(request);
        model.addAttribute("customerIdStr", customerId);
        log.debug("customerIdLong:{}",customerId);
        dQList=profileDemographicService.getDemographicQuestions(customerId, ProgramContextHolder.getProgramCode(), requestContext.getLocale().getLanguage().toUpperCase(), DemographicQuestionSetType.BIQ_TYPE_PROFILE, true);
        model.addAttribute("biqs", dQList);

        return "profile/viewBackgroundInfo";
    }


 @RequestMapping(value="/viewUpdateBackground", method=RequestMethod.GET)
 public String viewUpdateBackground( HttpServletRequest request,
 		@RequestParam("customerId") Long customerId,
 		Model model,
 		@ModelAttribute(TestTakerController.BIQ_FORM_ATTRIBUTE) BIQForm biqForm,
 		BindingResult errors
         )
 {
         model.addAttribute("customerId", customerId);


	     return "profile/updateBackgroundInfo";



 }


    @RequestMapping(value = "/background/update", method = RequestMethod.POST)
    public String onPostChangeBackgroundInfo(
            HttpServletRequest request,
            @RequestParam("customerId") Long customerId,
            HttpServletResponse response,
            Model model,
            @ModelAttribute(TestTakerController.BIQ_FORM_ATTRIBUTE) BIQForm biqForm,
            BindingResult errors, final RedirectAttributes redirectAttributes)
            throws ServiceException {

        if (null != biqForm.getProfile().getDemographicQuestions()) {
            log.debug("answer size is:{}", biqForm.getProfile()
                    .getDemographicQuestions().size());
            for (DemographicQuestionVO dq : biqForm.getProfile()
                    .getDemographicQuestions()) {

                log.debug("answered {},answer is:{}", dq.isAnswered(),
                        dq.getFirstSelectedResponse());
            }
        } else {
            log.debug("(answer size is null");
        }

        log.debug("customerId:{}", customerId);
        try {

            profileService.saveBIQ(customerId, biqForm.getProfile()
                    .getDemographicQuestions());

        } catch (Exception e) {
        	log.error("exception occurred",e);
        }

        String username = request.getUserPrincipal().getName();
        String encodeUrl = generateBase64StringBusinessService
                .encodeBase64String(username + Constant.ENCODING_DELIMITTER
                        + "?customerId=" + customerId);
        return "redirect:/secure/testtaker/viewBackground/"
                + Constant.URL_DECODE_IDENTIFIER + encodeUrl;
    }

 @ModelAttribute(TestTakerController.BIQ_FORM_ATTRIBUTE)
 public BIQForm initBIQForm(HttpServletRequest request) {
	 String customerIdStr=request.getParameter("customerId");
	 log.debug("request.getParameter(customerId):{}",customerIdStr);
	 List<DemographicQuestionVO> dQList=Collections.emptyList();

	 if(!StringUtils.isEmpty(customerIdStr)){
	    /* String username = request.getUserPrincipal().getName();
         String decodedCustomerId = generateBase64StringBusinessService.decodeBase64String(customerIdStr);
         if(decodedCustomerId.contains(username)){
             Long customerIdLong = Long.parseLong(StringUtils.remove(decodedCustomerId, username+Constant.ENCODING_DELIMITTER));*/
		 Long customerIdLong = Long.parseLong(customerIdStr);
            RequestContext requestContext = new RequestContext(request);
             dQList=profileDemographicService.getDemographicQuestions(customerIdLong, ProgramContextHolder.getProgramCode(), requestContext.getLocale().getLanguage().toUpperCase(), DemographicQuestionSetType.BIQ_TYPE_PROFILE, true);
       //  }

	 }

		 BIQForm biqForm =new BIQForm();

		 ProfileVO profile=new ProfileVO();

				biqForm.setProfile(profile);
		biqForm.getProfile().setDemographicQuestions(dQList);




 	return biqForm;
 }


}