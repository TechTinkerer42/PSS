package org.ets.ereg.profile.biq.dao;

import java.util.List;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.biq.CustomerDemographicResponse;

public interface CustomerDemographicResponseDao extends Dao<CustomerDemographicResponse>{
	
	List<CustomerDemographicResponse> getCustomerDemographicResponses(Long customerId,Long setId);
	void saveCustomerDemographicResponse(List<CustomerDemographicResponse> customerDemographicResponses);
	void deleteCustomerDemographicResponsesForCurrentSet(Long customerId,Long setId);
}
