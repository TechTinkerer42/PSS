package org.ets.ereg.common.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.ets.ereg.common.business.dao.ReferenceEntityDao;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria.ReferenceTypeOrderBy;
import org.ets.ereg.common.vo.CodeDescriptionPairVO;
import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;
import org.hibernate.ejb.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@SuppressWarnings("rawtypes")
@Repository("referenceEntityDao")
public class ReferenceEntityDaoImpl extends AbstractDaoImpl implements ReferenceEntityDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceEntityDaoImpl.class);

    @PersistenceContext(unitName = "blPU")
    private EntityManager entityManager;

	@Resource(name="blEntityConfiguration")
	private EntityConfiguration entityConfiguration;

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.dao.ReferenceEntityDao#getAll(java.lang.Class, org.ets.ereg.common.shared.util.ReferenceTypeCriteria, boolean)
	 */
	@Override
	public <T extends ReferenceEntityInterface> List<T> getAll(Class<T> clazz, ReferenceTypeCriteria referenceCriteria, boolean isDisplayable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        Root<T> rootClazz = criteriaQuery.from(getImplClass(clazz));
        criteriaQuery.select(rootClazz);
        ParameterExpression<Boolean> param = null;
        if(isDisplayable){
            param = builder.parameter(Boolean.class);
            criteriaQuery.where(builder.equal(rootClazz.get("isDisplayable"), param));
        }
        if(referenceCriteria != null) {
            List<Order> orderList = new ArrayList<Order>();
            if(referenceCriteria.getOrderbyList() != null) {
                for (ReferenceTypeOrderBy orderBy : referenceCriteria.getOrderbyList()){
                    orderList.add(orderBy.isAscending() ? builder.asc(rootClazz.get(orderBy.getOrderByAttribute())) : builder.desc(rootClazz.get(orderBy.getOrderByAttribute())));
                }
            }
            criteriaQuery.orderBy(orderList);
        }

        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setHint(QueryHints.HINT_CACHEABLE, true);

        if(isDisplayable){
            typedQuery.setParameter(param, true);
        }
        return typedQuery.getResultList();
    }

	/**
	 * Method to get the Implementation class from the domain interface name for JPA Criteria.
	 *
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
    private <T>Class<T> getImplClass(Class<T> clazz) {
		Object obj = entityConfiguration.createEntityInstance(clazz.getName());
        return (Class<T>) obj.getClass();
    }

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.dao.ReferenceEntityDao#getEntityByPrimaryKey(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T extends ReferenceEntityInterface> T getEntityByPrimaryKey(
            Class<T> clazz, String primaryKey){
	    return entityManager.find(getImplClass(clazz), primaryKey);
	}

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<CodeDescriptionPairVO> getReferenceData(String query) {
		// String[] aliases = new String[] { "code", "description" };
		@SuppressWarnings("unchecked")
		List<CodeDescriptionPairVO> result = (List<CodeDescriptionPairVO>) executeNativeSQL(query, null, null,
				CodeDescriptionPairVO.class);
		if (result != null && !result.isEmpty())
			return result;
		else
			return null;
	}

	public List<CodeDescriptionPairVO> getReferenceData(String query, String[] paramNames, Object[] paramValues) {
		@SuppressWarnings("unchecked")
		List<CodeDescriptionPairVO> result = (List<CodeDescriptionPairVO>) executeNativeSQL(query, paramNames,
				paramValues, CodeDescriptionPairVO.class);
		if (result != null && !result.isEmpty())
			return result;
		else
			return null;
	}

}
