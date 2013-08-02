/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.ets.pss.persistence.dao.CustomerPromptDAO;
import org.ets.pss.persistence.model.CustomerPrompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author ASAMPATH
 *
 */
@Repository
@Qualifier("customerPromptDAO")
public class CustomerPromptDAOImpl extends GenericDaoImpl<CustomerPrompt> implements  CustomerPromptDAO {

	public Map<Long, String> getCustomerPromptsForTask(Long customerId, Long taskId) {
		Query q = em().createNamedQuery("CustomerPrompt.getPromptsByTask");
		
		q.setParameter(1, taskId);
		q.setParameter(2, customerId);
		
		List<CustomerPrompt> cps = q.getResultList();
		
		Map<Long, String> ret = new HashMap<Long, String>();
		
				for( CustomerPrompt cp : cps){
					ret.put(cp.getPromptId(), cp.getPromptResponse());
				}
				
				
		return ret;
	}

}
