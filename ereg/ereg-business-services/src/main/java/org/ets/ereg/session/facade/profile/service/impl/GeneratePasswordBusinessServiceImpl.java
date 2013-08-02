package org.ets.ereg.session.facade.profile.service.impl;

import javax.annotation.Resource;

import org.ets.ereg.profile.service.GeneratePasswordService;
import org.ets.ereg.session.facade.profile.service.GeneratePasswordBusinessService;

import org.springframework.stereotype.Service;

@Service("generatePasswordBusinessService")
public class GeneratePasswordBusinessServiceImpl implements
		GeneratePasswordBusinessService {

	@Resource(name = "generatePasswordService")
	private GeneratePasswordService generatePasswordService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.ets.ereg.session.facade.profile.service.GeneratePasswordBusinessService
	 * #generateNewPassword()
	 */
	@Override
	public String generateNewPassword() {
		return generatePasswordService.generateNewPassword();
	}

	@Override
	public String generateRandomString() {
		return generatePasswordService.generateRandomString();
	}

}
