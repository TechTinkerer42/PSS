package org.ets.ereg.session.facade.shared.service.util;

public interface GenerateBase64StringBusinessService {

    String encodeBase64String(String inputString);

    String decodeBase64String(String encodeString);

}
