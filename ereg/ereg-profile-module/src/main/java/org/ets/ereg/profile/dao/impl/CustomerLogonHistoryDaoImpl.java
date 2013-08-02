package org.ets.ereg.profile.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;
import org.ets.ereg.domain.profile.CustomerLogonHistoryImpl;
import org.ets.ereg.profile.dao.CustomerLogonHistoryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("etsCustomerLogonHistoryDao")
public class CustomerLogonHistoryDaoImpl implements CustomerLogonHistoryDao{
	
	private static Logger logger = LoggerFactory.getLogger(CustomerLogonHistoryDaoImpl.class);
	
	@Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

	@PersistenceContext(unitName="blPU")
    protected EntityManager em;	
	
	public CustomerLogonHistory create() {
		CustomerLogonHistory  customerLogonHistory = (CustomerLogonHistory)entityConfiguration.createEntityInstance(CustomerLogonHistory.class.getName());
		return customerLogonHistory;
	}

	
	public CustomerLogonHistory save(CustomerLogonHistory custLogonHistory) {
		return em.merge(custLogonHistory);
		
	}
	

	
	public CustomerLogonHistory getCustomerLogonHistory(String username,
			String sessionId) {
		TypedQuery<CustomerLogonHistory> query = em.createNamedQuery("CUSTOMER_LOGON_HISTORY_BY_USERNAME_SESSIONID", CustomerLogonHistory.class);
		
        query.setParameter("username", username);
        query.setParameter("sessionId", sessionId);
        logger.debug("Query: {}",query.toString());
        
        List<CustomerLogonHistory> customerLogonHistory= query.getResultList(); 
        logger.debug("Result size: {}",customerLogonHistory.size());
        return customerLogonHistory.size() == 0 || customerLogonHistory.isEmpty() ? null : customerLogonHistory.get(0);
	}


	@Override
	public CustomerLogonHistory readCustomerLogonHistoryById(Long id) {
		
		
		return em.find(CustomerLogonHistoryImpl.class, id);
	}


	@Override
	public CustomerLogonHistory getCustomerLogonHistory(String sessionId) {
		TypedQuery<CustomerLogonHistory> query = em.createNamedQuery("CUSTOMER_LOGON_HISTORY_BY_SESSIONID", CustomerLogonHistory.class);
		
     
        query.setParameter("sessionId", sessionId);     
        
        List<CustomerLogonHistory> customerLogonHistory= query.getResultList(); 
        logger.debug("Result size: {}",customerLogonHistory.size());
        return customerLogonHistory.size() == 0 || customerLogonHistory.isEmpty() ? null : customerLogonHistory.get(0);
	}

}
