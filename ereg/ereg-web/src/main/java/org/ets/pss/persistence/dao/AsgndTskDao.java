/**
 * 
 */
package org.ets.pss.persistence.dao;

import java.util.List;

import org.ets.ereg.security.user.ERegUser;
import org.ets.pss.persistence.model.AsgndTsk;

/**
 * @author asampath
 *
 */
public interface AsgndTskDao extends GenericDao<AsgndTsk> {
	public List<AsgndTsk> getCurrentTasks();
}
