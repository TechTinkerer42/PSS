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

import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "LNKG_TYP")
public class LinkageTypeImpl implements LinkageType, Serializable {
	@Id
	@Column(name = "LNKG_TYP_CDE", nullable = false, length = 15)
	private String code;
	
	@Column(name = "LNKG_TYP_DSC", length = 175)
	private String description;
	
	@Column(name = "LNKG_TYP_DSPLY_SEQ")
	private long linkageTypeDisplaySeq;
	
    @Column(name = "DSPLY_DTA_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
    @Type(type="yes_no")
    private Boolean isDisplayable;
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code=code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description=description;
	}

	@Override
	public Long getDisplaySequence() {
		return linkageTypeDisplaySeq;
	}

	@Override
	public void setDisplaySequence(Long displaySequence) {
		linkageTypeDisplaySeq=displaySequence;
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
		if (obj instanceof LinkageTypeImpl) {
			final LinkageTypeImpl other = (LinkageTypeImpl) obj;
			return new EqualsBuilder().append(code, other.code)
					.append(description, other.description).isEquals();
		} else {
			return false;
		}
	}

}
