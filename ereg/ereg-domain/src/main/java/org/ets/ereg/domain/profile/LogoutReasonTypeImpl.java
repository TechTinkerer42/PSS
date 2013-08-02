package org.ets.ereg.domain.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.profile.LogoutReasonType;


@Entity
@Table(name="LOGOUT_RSN_TYP")
public class LogoutReasonTypeImpl implements Serializable,LogoutReasonType{	
	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LOGOUT_RSN_TYP_CDE", nullable = false, length = 5)
	private String code;

	@Column(name = "LOGOUT_RSN_TYP_DSC", nullable = false, length = 175)
	private String description;

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#getCode()
	 */
	@Override
	public String getCode() {
		return code;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	  /*
     * (non-Javadoc)
     * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#getDisplaySequence()
     */
    @Override
    public Long getDisplaySequence() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#setDisplaySequence(java.lang.String)
     */
    @Override
    public void setDisplaySequence(Long displaySequence) {
        
    }

    /*
     * (non-Javadoc)
     * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#isDisplayable(java.lang.String)
     *
     */
  /*  @Override
    public Boolean isDisplayable() {
        return isDisplayable;
    }
*/
    /*
     * (non-Javadoc)
     * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#setDisplayable(java.lang.String)
     */
   /* @Override
    public void setDisplayable(Boolean isDisplayable) {
        this.isDisplayable = isDisplayable;
    }*/



	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(code).append(description)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LogoutReasonTypeImpl) {
			final LogoutReasonTypeImpl other = (LogoutReasonTypeImpl) obj;
			return new EqualsBuilder().append(code, other.code)
					.append(description, other.description).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public Boolean isDisplayable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDisplayable(Boolean isDisplayable) {
		// TODO Auto-generated method stub
		
	}

	
	

}
