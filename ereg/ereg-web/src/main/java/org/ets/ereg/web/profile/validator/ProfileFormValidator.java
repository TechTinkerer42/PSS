package org.ets.ereg.web.profile.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.business.vo.biq.DmgrphQstnTriggerVO;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionRespType;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.web.profile.form.ProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("profileFormValidator")
public class ProfileFormValidator implements Validator {
	private static final String FIRST_NAME_REQUIRED = "profile.create.validation.FirstNameRequired";
	private static final String FIRST_NAME_INVALID = "profile.create.validation.FirstNameInvalid";
	private static final String MIDDLE_INITIAL_INVALID = "profile.create.validation.MiddleInitialInvalid";
	private static final String LAST_NAME_REQUIRED = "profile.create.validation.LastNameRequired";
	private static final String LAST_NAME_INVALID = "profile.create.validation.LastNameInvalid";
	private static final String DATE_OF_BIRTH_REQUIRED = "profile.create.validation.dateOfBirthRequired";
	private static final String GENDER_REQUIRED = "profile.create.validation.genderRequired";
	private static final String SOCIAL_SECURITY_REQUIRED = "profile.create.validation.socialSecurityRequired";
	private static final String SOCIAL_SECURITY_INVALID = "profile.create.validation.socialSecurityInvalid";
	private static final String ADDRESS_LINE1_REQUIRED = "profile.create.validation.AddressLine1Required";
	private static final String CITY_REQUIRED = "profile.create.validation.CityRequired";
	private static final String STATE_REQUIRED = "profile.create.validation.StateRequired";
	private static final String COUNTRY_REQUIRED = "profile.create.validation.CountryRequired";
	private static final String POSTAL_CODE_REQUIRED = "profile.create.validation.PostalCodeRequired";
	private static final String POSTAL_CODE_INVALID = "profile.create.validation.PostalCodeInvalid";
	private static final String PHONE_COUNTRY_CODE_REQUIRED = "profile.create.validation.PhoneCountryCodeRequired";
	private static final String PHONE_REQUIRED = "profile.create.validation.phoneRequired";
	private static final String PHONE_INVALID = "profile.create.validation.phoneInvalid";
	private static final String PHONE_EXTENSION_INVALID = "profile.create.validation.phoneExtensionInvalid";
	private static final String PHONE_TYPE_REQUIRED = "profile.create.validation.PhoneTypeRequired";
	private static final String EMAIL_ADDRESS_REQUIRED = "profile.create.validation.EmailAddressRequired";
	private static final String EMAIL_ADDRESS_INVALID = "profile.create.validation.EmailAddressIinvalid";
	private static final String USERNAME_REQUIRED = "profile.create.validation.usernameRequired";
	private static final String USERNAME_INVALID = "profile.create.validation.usernameInvalid";
	private static final String PASSWORD_REQUIRED = "profile.create.validation.passwordRequired";
	private static final String PASSWORD_CONFIRM_REQUIRED = "profile.create.validation.passwordConfirmRequired";
	private static final String PASSWORD_CONFIRM_INVALID = "profile.create.validation.passwordConfirmInvalid";
	private static final String SECURITY_QUESTION_REQUIRED = "profile.create.validation.challengeQuestionRequired";
	private static final String SECURITY_ANSWER_REQUIRED = "profile.create.validation.challengeAnswerRequired";
	private static final String AGREE_TERMS_REQUIRED = "agreeTerms.required";
	private static final String PREFERRED_LANGUAGE_REQUIRED = "preferredLanguage.required";
	private static final String MILITARY_STATUS_REQUIRED = "profile.create.validation.militaryStatusRequired";
	private static final String CURRENT_PASSWORD_REQUIRED = "passwordchange.currentPasswordRequired";
	private static final String NEW_PASSWORD_REQUIRED = "passwordchange.newPasswordRequired";
	private static final String NEW_PASSWORD_CONFIRM_REQUIRED = "passwordchange.newPasswordConfirmRequired";
	private static final String NEW_PASSWORD_CONFIRM_INVALID = "passwordchange.newPasswordConfirmInvalid";
	private static final String MANDATORY_FIELDS = "technicalerrorpage.MandatoryFields";

	public static final String REGEX_NAME = "[\\s\\w\\-&&[^_0-9]]*";
	public static final String REGEX_MIDDLE_INITIAL = "[\\w\\-&&[^_0-9]]{1}";
	public static final String REGEX_POSTAL_CODE = "[0-9]{5}";
	public static final String REGEX_PHONE_NUMBER = "[0-9]{10}";
	public static final String REGEX_PHONE_EXTENSION = "[0-9]{1,5}";
	public static final String REGEX_SSN_LASTFOUR = "[0-9]{4}";
	public static final String REGEX_USERNAME = "(([^\\s<>,;\\-])|(-(?!-))){6,16}";
	public static final String REGEX_EMAIL = "^([\\w]((\\.(?!\\.))|[-!#\\$%'\\*\\+/=\\?\\^`\\{\\}\\|~\\w])*)(?<=[\\w])@(([\\w][-\\w]*[\\w]\\.)+[a-zA-Z]{2,6})$";

	private static boolean isValidName(String name){
		return name.matches(REGEX_NAME);
	}

	private static boolean isValidMiddleInitial(String mi){
		return mi.matches(REGEX_MIDDLE_INITIAL);
	}

	private static boolean isValidPostalCode(String postalCode){
		return postalCode.matches(REGEX_POSTAL_CODE);
	}

	private static boolean isValidPhoneNumber(String phoneNumber){
		return phoneNumber.matches(REGEX_PHONE_NUMBER);
	}

	private static boolean isValidPhoneExtension(String phoneNumber){
		return phoneNumber.matches(REGEX_PHONE_EXTENSION);
	}

	private static boolean isValidSSN(String ssn){
		return ssn.matches(REGEX_SSN_LASTFOUR);
	}

	private static boolean isValidUsername(String username){
		return username.matches("^[a-zA-Z0-9]{6,16}$");
	}

	private static void validateFirstName(String firstName, Errors errors){
		if(GenericValidator.isBlankOrNull(firstName)){
			errors.rejectValue(ProfileForm.FIRST_NAME, ProfileFormValidator.FIRST_NAME_REQUIRED);
		}
		else if(!isValidName(firstName))
		{
			errors.rejectValue(ProfileForm.FIRST_NAME, ProfileFormValidator.FIRST_NAME_INVALID);
		}
	}

	private static void validateMiddleInitial(String middleInitial, Errors errors){
		if(!GenericValidator.isBlankOrNull(middleInitial) && !isValidMiddleInitial(middleInitial))
		{
			errors.rejectValue(ProfileForm.MIDDLE_INITIAL, ProfileFormValidator.MIDDLE_INITIAL_INVALID);
		}
	}

	private static void validateLastName(String lastName, Errors errors){
		if(GenericValidator.isBlankOrNull(lastName)){
			errors.rejectValue(ProfileForm.LAST_NAME, ProfileFormValidator.LAST_NAME_REQUIRED);
		}
		else if(!isValidName(lastName))
		{
			errors.rejectValue(ProfileForm.LAST_NAME, ProfileFormValidator.LAST_NAME_INVALID);
		}
	}

	private static void validateDateOfBirth(Date dateOfBirth, Errors errors){
		if(null == dateOfBirth){
			errors.rejectValue(ProfileForm.DATE_OF_BIRTH, ProfileFormValidator.DATE_OF_BIRTH_REQUIRED);
		}
	}

	private static void validateGender(Gender gender, Errors errors){
		if(null == gender){
			errors.rejectValue(ProfileForm.GENDER, ProfileFormValidator.GENDER_REQUIRED);
		}
	}

	private static void validateSocialSecurity(String socialSecurity, Errors errors){
		if(!GenericValidator.isBlankOrNull(socialSecurity) && !isValidSSN(socialSecurity))
		{
			errors.rejectValue(ProfileForm.SOCIAL_SECURITY, ProfileFormValidator.SOCIAL_SECURITY_INVALID);
		}
	}

	private static void validateAddress(ETSAddress address, Errors errors){
		//address
		if(GenericValidator.isBlankOrNull(address.getAddressLine1())){
			errors.rejectValue(ProfileForm.ADDRESS_LINE1, ProfileFormValidator.ADDRESS_LINE1_REQUIRED);
		}

		//city
		if(GenericValidator.isBlankOrNull(address.getCity())){
			errors.rejectValue(ProfileForm.CITY, ProfileFormValidator.CITY_REQUIRED);
		}

		//state
		if(null == address.getState()){
			errors.rejectValue(ProfileForm.STATE, ProfileFormValidator.STATE_REQUIRED);
		}

		//postalCode
		if(GenericValidator.isBlankOrNull(address.getPostalCode())){
			errors.rejectValue(ProfileForm.POSTAL_CODE, ProfileFormValidator.POSTAL_CODE_REQUIRED);
		}

		//country
		if(null == address.getCountry()){
			errors.rejectValue(ProfileForm.COUNTRY, ProfileFormValidator.COUNTRY_REQUIRED);
		}
		else{
			//validate postalCode
			if(	!GenericValidator.isBlankOrNull(address.getPostalCode()) &&
				!isValidPostalCode(address.getPostalCode())
			){
				errors.rejectValue(ProfileForm.POSTAL_CODE, ProfileFormValidator.POSTAL_CODE_INVALID);
			}
		}
	}

	private static void validatePrimaryPhone(ETSPhone phone, Errors errors){
		if(null == phone.getCountry()){
			errors.rejectValue(ProfileForm.PRIMARY_PHONE_COUNTRY_CODE, ProfileFormValidator.PHONE_COUNTRY_CODE_REQUIRED);
		}
		else{
			if(GenericValidator.isBlankOrNull(phone.getPhoneNumber())){
				errors.rejectValue(ProfileForm.PRIMARY_PHONE_PHONE_NUMBER, ProfileFormValidator.PHONE_REQUIRED);
			}
			else if(!isValidPhoneNumber(phone.getPhoneNumber())
			){
				errors.rejectValue(ProfileForm.PRIMARY_PHONE_PHONE_NUMBER, ProfileFormValidator.PHONE_INVALID);
			}
			if(	!GenericValidator.isBlankOrNull(phone.getPhoneExtension()) &&
				!isValidPhoneExtension(phone.getPhoneExtension()))
			{
				errors.rejectValue(ProfileForm.PRIMARY_PHONE_EXTENSION, ProfileFormValidator.PHONE_EXTENSION_INVALID);
			}
			if(null == phone.getPhoneType()){
				errors.rejectValue(ProfileForm.PRIMARY_PHONE_PHONE_TYPE, ProfileFormValidator.PHONE_TYPE_REQUIRED);
			}
		}
	}

	private static void validateAlternatePhone(ETSPhone phone, Errors errors){
		if(!GenericValidator.isBlankOrNull(phone.getPhoneNumber())){
			if(null == phone.getCountry()){
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE, ProfileFormValidator.PHONE_COUNTRY_CODE_REQUIRED);
			}
			if(!isValidPhoneNumber(phone.getPhoneNumber())){
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER, ProfileFormValidator.PHONE_INVALID);
			}
			if(	!GenericValidator.isBlankOrNull(phone.getPhoneExtension()) &&
				!isValidPhoneExtension(phone.getPhoneExtension()))
			{
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_EXTENSION, ProfileFormValidator.PHONE_EXTENSION_INVALID);
			}
			if(null == phone.getPhoneType()){
				errors.rejectValue(ProfileForm.ALTERNATE_PHONE_PHONE_TYPE, ProfileFormValidator.PHONE_TYPE_REQUIRED);
			}
		}
	}

	private static void validateEmailAddress(String emailAddress, Errors errors){
		if(GenericValidator.isBlankOrNull(emailAddress)){
			errors.rejectValue(ProfileForm.EMAIL_ADDRESS, ProfileFormValidator.EMAIL_ADDRESS_REQUIRED);
		}
		else if(!GenericValidator.isEmail(emailAddress)
		){
			errors.rejectValue(ProfileForm.EMAIL_ADDRESS, ProfileFormValidator.EMAIL_ADDRESS_INVALID);
		}
	}

	private void validateMilitaryStatus(Boolean isMilitaryMember,
			MilitaryStatusType militaryStatus, Errors errors) {
		if (null == isMilitaryMember) {
			errors.rejectValue(ProfileForm.IS_MILITARY_MEMBER, "profile.create.validation.militaryMemberShipRequired");
		} else {
			if (isMilitaryMember) {
				if (null == militaryStatus) {
					errors.rejectValue(ProfileForm.MILITARY_STATUS,
							"profile.create.validation.militaryStatusRequired");
				} else if ("NOTAM".equalsIgnoreCase(militaryStatus.getCode())) {
					errors.rejectValue(ProfileForm.MILITARY_STATUS,
							"profile.create.validation.militaryStatusInvalid");
				}
			} else {
				if (null != militaryStatus
						&& !"NOTAM".equalsIgnoreCase(militaryStatus.getCode())) {
					errors.rejectValue(ProfileForm.MILITARY_STATUS,
							"profile.create.validation.militaryStatusInvalid");
				}
			}
		}
	}

	private static void validateUsername(String username, Errors errors){
		if(GenericValidator.isBlankOrNull(username)){
			errors.rejectValue(ProfileForm.USERNAME, ProfileFormValidator.USERNAME_REQUIRED);
		}
		else if(!isValidUsername(username)
		){
			errors.rejectValue(ProfileForm.USERNAME, ProfileFormValidator.USERNAME_INVALID);
		}
	}

	private static void validateSecurityQuestion(ChallengeQuestion securityQuestion, Errors errors){
		if(null == securityQuestion){
			errors.rejectValue(ProfileForm.SECURITY_QUESTION, ProfileFormValidator.SECURITY_QUESTION_REQUIRED);
		}
	}

	private static void validateSecurityAnswer(String securityAnswer, Errors errors){
		if(GenericValidator.isBlankOrNull(securityAnswer)){
			errors.reject(ProfileForm.SECURITY_ANSWER, ProfileFormValidator.SECURITY_ANSWER_REQUIRED);
		}
	}

	@Override
	public boolean supports(Class<?> cla55) {
		return ProfileForm.class.isAssignableFrom(cla55);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		validatePersonalInfo(arg0, errors);
		validateAccountInfo(arg0, errors,"");
		validateAdditionalInfo(arg0, errors);
	}


	public void validatePersonalInfo(Object arg0, Errors errors){
		ProfileForm profileForm = (ProfileForm)arg0;

		//first name
		validateFirstName(profileForm.getProfile().getCustomer().getFirstName(), errors);

		//middle initial
		validateMiddleInitial(profileForm.getProfile().getCustomer().getMiddleInitial(), errors);

		//last name
		validateLastName(profileForm.getProfile().getCustomer().getLastName(), errors);

		//dateOfbirth
		validateDateOfBirth(profileForm.getProfile().getCustomer().getDateOfBirth(), errors);

		//gender
		validateGender(profileForm.getProfile().getCustomer().getGender(), errors);

		//SSN
		validateSocialSecurity(profileForm.getProfile().getCustomer().getSocialSecurity(), errors);

		//address
		validateAddress(profileForm.getProfile().getAddress(), errors);

		//primaryPhone
		validatePrimaryPhone(profileForm.getProfile().getPrimaryPhone(), errors);

		//alternatePhone
		validateAlternatePhone(profileForm.getProfile().getAlternatePhone(), errors);

		//emailAddress
		validateEmailAddress(profileForm.getProfile().getCustomer().getEmailAddress(), errors);
	}

	public void validateAccountInfo(Object arg0, Errors errors, String authMechanish){
		ProfileForm profileForm = (ProfileForm)arg0;

		//username
		validateUsername(profileForm.getProfile().getCustomer().getUsername(), errors);

		//password
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.PASSWORD, ProfileFormValidator.PASSWORD_REQUIRED);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.PASSWORD_CONFIRM, ProfileFormValidator.PASSWORD_CONFIRM_REQUIRED);
		String password = profileForm.getPassword();

		if(ApplicationConfiguration.AUTH_MECHANISM_OAM.equalsIgnoreCase(authMechanish))
		{
			if (!password.matches("^.*(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d\\W]).*$")) {
			errors.rejectValue(ProfileForm.PASSWORD, "profile.create.validation.passwordInvalid");
			}
			else
			{
				if(password.startsWith(profileForm.getProfile().getCustomer().getFirstName()) || password.startsWith(profileForm.getProfile().getCustomer().getLastName()))
				{
					errors.rejectValue(ProfileForm.PASSWORD, "profile.create.validation.passwordInvalidUserName");
				}
			}
		}

		if(	null != password &&
			null != profileForm.getPasswordConfirm() &&
			0 != profileForm.getPasswordConfirm().compareTo(password)
		)
		{
			errors.rejectValue(ProfileForm.PASSWORD_CONFIRM, ProfileFormValidator.PASSWORD_CONFIRM_INVALID);
		}

		//security question
		validateSecurityQuestion(profileForm.getProfile().getCustomer().getChallengeQuestion(), errors);

		//security answer
		validateSecurityAnswer(profileForm.getProfile().getCustomer().getChallengeAnswer(), errors);

		//agree terms/conditions
		if(!profileForm.isAgreeTerms()){
			errors.rejectValue(ProfileForm.AGREE_TERMS, ProfileFormValidator.AGREE_TERMS_REQUIRED);
		}
	}

	public void validateAdditionalInfo(Object arg0, Errors errors){
		ProfileForm profileForm = (ProfileForm)arg0;

		//preferred language
//		if(null == profileForm.getProfile().getCustomer().getPrfrdTstTakingLang()){
//			errors.rejectValue(ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING, ProfileFormValidator.PREFERRED_LANGUAGE_REQUIRED);
//		}

		//Military Status
		//validateMilitaryStatus(profileForm.getProfile().getCustomer(), errors);
//		validateMilitaryStatus(profileForm.getProfile().getCustomer().getMilitaryMemberShip(), profileForm.getProfile().getCustomer().getMilitaryStatus(), errors);
	}

	public void validateBackgroundInfo(Object arg0, Errors errors){// checks for mandatory fields
		boolean mandatory_fields_answered=true;
		ProfileForm profileForm = (ProfileForm)arg0;
		List<DemographicQuestionVO> dmgrphQList=profileForm.getProfile().getDemographicQuestions();
		List<Long> mandatoryTriggerQNo=new ArrayList<Long>();

		for (Iterator<DemographicQuestionVO> itr =dmgrphQList.iterator();itr.hasNext() && mandatory_fields_answered;){
			DemographicQuestionVO dmgrphQ=itr.next();


			if(!isEmptyDropDownCheckboxResposne(dmgrphQ.getSelectedResponseIds()) && null!=dmgrphQ.getDependentQuestionVO() && !dmgrphQ.getDependentQuestionVO().isEmpty()){ // if Q has responses and it has dependent Q
				// if the trigger response is selected add the triggerQ to mandatory list
				for(DmgrphQstnTriggerVO ele:dmgrphQ.getDependentQuestionVO()){
					for(String selectedResp:dmgrphQ.getSelectedResponseIds()){
						if(selectedResp.equalsIgnoreCase(ele.getTriggerRespNo().toString())){
							mandatoryTriggerQNo.add(ele.getDependentQstnNo());
						}
					}
				}
			}

			if(dmgrphQ.isResponseRequired() ){
				if(dmgrphQ.isIndependent()){
					mandatory_fields_answered=validateDmgrphQResponse(dmgrphQ,errors);

				}else{ // answer is needed only if response was selected
					if(mandatoryTriggerQNo.contains(dmgrphQ.getQstnNo())){
						mandatory_fields_answered=validateDmgrphQResponse(dmgrphQ,errors);
					}
				}
				//
			}


		}
	}



	private boolean validateDmgrphQResponse(DemographicQuestionVO dmgrphQ,
			Errors errors) {
		 if(dmgrphQ.getResponseType().equalsIgnoreCase(DemographicQuestionRespType.respType.FREEFORM.getResponseType()) && (null== dmgrphQ.getFreeFormAnswer())){
			errors.rejectValue(null,ProfileFormValidator.MANDATORY_FIELDS);
			return false;
		}else{
			if (isEmptyDropDownCheckboxResposne(dmgrphQ.getSelectedResponseIds())){	// Validate non null value for required BIQ
				errors.rejectValue(null,ProfileFormValidator.MANDATORY_FIELDS);
				return false;
			}
		}
		return true;

	}

	private boolean isEmptyDropDownCheckboxResposne(String[] selectedResponseIds) {
		return (null== selectedResponseIds || selectedResponseIds.length==0 || selectedResponseIds[0]=="")?true:false;

	}

	public void validateChangePassword(Object arg0, Errors errors){
		ProfileForm profileForm = (ProfileForm)arg0;

		//old password
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.OLD_PASSWORD, ProfileFormValidator.CURRENT_PASSWORD_REQUIRED);

		//password
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.PASSWORD, ProfileFormValidator.NEW_PASSWORD_REQUIRED);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.PASSWORD_CONFIRM, ProfileFormValidator.NEW_PASSWORD_CONFIRM_REQUIRED);
		String password = profileForm.getPassword();
		if (!password.matches("^.*(?=.{6,16})(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d\\W]).*$")) {
			errors.rejectValue(ProfileForm.PASSWORD, "profile.create.validation.passwordInvalid");
		}
		if(null != password && null != profileForm.getPasswordConfirm() && 0 != profileForm.getPasswordConfirm().compareTo(password))
		{
			errors.rejectValue(ProfileForm.PASSWORD_CONFIRM, ProfileFormValidator.NEW_PASSWORD_CONFIRM_INVALID);
		}
	}

	public void validateChangeSecurityQuestion(Object arg0, Errors errors){
		ProfileForm profileForm = (ProfileForm)arg0;

		//old password
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, ProfileForm.OLD_PASSWORD, ProfileFormValidator.CURRENT_PASSWORD_REQUIRED);

		//security question
		validateSecurityQuestion(profileForm.getProfile().getCustomer().getChallengeQuestion(), errors);

		//security answer
		validateSecurityAnswer(profileForm.getProfile().getCustomer().getChallengeAnswer(), errors);
	}

	public void validatePersonalInfoForUpdate(Object arg0, Errors errors){
		ProfileForm profileForm = (ProfileForm)arg0;

		//SSN
		validateSocialSecurity(profileForm.getProfile().getCustomer().getSocialSecurity(), errors);

		//address
		validateAddress(profileForm.getProfile().getAddress(), errors);

		//primaryPhone
		validatePrimaryPhone(profileForm.getProfile().getPrimaryPhone(), errors);

		//alternatePhone
		validateAlternatePhone(profileForm.getProfile().getAlternatePhone(), errors);

		//emailAddress
		validateEmailAddress(profileForm.getProfile().getCustomer().getEmailAddress(), errors);
	}
}