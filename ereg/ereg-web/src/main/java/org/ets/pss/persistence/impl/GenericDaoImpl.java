/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ets.pss.persistence.dao.GenericDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sarabdeep Singh
 *
 */
public class GenericDaoImpl<E> implements GenericDao<E> {

	@PersistenceContext(unitName="pss")
    protected EntityManager em;

    public EntityManager em() {
        return em;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(final E entity) {
//    	em.getTransaction().begin();
    	
        em.persist(entity);
        
        em.flush();
//        em.getTransaction().commit();
    }

    public void update(final E entity) {
        em.merge(entity);
    }


    public E get(final Class<E> type, final Object id) {
        return em.find(type, id);
    }

    public void delete(final E entity) {
        em.remove(entity);
    }

	public List findAll(Class clz) {
		return em.createQuery("select c from " + clz.getSimpleName() + " c ").getResultList();
		
	}	
}