package org.ets.ereg.common.business.util;

import javax.annotation.Resource;

import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.springframework.stereotype.Component;

@Component("eregUtils")
public class EregUtils {
	
    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;
    

	public  boolean isOAMAuthentication() {
		String authMechanism = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM);
		if(ApplicationConfiguration.AUTH_MECHANISM_OAM.equals(authMechanism)){
			return true;
		}else if(ApplicationConfiguration.AUTH_MECHANISM_DATABASE.equals(authMechanism)){
			return false;
			
		}
		return true;
		
	}
	public  boolean isDBAuthentication() {
		String authMechanism = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM);
		if(ApplicationConfiguration.AUTH_MECHANISM_DATABASE.equals(authMechanism)){
			return true;
		}else if(ApplicationConfiguration.AUTH_MECHANISM_OAM.equals(authMechanism)){
			return false;
		}
		return true;
		
	}

}
