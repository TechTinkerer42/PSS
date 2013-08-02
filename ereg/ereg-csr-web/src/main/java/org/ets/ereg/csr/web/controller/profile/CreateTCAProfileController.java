package org.ets.ereg.csr.web.controller.profile;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.csr.web.controller.login.AdminLoginUtil;
import org.ets.ereg.csr.web.form.profile.TCAProfileForm;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.csr.web.util.ProfileUtil;
import org.ets.ereg.csr.web.validator.profile.TCAProfileFormValidator;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.profile.service.AdminLoginService;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(Constant.TCA_PROFILE)
@SessionAttributes(Constant.TCA_PROFILE_FORM)
public class CreateTCAProfileController {

	private static Logger log = LoggerFactory.getLogger(CreateTCAProfileController.class);

	@Resource(name="etsAdminUserBusinessService")
	ETSAdminUserBusinessService etsAdminUserBusinessService;

    @Resource(name = "referenceEntityBusinessService")
    private ReferenceBusinessService referenceEntityBusinessService;

	@Resource(name="tcaProfileFormValidator")
	TCAProfileFormValidator tcaProfileFormValidator;

    @Resource(name="applicationConfigurationService")
    private ApplicationConfigurationService applicationService;

    @Resource(name="adminLoginService")
    private AdminLoginService adminLoginService ;

	@ModelAttribute(Constant.TCA_PROFILE_FORM)
    public TCAProfileForm initProfileForm() {
	    TCAProfileForm tcaProfileForm = new TCAProfileForm();
	    tcaProfileForm.setTcaProfile(etsAdminUserBusinessService.createProfile());
	    tcaProfileForm.transfereToFormDateOfBirth();
	    ETSCountry us = (ETSCountry)etsAdminUserBusinessService.findCountryByAbbreviation("US");
	    tcaProfileForm.getTcaProfile().getAdminUser().getEtsPhone().setCountry(us);
        return tcaProfileForm;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String newProfile(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model,
                             @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                             BindingResult errors) {
        tcaProfileForm.setStep(Constant.STEP_PERSONAL_INFO);
        return Constant.PERSONAL_INFO_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processPersonalInfo(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model,
                                     @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                                     BindingResult errors) throws ServiceException {
        if (Constant.STEP_PERSONAL_INFO.equalsIgnoreCase(tcaProfileForm.getStep())) {
            tcaProfileForm.transfereFromFormDateOfBirth();
            tcaProfileFormValidator.validatePersonalInfo(tcaProfileForm, errors);
            tcaProfileFormValidator.validateContactInfo(tcaProfileForm, errors);
            if (!errors.hasErrors()) {
                DupCheckResponseObject dupCheckResponseObj =
                        etsAdminUserBusinessService.checkDuplicateProfile(tcaProfileForm.getTcaProfile());
                if (dupCheckResponseObj.isDuplicate()) {
                    tcaProfileForm.setStep(Constant.STEP_DUPLICATE_TCA_PROFILE);
                    return Constant.DUPLICATE_ACCOUNT_REDIRECT_VIEW;
                } else {
                    return Constant.ACCOUNT_REDIRECT_VIEW;
                }
            } else {
                return Constant.PERSONAL_INFO_VIEW;
            }
        } else {
            return Constant.PERSONAL_INFO_REDIRECT_VIEW;
        }
    }

    @RequestMapping(value = Constant.DUPLICATE, method = RequestMethod.GET)
    public String displayDuplicateAccountView(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                          BindingResult errors) {
        if (Constant.STEP_DUPLICATE_TCA_PROFILE.equalsIgnoreCase(tcaProfileForm.getStep())) {
            model=AdminLoginUtil.setAuthAttributes(request, model, applicationService);
            return Constant.DUPLICATE_ACCOUNT_VIEW;
        } else {
            return Constant.PERSONAL_INFO_REDIRECT_VIEW;
        }
    }

    @RequestMapping(value = Constant.ACCOUNT, method = RequestMethod.GET)
    public String displayCreateAccountFormView(HttpServletRequest request,
                                               HttpServletResponse response,
                                               Model model,
                                               @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                                               BindingResult errors) {
        if (Constant.STEP_PERSONAL_INFO.equalsIgnoreCase(tcaProfileForm.getStep())
                || Constant.STEP_REVIEW_INFO.equalsIgnoreCase(tcaProfileForm.getStep())) {
            tcaProfileForm.setStep(Constant.STEP_ACCOUNT_INFO);
            return Constant.ACCOUNT_INFO_VIEW;
        } else {
            return Constant.PERSONAL_INFO_REDIRECT_VIEW;
        }
    }

    @RequestMapping(value = Constant.ACCOUNT, method = RequestMethod.POST)
    public String processAccountInfo(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model,
                                     @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                                     BindingResult errors) {
        if (Constant.STEP_ACCOUNT_INFO.equalsIgnoreCase(tcaProfileForm.getStep())) {
            tcaProfileFormValidator.validateAccountInfo(tcaProfileForm, errors);
            if (!errors.hasErrors()) {
                return Constant.REVIEW_REDIRECT_VIEW;
            } else {
                return Constant.ACCOUNT_INFO_VIEW;
            }
        } else {
            return Constant.PERSONAL_INFO_REDIRECT_VIEW;
        }
    }

    @RequestMapping(value = Constant.REVIEW, method = RequestMethod.GET)
    public String displayReviewProfileView(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Model model,
                                           @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                                           BindingResult errors) {
        if (Constant.STEP_ACCOUNT_INFO.equalsIgnoreCase(tcaProfileForm.getStep())) {
            tcaProfileForm.setStep(Constant.STEP_REVIEW_INFO);
            return Constant.REVIEW_VIEW;
        } else {
            return Constant.PERSONAL_INFO_REDIRECT_VIEW;
        }
    }

    @RequestMapping(value = Constant.REVIEW, method = RequestMethod.POST)
    public String createProfile(HttpServletRequest request,
                                HttpServletResponse response,
                                Model model,
                                @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                                BindingResult errors,
                                SessionStatus sessionStatus) {
        if (Constant.STEP_REVIEW_INFO.equalsIgnoreCase(tcaProfileForm.getStep())) {
            String guid = etsAdminUserBusinessService.saveTCAProfile(tcaProfileForm.getTcaProfile(), Boolean.TRUE);
            etsAdminUserBusinessService.updateGUID(guid, tcaProfileForm.getTcaProfile().getAdminUser().getId());
            adminLoginService.adminLogin(tcaProfileForm.getTcaProfile().getAdminUser().getLogin(), tcaProfileForm.getTcaProfile().getAdminUser().getPassword());
            sessionStatus.setComplete();
            return Constant.ACCOUNT_VIEW;
        } else {
            return Constant.PERSONAL_INFO_REDIRECT_VIEW;
        }
    }

    @ModelAttribute("phoneTypes")
    public List<PhoneType> getPhoneTypes() {
        return referenceEntityBusinessService.getAll(PhoneType.class, new ReferenceTypeCriteria(), false);
    }

    @ModelAttribute("genders")
    public List<Gender> getGenders() {
        return referenceEntityBusinessService.getAll(Gender.class, new ReferenceTypeCriteria(), false);
    }

    @ModelAttribute("challengeQuestions")
    public List<ChallengeQuestion> getChallengeQuestions()
    {
        return etsAdminUserBusinessService.readChallengeQuestions();
    }

    @ModelAttribute("months")
    public List<String> getMonths()
    {
        return ProfileUtil.getMonths();
    }

    @ModelAttribute("days")
    public List<String> getDays()
    {
        return ProfileUtil.getDays();
    }

    @ModelAttribute("countries")
    public List<Country> getCountries() {
        return etsAdminUserBusinessService.findCountries();
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, Constant.DATE_OF_BIRTH, new PropertyEditorSupport() {
            private SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.CREATE_PROFILE_DATE_PATTERN);

            @Override
            public void setAsText(String text) {
                Date value = null;
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

        binder.registerCustomEditor(Gender.class, Constant.GENDER, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Gender value = referenceEntityBusinessService.getEntityByPrimaryKey(Gender.class, text);
                setValue(value);
            }
        });

        binder.registerCustomEditor(ChallengeQuestion.class, Constant.SECURITY_QUESTION, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                ChallengeQuestion challengeQuestion = null;
                if(null != text && !text.equalsIgnoreCase("")){
                    challengeQuestion = etsAdminUserBusinessService.readChallengeQuestionById(Long.parseLong(text));
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

        binder.registerCustomEditor(PhoneType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                PhoneType value = referenceEntityBusinessService.getEntityByPrimaryKey(PhoneType.class, text);
                setValue(value);
            }
        });

        binder.registerCustomEditor(Country.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Country country = etsAdminUserBusinessService.findCountryByAbbreviation(text);
                setValue(country);
            }
        });

    }



}
