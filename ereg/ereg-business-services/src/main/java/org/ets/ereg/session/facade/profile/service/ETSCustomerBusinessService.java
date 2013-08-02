package org.ets.ereg.session.facade.profile.service;



import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.business.vo.CustomerResultsVO;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;

public interface ETSCustomerBusinessService {
	public CustomerResultsVO searchCustomerByCriteria(ETSCustomer customer,ETSAddress address, ETSPhone phone,
			SearchParameters searchParams,String linkDispSeq,Long adminId, String username,Long apptNumber) throws ERegCheckedException;
	public Customer readCustomerById(Long userId);




}
