package org.ets.ereg.common.business.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateSecretKey {

	private static Logger logger = LoggerFactory.getLogger(GenerateSecretKey.class);
	
//    public static void main(String[] args) {
//        KeyGenerator generator;
//            try {
//                generator = KeyGenerator.getInstance("AES");
//                generator.init(new SecureRandom());
//                SecretKey key = generator.generateKey();
//                String encodeKey = Base64.encodeBase64URLSafeString(key.getEncoded());
//                logger.debug(encodeKey);
//
//                byte [] decodeBytes = Base64.decodeBase64(encodeKey);
//                SecretKey originalKey = new SecretKeySpec(decodeBytes, "AES");
//                if(key.equals(originalKey)){
//                    logger.debug("generated key equals to original key");
//                }
//            } catch (NoSuchAlgorithmException e1) {
//
//            	logger.error("exception occurred",e1);
//            }
//    }

}
