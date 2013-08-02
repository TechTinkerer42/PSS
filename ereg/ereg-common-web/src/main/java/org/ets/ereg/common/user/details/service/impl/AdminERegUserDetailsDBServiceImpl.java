package org.ets.ereg.common.user.details.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.security.service.AbstractERegUserDetailsDBService;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminERegUserDetailsDBServiceImpl extends AbstractERegUserDetailsDBService {
	
    @Resource(name="etsAdminUserBusinessService")
    private ETSAdminUserBusinessService etsAdminUserBusinessService;

	@Override
	protected UserDetails findUserAndPopulateAuthorities(String userName)
			throws UsernameNotFoundException {
		log.info("userName:"+userName);
        ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(userName);
        if (adminUser == null || adminUser.getActiveStatusFlag() == null || !adminUser.getActiveStatusFlag()) {
        	log.error("User not found :" + userName);
            throw new UsernameNotFoundException("User not found :" + userName);
        }
		if (BooleanUtils.isFalse(adminUser.getActiveStatusFlag())) {
			log.error("User \"" + userName + "\" not is Inactive");
			throw new UsernameNotFoundException("User \"" + userName + "\" is Inactive");
		}
        return createAndPopulateEregUser(userName, adminUser);
	}

}
