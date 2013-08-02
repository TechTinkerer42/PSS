package org.ets.ereg.common.user.details.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.model.service.common.ETSAdminUserService;
import org.ets.ereg.security.service.AbstractERegUserDetailsOAMPreAuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminERegUserDetailsOAMPreAuthServiceImpl extends AbstractERegUserDetailsOAMPreAuthService {

	@Resource(name = "etsAdminUserService")
	private ETSAdminUserService etsAdminUserService;

	@Override
	protected UserDetails findUserAndPopulateAuthorities(String userName) throws UsernameNotFoundException {
		log.info("Fetching User from DB");
		ETSAdminUser adminUser = etsAdminUserService.findUserByUsernameAndInternalFlag(StringUtils.lowerCase(userName),
				isInternalUser(getEIASUserTypeHeaderValue()));
		if (adminUser == null) {
			log.error("User \"" + userName + "\" not found");
			throw new UsernameNotFoundException("User \"" + userName + "\" not found");
		}
		if (BooleanUtils.isFalse(adminUser.getActiveStatusFlag())) {
			log.error("User \"" + userName + "\" not is Inactive");
			throw new UsernameNotFoundException("User \"" + userName + "\" is Inactive");
		}
		return createAndPopulateEregUser(userName, adminUser);
	}
}
