package org.ets.ereg.scheduling.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.scheduling.dao.TestCenterDao;
import org.ets.ereg.scheduling.service.TestCenterService;
import org.ets.ereg.scheduling.vo.SearchResult;
import org.ets.ereg.scheduling.vo.TestCenterSearchCriteria;
import org.ets.ereg.scheduling.vo.TestCenterSearchResultEntry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("testCenterService")
public class TestCenterServiceImpl implements TestCenterService, InitializingBean  {
	
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	
	@Resource(name = "testCenterDao")
	protected TestCenterDao testCenterDao;
	
	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;
	
	private int maxResultSize;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		String val = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfigurationService.MAX_TESTCENTER_SEARCH_RESULTS);
		if(null == val || val.trim().length() == 0){
			maxResultSize = 1000;
		}
		else{
			maxResultSize = Integer.parseInt(val);
		}
	}
	
	public List<TestCenter> getAssociatedTestCentersForAdmin(Long adminId){
		
		return testCenterDao.getAssociatedTestCentersForAdmin(adminId);
	}

	@Override
	public SearchResult<TestCenterSearchResultEntry> findTestCenters(
			TestCenterSearchCriteria searchCriteria, int offset, int count) {
		SearchResult<TestCenterSearchResultEntry> searchResult = new SearchResult<TestCenterSearchResultEntry>();
		
		List<TestCenterSearchResultEntry> searchedElements = testCenterDao.getTestCenterSearchResult(searchCriteria, offset, count);
		
		if(searchedElements.size() == count || offset > 0){
			Long resultSize = testCenterDao.getTestCenterSearchCount(searchCriteria);
			searchResult.setCount(resultSize);
		}
		else{
			searchResult.setCount((long)searchedElements.size());
		}
		
		searchResult.setHasMoreResults(offset+count < searchResult.getCount());
		
		if(searchResult.getCount() > maxResultSize){
			List<String> warnings = new ArrayList<String>();
			warnings.add("Exceded the Max Records please refine your search criteria");
			searchResult.setWarnings(warnings);
		}
		searchResult.setSearchedElements(searchedElements);
		return searchResult;
	}
	
	@Override
	public SearchResult<TestCenterSearchResultEntry> findTestCenters(
			TestCenterSearchCriteria searchCriteria) {
		return findTestCenters(searchCriteria, 0, maxResultSize);
	}

	@Override
	public TestCenter readTestCenterById(Long id) {
		return testCenterDao.readTestCenterById(id);
	}
	
	@Override
	public String getAddress(ETSAddress address) {
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(address.getAddressLine1());
        if (address.getAddressLine2() != null && !address.getAddressLine2().isEmpty()) {
			addressBuilder.append(SPACE + address.getAddressLine2());
            if (address.getAddressLine2() != null && !address.getAddressLine2().isEmpty()) {
				addressBuilder.append(SPACE + address.getAddressLine3());
            }
        }
		addressBuilder.append(COMMA + SPACE + address.getCity() + COMMA + SPACE);
		addressBuilder.append(address.getState().getAbbreviation().toUpperCase() + SPACE);
        addressBuilder.append(address.getPostalCode());
        return addressBuilder.toString();
	}

	@Override
	public List<TestCenter> getTestCentersByAgency(Long agencyId) {
		return testCenterDao.getTestCentersByAgency(agencyId);
	}
}
