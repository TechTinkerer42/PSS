package org.ets.ereg.profile.domain.service.common;

import javax.annotation.Resource;

import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;
import org.ets.ereg.profile.model.dao.ETSAdminUserPhoneDao;
import org.ets.ereg.profile.model.service.common.ETSAdminUserPhoneService;
import org.springframework.stereotype.Service;


@Service("etsAdminUserPhoneService")
public class ETSAdminUserPhoneServiceImpl implements  ETSAdminUserPhoneService{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Resource(name="etsAdminUserPhoneDao")
    private ETSAdminUserPhoneDao etsAdminUserPhoneDao;

    @Override
    public AdminUserPhone saveAdminUserPhone(AdminUserPhone adminPhone){
        return etsAdminUserPhoneDao.saveAdminUserPhone(adminPhone);
    }

    @Override
    public AdminUserPhone createAdminUserPhone(){
        return etsAdminUserPhoneDao.createAdminUserPhone();
    }

    @Override
    public AdminUserPhone findByPhonePrimaryKey(Object primaryKey){
        return etsAdminUserPhoneDao.findByPhonePrimaryKey(primaryKey);
    }

}
