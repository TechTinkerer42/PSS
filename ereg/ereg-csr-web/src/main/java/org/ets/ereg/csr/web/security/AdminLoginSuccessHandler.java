package org.ets.ereg.csr.web.security;

import javax.annotation.Resource;

import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("adminLoginSuccessHandler")

public class AdminLoginSuccessHandler {
	private static Logger logger = LoggerFactory.getLogger(AdminLoginSuccessHandler.class);
	@Resource(name="etsAdminUserBusinessService")
	ETSAdminUserBusinessService etsAdminUserBusinessService;
	
	public void onLogin(ERegUser loggedInUser)
	{
		try
		{
			if (!(null == loggedInUser.getGuId()))
			{
				etsAdminUserBusinessService.updateGUID(loggedInUser.getGuId(), loggedInUser.getId()); 
			}
		}
		catch(Exception e)
		{
			logger.error("Error in Guid Update {}",e);
		}
	}
}
