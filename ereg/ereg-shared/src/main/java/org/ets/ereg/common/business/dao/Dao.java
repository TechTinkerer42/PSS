package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;

public interface Dao<T> {

	/**
	 * Method to get domain object using primary key
	 *
	 * @param primaryKey
	 * @return
	 */
    public T findByPrimaryKey(Class<T> clazz, Object primaryKey);
    
    public T findByPrimaryKey(Object primaryKey);

    /**
     * Method to get List of domain objects
     *
     * @return
     */
    public <T> List<T> getAll(Class<T> clazz, ReferenceTypeCriteria criteria);
    
    public <T> List<T> getAll(ReferenceTypeCriteria criteria);
    
	public T save(T t);
	
	public T create();
	
	public void delete(T t);
	
	public void detach(T t);
	
	public void refresh(T t);
}
