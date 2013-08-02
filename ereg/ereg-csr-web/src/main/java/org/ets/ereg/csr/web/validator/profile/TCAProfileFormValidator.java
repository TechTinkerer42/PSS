package org.ets.ereg.csr.web.validator.profile;

import org.ets.ereg.csr.web.form.profile.TCAProfileForm;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface TCAProfileFormValidator extends Validator {

    public abstract void validateContactInfo(TCAProfileForm form, Errors errors);

    public abstract void validatePersonalInfo(TCAProfileForm form, Errors errors);

    public abstract void validateAccountInfo(TCAProfileForm form, Errors errors);

    public abstract void validateChangePassword(TCAProfileForm form, Errors errors);

    public abstract void validateChangeSecurityQuestion(
            TCAProfileForm tcaProfileForm, BindingResult errors);

    void validateUpdateEmailAddress(TCAProfileForm tcaProfileForm, Errors errors);


}
