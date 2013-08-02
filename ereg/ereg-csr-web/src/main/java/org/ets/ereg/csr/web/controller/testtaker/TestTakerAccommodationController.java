package org.ets.ereg.csr.web.controller.testtaker;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.common.business.service.AccommodationsService;
import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.common.web.controller.BaseAccommodationController;
import org.ets.ereg.common.web.form.CustomerAccommodationsForm;
import org.ets.ereg.common.web.form.CustomerAccomodationFormEntry;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.csr.web.validator.accommodation.AccommodationFormValidator;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.profile.accommodation.service.CustomerAccommodationService;
import org.ets.ereg.profile.accommodation.vo.AccommodationVO;
import org.ets.ereg.profile.accommodation.vo.CustomerAccommodationVO;
import org.ets.ereg.profile.accommodation.vo.CustomerDeliveryMethodAccommodationsVO;
import org.ets.ereg.profile.accommodation.vo.CustomerProgramAccommodationsVO;
import org.ets.ereg.profile.accommodation.vo.CustomerTestAccommodationsVO;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.booking.service.BookingService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContext;

@Controller
@RequestMapping("/secure/testtaker/accommodation")
public class TestTakerAccommodationController extends BaseAccommodationController{

	private static Logger log = LoggerFactory
			.getLogger(TestTakerAccommodationController.class);

	public static final String VIEW_FORM_ATTRIBUTE = "viewAccommodationForm";
	public static final String DEFAULT_ALL_TESTS = "All Tests";
	public static final String DEFAULT_ALL_TEST_TYPES = "All Test Types";
	public static final String DEFAULT_ALL_TEST_STATUS = "All Statuses";

	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;

	@Resource(name = "customerAccommodationService")
	private CustomerAccommodationService customerAccommodationService;

	@Resource(name = "accommodationsService")
	private AccommodationsService accommodationService;

	@Resource(name = "generateBase64StringBusinessService")
	private GenerateBase64StringBusinessService generateBase64StringBusinessService;

	@Resource(name = "bookingBusinessService")
	private BookingBusinessService bookingBusinessService;

	@Resource(name="bookingService")
	private BookingService bookingService;

	@Autowired
    private MessageSource messageSource;

	public static final String URL_MAPPING_ACCMDTNS="/secure/testtaker/accommodation/view/";

	public static final String ATTR_ACCMDTNS = "accommodations";
	public static final String ATTR_VIEW_ACTION = "view_action_url";
	public static final String ATTR_BACK_URL="backAction";
	public static final String ATTR_TST_TKR_HOME_URL="/secure/testtaker/view/";
	public static final String ATTR_FIRST_NAME = "firstName";
	public static final String ATTR_LAST_NAME = "lastName";
	public static final String ATTR_ENCODED_CUSTOMERID="encodeCustomerId";
	public static final String ATTR_DECODED_CUSTOMERID="customerId";
	public static final String ATTR_HAS_APPOINT_SCHEDULED_FOR_ACCOMM="has_appointment_scheduled";
	public static final String ATTR_TEST_ID="testId";
	public static final String ATTR_ACCOMMODATION_TYPE_CODE="accommodationTypeCode";
	public static final String ATTR_DELIVERY_METHOD_CODE="deliveryMethodCode";
	public static final String ATTR_STATUS_MSG ="operationMsg";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	static {
		DATE_FORMAT.setLenient(false);
	}

	@Resource(name = "accommodationValidator")
	private AccommodationFormValidator accommodationFormValidator;

	private String getAddTestTakerAccommodationsView() {
		return "testTaker/addTestTakerAccommodations";
	}

	private String getEditTestTakerAccommodationsView() {
		return "testTaker/editTestTakerAccommodations";
	}

	private String getTestTakerAccommodationView() {
		return "testTaker/viewTestTakerAccommodations";

	}

	private String getRemoveTestTakerAccommodationView() {
		return "testTaker/removeTestTakerAccommodations";
	}

	private String getPopUPAddTestTakerAccommodationView() {
		return "testTaker/selectAddTestTakerAccommodations";
	}

	private String getPopUPEditTestTakerAccommodationView() {
		return "testTaker/editTestTakerIndividualAccommodation";
	}

	private String getSingleEdit() {
		return "testTaker/editTestTakerIndividualAccommodation";
	}

	@Resource(name = "referenceEntityBusinessService")
	private ReferenceBusinessService referenceEntityBusinessService;

	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String viewTestTakerAccommodation(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(TestTakerAccommodationController.VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
			BindingResult errors) {

		log.debug("Message: {}",request.getParameter(ATTR_STATUS_MSG));
		// in get method the form has decoded customerId
		log.debug("in viewAcommodation:{}",viewAccommodationForm.getCustomerId());


			ProfileVO profile = profileBusinessService
					.readProfileById(viewAccommodationForm.getCustomerId());
			String firstName = profile.getCustomer().getFirstName();
			String lastName = profile.getCustomer().getLastName();


			List<CustomerTestAccommodationsVO> accommodations=super.getAccommodations(viewAccommodationForm.getCustomerId(),
					viewAccommodationForm.getProgramCode(),
					viewAccommodationForm.getTestId(),
					viewAccommodationForm.getDeliveryMode(),
					viewAccommodationForm.getAccommodationStatus());

			model.addAttribute(ATTR_STATUS_MSG,request.getParameter(ATTR_STATUS_MSG));
			model.addAttribute(ATTR_FIRST_NAME, firstName);
			model.addAttribute(ATTR_LAST_NAME, lastName);
			model.addAttribute(ATTR_ACCMDTNS, accommodations);
			model.addAttribute(ATTR_DECODED_CUSTOMERID,
					viewAccommodationForm.getCustomerId());
			model.addAttribute(ATTR_VIEW_ACTION, URL_MAPPING_ACCMDTNS);

			String encodeUrl = generateBase64StringBusinessService.encodeBase64String(request.getUserPrincipal().getName()+Constant.ENCODING_DELIMITTER + "?customerId=" + viewAccommodationForm.getCustomerId());
	        String  backAction=ATTR_TST_TKR_HOME_URL+ Constant.URL_DECODE_IDENTIFIER + encodeUrl;
			model.addAttribute(ATTR_BACK_URL, backAction);
			return getTestTakerAccommodationView();
	}

	@RequestMapping(value = "/view", method=RequestMethod.POST)
	public String viewTestTakerAccommodationPost(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(TestTakerAccommodationController.VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
			BindingResult errors) {



			log.debug("in viewAcommodation:{}",viewAccommodationForm.getCustomerId());

			ProfileVO profile = profileBusinessService
					.readProfileById(viewAccommodationForm.getCustomerId());
			String firstName = profile.getCustomer().getFirstName();
			String lastName = profile.getCustomer().getLastName();



			List<CustomerTestAccommodationsVO> accommodations=super.getAccommodations(viewAccommodationForm.getCustomerId(),
					viewAccommodationForm.getProgramCode(),
					viewAccommodationForm.getTestId(),
					viewAccommodationForm.getDeliveryMode(),
					viewAccommodationForm.getAccommodationStatus());

			model.addAttribute(ATTR_FIRST_NAME, firstName);
			model.addAttribute(ATTR_LAST_NAME, lastName);
			model.addAttribute(ATTR_DECODED_CUSTOMERID,
					viewAccommodationForm.getCustomerId());
			model.addAttribute(ATTR_ACCMDTNS, accommodations);

			model.addAttribute(ATTR_VIEW_ACTION, URL_MAPPING_ACCMDTNS);
			String encodeUrl = generateBase64StringBusinessService.encodeBase64String(request.getUserPrincipal().getName()+Constant.ENCODING_DELIMITTER + "?customerId=" + viewAccommodationForm.getCustomerId());
	        String  backAction=ATTR_TST_TKR_HOME_URL+ Constant.URL_DECODE_IDENTIFIER + encodeUrl;
			model.addAttribute(ATTR_BACK_URL, backAction);

			return getTestTakerAccommodationView();
	}


	@RequestMapping(value = "/remove/popUp", method = RequestMethod.GET)
	public String removeTestTakerAccommodation(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(TestTakerAccommodationController.VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
			BindingResult errors) {
		Long customerIdLong=viewAccommodationForm.getCustomerId();
	        ProfileVO profile = profileBusinessService
					.readProfileById(customerIdLong);
			String firstName = profile.getCustomer().getFirstName();
			String lastName = profile.getCustomer().getLastName();
			model.addAttribute(ATTR_FIRST_NAME, firstName);
			model.addAttribute(ATTR_LAST_NAME, lastName);

			model.addAttribute(ATTR_DECODED_CUSTOMERID, customerIdLong);
			model.addAttribute(ATTR_TEST_ID, viewAccommodationForm.getTestId());
			model.addAttribute(ATTR_ACCOMMODATION_TYPE_CODE, viewAccommodationForm.getAccommodationTypeCode());
			model.addAttribute(ATTR_DELIVERY_METHOD_CODE, viewAccommodationForm.getDeliveryMode());

			// Since coming from view screen Will always have a testfor the criteria
			CustomerAccommodationTest test = customerAccommodationService
					.getAccommodation(customerIdLong,ProgramContextHolder.getProgramCode(), viewAccommodationForm.getTestId(),
							viewAccommodationForm.getAccommodationTypeCode(), viewAccommodationForm.getDeliveryMode());

			// expiry date is mandatory so test.getexpiryDate will be not null
			List<Booking> bookings = bookingService.getBookingsWithAccommodation(customerIdLong, viewAccommodationForm.getAccommodationTypeCode(), DateHandler.getCurrentDate(), test.getExpiryDate());

			log.debug("Bookings for this accommodation {}",bookings.size());

			if(!bookings.isEmpty()){
				model.addAttribute(ATTR_HAS_APPOINT_SCHEDULED_FOR_ACCOMM,"true");
			}else{
				model.addAttribute(ATTR_HAS_APPOINT_SCHEDULED_FOR_ACCOMM,"false");
			}

			model.addAttribute("test", test);

			return getRemoveTestTakerAccommodationView();
	}

	@RequestMapping(value = "/add/popUp", method = RequestMethod.POST)
	public String addTestTakerAccommodation(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(value = TestTakerAccommodationController.VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
			BindingResult errors) {


		model.addAttribute(ATTR_DECODED_CUSTOMERID,viewAccommodationForm.getCustomerId());
		log.debug(" get customerId:{}", viewAccommodationForm.getCustomerId());


		return getPopUPAddTestTakerAccommodationView();

	}

	@RequestMapping(value = "/edit/popUp", method = RequestMethod.GET)
	public String modifyTestTakerAccommodation(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("accommodationsForm") CustomerAccommodationsForm customerAccommodationsForm,
			BindingResult errors) {

		log.debug(" in edit popUp customerAccommodationsForm.getCustomerId() {}",customerAccommodationsForm.getCustomerId());
		Long customerIdLong=customerAccommodationsForm.getCustomerId();

			ProfileVO profile = profileBusinessService
					.readProfileById(customerIdLong);
			String firstName = profile.getCustomer().getFirstName();
			String lastName = profile.getCustomer().getLastName();
			model.addAttribute(ATTR_FIRST_NAME, firstName);
			model.addAttribute(ATTR_LAST_NAME, lastName);

			model.addAttribute(ATTR_DECODED_CUSTOMERID, customerAccommodationsForm.getCustomerId());
			CustomerProgramAccommodationsVO customerProgramAccommodations = customerAccommodationService
					.getCustomerAccommodation(customerIdLong,
							ProgramContextHolder.getProgramCode(), customerAccommodationsForm.getTestId(),
							customerAccommodationsForm.getAccommodationTypeCode(), customerAccommodationsForm.getDeliveryMode());
			model.addAttribute("customerProgramAccommodations",
					customerProgramAccommodations);
			List<CustomerAccommodationTest> accommodations = customerAccommodationService
					.getAllAccommodations(Long.parseLong(customerAccommodationsForm.getCustomerId().toString()));
			Hashtable<String, AccommodationType> accommodationsTable = new Hashtable<String, AccommodationType>();
			for (CustomerAccommodationTest accommodation : accommodations) {
				if (null != accommodation.getAccommodationTypeValue()) {
					accommodationsTable
							.put(accommodation.getAccommodationTypeValue()
									.getAccommodationType().getCode(),
									accommodation.getAccommodationTypeValue()
											.getAccommodationType());
				}
			}
			List<CustomerAccomodationFormEntry> customerAccommodations = new ArrayList<CustomerAccomodationFormEntry>();
			for (CustomerTestAccommodationsVO customerTestAccommodations : customerProgramAccommodations
					.getCustomerTestAccommodations()) {
				for (CustomerDeliveryMethodAccommodationsVO customerDeliveryMethodAccommodations : customerTestAccommodations
						.getCustomerDeliveryMethodAccommodations()) {
					for (CustomerAccommodationVO customerAccommodation : customerDeliveryMethodAccommodations
							.getCustomerAccommodations()) {
						CustomerAccomodationFormEntry entry = new CustomerAccomodationFormEntry();
						entry.setAccommodation(accommodationsTable
								.get(customerAccommodation.getAccommodation()
										.getAccommodationTypeCode()));
						entry.setTestId(customerAccommodation.getTestId());
						entry.setDeliveryMethod(customerAccommodation
								.getDeliveryMethodCode());
						entry.setAccommodationTypeCode(customerAccommodation
								.getAccommodation().getAccommodationTypeCode());
						if (null != customerAccommodation.getAccommodation()
								.getValueId()) {
							entry.setAccommodationTypeValue(customerAccommodation
									.getAccommodation().getValueId().toString());
						} else if (null != customerAccommodation
								.getAccommodation().getOtherValueText()) {
							entry.setAccommodationTypeValue(customerAccommodation
									.getAccommodation().getOtherValueText());
						}
						entry.setApprovalDate(customerAccommodation
								.getApprovalDate());
						entry.setExpirationDate(customerAccommodation
								.getExpirationDate());
						entry.setOriginalExpirationDate(entry
								.getExpirationDate());
						entry.setOriginalAccommodationTypeValue(entry
								.getAccommodationTypeValue());
						entry.transfereDateToFields();
						customerAccommodations.add(entry);
					}
				}
			}
			customerAccommodationsForm
					.setAccommodations(customerAccommodations);

			customerAccommodationsForm.setBulkEdit(false);
			return getPopUPEditTestTakerAccommodationView();


	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String removeTestTakerAccommodationPost(
			HttpServletRequest request,
			Model model,
			@ModelAttribute(value = TestTakerAccommodationController.VIEW_FORM_ATTRIBUTE) CustomerAccommodationsForm viewAccommodationForm,
        	BindingResult errors) {
	        log.debug("before viewAccommodationForm.getCustomerId() {}",viewAccommodationForm.getCustomerId());
	        Long customerIdLong=viewAccommodationForm.getCustomerId();



    			 log.debug("customerIdLong {}",viewAccommodationForm.getCustomerId());
    			 log.debug("viewAccommodationForm.getTestId() {}",viewAccommodationForm.getTestId());
    			 log.debug(" viewAccommodationForm.getAccommodationTypeCode() {}", viewAccommodationForm.getAccommodationTypeCode());
    			 log.debug("viewAccommodationForm.getDeliveryMode() {}",viewAccommodationForm.getDeliveryMode());

		customerAccommodationService.deleteCustomerAccommodation(
				customerIdLong, ProgramContextHolder.getProgramCode(),
					viewAccommodationForm.getTestId(), viewAccommodationForm.getAccommodationTypeCode(), viewAccommodationForm.getDeliveryMode());
			RequestContext requestContext = new RequestContext(request);
    		model.addAttribute(ATTR_STATUS_MSG, messageSource.getMessage("accommodation.remove.success",null,requestContext.getLocale()))	;

    		String username = request.getUserPrincipal().getName();

			String encodeUrl = generateBase64StringBusinessService.encodeBase64String(username+Constant.ENCODING_DELIMITTER + "?customerId=" + viewAccommodationForm.getCustomerId()+"&"+ATTR_STATUS_MSG+"="+messageSource.getMessage("accommodation.remove.success",null,requestContext.getLocale()));
	        return "redirect:"+URL_MAPPING_ACCMDTNS+ Constant.URL_DECODE_IDENTIFIER + encodeUrl;

	}




	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String onGetTestTakerAccommodationAdd(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("accommodationsForm") CustomerAccommodationsForm customerAccommodationsForm) {
		if (null != customerAccommodationsForm.getTestId()
				&& customerAccommodationsForm.getTestId() > 0) {
			Test test = bookingBusinessService.getTestById(
					customerAccommodationsForm.getProgramCode(),
					customerAccommodationsForm.getTestId());
			model.addAttribute("testTitle", test.getTestName());
		} else {
			customerAccommodationsForm.setTestId(null);
			model.addAttribute("testTitle", DEFAULT_ALL_TESTS);
		}
		if (null != customerAccommodationsForm.getDeliveryMode()
				&& customerAccommodationsForm.getDeliveryMode().trim().length() > 0) {
			DeliveryModeType deliveryModeType = referenceEntityBusinessService
					.getEntityByPrimaryKey(DeliveryModeType.class,
							customerAccommodationsForm.getDeliveryMode());
			model.addAttribute("testType", deliveryModeType.getDescription());
		} else {
			customerAccommodationsForm.setDeliveryMode(null);
			model.addAttribute("testType", DEFAULT_ALL_TEST_TYPES);
		}
		if (null != customerAccommodationsForm.getAccommodationStatus()) {
			model.addAttribute("accommodationStatus",
					customerAccommodationsForm.getAccommodationStatus());
		} else {
			customerAccommodationsForm.setAccommodationStatus(null);
			model.addAttribute("accommodationStatus", DEFAULT_ALL_TEST_STATUS);
		}
		log.debug("reached add:{}" , customerAccommodationsForm.getCustomerId());

		customerAccommodationsForm
				.setAccommodationStatus(AccommodationStatus.ACTIVE);

		Long customerIdLong =customerAccommodationsForm.getCustomerId();
			ProfileVO profile = profileBusinessService
					.readProfileById(customerIdLong);
			String firstName = profile.getCustomer().getFirstName();
			String lastName = profile.getCustomer().getLastName();
			model.addAttribute(ATTR_FIRST_NAME, firstName);
			model.addAttribute(ATTR_LAST_NAME, lastName);
			CustomerProgramAccommodationsVO customerProgramAccommodations = customerAccommodationService
					.getAllAccommodations(customerIdLong,
							customerAccommodationsForm.getProgramCode(),
							customerAccommodationsForm.getTestId(),
							customerAccommodationsForm.getDeliveryMode(), AccommodationStatus.ACTIVE);
			Hashtable<String, CustomerAccommodationVO> accommodationsTable = new Hashtable<String, CustomerAccommodationVO>();
			for (CustomerTestAccommodationsVO customerTestAccommodations : customerProgramAccommodations
					.getCustomerTestAccommodations()) {
				for (CustomerDeliveryMethodAccommodationsVO customerDeliveryMethodAccommodations : customerTestAccommodations
						.getCustomerDeliveryMethodAccommodations()) {
					for (CustomerAccommodationVO customerAccommodation : customerDeliveryMethodAccommodations
							.getCustomerAccommodations()) {
						accommodationsTable.put(customerAccommodation
								.getAccommodation().getAccommodationTypeCode(),
								customerAccommodation);
					}
				}
			}
			List<AccommodationType> accommodations = accommodationService
					.getAllAccommodations(
							customerAccommodationsForm.getProgramCode(),
							customerAccommodationsForm.getDeliveryMode());
			model.addAttribute("accommodations", accommodations);
			List<CustomerAccomodationFormEntry> customerAccommodations = new ArrayList<CustomerAccomodationFormEntry>();
			for (AccommodationType accommodation : accommodations) {
				CustomerAccomodationFormEntry entry = new CustomerAccomodationFormEntry();
				CustomerAccommodationVO customerAccommodation = accommodationsTable
						.get(accommodation.getCode());
				if (null != customerAccommodation) {
					entry.setAccommodationTypeCode(customerAccommodation
							.getAccommodation().getAccommodationTypeCode());
					if (null != customerAccommodation.getAccommodation()
							.getValueId()) {
						entry.setAccommodationTypeValue(customerAccommodation
								.getAccommodation().getValueId().toString());
					} else if (null != customerAccommodation.getAccommodation()
							.getOtherValueText()) {
						entry.setAccommodationTypeValue(customerAccommodation
								.getAccommodation().getOtherValueText());
					}
					entry.setExpirationDate(customerAccommodation
							.getExpirationDate());
					entry.setOriginalExpirationDate(entry.getExpirationDate());
					entry.setOriginalAccommodationTypeValue(entry
							.getAccommodationTypeValue());
					entry.transfereDateToFields();
					entry.setExisting(true);
				}
				customerAccommodations.add(entry);
			}
			customerAccommodationsForm
					.setAccommodations(customerAccommodations);
			return getAddTestTakerAccommodationsView();

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String onPostTestTakerAccommodationAdd(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("accommodationsForm") CustomerAccommodationsForm customerAccommodationsForm,
			BindingResult errors) {

		Long customerIdLong =customerAccommodationsForm.getCustomerId();
		if (null != customerAccommodationsForm.getTestId()
				&& customerAccommodationsForm.getTestId() > 0) {
			Test test = bookingBusinessService.getTestById(
					customerAccommodationsForm.getProgramCode(),
					customerAccommodationsForm.getTestId());
			model.addAttribute("testTitle", test.getTestName());
		} else {
			customerAccommodationsForm.setTestId(null);
			model.addAttribute("testTitle", DEFAULT_ALL_TESTS);
		}
		if (null != customerAccommodationsForm.getDeliveryMode()
				&& customerAccommodationsForm.getDeliveryMode().trim().length() > 0) {
			DeliveryModeType deliveryModeType = referenceEntityBusinessService
					.getEntityByPrimaryKey(DeliveryModeType.class,
							customerAccommodationsForm.getDeliveryMode());
			model.addAttribute("testType", deliveryModeType.getDescription());
		} else {
			customerAccommodationsForm.setDeliveryMode(null);
			model.addAttribute("testType", DEFAULT_ALL_TEST_TYPES);
		}
		if (null != customerAccommodationsForm.getAccommodationStatus()) {
			model.addAttribute("accommodationStatus",
					customerAccommodationsForm.getAccommodationStatus());
		} else {
			customerAccommodationsForm.setAccommodationStatus(null);
			model.addAttribute("accommodationStatus", DEFAULT_ALL_TEST_STATUS);
		}

			List<AccommodationType> accommodations = accommodationService
					.getAllAccommodations(
							customerAccommodationsForm.getProgramCode(),
							customerAccommodationsForm.getDeliveryMode());

			model.addAttribute(ATTR_ACCMDTNS, accommodations);
			customerAccommodationsForm.transfereFromFormDateOfExpiration();
			Date currentDate = DateHandler.getCurrentDate();
			accommodationFormValidator.validateAddAccommodations(
					customerAccommodationsForm, currentDate, errors);
			if (errors.hasErrors()) {
				return getAddTestTakerAccommodationsView();
			} else {
				CustomerProgramAccommodationsVO customerProgramAccommodations = customerAccommodationService
						.getAllAccommodations(customerIdLong,
								customerAccommodationsForm.getProgramCode(),
								customerAccommodationsForm.getTestId(),
								customerAccommodationsForm.getDeliveryMode(),
								customerAccommodationsForm
										.getAccommodationStatus());
				Hashtable<String, CustomerAccommodationVO> accommodationsTable = new Hashtable<String, CustomerAccommodationVO>();
				for (CustomerTestAccommodationsVO customerTestAccommodations : customerProgramAccommodations
						.getCustomerTestAccommodations()) {
					for (CustomerDeliveryMethodAccommodationsVO customerDeliveryMethodAccommodations : customerTestAccommodations
							.getCustomerDeliveryMethodAccommodations()) {
						for (CustomerAccommodationVO customerAccommodation : customerDeliveryMethodAccommodations
								.getCustomerAccommodations()) {
							accommodationsTable.put(customerAccommodation
									.getAccommodation()
									.getAccommodationTypeCode(),
									customerAccommodation);
						}
					}
				}
				List<CustomerAccommodationVO> customerAccommodations = new ArrayList<CustomerAccommodationVO>();
				for (CustomerAccomodationFormEntry accommodation : customerAccommodationsForm
						.getAccommodations()) {
					if (null != accommodation.getAccommodationTypeCode()
							&& (!accommodationsTable.containsKey(accommodation
									.getAccommodationTypeCode()) || accommodation
									.getAccommodationTypeCode()
									.equalsIgnoreCase(
											AccommodationType.ACCOMMODATION_TYPE_CODE_OTHER))) {
						CustomerAccommodationVO customerAccommodationVO = new CustomerAccommodationVO();
						customerAccommodationVO.setCustomerId(customerIdLong);
						customerAccommodationVO
								.setProgramCode(customerAccommodationsForm
										.getProgramCode());
						customerAccommodationVO
								.setDeliveryMethodCode(customerAccommodationsForm
										.getDeliveryMode());
						customerAccommodationVO
								.setTestId(customerAccommodationsForm
										.getTestId());
						customerAccommodationVO.setExpirationDate(accommodation
								.getExpirationDate());
						customerAccommodationVO.setApprovalDate(currentDate);
						AccommodationVO accommodationVO = new AccommodationVO();
						accommodationVO.setAccommodationTypeCode(accommodation
								.getAccommodationTypeCode());
						if (null != accommodation.getAccommodationTypeValue()) {
							if (accommodation
									.getAccommodationTypeCode()
									.equalsIgnoreCase(
											AccommodationType.ACCOMMODATION_TYPE_CODE_OTHER)) {
								accommodationVO.setOtherValueText(accommodation
										.getAccommodationTypeValue());
							} else {
								accommodationVO.setValueId(Long
										.parseLong(accommodation
												.getAccommodationTypeValue()));
							}
						}
						customerAccommodationVO
								.setAccommodation(accommodationVO);
						customerAccommodations.add(customerAccommodationVO);
					}
				}
				customerAccommodationService
						.addAccommodations(customerAccommodations);
				RequestContext requestContext = new RequestContext(request);
	    		model.addAttribute(ATTR_STATUS_MSG, messageSource.getMessage("accommodation.add.success",null,requestContext.getLocale()))	;
	    		String encodeUrl = generateBase64StringBusinessService.encodeBase64String( request.getUserPrincipal().getName()+Constant.ENCODING_DELIMITTER + "?customerId=" + customerAccommodationsForm.getCustomerId()+"&"+ATTR_STATUS_MSG+"="+messageSource.getMessage("accommodation.add.success",null,requestContext.getLocale()));
		        return "redirect:"+URL_MAPPING_ACCMDTNS+ Constant.URL_DECODE_IDENTIFIER + encodeUrl;

			}

	}

	@RequestMapping(value = "/viewBulkEdit", method = RequestMethod.POST)
	public String onGetTestTakerAccommodationEdit(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("accommodationsForm") CustomerAccommodationsForm customerAccommodationsForm) {
		Long customerIdLong=customerAccommodationsForm.getCustomerId();

		if (null != customerAccommodationsForm.getTestId()
				&& customerAccommodationsForm.getTestId() > 0) {
			Test test = bookingBusinessService.getTestById(
					customerAccommodationsForm.getProgramCode(),
					customerAccommodationsForm.getTestId());
			model.addAttribute("testTitle", test.getTestName());
		} else {
			customerAccommodationsForm.setTestId(null);
			model.addAttribute("testTitle", "All Tests");
		}
		if (null != customerAccommodationsForm.getDeliveryMode()
				&& customerAccommodationsForm.getDeliveryMode().trim().length() > 0) {
			DeliveryModeType deliveryModeType = referenceEntityBusinessService
					.getEntityByPrimaryKey(DeliveryModeType.class,
							customerAccommodationsForm.getDeliveryMode());
			model.addAttribute("testType", deliveryModeType.getDescription());
		} else {
			customerAccommodationsForm.setDeliveryMode(null);
			model.addAttribute("testType", DEFAULT_ALL_TEST_TYPES);
		}
		if (null != customerAccommodationsForm.getAccommodationStatus()) {
			model.addAttribute("accommodationStatus",
					customerAccommodationsForm.getAccommodationStatus().getLabel());
		} else {
			customerAccommodationsForm.setAccommodationStatus(null);
			model.addAttribute("accommodationStatus", DEFAULT_ALL_TEST_STATUS);
		}

			ProfileVO profile = profileBusinessService
					.readProfileById(customerIdLong);
			String firstName = profile.getCustomer().getFirstName();
			String lastName = profile.getCustomer().getLastName();
			model.addAttribute(ATTR_FIRST_NAME, firstName);
			model.addAttribute(ATTR_LAST_NAME, lastName);
			CustomerProgramAccommodationsVO customerProgramAccommodations = customerAccommodationService
					.getAllAccommodations(customerIdLong,
							customerAccommodationsForm.getProgramCode(),
							customerAccommodationsForm.getTestId(),
							customerAccommodationsForm.getDeliveryMode(),
							customerAccommodationsForm.getAccommodationStatus());
			model.addAttribute("customerProgramAccommodations",
					customerProgramAccommodations);
			List<CustomerAccommodationTest> accommodations = customerAccommodationService
					.getAllAccommodations(customerIdLong);
			Hashtable<String, AccommodationType> accommodationsTable = new Hashtable<String, AccommodationType>();
			for (CustomerAccommodationTest accommodation : accommodations) {
				if (null != accommodation.getAccommodationTypeValue()) {
					accommodationsTable
							.put(accommodation.getAccommodationTypeValue()
									.getAccommodationType().getCode(),
									accommodation.getAccommodationTypeValue()
											.getAccommodationType());
				}
			}
			List<CustomerAccomodationFormEntry> customerAccommodations = new ArrayList<CustomerAccomodationFormEntry>();
			for (CustomerTestAccommodationsVO customerTestAccommodations : customerProgramAccommodations
					.getCustomerTestAccommodations()) {
				for (CustomerDeliveryMethodAccommodationsVO customerDeliveryMethodAccommodations : customerTestAccommodations
						.getCustomerDeliveryMethodAccommodations()) {
					for (CustomerAccommodationVO customerAccommodation : customerDeliveryMethodAccommodations
							.getCustomerAccommodations()) {
						CustomerAccomodationFormEntry entry = new CustomerAccomodationFormEntry();
						entry.setAccommodation(accommodationsTable
								.get(customerAccommodation.getAccommodation()
										.getAccommodationTypeCode()));
						entry.setTestId(customerAccommodation.getTestId());
						entry.setDeliveryMethod(customerAccommodation
								.getDeliveryMethodCode());
						entry.setAccommodationTypeCode(customerAccommodation
								.getAccommodation().getAccommodationTypeCode());
						if (null != customerAccommodation.getAccommodation()
								.getValueId()) {
							entry.setAccommodationTypeValue(customerAccommodation
									.getAccommodation().getValueId().toString());
						} else if (null != customerAccommodation
								.getAccommodation().getOtherValueText()) {
							entry.setAccommodationTypeValue(customerAccommodation
									.getAccommodation().getOtherValueText());
						}
						entry.setApprovalDate(customerAccommodation
								.getApprovalDate());
						entry.setExpirationDate(customerAccommodation
								.getExpirationDate());
						entry.setOriginalExpirationDate(entry
								.getExpirationDate());
						entry.setOriginalAccommodationTypeValue(entry
								.getAccommodationTypeValue());
						entry.transfereDateToFields();
						customerAccommodations.add(entry);
					}
				}
			}
			customerAccommodationsForm
					.setAccommodations(customerAccommodations);
			customerAccommodationsForm.setBulkEdit(true);
			return getEditTestTakerAccommodationsView();

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String onPostTestTakerAccommodationEdit(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("accommodationsForm") CustomerAccommodationsForm customerAccommodationsForm,
			BindingResult errors) {
		Long customerIdLong=customerAccommodationsForm.getCustomerId();
		log.debug("In edit post");
		if (null != customerAccommodationsForm.getTestId()
				&& customerAccommodationsForm.getTestId() > 0) {
			Test test = bookingBusinessService.getTestById(
					customerAccommodationsForm.getProgramCode(),
					customerAccommodationsForm.getTestId());
			model.addAttribute("testTitle", test.getTestName());
		} else {
			customerAccommodationsForm.setTestId(null);
			model.addAttribute("testTitle", DEFAULT_ALL_TESTS);
		}
		if (null != customerAccommodationsForm.getDeliveryMode()
				&& customerAccommodationsForm.getDeliveryMode().trim().length() > 0) {
			DeliveryModeType deliveryModeType = referenceEntityBusinessService
					.getEntityByPrimaryKey(DeliveryModeType.class,
							customerAccommodationsForm.getDeliveryMode());
			model.addAttribute("testType", deliveryModeType.getDescription());
		} else {
			customerAccommodationsForm.setDeliveryMode(null);
			model.addAttribute("testType", DEFAULT_ALL_TEST_TYPES);
		}
		if (null != customerAccommodationsForm.getAccommodationStatus()) {
			model.addAttribute("accommodationStatus",
					customerAccommodationsForm.getAccommodationStatus().getLabel());
		} else {
			customerAccommodationsForm.setAccommodationStatus(null);
			model.addAttribute("accommodationStatus", DEFAULT_ALL_TEST_STATUS);
		}


			CustomerProgramAccommodationsVO customerProgramAccommodations = customerAccommodationService
					.getAllAccommodations(customerIdLong,
							customerAccommodationsForm.getProgramCode(),
							customerAccommodationsForm.getTestId(),
							customerAccommodationsForm.getDeliveryMode(),
							customerAccommodationsForm.getAccommodationStatus());
			model.addAttribute("customerProgramAccommodations",
					customerProgramAccommodations);
			Date currentDate = DateHandler.getCurrentDate();
			customerAccommodationsForm.transfereFromFormDateOfExpiration();
			accommodationFormValidator.validateUpdateAccommodations(
					customerAccommodationsForm, currentDate, errors);
			if (errors.hasErrors()) {
				if (customerAccommodationsForm.isBulkEdit()) {
					List<CustomerAccommodationTest> accommodations = customerAccommodationService
							.getAllAccommodations(customerIdLong);
					Hashtable<String, AccommodationType> accommodationsTable = new Hashtable<String, AccommodationType>();
					for (CustomerAccommodationTest accommodation : accommodations) {
						if (null != accommodation.getAccommodationTypeValue()) {
							accommodationsTable.put(accommodation
									.getAccommodationTypeValue()
									.getAccommodationType().getCode(),
									accommodation.getAccommodationTypeValue()
											.getAccommodationType());
						}
					}
					for (CustomerAccomodationFormEntry accommodation : customerAccommodationsForm
							.getAccommodations()) {
						accommodation.setAccommodation(accommodationsTable
								.get(accommodation.getAccommodationTypeCode()));
					}
					return getEditTestTakerAccommodationsView();
				} else {
					return getSingleEdit();
				}
			} else {
				List<CustomerAccommodationVO> customerAccommodations = new ArrayList<CustomerAccommodationVO>();
				for (CustomerAccomodationFormEntry accommodationEntry : customerAccommodationsForm
						.getAccommodations()) {
					if (null != accommodationEntry.getAccommodationTypeCode()) {

						boolean changed = false;
						if (null == accommodationEntry
								.getOriginalAccommodationTypeValue()
								&& null == accommodationEntry
										.getAccommodationTypeValue()) {
							changed = false;
						} else if (null == accommodationEntry
								.getOriginalAccommodationTypeValue()
								|| null == accommodationEntry
										.getAccommodationTypeValue()) {
							changed = true;
						} else {
							changed = !accommodationEntry
									.getOriginalAccommodationTypeValue()
									.equals(accommodationEntry
											.getAccommodationTypeValue().trim());
						}

						changed = changed
								|| (accommodationEntry
										.getOriginalExpirationDate().compareTo(
												accommodationEntry
														.getExpirationDate()) != 0);

						if (changed) {
							CustomerAccommodationVO customerAccommodationVO = new CustomerAccommodationVO();
							customerAccommodationVO
									.setCustomerId(customerIdLong);
							customerAccommodationVO
									.setProgramCode(customerAccommodationsForm
											.getProgramCode());
							customerAccommodationVO
									.setDeliveryMethodCode(accommodationEntry
											.getDeliveryMethod());
							customerAccommodationVO
									.setTestId(accommodationEntry
											.getTestId());
							customerAccommodationVO
									.setExpirationDate(accommodationEntry
											.getExpirationDate());
							customerAccommodationVO
									.setApprovalDate(accommodationEntry.getApprovalDate());
							AccommodationVO accommodationVO = new AccommodationVO();
							accommodationVO
									.setAccommodationTypeCode(accommodationEntry
											.getAccommodationTypeCode());
							if (null != accommodationEntry
									.getAccommodationTypeValue()
									&& accommodationEntry
											.getAccommodationTypeValue().trim()
											.length() > 0) {
								if (accommodationEntry
										.getAccommodationTypeCode()
										.equalsIgnoreCase(
												AccommodationType.ACCOMMODATION_TYPE_CODE_OTHER)) {
									accommodationVO
											.setOtherValueText(accommodationEntry
													.getAccommodationTypeValue());
								} else {
									accommodationVO
											.setValueId(Long
													.parseLong(accommodationEntry
															.getAccommodationTypeValue()));
								}
							}
							customerAccommodationVO
									.setAccommodation(accommodationVO);
							customerAccommodations.add(customerAccommodationVO);
						}
					}
				}
				customerAccommodationService
						.updateAccommodations(customerAccommodations);

				RequestContext requestContext = new RequestContext(request);
	    		model.addAttribute(ATTR_STATUS_MSG, messageSource.getMessage("accommodation.edit.success",null,requestContext.getLocale()))	;

				String encodeUrl = generateBase64StringBusinessService.encodeBase64String(request.getUserPrincipal().getName()+Constant.ENCODING_DELIMITTER +"?customerId="+ customerIdLong+"&"+ATTR_STATUS_MSG+"="+messageSource.getMessage("accommodation.edit.success",null,requestContext.getLocale()));

				return "redirect:"+URL_MAPPING_ACCMDTNS+ Constant.URL_DECODE_IDENTIFIER + encodeUrl;
			}

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

	@ModelAttribute("years")
	public List<String> getYears() {

		List<String> years = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateHandler.getCurrentDate());
		int year = cal.get(Calendar.YEAR);
		for (int i = 0; i < 20; i++) {
			years.add(String.valueOf(year++));
		}
		return years;
	}

	@ModelAttribute("months")
	public List<String> getMonths() {
		List<String> months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		return months;
	}

	@ModelAttribute("days")
	public List<String> getDays() {
		List<String> days = new ArrayList<String>();
		days.add("01");
		days.add("02");
		days.add("03");
		days.add("04");
		days.add("05");
		days.add("06");
		days.add("07");
		days.add("08");
		days.add("09");
		days.add("10");
		days.add("11");
		days.add("12");
		days.add("13");
		days.add("14");
		days.add("15");
		days.add("16");
		days.add("17");
		days.add("18");
		days.add("19");
		days.add("20");
		days.add("21");
		days.add("22");
		days.add("23");
		days.add("24");
		days.add("25");
		days.add("26");
		days.add("27");
		days.add("28");
		days.add("29");
		days.add("30");
		days.add("31");
		return days;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {

		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				Date value = null;
				try {
					value = DATE_FORMAT.parse(text);
				} catch (Exception e) {
					// TODO log error
				}
				setValue(value);
			}

			@Override
            public String getAsText() {
				Date dateOfBirth = (Date) this.getValue();
				return (null == dateOfBirth) ? "" : DATE_FORMAT
						.format(dateOfBirth);
			}
		});


	}
}
