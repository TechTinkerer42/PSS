package org.ets.ereg.domain.model.program;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRuleType;
import org.hibernate.annotations.Type;

@Entity
@Table(name="PGM_CLMN_RUL_TYP")
public class ProgramColumnRuleTypeImpl implements ProgramColumnRuleType,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PGM_CLMN_RUL_TYP_CDE", nullable = false, length = 5)
	private String code;

	@Column(name = "PGM_CLMN_RUL_TYP_DSC", nullable = false, length = 175)
	private String description;
	
	@Column(name = "DSPLY_DTA_FLG", length=1)
	@Type(type="yes_no")
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDisplaySequence(Long displaySequence) {
		// TODO Auto-generated method stub

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
