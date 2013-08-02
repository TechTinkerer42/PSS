package org.ets.ereg.session.facade.shared.service;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;
import org.springframework.stereotype.Service;

@Service("referenceEntityBusinessService")
public class ReferenceBusinessServiceImpl implements ReferenceBusinessService{

	@Resource(name = "referenceEntityService")
    private ReferenceService referenceService;

	@Override
    public <T extends ReferenceEntityInterface> List<T> getAll(Class<T> clazz, ReferenceTypeCriteria referenceCriteria, boolean isDisplayable) {
        return referenceService.getAll(clazz, referenceCriteria, isDisplayable);

    }

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ReferenceBusinessService#getEntityByPrimaryKey(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T extends ReferenceEntityInterface> T getEntityByPrimaryKey(Class<T> clazz, String primaryKey){
			return referenceService.getEntityByPrimaryKey(clazz, primaryKey);

	}

}
