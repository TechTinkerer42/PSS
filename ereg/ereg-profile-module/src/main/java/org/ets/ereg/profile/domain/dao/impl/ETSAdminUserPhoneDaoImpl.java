package org.ets.ereg.profile.domain.dao.impl;


import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;
import org.ets.ereg.profile.model.dao.ETSAdminUserPhoneDao;
import org.springframework.stereotype.Repository;

@Repository("etsAdminUserPhoneDao")
public class ETSAdminUserPhoneDaoImpl extends AbstractDaoImpl<AdminUserPhone> implements ETSAdminUserPhoneDao {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Class<AdminUserPhone> getEntityClass() {
        return AdminUserPhone.class;
    }

    @Override
    public AdminUserPhone saveAdminUserPhone(AdminUserPhone adminPhone){
        return save(adminPhone);
    }

    @Override
    public AdminUserPhone createAdminUserPhone(){
        return create();
    }

    @Override
    public AdminUserPhone findByPhonePrimaryKey(Object primaryKey){
        return findByPrimaryKey(primaryKey);
    }

}


