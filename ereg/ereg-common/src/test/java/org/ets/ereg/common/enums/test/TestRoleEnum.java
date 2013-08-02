package org.ets.ereg.common.enums.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.ets.ereg.common.enums.RoleEnum;
import org.junit.Test;

public class TestRoleEnum {

	@Test
	public void testGetRole() {
		RoleEnum role = RoleEnum.get("test");
		assertEquals(role, RoleEnum.NULL);
		role = RoleEnum.get("ROLE_CUSTOMER");
		assertNotNull(role);
		assertEquals(role, RoleEnum.ROLE_CUSTOMER);
	}

	@Test
	public void testGetRoles() {
		Set<RoleEnum> roles = RoleEnum.get("test", "test");
		assertNotNull(roles);
		assertThat(roles.size(), equalTo(0));
		roles = RoleEnum.get("ROLE_CUSTOMER", "test");
		assertNotNull(roles);
		assertThat(roles.size(), equalTo(1));
		assertTrue(roles.contains(RoleEnum.ROLE_CUSTOMER));
		roles = RoleEnum.get("ROLE_CUSTOMER", "ROLE_ADMIN");
		assertNotNull(roles);
		assertThat(roles.size(), equalTo(2));
		assertTrue(roles.containsAll(Arrays.asList(RoleEnum.ROLE_CUSTOMER, RoleEnum.ROLE_ADMIN)));
	}


}
