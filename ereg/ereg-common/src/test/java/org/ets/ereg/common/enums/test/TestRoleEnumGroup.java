package org.ets.ereg.common.enums.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.common.enums.RoleEnumGroup;
import org.junit.Test;

public class TestRoleEnumGroup {
	@Test
	public void testGetRoles() {
		Set<RoleEnum> roles = RoleEnumGroup.ADMIN_ROLES_GROUP.getRolesInGroup();
		assertNotNull(roles);
		assertThat(roles.size(), equalTo(13));
	
	}

}
