/**
 * 
 */
package org.ets.pss.persistence.impl;

import javax.persistence.PersistenceContext;

import org.ets.pss.persistence.dao.CustCrDao;
import org.ets.pss.persistence.model.CustCr;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author SSINGH007
 *
 */
@Repository
@Qualifier("custCrDao")
public class CustCrDaoImpl extends GenericDaoImpl<CustCr> implements CustCrDao {

}
