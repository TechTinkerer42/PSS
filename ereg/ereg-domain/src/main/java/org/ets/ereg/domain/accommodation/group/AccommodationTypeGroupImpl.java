package org.ets.ereg.domain.accommodation.group;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.accommodation.AccommodationTypeImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.group.AccommodationGroup;
import org.ets.ereg.domain.interfaces.model.accommodation.group.AccommodationTypeGroup;
import org.ets.ereg.domain.interfaces.model.accommodation.group.id.AccommodationTypeGroupId;
import org.hibernate.annotations.Type;

@Entity
@Table(name="ACMDTN_TYP_GRP")
public class AccommodationTypeGroupImpl implements Serializable,
AccommodationTypeGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "accommodationGroupIdentifierNumber", column = @Column(name = "ACMDTN_GRP_ID_NO", nullable = false)),
		@AttributeOverride(name = "accommodationTypeCode", column = @Column(name = "ACMDTN_TYP_CDE", nullable = false)) })
	private AccommodationTypeGroupId accommodationTypeGroupId = new AccommodationTypeGroupId();

	@OneToOne(fetch = FetchType.EAGER, targetEntity = AccommodationGroupImpl.class)
	@JoinColumn(name = "ACMDTN_GRP_ID_NO", referencedColumnName = "ACMDTN_GRP_ID_NO", nullable = false, insertable = false, updatable = false)
	private AccommodationGroup accommodationGroup;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = AccommodationTypeImpl.class)
	@JoinColumn(name = "ACMDTN_TYP_CDE", referencedColumnName = "ACMDTN_TYP_CDE", nullable = false, insertable = false, updatable = false)
	private AccommodationType accommodationType;

	@Column(name = "ACMDTN_TYP_GRP_DSC")
	private String accommodationTypeGroupDescription;

	@Column(name = "ACMDTN_TYP_GRP_DSPLY_SEQ")
	private Integer accommodationTypeGroupDisplaySequence;

	@Column(name = "DSPLY_DTA_FLG", nullable = false)
	@Type(type = "yes_no")
	private Boolean isDisplayable;

	@Override
	public AccommodationTypeGroupId getAccommodationTypeGroupId() {
		return accommodationTypeGroupId;
	}

	@Override
	public void setAccommodationTypeGroupId(
			AccommodationTypeGroupId accommodationTypeGroupId) {
		this.accommodationTypeGroupId = accommodationTypeGroupId;
	}

	@Override
	public AccommodationGroup getAccommodationGroup() {
		return accommodationGroup;
	}

	@Override
	public void setAccommodationGroup(AccommodationGroup accommodationGroup) {
		this.accommodationGroup = accommodationGroup;
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
	public String getAccommodationTypeGroupDescription() {
		return accommodationTypeGroupDescription;
	}

	@Override
	public void setAccommodationTypeGroupDescription(
			String accommodationTypeGroupDescription) {
		this.accommodationTypeGroupDescription = accommodationTypeGroupDescription;
	}

	@Override
	public Integer getAccommodationTypeGroupDisplaySequence() {
		return accommodationTypeGroupDisplaySequence;
	}

	@Override
	public void setAccommodationTypeGroupDisplaySequence(
			Integer accommodationTypeGroupDisplaySequence) {
		this.accommodationTypeGroupDisplaySequence = accommodationTypeGroupDisplaySequence;
	}

	@Override
	public Boolean getIsDisplayable() {
		return isDisplayable;
	}

	@Override
	public void setIsDisplayable(Boolean isDisplayable) {
		this.isDisplayable = isDisplayable;
	}

}
