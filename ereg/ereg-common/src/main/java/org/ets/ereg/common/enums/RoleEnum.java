package org.ets.ereg.common.enums;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Enumeration of all possible Role Codes
 *  
 * @author Mangesh Pardesi
 * 
 * @version 1.0, Feb 15, 2013, 4:34:11 PM
 * 
 * @since 'HSET'  Release 1.0
 */
public enum RoleEnum {
	ROLE_CUSTOMER("ROLE_CUSTOMER","Customer"),
	ROLE_ADMIN("ROLE_ADMIN","Admin Master Access"),
	ROLE_MERCHANDISE_MANAGER("ROLE_MERCHANDISE_MANAGER","Merchandiser"),
	ROLE_PROMOTION_MANAGER("ROLE_PROMOTION_MANAGER","Promotion Manager"),
	ROLE_CUSTOMER_SERVICE_REP("ROLE_CUSTOMER_SERVICE_REP","CSR"),
	ROLE_CONTENT_EDITOR("ROLE_CONTENT_EDITOR","CMS Editor"),
	ROLE_CONTENT_APPROVER("ROLE_CONTENT_APPROVER","CMS Approver"),
	ROLE_TEST_CENTER_ADMIN("ROLE_TEST_CENTER_ADMIN","TCA"),
	ROLE_STATE_ADMIN("ROLE_STATE_ADMIN","SA"),
	ROLE_SSD_CSR("ROLE_SSD_CSR","SSD CSR"),
	ROLE_CUSTOMER_SERVICE_REP_LEAD("ROLE_CUSTOMER_SERVICE_REP_LEAD","CSR Lead"),
	ROLE_SSD_CSR_LEAD("ROLE_SSD_CSR_LEAD","SSD CSR Lead"),
	ROLE_RESO_CSR("ROLE_RESO_CSR","Reso CSR"),
	ROLE_PROGRAM_MANAGER("ROLE_PROGRAM_MANAGER","Program Manager"),
	//invalid role
	NULL (null,null);

	private static final Map<String, RoleEnum> ENUM_LOOK_UP = new HashMap<String, RoleEnum>();

	static {
		for (RoleEnum re : EnumSet.allOf(RoleEnum.class))
			ENUM_LOOK_UP.put(re.getCode(), re);
	}

	private String code;
	private String description;

	private RoleEnum(String code, String description) {
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

	public RoleEnum[] getCorpRoles() {
		return new RoleEnum[] { ROLE_TEST_CENTER_ADMIN };
	}

	public static RoleEnum get(String code) {
		return ENUM_LOOK_UP.get(code) != null ? ENUM_LOOK_UP.get(code) : NULL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static Set<RoleEnum> get(String... codes) {
		if (ArrayUtils.isEmpty(codes)){
			return Collections.emptySet();
		}
		Set<RoleEnum> roles = new HashSet<RoleEnum>(codes.length);
		for (String code : codes){
			if (ENUM_LOOK_UP.get(code) != null) {
				roles.add(ENUM_LOOK_UP.get(code));
			}
		}
		return roles;
	}
}

