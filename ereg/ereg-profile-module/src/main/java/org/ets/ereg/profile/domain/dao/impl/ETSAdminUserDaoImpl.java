package org.ets.ereg.profile.domain.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.openadmin.server.security.dao.AdminUserDaoImpl;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.model.dao.ETSAdminUserDao;
import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

@Repository("etsAdminUserDao")
public class ETSAdminUserDaoImpl extends AdminUserDaoImpl implements
ETSAdminUserDao {

	@Override
	public ETSAdminUser findUserByGuId(String guid) {
		TypedQuery<ETSAdminUser> query = em.createNamedQuery(FIND_BY_GUID,
				ETSAdminUser.class);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		query.setParameter("guid", guid);
		List<ETSAdminUser> users = query.getResultList();
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public ETSAdminUser findUserByUsernameAndInternalFlag(String username,
			Boolean internalUserFlag) {
		TypedQuery<ETSAdminUser> query = em.createNamedQuery(FIND_BY_USERNAMEANDFLAG,
				ETSAdminUser.class);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		query.setParameter("internalUserFlag", internalUserFlag);
		query.setParameter("login", username);
		List<ETSAdminUser> users = query.getResultList();
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
}
