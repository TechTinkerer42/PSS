package org.ets.ereg.web.scheduling.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.web.controller.AbstractSelfServeSchedulingController;
import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/secure/appointment")
@SessionAttributes(AppointmentController.APPOINTMENT_FORM)
public class AppointmentController extends AbstractSelfServeSchedulingController {

    private static Logger log = LoggerFactory.getLogger(AppointmentController.class);

    public static final String APPOINTMENT_FORM = "appointmentForm";
	public static final String INDEX = "index";


    private String redirectResumeScheduling() {
        return "redirect:/secure/appointment/resumeappointment";
    }

    private String getHoldSeatResultView() {
		return "scheduling/holdSeatResult";
	}

	@Override
	@RequestMapping(value = "/new/info/removeAppointment", method = RequestMethod.GET)
	@ResponseBody
	public String removeAppointment(@RequestParam(INDEX) Integer index,
			@ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm) {
		return super.removeAppointment(index, appointmentForm);
	}

    @Override
    public void doCartProcessing(String cartName, List<AppointmentVO> appointmentVOs,Customer customer ) throws AddToCartException{
    	bookingBusinessService.addTests(appointmentVOs, customer);
    }

	//This method is used when the user leaves the scheduling flow to update their profile
    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public String editProfile(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
            BindingResult errors) {

        request.getSession().setAttribute(org.ets.ereg.common.web.util.Constant.AFTER_UPDATE_PROFILE_URL_SESSION_ATTR, redirectResumeScheduling());

        return "redirect:/secure/profile/";
    }

    //This method is used when the user comes back to the scheduling flow from updating their profile
    @RequestMapping(value = "/resumeappointment", method = RequestMethod.GET)
    public String resumeAppointment(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(APPOINTMENT_FORM) AppointmentForm appointmentForm,
            BindingResult errors) {
        appointmentForm.setProfile(profileBusinessService.readProfileByUsername(
                request.getUserPrincipal().getName()));
        return getPersonalInfoView();
    }

    @Override
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
    	super.initBinder(request, binder);
    }

}