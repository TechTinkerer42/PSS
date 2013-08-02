package org.ets.ereg.domain.dao.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CountryImpl;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.domain.StateImpl;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.domain.common.ETSAddressImpl;
import org.ets.ereg.domain.common.ETSCountryImpl;
import org.ets.ereg.domain.common.ETSPhoneImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.profile.model.dao.ETSCustomerDao;
import org.ets.ereg.profile.vo.ProfileVO;
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
public class TestETSCustomerDaoImpl {
	
	private static Logger logger = LoggerFactory.getLogger(TestETSCustomerDaoImpl.class);
	
	@Resource(name="etsCustomerDao") 
	private ETSCustomerDao etsCustomerDao;
	

	@Test
	public void testSearchCustomerByEmailAddressCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		customer.setEmailAddress("akovuri@ets.org");
		ETSAddress address = new ETSAddressImpl();
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone, searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	
	private SearchParameters populateSearchParams(){
		SearchParameters searchParams= new SearchParameters();
		searchParams.setNumofRows(0);
		searchParams.setPageSize(5);
		searchParams.setDes(null);
		searchParams.setSortBy(null);
		return searchParams;
		
	}
	
	@Test
	public void testGetCustomerCount(){
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		Country country= new  CountryImpl();
		country.setAbbreviation("US");
		address.setCountry(country);
		ETSPhone phone = new ETSPhoneImpl();
		Long count = etsCustomerDao.getCountForCustomersSearch(customer, address, phone,null,0L,null);
		Assert.assertEquals(10,(long) count);
	}
	
	
	@Test
	public void testSearchCustomerByCountryCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		Country country= new  CountryImpl();
		country.setAbbreviation("US");
		address.setCountry(country);
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams= new SearchParameters();
		searchParams.setNumofRows(2);
		searchParams.setPageSize(2);
		searchParams.setDes(null);
		searchParams.setSortBy(null);
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone, searchParams,null,0L,null);
		Assert.assertEquals(2, customers.size());
	}
	@Test
	public void testSearchCustomerByPostalCode(){
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		address.setPostalCode("08817");
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone, searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	
	@Test
	public void testSearchCustomerByCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone phone = new ETSPhoneImpl();
		phone.setPhoneNumber("893298329");
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone, searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	@Test
	public void testSearchCustomerByPhoneCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();	
		ETSAddress address = new ETSAddressImpl();
		ETSPhone phone = new ETSPhoneImpl();
		phone.setPhoneNumber("893298329");
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone,searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	
	@Test
	public void testSearchCustomerByStateCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		State state = new StateImpl();
		state.setAbbreviation("CT");
		address.setState(state);
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone, searchParams,null,0L,null);
		Assert.assertEquals(5, customers.size());
	}
	
	
	@Test
	public void testSearchCustomerByFirstNameCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		customer.setFirstName("Arvind");
		ETSAddress address = new ETSAddressImpl();
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone, searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	@Test
	public void testSearchCustomerByLastNameCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		customer.setLastName("Kovuri");
		customer.setMiddleInitial("K");
		ETSAddress address = new ETSAddressImpl();
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone, searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	


	@Test
	public void testSearchCustomerByDateOfBirthCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		SimpleDateFormat sf= new SimpleDateFormat("dd-MM-yyyy");
		Date dt = null;
		try {
			 dt=new Date(sf.parse("09-10-1983").getTime());
		} catch (ParseException e) {
			logger.error("exception occurred",e);
		}
		customer.setDateOfBirth(dt);
		ETSAddress address = new ETSAddressImpl();
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone,searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	@Test
	public void testSearchCustomerBySocialSecurityCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		customer.setSocialSecurity("7828");
		ETSAddress address = new ETSAddressImpl();
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone,searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}
	@Test
	public void testSearchCustomerByCityCriteria(){
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		address.setCity("Edison");
		ETSPhone phone = new ETSPhoneImpl();
		SearchParameters searchParams=populateSearchParams();
		List<CustomerVO> customers = etsCustomerDao.searchCustomerByCriteria(customer, address, phone,searchParams,null,0L,null);
		Assert.assertEquals(1, customers.size());
	}

	@Test
	public void testCheckDuplicateProfileMatch(){
		logger.debug(" >>>>> Test for Profile Match Starts here");
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-07-12"));
		customer.setEmailAddress("akovuri@ets.org");
		customer.setSocialSecurity("8930");
		profile.setCustomer(customer);

		address.setPostalCode("08817");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("3098251772");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		
		DupCheckResponseObject duplicate = etsCustomerDao.checkDuplicateProfile(profile);
		
		Assert.assertEquals(true, duplicate.isDuplicate());
		logger.debug(" >>>>> Test for Profile Match ends here");
		logger.debug(" =========================================================== ");
	}	
	@Test
	public void testCheckDuplicateProfileNoMatch(){
		logger.debug(" >>>>> Test for Profile Not Match Starts here \n");
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("test");
		customer.setLastName("test");
		customer.setDateOfBirth(Date.valueOf("1901-07-12"));
		customer.setEmailAddress("test@ets.org");
		customer.setSocialSecurity("1234");
		profile.setCustomer(customer);

		address.setPostalCode("12345");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("1234567890");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		
		DupCheckResponseObject duplicate = etsCustomerDao.checkDuplicateProfile(profile);
	
		Assert.assertEquals(false, duplicate.isDuplicate());
		logger.debug(" >>>>> Test for Profile Not Match ends here \n");
		logger.debug(" =========================================================== ");
	}
	
	@Test
	public void testCheckDuplicateProfileEmailMatch(){
		
		logger.debug(" >>>>> Test for Profile Email Match Starts here");
		
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("test");
		customer.setLastName("test");
		customer.setDateOfBirth(Date.valueOf("1901-07-12"));
		customer.setEmailAddress("akovuri@ets.org");
		customer.setSocialSecurity("1234");
		profile.setCustomer(customer);

		address.setPostalCode("12345");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("1234567890");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		
		DupCheckResponseObject duplicate = etsCustomerDao.checkDuplicateProfile(profile);

		Assert.assertEquals("Duplicate Email", duplicate.getMessageList().get(0).trim());
		
		logger.debug(" >>>>> Test for Profile Email Match Ends here \n");
		logger.debug(" =========================================================== ");
	}
	
	@Test
	public void testCheckDuplicateProfileZIPMatch(){
		
		logger.debug(" >>>>> Test for Profile Zip code Match Starts here");
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-10-09"));
		customer.setEmailAddress("test1@ets.org");
		customer.setSocialSecurity("8930");
		profile.setCustomer(customer);

		address.setPostalCode("08817");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("3098251772");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		
		DupCheckResponseObject duplicate = etsCustomerDao.checkDuplicateProfile(profile);

		Assert.assertEquals("Duplicate Name or Date of Birth or Zip code",duplicate.getMessageList().get(0).trim());
		logger.debug(" >>>>> Test for Profile Email Match ends here \n");
		logger.debug(" =========================================================== ");
	}
	@Test
	public void testCheckDuplicateProfileSSNMatch(){
		
		logger.debug(" >>>>> Test for Profile SSN Match Starts here");
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-10-09"));
		customer.setEmailAddress("test1@ets.org");
		customer.setSocialSecurity("7828");
		profile.setCustomer(customer);

		address.setPostalCode("0000");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("3098251772");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		
		DupCheckResponseObject duplicate = etsCustomerDao.checkDuplicateProfile(profile);
		
		Assert.assertEquals("Duplicate Name or Date of Birth or SSN",duplicate.getMessageList().get(0).trim());
		
		logger.debug(" >>>>> Test for Profile SSN Match Ends here \n");
		logger.debug(" =========================================================== ");
	}
	@Test
	public void testCheckDuplicateProfilePhone1Match(){
		
		logger.debug(" >>>>> Test for Profile Phone 1 Match Starts here");
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-10-09"));
		customer.setEmailAddress("test1@ets.org");
		customer.setSocialSecurity("");
		profile.setCustomer(customer);

		address.setPostalCode("0000");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("87389883898");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		
		DupCheckResponseObject duplicate = etsCustomerDao.checkDuplicateProfile(profile);
		
		Assert.assertEquals("Duplicate Name or Date of Birth or Phone",duplicate.getMessageList().get(0).trim());
		
		logger.debug(" >>>>> Test for Profile Phone 1 Match Ends here \n");
		logger.debug(" =========================================================== ");
	}
	
	@Test
	public void testCheckDuplicateProfilePhone2Match(){
		
		logger.debug(" >>>>> Test for Profile Phone 2 Match Starts here");
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-10-09"));
		customer.setEmailAddress("test1@ets.org");
		customer.setSocialSecurity("");
		profile.setCustomer(customer);

		address.setPostalCode("0000");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("000000");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("USA");
		secondaryPhone.setPhoneNumber("893298329");
		profile.setAlternatePhone(secondaryPhone);
		
		DupCheckResponseObject duplicate = etsCustomerDao.checkDuplicateProfile(profile);
		
		Assert.assertEquals("Duplicate Name or Date of Birth or Phone",duplicate.getMessageList().get(0).trim());
		
		logger.debug(" >>>>> Test for Profile Phone 2 Match Ends here \n");
	}
	
	@Test
	public void testGetDuplicatetCustomerCount(){
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-10-09"));
		customer.setEmailAddress("test1@ets.org");
		//customer.setSocialSecurity("8930");
		profile.setCustomer(customer);

		address.setPostalCode("08817");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("87389883898");
		profile.setPrimaryPhone(primaryPhone);
		//secondaryPhone.setCountry(country2);
		//secondaryPhone.getCountry().setAbbreviation("US");
		//secondaryPhone.setPhoneNumber("3098251772");
		//profile.setAlternatePhone(secondaryPhone);
		logger.debug(" ===========================================================");
		List<CustomerVO> test = etsCustomerDao.getDuplicateProfiles(profile, false);
		logger.debug("duplicate profiles size {}",test.size());
		Assert.assertNotNull(test);
	}
	
	@Test
	public void testGetDuplicatetCustomerBooleanTrue(){
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-10-09"));
		customer.setEmailAddress("test1@ets.org");
		//customer.setSocialSecurity("8930");
		profile.setCustomer(customer);

		address.setPostalCode("08817");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("3098251772");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		logger.debug(" ===========================================================");
		boolean isDuplicate = etsCustomerDao.hasDuplicateProfiles(profile, false);
		Assert.assertTrue(isDuplicate);
	}
	@Test
	public void testGetDuplicatetCustomerBooleanFalse(){
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		ETSAddress address = new ETSAddressImpl();
		ETSPhone primaryPhone = new ETSPhoneImpl();
		ETSPhone secondaryPhone = new ETSPhoneImpl();
		ETSCountry country1= new ETSCountryImpl();
		ETSCountry country2= new ETSCountryImpl();
		customer.setFirstName("Arvind");
		customer.setLastName("Kovuri");
		customer.setDateOfBirth(Date.valueOf("1983-10-09"));
		customer.setEmailAddress("akovuri@ets.org");
		customer.setSocialSecurity("7828");
		customer.setId(new Long(1));
		profile.setCustomer(customer);

		address.setPostalCode("08817");		
		profile.setAddress(address);
		primaryPhone.setCountry(country1);
		primaryPhone.getCountry().setAbbreviation("US");
		primaryPhone.setPhoneNumber("3098251772");
		profile.setPrimaryPhone(primaryPhone);
		secondaryPhone.setCountry(country2);
		secondaryPhone.getCountry().setAbbreviation("US");
		secondaryPhone.setPhoneNumber("3098251772");
		profile.setAlternatePhone(secondaryPhone);
		logger.debug(" ===========================================================");
		//List<CustomerVO> test = etsCustomerDao.getDuplicateProfiles(profile);
		boolean isDuplicate = etsCustomerDao.hasDuplicateProfiles(profile, true);
		Assert.assertFalse(isDuplicate);
	}
	
	@Test
	public void testFindCustomerByUsernameAndInternalFlag(){
	    ETSCustomer customer = (ETSCustomer) etsCustomerDao.findCustomerByUsernameAndInternalFlag("akovuri",Boolean.TRUE);
	    assertNotNull(customer);
	    assertThat(customer.getId(), equalTo(1L));
	    customer = (ETSCustomer) etsCustomerDao.findCustomerByUsernameAndInternalFlag("sun.sum",Boolean.FALSE);
	    assertNotNull(customer);
	    assertThat(customer.getId(), equalTo(2L));
	    customer = (ETSCustomer) etsCustomerDao.findCustomerByUsernameAndInternalFlag("akovuri",Boolean.FALSE);
	    assertNull(customer);
	}
	
	
}
