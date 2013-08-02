package org.ets.ereg.domain.biq;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.biq.CustomerDemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.DemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.id.CustomerDemographicResponseId;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.profile.ETSCustomerImpl;

@Entity
@Table(name="CUST_DMGRPH_RSP")
public class CustomerDemographicResponseImpl implements CustomerDemographicResponse, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "customerId", column = @Column(name = "CUSTOMER_ID", nullable = false, length=5)),
			@AttributeOverride(name = "demographicRespNo", column = @Column(name = "DMGRPH_RSP_NO", nullable = false, length=9)),		
			@AttributeOverride(name = "demographicQstnNo", column = @Column(name = "DMGRPH_QSTN_NO", nullable = false, length=9))
	})	
	private CustomerDemographicResponseId customerDemographicRespId = new CustomerDemographicResponseId();
	
	@Column(name="FR_FRM_TXT_RSP",length=225)
	private String freeFormTxtResp;
			
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DemographicResponseImpl.class)
	@JoinColumns({@JoinColumn(name = "DMGRPH_QSTN_NO", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "DMGRPH_RSP_NO", nullable = false, insertable = false, updatable = false)})
	private DemographicResponse demograpicRespNo;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=ETSCustomerImpl.class)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false, insertable = false, updatable = false)
	private ETSCustomer etsCustomerId;
	 
	public ETSCustomer getEtsCustomerId() {
		return etsCustomerId;
	}

	public void setEtsCustomerId(ETSCustomer etsCustomerId) {
		this.etsCustomerId = etsCustomerId;
	}
	
	public DemographicResponse getDemograpicRespNo() {
		return demograpicRespNo;
	}

	public void setDemograpicRespNo(DemographicResponse demograpicRespNo) {
		this.demograpicRespNo = demograpicRespNo;
	}
	

	@Override
	public String getFreeFormTxtResp() {		
		return freeFormTxtResp;
	}

	@Override
	public void setFreeFormTxtResp(String freeFormTxtResp) {
		this.freeFormTxtResp =freeFormTxtResp;

	}

	@Override
	public CustomerDemographicResponseId getCustomerDemographicRespId() {
		return customerDemographicRespId;
	}
    
	@Override
	public void setCustomerDemographicRespId(
			CustomerDemographicResponseId customerDemographicRespId) {
		this.customerDemographicRespId = customerDemographicRespId;
	}

}
