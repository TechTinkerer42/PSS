package org.ets.ereg.common.business.dao.impl;

import org.ets.ereg.common.business.dao.ETSApplicationUserDao;
import org.ets.ereg.domain.common.ETSApplicationUserImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;
import org.springframework.stereotype.Repository;

@Repository("ETSApplicationUserDao")
public class ETSApplicationUserDaoImpl extends AbstractDaoImpl<ETSApplicationUser> implements ETSApplicationUserDao {

	@Override
	public Class<ETSApplicationUserImpl> getEntityClass() {
		return  ETSApplicationUserImpl.class;
	}

}
