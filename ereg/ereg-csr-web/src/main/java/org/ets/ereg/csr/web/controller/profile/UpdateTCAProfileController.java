package org.ets.ereg.csr.web.controller.profile;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.service.GenericResponse;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.csr.web.form.profile.TCAProfileForm;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.csr.web.util.ProfileUtil;
import org.ets.ereg.csr.web.validator.profile.TCAProfileFormValidator;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(Constant.TCA_SECURE_PROFILE)
@SessionAttributes(Constant.TCA_PROFILE_FORM)
public class UpdateTCAProfileController {

	private static Logger log = LoggerFactory.getLogger(UpdateTCAProfileController.class);

	@Resource(name="etsAdminUserBusinessService")
	ETSAdminUserBusinessService etsAdminUserBusinessService;

    @Resource(name = "referenceEntityBusinessService")
    private ReferenceBusinessService referenceEntityBusinessService;

	@Resource(name="tcaProfileFormValidator")
	TCAProfileFormValidator tcaProfileFormValidator;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constant.CREATE_PROFILE_DATE_PATTERN);

    @RequestMapping(method = RequestMethod.GET)
    public String viewProfile(HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
            BindingResult errors) {
        String userName = null;

        if(SecurityContextHolder.getContext().getAuthentication() != null){
            userName = SecurityContextHolder.getContext().getAuthentication().getName();
        } else{
            return Constant.LOGIN_PAGE;
        }

        ETSAdminUser adminUser =  etsAdminUserBusinessService.readAdminUserByUserName(userName);
        TCAProfileVO tcaProfile = new TCAProfileVO();

        tcaProfile.setAdminUser(adminUser);
        if(adminUser.getAdminUserPhone()!=null){
            adminUser.setEtsPhone(adminUser.getAdminUserPhone().getEtsPhone());
        }
        tcaProfileForm.setTcaProfile(tcaProfile);
        tcaProfileForm.transfereToFormDateOfBirth();
        return Constant.PROFILE_VIEW;
    }



    @RequestMapping(method = RequestMethod.POST)
    public String getUpdateView(@ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
                                SessionStatus sessionStatus) {
        TCAProfileVO tcaProfile = etsAdminUserBusinessService.readByAdminUserId(tcaProfileForm.getTcaProfile().getAdminUser().getId());
        if (null != tcaProfile) {
            if(tcaProfile.getAdminUser().getAdminUserPhone()!=null){
                tcaProfile.getAdminUser().setEtsPhone(tcaProfile.getAdminUser().getAdminUserPhone().getEtsPhone());
            } else{
                createAdminPhone(tcaProfile.getAdminUser());
            }
            tcaProfileForm.setTcaProfile(tcaProfile);
            sessionStatus.setComplete();
        }
        return Constant.PROFILE_UPDATE_VIEW;
    }

    @RequestMapping(value = Constant.EDIT_PROFILE, method = RequestMethod.POST)
    public String updateProfile(
            @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
            BindingResult errors,
            SessionStatus sessionStatus, final RedirectAttributes redirectAttributes, Principal principal) {
        tcaProfileForm.transfereFromFormDateOfBirth();
        tcaProfileFormValidator.validateContactInfo(tcaProfileForm, errors);
        tcaProfileFormValidator.validatePersonalInfo(tcaProfileForm, errors);
        tcaProfileFormValidator.validateUpdateEmailAddress(tcaProfileForm, errors);
        if (errors.hasErrors()) {
            return Constant.PROFILE_UPDATE_VIEW;
        } else if(principal!=null){
                TCAProfileVO tcaProfile = etsAdminUserBusinessService.readByAdminUserId(tcaProfileForm.getTcaProfile().getAdminUser().getId());
                tcaProfile=prepareProfileVOForUpdate(tcaProfileForm.getTcaProfile(),tcaProfile);
                etsAdminUserBusinessService.saveTCAProfile(tcaProfile, Boolean.FALSE);
                redirectAttributes.addFlashAttribute(Constant.STATUS_MESSAGE, "Profile updated successfuly.");
                sessionStatus.setComplete();
                return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
            }
        else{
            return Constant.ACCESS_ERROR_PAGE;
        }

    }

    @RequestMapping(value = Constant.CHANGE_PASSWORD, method = RequestMethod.GET)
    public String updateProfileChangePassword(
            @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
            BindingResult errors,
            SessionStatus sessionStatus, Principal principal) {
        if(principal!=null){
            ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(principal.getName());
            if(adminUser==null){
                return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
            }
            else{
                TCAProfileVO tcaProfile = etsAdminUserBusinessService.readByAdminUserId(adminUser.getId());
                tcaProfileForm.setTcaProfile(tcaProfile);
                tcaProfileForm.setStep(Constant.STEP_CHANGE_PASSWORD);

            }
        }
        return Constant.CHANGE_PASSWORD_VIEW;
    }

    @RequestMapping(value = Constant.CHANGE_PASSWORD, method=RequestMethod.POST)
    public String postUpdateProfileChangePassword(
            @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
            BindingResult errors,
            SessionStatus sessionStatus, final RedirectAttributes redirectAttributes, Principal principal) {
        if(principal!=null){
            ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(principal.getName());
            if(adminUser==null){
                return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
            }
            else{
                tcaProfileFormValidator.validateChangePassword(tcaProfileForm, errors);
                if (errors.hasErrors()) {
                    return Constant.CHANGE_PASSWORD_VIEW;
                } else{
                GenericResponse response = etsAdminUserBusinessService.changePassword(principal.getName(), tcaProfileForm.getTcaProfile().getAdminUser().getOldPassword(), tcaProfileForm.getTcaProfile().getAdminUser().getPassword(), tcaProfileForm.getTcaProfile().getAdminUser().getPasswordConfirm());
                if(response.getHasErrors()){
                    if(response.getErrorCodesList().contains(Constant.INVALID_PASSWORD)){
                        errors.rejectValue(Constant.OLD_PASSWORD,Constant.PASSWORD_INVALID_MESSAGE);
                    }
                    else if (response.getErrorCodesList().contains(Constant.PASSWORD_MISMATCH)){
                        errors.rejectValue(Constant.PASSWORD,Constant.PASSWORD_MISMATCH_MESSAGE);
                        errors.rejectValue(Constant.PASSWORD_CONFIRM,Constant.PASSWORD_MISMATCH_MESSAGE);
                    }
                    return Constant.CHANGE_PASSWORD_VIEW;
                }
                redirectAttributes.addFlashAttribute(Constant.STATUS_MESSAGE, "Password changed successfuly");
                }

            }

            }
        return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
    }


    @RequestMapping(value=Constant.CHANGE_SECURITY_QUESTION, method=RequestMethod.GET)
    public String onGetChangeSecurityQuestion(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
            BindingResult errors, Principal principal){
        if(principal!=null){
            ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(principal.getName());
            if(adminUser==null){
                return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
            } else{
                TCAProfileVO tcaProfile = etsAdminUserBusinessService.readByAdminUserId(adminUser.getId());
                tcaProfileForm.setTcaProfile(tcaProfile);
                tcaProfileForm.setStep(Constant.STEP_CHANGE_SECURITY_QUESTION);
                return Constant.SECURITY_QUESTION_VIEW;
        }
        }
        return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
    }

    @RequestMapping(value=Constant.CHANGE_SECURITY_QUESTION, method=RequestMethod.POST)
    public String onPostChangeSecurityQuestion(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @ModelAttribute(Constant.TCA_PROFILE_FORM) TCAProfileForm tcaProfileForm,
            BindingResult errors,
            final RedirectAttributes redirectAttributes, Principal principal) throws ServiceException {
        if(principal!=null && principal.getName().equalsIgnoreCase(tcaProfileForm.getTcaProfile().getAdminUser().getLogin())
                && Constant.STEP_CHANGE_SECURITY_QUESTION.equalsIgnoreCase(tcaProfileForm.getStep())){
            tcaProfileFormValidator.validateChangeSecurityQuestion(tcaProfileForm, errors);
            if(errors.hasErrors()){
                return Constant.SECURITY_QUESTION_VIEW;
            }
            else{
                TCAProfileVO tcaProfile = etsAdminUserBusinessService.authenthicate(tcaProfileForm.getTcaProfile().getAdminUser().getLogin(), tcaProfileForm.getTcaProfile().getAdminUser().getOldPassword());
                if(null == tcaProfile){
                    errors.rejectValue(Constant.OLD_PASSWORD, "passwordchange.currentPasswordIncorrect");
                    return Constant.SECURITY_QUESTION_VIEW;
                }
                else{
                    etsAdminUserBusinessService.saveTCAProfile(tcaProfileForm.getTcaProfile(), Boolean.FALSE);
                    redirectAttributes.addFlashAttribute(Constant.STATUS_MESSAGE, "Security question/answer changed successfuly.");
                    return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
                }
            }
        }
        return Constant.HOME_PAGE_SECURE_REDIRECT_VIEW;
    }

    private TCAProfileVO prepareProfileVOForUpdate(TCAProfileVO sourceProfile,TCAProfileVO destProfile){

        destProfile.getAdminUser().setFirstName(sourceProfile.getAdminUser().getFirstName());
        destProfile.getAdminUser().setLastName(sourceProfile.getAdminUser().getLastName());
        destProfile.getAdminUser().setMiddleName(sourceProfile.getAdminUser().getMiddleName());
        destProfile.getAdminUser().setEmail(sourceProfile.getAdminUser().getEmail());
        destProfile.getAdminUser().setDateOfBirth(sourceProfile.getAdminUser().getDateOfBirth());
        destProfile.getAdminUser().setEmail(sourceProfile.getAdminUser().getEmail());
        destProfile.getAdminUser().setGender(sourceProfile.getAdminUser().getGender());
        destProfile.getAdminUser().setEtsPhone(sourceProfile.getAdminUser().getEtsPhone());
        destProfile.getAdminUser().setAdminIdentificationStr(sourceProfile.getAdminUser().getAdminIdentificationStr());
        return destProfile;
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

    @ModelAttribute("countries")
    public List<Country> getCountries() {
        return etsAdminUserBusinessService.findCountries();
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

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, Constant.DATE_OF_BIRTH, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                Date value = null;
                try {
                    DATE_FORMAT.setLenient(false);
                    value = DATE_FORMAT.parse(text);
                } catch (java.text.ParseException e) {
                    log.error(e.toString());
                }
                setValue(value);
            }

            @Override
            public String getAsText() {
                DATE_FORMAT.setLenient(false);
                Date value = (Date) getValue();
                return null == value ? "" : DATE_FORMAT.format(value);
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

    @ModelAttribute(Constant.TCA_PROFILE_FORM)
    public TCAProfileForm initProfileForm() {
        TCAProfileForm tcaProfileForm = new TCAProfileForm();
        tcaProfileForm.setTcaProfile(etsAdminUserBusinessService.createProfile());
        ETSCountry us = (ETSCountry)etsAdminUserBusinessService.findCountryByAbbreviation("US");
        tcaProfileForm.getTcaProfile().getAdminUser().getEtsPhone().setCountry(us);
        return tcaProfileForm;
    }

    private ETSAdminUser createAdminPhone(ETSAdminUser etsAdminUser){
        etsAdminUserBusinessService.createPhone(etsAdminUser);
        ETSCountry us = (ETSCountry)etsAdminUserBusinessService.findCountryByAbbreviation("US");
        etsAdminUser.getEtsPhone().setCountry(us);
        return etsAdminUser;
    }



}
