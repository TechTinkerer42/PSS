package org.ets.ereg.domain.rule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.rule.RuleSetType;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "RUL_SET_TYP")
public class RuleSetTypeImpl implements RuleSetType {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RUL_SET_TYP_CDE", nullable = false, length = 5)
	private String code;
	@Column(name = "RUL_SET_DSC", nullable = false, length = 175)
	private String description;

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Long getDisplaySequence() {
		return null;
	}

	@Override
	public void setDisplaySequence(Long displaySequence) {

	}

	@Override
	public Boolean isDisplayable() {
		return Boolean.TRUE;
	}

	@Override
	public void setDisplayable(Boolean isDisplayable) {

	}
}
