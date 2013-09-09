/**
 * 
 */
package org.ets.pss.persistence.dao;

import java.util.List;

/**
 * @author asampath
 *
 */
public interface GenericDao<E> {
	
    void create(final E entity);

    void update(final E entity);

    E get(final Class<E> type, final Object id);
    
    void delete(final E entity);
    
    public List findAll(Class clz);

}
