package org.ets.ereg.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.profile.core.domain.CustomerRole;
import org.broadleafcommerce.profile.core.service.RoleService;
import org.ets.ereg.common.enums.EIASHeadersEnum;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.security.user.ERegUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class AbstractERegUserDetailsService implements ERegUserDetails {
	@Resource(name = "blRoleService")
	private RoleService roleService;

	protected Logger log = LoggerFactory.getLogger(getClass());
	protected static final String INTERNAL_USER_IDENTIFICATION_PATTERN = "Internal";
	protected static final String EXTERNAL_USER_IDENTIFICATION_PATTERN = "External";

	protected abstract UserDetails findUserAndPopulateAuthorities(String userName) throws UsernameNotFoundException;

	protected ERegUser createAndPopulateEregUser(String username, ETSAdminUser adminUser) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<RoleEnum> roles = new HashSet<RoleEnum>();
		for (AdminRole role : adminUser.getAllRoles()) {
			roles.add(RoleEnum.get(role.getName()));
			for (AdminPermission permission : role.getAllPermissions()) {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}
		for (AdminPermission adminPermission : adminUser.getAllPermissions()) {
			authorities.add(new SimpleGrantedAuthority(adminPermission.getName()));
		}
		ERegUser eregUser = new ERegUser(username, StringUtils.stripToEmpty(adminUser.getPassword()), true, true, true, true, authorities);
		eregUser.setId(adminUser.getId());
		eregUser.setFirstName(adminUser.getFirstName());
		eregUser.setLastName(adminUser.getLastName());
		eregUser.setGuId(getHeaderValue(EIASHeadersEnum.EIASSTRINGGUID.getCode()));
		eregUser.setEmailAdress(adminUser.getEmail());
		eregUser.setActive(adminUser.getActiveStatusFlag());
		eregUser.setUserType(adminUser.getInternalUserFlag() ? INTERNAL_USER_IDENTIFICATION_PATTERN
				: EXTERNAL_USER_IDENTIFICATION_PATTERN);
		eregUser.setEregUserRoles(roles);
		log.info("ERegUser:" + eregUser);
		log.info("is ERegUser CSR :" + eregUser.hasCSRRole());
		return eregUser;
	}

	protected ERegUser createAndPopulateEregUser(String username, ETSCustomer customer) {
		// get roles of the customer
		List<CustomerRole> customerRoles = roleService.findCustomerRolesByCustomerId(customer.getId());
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<RoleEnum> roles = new HashSet<RoleEnum>();
		if (CollectionUtils.isNotEmpty(customerRoles)) {
			for (CustomerRole customerRole : customerRoles) {
				roles.add(RoleEnum.get(customerRole.getRoleName()));
				authorities.add(new SimpleGrantedAuthority(customerRole.getRoleName()));
			}
		}
		ERegUser eregUser = new ERegUser(username, StringUtils.stripToEmpty(customer.getPassword()), true, true, true, true, authorities);
		eregUser.setId(customer.getId());
		eregUser.setFirstName(customer.getFirstName());
		eregUser.setLastName(customer.getLastName());
		eregUser.setGuId(getHeaderValue(EIASHeadersEnum.EIASSTRINGGUID.getCode()));
		eregUser.setEmailAdress(customer.getEmailAddress());
		eregUser.setActive(!customer.isDeactivated());
		eregUser.setUserType(customer.getInternalUserFlag() ? INTERNAL_USER_IDENTIFICATION_PATTERN
				: EXTERNAL_USER_IDENTIFICATION_PATTERN);
		eregUser.setEregUserRoles(roles);
		log.info("ERegUser:" + eregUser);
		return eregUser;
	}

	protected HttpServletRequest getCurrentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	protected String getHeaderValue(String headerName) {
		return getCurrentRequest().getHeader(headerName);
	}

}