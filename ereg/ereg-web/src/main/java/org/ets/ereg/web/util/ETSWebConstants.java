package org.ets.ereg.web.util;

public class ETSWebConstants {
	public static final String SIGIN="/public/signin";
	public static final String SUCCESS_PAGE="secured/home";
	public static final String BIQ_PAGE="profile/background";
		
	public static final String SUCCESS_FORGOT_NAME="successForgotName.jsp";
	public static final String FAILURE_FORGOT_NAME="failureForgotName.jsp";
	
	public static final String SUCCESS_FORGOT_PASSWORD="successForgotPassword";
	public static final String FAILURE_FORGOT_PASSWORD="failureForgotPassword";
	
	public static final String LOGIN_PAGE="public/register/register";
	public static final String POST_LOGIN_PAGE = "/secure/login";	
	public static final String FAILURE_PAGE=LOGIN_PAGE+"?error=true";
	public static final String BIQ_ANSWERED="BIQ_ANSWERED";

	public static final String USERAGENT="User-Agent";
	public static final String HSET_DEFAULT_APPT_DISPLAY_COUNT="appointmentDisplayCount";
	public static final String REDIRECT = "redirect";
	public static final String HOME_PAGE = "/secure/home";
	public static final String REDIRECT_HOME_PAGE = REDIRECT+":"+HOME_PAGE;
	public static final String TARGET_URL_ATTRIB = "loginTarget";
}
