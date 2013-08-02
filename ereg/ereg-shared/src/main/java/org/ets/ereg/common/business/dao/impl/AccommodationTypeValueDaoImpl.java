package org.ets.ereg.common.business.dao.impl;


import org.ets.ereg.common.business.dao.AccommodationTypeValueDao;
import org.ets.ereg.domain.accommodation.AccommodationTypeValueImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.springframework.stereotype.Repository;

@Repository("accommodationTypeValueDao")
public class AccommodationTypeValueDaoImpl extends AbstractDaoImpl<AccommodationTypeValue> implements AccommodationTypeValueDao {

	@Override
	public Class<AccommodationTypeValueImpl> getEntityClass() {		
		return AccommodationTypeValueImpl.class;
	}

}
