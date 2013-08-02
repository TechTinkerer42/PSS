package org.ets.ereg.scheduling.service;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public interface AgencyService {
	
	List<Agency> getAllAgencies(boolean includeActiveOnly);
	
	Agency getAgencyById(Long agencyId);

}
