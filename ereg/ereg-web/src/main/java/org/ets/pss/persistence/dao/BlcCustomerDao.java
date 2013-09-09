/**
 * 
 */
package org.ets.pss.persistence.dao;

import org.ets.pss.persistence.model.BlcCustomer;

/**
 * @author asampath
 *
 */
public interface BlcCustomerDao extends GenericDao<BlcCustomer> {

	BlcCustomer getCustomer(long customerId);
}
