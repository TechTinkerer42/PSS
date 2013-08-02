package org.ets.ereg.domain.interfaces.model.accommodation.group.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class AccommodationTypeGroupId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ACMDTN_GRP_ID_NO", nullable = false)
	private Long accommodationGroupIdentifierNumber;

	@Column(name = "ACMDTN_TYP_CDE", nullable = false)
	private String accommodationTypeCode;

	public Long getAccommodationGroupIdentifierNumber() {
		return accommodationGroupIdentifierNumber;
	}

	public void setAccommodationGroupIdentifierNumber(
			Long accommodationGroupIdentifierNumber) {
		this.accommodationGroupIdentifierNumber = accommodationGroupIdentifierNumber;
	}

	public String getAccommodationTypeCode() {
		return accommodationTypeCode;
	}

	public void setAccommodationTypeCode(String accommodationTypeCode) {
		this.accommodationTypeCode = accommodationTypeCode;
	}

	@Override
	public int hashCode() {

		return new HashCodeBuilder().append(accommodationGroupIdentifierNumber)
				.append(accommodationTypeCode).hashCode();
	}

	@Override
	public boolean equals(final Object obj) {

		if (obj instanceof AccommodationTypeGroupId) {

			final AccommodationTypeGroupId other = (AccommodationTypeGroupId) obj;

			return new EqualsBuilder()
			.append(accommodationGroupIdentifierNumber,
					other.accommodationGroupIdentifierNumber)
					.append(accommodationTypeCode, other.accommodationTypeCode)
					.isEquals();

		} else {
			return false;
		}

	}

}