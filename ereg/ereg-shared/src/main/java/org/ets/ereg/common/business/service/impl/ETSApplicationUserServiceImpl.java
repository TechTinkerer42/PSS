package org.ets.ereg.common.business.service.impl;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ETSApplicationUserDao;
import org.ets.ereg.common.business.service.ETSApplicationUserService;
import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;
import org.springframework.stereotype.Service;

@Service("ETSApplicationUserService")
public class ETSApplicationUserServiceImpl implements ETSApplicationUserService {

	@Resource(name = "ETSApplicationUserDao")
    private ETSApplicationUserDao etsApplicationuserDao;
	
	@Override
	public ETSApplicationUser getUserByPrimaryKey(String primaryKey) {
		return etsApplicationuserDao.findByPrimaryKey(primaryKey);
	}

	public void setETSApplicationUserDao(ETSApplicationUserDao etsApplUserDao)
	{
		etsApplicationuserDao = etsApplUserDao;
	}
}
