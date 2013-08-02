package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;

public interface ApplicationConfigurationDao extends Dao<ApplicationConfiguration>{

	/**
	 * Method to get ApplicationConfiguration domain object
	 *
	 * @param name
	 * @return
	 */
	public List<ApplicationConfiguration> findConfigurationsByName(String name);
}
