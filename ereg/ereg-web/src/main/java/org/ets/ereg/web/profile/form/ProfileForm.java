package org.ets.ereg.web.profile.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	public static final String DAY_OF_BIRTH = "birthDay";
	public static final String MONTH_OF_BIRTH = "birthMonth";
	public static final String YEAR_OF_BIRTH = "birthYear";
	public static final String GENDER = "profile.customer.gender";
	public static final String ETHNICITY = "profile.customer.ethnicityType";
	public static final String PREFERRED_LANGUAGE_FOR_TEST_TAKING = "profile.customer.prfrdTstTakingLang";
	public static final String PRIMARY_SPEAKING_LANGUAGE = "profile.customer.prmrySpkngLang";
	public static final String IS_MILITARY_MEMBER = "profile.customer.militaryMemberShip";
	public static final String MILITARY_STATUS = "profile.customer.militaryStatus";
	public static final String SOCIAL_SECURITY = "profile.customer.socialSecurity";
	public static final String USERNAME = "profile.customer.username";
	public static final String PASSWORD = "password";
	public static final String OLD_PASSWORD = "oldPassword";
	public static final String PASSWORD_CONFIRM = "passwordConfirm";
	public static final String SECURITY_QUESTION = "profile.customer.challengeQuestion";
	public static final String SECURITY_ANSWER = "profile.customer.challengeAnswer";
	public static final String AGREE_TERMS = "agreeTerms";
	
	public static final String STEP_PERSONAL_INFO = "Personal Information";
	public static final String STEP_ACCOUNT_INFO = "Account Information";
	public static final String STEP_ADDITIONAL_INFO = "Additional Information";
	public static final String STEP_BIQ_INFO = "Background Information";
	public static final String STEP_REVIEW_INFO = "Review Information";
	public static final String STEP_DUPLICATE_PROFILE = "Duplicate Profile";
	
	public static final String SCENARIO_CREATE_PROFILE = "Create Profile";
	public static final String SCENARIO_UPDATE_PROFILE = "Update Profile";	
	
	public static final int STATUS_OK = 0;
	public static final int STATUS_KO = -1;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final String DATE_FIELD_SEPARATOR = "-";
	private static final int MIN_YEAR = 0;
	private static final int MAX_YEAR = 9999;
		
	private String scenario;
	
	private String step;
	
	private ProfileVO profile;
	
	private String oldPassword;
	
	private String password;
	
	private String passwordConfirm;
	
	private boolean agreeTerms;
	
	private int statusCode;
	
	private String birthDay;
	
	private String birthMonth;
	
	private String birthYear;
	
	private String statusMessage;	
	
	
	
	static{
		DATE_FORMAT.setLenient(false);
	}
	
	public ProfileForm(){
		step = STEP_PERSONAL_INFO;
		statusCode = ProfileForm.STATUS_OK;
		statusMessage = "";
		birthDay = "";
		birthMonth = "";
		birthYear = "";
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public ProfileVO getProfile() {
		return profile;
	}

	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public boolean isAgreeTerms() {
		return agreeTerms;
	}

	public void setAgreeTerms(boolean agreeTerms) {
		this.agreeTerms = agreeTerms;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	
	public void transfereFromFormDateOfBirth(){
		Date dateOfBirth = null;
		if(	null != birthYear && birthYear.trim().length() > 0 &&
			null != birthMonth && birthMonth.trim().length() > 0 &&
			null != birthDay && birthDay.trim().length() > 0
		){
			int year = Integer.parseInt(birthYear);
			if(year > ProfileForm.MIN_YEAR && year <= ProfileForm.MAX_YEAR){
				try {
					dateOfBirth = DATE_FORMAT.parse(birthYear + ProfileForm.DATE_FIELD_SEPARATOR + birthMonth + ProfileForm.DATE_FIELD_SEPARATOR + birthDay);
				}
				catch(ParseException e){
					
				}
			}
		}
		profile.getCustomer().setDateOfBirth(dateOfBirth);
	}
	
	public void transfereToFormDateOfBirth(){
		Date dateOfBirth = profile.getCustomer().getDateOfBirth();
		if(null == dateOfBirth){
			birthDay = "";
			birthMonth = "";
			birthYear = "";
		}
		else{
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.setTime(dateOfBirth);
			birthDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			birthMonth = Integer.toString(cal.get(Calendar.MONTH)+1);
			birthYear = Integer.toString(cal.get(Calendar.YEAR));
		}
	}
}