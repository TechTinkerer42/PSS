package org.ets.ereg.common.business.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.ets.ereg.common.business.dao.ApplicationConfigurationDao;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository("applicationConfigurationDao")
public class ApplicationConfigurationDaoImpl extends AbstractDaoImpl<ApplicationConfiguration>implements ApplicationConfigurationDao {
	private static Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.dao.ApplicationConfigurationDao#findConfigurationsByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationConfiguration> findConfigurationsByName(String name) {
		Query query = entityManager.createNamedQuery("ApplicationConfiguration.findByName");
		query.setParameter("name", name);
		try{
			return (List<ApplicationConfiguration>) query.getResultList();
		} catch(NoResultException nre) {
			log.info("No result found");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.dao.impl.AbstractDaoImpl#getEntityClass()
	 */
	@Override
	public Class<ApplicationConfiguration> getEntityClass() {
		return  ApplicationConfiguration.class;
	}

}
