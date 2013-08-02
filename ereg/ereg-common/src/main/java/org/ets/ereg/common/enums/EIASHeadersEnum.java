package org.ets.ereg.common.enums;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum EIASHeadersEnum {
		EIASEMPLOYEEID("EIASEMPLOYEEID"),
		EIASUSERNAME("EIASUSERNAME"),
		EIASSTRINGGUID("EIASSTRINGGUID"),
		EIASMEMBEROF("EIASMEMBEROF"),
		EIASLASTNAME("EIASLASTNAME"),
		EIASGIVENNAME("EIASGIVENNAME"),
		EIASEMAIL("EIASEMAIL"),
		EIASUSERTYPE("EIASUSERTYPE"),
		NULL(null);

	private static final Map<String, EIASHeadersEnum> ENUM_LOOK_UP = new HashMap<String, EIASHeadersEnum>();

	static {
		for (EIASHeadersEnum ee : EnumSet.allOf(EIASHeadersEnum.class))
			ENUM_LOOK_UP.put(ee.getCode(), ee);
	}

	private String code;

	private EIASHeadersEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return getCode();
	}

	public static EIASHeadersEnum get(String code) {
		if (StringUtils.isEmpty(code)) {
			return NULL;
		}
		return ENUM_LOOK_UP.get(code) != null ? ENUM_LOOK_UP.get(code) : NULL;
	}

	public static List<EIASHeadersEnum> getMandatoryHeaders() {
		return Arrays.asList(EIASUSERNAME, EIASSTRINGGUID, EIASUSERTYPE);
	}

}
