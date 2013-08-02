package org.ets.ereg.common.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
/**
 * Enumeration of EIAS Error Code To be displayed
 *  
 * @author Nishant Jain
 * 
 * @version 1.0, 16 May, 2013, 2:34:11 PM
 * 
 * @since 'HSET'  Release 1.0
 */
public enum EIASExceptionMsgMapping {

	EIAS08("08","eias.exception.samePassword"),
	NULL ("OTHERS","eias.exception.Others");

	private static final Map<String, String> ENUM_LOOK_UP = new HashMap<String, String>();

	static {
		for (EIASExceptionMsgMapping re : EnumSet.allOf(EIASExceptionMsgMapping.class))
			ENUM_LOOK_UP.put(re.getCode(), re.getDescription());
	}

	private String code;
	private String description;

	private EIASExceptionMsgMapping(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public static String get(String code) {
		return (String) (ENUM_LOOK_UP.get(code) != null ? ENUM_LOOK_UP.get(code) : ENUM_LOOK_UP.get("OTHERS"));
	}	

	public String getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return getCode();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
