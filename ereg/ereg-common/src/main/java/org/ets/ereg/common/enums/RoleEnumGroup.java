package org.ets.ereg.common.enums;

import java.util.EnumSet;


public enum RoleEnumGroup {
	CSR_GROUP("CSR_GROUP","All CSR Roles"){
		@Override
		public EnumSet<RoleEnum> getRolesInGroup() {
			return EnumSet.of(RoleEnum.ROLE_RESO_CSR, RoleEnum.ROLE_SSD_CSR_LEAD, RoleEnum.ROLE_CUSTOMER_SERVICE_REP,
					RoleEnum.ROLE_SSD_CSR, RoleEnum.ROLE_CUSTOMER_SERVICE_REP_LEAD);
		}
	},
	ADMIN_ROLES_GROUP("ADMIN_ROLES_GROUP","All ADMIN Roles"){
		@Override
		public EnumSet<RoleEnum> getRolesInGroup() {
			
			EnumSet<RoleEnum> roleSet=EnumSet.of(RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_MERCHANDISE_MANAGER, 
					RoleEnum.ROLE_PROMOTION_MANAGER, RoleEnum.ROLE_CONTENT_EDITOR, RoleEnum.ROLE_CONTENT_APPROVER, 
					RoleEnum.ROLE_TEST_CENTER_ADMIN,RoleEnum.ROLE_STATE_ADMIN,RoleEnum.ROLE_PROGRAM_MANAGER);
			roleSet.addAll(CSR_GROUP.getRolesInGroup());
			return roleSet;
		}
	},
	NULL(null,"Null"){
		@Override
		public EnumSet<RoleEnum> getRolesInGroup() {
			return EnumSet.of(RoleEnum.NULL);
		}
	};
	
	public abstract EnumSet<RoleEnum> getRolesInGroup();
	
	String code;
	String description;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	RoleEnumGroup(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public static RoleEnumGroup findByCode(String code) {
		for(RoleEnumGroup roleEnumGroup : values()) {
			if(roleEnumGroup.getCode().equals(code)) { 
				return roleEnumGroup;
			}
		}
		return NULL;
	}

}
