package org.ets.ereg.web.profile.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.broadleafcommerce.profile.web.core.service.LoginService;
import org.codehaus.plexus.util.StringUtils;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.EregUtils;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetType;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.EthnicityType;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.session.facade.profile.service.ETSCustomerBusinessService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.shared.service.ETSEmailService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.ets.ereg.web.profile.form.ProfileForm;
import org.ets.ereg.web.profile.validator.ProfileFormValidator;

import org.ets.ereg.web.util.LoginUtil;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.support.RequestContext;


@Controller
@RequestMapping("/public/profile")
@SessionAttributes(CreateProfileController.FORM_ATTRIBUTE)
public class CreateProfileController {

	private static Logger LOG = LoggerFactory.getLogger(CreateProfileController.class);

		public static final String FORM_ATTRIBUTE = "profileForm";
		public String languageCode;

		private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

		@Resource(name = "profileBusinessService")
		private ProfileBusinessService profileBusinessService;

		@Resource(name = "referenceEntityBusinessService")
		private ReferenceBusinessService referenceEntityBusinessService;

	    @Resource(name="profileFormValidator")
	    private ProfileFormValidator profileFormValidator;

	    @Resource(name="blLoginService")
	    private LoginService loginService;
	    
	    @Resource(name="forgotCredentialsEmailInfo")
	    protected EmailInfo forgotCredentialsEmailInfo;   

	    @Resource(name="applicationConfigurationService")
		private ApplicationConfigurationService applicationService;
	    @Resource(name="etsCustomerBusinessService")
        ETSCustomerBusinessService etsCustomerBusinessService;
	    
	    @Resource(name="profileDemographicQuestionService")
		protected ProfileDemographicQuestionService profileDemographicService;
	    
	    @Resource(name="etsEmailService")
	    protected ETSEmailService etsEmailService;
		
	    @Resource(name = "eregUtils")
		private EregUtils eregUtils;	    

		public static final String getPersonalInfoStepView(){
			return "public/profile/personalInfo";
		}

		public static final String getDuplicateAccountStepView(){
			return "public/profile/duplicateAccount";
		}

		public static final String getAccountInfoStepView(){
			return "public/profile/accountInfo";
		}

		public static final String getAdditionalInfoStepView(){
			return "public/profile/additionalInfo";
		}

		public static final String getBackgroundInfoStepView(){
			return "public/profile/backgroundInfo";
		}

		public static final String getReviewInfoStepView(){
			return "public/profile/reviewInfo";
		}

		public static final String getUpdateProfileView(){
			return "public/profile/updateProfile";
		}

		public static final String getUpdateAccountView(){
			return "profile/updateAccount";
		}

		public static final String getAccountCreatedView(){
			return "public/profile/accountCreated";
		}

		public static final String getPersonalInfoStepRedirect(){
			return "redirect:/public/profile/";
		}

		public static final String getDuplicateAccountStepRedirect(){
			return "redirect:/public/profile/account/duplicate";
		}
		public static final String getDuplicateAccountStep(){
			return "public/profile/duplicateAccount";
		}
		public static final String getAccountInfoStepRedirect(){
			return "redirect:/public/profile/account";
		}

		public static final String getAdditionalInfoStepRedirect(){
			return "redirect:/public/profile/additional";
		}

		public static final String getBackgroundInfoStepRedirect(){
			return "redirect:/public/profile/background";
		}

		public static final String getReviewInfoStepRedirect(){
			return "redirect:/public/profile/review";
		}

		public static final String getCreateProfileRedirect(){
			return "redirect:/public/profile/";
		}

		public static final String getLdapAutoLoginView(){
			return "public/profile/autoLogin";
		}

		static {
			DATE_FORMAT.setLenient(false);
		}

		private void setGenericValidationError(ProfileForm profileForm){
			profileForm.setStatusCode(ProfileForm.STATUS_KO);
			profileForm.setStatusMessage("<b>Important Message</b>: An error was found in the information you entered.  Please check your responses below.  All required fields must be answered.\n");
		}
		
		
		@RequestMapping(value="/submitEmail",method=RequestMethod.POST)
		public @ResponseBody  Map<String,Object> submitCredentialsRequest(Model model,@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm){
			String email=profileForm.getProfile().getCustomer().getEmailAddress();
			ETSCustomer customer=etsCustomerBusinessService.getCustomerByEmail(email);
			Map<String,Object> map= new HashMap<String,Object>();
			List<String> list=new ArrayList<String>();
			if(customer==null){
				list.add("Please enter a valid email address");
				map.put("errors", true);
				map.put("results", list);
				return map;
			}
			
			
			HashMap<String, Object> props = new HashMap<String, Object>();
			props.put("customer", customer);
			if("password".equals(profileForm.getScenario())){
				props.put("requestedField", "password");
				props.put("value", customer.getPassword());
			}
			else{
			props.put("requestedField", "user name");
			props.put("value", customer.getUsername());
			}
			etsEmailService.sendTemplateEmail(customer.getEmailAddress(),forgotCredentialsEmailInfo , props);
			list.add("Email message sent.");
			model.addAttribute("messageList",list);
			map.put("errors", false);
			map.put("results", list);
			return map;
			
		}
		

		
		@RequestMapping(value="/accountrecovery",method=RequestMethod.GET)
		public String recoverAccount(HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm){
			return "/public/profile/accountRecovery";
		}

		
		
		
		

		@RequestMapping(method=RequestMethod.GET)
		public String onGetNewProfile(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
		){
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				profileForm.setScenario(ProfileForm.SCENARIO_CREATE_PROFILE);
				profileForm.setStep(ProfileForm.STEP_PERSONAL_INFO);
				profileForm.transfereToFormDateOfBirth();
				return getPersonalInfoStepView();
			}
			else{
				return UpdateProfileController.getUpdateProfileRedirect();
			}
		}

		@RequestMapping(value="/personal", method=RequestMethod.POST)
		public String onPostPersonalInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors) {
			if(ProfileForm.STEP_PERSONAL_INFO.equalsIgnoreCase(profileForm.getStep())){
				profileForm.transfereFromFormDateOfBirth();
				profileFormValidator.validatePersonalInfo(profileForm, errors);
				if(errors.hasErrors()){
					setGenericValidationError(profileForm);
					return getPersonalInfoStepView();
				}
				else{
					profileForm.setStatusCode(ProfileForm.STATUS_OK);
					if(profileBusinessService.hasDuplicateProfiles(profileForm.getProfile(), false)){
						return getDuplicateAccountStepRedirect();
					}
					else{
						profileForm.setStatusCode(ProfileForm.STATUS_OK);
						profileForm.setStep(ProfileForm.STEP_ADDITIONAL_INFO); 
								
						return getBackgroundInfoStepRedirect();
					}
				}
			}
			else{
				return getPersonalInfoStepRedirect();
			}
		}

		@RequestMapping(value="/account/duplicate", method=RequestMethod.GET)
		public String onGetDuplicateProfile(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors){
			if(ProfileForm.STEP_PERSONAL_INFO.equalsIgnoreCase(profileForm.getStep())){
				profileForm.setStep(ProfileForm.STEP_DUPLICATE_PROFILE);
				model=LoginUtil.getAuthAttributes(request, model, applicationService);
				return getDuplicateAccountStepView();
			}
			else{
				return getPersonalInfoStepRedirect();
			}
		}


		@RequestMapping(value="/account", method=RequestMethod.GET)
		public String onGetAccountInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
				){
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				if(	(ProfileForm.STEP_BIQ_INFO.equalsIgnoreCase(profileForm.getStep())) ||
					(ProfileForm.STEP_REVIEW_INFO.equalsIgnoreCase(profileForm.getStep()))
				){
					profileForm.setStep(ProfileForm.STEP_ACCOUNT_INFO);
					return getAccountInfoStepView();
				}
				else{
					return getPersonalInfoStepRedirect();
				}
			}
			else{
				return UpdateProfileController.getUpdateProfileRedirect();
			}
		}


		@RequestMapping(value="/account",method=RequestMethod.POST)
		public String onPostAccountInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors,
				SessionStatus sessionStatus) throws ServiceException {
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				if(ProfileForm.STEP_ACCOUNT_INFO.equalsIgnoreCase(profileForm.getStep())){
					String authMechanish = (applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM));
					profileFormValidator.validateAccountInfo(profileForm, errors,authMechanish);
					if(errors.hasErrors()){
						setGenericValidationError(profileForm);
						return getAccountInfoStepView();
					}
					else{
						if(!profileBusinessService.isUsernameAvailable(profileForm.getProfile().getCustomer().getUsername())){
							profileForm.setStatusCode(ProfileForm.STATUS_OK);
							return getReviewInfoStepRedirect();
						}
						else{
							errors.rejectValue(ProfileForm.USERNAME, "profile.create.validation.usernameUsed");
							setGenericValidationError(profileForm);
							return getAccountInfoStepView();
						}
					}
				}
				else{
					return getPersonalInfoStepRedirect();
				}
			}
			else{
				return UpdateProfileController.getUpdateProfileRedirect();
			}
		}

		@RequestMapping(value="/additional", method=RequestMethod.GET)
		public String onGetAdditionalInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
				){
			if(	(ProfileForm.STEP_PERSONAL_INFO.equalsIgnoreCase(profileForm.getStep())) ||
				(ProfileForm.STEP_BIQ_INFO.equalsIgnoreCase(profileForm.getStep()))
			){
				profileForm.setStep(ProfileForm.STEP_ADDITIONAL_INFO);
				return getAdditionalInfoStepView();
			}
			else{
				return getPersonalInfoStepRedirect();
			}
		}

		@RequestMapping(value="/additional",method=RequestMethod.POST)
		public String onPostAdditionalInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors) throws ServiceException {
			if(ProfileForm.STEP_ADDITIONAL_INFO.equalsIgnoreCase(profileForm.getStep())){
				profileFormValidator.validateAdditionalInfo(profileForm, errors);
				if(errors.hasErrors()){
					setGenericValidationError(profileForm);
					return getAdditionalInfoStepView();
				}
				else{
					profileForm.setStatusCode(ProfileForm.STATUS_OK);
					return getBackgroundInfoStepRedirect();
				}
			}
			else{
				return getPersonalInfoStepRedirect();
			}
		}

		@RequestMapping(value="/background", method=RequestMethod.GET)
		public String onGetBackgroundInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
				){
			if(	(ProfileForm.STEP_ADDITIONAL_INFO.equalsIgnoreCase(profileForm.getStep())) ||
				(ProfileForm.STEP_ACCOUNT_INFO.equalsIgnoreCase(profileForm.getStep()))
			){
				RequestContext requestContext = new RequestContext(request);
				languageCode= requestContext.getLocale().getLanguage();
				LOG.debug("Language Code {}",languageCode);
				profileForm.setStep(ProfileForm.STEP_BIQ_INFO);
				return getBackgroundInfoStepView();
			}
			else{
				return getPersonalInfoStepRedirect();
			}
		}

		@RequestMapping(value="/background",method=RequestMethod.POST)
		public String onPostBackgroundInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors) throws ServiceException {
			if(ProfileForm.STEP_BIQ_INFO.equalsIgnoreCase(profileForm.getStep())){
				profileFormValidator.validateBackgroundInfo(profileForm, errors);
				// Below is for back button ,so as to have dependent fields get displayed
				profileDemographicService.setGotTriggeredFlag(profileForm.getProfile().getDemographicQuestions());
				if(errors.hasErrors()){
					setGenericValidationError(profileForm);
					return getBackgroundInfoStepView();
				}
				else{
					profileForm.setStatusCode(ProfileForm.STATUS_OK);
					return getAccountInfoStepRedirect();
				}
			}
			else{
				return getPersonalInfoStepRedirect();
			}
		}

		@RequestMapping(value="/review", method=RequestMethod.GET)
		public String onGetReviewInfo(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
				){
			if(ProfileForm.STEP_ACCOUNT_INFO.equalsIgnoreCase(profileForm.getStep())){
				profileForm.setStep(ProfileForm.STEP_REVIEW_INFO);
				return getReviewInfoStepView();
			}
			else{
				return getPersonalInfoStepRedirect();
			}
		}

		@RequestMapping(method=RequestMethod.POST)
		public String createProfile(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(CreateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors,
				SessionStatus sessionStatus) throws ServiceException {
			LOG.debug("Inside create profile");
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				if(ProfileForm.STEP_REVIEW_INFO.equalsIgnoreCase(profileForm.getStep()))
				{
					profileForm.getProfile().getCustomer().setPassword(profileForm.getPassword());
					profileForm.getProfile().getCustomer().setAggrementTimeStamp(new java.util.Date());
					profileForm.getProfile().getCustomer().setInternalUserFlag(false);

					// to check if the account personal information is duplicate at the last step again
					if(profileBusinessService.hasDuplicateProfiles(profileForm.getProfile(), false)){
						profileForm.setStatusCode(ProfileForm.STATUS_OK);
						return getDuplicateAccountStep();
					}

					// to check if the ldap username is duplicate at the last step again
					if(profileBusinessService.isUsernameAvailable(profileForm.getProfile().getCustomer().getUsername())){
						errors.rejectValue(ProfileForm.USERNAME, "profile.create.validation.usernameUsed");
						setGenericValidationError(profileForm);
						return getAccountInfoStepRedirect();
					}

					String plainTextPassword = profileForm.getProfile().getCustomer().getPassword();
					
					String guid = profileBusinessService.saveProfile(profileForm.getProfile());

					profileBusinessService.registerProfile(profileForm.getProfile());
					request.getSession().setAttribute(Constant.IS_NEW_CUSTOMER, true);
					if(eregUtils.isDBAuthentication())
					{
						loginService.loginCustomer(
								profileForm.getProfile().getCustomer().getUsername(),
								plainTextPassword);
						sessionStatus.setComplete();
						profileForm.setStatusCode(ProfileForm.STATUS_OK);

						SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
			            String redirectUrl = (savedRequest == null ? null : savedRequest.getRedirectUrl());
			            LOG.debug("Registration redirect url: " + redirectUrl);

			            if(redirectUrl != null) {
			            	return "redirect:" + redirectUrl;
			            }
						return "redirect:/secure/home";
					}
					else
					{
						profileBusinessService.updateGUID(guid, profileForm.getProfile().getCustomer().getId());

						model=LoginUtil.getAuthAttributes(request, model, applicationService);
						model.addAttribute("userid", profileForm.getProfile().getCustomer().getUsername());
						model.addAttribute("userpassword", profileForm.getProfile().getCustomer().getPassword());
						sessionStatus.setComplete();
						profileForm.setStatusCode(ProfileForm.STATUS_OK);

						return getLdapAutoLoginView();
					}
				}
				else{
					return getPersonalInfoStepRedirect();
				}
			}
			else{
				return UpdateProfileController.getUpdateProfileRedirect();
			}
		}

	    @ModelAttribute(CreateProfileController.FORM_ATTRIBUTE)
	    public ProfileForm initProfileForm(HttpServletRequest request) {
	    	ProfileForm profileForm = new ProfileForm();
	    	profileForm.setProfile(profileBusinessService.createProfile());

	    	RequestContext requestContext = new RequestContext(request);
	    	List<DemographicQuestionVO> biqs=profileDemographicService.getDemographicQuestions(null, ProgramContextHolder.getProgramCode(), requestContext.getLocale().getLanguage().toUpperCase(), DemographicQuestionSetType.BIQ_TYPE_PROFILE, true);
	    	profileForm.getProfile().setDemographicQuestions(biqs);
	    	LOG.debug("length of VOs:{}",biqs.size());

	    	ETSCountry us = (ETSCountry)profileBusinessService.findCountryByAbbreviation("US");
	    	profileForm.getProfile().getAddress().setCountry(us);
	    	profileForm.getProfile().getPrimaryPhone().setCountry(us);
	    	profileForm.getProfile().getAlternatePhone().setCountry(us);

	    	return profileForm;
	    }




		@ModelAttribute("countries")
	    public List<Country> getCountries()
	    {
	    	return profileBusinessService.findCountries();
	    }

	    @ModelAttribute("states")
	    public List<State> getStates()
	    {
	    	return profileBusinessService.findStates("US");
	    }

	    @ModelAttribute("challengeQuestions")
	    public List<ChallengeQuestion> getChallengeQuestions()
	    {
	    	return profileBusinessService.readChallengeQuestions();
	    }

	    @ModelAttribute("genders")
	    public List<Gender> getGenders()
	    {
	    	return referenceEntityBusinessService.getAll(Gender.class, new ReferenceTypeCriteria(), false);
	    }

	    @ModelAttribute("militaryStatusTypes")
	    public List<MilitaryStatusType> getMilitaryStatusTypes()
	    {
	    	return referenceEntityBusinessService.getAll(MilitaryStatusType.class, new ReferenceTypeCriteria(), true);
	    }

	    @ModelAttribute("ethnicityTypes")
	    public List<EthnicityType> getEthnicityTypes()
	    {
	    	return referenceEntityBusinessService.getAll(EthnicityType.class, new ReferenceTypeCriteria(), false);
	    }

	    @ModelAttribute("languageTypes")
	    public List<LanguageType> getLanguageTypes()
	    {
	    	return referenceEntityBusinessService.getAll(LanguageType.class, new ReferenceTypeCriteria(), false);
	    }

	    @ModelAttribute("phoneTypes")
	    public List<PhoneType> getPhoneTypes()
	    {
	    	return referenceEntityBusinessService.getAll(PhoneType.class, new ReferenceTypeCriteria(), false);
	    }

	    @ModelAttribute("months")
	    public List<String> getMonths()
	    {
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
	    public List<String> getDays()
	    {
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
	    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	        binder.registerCustomEditor(State.class, ProfileForm.STATE, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	State state = profileBusinessService.findStateByAbbreviation(text);
	                setValue(state);
	            }
	        });

	        binder.registerCustomEditor(Country.class, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	Country country = profileBusinessService.findCountryByAbbreviation(text);
	                setValue(country);
	            }
	        });

	        binder.registerCustomEditor(ChallengeQuestion.class, ProfileForm.SECURITY_QUESTION, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	ChallengeQuestion challengeQuestion = null;
	            	if(null != text && !text.equalsIgnoreCase("")){
	            		challengeQuestion = profileBusinessService.readChallengeQuestionById(Long.parseLong(text));
	            	}
	                setValue(challengeQuestion);
	            }

	            public String getAsText(){
	            	ChallengeQuestion challengeQuestion = (ChallengeQuestion)this.getValue();
	            	if(null != challengeQuestion){
	            		return challengeQuestion.getId().toString();
	            	}
            		return null;
	            }

	        });

	        binder.registerCustomEditor(Date.class, ProfileForm.DATE_OF_BIRTH, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	Date value = null;
	            	try{
	            		value = DATE_FORMAT.parse(text);
	            	}
	            	catch(Exception e){
	            		LOG.error("failed to parse date",e);
	            	}
	            	setValue(value);
	            }

	            public String getAsText(){
	            	Date dateOfBirth = (Date)this.getValue();
	            	return (null == dateOfBirth)?"":DATE_FORMAT.format(dateOfBirth);
	            }
	        });

	        binder.registerCustomEditor(PhoneType.class, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	PhoneType value = referenceEntityBusinessService.getEntityByPrimaryKey(PhoneType.class, text);
	            	setValue(value);
	            }
	        });

	        binder.registerCustomEditor(Gender.class, ProfileForm.GENDER, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	Gender value = referenceEntityBusinessService.getEntityByPrimaryKey(Gender.class, text);
	            	setValue(value);
	            }
	        });

	        binder.registerCustomEditor(EthnicityType.class, ProfileForm.ETHNICITY, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	EthnicityType value = referenceEntityBusinessService.getEntityByPrimaryKey(EthnicityType.class, text);
	            	setValue(value);
	            }
	        });

	        binder.registerCustomEditor(LanguageType.class, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	LanguageType value = referenceEntityBusinessService.getEntityByPrimaryKey(LanguageType.class, text);
	            	setValue(value);
	            }
	        });

	        binder.registerCustomEditor(MilitaryStatusType.class, ProfileForm.MILITARY_STATUS, new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) {
	            	MilitaryStatusType value = referenceEntityBusinessService.getEntityByPrimaryKey(MilitaryStatusType.class, text);
	            	setValue(value);
	            }
	        });

	    }
}

