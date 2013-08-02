package org.ets.ereg.web.controller.home;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.service.ProgramService;
import org.ets.ereg.common.business.util.EregUtils;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.helpers.ThreadLocalFacade;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessService;
import org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService;
import org.ets.ereg.web.util.ETSWebConstants;
import org.ets.ereg.web.util.LoginUtil;
import org.ets.ereg.web.util.WebJspMappingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller("etsHomeController")
public class HomeController {

	private static Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@Resource(name = "etsCustomerService")
	private ETSCustomerService customerService;

	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;

	@Resource(name = "bookingBusinessService")
	private BookingBusinessService bookingBusinessService;

	@Resource(name="applicationConfigurationService")
	protected ApplicationConfigurationService applicationService;

	@Resource(name="profileDemographicQuestionService")
	protected ProfileDemographicQuestionService profileDemographicService;

	@Resource(name = "applicationConfigurationBusinessService")
	private ApplicationConfigurationBusinessService applicationConfigurationBusinessService;

	@Resource(name = "eregUtils")
	private EregUtils eregUtils;
	
    @Resource(name = "etsBatterySubscriptionService")
    private ETSBatterySubscriptionService etsBatterySubscriptionService;
    

	@Resource(name = "programService")
	private ProgramService programService;	    


	@RequestMapping(value="/secure/home/{tenantCode}",method=RequestMethod.GET)
	public String home(@PathVariable("tenantCode") String tenantCode, ModelMap model, HttpServletRequest request,Principal principal,
			@LoggedInUser ERegUser loggedInUser )  throws ServiceException {
	


		initCustomerContext(request, loggedInUser, tenantCode);
       	
		return home(model, request, principal, loggedInUser);
	}	
	
	private void initCustomerContext(HttpServletRequest request, ERegUser loggedInUser, String tenantCode) {
		Customer customer = customerService.readCustomerById(loggedInUser.getId());
		List<String> customerPrograms = customerService.getCustomerProgramInterests(customer.getId());
		LoginUtil.switchCustomerContext(request, tenantCode, customerPrograms);
       	if(! customerPrograms.contains(ThreadLocalFacade.getProgramCode())) {
			ProgramType programType = programService.getProgramByPrimaryKey(ThreadLocalFacade.getProgramCode());
			customerService.addCustomerProgramInterest((ETSCustomer)customer, programType);
       	}		
	}
	
	
	@RequestMapping(value="/secure/home",method=RequestMethod.GET)
	public String home(ModelMap model, HttpServletRequest request,Principal principal,
			@LoggedInUser ERegUser loggedInUser )  throws ServiceException {

		if(! LoginUtil.isCustomerContextInitialized(request) ) {
			initCustomerContext(request, loggedInUser, "mo");
		}
		
		 ProfileVO profile = profileBusinessService.readProfileById(loggedInUser.getId());
		 model.addAttribute("FName", loggedInUser.getFirstName());
		 model.addAttribute("customer", profile.getCustomer());
		 model.addAttribute("customer_address", profile.getAddress());
		 LOG.info("In the Home Controller");

		 
         List<BatterySubscription> batterySubscriptions = etsBatterySubscriptionService.readAllOpenBatteriesForCustomer(CustomerState.getCustomer());
         model.put("batterySubscriptions", batterySubscriptions);
         
		 if (null==loggedInUser.getFirstName()){
			 return ETSWebConstants.SIGIN;
		 }else if(!profileDemographicService.areProfileRequiredDQAnswered(loggedInUser.getId(), ProgramContextHolder.getProgramCode())){
			 LOG.debug("Mandatory BIq not answered");
			 return "redirect:/secure/profile/background/mandatory";
		 }else{
			 LOG.debug("customer.getId() {}",loggedInUser.getId());
			 LOG.debug("PGM ID {}", ProgramContextHolder.getProgramCode());
			 ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			 HttpSession session = attr.getRequest().getSession();
			 String requestedURL= (String)session.getAttribute(ETSWebConstants.TARGET_URL_ATTRIB);
			 LOG.info("The Taget URL is (If blank, secure/home would be called): {}", requestedURL);

			 String displayAppointmentCount="";

			 if (!StringUtils.isEmpty(requestedURL))
		 	 {
		 		return requestedURL;
		 	 }
		 	 else
		 	 {
				if(profileDemographicService.areProfileDQAnswered(loggedInUser.getId(), ProgramContextHolder.getProgramCode()))
				{
					   model.addAttribute("all_biq_answered", true);
				}
				else
				{
					   model.addAttribute("all_biq_answered", false);
				}

		 		displayAppointmentCount = applicationConfigurationBusinessService.findApplicationConfigurationValue(ETSWebConstants.HSET_DEFAULT_APPT_DISPLAY_COUNT);
				List<Booking> futureBookings = bookingBusinessService.getFutureBookingsForCustomer(loggedInUser.getId(), ProgramContextHolder.getProgramCode(), Integer.parseInt(displayAppointmentCount));

				model.addAttribute("futureBookings", futureBookings);
				CustomerLinkage custLink=customerService.getCustomerLinkage(loggedInUser.getId(), LinkageType.USER_DISP_ID);

				if(custLink!=null)
				{
					model.addAttribute("testTakerId", custLink.getLinkageKey());
				}

				if(eregUtils.isOAMAuthentication())
				{
					model.addAttribute("changeSecurityQuestion", applicationService.findApplicationConfigurationValue(ApplicationConfiguration.CHANGE_SEC_QUES_EREG));
					model.addAttribute("changePassword",applicationService.findApplicationConfigurationValue(ApplicationConfiguration.CHANGE_PASSWORD_EREG));
				}
				else
				{
					model.addAttribute("changeSecurityQuestion","/secure/profile/changeSecurityQuestion");
					model.addAttribute("changePassword","/secure/profile/changePassword");
				}
				return "forward:/pss/task/myPage";
//		 		return WebJspMappingConstants.HOME_PAGE;
		 	 }
		 }


	}


	private String getBeforeScheduleView() {
		return "scheduling/beforeSchedule";
	}

	private String redirectPersonalInfoView() {
		return "redirect:/secure/appointment/new";
	}

	@RequestMapping(value = "public/appointment/before", method = RequestMethod.GET)
	public String displayBeforeScheduleView(@RequestParam("testCenterId") Long testCenterId) {
		return getBeforeScheduleView();
	}

	@RequestMapping(value = "public/appointment/before", method = RequestMethod.POST)
	public String onNextBeforeScheduleView(@RequestParam("testCenterId") Long testCenterId) {
		return redirectPersonalInfoView() + "?testCenterId=" + testCenterId;
	}

}
