package org.ets.ereg.csr.web.controller.appointment;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.web.controller.AbstractSelfServeSchedulingController;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/secure/appointment")
@SessionAttributes(AppointmentController.APPOINTMENT_FORM)
public class SelfServeSchedulingController extends AbstractSelfServeSchedulingController{

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(SelfServeSchedulingController.class);
	
	private String getBeforeScheduleView() {
		return "scheduling/beforeSchedule";
	}

	private String redirectPersonalInfoView() {
		return "redirect:/secure/appointment/new";
	}
	
	
	public static final String getTestCenterOptions() {
		return "scheduling/testCenterOptions";
	}
	
	@RequestMapping(value = "/beforeSchedule", method = RequestMethod.GET)
	public String displayBeforeSchedule(@RequestParam("testCenterId") Long testCenterId) {
		
		return getBeforeScheduleView();
	}
	

	@RequestMapping(value = "/beforeSchedule", method = RequestMethod.POST)
	public String onNextBeforeScheduleView(@RequestParam("testCenterId") Long testCenterId,@RequestParam("skipProfile") boolean skipProfile) {
		
		if(skipProfile== true){
			return redirectAppointmentInfoView();
		}else{
			return redirectPersonalInfoView()  + "?testCenterId=" + testCenterId;
		}
		
	}


    public void doCartProcessing(String cartName, List<AppointmentVO> appointmentVOs,Customer customer ) throws AddToCartException{
    	
    	bookingBusinessService.addTestsToNamedCart(cartName, appointmentVOs, customer);
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
	
	@RequestMapping(value = "/new/info/getTestCenters", method = RequestMethod.GET)
	public String getTestCentersByAgency(@RequestParam Long agencyId, Model model) {
		List<TestCenter> testCenters = super.getTestCentersByAgency(agencyId);
		model.addAttribute(TEST_CENTERS, testCenters);
		return getTestCenterOptions();
	}

}