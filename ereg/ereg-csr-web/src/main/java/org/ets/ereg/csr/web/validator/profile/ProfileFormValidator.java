package org.ets.ereg.csr.web.validator.profile;

import org.ets.ereg.csr.web.form.profile.ProfileForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface ProfileFormValidator extends Validator {

	public abstract void validateContactInfo(ProfileForm form, Errors errors);

	public abstract void validatePersonalInfo(ProfileForm form, Errors errors);

	public abstract void validateAccountInfo(ProfileForm form, Errors errors);
	
	public abstract void validateEIASPassword(ProfileForm form, Errors errors);
	
	public abstract void validateNonEIASPassword(ProfileForm form, Errors errors);
	
}
