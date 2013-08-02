package org.ets.ereg.scheduling.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.scheduling.AgencyImpl;
import org.ets.ereg.scheduling.dao.AgencyDao;
import org.springframework.stereotype.Repository;

@Repository("agencyDao")
public class AgencyDaoImpl extends AbstractDaoImpl<Agency> implements AgencyDao {
	@PersistenceContext(unitName="blPU")
    private EntityManager em;
	
	private static final String ACTIVE_FLAG = "isActive";
	
	@Override
	public Class<AgencyImpl> getEntityClass() {
		// TODO Auto-generated method stub
		return AgencyImpl.class;
	}

	@Override
	public List<Agency> getAllActiveAgencies() {
		TypedQuery<Agency> query = em.createNamedQuery("Agency.findAllActive", Agency.class);
		query.setParameter(AgencyDaoImpl.ACTIVE_FLAG, true);
		try{
			return query.getResultList();
		} catch(NoResultException nre) {
			return null;
		}
	}

}
