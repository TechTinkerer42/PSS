package org.ets.ereg.profile.biq.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.biq.CustomerDemographicResponse;
import org.ets.ereg.profile.biq.dao.CustomerDemographicResponseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("customerDemographicResponseDao")
public class CustomerDemographicResponseDaoImpl extends AbstractDaoImpl<CustomerDemographicResponse> implements CustomerDemographicResponseDao {

	private static Logger logger = LoggerFactory.getLogger(CustomerDemographicResponseDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDemographicResponse> getCustomerDemographicResponses(
			Long customerId,Long setId) {
		
		Query query = entityManager.createNamedQuery("DemographicQuestions.getCustomerDemographicResponses");		
		query.setParameter("customerId", customerId);		
		query.setParameter("setId", setId);
		return (List<CustomerDemographicResponse>) query.getResultList();
	}
	
	@Override
	public Class<CustomerDemographicResponse> getEntityClass() {
		
		return CustomerDemographicResponse.class;
	}
	
	public void deleteCustomerDemographicResponsesForCurrentSet(Long customerId,Long setId){	
		List<CustomerDemographicResponse> customerDmgrphResps = getCustomerDemographicResponses(customerId, setId);
		logger.debug("No of customer demogrphic responses to delete are {}",customerDmgrphResps.size());		
		if(customerDmgrphResps.size()>0){			
			for(CustomerDemographicResponse customerDmgrphResp: customerDmgrphResps){
				delete(customerDmgrphResp);
			}
		}		
	}

	@Override
	public void saveCustomerDemographicResponse(
			List<CustomerDemographicResponse> customerDemographicResponses) {		
		
		for(CustomerDemographicResponse customerDemographicResponse: customerDemographicResponses){
			save(customerDemographicResponse);			
		}		
	}

}
