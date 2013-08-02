package org.ets.ereg.domain.booking;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.accommodation.AccommodationTypeImpl;
import org.ets.ereg.domain.accommodation.AccommodationTypeValueImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.booking.id.BookingAccommodationId;

@Entity
@Table(name = "BKNG_ACMDTN")
public class BookingAccommodationImpl implements BookingAccommodation,
        Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "bookingId", column = @Column(name = "BKNG_ID", nullable = false)),
            @AttributeOverride(name = "accommodationTypeCode", column = @Column(name = "ACMDTN_TYP_CDE", nullable = false)) })
    private BookingAccommodationId bookingAccommodationId = new BookingAccommodationId();

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = BookingImpl.class)
    @JoinColumn(name = "BKNG_ID", updatable = false, insertable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = AccommodationTypeImpl.class)
    @JoinColumn(name = "ACMDTN_TYP_CDE", updatable = false, insertable = false)
    private AccommodationType accommodationType;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = AccommodationTypeValueImpl.class)
    @JoinColumn(name = "ACMDTN_TYP_VAL_ID_NO", referencedColumnName = "ACMDTN_TYP_VAL_ID_NO")
    private AccommodationTypeValue accommodationTypeValue;


    @Column(name = "APRVD_DTE")
    private Date approvedDate;

    @Column(name = "EXPRTN_DTE")
    private Date expirationDate;

    @Column(name = "OTHR_ACMDTN_TYP_TXT")
    private String othrAcmdtnTypTxt;

    @Override
    public BookingAccommodationId getBookingAccommodationId() {
        return bookingAccommodationId;
    }

    @Override
    public void setBookingAccommodationId(
            BookingAccommodationId bookingAccommodationId) {
        this.bookingAccommodationId = bookingAccommodationId;
    }

    @Override
    public Booking getBooking() {
        return booking;
    }

    @Override
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    @Override
    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
    }

    @Override
    public AccommodationTypeValue getAccommodationTypeValue() {
        return accommodationTypeValue;
    }

    @Override
    public void setAccommodationTypeValue(
            AccommodationTypeValue accommodationTypeValue) {
        this.accommodationTypeValue = accommodationTypeValue;
    }

    @Override
    public Date getApprovedDate() {
        return approvedDate;
    }

    @Override
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String getOthrAcmdtnTypTxt() {
        return othrAcmdtnTypTxt;
    }

    @Override
    public void setOthrAcmdtnTypTxt(String othrAcmdtnTypTxt) {
        this.othrAcmdtnTypTxt = othrAcmdtnTypTxt;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bookingAccommodationId)
                .append(booking).append(accommodationType)
                .append(accommodationTypeValue).append(approvedDate)
                .append(expirationDate).append(othrAcmdtnTypTxt).toHashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BookingAccommodationImpl) {
            final BookingAccommodationImpl other = (BookingAccommodationImpl) obj;
            return new EqualsBuilder()
                    .append(bookingAccommodationId,
                            other.bookingAccommodationId)
                    .append(booking, other.booking)
                    .append(accommodationType, other.accommodationType)
                    .append(accommodationTypeValue,
                            other.accommodationTypeValue)
                    .append(approvedDate, other.approvedDate)
                    .append(expirationDate, other.expirationDate)
                    .append(othrAcmdtnTypTxt, other.othrAcmdtnTypTxt)
                    .isEquals();
        } else {
            return false;
        }
    }

}
