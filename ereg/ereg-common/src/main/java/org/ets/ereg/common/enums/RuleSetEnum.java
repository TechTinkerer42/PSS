package org.ets.ereg.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RuleSetEnum {
	RETAKE_RULES("SCHRL","Scheduling Rules"),
	ECOMM_RULES("ECOMM","ECommerce Rules"),
	NULL (null,null);

	private static final Map<String, RuleSetEnum> ENUM_LOOK_UP = new HashMap<String, RuleSetEnum>();

	static {
		for (RuleSetEnum re : EnumSet.allOf(RuleSetEnum.class))
			ENUM_LOOK_UP.put(re.getCode(), re);
	}

	private String code;
	private String description;

	private RuleSetEnum(String code, String description) {
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
	 public static RuleSetEnum get(String code) { 
	        return ENUM_LOOK_UP.get(code); 
	 }

	public void setDescription(String description) {
		this.description = description;
	}

}
