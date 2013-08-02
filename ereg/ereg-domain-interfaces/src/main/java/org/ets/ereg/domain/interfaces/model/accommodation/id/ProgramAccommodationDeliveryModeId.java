package org.ets.ereg.domain.interfaces.model.accommodation.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ProgramAccommodationDeliveryModeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "PGM_CDE", nullable = false)
	private String programCode;

	@Column(name = "ACMDTN_TYP_CDE", nullable = false)
	private String accommodationTypeCode;

	@Column(name = "DLVY_MDE_CDE", nullable = false)
	private String deliveryModeCode;

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getAccommodationTypeCode() {
		return accommodationTypeCode;
	}

	public void setAccommodationTypeCode(String accommodationTypeCode) {
		this.accommodationTypeCode = accommodationTypeCode;
	}

	public String getDeliveryModeCode() {
		return deliveryModeCode;
	}

	public void setDeliveryModeCode(String deliveryModeCode) {
		this.deliveryModeCode = deliveryModeCode;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(programCode)
				.append(deliveryModeCode).append(accommodationTypeCode)
				.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof ProgramAccommodationDeliveryModeId) {
			final ProgramAccommodationDeliveryModeId other = (ProgramAccommodationDeliveryModeId) obj;
			return new EqualsBuilder().append(programCode, other.programCode)
					.append(deliveryModeCode, other.deliveryModeCode)
					.append(accommodationTypeCode, other.accommodationTypeCode)
					.isEquals();
		}
		return false;
	}

}
