package org.ets.ereg.csr.web.controller.profile;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.State;
import org.ets.ereg.common.business.util.EregUtils;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria.ReferenceTypeOrderBy;
import org.ets.ereg.csr.web.form.profile.ProfileForm;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.csr.web.validator.profile.ProfileFormValidator;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetType;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.EthnicityType;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.external.service.eias.client.types.UserVO;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.session.facade.profile.service.ETSCustomerBusinessService;
import org.ets.ereg.session.facade.profile.service.GeneratePasswordBusinessService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.support.RequestContext;


@Controller
@RequestMapping("/secure/profile")
@SessionAttributes(ProfileController.PROFILE_FORM)
public class ProfileController {

	private static Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;

	@Resource(name = "referenceEntityBusinessService")
	private ReferenceBusinessService referenceEntityBusinessService;

	@Resource(name = "generatePasswordBusinessService")
	private GeneratePasswordBusinessService generatePasswordBusinessService;

	@Resource(name = "profileFormValidator")
	private ProfileFormValidator profieFormValidator;

	@Resource(name="etsCustomerBusinessService")
	private ETSCustomerBusinessService etsCustBusService;

	@Resource(name="profileDemographicQuestionService")
	protected ProfileDemographicQuestionService profileDemographicService;

	@Resource(name = "generateBase64StringBusinessService")
	private GenerateBase64StringBusinessService generateBase64StringBusinessService;

	@Resource(name = "eregUtils")
	private EregUtils eregUtils;

	public static final String PROFILE_FORM = "profileForm";
	public static final String PROFILE_FORM_VIEW = "profileFormView";

	public static final String getPersonalInfoFormView() {
		return "profile/personalInfo";
	}

	public static final String getDuplicateAccountView() {
		return "profile/duplicateAccount";
	}

	public static final String getBackgroundInfoInfoFormView() {
		return "profile/backgroundInfo";
	}

	public static final String getAdditionalInfoFormView() {
		return "profile/additionalInfo";
	}

	public static final String getAccountInfoFormView() {
		return "profile/accountInfo";
	}

	public static final String getReviewProfileView() {
		return "profile/reviewProfile";
	}

	public static final String getAccountCreatedView() {
		return "profile/profileCreated";
	}

	public static final String getViewProfileView() {
		return "profile/viewProfile";
	}

	public static final String getUpdateProfileView() {
		return "profile/updateProfile";
	}

	public static final String redirectPersonalInfoFormView() {
		return "redirect:/secure/profile";
	}

	public static final String redirectAdditionalInfoFormView() {
		return "redirect:/secure/profile/additional";
	}

	public static final String redirectBackgroundInfoFormView(){
		return "redirect:/secure/profile/background";
	}

	public static final String redirectAccountInfoFormView() {
		return "redirect:/secure/profile/account";
	}

	public static final String redirectReviewProfileView() {
		return "redirect:/secure/profile/review";
	}

	public static final String redirectDuplicateAccountView() {
		return "redirect:/secure/profile/duplicate";
	}

	public ProfileController() {}

	/* Getters and setters for unit testing */
	public ProfileBusinessService getProfileBusinessService() {
		return profileBusinessService;
	}

	public void setProfileBusinessService(ProfileBusinessService profileBusinessService) {
		this.profileBusinessService = profileBusinessService;
	}

	public void setprofileDemographicQuestionService(ProfileDemographicQuestionService profileDemographicQuestionService) {
		this.profileDemographicService = profileDemographicQuestionService;
	}

	public ReferenceBusinessService getReferenceEntityBusinessService() {
		return referenceEntityBusinessService;
	}

	public void setReferenceEntityBusinessService(
			ReferenceBusinessService referenceEntityBusinessService) {
		this.referenceEntityBusinessService = referenceEntityBusinessService;
	}

	public GeneratePasswordBusinessService getGeneratePasswordBusinessService() {
		return generatePasswordBusinessService;
	}

	public void setGeneratePasswordBusinessService(
			GeneratePasswordBusinessService generatePasswordBusinessService) {
		this.generatePasswordBusinessService = generatePasswordBusinessService;
	}

	public ProfileFormValidator getProfieFormValidator() {
		return profieFormValidator;
	}

	public void setProfieFormValidator(ProfileFormValidator profieFormValidator) {
		this.profieFormValidator = profieFormValidator;
	}

	public GenerateBase64StringBusinessService getGenerateBase64StringBusinessService() {
        return generateBase64StringBusinessService;
    }

    public void setGenerateBase64StringBusinessService(
            GenerateBase64StringBusinessService generateBase64StringBusinessService) {
        this.generateBase64StringBusinessService = generateBase64StringBusinessService;
    }

    @RequestMapping(method = RequestMethod.GET)
	public String newProfile(HttpServletRequest request,
							 HttpServletResponse response,
							 Model model,
							 @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
							 BindingResult errors) {
		profileForm.setStep(ProfileForm.STEP_PERSONAL_INFO);
		return getPersonalInfoFormView();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processPersonalInfo(HttpServletRequest request,
					 				 HttpServletResponse response,
					 				 Model model,
					 				 @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
					 				 BindingResult errors) throws ServiceException {
		if (ProfileForm.STEP_PERSONAL_INFO.equalsIgnoreCase(profileForm.getStep())) {
			profieFormValidator.validateContactInfo(profileForm, errors);
			if (!errors.hasErrors()) {
				DupCheckResponseObject dupCheckResponseObj =
						profileBusinessService.checkDuplicateProfile(profileForm.getProfile());
				if (dupCheckResponseObj.isDuplicate()) {
					profileForm.setStep(ProfileForm.STEP_DUPLICATE_PROFILE);
					return redirectDuplicateAccountView();
				} else {
					return redirectAdditionalInfoFormView();
				}
			} else {
				return getPersonalInfoFormView();
			}
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value = "/duplicate", method = RequestMethod.GET)
	public String displayDuplicateAccountView(HttpServletRequest request,
						  HttpServletResponse response,
						  Model model,
						  @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
						  BindingResult errors) {

		if (ProfileForm.STEP_DUPLICATE_PROFILE.equalsIgnoreCase(profileForm.getStep())) {

			List<CustomerVO> dupCustomers =
					profileBusinessService.getDuplicateProfiles(profileForm.getProfile(), false);
			model.addAttribute("dupCustomers", dupCustomers);
			return getDuplicateAccountView();
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value = "/duplicate", method = RequestMethod.POST)
	public String overrideDuplicateAccount(HttpServletRequest request,
					       				   HttpServletResponse response,
					       				   Model model,
					       				   @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
										   BindingResult errors) {
		if (ProfileForm.STEP_DUPLICATE_PROFILE.equalsIgnoreCase(profileForm.getStep())) {
			profileForm.setStep(ProfileForm.STEP_PERSONAL_INFO);
			return redirectAdditionalInfoFormView();
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value = "/additional", method = RequestMethod.GET)
	public String displayAdditionalInfoFormView(HttpServletRequest request,
											  HttpServletResponse response,
											  Model model,
											  @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
											  BindingResult errors) {
		if (ProfileForm.STEP_PERSONAL_INFO.equalsIgnoreCase(profileForm.getStep())
				|| ProfileForm.STEP_BIQ_INFO.equalsIgnoreCase(profileForm.getStep())) {
			profileForm.setStep(ProfileForm.STEP_ADDITIONAL_INFO);
			return getAdditionalInfoFormView();
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value = "/additional", method = RequestMethod.POST)
	public String processAdditionalInfo(HttpServletRequest request,
									  HttpServletResponse response,
									  Model model,
									  @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
									  BindingResult errors) {
		if (ProfileForm.STEP_ADDITIONAL_INFO.equalsIgnoreCase(profileForm.getStep())) {
			profieFormValidator.validatePersonalInfo(profileForm, errors);
			if (!errors.hasErrors()) {
				return redirectBackgroundInfoFormView();
				//return redirectAccountInfoFormView();
			} else {
				return getAdditionalInfoFormView();
			}
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value="/background", method=RequestMethod.GET)
	public String displayBackgroundInfoFormView(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
			BindingResult errors
			){
		if(	(ProfileForm.STEP_ADDITIONAL_INFO.equalsIgnoreCase(profileForm.getStep())) ||
			(ProfileForm.STEP_ACCOUNT_INFO.equalsIgnoreCase(profileForm.getStep())) || 
				(ProfileForm.STEP_REVIEW_INFO.equalsIgnoreCase(profileForm.getStep())) 
		){
			profileForm.setStep(ProfileForm.STEP_BIQ_INFO);
			return getBackgroundInfoInfoFormView();

		}
		else{
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value="/background",method=RequestMethod.POST)
	public String processBackgroundInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
			BindingResult errors)  {
		profileDemographicService.setGotTriggeredFlag(profileForm.getProfile().getDemographicQuestions());
		if(ProfileForm.STEP_BIQ_INFO.equalsIgnoreCase(profileForm.getStep())){
			if (eregUtils.isDBAuthentication())
				return redirectAccountInfoFormView(); 
			else
				return redirectReviewProfileView();
		}
		else{
			return redirectPersonalInfoFormView();
		}
	}


	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String displayCreateAccountFormView(HttpServletRequest request,
											   HttpServletResponse response,
											   Model model,
											   @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
											   BindingResult errors) {
		if (ProfileForm.STEP_BIQ_INFO.equalsIgnoreCase(profileForm.getStep())
				|| ProfileForm.STEP_REVIEW_INFO.equalsIgnoreCase(profileForm.getStep())) {
			profileForm.setStep(ProfileForm.STEP_ACCOUNT_INFO);
			return getAccountInfoFormView();
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value = "/viewProfile", method = RequestMethod.POST)
	public String getUpdateView(HttpServletRequest request, @RequestParam("profileId") Long profileId,
								@ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
								SessionStatus sessionStatus) {
            ProfileVO profile = profileBusinessService.readProfileById(profileId);
    		if (null != profile) {
    			profileForm.setProfile(profile);
    			profileForm.getProfile().getCustomer().setMilitaryMemberShip(profile.getCustomer().getMilitaryMemberShip());
    			sessionStatus.setComplete();
    		}
    		return getUpdateProfileView();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String updateProfile(HttpServletRequest request,
			@ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
			BindingResult errors,
			SessionStatus sessionStatus) {
	    request.getSession().setAttribute("viewProfileBackUrl", request.getRequestURI());
		profieFormValidator.validateContactInfo(profileForm, errors);
		profieFormValidator.validatePersonalInfo(profileForm, errors);
		if (errors.hasErrors()) {
			return getUpdateProfileView();
		} else {
			ProfileVO profile = profileBusinessService.readProfileById(profileForm.getProfile().getCustomer().getId());			
			profile=prepareProfileVOForUpdate(profileForm.getProfile(),profile);
			profileBusinessService.saveProfile(profile);	
			sessionStatus.setComplete();
			
			String nextUrl = (String)request.getSession().getAttribute(org.ets.ereg.common.web.util.Constant.AFTER_UPDATE_PROFILE_URL_SESSION_ATTR);
			if(StringUtils.isNotEmpty(nextUrl)) {
				request.getSession().removeAttribute(org.ets.ereg.common.web.util.Constant.AFTER_UPDATE_PROFILE_URL_SESSION_ATTR);
				return nextUrl;
			}
			
            String encodeUrl = generateBase64StringBusinessService.encodeBase64String(request.getUserPrincipal().getName()+Constant.ENCODING_DELIMITTER +"?profileId=" + profileForm.getProfile().getCustomer().getId());
            return "redirect:/secure/profile/viewProfile/" + Constant.URL_DECODE_IDENTIFIER + encodeUrl;
		}
	}

	@RequestMapping(value = "/editCustomerProfile", method = RequestMethod.GET)
	public String displayUpdateProfileView(HttpServletRequest request,
			@RequestParam("customerId") Long customerId,
			@ModelAttribute(PROFILE_FORM) ProfileForm profileForm,
			BindingResult errors,
			SessionStatus sessionStatus) {
	    //String username = request.getUserPrincipal().getName();
        ProfileVO profile = profileBusinessService.readProfileById(customerId);
		if (null == profile) {
			return "error/customerNotFound";
		} else {
			profileForm.setProfile(profile);
			return getUpdateProfileView();
		}
	}

	@RequestMapping(value = "/editCustomerProfile", method = RequestMethod.POST)
	public String processUpdateProfile(HttpServletRequest request,
	        @RequestParam("customerId") Long customerId,
			@ModelAttribute(PROFILE_FORM) ProfileForm profileForm,
			BindingResult errors,
			SessionStatus sessionStatus) {
		profieFormValidator.validateContactInfo(profileForm, errors);
		profieFormValidator.validatePersonalInfo(profileForm, errors);
		if (errors.hasErrors()) {
			return getUpdateProfileView();
		} else {
			ProfileVO profile = profileForm.getProfile();
			profileBusinessService.saveProfile(profile);
			sessionStatus.setComplete();
			return "redirect:/secure/profile/viewProfile/?profileId=" + customerId;
		}
	}

    private ProfileVO prepareProfileVOForUpdate(ProfileVO sourcePro,
            ProfileVO destPro) {
        if (!sourcePro.getCustomer().getFirstName()
                .equalsIgnoreCase(destPro.getCustomer().getFirstName())
                || !sourcePro.getCustomer().getLastName()
                        .equalsIgnoreCase(destPro.getCustomer().getLastName())
                || !sourcePro
                        .getCustomer()
                        .getMiddleInitial()
                        .equalsIgnoreCase(
                                destPro.getCustomer().getMiddleInitial())) {
            //destPro.setNameChanged(Boolean.TRUE);
            destPro.getCustomer().setNameUpdated(Boolean.TRUE);
        }
        destPro.getCustomer().setFirstName(
                sourcePro.getCustomer().getFirstName());
        destPro.getCustomer()
                .setLastName(sourcePro.getCustomer().getLastName());
        destPro.getCustomer().setMiddleInitial(
                sourcePro.getCustomer().getMiddleInitial());
        destPro.getCustomer().setDateOfBirth(
                sourcePro.getCustomer().getDateOfBirth());
        destPro.getCustomer().setSocialSecurity(
                sourcePro.getCustomer().getSocialSecurity());
        destPro.getCustomer().setGender(sourcePro.getCustomer().getGender());
        destPro.getCustomer().setEthnicityType(
                sourcePro.getCustomer().getEthnicityType());
        destPro.getCustomer().setEmailAddress(
                sourcePro.getCustomer().getEmailAddress());
        destPro.getCustomer().setMilitaryMemberShip(
                sourcePro.getCustomer().getMilitaryMemberShip());
        destPro.getCustomer().setMilitaryStatus(
                sourcePro.getCustomer().getMilitaryStatus());
        destPro.getCustomer().setPrfrdTstTakingLang(
                sourcePro.getCustomer().getPrfrdTstTakingLang());
        destPro.getCustomer().setPrmrySpkngLang(
                sourcePro.getCustomer().getPrmrySpkngLang());
        destPro.getAddress().setAddressLine1(
                sourcePro.getAddress().getAddressLine1());
        destPro.getAddress().setAddressLine2(
                sourcePro.getAddress().getAddressLine2());
        destPro.getAddress().setCity(sourcePro.getAddress().getCity());
        destPro.getAddress().setState(sourcePro.getAddress().getState());
        destPro.getAddress().setPostalCode(
                sourcePro.getAddress().getPostalCode());
        destPro.getAddress().setCountry(sourcePro.getAddress().getCountry());
        destPro.getPrimaryPhone().setPhoneNumber(
                sourcePro.getPrimaryPhone().getPhoneNumber());
        destPro.getPrimaryPhone().setCountry(
                sourcePro.getPrimaryPhone().getCountry());
        destPro.getPrimaryPhone().setPhoneType(
                sourcePro.getPrimaryPhone().getPhoneType());
        destPro.getPrimaryPhone().setPhoneExtension(
                sourcePro.getPrimaryPhone().getPhoneExtension());
        destPro.getAlternatePhone().setPhoneNumber(
                sourcePro.getAlternatePhone().getPhoneNumber());
        destPro.getAlternatePhone().setCountry(
                sourcePro.getAlternatePhone().getCountry());
        destPro.getAlternatePhone().setPhoneType(
                sourcePro.getAlternatePhone().getPhoneType());
        destPro.getAlternatePhone().setPhoneExtension(
                sourcePro.getAlternatePhone().getPhoneExtension());

        return destPro;
    }

	@RequestMapping(value = "/viewProfile", method = RequestMethod.GET)
	public String getProfileById(HttpServletRequest request,
			@RequestParam("profileId") Long profileId,
			@ModelAttribute(ProfileController.PROFILE_FORM_VIEW) ProfileForm profileForm,
			SessionStatus sessionStatus
	){

        //String username = request.getUserPrincipal().getName();
        //String decodedProfileId = generateBase64StringBusinessService.decodeBase64String(profileId);
        //if(decodedProfileId.contains(username)){
            //Long profileIdLong = Long.parseLong(StringUtils.remove(decodedProfileId, username+Constant.ENCODING_DELIMITTER));
    		Customer cust=etsCustBusService.readCustomerById(profileId);
    		if(cust==null){
    			return "redirect:/secure/customersearch/search";
    		}

    		ProfileVO profile = profileBusinessService.readProfileById(profileId);
    		if (null != profile) {
    			profileForm.setProfile(profile);
    			return getViewProfileView();
    		}
    		return "redirect:/secure/customersearch/search";
        //}
        //request.getSession().invalidate();
        //return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
	}

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String processAccountInfo(HttpServletRequest request,
									 HttpServletResponse response,
									 Model model,
									 @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
									 BindingResult errors) {
		if (ProfileForm.STEP_ACCOUNT_INFO.equalsIgnoreCase(profileForm.getStep())) {
			profieFormValidator.validateAccountInfo(profileForm, errors);
			if (!errors.hasErrors()) {
				return redirectReviewProfileView();
			} else {
				return getAccountInfoFormView();
			}
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value = "/account", params = "generatePassword", method = RequestMethod.POST)
	public String generatePassword(HttpServletRequest request,
								   HttpServletResponse response,
								   Model model,
								   @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm) {
		String generatedPassword = generatePasswordBusinessService.generateNewPassword();
		profileForm.getProfile().getCustomer().setPassword(generatedPassword);
		return getAccountInfoFormView();
	}

	@RequestMapping(value = "/review", method = RequestMethod.GET)
	public String displayReviewProfileView(HttpServletRequest request,
										   HttpServletResponse response,
										   Model model,
										   @ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
										   BindingResult errors) {
		if ((ProfileForm.STEP_ACCOUNT_INFO.equalsIgnoreCase(profileForm.getStep())) ||
		 (ProfileForm.STEP_BIQ_INFO.equalsIgnoreCase(profileForm.getStep()))) {
			profileForm.setStep(ProfileForm.STEP_REVIEW_INFO);
			if(eregUtils.isDBAuthentication())
			{
				model.addAttribute("prev_url", "/secure/profile/account");
				model.addAttribute("auth", "DB");
			}
			else
			{
				model.addAttribute("prev_url", "/secure/profile/background");
				model.addAttribute("auth", "OAM");
			}
			return getReviewProfileView();
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public String createProfile(HttpServletRequest request,
								HttpServletResponse response,
								HttpSession session,
								Model model,
								@ModelAttribute(ProfileController.PROFILE_FORM) ProfileForm profileForm,
								BindingResult errors,
								SessionStatus sessionStatus) {

        //String backUrl = request.getRequestURI();
	    session.setAttribute("viewProfileBackUrl", "/profile/review"); 
		if (ProfileForm.STEP_REVIEW_INFO.equalsIgnoreCase(profileForm.getStep())) {
		    UserVO user = profileBusinessService.saveProfileByCSR(profileForm.getProfile());
			profileBusinessService.registerProfile(profileForm.getProfile());
			if(eregUtils.isOAMAuthentication())
			{
				profileBusinessService.updateCustomerGuid(user, profileForm.getProfile().getCustomer().getId());
			}
			model.addAttribute("userID",user.getUserID());
			model.addAttribute("username",profileForm.getProfile().getCustomer().getFirstName()+" "+profileForm.getProfile().getCustomer().getLastName());
			sessionStatus.setComplete();
			return getAccountCreatedView();
		} else {
			return redirectPersonalInfoFormView();
		}
	}

	@ModelAttribute(ProfileController.PROFILE_FORM)
	public ProfileForm initProfileForm(HttpServletRequest request) {
		ProfileForm profileForm = new ProfileForm();
		profileForm.setProfile(profileBusinessService.createProfile());
    	ETSCountry us = (ETSCountry)profileBusinessService.findCountryByAbbreviation("US");
    	profileForm.getProfile().getAddress().setCountry(us);
    	profileForm.getProfile().getPrimaryPhone().setCountry(us);
    	profileForm.getProfile().getAlternatePhone().setCountry(us);
    	profileForm.getProfile().getCustomer().setMilitaryMemberShip(null);
    	RequestContext requestContext = new RequestContext(request);
    	List<DemographicQuestionVO> biqs=profileDemographicService.getDemographicQuestions(null, ProgramContextHolder.getProgramCode(), requestContext.getLocale().getLanguage().toUpperCase(), DemographicQuestionSetType.BIQ_TYPE_PROFILE, false);
    	profileForm.getProfile().setDemographicQuestions(biqs);
		return profileForm;
	}

	@ModelAttribute("countries")
	public List<Country> getCountries() {
		return profileBusinessService.findCountries();
	}

	@ModelAttribute("states")
	public List<State> getStates() {
		return profileBusinessService.findStates("US");
	}

    @ModelAttribute("phoneTypes")
	public List<PhoneType> getPhoneTypes() {
		return referenceEntityBusinessService.getAll(PhoneType.class, new ReferenceTypeCriteria(), false);
	}

	@ModelAttribute("genders")
	public List<Gender> getGenders() {
		return referenceEntityBusinessService.getAll(Gender.class, new ReferenceTypeCriteria(), false);
	}

	@ModelAttribute("ethnicityTypes")
	public List<EthnicityType> getEthnicityTypes() {
		return referenceEntityBusinessService.getAll(EthnicityType.class, new ReferenceTypeCriteria(), false);
	}

	@ModelAttribute("languageTypes")
	public List<LanguageType> getLanguageTypes() {
		ReferenceTypeCriteria reference = new ReferenceTypeCriteria();
		ReferenceTypeOrderBy firstOrder = new ReferenceTypeOrderBy();
		firstOrder.setAscending(true);
		firstOrder.setOrderByAttribute(ReferenceTypeCriteria.ORDER_BY_SEQUENCE_COLUMN);
		List<ReferenceTypeOrderBy> defaultOrderByList = new ArrayList<ReferenceTypeOrderBy>();
		defaultOrderByList.add(firstOrder);
		reference.setOrderbyList(defaultOrderByList);
		return referenceEntityBusinessService.getAll(LanguageType.class, reference, false);
	}

	@ModelAttribute("militaryStatusTypes")
	public List<MilitaryStatusType> getMilitaryStatusTypes() {
		return referenceEntityBusinessService.getAll(MilitaryStatusType.class, new ReferenceTypeCriteria(), false);
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

        binder.registerCustomEditor(Date.class, ProfileForm.DATE_OF_BIRTH, new PropertyEditorSupport() {
        	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        	@Override
            public void setAsText(String text) {
            	Date value = null;
            	dateFormat.setLenient(false);

        		try {
					value = dateFormat.parse(text);
				} catch (java.text.ParseException e) {
					log.error(e.toString());
				}
            	setValue(value);
            }

            @Override
            public String getAsText() {
            	Date value = (Date) getValue();
            	return null == value ? "" : dateFormat.format(value);
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

        binder.registerCustomEditor(LanguageType.class, ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
            	LanguageType value = referenceEntityBusinessService.getEntityByPrimaryKey(LanguageType.class, text);
            	setValue(value);
            }
        });

        binder.registerCustomEditor(LanguageType.class, ProfileForm.PRIMARY_SPEAKING_LANGUAGE, new PropertyEditorSupport() {
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
	public void setEregUtils(EregUtils eregUtils)
	{
		this.eregUtils = eregUtils;
	}
	
	public EregUtils getEregUtils()
	{
		return eregUtils;
	}
}
