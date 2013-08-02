/**
 * 
 */
package org.ets.pss.persistence.dao;

import java.util.List;

import org.ets.pss.persistence.model.UserTask;

/**
 * @author SSINGH007
 *
 */
public interface UserTaskDao {

	void addUserTask(UserTask ut);
	void removeUserTask(UserTask ut);
	List<UserTask> getUserTasks(String userId);
	void saveUserTask(UserTask ut);
}
