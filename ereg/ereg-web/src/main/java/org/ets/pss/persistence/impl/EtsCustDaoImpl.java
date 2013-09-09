/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ets.pss.persistence.dao.EtsCustDao;
import org.ets.pss.persistence.model.EtsCust;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author asampath
 *
 */
@Repository
@Qualifier("customerDao")
public class EtsCustDaoImpl extends GenericDaoImpl<EtsCust> implements EtsCustDao {

	public EtsCust getCustomer(long customerId)
	{
		Query q = em().createQuery("SELECT x FROM EtsCust x where x.customerId = ?1");		
		q.setParameter(1, customerId);
		@SuppressWarnings("unchecked")
		List<EtsCust> customers = (List<EtsCust>) q.getResultList();
		
		if(customers != null && customers.size()>0)
		{
			return customers.get(0);
		}
		else
		{
			return null;
		}				
	}

}
