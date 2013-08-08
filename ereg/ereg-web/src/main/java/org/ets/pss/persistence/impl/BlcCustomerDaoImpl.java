/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.pss.persistence.dao.BlcCustomerDao;
import org.ets.pss.persistence.model.BlcCustomer;
import org.ets.pss.persistence.model.UserTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author asampath
 *
 */
@Repository
@Qualifier("blcCustomerDao")
public class BlcCustomerDaoImpl  extends GenericDaoImpl<BlcCustomer> implements BlcCustomerDao  {

	public BlcCustomer getCustomer(long customerId)
	{
		Query q = em.createQuery("SELECT x FROM BlcCustomer x where x.userId=?1");
		q.setParameter(1, customerId);
		BlcCustomer blcCustomer = (BlcCustomer) q.getResultList();
		return blcCustomer;
	}
}
