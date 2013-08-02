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
import javax.persistence.criteria.Root;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria.ReferenceTypeOrderBy;

@SuppressWarnings("unchecked")
public abstract class AbstractSearchDaoImpl<T> implements Dao<T> {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager entityManager;

	@Resource(name = "blEntityConfiguration")
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
	 * 
	 * @see org.ets.ereg.common.business.dao.Dao#findByPrimaryKey(java.lang.Object)
	 */
	@Override
	public T findByPrimaryKey(Class<T> clazz, Object primaryKey) {
		return getEntityManager().find(getImplClass(clazz), primaryKey);
	}

	public T findByPrimaryKey(Object primaryKey) {
		return getEntityManager().find(getEntityClass(), primaryKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ereg.common.business.dao.Dao#getAll()
	 */
	@SuppressWarnings("hiding")
	@Override
	public <T> List<T> getAll(Class<T> clazz,
			ReferenceTypeCriteria referenceCriteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
		Root<T> rootClazz = criteriaQuery.from(getImplClass(clazz));
		criteriaQuery.select(rootClazz);
		if (referenceCriteria != null) {
			List<Order> orderList = new ArrayList<Order>();
			if (referenceCriteria.getOrderbyList() != null) {
				for (ReferenceTypeOrderBy orderBy : referenceCriteria
						.getOrderbyList()) {
					orderList
							.add(orderBy.isAscending() ? builder.asc(rootClazz
									.get(orderBy.getOrderByAttribute()))
									: builder.desc(rootClazz.get(orderBy
											.getOrderByAttribute())));
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
		CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) builder
				.createQuery(getEntityClass());
		Root<T> rootClazz = (Root<T>) criteriaQuery.from(getEntityClass());
		criteriaQuery.select(rootClazz);
		if (referenceCriteria != null) {
			List<Order> orderList = new ArrayList<Order>();
			if (referenceCriteria.getOrderbyList() != null) {
				for (ReferenceTypeOrderBy orderBy : referenceCriteria
						.getOrderbyList()) {
					orderList
							.add(orderBy.isAscending() ? builder.asc(rootClazz
									.get(orderBy.getOrderByAttribute()))
									: builder.desc(rootClazz.get(orderBy
											.getOrderByAttribute())));
				}
			}
			criteriaQuery.orderBy(orderList);
		}

		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	private <T> Class<T> getImplClass(Class<T> clazz) {
		Object obj = entityConfiguration.createEntityInstance(clazz.getName());
		return (Class<T>) obj.getClass();
	}

	@Override
	public T save(T t) {
		return entityManager.merge(t);
	}

	public void delete(T t) {
		entityManager.remove(t);
	}

	@Override
	public void refresh(T t) {
		entityManager.refresh(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T create() {
		return (T) entityConfiguration.createEntityInstance(getEntityClass()
				.getName());
	}

	@Override
	public void detach(T t) {
		getEntityManager().detach(t);
	}

}
