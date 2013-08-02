package org.ets.ereg.scheduling.booking.dao.impl;

import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.booking.HeldBookingImpl;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.scheduling.booking.dao.HeldBookingDao;
import org.springframework.stereotype.Repository;

@Repository("heldBookingDao")
public class HeldBookingDaoImpl extends AbstractDaoImpl<HeldBooking> implements
		HeldBookingDao {

	@Override
	public Class<HeldBookingImpl> getEntityClass() {
		return HeldBookingImpl.class;
	}

}
