package org.ets.ereg.scheduling.booking.dao.impl;

import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.booking.BookingAccommodationImpl;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.scheduling.booking.dao.BookingAccommodationDao;
import org.springframework.stereotype.Repository;

@Repository("bookingAccommodationDao")
public class BookingAccommodationDaoImpl extends AbstractDaoImpl<BookingAccommodation> implements BookingAccommodationDao{

    @Override
    public Class<BookingAccommodationImpl> getEntityClass() {
        return BookingAccommodationImpl.class;
    }

}
