package org.ets.ereg.security.service;

import javax.annotation.Resource;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class AbstractERegUserDetailsDBService extends
		AbstractERegUserDetailsService implements UserDetailsService {

    @Resource(name="applicationConfigurationService")
    private ApplicationConfigurationService applicationService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
        if(!ApplicationConfiguration.AUTH_MECHANISM_DATABASE.equals(applicationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM))){
        	log.error("Authentication mechanism is not configured as database");
            throw new UsernameNotFoundException("Authentication mechanism is not configured as database");
        }
        return findUserAndPopulateAuthorities(username);
	}

}
