package org.ets.ereg.profile.model.service.common;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;

public interface ETSAdminUserPhoneService extends Serializable{

    AdminUserPhone saveAdminUserPhone(AdminUserPhone adminPhone);

    AdminUserPhone findByPhonePrimaryKey(Object primaryKey);

    AdminUserPhone createAdminUserPhone();




}
