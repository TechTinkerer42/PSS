/**
 * 
 */
package org.ets.pss.persistence.dao;

import java.util.List;

import org.ets.pss.persistence.model.AsgndTsk;

/**
 * @author SSINGH007
 *
 */
public interface AsgndTskDao extends GenericDao<AsgndTsk> {
	public List<AsgndTsk> getCurrentTasks();
}
