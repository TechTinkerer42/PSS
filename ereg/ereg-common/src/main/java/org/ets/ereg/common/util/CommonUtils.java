package org.ets.ereg.common.util;



import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component("commonUtils")
public class CommonUtils {
	   
	   public static String encodeString(String str){
		   Base64 b = new Base64();
		   return new String(b.encode(str.getBytes()));
	   }
	   
	   public static String deCodeString(String str){
		   Base64 b = new Base64();
		   return new String(b.decode(str.getBytes()));
	   }
		
}
