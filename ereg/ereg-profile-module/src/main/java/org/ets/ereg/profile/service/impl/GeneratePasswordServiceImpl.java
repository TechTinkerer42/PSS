package org.ets.ereg.profile.service.impl;

import javax.annotation.Resource;

import org.ets.ereg.common.business.util.GenerateUniqueCode;
import org.ets.ereg.profile.service.GeneratePasswordService;
import org.springframework.stereotype.Service;

@Service("generatePasswordService")
public class GeneratePasswordServiceImpl implements GeneratePasswordService/*, InitializingBean*/{

    @Resource(name = "generateUniqueCode")
    private GenerateUniqueCode generateUniqueCode;

    /*
     * (non-Javadoc)
     * @see org.ets.ereg.profile.service.GeneratePasswordService#generateNewPassword()
     */
    @Override
    public String generateNewPassword() {
        return generateUniqueCode.generateNewPassword();
    }

	@Override
	public String generateRandomString() {
	    return generateUniqueCode.generateRandomString();
	}

}
