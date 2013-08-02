package org.ets.ereg.profile.model.dao;

import org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;

public interface ETSAdminUserDao extends AdminUserDao{
	final String FIND_BY_GUID = "ETSAdminUser.findByGuID";
	final String FIND_BY_USERNAMEANDFLAG = "ETSAdminUser.findByUsrnameAndIntFlg";
	ETSAdminUser findUserByGuId(String guid);
	ETSAdminUser findUserByUsernameAndInternalFlag(String username, Boolean internalUserFlag);
}
