package org.ets.ereg.session.facade.profile.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.business.vo.CustomerResultsVO;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.session.facade.profile.service.ETSCustomerBusinessService;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.springframework.stereotype.Service;

@Service("etsCustomerBusinessService")
public class ETSCustomerBusinessServiceImpl  implements ETSCustomerBusinessService{


	@Resource(name="etsCustomerService")
	ETSCustomerService etsCustomerService;

    @Resource(name="generateBase64StringBusinessService")
    GenerateBase64StringBusinessService generateBase64StringBusinessService;

	@Override
	public CustomerResultsVO searchCustomerByCriteria(ETSCustomer customer,
			ETSAddress address, ETSPhone phone,SearchParameters searchParams,String linkDispSeq,Long adminId, String username,Long apptNumber) throws ERegCheckedException {
		int pageSize=searchParams.getPageSize();
		int rowNum=searchParams.getNumofRows();
		CustomerResultsVO custResultVO= new CustomerResultsVO();
		List<CustomerVO> customers = null;
		String encodeCustomerId = null;
		try{

		customers=etsCustomerService.searchCustomerByCriteria(customer, address, phone, searchParams,linkDispSeq,adminId,apptNumber);
		for (CustomerVO customerVO : customers) {
		    encodeCustomerId = generateBase64StringBusinessService.encodeBase64String(username+"|"+customerVO.getCandidateId());
		    customerVO.setCandidateIdStr(encodeCustomerId);
        }
		if(customers.size()==pageSize || rowNum!=0){
			long count=etsCustomerService.getCountForCustomersSearch(customer, address, phone,linkDispSeq,adminId,apptNumber);
			custResultVO.setCount(count);
		}else if(rowNum==0 && customers.size()<pageSize){
			custResultVO.setCount((long)customers.size());
		}
		custResultVO.setCustomers(customers);
		}catch(Exception ex){

			 throw new ERegCheckedException(ex);

		}
		return custResultVO;
	}

	@Override
	public Customer readCustomerById(Long userId) {
		// TODO Auto-generated method stub
		return etsCustomerService.readCustomerById(userId);
	}

}
