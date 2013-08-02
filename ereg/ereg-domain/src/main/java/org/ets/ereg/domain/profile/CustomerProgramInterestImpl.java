package org.ets.ereg.domain.profile;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.CustomerProgramInterest;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.common.id.CustomerProgramInterestId;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;

@Entity
@Table(name = "CUST_PGM_INTRST")
public class CustomerProgramInterestImpl implements CustomerProgramInterest {
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "programCode", column = @Column(name = "PGM_CDE", nullable = false, length = 5)),
			@AttributeOverride(name = "customerId", column = @Column(name = "CUSTOMER_ID", nullable = false)) })
	private CustomerProgramInterestId custProgramInterestId = new CustomerProgramInterestId();
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=ETSCustomerImpl.class)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false, insertable = false, updatable = false)
	private ETSCustomer customer;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=ProgramTypeImpl.class)
	@JoinColumn(name = "PGM_CDE", nullable = false, insertable = false, updatable = false)
	private ProgramType programType; 

	@Override
	public ETSCustomer getCustomer() {
		return customer;
	}

	@Override
	public void setCustomer(ETSCustomer customer) {
		this.customer=customer;
	}

	@Override
	public ProgramType getProgramType() {
		return programType;
	}

	@Override
	public void setProgramType(ProgramType programType) {
		this.programType=programType;

	}

	@Override
	public CustomerProgramInterestId getId() {
		return custProgramInterestId;
	}
	@Override
	public void setId(CustomerProgramInterestId custProgramInterestId) {
		this.custProgramInterestId=custProgramInterestId;
		
	}

}
