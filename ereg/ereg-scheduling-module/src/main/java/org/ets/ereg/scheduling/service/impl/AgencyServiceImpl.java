package org.ets.ereg.scheduling.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.scheduling.dao.AgencyDao;
import org.ets.ereg.scheduling.service.AgencyService;
import org.springframework.stereotype.Service;

@Service("agencyService")
public class AgencyServiceImpl implements AgencyService {
	
	@Resource(name = "agencyDao")
	private AgencyDao agencyDao;

	@Override
	public List<Agency> getAllAgencies(boolean includeActiveOnly) {
		if(!includeActiveOnly) {
			return agencyDao.getAll(null);
		} else {
			return agencyDao.getAllActiveAgencies();
		}
	}
	
	public Agency getAgencyById(Long agencyId) {
		return agencyDao.findByPrimaryKey(agencyId);
	}

}
