package org.ets.ereg.domain.i18n;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.i18n.ContentType;

@Entity
@Table(name="CNTNT_TYP")
public class ContentTypeImpl implements ContentType, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CNTNT_TYP_CDE", nullable = false, length = 5)
	private String code;

	@Column(name = "CNTNT_TYP_DSC", nullable = false, length = 175)
	private String description;	

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
		return null;
	}

	@Override
	public void setDisplayable(Boolean isDisplayable) {
		// TODO Auto-generated method stub
	}

}
