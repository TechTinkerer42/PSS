package org.ets.ereg.common.user.details.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.security.service.AbstractERegUserDetailsDBService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerERegUserDetailsDBServiceImpl extends AbstractERegUserDetailsDBService {
	@Resource(name="blCustomerService")
	private CustomerService customerService;
	
	@Override
	protected UserDetails findUserAndPopulateAuthorities(String userName)
			throws UsernameNotFoundException {
		log.info("userName:"+userName);
		ETSCustomer customer = (ETSCustomer)customerService.readCustomerByUsername(userName);
		if(customer == null){
			log.error("User not found :" + userName);
			throw new UsernameNotFoundException("User not found :" + userName);
		}
		if (BooleanUtils.isTrue(customer.isDeactivated())) {
			log.error("User \"" + userName + "\" not is Inactive");
			throw new UsernameNotFoundException("User \"" + userName + "\" has been Deactivated");
		}
		return createAndPopulateEregUser(userName, customer);
	}

}
