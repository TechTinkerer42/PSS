package org.ets.ereg.common.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.common.business.query.binder.DefaultQueryParameterBinder;
import org.ets.ereg.common.business.query.binder.NameBasedParameterBinder;
import org.ets.ereg.common.business.query.binder.ParameterBinder;
import org.ets.ereg.common.business.util.ReflectionUtils;
import org.ets.ereg.common.exception.ERegRuntimeException;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria.ReferenceTypeOrderBy;
import org.hibernate.transform.Transformers;

@SuppressWarnings("unchecked")
public abstract class AbstractDaoImpl<T> implements  Dao<T> {

	@PersistenceContext(unitName="blPU")
    protected EntityManager entityManager;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public abstract Class<? extends T> getEntityClass();

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public EntityConfiguration getEntityConfiguration() {
		return entityConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.dao.Dao#findByPrimaryKey(java.lang.Object)
	 */
	@Override
	public T findByPrimaryKey(Class<T> clazz, Object primaryKey){
		return getEntityManager().find(getImplClass(clazz), primaryKey);
	}
	
	public T findByPrimaryKey(Object primaryKey){
		return getEntityManager().find(getEntityClass(), primaryKey);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.dao.Dao#getAll()
	 */
    @SuppressWarnings("hiding")
    @Override
    public <T> List<T> getAll(Class<T> clazz, ReferenceTypeCriteria referenceCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        Root<T> rootClazz = criteriaQuery.from(getImplClass(clazz));
        criteriaQuery.select(rootClazz);
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
        return typedQuery.getResultList();
    }
    
    @SuppressWarnings("hiding")
    @Override
    public <T> List<T> getAll(ReferenceTypeCriteria referenceCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) builder.createQuery(getEntityClass());
        Root<T> rootClazz = (Root<T>) criteriaQuery.from(getEntityClass());
        criteriaQuery.select(rootClazz);
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
        return typedQuery.getResultList();
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    private <T>Class<T> getImplClass(Class<T> clazz) {
        Object obj = entityConfiguration.createEntityInstance(clazz.getName());
        return (Class<T>) obj.getClass();
    }
    @Override
    public T save(T t){
    	return entityManager.merge(t);
    }
    
    public void delete(T t){
    	entityManager.remove(t);
    }
    
    @Override
    public void refresh(T t) {
    	entityManager.refresh(t);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public T create(){
    	return (T)entityConfiguration.createEntityInstance(getEntityClass().getName());
    }
	
	@Override
	public void detach(T t){
		getEntityManager().detach(t);
	}
	
	/**
	 * Convenient method to execute a 'native' SQL which - has parameters -
	 * returns multiple columns and rows and hence needs result set converted to
	 * a non-managed entity - needs parameter binding
	 * 
	 * By default parameters will be bound assuming order in query is same as
	 * order of the passed parameter array. Caller can provide a different
	 * {@link ParameterBinder} if this assumption is not correct.
	 * 
	 * In addition caller can also provide a return non-managed entity class to
	 * which the query return must be transformed to. This transformation is
	 * based on Hibernate's {@link Transformers}.aliasToBean()
	 * 
	 * Typically will be used to execute a query of type: select column1 alias1,
	 * column2 alias2, column3 alias3 from table1 where column4 = ? and column5
	 * = ? which returns multiple rows and each row needs to be transformed to a
	 * non-managed entity {Long alias1, String alias2, Integer alias3)
	 * 
	 * The call would look something like: <code>
	 * executeNativeSQL(sql, new Object[] {value1, value2}, ParameterBinder, NonManagedVo.class);
	 * </code>
	 * 
	 * Note: By default all attributes in the return type are required to be
	 * present in the SQL alias If this is not the case, an alias array can be
	 * provided which can contain a subset of the attributes in the return type.
	 * Only those attributes will be populated in the returned object.
	 * 
	 * @param nativeSQL
	 * @param paramNames
	 * @param paramValues
	 * @param parameterBinder
	 * @param returnType
	 * @return
	 */
	protected Object executeNativeSQL(final String nativeSQL,final String[] paramNames, final Object[] paramValues, final Class returnType) {

		Query query = entityManager.createNativeQuery(nativeSQL);
		ParameterBinder parameterBinder = new NameBasedParameterBinder();
		parameterBinder.bind(query, paramNames, paramValues);

		List<String> propertyNames = ReflectionUtils.getAnnotationPropertyNames(returnType);
		
		List<Object[]> listResults = query.getResultList();

		if (listResults != null && listResults.size() > 0) {
			int numofColumns = listResults.get(0).length;
			if (propertyNames.size() != numofColumns)
			{
				throw new IllegalArgumentException(
						"Return Class property names do not match query columns . Property names: "
								+ propertyNames.size() + " No. of Columns: "
								+ numofColumns);
			}
		}

		return createReturnType(listResults, returnType, propertyNames);
	}
	
	private Object createReturnType(List<Object[]> listResults, Class returnType, List<String> propertyNames) {
	
		List<Object> listObjectVO = new ArrayList<Object>();
		try {

			for (Object[] record : listResults) {
				Class clazz = Class.forName(returnType.getName());
				Object obj = clazz.newInstance();
				for (int columnNo = 0; columnNo < record.length; columnNo++) {
					ReflectionUtils.setFieldValueByReflection(obj,
							propertyNames.get(columnNo), record[columnNo]);
				}
				listObjectVO.add(obj);
			}

		} catch (Exception e) {
			throw new ERegRuntimeException(e);
		}
		return listObjectVO;
	}
		

	protected Object executeNativeSQL(final String nativeSQL,
			final String[] paramNames, final Object[] paramValues,
			final ParameterBinder parameterBinder) {
		Query query = entityManager.createNativeQuery(nativeSQL);

		if (parameterBinder != null)
			parameterBinder.bind(query, paramNames, paramValues);
		else
			new DefaultQueryParameterBinder().bind(query, null, paramValues);
		
		return query.getResultList();
	}
	
	
	protected List<Object[]>  executeNativeSQL(final String nativeSQL,
			final List<String> paramNames, final List<Object> paramValues,
			final ParameterBinder parameterBinder) {
		Query query = entityManager.createNativeQuery(nativeSQL);

		if (parameterBinder != null)
			parameterBinder.bind(query, paramNames, paramValues);
		else
			new DefaultQueryParameterBinder().bind(query, null, paramValues);
	
		return query.getResultList();
	}




}

