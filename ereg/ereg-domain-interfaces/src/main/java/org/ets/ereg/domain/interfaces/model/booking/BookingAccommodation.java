package org.ets.ereg.domain.interfaces.model.booking;

import java.util.Date;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.booking.id.BookingAccommodationId;

public interface BookingAccommodation {

    void setBookingAccommodationId(BookingAccommodationId bookingAccommodationId);
    
    BookingAccommodationId getBookingAccommodationId();

    Booking getBooking();

    void setBooking(Booking booking);

    AccommodationType getAccommodationType();

    void setAccommodationType(AccommodationType accommodationType);

    AccommodationTypeValue getAccommodationTypeValue();

    void setAccommodationTypeValue(AccommodationTypeValue accommodationTypeValue);

    Date getApprovedDate();

    void setApprovedDate(Date approvedDate);

    Date getExpirationDate();

    void setExpirationDate(Date expirationDate);

    String getOthrAcmdtnTypTxt();

    void setOthrAcmdtnTypTxt(String othrAcmdtnTypTxt);

}
