package org.ets.ereg.eias.client.service;

import static org.junit.Assert.*;

import java.sql.Date;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestionImpl;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.profile.ETSAdminUserImpl;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.external.service.eias.client.exception.EIASException;
import org.ets.ereg.external.service.eias.client.service.EIASWebServiceClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestEIASWebServiceClientImpl {

	@Resource(name = "EIASWebServiceClient")
	private EIASWebServiceClient eiasWebServiceClient;
	
	ETSCustomer customer;
	
	ETSAdminUser adminUser;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	    customer = new ETSCustomerImpl();
		ChallengeQuestion challengeQuestion = new ChallengeQuestionImpl();
		//challengeQuestion.setQuestion("1");
		challengeQuestion.setId(new Long(1));
		customer.setChallengeAnswer("NA");
		customer.setFirstName("ETS");
		customer.setLastName("TestCase");
		customer.setDateOfBirth(Date.valueOf("1983-07-12"));
		customer.setEmailAddress("akovuri@ets.org");
		customer.setSocialSecurity("8930");
		customer.setPassword("1qaz@WSX");
		customer.setUsername("njain001");
		customer.setChallengeQuestion(challengeQuestion);
		
		adminUser = new ETSAdminUserImpl();
		adminUser.setFirstName("ETS");
		adminUser.setLastName("TestCase");
		adminUser.setDateOfBirth(Date.valueOf("1983-07-12"));
		adminUser.setEmail("njain003@ets.org");
		adminUser.setLogin("TestTaker1");
		adminUser.setPassword("1qaz@WSX");
		adminUser.setChallengeQuestion(challengeQuestion);
		adminUser.setChallengeAnswer("NA");
		

		
	}
	
	@Test(expected=EIASException.class)
	public void testCreateUser()
	{
		String guid = eiasWebServiceClient.createUser(customer);
		assertTrue(guid.getClass()==String.class);
	}

	@Test
	public void testSearchUser()
	{
		boolean isUserAvailable = eiasWebServiceClient.searchUser("TestTaker1");
		assertFalse(isUserAvailable);
	}
	
	@Test
	public void testModifyUser()
	{
		String guid  = eiasWebServiceClient.modifyUser(customer);
		assertTrue(guid.getClass()==String.class);
	}	
	
	@Test(expected=EIASException.class)
	public void testResetPassword()
	{
		String guid  = eiasWebServiceClient.modifyUser(customer);
		eiasWebServiceClient.resetPassword("TestTaker1", guid, "1qaz@WSX", "N");
	}	
	
	@Test(expected=EIASException.class)
	public void testCreateAdmin()
	{
		String guid = eiasWebServiceClient.createUser(adminUser);
		assertTrue(guid.getClass()==String.class);
	}

	@Test
	public void testSearchAdmin()
	{
		boolean isUserAvailable = eiasWebServiceClient.searchUser("TestTaker1");
		assertFalse(isUserAvailable);
	}
	
	@Test
	public void testModifyAdmin()
	{
		String guid  = eiasWebServiceClient.modifyUser(adminUser);
		assertTrue(guid.getClass()==String.class);
	}	
	
	@Test(expected=EIASException.class)
	public void testResetPasswordAdmin()
	{
		String guid  = eiasWebServiceClient.modifyUser(adminUser);
		eiasWebServiceClient.resetPassword("njain001", guid, "1qaz@WSX", "N");
	}	

}
