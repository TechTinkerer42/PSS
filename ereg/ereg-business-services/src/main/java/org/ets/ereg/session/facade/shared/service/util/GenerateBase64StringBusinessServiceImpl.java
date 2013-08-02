package org.ets.ereg.session.facade.shared.service.util;

import javax.annotation.Resource;

import org.ets.ereg.common.business.util.GenerateBase64String;
import org.springframework.stereotype.Service;

@Service("generateBase64StringBusinessService")
public class GenerateBase64StringBusinessServiceImpl implements GenerateBase64StringBusinessService{

    @Resource(name = "generateBase64String")
    private GenerateBase64String generateBase64String;

    @Override
    public String encodeBase64String(String inputString) {
        return generateBase64String.encodeBase64String(inputString);
    }

    @Override
    public String decodeBase64String(String encodeString) {
        return generateBase64String.decodeBase64String(encodeString);
    }


}
