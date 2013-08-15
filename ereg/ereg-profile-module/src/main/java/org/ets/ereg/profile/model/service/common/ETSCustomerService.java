package org.ets.ereg.profile.model.service.common;

import java.util.List;

import org.broadleafcommerce.profile.core.service.CustomerService;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.CustomerProgramInterest;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.vo.ProfileVO;

public interface ETSCustomerService extends CustomerService {
	
	public List<CustomerVO> searchCustomerByCriteria(ETSCustomer customer,ETSAddress address,ETSPhone phone, SearchParameters searchParams,String linkDispSeq,Long adminId,Long apptNumber);
	public Long getCountForCustomersSearch(ETSCustomer customer,ETSAddress address, ETSPhone phone, String linkDispSeq,Long adminId,Long apptNumber);
	public DupCheckResponseObject checkDuplicateProfile(ProfileVO profile);
	public CustomerProgramInterest addCustomerProgramInterest(ETSCustomer customer, ProgramType programType);
	public CustomerLinkage addCustomerLinkage(ETSCustomer customer, LinkageType linkageType, String linkageKey);
	public Boolean hasDuplicateProfiles(ProfileVO profile,boolean currentloggedCust);
	public List<CustomerVO> getDuplicateProfiles(ProfileVO profile,boolean currentloggedCust);
	public CustomerLinkage getCustomerLinkage(Long custid,String linkageType);
	public boolean checkForCustomerLinkageKey(String linkageKey);
	public String getGuidID(ETSCustomer customer, LinkageType linkageType);
	ETSCustomer findCustomerByUsernameAndInternalFlag(String username, Boolean internalUserFlag);
	public List<String> getCustomerProgramInterests( Long customerId );
	public ETSCustomer getCustomerByEmail(String email);
}
