package org.ets.ereg.domain.accommodation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ACMDTN_TYP_VAL")
public class AccommodationTypeValueImpl implements Serializable,
AccommodationTypeValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "AccommodationTypeValueIdentifierNumber", strategy = GenerationType.TABLE)
	@TableGenerator(name = "AccommodationTypeValueIdentifierNumber", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "AccommodationTypeValueIdentifierNumberImpl", allocationSize = 50)
	@Column(name = "ACMDTN_TYP_VAL_ID_NO")
	private Long accommodationTypeValueIdentifierNumber;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AccommodationTypeImpl.class)
	@JoinColumn(name = "ACMDTN_TYP_CDE", referencedColumnName = "ACMDTN_TYP_CDE", nullable = false, insertable = false, updatable = false)
	private AccommodationType accommodationType;

	@Column(name = "LBL", nullable = false)
	private String label;

	@Column(name = "VAL", nullable = false)
	private String value;

	@Column(name = "SCHDLG_SVC_ACMDTN_VAL")
	private String schedulingServiceAccommodationValue;

	@Column(name = "ACMDTN_TYP_VAL_UI_TXT")
	private String accommodationTypeValueUIText;

	@Column(name = "ACMDTN_TYP_VAL_DSPLY_SEQ")
	private Integer accommodationTypeValueDisplaySequence;

	@Column(name = "DSPLY_DTA_FLG", nullable = false)
	@Type(type = "yes_no")
	private Boolean isDisplayable;

	@Override
	public Long getAccommodationTypeValueIdentifier() {
		return accommodationTypeValueIdentifierNumber;
	}

	public void setAccommodationTypeValueIdentifier(
			Long accommodationTypeValueIdentifierNumber) {
		this.accommodationTypeValueIdentifierNumber = accommodationTypeValueIdentifierNumber;
	}

	public AccommodationType getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSchedulingServiceAccommodationValue() {
		return schedulingServiceAccommodationValue;
	}

	public void setSchedulingServiceAccommodationValue(
			String schedulingServiceAccommodationValue) {
		this.schedulingServiceAccommodationValue = schedulingServiceAccommodationValue;
	}

	public String getAccommodationTypeValueUIText() {
		return accommodationTypeValueUIText;
	}

	public void setAccommodationTypeValueUIText(
			String accommodationTypeValueUIText) {
		this.accommodationTypeValueUIText = accommodationTypeValueUIText;
	}

	public Integer getAccommodationTypeValueDisplaySequence() {
		return accommodationTypeValueDisplaySequence;
	}

	public void setAccommodationTypeValueDisplaySequence(
			Integer accommodationTypeValueDisplaySequence) {
		this.accommodationTypeValueDisplaySequence = accommodationTypeValueDisplaySequence;
	}

	public Boolean getIsDisplayable() {
		return isDisplayable;
	}

	public void setIsDisplayable(Boolean isDisplayable) {
		this.isDisplayable = isDisplayable;
	}

}
