package org.ets.ereg.profile.service.impl;

import javax.annotation.Resource;


import org.broadleafcommerce.profile.core.service.IdGenerationService;
import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;
import org.ets.ereg.profile.dao.CustomerLogonHistoryDao;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.profile.service.CustomerLogonHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service("customerLogonHistoryService")
public class CustomerLogonHistoryServiceImpl implements
		CustomerLogonHistoryService {
	private static Logger LOG = LoggerFactory.getLogger(CustomerLogonHistoryServiceImpl.class);

	@Resource(name = "etsCustomerLogonHistoryDao")
	protected CustomerLogonHistoryDao customerLogonHistoryDao;
	
	@Resource(name="blIdGenerationService")
    protected IdGenerationService idGenerationService;
	
	@Resource(name="etsCustomerService")
	ETSCustomerService etsCustomerService;

	@Override
	public CustomerLogonHistory create() {		
		return createCustomerLogonHistory(null,null);
	}
	
	public CustomerLogonHistory save(CustomerLogonHistory custLogonHistory) {
		LOG.debug("saving {}",custLogonHistory.getId());
		return customerLogonHistoryDao.save(custLogonHistory);
	}

	public CustomerLogonHistory getCustomerLogonHistory(String username,
			String sessionId) {
		return customerLogonHistoryDao.getCustomerLogonHistory(username,
				sessionId);
	}
	
	public CustomerLogonHistory getCustomerLogonHistory(
			String sessionId) {
		return customerLogonHistoryDao.getCustomerLogonHistory(
				sessionId);
	}

	/**
	 * Allows to call from subclassed service.
	 * 
	 * @return
	 */
	public Long findNextCustomerHistoryLogonId() {
		LOG.debug("getting id");
		return idGenerationService
				.findNextId("org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory");
	}

	@Override
	public CustomerLogonHistory createCustomerLogonHistory(String username,String sessionId) {
		CustomerLogonHistory customerLogonHistory=null;
		LOG.debug("saving customerLogonHistory() username:{}",username);
		LOG.debug("saving customerLogonHistory() sessionId:{}",sessionId);
		
		if  (username!=null && sessionId != null ){
			LOG.debug("retrieving existing");
			customerLogonHistory = getCustomerLogonHistory(username,sessionId);
			
		}
		
		if(username==null && sessionId == null)
		{
			LOG.debug("creating new obj");
			customerLogonHistory = customerLogonHistoryDao.create();
			customerLogonHistory.setId(findNextCustomerHistoryLogonId());	
		}		
		
		return customerLogonHistory;
	}

	
}
