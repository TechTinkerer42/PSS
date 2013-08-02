package org.ets.ereg.domain.profile;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ets.ereg.domain.common.LinkageTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.common.id.CustomerLinkId;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
@Entity
@Table(name = "ETS_CUST_LNKG")
public class CustomerLinkageImpl implements CustomerLinkage,Serializable {
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "linkTypeCode", column = @Column(name = "LNKG_TYP_CDE", nullable = false, length = 15)),
			@AttributeOverride(name = "customerId", column = @Column(name = "CUSTOMER_ID", nullable = false)) })
	private CustomerLinkId etsCustomerLinkId = new CustomerLinkId();
	@Column(name="LNKG_KY", nullable=false)
	private String linkageKey;
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=ETSCustomerImpl.class)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false, insertable = false, updatable = false)
	private ETSCustomer customer; 
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=LinkageTypeImpl.class)
	@JoinColumn(name = "LNKG_TYP_CDE", nullable = false, insertable = false, updatable = false)
	private LinkageType linkageType;

	@Override
	public String getLinkageKey() {
		return linkageKey;
	}
	
	@Override
	public void setLinkageKey(String linkageKey) {
		this.linkageKey=linkageKey;

	}
	public ETSCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ETSCustomer customer) {
		this.customer = customer;
	}

	@Override
	public CustomerLinkId getId() {
		return etsCustomerLinkId;
	}

	@Override
	public CustomerLinkId setId(CustomerLinkId id) {
		return this.etsCustomerLinkId=id;
	}
	@Override
	public LinkageType getLinkageType() {
		return linkageType;
	}
	@Override
	public void setLinkageType(LinkageType linkageType) {
		this.linkageType = linkageType;
	}
}
