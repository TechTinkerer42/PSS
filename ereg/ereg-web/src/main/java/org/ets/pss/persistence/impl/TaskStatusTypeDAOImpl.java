/**
 * 
 */
package org.ets.pss.persistence.impl;

import org.ets.pss.persistence.dao.TaskStatusTypeDAO;
import org.ets.pss.persistence.model.TaskStatusType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author asampath
 *
 */
@Repository
@Qualifier("taskStatusTypeDAO")
public class TaskStatusTypeDAOImpl extends GenericDaoImpl<TaskStatusType> implements TaskStatusTypeDAO {

}
