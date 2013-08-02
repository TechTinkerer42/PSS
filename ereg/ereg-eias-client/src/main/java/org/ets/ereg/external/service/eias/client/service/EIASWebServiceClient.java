/**
 * 
 */
package org.ets.ereg.external.service.eias.client.service;

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.external.service.eias.client.types.UserVO;


public interface EIASWebServiceClient {
	String createUser(ETSCustomer customer);

	boolean searchUser(String userID);

	String modifyUser(ETSCustomer customer);

	String resetPassword(String userID, String objectID, String password, String emailNotifyFlag);

	String createUser(ETSAdminUser tca);

	String modifyUser(ETSAdminUser tca);
	
	UserVO createUserFromCSR(ETSCustomer customer);
	
}
