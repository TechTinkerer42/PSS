package org.ets.ereg.domain.interfaces.model.common;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.id.CustomerProgramInterestId;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;

public interface CustomerProgramInterest extends Serializable {
	
	public static String CUSTOMER_PROGRAM_INTERESTS = "Customer.findCustomerProgramInterests";
	
	public ETSCustomer getCustomer(); 
	public void setCustomer(ETSCustomer customer); 
	public ProgramType getProgramType(); 
	public void setProgramType(ProgramType programType);
	public CustomerProgramInterestId getId();
	public void setId(CustomerProgramInterestId id);
	
}
