package org.ets.ereg.domain.interfaces.model.scheduling.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TestCenterDeliveryModeId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TST_CNTR_ID_NO", nullable = false)
	private Long testCenterId;
	
	@Column(name = "DLVY_MDE_CDE", nullable = false, length=5)
	private String deliveryModeCode;
	
	public Long getTestCenterId() {
		return testCenterId;
	}
	
	public void setTestCenterIdId(Long testCenterId) {
		this.testCenterId = testCenterId;
	}
	
	public String getDeliveryModeCode() {
		return deliveryModeCode;
	}
	
	public void setDeliveryModeCode(String deliveryModeCode) {
		this.deliveryModeCode = deliveryModeCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(testCenterId).append(deliveryModeCode)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TestCenterDeliveryModeId) {
			final TestCenterDeliveryModeId other = (TestCenterDeliveryModeId) obj;
			return new EqualsBuilder().append(deliveryModeCode, other.deliveryModeCode)
					.append(testCenterId, other.testCenterId).isEquals();
		} else {
			return false;
		}
	}
}
