package org.ets.ereg.domain.scheduling;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.scheduling.SchedulingType;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "SCHDLG_TYP")
public class SchedulingTypeImpl implements SchedulingType {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SCHDLG_TYP_CDE", nullable = false, length = 5)
	private String code;
	
	@Column(name = "SCHDLG_TYP_DSC", nullable = false, length = 175)
	private String description;
	
	@Column(name = "SCHDLG_TYP_DSPLY_SEQ", nullable = false)
	private Long displaySequence;
	
	@Column(name = "DSPLY_DTA_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
	@Type(type = "yes_no")
	private Boolean isDisplayable;

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

}
