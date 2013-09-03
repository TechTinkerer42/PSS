package org.ets.ereg.profile.util;

import java.security.MessageDigest;

import javax.annotation.Resource;

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;
import org.ets.ereg.profile.model.service.common.ETSAdminUserService;
import org.springframework.stereotype.Component;

@Component("profileUtils")
public class ProfileUtils {
	   @Resource(name = "etsAdminUserService")
	   private ETSAdminUserService etsAdminUserService;
	   
	   public ETSAdminUser getETSAdminUser(Long adminId){
		   TCAProfileVO tcaProfile =etsAdminUserService.readByAdminUserId(adminId);
		   return tcaProfile.getAdminUser();
	   }

	   public static String encryptString(String str) throws Exception
	   {
		   MessageDigest md = MessageDigest.getInstance("sha");
           md.update(str.getBytes());
           
           byte byteData[] = md.digest();

          //convert the byte to hex format method 1
           StringBuffer sb = new StringBuffer();
           for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
           }
           
		   return sb.toString();
	   }
}
