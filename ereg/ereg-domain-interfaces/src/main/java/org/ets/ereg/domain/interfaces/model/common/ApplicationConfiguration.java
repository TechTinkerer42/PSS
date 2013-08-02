package org.ets.ereg.domain.interfaces.model.common;

public interface ApplicationConfiguration {
	static final String AUTH_MECHANISM = "AUTH_MECHANISM";
	static final String AUTH_MECHANISM_DATABASE = "DATABASE";
	static final String AUTH_MECHANISM_OAM = "OAM";
	static final String AUTH_URL="AUTH_URL";
	static final String FIELDNAME_USENAME="FIELDNAME_USENAME";
	static final String FIELDNAME_PASSWORD="FIELDNAME_PASSWORD";
	static final String HELPDESK_EMAIL = "HELPDESK_EMAIL";
	static final String SMTP_HOST = "smtp.host";
	static final String SMTP_PORT = "smtp.port";
	static final String SMTP_PROTOCOL = "smtp.protocol";
	static final String SMTP_USERNAME = "smtp.username";
	static final String SMTP_PASSWORD = "smtp.password";
	static final String MAIL_PROPERTIES = "mail.properties";
	static final String MAIL_FROM = "mail.from";
	static final String MAIL_ENABLED = "mail.enabled";
	static final String CSR_USR_HOME_PAGE = "CSR_USR_HOME_PAGE";
	static final String TCA_USR_HOME_PAGE = "TCA_USR_HOME_PAGE";
	static final String TESTCENTER_SEARCH_URL = "TESTCENTER_SEARCH_URL";
	
	static final String CSR_WEB_INTERNAL_LOGOUT_URL="CSR_WEB_INTERNAL_LOGOUT_URL";
	static final String CSR_WEB_EXTERNAL_LOGOUT_URL="CSR_WEB_EXTERNAL_LOGOUT_URL";
	// added for the ldap user creation
	static final String LDAP_CREATE_CANDIDATE_ROLE = "LDAP_CREATE_CANDIDATE_ROLE";
	static final String LDAP_CREATE_TCA_ROLE = "LDAP_CREATE_TCA_ROLE";
	static final String LDAP_SEARCH_USER_DIR = "LDAP_SEARCH_USER_DIR";
	static final String LDAP_USER_TYPE = "LDAP_USER_TYPE";
	// added for the ldap login
	static final String FIELDNAME_LOCKPAGE = "FIELDNAME_LOCKPAGE";
	static final String FIELDNAME_LOCKPAGE_VALUE = "FIELDNAME_LOCKPAGE_VALUE";
	static final String FIELDNAME_EXPIREDPWDPAGE = "FIELDNAME_EXPIREDPWDPAGE";
	static final String FIELDNAME_EXPIREDPWDPAGE_VALUE = "FIELDNAME_EXPIREDPWDPAGE_VALUE";
	static final String FIELDNAME_EXPIREDPWDPAGE_VALUE_CSR = "FIELDNAME_EXPIREDPWDPAGE_VALUE_CSR";	
	static final String FORGET_USERNAME_EREG = "FORGET_USERNAME_EREG";
	static final String FORGET_PASSWORD_EREG = "FORGET_PASSWORD_EREG";
	static final String CHANGE_SEC_QUES_EREG = "CHANGE_SEC_QUES_EREG";
	static final String CHANGE_PASSWORD_EREG = "CHANGE_PASSWORD_EREG";
	static final String CANDIDATE_PASS_CHNG_EREG = "CANDIDATE_PASS_CHNG_EREG";
	static final String FORGET_USERNAME_CSR = "FORGET_USERNAME_CSR";
	static final String FORGET_PASSWORD_CSR = "FORGET_PASSWORD_CSR";
	
	static final String WSDL_LOCATION = "WSDL_LOCATION";
	static final String REG_SYS_ID = "REG_SYS_ID";
		
	/**
	 * Method to get Id
	 *
	 * @return
	 */
	abstract Long getId();

	/**
	 * Method to set Id
	 *
	 * @param id
	 */
	abstract void setId(Long id);

	/**
	 * Method to get Name
	 *
	 * @return
	 */
	abstract String getName();

	/**
	 * Method to set Name
	 *
	 * @param name
	 */
	abstract void setName(String name);

	/**
	 * Method to get Value
	 *
	 * @return
	 */
	abstract String getValue();

	/**
	 * Method to set Value
	 *
	 * @param value
	 */
	abstract void setValue(String value);

}
