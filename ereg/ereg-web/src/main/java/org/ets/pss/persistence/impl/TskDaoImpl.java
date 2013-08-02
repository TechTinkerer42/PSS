/**
 * 
 */
package org.ets.pss.persistence.impl;

import org.ets.pss.persistence.dao.TskDao;
import org.ets.pss.persistence.model.Task;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author SSINGH007
 *
 */
@Repository
@Qualifier("taskDao")
public class TskDaoImpl extends GenericDaoImpl<Task> implements TskDao {

}
