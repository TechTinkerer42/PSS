package org.ets.ereg.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.common.EthnicityType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

	@Entity
	@Table(name = "ETNCTY")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "etsStandardElements")
	public class EthnicityTypeImpl implements Serializable, EthnicityType {

		private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "ETNCTY_CDE", nullable = false, length = 5)
		private String code;

		@Column(name = "ETNCTY_DSC", nullable = false, length = 100)
		private String description;

		@Column(name = "ETNCTY_DSPLY_SEQ")
        private Long displaySequence;

		@Column(name = "DSPLY_DTA_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
		@Type(type="yes_no")
        private Boolean isDisplayable;



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
	        return displaySequence;
	    }

	    /*
	     * (non-Javadoc)
	     * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#setDisplaySequence(java.lang.String)
	     */
	    @Override
	    public void setDisplaySequence(Long displaySequence) {
	        this.displaySequence = displaySequence;
	    }

	    /*
	     * (non-Javadoc)
	     * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#isDisplayable()
	     */
	    @Override
	    public Boolean isDisplayable() {
            return isDisplayable;
        }

	    /*
	     * (non-Javadoc)
	     * @see org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface#setDisplayable(java.lang.Boolean)
	     */
	    @Override
        public void setDisplayable(Boolean isDisplayable) {
            this.isDisplayable = isDisplayable;
        }



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
			if (obj instanceof EthnicityTypeImpl) {
				final EthnicityTypeImpl other = (EthnicityTypeImpl) obj;
				return new EqualsBuilder().append(code, other.code)
						.append(description, other.description).isEquals();
			} else {
				return false;
			}
		}

	}
