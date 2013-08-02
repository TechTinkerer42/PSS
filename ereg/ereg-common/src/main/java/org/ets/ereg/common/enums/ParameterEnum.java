package org.ets.ereg.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ParameterEnum {
	FILE_LOCATION_CBT("FLCBT","File Location for CBT"),
	FILE_LOCATION_PBT("FLPBT","File Location for PBT"),
	MAX_TIMES_ATTEMPT("MTA","Max Times Attempt"),
	WAIT_PERIOD("WPP","Wait Period");

	private static final Map<String, ParameterEnum> ENUM_LOOK_UP = new HashMap<String, ParameterEnum>();

	static {
		for (ParameterEnum re : EnumSet.allOf(ParameterEnum.class))
			ENUM_LOOK_UP.put(re.getCode(), re);
	}

	private String code;
	private String description;

	private ParameterEnum(String code, String description) {
		this.code = code;
		this.description = description;
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
