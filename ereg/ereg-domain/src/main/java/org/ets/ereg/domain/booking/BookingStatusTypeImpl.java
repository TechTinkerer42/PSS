package org.ets.ereg.domain.booking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity()
@Table(name = "BKNG_STS_TYP" )
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "etsStandardElements")
public class BookingStatusTypeImpl implements BookingStatusType, Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @Column(name = "BKNG_STS_TYP_CDE", nullable = false)
    private String code;
    
    @Column(name="BKNG_STS_TYP_DSC", nullable = false)
    private String description;
    
    @Column(name="BKNG_STS_TYP_DSPLY_SEQ")
    private Long BknStatusTypeDispSeq;
    
    @Column(name="DSPLY_DTA_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
    @Type(type="yes_no")
    private Boolean BknStatusDispDataFlag;
    
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDisplaySequence(Long BknStatusTypeDispSeq) {
		this.BknStatusTypeDispSeq = BknStatusTypeDispSeq;
	}

	@Override
	public Long getDisplaySequence() {
		return BknStatusTypeDispSeq;
	}

	@Override
	public void setDisplayable(Boolean BknStatusDispDataFlag) {
		this.BknStatusDispDataFlag = BknStatusDispDataFlag;
	}

	@Override
	public Boolean isDisplayable() {
		return BknStatusDispDataFlag;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(code).append(description)
				.toHashCode();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof BookingStatusTypeImpl) {
			final BookingStatusTypeImpl other = (BookingStatusTypeImpl) obj;
			return new EqualsBuilder().append(code, other.code)
					.append(description, other.description).isEquals();
		} else {
			return false;
		}
	}
}
