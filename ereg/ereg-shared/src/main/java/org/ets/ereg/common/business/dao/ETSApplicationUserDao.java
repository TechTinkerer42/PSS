package org.ets.ereg.common.business.dao;

import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;

public interface ETSApplicationUserDao extends Dao<ETSApplicationUser> {

    public ETSApplicationUser findByPrimaryKey(Object primaryKey);
}
