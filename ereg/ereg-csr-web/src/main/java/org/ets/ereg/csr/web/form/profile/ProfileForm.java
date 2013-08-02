package org.ets.ereg.csr.web.form.profile;

import org.ets.ereg.profile.vo.ProfileVO;

public class ProfileForm {

	public static final String FIRST_NAME = "profile.customer.firstName";
	public static final String MIDDLE_INITIAL = "profile.customer.middleInitial";
	public static final String LAST_NAME = "profile.customer.lastName";
	public static final String ADDRESS_LINE1 = "profile.address.addressLine1";
	public static final String ADDRESS_LINE2 = "profile.address.addressLine2";
	public static final String COUNTRY = "profile.address.country";
	public static final String STATE = "profile.address.state";
	public static final String CITY = "profile.address.city";
	public static final String POSTAL_CODE = "profile.address.postalCode";
	public static final String PRIMARY_PHONE_COUNTRY_CODE = "profile.primaryPhone.country";
	public static final String PRIMARY_PHONE_PHONE_NUMBER = "profile.primaryPhone.phoneNumber";
	public static final String PRIMARY_PHONE_EXTENSION = "profile.primaryPhone.phoneExtension";
	public static final String PRIMARY_PHONE_PHONE_TYPE = "profile.primaryPhone.phoneType";
	public static final String ALTERNATE_PHONE_COUNTRY_CODE = "profile.alternatePhone.country";
	public static final String ALTERNATE_PHONE_PHONE_NUMBER = "profile.alternatePhone.phoneNumber";
	public static final String ALTERNATE_PHONE_EXTENSION = "profile.alternatePhone.phoneExtension";
	public static final String ALTERNATE_PHONE_PHONE_TYPE = "profile.alternatePhone.phoneType";
	public static final String EMAIL_ADDRESS = "profile.customer.emailAddress";
	public static final String DATE_OF_BIRTH = "profile.customer.dateOfBirth";
	public static final String GENDER = "profile.customer.gender";
	public static final String ETHNICITY = "profile.customer.ethnicityType";
	public static final String PREFERRED_LANGUAGE_FOR_TEST_TAKING = "profile.customer.prfrdTstTakingLang";
	public static final String PRIMARY_SPEAKING_LANGUAGE = "profile.customer.prmrySpkngLang";
	public static final String IS_MILITARY_MEMBER = "profile.customer.militaryMemberShip";
	public static final String MILITARY_STATUS = "profile.customer.militaryStatus";
	public static final String SOCIAL_SECURITY = "profile.customer.socialSecurity";
	public static final String USERNAME = "profile.customer.username";
	public static final String PASSWORD = "profile.customer.password";
	public static final String CANDIDATE_PASSWORD = "password";
	public static final String PASSWORD_CONFIRM = "passwordConfirm";	
	public static final String SECURITY_QUESTION = "profile.customer.challengeQuestion";

	public static final String STEP_PERSONAL_INFO = "Personal Information";
	public static final String STEP_ACCOUNT_INFO = "Account Information";
	public static final String STEP_ADDITIONAL_INFO = "Additional Information";
	public static final String STEP_REVIEW_INFO = "Review Information";
	public static final String STEP_DUPLICATE_PROFILE = "Duplicate Profile";
	public static final String STEP_BIQ_INFO = "Background Information";

	private String step;
	private ProfileVO profile;
	private String password;
	private String passwordConfirm;


	public ProfileForm() {
		step = STEP_PERSONAL_INFO;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public ProfileVO getProfile() {
		return profile;
	}

	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}


}
