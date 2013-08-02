package org.ets.ereg.csr.web.validator.profile;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.validator.GenericValidator;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.ets.ereg.csr.web.form.profile.TCAProfileForm;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;


@Component("tcaProfileFormValidator")
public class TCAProfileFormValidatorImpl implements TCAProfileFormValidator {

    @Resource(name="etsAdminUserBusinessService")
    ETSAdminUserBusinessService etsAdminUserBusinessService;

	@Override
	public boolean supports(Class<?> clazz) {
	    return TCAProfileForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

	}

	@Override
    public void validateContactInfo(TCAProfileForm form, Errors errors) {
	    ETSPhone phone = form.getTcaProfile().getAdminUser().getEtsPhone();
	    validatePhone(phone, errors);
    }

	private void validatePhone(ETSPhone phoneNumber, Errors errors) {
        if (null == phoneNumber.getCountry()) {
            errors.rejectValue(Constant.PHONE_COUNTRY_CODE, "profile.create.validation.PhoneCountryCodeRequired");
        }
        if (GenericValidator.isBlankOrNull(phoneNumber.getPhoneNumber())) {
            errors.rejectValue(Constant.PHONE_NUMBER, "profile.create.validation.phoneRequired");
        } else if (!phoneNumber.getPhoneNumber().matches("[1-9][0-9]{9}")) {
            errors.rejectValue(Constant.PHONE_NUMBER, "profile.create.validation.phoneInvalid");
        }
        if (!GenericValidator.isBlankOrNull(phoneNumber.getPhoneExtension()) &&
                !phoneNumber.getPhoneExtension().matches("[0-9]{1,5}")) {
            errors.rejectValue(Constant.PHONE_EXTENSION, "profile.create.validation.phoneExtensionInvalid");
        }
        if (null == phoneNumber.getPhoneType()) {
            errors.rejectValue(Constant.PHONE_TYPE, "profile.create.validation.PhoneTypeRequired");
        }

    }

    @Override
    public void validatePersonalInfo(TCAProfileForm form, Errors errors) {
        ETSAdminUser adminUser = form.getTcaProfile().getAdminUser();

        validateFirstName(adminUser.getFirstName(), errors);
        validateMiddleInitial(adminUser.getMiddleName(), errors);
        validateLastName(adminUser.getLastName(), errors);
        validateEmailAddress(adminUser.getEmail(), errors);
        validateDateOfBirth(adminUser.getDateOfBirth(), errors);
        validateGender(adminUser.getGender(), errors);
     }

    @Override
    public void validateAccountInfo(TCAProfileForm form, Errors errors) {
        ETSAdminUser adminUser = form.getTcaProfile().getAdminUser();

        validateUsername(adminUser.getLogin(), errors);


        //password
        validatePassword(adminUser.getPassword(), errors);
        validateConfirmPassword(adminUser.getPasswordConfirm(), errors);
        compareBothPasswords(adminUser.getPassword(), adminUser.getPasswordConfirm(), errors);

    }

    private void compareBothPasswords(String password, String passwordConfirm, Errors errors) {
        if( null != password && null != passwordConfirm && !passwordConfirm.equals(password)){
            errors.rejectValue(Constant.PASSWORD, "profile.create.validation.passwordConfirmInvalid");
        }

    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z][a-z]*([\\-|\\s'][A-Z]?[a-z]+)*");
    }

    private void validateFirstName(String firstName, Errors errors) {
        if (GenericValidator.isBlankOrNull(firstName)) {
            errors.rejectValue(Constant.FIRST_NAME, "profile.create.validation.FirstNameRequired");
        } else if (!isValidName(firstName)) {
            errors.rejectValue(Constant.FIRST_NAME, "profile.create.validation.FirstNameInvalid");
        }
    }

    private void validateMiddleInitial(String middleInitial, Errors errors) {
        if (!GenericValidator.isBlankOrNull(middleInitial) &&
                !isValidName(middleInitial)) {
            errors.rejectValue(Constant.MIDDLE_INITIAL, "profile.create.validation.MiddleInitialInvalid");
        }
    }

    private void validateLastName(String lastName, Errors errors) {
        if (GenericValidator.isBlankOrNull(lastName)) {
            errors.rejectValue(Constant.LAST_NAME, "profile.create.validation.LastNameRequired");
        } else if (!isValidName(lastName)) {
            errors.rejectValue(Constant.LAST_NAME, "profile.create.validation.LastNameInvalid");
        }
    }

    private void validateEmailAddress(String emailAddress, Errors errors) {
        if (GenericValidator.isBlankOrNull(emailAddress)) {
            errors.rejectValue(Constant.EMAIL_ADDRESS, "profile.create.validation.EmailAddressRequired");
        } else if (!GenericValidator.isEmail(emailAddress)) {
            errors.rejectValue(Constant.EMAIL_ADDRESS, "profile.create.validation.EmailAddressIinvalid");
        }
    }

    private void validateUsername(String userName, Errors errors) {
        if (GenericValidator.isBlankOrNull(userName)) {
            errors.rejectValue(Constant.USERNAME, "profile.create.validation.usernameRequired");
        } else {
            if (!userName.matches("[a-zA-z0-9]{4,16}")) {
                errors.rejectValue(Constant.USERNAME, "profile.create.validation.usernameInvalid");
            } else {
                if (!etsAdminUserBusinessService.isUsernameAvailable(userName)) {
                    errors.rejectValue(Constant.USERNAME, "profile.create.validation.usernameUsed");
                }
            }
        }
    }

    private void validatePassword(String password, Errors errors) {
        if (GenericValidator.isBlankOrNull(password)) {
            errors.rejectValue(Constant.PASSWORD, "profile.create.validation.passwordRequired");
        } else if (!password.matches("[a-zA-z0-9!@#$%&*^+=]{6,16}")) {
                errors.rejectValue(Constant.PASSWORD, "profile.create.validation.passwordInvalid");
        }
    }

    private void validateConfirmPassword(String password, Errors errors) {
        if (GenericValidator.isBlankOrNull(password)) {
            errors.rejectValue(Constant.PASSWORD_CONFIRM, "profile.create.validation.passwordRequired");
        } else if (!password.matches("[a-zA-z0-9!@#$%&*^+=]{6,16}")) {
                errors.rejectValue(Constant.PASSWORD_CONFIRM, "profile.create.validation.passwordInvalid");
        }
    }

    private void validateDateOfBirth(Date dateOfBirth, Errors errors) {

        if (null == dateOfBirth) {
            errors.rejectValue(Constant.DATE_OF_BIRTH, "profile.create.validation.dateOfBirthRequired");
        } else {

            if(dateOfBirth.after(new Date())){
            errors.rejectValue(Constant.DATE_OF_BIRTH, "profile.create.validation.dateOfBirthFuturedate");
        }
        }
    }

    private void validateGender(Gender gender, Errors errors) {
        if (null == gender) {
            errors.rejectValue(Constant.GENDER, "profile.create.validation.genderRequired");
        }
    }

    @Override
    public void validateChangePassword(TCAProfileForm form, Errors errors) {
        validatePassword(form.getTcaProfile().getAdminUser().getPassword(), errors);

    }

    private static void validateSecurityQuestion(ChallengeQuestion securityQuestion, Errors errors){
        if(null == securityQuestion){
            errors.rejectValue(Constant.SECURITY_QUESTION, "securityQuestion.required");
        }
    }

    private static void validateSecurityAnswer(String securityAnswer, Errors errors){
        if(GenericValidator.isBlankOrNull(securityAnswer)){
            errors.reject(Constant.SECURITY_ANSWER, "securityAnswer.required");
        }
    }

    @Override
    public void validateChangeSecurityQuestion(TCAProfileForm tcaProfileForm,
            BindingResult errors) {
            //old password
        if (GenericValidator.isBlankOrNull(Constant.OLD_PASSWORD)) {
            errors.rejectValue(Constant.OLD_PASSWORD, "profile.create.validation.passwordRequired");
        }

            //security question
            validateSecurityQuestion(tcaProfileForm.getTcaProfile().getAdminUser().getChallengeQuestion(), errors);

            //security answer
            validateSecurityAnswer(tcaProfileForm.getTcaProfile().getAdminUser().getChallengeAnswer(), errors);
        }

    @Override
    public void validateUpdateEmailAddress(TCAProfileForm tcaProfileForm, Errors errors) {
        List<AdminUser> adminUsers = etsAdminUserBusinessService.readUserByEmail(tcaProfileForm.getTcaProfile().getAdminUser().getEmail());
        if(adminUsers!=null && adminUsers.size()>0 && !adminUsers.get(0).getId().equals(tcaProfileForm.getTcaProfile().getAdminUser().getId())){
            errors.rejectValue(Constant.EMAIL_ADDRESS, "profile.create.validation.EmailAddressUsed");
        }
    }

}

