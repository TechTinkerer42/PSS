package org.ets.ereg.scheduling.dao;

import java.util.List;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public interface AgencyDao extends Dao<Agency> {
	List<Agency> getAllActiveAgencies();
}
