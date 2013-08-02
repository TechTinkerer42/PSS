package org.ets.ereg.profile.util;

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

}
