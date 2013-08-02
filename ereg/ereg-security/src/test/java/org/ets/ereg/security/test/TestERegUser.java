package org.ets.ereg.security.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.security.user.ERegUser;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

public class TestERegUser {
	@Test
	public void testERegUserRoles(){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		ERegUser eregUser = new ERegUser("test", "test", true, true, true, true, authorities);
		assertFalse(eregUser.hasCSRRole());
		assertFalse(eregUser.hasTCARole());
		assertFalse(eregUser.isUserInRole("test"));
		assertFalse(eregUser.isUserInRole("ROLE_CUSTOMER"));
		assertFalse(eregUser.isUserInAnyRoles("ROLE_SSD_CSR_LEAD","ROLE_CUSTOMER"));
		assertFalse(eregUser.isUserInAnyRoles("ROLE_SSD_CSR_LEAD","ROLE_ADMIN"));
		assertFalse(eregUser.isUserInAnyRoles("ROLE_SSD_CSR_LEAD","ROLE_ADMIN","ROLE_TEST_CENTER_ADMIN"));
		assertFalse(eregUser.isUserInAllRoles("ROLE_SSD_CSR_LEAD","ROLE_CUSTOMER"));
		assertFalse(eregUser.isUserInAllRoles("ROLE_SSD_CSR_LEAD","ROLE_CUSTOMER","ROLE_TEST_CENTER_ADMIN"));
		Set<RoleEnum> roles = new HashSet<RoleEnum>();
		eregUser.setEregUserRoles(roles);
		roles.add(RoleEnum.ROLE_TEST_CENTER_ADMIN);
		assertFalse(eregUser.hasCSRRole());
		assertTrue(eregUser.hasTCARole());
		assertTrue(eregUser.isUserInRole("ROLE_TEST_CENTER_ADMIN"));
		roles.add(RoleEnum.ROLE_SSD_CSR_LEAD);
		assertTrue(eregUser.hasCSRRole());
		assertTrue(eregUser.hasTCARole());
		assertTrue(eregUser.isUserInRole("ROLE_SSD_CSR_LEAD"));
		roles.add(RoleEnum.ROLE_CUSTOMER);
		assertTrue(eregUser.isUserInAnyRoles("ROLE_SSD_CSR_LEAD","ROLE_CUSTOMER"));
		assertTrue(eregUser.isUserInAnyRoles("ROLE_SSD_CSR_LEAD","ROLE_ADMIN"));
		assertTrue(eregUser.isUserInAnyRoles("ROLE_SSD_CSR_LEAD","ROLE_ADMIN","ROLE_TEST_CENTER_ADMIN"));
		assertTrue(eregUser.isUserInAllRoles("ROLE_SSD_CSR_LEAD","ROLE_CUSTOMER"));
		assertTrue(eregUser.isUserInAllRoles("ROLE_SSD_CSR_LEAD","ROLE_CUSTOMER","ROLE_TEST_CENTER_ADMIN"));
	}

}
