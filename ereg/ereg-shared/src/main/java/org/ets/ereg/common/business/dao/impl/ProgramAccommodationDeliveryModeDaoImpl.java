package org.ets.ereg.common.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.common.business.dao.ProgramAccommodationDeliveryModeDao;
import org.ets.ereg.domain.accommodation.ProgramAccommodationDeliveryModeImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.accommodation.id.ProgramAccommodationDeliveryModeId;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.springframework.stereotype.Repository;

@Repository("programAccommodationDeliveryModeDao")
public class ProgramAccommodationDeliveryModeDaoImpl extends AbstractDaoImpl<ProgramAccommodationDeliveryMode>
											implements ProgramAccommodationDeliveryModeDao{

	@Override
	public Class<ProgramAccommodationDeliveryModeImpl> getEntityClass() {
		return ProgramAccommodationDeliveryModeImpl.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryModeType> getAccommodationDeliveryMethods(
			String programCode) {
		Query query	 = entityManager.createNamedQuery("Accommodations.getAccommodationDeliveryMethods");	
		query.setParameter("programCode", programCode);
		return (List<DeliveryModeType>) query.getResultList();
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List<AccommodationType> getAllAccommodations(
			String programCode, String deliveryModeCode) {
		
		Query query	 = entityManager.createNamedQuery("Accommodations.getAllAccommodations");	
		query.setParameter("programCode", programCode);
		query.setParameter("deliveryModeCode", deliveryModeCode);
		return (List<AccommodationType>) query.getResultList();
	}
	
	
	@Override
	public ProgramAccommodationDeliveryMode getProgramAccommodationDeliveryMode(String programCode, String deliveryModeCode, String accommodationTypeCode){
		ProgramAccommodationDeliveryModeId id = new ProgramAccommodationDeliveryModeId();
		id.setAccommodationTypeCode(accommodationTypeCode);
		id.setDeliveryModeCode(deliveryModeCode);
		id.setProgramCode(programCode);
		
		return findByPrimaryKey(id);
	}
	
}
