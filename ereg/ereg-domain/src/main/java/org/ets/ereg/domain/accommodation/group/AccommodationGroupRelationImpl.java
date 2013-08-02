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

import org.ets.ereg.domain.interfaces.model.accommodation.group.AccommodationGroup;
import org.ets.ereg.domain.interfaces.model.accommodation.group.AccommodationGroupRelation;
import org.ets.ereg.domain.interfaces.model.accommodation.group.id.AccommodationGroupRelationId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ACMDTN_GRP_REL")
public class AccommodationGroupRelationImpl implements Serializable,
AccommodationGroupRelation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "parentAccommodationGroupIdNumber", column = @Column(name = "ACMDTN_GRP_ID_PARNT", nullable = false)),
		@AttributeOverride(name = "childAccommodationGroupIdNumber", column = @Column(name = "ACMDTN_GRP_ID_CHLD", nullable = false)) })
	AccommodationGroupRelationId accommodationGroupRelation = new AccommodationGroupRelationId();

	@OneToOne(fetch = FetchType.LAZY, targetEntity = AccommodationGroupImpl.class)
	@JoinColumn(name = "ACMDTN_GRP_ID_PARNT", referencedColumnName = "ACMDTN_GRP_ID_NO", nullable = false, insertable = false, updatable = false)
	private AccommodationGroup parentAccommodationGroup;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = AccommodationGroupImpl.class)
	@JoinColumn(name = "ACMDTN_GRP_ID_CHLD", referencedColumnName = "ACMDTN_GRP_ID_NO", nullable = false, insertable = false, updatable = false)
	private AccommodationGroup childAccommodationGroup;

	@Column(name = "ACMDTN_GRP_REL_DSPLY_SEQ")
	private Integer accommodationGroupRelDispSequence;

	@Column(name = "DSPLY_DTA_FLG", nullable = false)
	@Type(type = "yes_no")
	private Boolean isDisplayable;

	@Override
	public AccommodationGroupRelationId getAccommodationGroupRelation() {
		return accommodationGroupRelation;
	}

	@Override
	public void setAccommodationGroupRelation(
			AccommodationGroupRelationId accommodationGroupRelation) {
		this.accommodationGroupRelation = accommodationGroupRelation;
	}

	@Override
	public AccommodationGroup getParentAccommodationGroup() {
		return parentAccommodationGroup;
	}

	@Override
	public void setParentAccommodationGroup(
			AccommodationGroup parentAccommodationGroup) {
		this.parentAccommodationGroup = parentAccommodationGroup;
	}

	@Override
	public AccommodationGroup getChildAccommodationGroup() {
		return childAccommodationGroup;
	}

	@Override
	public void setChildAccommodationGroup(
			AccommodationGroup childAccommodationGroup) {
		this.childAccommodationGroup = childAccommodationGroup;
	}

	@Override
	public Integer getAccommodationGroupRelDispSequence() {
		return accommodationGroupRelDispSequence;
	}

	@Override
	public void setAccommodationGroupRelDispSequence(
			Integer accommodationGroupRelDispSequence) {
		this.accommodationGroupRelDispSequence = accommodationGroupRelDispSequence;
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
