package org.ets.ereg.domain.interfaces.model.booking.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class BookingAccommodationId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8163621937763521168L;

    @Column(name = "BKNG_ID", nullable = false)
	private long bookingId;

	@Column(name = "ACMDTN_TYP_CDE", nullable = false)
	private String accommodationTypeCode;


	public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }



    public String getAccommodationTypeCode() {
        return accommodationTypeCode;
    }

    public void setAccommodationTypeCode(String accommodationTypeCode) {
        this.accommodationTypeCode = accommodationTypeCode;
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(bookingId).append(accommodationTypeCode)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof BookingAccommodationId) {
			final BookingAccommodationId other = (BookingAccommodationId) obj;
			return new EqualsBuilder().append(bookingId, other.bookingId)
					.append(accommodationTypeCode, other.accommodationTypeCode).isEquals();
		} else {
			return false;
		}
	}
}
