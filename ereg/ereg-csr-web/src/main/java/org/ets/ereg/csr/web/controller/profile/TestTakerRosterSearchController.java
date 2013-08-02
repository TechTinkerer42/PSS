package org.ets.ereg.csr.web.controller.profile;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.ets.ereg.common.business.service.PaginationService;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.business.vo.CodeValuePairVo;
import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.util.DateTimeUtil;
import org.ets.ereg.csr.web.context.TestTakerRosterSearchContext;
import org.ets.ereg.csr.web.context.TestTakerSummaryContext;
import org.ets.ereg.csr.web.form.profile.TestTakerRosterSearchForm;
import org.ets.ereg.csr.web.form.scheduling.BookingForm;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.booking.EventOutComeStatus;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.profile.domain.vo.TestTakerRosterPaginationResultVO;
import org.ets.ereg.profile.domain.vo.TestTakerRosterSearchCriteriaVO;
import org.ets.ereg.profile.domain.vo.TestTakerRosterSearchResultVO;
import org.ets.ereg.scheduling.booking.service.BookingService;
import org.ets.ereg.scheduling.exception.SubFormNotFoundException;
import org.ets.ereg.scheduling.service.TestCenterService;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/secure/testTakerRosterSearch")
public class TestTakerRosterSearchController {

	private final static Logger logger = LoggerFactory.getLogger(TestTakerRosterSearchController.class);

	private final static String TEST_TAKER_ROSTER_SEARCH_VIEW = "search/testTakerRosterSearch";
	private final static String TEST_TAKER_ROSTER_SEARCH_RESULTS_VIEW = "/search/testTakerRosterSearchResults";

	@Autowired
	private MessageSource messageSource;

	@Resource(name = "testTakerRosterSearchService")
	private PaginationService<TestTakerRosterSearchCriteriaVO, TestTakerRosterPaginationResultVO> paginationService;

	@Resource(name = "bookingBusinessService")
	private BookingBusinessService bookingBusinessService;

	@Resource(name = "referenceEntityBusinessService")
	private ReferenceBusinessService referenceEntityBusinessService;

	@Resource(name = "testCenterService")
	private TestCenterService testCenterService;

	@Resource(name = "etsAdminUserBusinessService")
	private ETSAdminUserBusinessService etsAdminUserBusinessService;

	@Resource(name = "bookingService")
	private BookingService bookingService;

	@Resource(name = "referenceEntityService")
	private ReferenceService referenceService;

	@Resource(name = "generateBase64StringBusinessService")
	GenerateBase64StringBusinessService generateBase64StringBusinessService;


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	protected String searchTestTakerRoster(
			@ModelAttribute("testTakerRosterSearchForm") TestTakerRosterSearchForm testTakerRosterSearchForm,HttpServletRequest request, HttpServletResponse response) throws ERegCheckedException {
		
		HttpSession session = request.getSession();
		session.removeAttribute(TestTakerRosterSearchContext.TEST_TAKER_ROSTER_SEARCH_CRITERA);
		return TEST_TAKER_ROSTER_SEARCH_VIEW;
	}
	
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	protected String backTestTakerRoster(
			@ModelAttribute("testTakerRosterSearchForm") TestTakerRosterSearchForm testTakerRosterSearchForm,HttpServletRequest request, HttpServletResponse response) throws ERegCheckedException {
		
		TestTakerRosterSearchCriteriaVO testTakerRosterSearchCriteriaVo = null;
		HttpSession session = request.getSession();
		TestTakerRosterSearchContext searchContext = (TestTakerRosterSearchContext) session.getAttribute(TestTakerRosterSearchContext.TEST_TAKER_ROSTER_SEARCH_CRITERA);
		if(searchContext != null)	{	
			testTakerRosterSearchCriteriaVo = searchContext.getSearchCriteriaVO();
			if(testTakerRosterSearchCriteriaVo != null){
				copyProperties(testTakerRosterSearchCriteriaVo,testTakerRosterSearchForm);
				testTakerRosterSearchForm.setRowperPage(testTakerRosterSearchCriteriaVo.getRowsperPage());
				testTakerRosterSearchForm.setAction("backButton");
			}
		
		}
		//logger.info("testTakerRosterSearchCriteriaVo :"+testTakerRosterSearchCriteriaVo);
		return TEST_TAKER_ROSTER_SEARCH_VIEW;
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	protected String searchTestTakerRosterByCriteria(
			@ModelAttribute("testTakerRosterSearchForm") TestTakerRosterSearchForm testTakerRosterSearchForm,
			BindingResult errors, HttpServletRequest request, HttpServletResponse response,@LoggedInUser ERegUser loggedInUser) throws ERegCheckedException {
		request.setAttribute("generateBase64StringBusinessService", generateBase64StringBusinessService);
		HttpSession session = request.getSession();
		TestTakerRosterSearchCriteriaVO testTakerRosterSearchCriteriaVo = null;
		if ("criteriaUpdated".equals(testTakerRosterSearchForm.getAction())) {

			testTakerRosterSearchCriteriaVo = new TestTakerRosterSearchCriteriaVO();
			if (loggedInUser.hasTCARole()) {
				testTakerRosterSearchForm.setAdminId(getAdminId());
			}
			copyProperties(testTakerRosterSearchForm, testTakerRosterSearchCriteriaVo);
			TestTakerRosterSearchContext searchContext = new TestTakerRosterSearchContext();
			searchContext.setSearchCriteriaVO(testTakerRosterSearchCriteriaVo);

			session.setAttribute(TestTakerRosterSearchContext.TEST_TAKER_ROSTER_SEARCH_CRITERA, searchContext);

			TestTakerSummaryContext summaryContext = new TestTakerSummaryContext();
			summaryContext.setBackButtonLink("/secure/testTakerRosterSearch/back");
			session.setAttribute(TestTakerSummaryContext.TEST_TAKER_SUMMARY, summaryContext);
			
		

		} else if (testTakerRosterSearchCriteriaVo == null) {
			TestTakerRosterSearchContext searchContext = (TestTakerRosterSearchContext) session
					.getAttribute(TestTakerRosterSearchContext.TEST_TAKER_ROSTER_SEARCH_CRITERA);
			testTakerRosterSearchCriteriaVo = searchContext.getSearchCriteriaVO();
		}

		testTakerRosterSearchCriteriaVo.setPageNo(testTakerRosterSearchForm.getPageNo());
		testTakerRosterSearchCriteriaVo.setRowsperPage(testTakerRosterSearchForm.getRowperPage());

		invokePagination( request,testTakerRosterSearchCriteriaVo);
		
		return TEST_TAKER_ROSTER_SEARCH_RESULTS_VIEW;
	}
	
	
	
	private void invokePagination( HttpServletRequest request,TestTakerRosterSearchCriteriaVO testTakerRosterSearchCriteriaVo){
		TestTakerRosterPaginationResultVO testTakerRosterPaginationResultVO = paginationService
				.getDataList(testTakerRosterSearchCriteriaVo);
		List<TestTakerRosterSearchResultVO> listTestTakerRosterSearchResultVO = testTakerRosterPaginationResultVO
				.getResults();
		for (TestTakerRosterSearchResultVO testTakerRosterSearchResultVO : listTestTakerRosterSearchResultVO) {
			if ("Yes".equals(testTakerRosterSearchResultVO.getAccomodation())) {
				Booking booking = bookingService.getBookingById(testTakerRosterSearchResultVO.getBookingId()
						.longValue());
				Set<BookingAccommodation> setBookingAccommodation = booking.getBookingAccommodations();
				for (BookingAccommodation bookingAccommodation : setBookingAccommodation) {
					testTakerRosterSearchResultVO.getListAccomodationDesc().add(
							bookingAccommodation.getAccommodationType().getDescription());

				}
			}
		}

		request.setAttribute("testTakerRosterResultVO", testTakerRosterPaginationResultVO);

	}

	@RequestMapping(value = "/loadFormCode", method = RequestMethod.POST)
	protected @ResponseBody
	List<CodeValuePairVo> loadFormCode(
			@ModelAttribute("testTakerRosterSearchForm") TestTakerRosterSearchForm testTakerRosterSearchForm,
			BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws ERegCheckedException {

		List<Form> listForm = bookingBusinessService.getAvailableTestFormsByTestCode(testTakerRosterSearchForm
				.getTestId());
		List<CodeValuePairVo> listCodeValuePairVo = new ArrayList<CodeValuePairVo>();
		for (Form form : listForm) {
			CodeValuePairVo codeValuePairVo = new CodeValuePairVo(form.getFormID().toString(), form.getFormDesc());
			listCodeValuePairVo.add(codeValuePairVo);
		}

		return listCodeValuePairVo;

	}

	@RequestMapping(value = "/loadTestCenter", method = RequestMethod.POST)
	protected @ResponseBody
	List<CodeValuePairVo> loadCenterCode(
			@ModelAttribute("testTakerRosterSearchForm") TestTakerRosterSearchForm testTakerRosterSearchForm,
			BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws ERegCheckedException {
			List<CodeValuePairVo> listCodeValuePairVo = new ArrayList<CodeValuePairVo>();
			if(testTakerRosterSearchForm.getAgencyId() != null ){
			    List<TestCenter> listTestCenter = bookingBusinessService.getTestCentersByAgency(testTakerRosterSearchForm.getAgencyId());
					for (TestCenter testCenter : listTestCenter) {
						CodeValuePairVo codeValuePairVo = new CodeValuePairVo(
								testCenter.getId(), testCenter.getName());
						listCodeValuePairVo.add(codeValuePairVo);
					}
			}
			return listCodeValuePairVo;
	}


	private void copyProperties(TestTakerRosterSearchForm testTakerRosterSearchForm,
			TestTakerRosterSearchCriteriaVO testTakerRosterSearchCriteriaVo) throws ERegCheckedException {
		try {

			
			BeanUtils.copyProperties(testTakerRosterSearchCriteriaVo, testTakerRosterSearchForm);

		} catch (IllegalAccessException e) {
			throw new ERegCheckedException("Error while assembling", e);
		} catch (InvocationTargetException e) {
			throw new ERegCheckedException("Error while assembling", e);
		}

	}
	
	private void copyProperties(TestTakerRosterSearchCriteriaVO testTakerRosterSearchCriteriaVo,TestTakerRosterSearchForm testTakerRosterSearchForm
			) throws ERegCheckedException {
		try {

			
			BeanUtils.copyProperties(testTakerRosterSearchForm,testTakerRosterSearchCriteriaVo);

		} catch (IllegalAccessException e) {
			throw new ERegCheckedException("Error while assembling", e);
		} catch (InvocationTargetException e) {
			throw new ERegCheckedException("Error while assembling", e);
		}

	}

	@ModelAttribute("submitBookingForm")
	public BookingForm initBookingForm() {
		BookingForm bookingForm = new BookingForm();
		return bookingForm;
	}

	@RequestMapping(value = "/confirmStatus", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, String> confirmStatus(@ModelAttribute("submitBookingForm") BookingForm bookingForm)
			throws SubFormNotFoundException {
		Long bookingId = bookingForm.getBookingId();
		Booking booking = bookingService.getBookingById(bookingId);

		String eventOutComeCode = bookingForm.getEventOutComeStatusCd();
		EventOutComeStatus evntOutComeStatus = referenceService.getEntityByPrimaryKey(EventOutComeStatus.class,
				eventOutComeCode);
		booking.setEvntOutComeStatus(evntOutComeStatus);
		if ("CHI".equals(evntOutComeStatus.getCode()) && "PBT".equals(booking.getTestVariation().getDeliveryModeType().getCode())) {
			Long parentFormID = bookingForm.getParentBaseFormId();
			String langCode = bookingForm.getBaseFormLangCd();
			Form form = bookingService.getFormById(parentFormID);
			Long testId = form.getTest().getTestId();
			Set<BookingAccommodation> bookingAccomidations = booking.getBookingAccommodations();
			List<String> listOfAccomCd = new ArrayList<String>();
			for (BookingAccommodation bookAccom : bookingAccomidations) {
				String code = bookAccom.getAccommodationType().getCode();
				listOfAccomCd.add(code);
			}
			List<Form> subForms = bookingService.getSubForms(parentFormID, testId, langCode, "PBT", listOfAccomCd);
			if (!subForms.isEmpty()) {
				Form subForm = subForms.get(0);
				booking.setForm(subForm);
			}

		}
		Booking bkng = bookingService.saveBooking(booking);
		Map<String, String> result = new LinkedHashMap<String, String>();
		result.put("bookingId", Long.toString(bkng.getId()));
		result.put("statusDesc", bkng.getEvntOutComeStatus().getDescription());
		result.put("statusCd", bkng.getEvntOutComeStatus().getCode());

		return result;
	}

	@RequestMapping(value = "/chgStatusAppForm", method = RequestMethod.POST)
	protected String changeStatusOfApplicationForm(HttpServletRequest request,
			@ModelAttribute("submitBookingForm") BookingForm bookingForm, @LoggedInUser ERegUser loggedInUser) throws ParseException {

		Long bookingId = bookingForm.getBookingId();
		Booking booking = bookingService.getBookingById(bookingId);
		Date appDt = booking.getAppointmentDateTime();
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		today = DateTimeUtil.getDate(today);
		appDt = DateTimeUtil.getDate(appDt);
		if(request.getParameter("candidateDeliveryModeDesc").equals("Computer")) {
			request.setAttribute("formNotAvailableForCBTDeliveryFlag", true);
			return "search/changeAppStatusForm";
		}

		if (appDt.after(today)) {
			request.setAttribute("formNotAvailableFlag", true);
			return "search/changeAppStatusForm";
		} else if (appDt.before(today) && loggedInUser.hasTCARole()) {
			request.setAttribute("formNotAvailableBeforeDateFlag", true);
			return "search/changeAppStatusForm";
		}

		List<Form> listOfForms = bookingService.getAvailTestFormsIncCurrent(bookingId);
		List<LanguageType> langs = new ArrayList<LanguageType>();
		LanguageType eng = referenceService.getEntityByPrimaryKey(LanguageType.class, LanguageType.English);
		LanguageType spa = referenceService.getEntityByPrimaryKey(LanguageType.class, LanguageType.Spanish);
		langs.add(eng);
		langs.add(spa);
		bookingForm.setParentBaseFormId(booking.getForm().getParentFormID().getFormID());
		bookingForm.setBaseFormLangCd(booking.getTestVariation().getLanguageType().getCode());
		EventOutComeStatus eventOutComeSts = referenceService.getEntityByPrimaryKey(EventOutComeStatus.class, booking
				.getEvntOutComeStatus().getCode());
		bookingForm.setEventOutComeStatusCd(eventOutComeSts.getCode());
		// request.setAttribute("languageCode",
		// booking.getForm().getLangCode());

		request.setAttribute("TestType", booking.getTestVariation().getDeliveryModeType().getCode());
		request.setAttribute("languages", langs);

		request.setAttribute("listOfForms", listOfForms);
		request.setAttribute("formAvailableFlag", true);
		return "search/changeAppStatusForm";
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public List<String> validateSearchForm(TestTakerRosterSearchForm appointmentSearchForm) {
		List<String> errorList = new ArrayList<String>();

		return errorList;
	}

	@ModelAttribute("testCenterNames")
	public Map<Long, String> getTestCenterNames() {

		Map<Long, String> tcnMap = new HashMap<Long, String>();

		List<TestCenter> results = testCenterService.getAssociatedTestCentersForAdmin(getAdminId());

		for (TestCenter testCenter : results) {

			tcnMap.put(testCenter.getId(), testCenter.getName());
		}

		return tcnMap;

	}

	private Long getAdminId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		User princ = (User) principal;

		Long adminId = 0l;

		ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(princ.getUsername());
		adminId = adminUser.getId();

		return adminId;
	}

	@ModelAttribute("accomodations")
	public Map<String, String> getAccomodations() {
		Map<String, String> accMap = new HashMap<String, String>();
		accMap.put("Yes", "Yes");
		accMap.put("No", "No");

		return accMap;
	}

	@ModelAttribute("checkInStatus")
	public List<EventOutComeStatus> getCheckInTypes() {
		return referenceEntityBusinessService.getAll(EventOutComeStatus.class, new ReferenceTypeCriteria(), false);
	}

	@ModelAttribute("deliveryModeTypes")
	public List<DeliveryModeType> getDeliveryModeTypes() {
		/*
		 * return referenceEntityBusinessService.getAll(DeliveryModeType.class,
		 * new ReferenceTypeCriteria(), false);
		 */
		List<DeliveryModeType> listDeliveryModeType = new ArrayList<DeliveryModeType>();

		listDeliveryModeType.add(referenceEntityBusinessService.getEntityByPrimaryKey(DeliveryModeType.class,
				DeliveryModeType.PBT));
		listDeliveryModeType.add(referenceEntityBusinessService.getEntityByPrimaryKey(DeliveryModeType.class,
				DeliveryModeType.CBT));

		return listDeliveryModeType;
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

	@ModelAttribute("TestList")
	List<Test> getTests(String programCode) {
		List<Test> listTest = bookingBusinessService.getAllTests(ProgramContextHolder.getProgramCode());
		return listTest;
	}

	@ModelAttribute("agencies")
	public List<Agency> getAgencies() {
		List<Agency> agencies = bookingBusinessService.getAllAgencies(true);
		return agencies;
	}

}
