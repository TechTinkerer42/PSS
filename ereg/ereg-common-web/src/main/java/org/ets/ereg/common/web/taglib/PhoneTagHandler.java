package org.ets.ereg.common.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneTagHandler extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(PhoneTagHandler.class);
	
	private static final String OPEN_PARENTHESES = "(";
	private static final String CLOSE_PARENTHESES = ")";
	private static final String PLUS = "+";
	private static final String SPACE = " ";
	private static final String EXT = " x";
	
	private String phoneNumber;
	private String countryAbbr;
	private String countryCode;
	private String extension;
	private String phoneType;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountryAbbr() {
		return countryAbbr;
	}

	public void setCountryAbbr(String countryAbbr) {
		this.countryAbbr = countryAbbr;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	@Override
	public int doStartTag() throws JspException {
		StringBuilder sb = new StringBuilder();
		
		// country code 
		if (countryCode != null && !countryCode.isEmpty()) {
			sb.append(OPEN_PARENTHESES + PLUS);
			sb.append(countryCode);
			sb.append(CLOSE_PARENTHESES + SPACE);
		}
		
		// country abbreviation default to be "US"
		if (countryAbbr == null) {
			countryAbbr = "US";
		}
		
		// phone number
		if (countryAbbr.equalsIgnoreCase("US")) {
			phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
			if (phoneNumber.matches("[2-9][0-9]{9}")) {
				sb.append(String.format("(%s) %s-%s",
						phoneNumber.substring(0, 3),
						phoneNumber.substring(3, 6), 
						phoneNumber.substring(6)));
			}
		} else {
			sb.append(phoneNumber);
		}
		
		// extension
		if (extension != null && extension.length() > 0) {
			sb.append(EXT);
			sb.append(extension);
		}
		
		// phone type
		if (phoneType != null && !phoneType.isEmpty()) {
			sb.append(SPACE + OPEN_PARENTHESES);
			sb.append(phoneType);
			sb.append(CLOSE_PARENTHESES);
		}
		
		try {
			pageContext.getOut().write(sb.toString());
		} catch (IOException e) {
			log.error("Error outputing phone number: {}", e.toString());
		}
		return EVAL_BODY_INCLUDE;
	}
	
}


