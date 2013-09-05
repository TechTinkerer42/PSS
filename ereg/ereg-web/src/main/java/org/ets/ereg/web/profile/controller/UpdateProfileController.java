package org.ets.ereg.web.profile.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.util.CommonUtils;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetType;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.EthnicityType;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.service.ProfileService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.ets.ereg.web.profile.form.ProfileForm;
import org.ets.ereg.web.profile.validator.ProfileFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContext;

@Controller
@RequestMapping("/secure/profile")

public class UpdateProfileController {
		
		public static final String FORM_ATTRIBUTE = "profileForm";

		private static final String STATUS_MESSAGE = "STATUS_MESSAGE";

		private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

		@Resource(name = "profileBusinessService")
		private ProfileBusinessService profileBusinessService;
		
		@Resource(name = "profileService")
		private ProfileService profileService;

		@Resource(name = "referenceEntityBusinessService")
		private ReferenceBusinessService referenceEntityBusinessService;

	    @Resource(name="profileFormValidator")
	    private ProfileFormValidator profileFormValidator;
	    
	    @Resource(name="profileDemographicQuestionService")
		 protected ProfileDemographicQuestionService profileDemographicService;
	    
	    @Autowired
	    private MessageSource messageSource;

	    public static final String getUpdateProfileView(){
			return "public/profile/updateProfile";
		}

	    public static final String getChangePasswordView(){
			return "public/profile/changePassword";
		}

	    public static final String getChangeSecurityQuestionView(){
			return "public/profile/changeSecurityQuestion";
		}

	    public static final String getChangeBackgroundInfoView(){
			return "public/profile/changeBackgroundInfo";
		}

	    public static final String getHomePageRedirect(){
			return "redirect:/secure/home";
		}

		public static final String getUpdateProfileRedirect(){
			return "redirect:/secure/profile/";
		}

		static {
			DATE_FORMAT.setLenient(false);
		}

		private void setGenericValidationError(ProfileForm profileForm){
			profileForm.setStatusCode(ProfileForm.STATUS_KO);
			profileForm.setStatusMessage("<b>Important Message</b>: An error was found in the information you entered.  Please check your responses below.  All required fields must be answered.\n");
		}

		@RequestMapping(method=RequestMethod.GET)
		public String getProfile(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
		){
			Customer customer = CustomerState.getCustomer();
			if(null != customer && customer.isLoggedIn()){
				return getUpdateProfileView();
			}
			else{
				return CreateProfileController.getCreateProfileRedirect();
			}
		}

		@RequestMapping(method=RequestMethod.POST)
		public String updateProfile(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors,
				final RedirectAttributes redirectAttributes) throws ServiceException {
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				return CreateProfileController.getCreateProfileRedirect();
			}
			else{
				if(ProfileForm.SCENARIO_UPDATE_PROFILE.equalsIgnoreCase(profileForm.getScenario())){
					profileFormValidator.validatePersonalInfoForUpdate(profileForm, errors);
					profileFormValidator.validateAdditionalInfo(profileForm, errors);
					if(errors.hasErrors()){
						setGenericValidationError(profileForm);
						return getUpdateProfileView();
					}
					else{
						if(profileBusinessService.hasDuplicateProfiles(profileForm.getProfile(), true)){
							redirectAttributes.addFlashAttribute(STATUS_MESSAGE, "Profile was not updated successfuly. Update will create a duplicate.");
						}
						else{
							profileBusinessService.saveProfile(profileForm.getProfile());
							redirectAttributes.addFlashAttribute(STATUS_MESSAGE, "Profile updated successfuly.");
							String nextUrl = (String)request.getSession().getAttribute(org.ets.ereg.common.web.util.Constant.AFTER_UPDATE_PROFILE_URL_SESSION_ATTR);
							if(StringUtils.isNotEmpty(nextUrl)) {
								request.getSession().removeAttribute(org.ets.ereg.common.web.util.Constant.AFTER_UPDATE_PROFILE_URL_SESSION_ATTR);
								return nextUrl;
							}
						}
						return getHomePageRedirect();
					}
				}
				else{
					return UpdateProfileController.getUpdateProfileRedirect();
				}
			}
		}
		
		@RequestMapping(value="/changePassword", method=RequestMethod.GET)
		public String onGetChangePassword(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
				){
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				return CreateProfileController.getCreateProfileRedirect();
			}
			else{
				ProfileVO profile = profileBusinessService.readProfileById(customer.getId());
				profileForm.setProfile(profile);
				profileForm.setScenario(ProfileForm.SCENARIO_UPDATE_PROFILE);
				return getChangePasswordView();
			}
		}

		@RequestMapping(value="/changePassword", method=RequestMethod.POST)
		public String onPostChangePassword(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors,
				final RedirectAttributes redirectAttributes) throws ServiceException {
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				return CreateProfileController.getCreateProfileRedirect();
			}
			else{
				if(ProfileForm.SCENARIO_UPDATE_PROFILE.equalsIgnoreCase(profileForm.getScenario())){
					profileFormValidator.validateChangePassword(profileForm, errors);
					if(errors.hasErrors()){
						setGenericValidationError(profileForm);
						return getChangePasswordView();
					}
					else{
						ProfileVO profile = profileBusinessService.readProfileById(CustomerState.getCustomer().getId());
						
//						String password = null;
//						try {
//							password = CommonUtils.encodeString(profileForm.getOldPassword()).trim();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
						
						
						
						// A call need to be made to OAM to match the password
						profile = profileBusinessService.authenthicate(profile.getCustomer().getUsername(), profileForm.getOldPassword());
						if(null == profile){
							errors.rejectValue(ProfileForm.OLD_PASSWORD, "passwordchange.currentPasswordIncorrect");
							setGenericValidationError(profileForm);
							return getChangePasswordView();
						}
						else{
							if(null != profileForm.getPassword() && profileForm.getPassword().trim().length() > 0){
								profileForm.getProfile().getCustomer().setPassword(profileForm.getPassword());
							}
							//profileBusinessService.saveProfile(profileForm.getProfile());
							profileBusinessService.changePassword(profileForm.getProfile());
							redirectAttributes.addFlashAttribute(STATUS_MESSAGE, "Password changed successfuly");
							return getHomePageRedirect();
						}
					}
				}
				else{
					return UpdateProfileController.getUpdateProfileRedirect();
				}
			}
		}

		@RequestMapping(value="/changeSecurityQuestion", method=RequestMethod.GET)
		public String onGetChangeSecurityQuestion(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
				){
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				return CreateProfileController.getCreateProfileRedirect();
			}
			else{
				ProfileVO profile = profileBusinessService.readProfileById(customer.getId());
				profileForm.setProfile(profile);
				profileForm.setScenario(ProfileForm.SCENARIO_UPDATE_PROFILE);
				return getChangeSecurityQuestionView();
			}
		}

		@RequestMapping(value="/changeSecurityQuestion", method=RequestMethod.POST)
		public String onPostChangeSecurityQuestion(
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors,
				final RedirectAttributes redirectAttributes) throws ServiceException {
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				return CreateProfileController.getCreateProfileRedirect();
			}
			else{
				if(ProfileForm.SCENARIO_UPDATE_PROFILE.equalsIgnoreCase(profileForm.getScenario())){
					profileFormValidator.validateChangeSecurityQuestion(profileForm, errors);
					if(errors.hasErrors()){
						setGenericValidationError(profileForm);
						return getChangeSecurityQuestionView();
					}
					else{
						ProfileVO profile = profileBusinessService.readProfileById(CustomerState.getCustomer().getId());
						
//						String password = null;
//						try {
//							password = CommonUtils.encodeString(profileForm.getOldPassword());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
						
						// A call need to be made to OAM to match the password
						profile = profileBusinessService.authenthicate(profile.getCustomer().getUsername(), profileForm.getOldPassword());
						if(null == profile){
							errors.rejectValue(ProfileForm.OLD_PASSWORD, "passwordchange.currentPasswordIncorrect");
							setGenericValidationError(profileForm);
							return getChangeSecurityQuestionView();
						}
						else{
							//profileBusinessService.saveProfile(profileForm.getProfile());
							profileBusinessService.changeSecQuestion(profileForm.getProfile());
							redirectAttributes.addFlashAttribute(STATUS_MESSAGE, "Security question/answer changed successfuly.");
							return getHomePageRedirect();
						}
					}
				}
				else{
					return UpdateProfileController.getUpdateProfileRedirect();
				}
			}
		}


		@RequestMapping(value="/background/{mandate}", method=RequestMethod.GET)
		public String onGetChangeBackgroundInfo(@PathVariable(value="mandate") String mandate,
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors
				){
			RequestContext requestContext = new RequestContext(request);
			if(null!=mandate && mandate.equalsIgnoreCase("mandatory"))
			{
				model.addAttribute("headText",getMessage("background.complete.heading",requestContext.getLocale()));
			}
			else
			{
				model.addAttribute("headText",getMessage("background.update.heading",requestContext.getLocale()));
			}
			
			Customer customer = CustomerState.getCustomer();
			if(null == customer || !customer.isLoggedIn()){
				return CreateProfileController.getCreateProfileRedirect();
			}
			else{				
				profileForm.setScenario(ProfileForm.SCENARIO_UPDATE_PROFILE);			
				return getChangeBackgroundInfoView();
			}
		}

		

		private String getMessage(String code,Locale locale) {				
			return messageSource.getMessage(code, null, locale);
			
		}

		@RequestMapping(value="/background/{mandate}",method=RequestMethod.POST)
		public String onPostChangeBackgroundInfo(@PathVariable(value="mandate") String mandate,
				HttpServletRequest request,
				HttpServletResponse response,
				Model model,
				@ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE) ProfileForm profileForm,
				BindingResult errors,
				final RedirectAttributes redirectAttributes) throws ServiceException {
			RequestContext requestContext = new RequestContext(request);
			Customer customer = CustomerState.getCustomer();
			if(null!=mandate && mandate.equalsIgnoreCase("mandatory"))
			{
				model.addAttribute("headText",getMessage("background.complete.heading",requestContext.getLocale()));
			}
			else
			{
				model.addAttribute("headText",getMessage("background.update.heading",requestContext.getLocale()));
			}
			
			if(null == customer || !customer.isLoggedIn()){
				return CreateProfileController.getCreateProfileRedirect();
			}
			else{
				profileFormValidator.validateBackgroundInfo(profileForm, errors);
				if(errors.hasErrors()){
					setGenericValidationError(profileForm);
					return getChangeBackgroundInfoView();
				}
				else{
					//call save
					
				profileService.saveBIQ(customer.getId(), profileForm.getProfile().getDemographicQuestions());
					
				redirectAttributes.addFlashAttribute("STATUS_MESSAGE",getMessage("background.update.success",requestContext.getLocale()));
				return getHomePageRedirect();
				}
			}
			
		}

	    @ModelAttribute(UpdateProfileController.FORM_ATTRIBUTE)
	    public ProfileForm initProfileForm(HttpServletRequest request) {
	    	ProfileForm profileForm = new ProfileForm();
	    	profileForm.setScenario(ProfileForm.SCENARIO_UPDATE_PROFILE);
	    	Customer customer = CustomerState.getCustomer();
			if(null != customer && customer.isLoggedIn()){
				ETSCountry us = (ETSCountry)profileBusinessService.findCountryByAbbreviation("US");
				ProfileVO profile = profileBusinessService.readProfileById(customer.getId());
				if(	null == profile.getAlternatePhone().getPhoneNumber() ||
					profile.getAlternatePhone().getPhoneNumber().trim().length() == 0
				){
					profile.getAlternatePhone().setCountry(us);
				}
				profileForm.setProfile(profile);
			}
			else{
				profileForm.setProfile(profileBusinessService.createProfile());
			}	
			
			RequestContext requestContext = new RequestContext(request);
			List<DemographicQuestionVO> dQList=profileDemographicService.getDemographicQuestions(customer.getId(), ProgramContextHolder.getProgramCode(), requestContext.getLocale().getLanguage().toUpperCase(), DemographicQuestionSetType.BIQ_TYPE_PROFILE, true);
			
			profileForm.getProfile().setDemographicQuestions(dQList);
			profileForm.transfereToFormDateOfBirth();
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
	            		//TODO log error
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

