package org.ets.ereg.csr.web.controller.profile;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.StateService;
import org.ets.ereg.common.business.service.PaginationService;
import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.csr.web.context.CustomerSearchContext;
import org.ets.ereg.csr.web.context.TestTakerSummaryContext;
import org.ets.ereg.csr.web.form.profile.CustomerSearchForm;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.domain.vo.CustomerSearchCriteriaVO;
import org.ets.ereg.profile.domain.vo.CustomerSearchPaginationResultVO;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.ets.ereg.session.facade.profile.service.ETSCustomerBusinessService;
import org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/secure/customersearch")
public class CustomerSearchController {

	private final static Logger logger = LoggerFactory.getLogger(CustomerSearchController.class);

	private final String customerSearchView="search/candidateSearch";

	@Autowired
	private MessageSource messageSource;


    @Resource(name="applicationConfigurationBusinessService")
    protected ApplicationConfigurationBusinessService appConfigBussServ;

	@Resource(name = "blCountryService")
	private CountryService countryService;

	@Resource(name = "blStateService")
	private StateService stateService;

	@Resource(name="etsCustomerBusinessService")
	private ETSCustomerBusinessService etsCustomerBusinessService;

    @Resource(name="etsAdminUserBusinessService")
    private ETSAdminUserBusinessService etsAdminUserBusinessService;
    

	@Resource(name = "generateBase64StringBusinessService")
	GenerateBase64StringBusinessService generateBase64StringBusinessService;
    
    @Resource(name = "customerSearchService")
	private PaginationService<CustomerSearchCriteriaVO, CustomerSearchPaginationResultVO> paginationService;

	  @RequestMapping(value="/search", method = RequestMethod.GET )
	  protected String searchCustomer(@ModelAttribute("customerSearchForm") CustomerSearchForm customerSearchForm,HttpServletRequest request, HttpServletResponse response){
		  HttpSession session = request.getSession();
			session.removeAttribute(CustomerSearchContext.CUSTOMER_SEARCH_CRITERA);
		  return getCustomerSearchView();
	  }

	  
	  @RequestMapping(value="/back", method = RequestMethod.GET )
	  protected String backCustomer(@ModelAttribute("customerSearchForm") CustomerSearchForm customerSearchForm,HttpServletRequest request, HttpServletResponse response)throws ERegCheckedException{
		  CustomerSearchCriteriaVO custSearchCritVo = null;
		  HttpSession session = request.getSession();
		  CustomerSearchContext searchContext = (CustomerSearchContext) session.getAttribute(CustomerSearchContext.CUSTOMER_SEARCH_CRITERA);
		  custSearchCritVo = searchContext.getSearchCriteriaVO();
		  if(custSearchCritVo != null){
			  populateVOToForm(custSearchCritVo,customerSearchForm);
		  }
		 
		  return getCustomerSearchView();
	  }

	  public MessageSource getMessageSource() {
			return messageSource;
	  }

	  public void setMessageSource(MessageSource messageSource) {
			this.messageSource = messageSource;
	  }

	  @ModelAttribute("countries")
	public List<Country> getCountries()
	{
		return countryService.findCountries();
	}
	
	private List<String> blnValidateCandidateSearch(CustomerSearchForm customerSearchForm) {
		List<String> errorList= new ArrayList<String>();
		boolean customerCheck= StringUtils.isEmpty(customerSearchForm.getTestTakerId())
				  && customerSearchForm.getAppointmentNumber()==null
				  &&StringUtils.isEmpty(customerSearchForm.getFirstName())
				  &&StringUtils.isEmpty(customerSearchForm.getLastName())
				  &&StringUtils.isEmpty(customerSearchForm.getSsn())
				  &&customerSearchForm.getDateOfBirth()==null && StringUtils.isEmpty(customerSearchForm.getEmail());
		boolean addressCheck= StringUtils.isEmpty(customerSearchForm.getCountry())&&
                  StringUtils.isEmpty(customerSearchForm.getCity()) &&
                  StringUtils.isEmpty(customerSearchForm.getState()) &&
                  StringUtils.isEmpty(customerSearchForm.getPostalCode());
		boolean phoneCheck=StringUtils.isEmpty(customerSearchForm.getPhone());
		if(customerCheck && addressCheck && phoneCheck){
			if(!StringUtils.isEmpty(customerSearchForm.getMiddleInitial())){
				errorList.add("search.candidateSearch.validation.middleInitialSearchInvalid");
			}else{
				 errorList.add("search.candidateSearch.validation.emptyFields");
			 }
		 }
		return errorList;
		}  
	  
	private List<String> blnValidateAppointmentSearch(CustomerSearchForm customerSearchForm) {
		List<String> errorList= new ArrayList<String>();
		boolean appointmentSearchFormCheck = StringUtils.isNotEmpty(customerSearchForm.getLastName())
				  && (StringUtils.isNotEmpty(customerSearchForm.getTestTakerId()) || customerSearchForm.getAppointmentNumber()!=null);
		if(!appointmentSearchFormCheck) {
			  errorList.add("search.appointmentSearch.validation.emptyFields");
		}
		return errorList;
	}


	public List<String> validateSearchForm(CustomerSearchForm customerSearchForm){
		List<String> errorList= new ArrayList<String>();		  
		  if(customerSearchForm.getFormType().equalsIgnoreCase("appointmentSearch")) {			  
			  errorList = blnValidateAppointmentSearch(customerSearchForm);
		  }
	  
		  if(customerSearchForm.getFormType().equalsIgnoreCase("candidateSearch")) {  
			  errorList = blnValidateCandidateSearch(customerSearchForm);
         } 
			 return errorList;
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

	  @RequestMapping(value="/search", method = RequestMethod.POST)
	  protected String searchCustomerByCriteria(@ModelAttribute("customerSearchForm") CustomerSearchForm customerSearchForm,
	            BindingResult errors, HttpServletRequest request, HttpServletResponse response,@LoggedInUser ERegUser loggedInUser) throws ERegCheckedException {
			request.setAttribute("generateBase64StringBusinessService",	generateBase64StringBusinessService);
		  logger.debug("Birth Date:{} ",customerSearchForm.getDateOfBirth());
		  HttpSession session = request.getSession();
		  CustomerSearchCriteriaVO custSearchCritVo = null;
		  
		  List<String> validationErrors=validateSearchForm(customerSearchForm);
		  if(!validationErrors.isEmpty()){
			  request.setAttribute("errors", validationErrors);
			  return "validationErrors";
		  }

			if ("criteriaUpdated".equals(customerSearchForm.getAction())) {
				custSearchCritVo = new CustomerSearchCriteriaVO();
				populateFormToVO(customerSearchForm,custSearchCritVo,loggedInUser.hasTCARole());
				CustomerSearchContext searchContext = new CustomerSearchContext();
				searchContext.CustomerSearchCriteriaVO(custSearchCritVo);
				session.setAttribute(CustomerSearchContext.CUSTOMER_SEARCH_CRITERA,searchContext);
				TestTakerSummaryContext summaryContext = new TestTakerSummaryContext();
				summaryContext.setBackButtonLink("/secure/customersearch/back");
				session.setAttribute(TestTakerSummaryContext.TEST_TAKER_SUMMARY,summaryContext);
			}

			if (custSearchCritVo == null) {
				CustomerSearchContext searchContext = (CustomerSearchContext) session.getAttribute(CustomerSearchContext.CUSTOMER_SEARCH_CRITERA);
				custSearchCritVo = searchContext.getSearchCriteriaVO();
			}
			
			custSearchCritVo.setRowsperPage(customerSearchForm.getRowperPage());
			custSearchCritVo.setPageNo(customerSearchForm.getPageNo());
			

			CustomerSearchPaginationResultVO customerPaginationResultVO = paginationService.getDataList(custSearchCritVo);
			request.setAttribute("customerResultVO",customerPaginationResultVO);

			  
		  return "/search/candidateSearchResults";


	  }
	  
	  
	  private void populateFormToVO(CustomerSearchForm customerSearchForm,CustomerSearchCriteriaVO searchCriteriaVO,boolean hasTCARole){
		 
		  searchCriteriaVO.setCity(customerSearchForm.getCity());
		  searchCriteriaVO.setCountry(customerSearchForm.getCountry());
		  searchCriteriaVO.setDateOfBirth(customerSearchForm.getDateOfBirth());
		  searchCriteriaVO.setEmail(customerSearchForm.getEmail());
		  searchCriteriaVO.setFirstName(customerSearchForm.getFirstName());
		  searchCriteriaVO.setLastName(customerSearchForm.getLastName());
		  searchCriteriaVO.setMiddleInitial(customerSearchForm.getMiddleInitial());
		  searchCriteriaVO.setPhone(customerSearchForm.getPhone());
		  searchCriteriaVO.setPostalCode(customerSearchForm.getPostalCode());
		  searchCriteriaVO.setSsn(customerSearchForm.getSsn());
		  searchCriteriaVO.setState(customerSearchForm.getState());
		  searchCriteriaVO.setTestTakerId(customerSearchForm.getTestTakerId());
		  if(customerSearchForm.getAppointmentNumber()!=null && customerSearchForm.getAppointmentNumber()!=0L)
		  {
			  searchCriteriaVO.setAppointmentNumber(customerSearchForm.getAppointmentNumber().toString());
		  }
		  if(hasTCARole)
		  {
			  searchCriteriaVO.setAdminId(getAdminId());
		  }
	  }

	  private void populateVOToForm(CustomerSearchCriteriaVO searchCriteriaVO,CustomerSearchForm customerSearchForm){
			 
		  customerSearchForm.setCity(searchCriteriaVO.getCity());
		  customerSearchForm.setCountry(searchCriteriaVO.getCountry());
		  customerSearchForm.setDateOfBirth(searchCriteriaVO.getDateOfBirth());
		  customerSearchForm.setEmail(searchCriteriaVO.getEmail());
		  customerSearchForm.setFirstName(searchCriteriaVO.getFirstName());
		  customerSearchForm.setLastName(searchCriteriaVO.getLastName());
		  customerSearchForm.setMiddleInitial(searchCriteriaVO.getMiddleInitial());
		  customerSearchForm.setPhone(searchCriteriaVO.getPhone());
		  customerSearchForm.setPostalCode(searchCriteriaVO.getPostalCode());
		  customerSearchForm.setSsn(searchCriteriaVO.getSsn());
		  customerSearchForm.setState(searchCriteriaVO.getState());
		  customerSearchForm.setTestTakerId(searchCriteriaVO.getTestTakerId());
		  customerSearchForm.setRowperPage(searchCriteriaVO.getRowsperPage());
		  customerSearchForm.setAdminId(searchCriteriaVO.getAdminId());
		  customerSearchForm.setPageNo(searchCriteriaVO.getPageNo());
		  customerSearchForm.setAction("backButton");
	  }



	  @ModelAttribute("states")
	    public List<State> getStates()
	    {
	    	return stateService.findStates("US");
	    }
		public ETSCustomerBusinessService getEtsCustomerBusinessService() {
			return etsCustomerBusinessService;
		}
		public void setEtsCustomerBusinessService(
				ETSCustomerBusinessService etsCustomerBusinessService) {
			this.etsCustomerBusinessService = etsCustomerBusinessService;
		}
		public String getCustomerSearchView() {
			return customerSearchView;
		}






}
