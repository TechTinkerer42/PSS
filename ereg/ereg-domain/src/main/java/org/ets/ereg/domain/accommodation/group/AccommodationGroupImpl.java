package org.ets.ereg.domain.accommodation.group;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ets.ereg.domain.interfaces.model.accommodation.group.AccommodationGroup;

@Entity
@Table(name="ACMDTN_GRP")
public class AccommodationGroupImpl implements Serializable, AccommodationGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "AccommodationGroupIdentifierNumber", strategy = GenerationType.TABLE)
	@TableGenerator(name = "AccommodationGroupIdentifierNumber", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "AccommodationGroupIdentifierNumberImpl", allocationSize = 50)
	@Column(name = "ACMDTN_GRP_ID_NO")
	private Long accommodationGroupIdentifierNumber;

	@Column(name = "DSPLY_TYP_CDE")
	private String displayTypeCode;

	@Column(name = "ACMDTN_GRP_SEQ", nullable = false)
	private Integer accommodationGroupSequenceNumber;

	@Column(name = "ACMDTN_GRP_DSC")
	private String accommodationGroupDescription;

	@Column(name = "ACMDTN_GRP_UI_TXT")
	private String accommodationGroupUIText;

	@Override
	public Long getAccommodationGroupIdentifierNumber() {
		return accommodationGroupIdentifierNumber;
	}

	@Override
	public void setAccommodationGroupIdentifierNumber(
			Long accommodationGroupIdentifierNumber) {
		this.accommodationGroupIdentifierNumber = accommodationGroupIdentifierNumber;
	}

	@Override
	public String getDisplayTypeCode() {
		return displayTypeCode;
	}

	@Override
	public void setDisplayTypeCode(String displayTypeCode) {
		this.displayTypeCode = displayTypeCode;
	}

	@Override
	public Integer getAccommodationGroupSequenceNumber() {
		return accommodationGroupSequenceNumber;
	}

	@Override
	public void setAccommodationGroupSequenceNumber(
			Integer accommodationGroupSequenceNumber) {
		this.accommodationGroupSequenceNumber = accommodationGroupSequenceNumber;
	}

	@Override
	public String getAccommodationGroupDescription() {
		return accommodationGroupDescription;
	}

	@Override
	public void setAccommodationGroupDescription(
			String accommodationGroupDescription) {
		this.accommodationGroupDescription = accommodationGroupDescription;
	}

	@Override
	public String getAccommodationGroupUIText() {
		return accommodationGroupUIText;
	}

	@Override
	public void setAccommodationGroupUIText(String accommodationGroupUIText) {
		this.accommodationGroupUIText = accommodationGroupUIText;
	}

}