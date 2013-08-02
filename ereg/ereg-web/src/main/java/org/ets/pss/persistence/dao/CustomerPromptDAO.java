/**
 * 
 */
package org.ets.pss.persistence.dao;

import java.util.Map;

import org.ets.pss.persistence.model.CustomerPrompt;

/**
 * @author ASAMPATH
 *
 */
public interface CustomerPromptDAO extends GenericDao<CustomerPrompt> {
	
	public Map<Long, String> getCustomerPromptsForTask(Long customerId, Long taskId);
	
}
