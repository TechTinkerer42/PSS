package org.ets.ereg.domain.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.domain.interfaces.model.biq.CustomerDemographicResponse;
import org.ets.ereg.domain.interfaces.model.biq.id.CustomerDemographicResponseId;
import org.ets.ereg.profile.biq.dao.CustomerDemographicResponseDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestCustomerDemographicResponseDao {

	private static Logger logger = LoggerFactory.getLogger(TestCustomerDemographicResponseDao.class);
	
	@Resource(name="customerDemographicResponseDao")
	private CustomerDemographicResponseDao customerDemographicResponseDao;
	
	
	@Test
	public void testGetCustomerDemographicResponses(){
		
		List<CustomerDemographicResponse> customerDemographicResponses= 
					customerDemographicResponseDao.getCustomerDemographicResponses(1L,1L);
		Assert.assertNotNull(customerDemographicResponses);
		for(CustomerDemographicResponse custDmgrphResp : customerDemographicResponses ){			
			logger.debug("customer id {}",custDmgrphResp.getCustomerDemographicRespId().getCustomerId());			
			logger.debug("customer demographic question no {} ",custDmgrphResp.getCustomerDemographicRespId().getDemographicQstnNo());
			logger.debug("customer demographic response no {} ",custDmgrphResp.getCustomerDemographicRespId().getDemographicRespNo());
		}
	}
	
	@Test
	public void testSaveCustomerDemographicResponses(){
		
		List<CustomerDemographicResponse> customerDemographicResponses = new ArrayList<CustomerDemographicResponse>();
		
		CustomerDemographicResponse customerDmgrphResp = customerDemographicResponseDao.create();
		CustomerDemographicResponseId id = customerDmgrphResp.getCustomerDemographicRespId();
		
		id.setCustomerId(1L);
		id.setDemographicQstnNo(1L);
		id.setDemographicRespNo(2L);
		
		customerDemographicResponses.add(customerDmgrphResp);
		customerDemographicResponseDao.saveCustomerDemographicResponse(customerDemographicResponses);		
		
	}
	
	@Test
	public void testDeleteCustomerDemographicResponsesForCurrentSet(){
		customerDemographicResponseDao.deleteCustomerDemographicResponsesForCurrentSet(502L, 1L);
	}
	
	
}
