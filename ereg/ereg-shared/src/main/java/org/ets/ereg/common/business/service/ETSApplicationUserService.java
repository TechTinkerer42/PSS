package org.ets.ereg.common.business.service;

import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;

public interface ETSApplicationUserService {
	
	public ETSApplicationUser getUserByPrimaryKey(String primaryKey);

}
