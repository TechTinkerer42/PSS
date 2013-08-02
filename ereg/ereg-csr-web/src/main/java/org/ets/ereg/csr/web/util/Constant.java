package org.ets.ereg.csr.web.util;

public class Constant {

    //URLS
    public static final String LOGIN_SUCCESS_PAGE = "secured/home";
    public static final String LOGIN_PAGE = "public/authn/signin";
    public static final String ACCESS_ERROR_PAGE = "error/customErrorPage";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String SIGNIN = "/public/signin";
    public static final String ACCESS_ERROR = "/customErrorPage";
    public static final String HOME = "/secure/home";
    public static final String LOGIN = "/secure/login";    
    public static final String DUPLICATE = "/duplicate";
    public static final String ACCOUNT = "/account";
    public static final String REVIEW = "/review";
    public static final String EDIT_PROFILE ="/edit";
    public static final String CHANGE_PASSWORD = "/changePassword";
    public static final String CHANGE_SECURITY_QUESTION = "/changeSecurityQuestion";
    public static final String TCA_PROFILE = "/tca/profile";
    public static final String TCA_SECURE_PROFILE = "/secure/tca/profile";
    public static final String SECURITY_QUESTION_VIEW = "tcaprofile/changeSecurityQuestion";
    public static final String PERSONAL_INFO_VIEW = "tcaprofile/personalInfo";
    public static final String DUPLICATE_ACCOUNT_VIEW = "tcaprofile/duplicateAccount";
    public static final String ACCOUNT_INFO_VIEW = "tcaprofile/accountInfo";
    public static final String REVIEW_VIEW = "tcaprofile/reviewProfile";
    public static final String ACCOUNT_VIEW = "tcaprofile/profileCreated";
    public static final String PROFILE_VIEW = "tcaprofile/viewProfile";
    public static final String PROFILE_UPDATE_VIEW = "tcaprofile/updateProfile";
    public static final String CHANGE_PASSWORD_VIEW = "tcaprofile/changePassword";
    public static final String ACCOUNT_REDIRECT_VIEW = "redirect:/tca/profile/account";
    public static final String DUPLICATE_ACCOUNT_SECURE_REDIRECT_VIEW = "redirect:/secure/tca/profile/duplicate";
    public static final String HOME_PAGE_SECURE_REDIRECT_VIEW ="redirect:/secure/home";
    public static final String PERSONAL_INFO_SECURE_REDIRECT_VIEW = "redirect:/secure/tca/profile";
    public static final String REVIEW_REDIRECT_VIEW = "redirect:/tca/profile/review";
    public static final String PERSONAL_INFO_REDIRECT_VIEW = "redirect:/tca/profile";
    public static final String DUPLICATE_ACCOUNT_REDIRECT_VIEW = "redirect:/tca/profile/duplicate";

    public static final String FIRST_NAME = "tcaProfile.adminUser.firstName";
    public static final String MIDDLE_INITIAL = "tcaProfile.adminUser.middleName";
    public static final String LAST_NAME = "tcaProfile.adminUser.lastName";
    public static final String EMAIL_ADDRESS = "tcaProfile.adminUser.email";
    public static final String USERNAME = "tcaProfile.adminUser.login";
    public static final String PASSWORD = "tcaProfile.adminUser.password";
    public static final String PASSWORD_CONFIRM = "tcaProfile.adminUser.passwordConfirm";
    public static final String OLD_PASSWORD = "tcaProfile.adminUser.oldPassword";
    public static final String DATE_OF_BIRTH = "tcaProfile.adminUser.dateOfBirth";
    public static final String GENDER = "tcaProfile.adminUser.gender";
    public static final String DAY_OF_BIRTH = "birthDay";
    public static final String MONTH_OF_BIRTH = "birthMonth";
    public static final String YEAR_OF_BIRTH = "birthYear";
    public static final String PHONE_COUNTRY_CODE = "tcaProfile.adminUser.etsPhone.country";
    public static final String PHONE_NUMBER = "tcaProfile.adminUser.etsPhone.phoneNumber";
    public static final String PHONE_EXTENSION = "tcaProfile.adminUser.etsPhone.phoneExtension";
    public static final String PHONE_TYPE = "tcaProfile.adminUser.etsPhone.phoneType";
    public static final String SECURITY_QUESTION = "tcaProfile.adminUser.challengeQuestion";
    public static final String SECURITY_ANSWER = "tcaProfile.adminUser.challengeAnswer";
    public static final String IDENTIFICATION_TEXT = "tcaProfile.adminUser.adminIdentificationStr";

    public static final String STEP_PERSONAL_INFO = "Personal_Information";
    public static final String STEP_ACCOUNT_INFO = "Account_Information";
    public static final String STEP_REVIEW_INFO = "Review_Information";
    public static final String STEP_DUPLICATE_TCA_PROFILE = "Duplicate_TCA_Profile";
    public static final String STEP_CHANGE_PASSWORD = "Change_Password";
    public static final String STEP_CHANGE_SECURITY_QUESTION = "Change_Security_Question";

    //String Constants
    public static final String ERROR = "error";
    public static final String TRUE = "true";
    public static final String LOGIN_INVALID = "loginpage.notSuccessfulMessage";
    public static final String STATUS_MESSAGE = "STATUS_MESSAGE";
    public static final String TCA_PROFILE_FORM = "tcaProfileForm";
    public static final String PROFILE_FORM = "profileForm";

    public static final String INVALID_PASSWORD = "invalidPassword";
    public static final String PASSWORD_INVALID_MESSAGE = "profile.create.validation.passwordInvalid";
    public static final String PASSWORD_MISMATCH = "passwordMismatch";
    public static final String PASSWORD_MISMATCH_MESSAGE = "profile.create.validation.passwordConfirmInvalid";

    public static final String CREATE_PROFILE_DATE_PATTERN = "MM-dd-yyyy";

   /* public static String BIQ_TYPE_PROFILE="PRFL";
	public static String BIQ_TYPE_TEST="TST";*/
	//public static String LANG_CODE="EN";

	public static final String ENCODING_DELIMITTER = "|";
	public static final int APPOINTMENT_NUMBER_LENGTH = 16;
	public static final String ZEROS = "0000000";
	public static final String HSET_DEFAULT_APPT_DISPLAY_COUNT = "appointmentDisplayCount";
    public static final String FORWARD_SLASH = "/";
    public static final String URL_DECODE_IDENTIFIER = "tWcszZ9i17cP6wAudlndgw";
    public static final CharSequence QUERY_STRING_IDENTIFIER = "?";
    public static final String BASE64_REGEX = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$";
    
    public static final String CUSTOMER_USER_NAME = "customerUserName";

}
