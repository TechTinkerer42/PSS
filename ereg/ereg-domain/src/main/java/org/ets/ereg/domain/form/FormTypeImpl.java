package org.ets.ereg.domain.form;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.form.FormType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity()
@Table(name = "FRM_TYP" )
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "etsStandardElements")
public class FormTypeImpl implements FormType, Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FRM_TYP_CDE", nullable = false)
    private String formTypeCode;
    
    @Column(name="FRM_TYP_DSC", nullable = false)
    private String formTypeDesc;    
    
    @Column(name="FRM_TYP_DSPLY_SEQ")
    private Long formTypeDispSeq;
    
    @Column(name="DSPLY_DTA_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
    @Type(type="yes_no")
    private Boolean formDispDataFlag;

	@Override
	public void setCode(String formTypeCode) {
		this.formTypeCode = formTypeCode;
	}

	@Override
	public String getCode() {
		return formTypeCode;
	}

	@Override
	public void setDescription(String formTypeDesc) {
		this.formTypeDesc = formTypeDesc;
	}

	@Override
	public String getDescription() {
		return formTypeDesc;
	}

	@Override
	public void setDisplaySequence(Long formTypeDispSeq) {
		this.formTypeDispSeq = formTypeDispSeq;
	}

	@Override
	public Long getDisplaySequence() {
		return formTypeDispSeq;
	}

	@Override
	public void setDisplayable(Boolean formDispDataFlag) {
		this.formDispDataFlag = formDispDataFlag; 
	}

	@Override
	public Boolean isDisplayable() {
		return formDispDataFlag;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(formTypeCode).append(formTypeDesc)
				.toHashCode();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof FormTypeImpl) {
			final FormTypeImpl other = (FormTypeImpl) obj;
			return new EqualsBuilder().append(formTypeCode, other.formTypeCode)
					.append(formTypeDesc, other.formTypeDesc).isEquals();
		} else {
			return false;
		}
	}
}
