package org.ets.ereg.domain.interfaces.model.accommodation.group.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class AccommodationGroupRelationId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ACMDTN_GRP_ID_PARNT", nullable = false)
	private Integer parentAccommodationGroupIdNumber;

	@Column(name = "ACMDTN_GRP_ID_CHLD", nullable = false)
	private Integer childAccommodationGroupIdNumber;

	public Integer getParentAccommodationGroupIdNumber() {
		return parentAccommodationGroupIdNumber;
	}

	public void setParentAccommodationGroupIdNumber(
			Integer parentAccommodationGroupIdNumber) {
		this.parentAccommodationGroupIdNumber = parentAccommodationGroupIdNumber;
	}

	public Integer getChildAccommodationGroupIdNumber() {
		return childAccommodationGroupIdNumber;
	}

	public void setChildAccommodationGroupIdNumber(
			Integer childAccommodationGroupIdNumber) {
		this.childAccommodationGroupIdNumber = childAccommodationGroupIdNumber;
	}

	@Override
	public int hashCode() {

		return new HashCodeBuilder().append(parentAccommodationGroupIdNumber)
				.append(childAccommodationGroupIdNumber).hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof AccommodationGroupRelationId) {
			final AccommodationGroupRelationId other = (AccommodationGroupRelationId) obj;
			return new EqualsBuilder()
			.append(parentAccommodationGroupIdNumber,
					other.parentAccommodationGroupIdNumber)
					.append(childAccommodationGroupIdNumber,
							other.childAccommodationGroupIdNumber).isEquals();
		} else {
			return false;
		}
	}

}
