package org.ets.ereg.web.profile.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.common.web.controller.BaseAccommodationController;
import org.ets.ereg.common.web.form.CustomerAccommodationsForm;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.profile.accommodation.vo.CustomerTestAccommodationsVO;
import org.ets.ereg.web.util.ETSWebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/secure/testtaker/accommodations")
public class AccommodationsController extends BaseAccommodationController  {
	private static Logger log = LoggerFactory
			.getLogger(AccommodationsController.class);
	public static final String VIEW_FORM_ATTRIBUTE = "viewAccommodationForm";
	private static final String RETURN_VIEW = "public/profile/viewTestTakerAccommodations";
	public static final String URL_MAPPING_VIEW = "/view";
	public static final String URL_MAPPING_BACK = "/back";
	public static final String ATTR_ACCMDTNS = "accommodations";
	public static final String URL_MAPPING_ACCMDTNS = "/secure/testtaker/accommodations";
	public static final String ATTR_VIEW_ACTN_URL = "view_action_url";

	@RequestMapping(value = URL_MAPPING_VIEW, method = RequestMethod.GET)
	public String viewTestTakerAccommodationGet(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
			BindingResult errors) {
		log.info("Method viewTestTakerAccommodationGet invoked");
		return getTestTakerAccommodations(request, model,
				viewAccommodationForm, errors);
	}

	@RequestMapping(value = URL_MAPPING_VIEW, method = RequestMethod.POST)
	public String viewTestTakerAccommodation(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
			BindingResult errors) {
		log.info("Method viewTestTakerAccommodation invoked");
		return getTestTakerAccommodations(request, model,
				viewAccommodationForm, errors);
	}

	private String getTestTakerAccommodations(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
			BindingResult errors) {
		if (errors.hasErrors()) {
			// ??
		}
		Customer customer = CustomerState.getCustomer();
		log.info("customer id : " + customer.getId());
		log.info("testtitle:" + viewAccommodationForm.getTestId());
		log.info("testype:" + viewAccommodationForm.getDeliveryMode());
		log.info("acc status:" + viewAccommodationForm.getAccommodationStatus());
		List<CustomerTestAccommodationsVO> accommodations =super.getAccommodations(customer.getId(),
						viewAccommodationForm.getProgramCode(),
						viewAccommodationForm.getTestId(),
						viewAccommodationForm.getDeliveryMode(),
						viewAccommodationForm.getAccommodationStatus());

		model.addAttribute(ATTR_ACCMDTNS, accommodations);
		model.addAttribute(ATTR_VIEW_ACTN_URL, URL_MAPPING_ACCMDTNS
				+ URL_MAPPING_VIEW);
		return RETURN_VIEW;
	}

	@RequestMapping(value = URL_MAPPING_BACK, method = RequestMethod.GET)
	public String navigateToHome(RedirectAttributes ra) {
		return ETSWebConstants.REDIRECT_HOME_PAGE;
	}

	@ModelAttribute("testTypes")
	public List<DeliveryModeType> getTestTypes() {
		return super.getAllTestTypes();
	}

	@ModelAttribute("tests")
	public List<Test> getTests() {
		return super.getAllTests();
	}

	@ModelAttribute("accommodationStatus")
	public AccommodationStatus[] getAccomodationStatus() {
		return super.getAllAccomodationStatus();
	}

}
