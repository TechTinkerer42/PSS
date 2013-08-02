package org.ets.ereg.domain.interfaces.model.accommodation.id;

import java.io.Serializable;
import javax.persistence.Column;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomerAccommodationTestId implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUSTOMER_ID", nullable = false)
	private Long customerId;

	@Column(name = "PGM_CDE", nullable = false)
	private String programCode;

	@Column(name = "TST_ID", nullable = false)
	private Long testId;

	@Column(name = "ACMDTN_TYP_CDE", nullable = false)
	private String accommodationTypeCode;

	@Column(name="DLVY_MDE_CDE", nullable=false)
	private String deliveryModeCode;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getAccommodationTypeCode() {
		return accommodationTypeCode;
	}

	public void setAccommodationTypeCode(String accommodationTypeCode) {
		this.accommodationTypeCode = accommodationTypeCode;
	}


	@Override
	public int hashCode() {

		return new HashCodeBuilder().append(customerId).append(programCode)
				.append(testId)
				.append(accommodationTypeCode)
				.append(deliveryModeCode).hashCode();
	}

	@Override
	public boolean equals(final Object obj) {

		if (obj instanceof CustomerAccommodationTestId) {

			final CustomerAccommodationTestId other = (CustomerAccommodationTestId) obj;

			return new EqualsBuilder().append(customerId, other.customerId)
					.append(programCode, other.programCode)
					.append(testId, other.testId)
					.append(accommodationTypeCode, other.accommodationTypeCode)
					.append(deliveryModeCode, other.deliveryModeCode)
					.isEquals();

		} else {
			return false;
		}

	}

	public String getDeliveryModeCode() {
		return deliveryModeCode;
	}

	public void setDeliveryModeCode(String deliveryModeCode) {
		this.deliveryModeCode = deliveryModeCode;
	}

}
