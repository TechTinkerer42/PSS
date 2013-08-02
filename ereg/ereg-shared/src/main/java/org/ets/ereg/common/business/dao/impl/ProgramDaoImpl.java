package org.ets.ereg.common.business.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ets.ereg.common.business.dao.ProgramDao;
import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.springframework.stereotype.Repository;

@Repository("programDao")
public class ProgramDaoImpl extends AbstractDaoImpl<ProgramType> implements
        ProgramDao {

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.dao.impl.AbstractDaoImpl#getEntityClass()
	 */
    @Override
    public Class<ProgramTypeImpl> getEntityClass() {
        return ProgramTypeImpl.class;

    }

    /*
     * (non-Javadoc)
     * @see org.ets.ereg.common.business.dao.ProgramDao#findByPrimaryKey(java.lang.Object)
     */
    @Override
    public ProgramType findByPrimaryKey(Object primaryKey) {
        return entityManager.find(getEntityClass(), primaryKey);
    }

    /*
     * (non-Javadoc)
     * @see org.ets.ereg.common.business.dao.ProgramDao#getAll()
     */
    @Override
    public List<ProgramType> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProgramType> criteriaQuery = builder.createQuery(ProgramType.class);
        Root<ProgramTypeImpl> rootClazz = criteriaQuery.from(getEntityClass());
        criteriaQuery.select(rootClazz);
        TypedQuery<ProgramType> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();

    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<Test> getAllTests(String programCode) {
		Query query	 = entityManager.createNamedQuery("ProgramType.getAllTestsForProgram");
		query.setParameter("programCode", programCode);
		return (List<Test>) query.getResultList();		
	}

}
