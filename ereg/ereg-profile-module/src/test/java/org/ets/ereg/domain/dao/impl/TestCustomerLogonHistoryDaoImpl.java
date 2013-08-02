package org.ets.ereg.domain.dao.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;
import org.ets.ereg.profile.dao.CustomerLogonHistoryDao;
import org.ets.ereg.profile.model.dao.ETSCustomerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestCustomerLogonHistoryDaoImpl {
	@Resource(name="etsCustomerLogonHistoryDao")
	protected CustomerLogonHistoryDao customerLogonHistoryDao;
	
	@Resource(name="etsCustomerDao") 
	private ETSCustomerDao etsCustomerDao;
	
	@PersistenceContext(unitName="blPU")
    protected EntityManager em;	
	
	
	
	/*@Resource(name="blCustomerService")
    protected CustomerService customerService;*/
	
	public CustomerLogonHistory createRecord(){
		CustomerLogonHistory histRecord=customerLogonHistoryDao.create();
		histRecord.setId((long) 0);
		histRecord.setApplicationServerId("tomcat");
		histRecord.setBrowserName("IE");
		
	//	histRecord.setCustomer(getCustomer());
		histRecord.setJavaSessionId("SomeSessionId");
		
		Date sysdate=new Date();
		histRecord.setLogonTimestamp(sysdate);
		
		
		histRecord.setLogoutTimestamp(sysdate);
		histRecord.setOperatingSystemname("Win");
		
		return histRecord;
		
	}
	
	/*private Customer getCustomer() {
		ETSCustomer customer = new ETSCustomerImpl();
		CustomerDaoImpl c=new CustomerDaoImpl();
		customer.setUsername("ABC");
		customer.setEmailAddress("abc@gmail.com");
		customer.setId((long) 140);
		customer.setFirstName("abc");
		customer.setLastName("last");
		customer.setPassword("pass");
		customer.setPasswordChangeRequired(false);
		customer.setReceiveEmail(false);
		customer.setRegistered(true);		
		customer.setTaxExmptFlag(true);
	
		//CustomerType customerType = referenceService.getEntityByPrimaryKey(CustomerType.class, CustomerType.TestTaker);
		
		//customer.setCustomerType(customerType);
		
		return etsCustomerDao.save(customer);
		
	//	Customer customer1=c.readCustomerByUsername("Tom.Rodgers");
		
	//	return em.find(Customer.class, 18);
		
		//return customer1;
	}
*/
	@Test
	public void saveNRetrieveRecord(){
		/*CustomerLogonHistory record=createRecord();
		CustomerLogonHistory saved=customerLogonHistoryDao.save(record);
		String sessionId=saved.getJavaSessionId();
		logger.debug("sessionId:"+sessionId);
		String user=saved.getCustomer().getUsername();
		logger.debug("user:"+user);
		logger.debug("Id of Customer History:"+saved.getId());
		assertEquals("IE",saved.getBrowserName());
		CustomerLogonHistory retrieved=customerLogonHistoryDao.getCustomerLogonHistory("Tom.Rodgers", "SomeSessionId");		
		logger.debug("retrieved:"+retrieved==null?"null":"not null");
		logger.debug("retrieved Browser:"+retrieved.getBrowserName());
		logger.debug("retrieved:"+retrieved.getLogoutReasonTypeCode());*/
		
	}	

}
