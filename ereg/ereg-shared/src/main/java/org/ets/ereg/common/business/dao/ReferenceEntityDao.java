package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.vo.CodeDescriptionPairVO;
import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface ReferenceEntityDao {

     /**
     * Method to get the list of domain objects by using domain object interface according to disaplyable flag
     *
     * @param clazz
     * @return
     */
    public <T extends ReferenceEntityInterface> List<T> getAll(Class<T> clazz, ReferenceTypeCriteria referenceCriteria, boolean isDisplayable);

	/**
	 * Method to get domain object using domain object interface and primary key.
	 *
	 * @param primaryKey
	 * @return
	 */
    public <T extends ReferenceEntityInterface> T getEntityByPrimaryKey(
            Class<T> clazz, String primaryKey);
    
    List<CodeDescriptionPairVO> getReferenceData(String query);
    List<CodeDescriptionPairVO> getReferenceData(String query, String[] paramNames, Object[] paramValues);

}
