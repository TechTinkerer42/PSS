package org.ets.ereg.domain.interfaces.model.common;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.admin.id.AdminUserPhoneId;

public interface AdminUserPhone extends Serializable{

    AdminUserPhoneId getAdminUserPhoneId();

    void setAdminUserPhoneId(AdminUserPhoneId adminUserPhoneId);

    ETSAdminUser getEtsAdminUser();

    void setEtsAdminUser(ETSAdminUser etsAdminUser);

    ETSPhone getEtsPhone();

    void setEtsPhone(ETSPhone etsPhone);

    String getName();

    void setName(String name);

}
