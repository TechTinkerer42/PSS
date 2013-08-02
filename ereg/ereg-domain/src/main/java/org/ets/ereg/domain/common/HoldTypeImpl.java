package org.ets.ereg.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.ets.ereg.domain.interfaces.model.common.HoldType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "HLD_TYP")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "etsStandardElements")
public class HoldTypeImpl implements HoldType, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "HLD_TYP_CDE", nullable = false, length = 5)
	private String code;

	@Column(name = "HLD_TYP_DSC", nullable = false, length = 175)
	private String description;

    @Column(name = "HLD_TYP_DSPLY_SEQ")
    private Long displaySequence;

    @Column(name = "DSPLY_DTA_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
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
	


	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(code).append(description)
				.toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof HoldTypeImpl) {
			final HoldTypeImpl other = (HoldTypeImpl) obj;
			return new EqualsBuilder().append(code, other.code)
					.append(description, other.description).isEquals();
		} else {
			return false;
		}
	}


}
