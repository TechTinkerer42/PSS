package org.ets.pss.persistence.dao;


import java.util.List;

import org.ets.pss.persistence.model.CrBlb;

/**
 * @author asampath
 *
 */
public interface CustBlbDao extends GenericDao<CrBlb> {
		
		List<String> getCurrentVideoEntriesForPrompt(long promptId,long taskId,long custId);
		
}

