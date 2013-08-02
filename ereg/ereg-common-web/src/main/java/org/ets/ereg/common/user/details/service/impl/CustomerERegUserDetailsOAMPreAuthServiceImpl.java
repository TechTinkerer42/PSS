package org.ets.ereg.common.user.details.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.security.service.AbstractERegUserDetailsOAMPreAuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerERegUserDetailsOAMPreAuthServiceImpl extends AbstractERegUserDetailsOAMPreAuthService {
	@Resource(name = "etsCustomerService")
	private ETSCustomerService etsCustomerService;

	@Override
	protected UserDetails findUserAndPopulateAuthorities(String userName) throws UsernameNotFoundException {
		log.info("Fetching User from DB");
		ETSCustomer customer = etsCustomerService.findCustomerByUsernameAndInternalFlag(
				StringUtils.lowerCase(userName), isInternalUser(getEIASUserTypeHeaderValue()));
		if (customer == null) {
			log.error("User \"" + userName + "\" not found");
			throw new UsernameNotFoundException("User \"" + userName + "\" not found");
		}
		if (BooleanUtils.isTrue(customer.isDeactivated())) {
			log.error("User \"" + userName + "\" is Inactive");
			throw new UsernameNotFoundException("User \"" + userName + "\" has been Deactivated");
		}
		return createAndPopulateEregUser(userName, customer);
	}

}
