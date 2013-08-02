package org.ets.ereg.profile.model.dao;

import java.io.Serializable;

import org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao;
import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;

public interface ETSAdminUserPhoneDao extends Serializable{

    AdminUserPhone saveAdminUserPhone(AdminUserPhone adminPhone);

    AdminUserPhone createAdminUserPhone();

    AdminUserPhone findByPhonePrimaryKey(Object primaryKey);

}
