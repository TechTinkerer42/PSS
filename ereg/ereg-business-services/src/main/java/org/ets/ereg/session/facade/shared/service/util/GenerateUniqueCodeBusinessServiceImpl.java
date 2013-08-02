package org.ets.ereg.session.facade.shared.service.util;

import javax.annotation.Resource;

import org.ets.ereg.common.business.util.GenerateUniqueCode;
import org.springframework.stereotype.Service;

@Service("generateUniqueCodeBusinessService")
public class GenerateUniqueCodeBusinessServiceImpl implements GenerateUniqueCodeBusinessService {

    @Resource(name = "generateUniqueCode")
    private GenerateUniqueCode generateUniqueCode;


    @Override
    public String generateRandomStringForBase64() {
        return generateUniqueCode.generateRandomStringForBase64();
    }

}
