package org.ets.ereg.common.business.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;



@Service("generateBase64String")
public class GenerateBase64String implements InitializingBean{

    private static Logger log = LoggerFactory.getLogger(GenerateBase64String.class);

    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

    private String keyStoreSecretKey;
    private String keyStorePadding;
    private String keyStoreAlgorithm;

    @Override
    public void afterPropertiesSet() throws Exception {

        keyStoreSecretKey = getApplicationConfigurationvalue(Constant.KEY_STORE_SECRET_KEY);
        keyStoreAlgorithm = getApplicationConfigurationvalue(Constant.KEY_STORE_ALGORITHM);
        keyStorePadding = getApplicationConfigurationvalue(Constant.KEY_STORE_PADDING);

    }

    /**
    *
    * Method to get ApplicationConfiguration value from database
    *
    * @param key
    * @return
    */
   private String getApplicationConfigurationvalue(String key) {
       return applicationConfigurationService.findApplicationConfigurationValue(key);
   }

    public String encodeBase64String(String inputString) {
       return encrypt(inputString);
    }

    public String decodeBase64String(String encodedString) {
        return decrypt(encodedString);
    }


    private String encrypt(String message)  {
    String base64 = null;
    try {
        SecretKey key = getKey();
        Cipher cipher = Cipher.getInstance(keyStorePadding);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] stringBytes = message.getBytes("UTF8");
        byte[] raw = cipher.doFinal(stringBytes);
        String encoder = Base64.encodeBase64URLSafeString(raw);
        base64 = URLEncoder.encode(encoder, "UTF8");
    } catch (NoSuchAlgorithmException e) {

        log.error(e.getMessage());
    } catch (IOException e) {

        log.error(e.getMessage());
    } catch (InvalidKeyException e) {

        log.error(e.getMessage());
    } catch (Exception e) {

        log.error(e.getMessage());
    }

    return base64;

    }

    private String decrypt(String encrypted) {

        String decryptString = null;
        try {
            SecretKey key = getKey();
            Cipher cipher = Cipher.getInstance(keyStorePadding);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] raw = Base64.decodeBase64(encrypted);
            byte[] stringBytes = cipher.doFinal(raw);
            decryptString = new String(stringBytes, "UTF8");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());

        } catch (IOException e) {

            log.error(e.getMessage());
        } catch (NoSuchPaddingException e) {

            log.error(e.getMessage());
        } catch (InvalidKeyException e) {

            log.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {

            log.error(e.getMessage());
        } catch (BadPaddingException e) {

            log.error(e.getMessage());
        }
        return decryptString;
    }

    private SecretKey getKey() {
        byte[] decodeBytes;
        SecretKey originalKey = null;
        decodeBytes = Base64.decodeBase64(keyStoreSecretKey);
        originalKey = new SecretKeySpec(decodeBytes, keyStoreAlgorithm);
        return originalKey;
    }
}
