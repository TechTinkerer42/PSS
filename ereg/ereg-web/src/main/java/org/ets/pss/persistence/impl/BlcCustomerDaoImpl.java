/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.List;

import org.ets.pss.persistence.dao.BlcCustomerDao;
import org.ets.pss.persistence.model.BlcCustomer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author VSHANMUGAM
 *
 */
@Repository
@Qualifier("blcCustomerDao")
public class BlcCustomerDaoImpl  extends GenericDaoImpl<BlcCustomer> implements BlcCustomerDao  {


}
