package org.ets.ereg.domain.booking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.booking.EventOutComeStatus;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "EVNT_OTCM_STS_TYP")
public class EventOutComeStatusImpl implements EventOutComeStatus {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EVNT_OTCM_STS_TYP_CDE", nullable=false, length=4)
	private String code;
	
	@Column(name = "EVNT_OTCM_STS_TYP_DSC", nullable=false)
	private String description;
	
	@Column(name = "EVNT_OTCM_STS_TYP_DSPLY_SEQ")
	private Long evntStatusDispSeq;
	
	@Column(name = "DSPLY_DTA_FLG", nullable=false)
	@Type(type = "yes_no")
	private Boolean dispDataFlag;
	
	@Column(name = "FRM_USED_FLG", nullable=false)
	@Type(type = "yes_no")
	private Boolean formUsedFlag;

	@Override
	public void setFormUsedFlag(Boolean formUsedFlag) {
		this.formUsedFlag = formUsedFlag;
	}

	@Override
	public Boolean getFormUsedFlag() {
		return formUsedFlag;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public void setCode(String code) {
		// TODO Auto-generated method stub
		this.code=code;
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		this.description=description;
		
	}

	@Override
	public Long getDisplaySequence() {
		// TODO Auto-generated method stub
		return evntStatusDispSeq;
	}

	@Override
	public void setDisplaySequence(Long displaySequence) {
		// TODO Auto-generated method stub
		evntStatusDispSeq=displaySequence;
		
	}

	@Override
	public Boolean isDisplayable() {
		// TODO Auto-generated method stub
		return dispDataFlag;
	}

	@Override
	public void setDisplayable(Boolean isDisplayable) {
		// TODO Auto-generated method stub
		dispDataFlag=isDisplayable;
		
	}

}
