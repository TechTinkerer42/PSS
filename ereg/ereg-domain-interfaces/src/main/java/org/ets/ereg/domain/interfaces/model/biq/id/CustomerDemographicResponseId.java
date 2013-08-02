package org.ets.ereg.domain.interfaces.model.biq.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomerDemographicResponseId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="CUSTOMER_ID",nullable=false)
	private Long customerId;
	
	@Column(name="DMGRPH_QSTN_NO",nullable=false)
	private Long demographicQstnNo;
	
	@Column(name="DMGRPH_RSP_NO",nullable=false)
	private Long demographicRespNo;
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getDemographicQstnNo() {
		return demographicQstnNo;
	}

	public void setDemographicQstnNo(Long demographicQstnNo) {
		this.demographicQstnNo = demographicQstnNo;
	}

	public Long getDemographicRespNo() {
		return demographicRespNo;
	}

	public void setDemographicRespNo(Long demographicRespNo) {
		this.demographicRespNo = demographicRespNo;
	}
	
	@Override
	public int hashCode(){
		
		return new HashCodeBuilder().append(demographicRespNo)
				.append(demographicQstnNo)
				.append(customerId).hashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		
		if(obj instanceof CustomerDemographicResponseId){
			
			final CustomerDemographicResponseId other = (CustomerDemographicResponseId) obj;
			
			return new EqualsBuilder().append(customerId, other.customerId)
					.append(demographicRespNo, other.demographicRespNo)
					.append(demographicQstnNo, other.demographicQstnNo).isEquals();
			
		}else{
			return false;
		}
		
	}
	
	
	

}
