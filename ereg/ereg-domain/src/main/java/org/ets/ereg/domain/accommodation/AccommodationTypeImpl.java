package org.ets.ereg.domain.accommodation;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.accommodation.group.AccommodationTypeGroupImpl;
import org.ets.ereg.domain.booking.BookingAccommodationImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.accommodation.group.AccommodationTypeGroup;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.hibernate.annotations.Type;

@Entity
@Table(name="ACMDTN_TYP")
public class AccommodationTypeImpl implements AccommodationType {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ACMDTN_TYP_CDE", nullable = false)
	private String code;

	@Column(name = "ACMDTN_TYP_DSC", nullable = false)
	private String description;

	@Column(name="ACMDTN_TYP_DSPLY_SEQ")
	private Long displaySequence;

	@Column(name="DSPLY_DTA_FLG")
	@Type(type="yes_no")
    private Boolean isDisplayable;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodationType", targetEntity=AccommodationTypeValueImpl.class)
    private List<AccommodationTypeValue> accommodationTypeValues;

	@OneToOne(mappedBy="accommodationType",fetch = FetchType.LAZY, targetEntity=AccommodationTypeGroupImpl.class)
    private AccommodationTypeGroup accommodationTypeGroup;

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Long getDisplaySequence() {
		return displaySequence;
	}

	@Override
	public void setDisplaySequence(Long displaySequence) {
		this.displaySequence = displaySequence;
	}

	@Override
	public Boolean isDisplayable() {
			return isDisplayable;
	}

	@Override
	public void setDisplayable(Boolean isDisplayable) {
		this.isDisplayable = isDisplayable;
	}

	@Override
	public List<AccommodationTypeValue> getAccommodationTypeValues() {
		return accommodationTypeValues;
	}

	@Override
	public void setAccommodationTypeValues(
			List<AccommodationTypeValue> accommodationTypeValues) {
		this.accommodationTypeValues = accommodationTypeValues;
	}

	@Override
	public AccommodationTypeGroup getAccommodationTypeGroup() {
		return accommodationTypeGroup;
	}

	@Override
	public void setAccommodationTypeGroup(
			AccommodationTypeGroup accommodationTypeGroup) {
		this.accommodationTypeGroup = accommodationTypeGroup;
	}

}
