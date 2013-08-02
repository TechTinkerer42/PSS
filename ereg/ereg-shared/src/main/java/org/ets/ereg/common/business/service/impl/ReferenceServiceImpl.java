package org.ets.ereg.common.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ReferenceEntityDao;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.vo.CodeDescriptionPairVO;
import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;
import org.springframework.stereotype.Service;

@Service("referenceEntityService")
public class ReferenceServiceImpl implements  ReferenceService{

	@Resource(name = "referenceEntityDao")
    private ReferenceEntityDao referenceEntityDao;


	public void setReferenceEntityDao(ReferenceEntityDao referenceEntityDao) {
        this.referenceEntityDao = referenceEntityDao;
    }

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ReferenceService#getAll(java.lang.Class, org.ets.ereg.common.shared.util.ReferenceTypeCriteria, boolean)
	 */
    @Override
    public <T extends ReferenceEntityInterface> List<T> getAll(Class<T> clazz, ReferenceTypeCriteria referenceCriteria, boolean isDisplayable) {
        return referenceEntityDao.getAll(clazz, referenceCriteria, isDisplayable);

    }

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ReferenceService#getEntityByPrimaryKey(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T extends ReferenceEntityInterface> T getEntityByPrimaryKey(Class<T> clazz, String primaryKey){
			return referenceEntityDao.getEntityByPrimaryKey(clazz, primaryKey);

	}
	
	public <T extends CodeDescriptionPairVO> List<T> getTypeListUseNaviteSql(Class clz, ReferenceTypeCriteria criteria) {
		List<? extends CodeDescriptionPairVO> list = referenceEntityDao.getReferenceData(criteria.getNativeSql(),criteria.getNativeSqlParamNames(),criteria.getNativeSqlParamValues());
		if (list != null) 
		{
			return (List<T>) list;
		}
		// throw exception
		throw new IllegalArgumentException(
				"Reference map does not contain class [" + clz.getName() + "]");

	}

}
