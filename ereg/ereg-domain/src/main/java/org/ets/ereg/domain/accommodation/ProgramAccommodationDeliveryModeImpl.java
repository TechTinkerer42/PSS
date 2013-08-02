package org.ets.ereg.domain.accommodation;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.accommodation.id.ProgramAccommodationDeliveryModeId;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;

@Entity
@Table(name = "PGM_ACMDTN_DLVY_MDE")
public class ProgramAccommodationDeliveryModeImpl implements Serializable,
ProgramAccommodationDeliveryMode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "programCode", column = @Column(name = "PGM_CDE", nullable = false)),
		@AttributeOverride(name = "deliveryModeCode", column = @Column(name = "DLVY_MDE_CDE", nullable = false)),
		@AttributeOverride(name = "accommodationTypeCode", column = @Column(name = "ACMDTN_TYP_CDE", nullable = false,insertable = false, updatable = false)) })
	private ProgramAccommodationDeliveryModeId programAccommodationDeliveryModeId = new ProgramAccommodationDeliveryModeId();

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ProgramTypeImpl.class)
	@JoinColumn(name = "PGM_CDE", nullable = false, insertable = false, updatable = false)
	private ProgramType programCode;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = DeliveryModeTypeImpl.class)
	@JoinColumn(name = "DLVY_MDE_CDE", nullable = false, insertable = false, updatable = false)
	private DeliveryModeType deliveryModeType;


	@JoinColumn(name="ACMDTN_TYP_CDE",nullable = false, insertable = false, updatable = false)
	@OneToOne(fetch = FetchType.EAGER, targetEntity = AccommodationTypeImpl.class)	
	private AccommodationType accommodationType;
	
	

	@Override
	public ProgramAccommodationDeliveryModeId getProgramAccommodationDeliveryModeId() {
		return programAccommodationDeliveryModeId;
	}

	@Override
	public void setProgramAccommodationDeliveryModeId(
			ProgramAccommodationDeliveryModeId programAccommodationDeliveryModeId) {
		this.programAccommodationDeliveryModeId = programAccommodationDeliveryModeId;
	}

	@Override
	public ProgramType getProgramCode() {
		return programCode;
	}

	@Override
	public void setProgramCode(ProgramType programCode) {
		this.programCode = programCode;
	}

	@Override
	public DeliveryModeType getDeliveryModeType() {
		return deliveryModeType;
	}

	@Override
	public void setDeliveryModeType(DeliveryModeType deliveryModeType) {
		this.deliveryModeType = deliveryModeType;
	}

	@Override
	public AccommodationType getAccommodationType() {
		return accommodationType;
	}

	@Override
	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}

}
