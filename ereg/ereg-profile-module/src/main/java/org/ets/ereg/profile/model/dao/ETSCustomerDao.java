package org.ets.ereg.profile.model.dao;

import java.util.List;

import org.broadleafcommerce.profile.core.dao.CustomerDao;
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

public interface ETSCustomerDao extends CustomerDao {
	final String FIND_BY_USERNAMEANDFLAG = "Customer.findCustomerByUsernameAndIntFlg"; 
	public List<CustomerVO> searchCustomerByCriteria(ETSCustomer customer,ETSAddress address, ETSPhone phone, SearchParameters searchParams,String linkDispSeq,Long adminId, Long apptNumber);
	public Long getCountForCustomersSearch(ETSCustomer customer,ETSAddress address, ETSPhone phone, String linkDispSeq,Long adminId,Long apptNumber);
	public DupCheckResponseObject checkDuplicateProfile(ProfileVO profile);
	public CustomerProgramInterest getCustomerProgramInterest(ETSCustomer customer, ProgramType programType);
	public CustomerProgramInterest addCustomerProgramInterest(ETSCustomer customer, ProgramType programType);
	public CustomerLinkage getCustomerLinkage(Long custid,String linkageType);	
	public CustomerLinkage addCustomerLinkage(ETSCustomer customer, LinkageType linkageType, String linkageKey);
	public Boolean hasDuplicateProfiles(ProfileVO profile,boolean currentloggedCust);
	public List<CustomerVO> getDuplicateProfiles(ProfileVO profile,boolean currentloggedCust); 
	public boolean checkForCustomerLinkageKey(String linkageKey);
	public String getGuidID(ETSCustomer customer, LinkageType linkageType);
	ETSCustomer findCustomerByUsernameAndInternalFlag(String username, Boolean internalUserFlag);
	public List<String> getCustomerProgramInterests( Long customerId );
	public ETSCustomer getCustomerByEmail(String email);
}
