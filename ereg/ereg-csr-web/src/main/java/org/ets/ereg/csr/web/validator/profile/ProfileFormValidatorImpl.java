package org.ets.ereg.csr.web.validator.profile;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.validator.GenericValidator;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.State;
import org.ets.ereg.csr.web.controller.appointment.AppointmentController;
import org.ets.ereg.csr.web.form.profile.ProfileForm;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component("profileFormValidator")
public class ProfileFormValidatorImpl implements ProfileFormValidator {

    private static final String VALID_NAME_REGEX = "[a-zA-Z\\-'\\s]+";
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(AppointmentController.class);

	@Resource(name = "referenceEntityBusinessService")
	private ReferenceBusinessService referenceEntityBusinessService;

	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;

	public ProfileFormValidatorImpl() {

	}

	public ReferenceBusinessService getReferenceEntityBusinessService() {
		return referenceEntityBusinessService;
	}

	public void setReferenceEntityBusinessService(
			ReferenceBusinessService referenceEntityBusinessService) {
		this.referenceEntityBusinessService = referenceEntityBusinessService;
	}

	public ProfileBusinessService getProfileBusinessService() {
		return profileBusinessService;
	}

	public void setProfileBusinessService(
			ProfileBusinessService profileBusinessService) {
		this.profileBusinessService = profileBusinessService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfileForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {

	}

	@Override
	public void validateContactInfo(ProfileForm form, Errors errors) {
		ETSCustomer customer = form.getProfile().getCustomer();
		ETSAddress address = form.getProfile().getAddress();
		ETSPhone primaryPhone = form.getProfile().getPrimaryPhone();
		ETSPhone alternatePhone = form.getProfile().getAlternatePhone();

		validateFirstName(customer.getFirstName(), errors);
		validateMiddleInitial(customer.getMiddleInitial(), errors);
		validateLastName(customer.getLastName(), errors);
		validateDateOfBirth(customer.getDateOfBirth(), errors);
		validateGender(customer.getGender(), errors);
		validateSocialSecurity(customer.getSocialSecurity(), errors);
		validateEmailAddress(customer.getEmailAddress(), errors);

		validateCountry(address.getCountry(), errors);
		validateAddressLine1(address.getAddressLine1(), errors);
		validateCity(address.getCity(), errors);
		validateState(address.getState(), errors);
		validatePostalCode(address.getPostalCode(), errors);

		validatePrimaryPhone(primaryPhone, errors);

		validateAlternatePhone(alternatePhone, errors);
	}

	private boolean isValidName(String name) {
		return name.matches(VALID_NAME_REGEX);
	}

	private void validateFirstName(String firstName, Errors errors) {
		if (GenericValidator.isBlankOrNull(firstName)) {
			errors.rejectValue(ProfileForm.FIRST_NAME, "profile.create.validation.FirstNameRequired");
		} else if (!isValidName(firstName)) {
			errors.rejectValue(ProfileForm.FIRST_NAME, "profile.create.validation.FirstNameInvalid");
		}
	}

	private void validateMiddleInitial(String middleInitial, Errors errors) {
		if (!GenericValidator.isBlankOrNull(middleInitial) &&
				!middleInitial.matches("[a-zA-z]{1}")) {
			errors.rejectValue(ProfileForm.MIDDLE_INITIAL, "profile.create.validation.MiddleInitialInvalid");
		}
	}

	private void validateLastName(String lastName, Errors errors) {
		if (GenericValidator.isBlankOrNull(lastName)) {
			errors.rejectValue(ProfileForm.LAST_NAME, "profile.create.validation.LastNameRequired");
		} else if (!isValidName(lastName)) {
			errors.rejectValue(ProfileForm.LAST_NAME, "profile.create.validation.LastNameInvalid");
		}
	}

	private void validateDateOfBirth(Date dateOfBirth, Errors errors) {
		if (null == dateOfBirth) {
			errors.rejectValue(ProfileForm.DATE_OF_BIRTH, "profile.create.validation.dateOfBirthRequired");
		}
	}

	private void validateGender(Gender gender, Errors errors) {
		if (null == gender) {
			errors.rejectValue(ProfileForm.GENDER, "profile.create.validation.genderRequired");
		}
	}

	private void validateSocialSecurity(String socialSecurity, Errors errors) {
		if (!GenericValidator.isBlankOrNull(socialSecurity) && !socialSecurity.matches("[0-9]{4}")) {
			errors.rejectValue(ProfileForm.SOCIAL_SECURITY, "profile.create.validation.socialSecurityInvalid");
		}
	}

	private void validateEmailAddress(String emailAddress, Errors errors) {
		if (GenericValidator.isBlankOrNull(emailAddress)) {
			errors.rejectValue(ProfileForm.EMAIL_ADDRESS, "profile.create.validation.EmailAddressRequired");
		} else if (!GenericValidator.isEmail(emailAddress)) {
			errors.rejectValue(ProfileForm.EMAIL_ADDRESS, "profile.create.validation.EmailAddressIinvalid");
		}
	}

	private void validateCountry(Country country, Errors errors) {
		if (null == country) {
			errors.rejectValue(ProfileForm.COUNTRY, "country.required");
		}
	}

	private void validateAddressLine1(String addressLine1, Errors errors) {
		if (GenericValidator.isBlankOrNull(addressLine1)) {
			errors.rejectValue(ProfileForm.ADDRESS_LINE1, "profile.create.validation.AddressLine1Required");
		}
	}

	private void validateCity(String city, Errors errors) {
		if (GenericValidator.isBlankOrNull(city)) {
			errors.rejectValue(ProfileForm.CITY, "profile.create.validation.CityRequired");
		} else if (!isValidName(city)) {
			errors.rejectValue(ProfileForm.CITY, "city.invalid");
		}
	}

	private void validateState(State state, Errors errors) {
		if (null == state) {
			errors.rejectValue(ProfileForm.STATE, "profile.create.validation.StateRequired");
		}
	}

	private void validatePostalCode(String postalCode, Errors errors) {
		if (GenericValidator.isBlankOrNull(postalCode)) {
			errors.rejectValue(ProfileForm.POSTAL_CODE, "profile.create.validation.PostalCodeRequired");
		} else if (!postalCode.matches("[0-9]{5}")) {
			errors.rejectValue(ProfileForm.POSTAL_CODE, "profile.create.validation.PostalCodeInvalid");
		}
	}

	private void validatePrimaryPhone(ETSPhone primaryPhone, Errors errors) {
		if (null == primaryPhone.getCountry()) {
			errors.rejectValue(ProfileForm.PRIMARY_PHONE_COUNTRY_CODE, "profile.create.validation.PhoneCountryCodeRequired");
		}
		if (GenericValidator.isBlankOrNull(primaryPhone.getPhoneNumber())) {
			errors.rejectValue(ProfileForm.PRIMARY_PHONE_PHONE_NUMBER, "profile.create.validation.phoneRequired");
		} else if (!primaryPhone.getPhoneNumber().matches("[1-9][0-9]{9}")) {
			errors.rejectValue(ProfileForm.PRIMARY_PHONE_PHONE_NUMBER, "profile.create.validation.phoneInvalid");
		}
		if (!GenericValidator.isBlankOrNull(primaryPhone.getPhoneExtension()) &&
				!primaryPhone.getPhoneExtension().matches("[0-9]{1,5}")) {
			errors.rejectValue(ProfileForm.PRIMARY_PHONE_EXTENSION, "profile.create.validation.PhoneExtensionInvalid");
		}
		if (null == primaryPhone.getPhoneType()) {
			errors.rejectValue(ProfileForm.PRIMARY_PHONE_PHONE_TYPE, "profile.create.validation.PhoneTypeRequired");
		}
	}

	private void validateAlternatePhone(ETSPhone alternatePhone, Errors errors) {
		if (!GenericValidator.isBlankOrNull(alternatePhone.getPhoneNumber())) {
			if (null == alternatePhone.getCountry()) {
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE, "profile.create.validation.PhoneCountryCodeRequired");
			}
			if (!alternatePhone.getPhoneNumber().matches("[1-9][0-9]{9}")) {
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER, "profile.create.validation.phoneInvalid");
			}
			if (!GenericValidator.isBlankOrNull(alternatePhone.getPhoneExtension()) &&
					!alternatePhone.getPhoneExtension().matches("[0-9]{1,5}")) {
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_EXTENSION, "profile.create.validation.PhoneExtensionInvalid");
			}
			if (null == alternatePhone.getPhoneType()) {
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_PHONE_TYPE, "profile.create.validation.PhoneTypeRequired");
			}
		}
	}

	@Override
	public void validatePersonalInfo(ProfileForm form, Errors errors) {
		ETSCustomer customer = form.getProfile().getCustomer();

		validatePreferredLanguage(customer.getPrfrdTstTakingLang(), errors);
		validateMilitaryStatus(customer.getMilitaryMemberShip(), customer.getMilitaryStatus(), errors);
	}

	private void validatePreferredLanguage(LanguageType language, Errors errors) {
		if (null == language) {
			errors.rejectValue(ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING, "profile.create.validation.prfrdTstTakingLangRequired");
		}
	}

	private void validateMilitaryStatus(Boolean isMilitaryMember,
										MilitaryStatusType militaryStatus,
										Errors errors) {
		if (null == isMilitaryMember) {
			errors.rejectValue(ProfileForm.IS_MILITARY_MEMBER, "profile.create.validation.militaryMemberShipRequired");
		} else {
			if (isMilitaryMember) {
				if (null == militaryStatus) {
					errors.rejectValue(ProfileForm.MILITARY_STATUS, "profile.create.validation.militaryStatusRequired");
				} else if ("NOTAM".equalsIgnoreCase(militaryStatus.getCode())) {
					errors.rejectValue(ProfileForm.MILITARY_STATUS, "profile.create.validation.militaryStatusInvalid");
				}
			} else {
				if (null != militaryStatus && !"NOTAM".equalsIgnoreCase(militaryStatus.getCode())) {
					errors.rejectValue(ProfileForm.MILITARY_STATUS, "profile.create.validation.militaryStatusInvalid");
				}
			}
		}
	}

	@Override
	public void validateAccountInfo(ProfileForm form, Errors errors) {
		ETSCustomer customer = form.getProfile().getCustomer();

		validateUsername(customer.getUsername(), errors);
		validatePassword(customer.getPassword(), errors);
	}

	private void validateUsername(String username, Errors errors) {
		if (GenericValidator.isBlankOrNull(username)) {
			errors.rejectValue(ProfileForm.USERNAME, "profile.create.validation.usernameRequired");
		} else {
			if (!username.matches("[a-zA-z0-9]{6,16}")) {
				errors.rejectValue(ProfileForm.USERNAME, "profile.create.validation.usernameInvalid");
			} else {
				if (!profileBusinessService.isUsernameAvailable(username)) {
					errors.rejectValue(ProfileForm.USERNAME, "profile.create.validation.usernameUsed");
				}
			}
		}
	}

	private void validatePassword(String password, Errors errors) {
		if (GenericValidator.isBlankOrNull(password)) {
			errors.rejectValue(ProfileForm.PASSWORD, "profile.create.validation.passwordRequired");
		}
	}
	@Override
	public void validateEIASPassword(ProfileForm profileForm, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.CANDIDATE_PASSWORD, "profile.create.validation.passwordRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.PASSWORD_CONFIRM, "profile.create.validation.passwordRequired");
		String password = profileForm.getPassword();

		if (!password.matches("^.*(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d\\W]).*$")) {
			errors.rejectValue(ProfileForm.CANDIDATE_PASSWORD, "profile.create.validation.passwordInvalid");
		}
		
		if(	null != password && null != profileForm.getPasswordConfirm() && 0 != profileForm.getPasswordConfirm().compareTo(password))
		{
			errors.rejectValue(ProfileForm.PASSWORD_CONFIRM, "profile.create.validation.passwordConfirmInvalid");
		}
	}
	@Override
	public void validateNonEIASPassword(ProfileForm profileForm, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.CANDIDATE_PASSWORD, "profile.create.validation.passwordRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.PASSWORD_CONFIRM, "profile.create.validation.passwordRequired");
		if(	null != profileForm.getPassword() && null != profileForm.getPasswordConfirm() && 0 != profileForm.getPasswordConfirm().compareTo(profileForm.getPassword()))
		{
			errors.rejectValue(ProfileForm.PASSWORD_CONFIRM, "profile.create.validation.passwordRequired");
		}
	}	
}