package org.ets.ereg.domain.interfaces.model.common.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;



@Embeddable
public class CustomerLinkId implements Serializable {
	@Column(name = "CUSTOMER_ID", nullable = false)
	private long customerId;
	@Column(name = "LNKG_TYP_CDE", nullable = false)
	private String linkTypeCode;
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getLinkTypeCode() {
		return linkTypeCode;
	}
	public void setLinkTypeCode(String linkTypeCode) {
		this.linkTypeCode = linkTypeCode;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(customerId).append(linkTypeCode)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof CustomerLinkId) {
			final CustomerLinkId other = (CustomerLinkId) obj;
			return new EqualsBuilder().append(linkTypeCode, other.linkTypeCode)
					.append(customerId, other.customerId).isEquals();
		} else {
			return false;
		}
	}

	
	
	
	

}
