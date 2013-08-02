package org.ets.ereg.common.business.service;

import java.util.List;

import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface ReferenceService {

    /**
     * Method to get all reference data for specified domain object for displayable true/1
     *
     * @param clazz
     * @return
     */
	public <T extends ReferenceEntityInterface> List<T> getAll(Class<T> clazz, ReferenceTypeCriteria referenceCriteria, boolean isDisplayable);
	/**
	 * Method to get reference data for specified domain object and primary key
	 *
	 * @param clazz
	 * @param primaryKey
	 * @return
	 */
	public <T extends ReferenceEntityInterface> T getEntityByPrimaryKey(
			Class<T> clazz, String primaryKey);

}
