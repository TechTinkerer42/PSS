package org.ets.ereg.shared.service;

import junit.framework.Assert;

import org.ets.ereg.common.business.dao.ETSApplicationUserDao;
import org.ets.ereg.common.business.service.impl.ETSApplicationUserServiceImpl;
import org.ets.ereg.domain.common.ETSApplicationUserImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestETSApplicationUserServiceImpl {

	private static ETSApplicationUserServiceImpl applicationUserService = new ETSApplicationUserServiceImpl();
	
	private static Mockery mockingContext = new Mockery();
	private static ETSApplicationUserDao mockedApplicationUserDao;
	
	@BeforeClass
	public static void setup() {
		setupMockObjects();
		applicationUserService.setETSApplicationUserDao(mockedApplicationUserDao);
	}

	private static void setupMockObjects() {
		mockedApplicationUserDao = mockingContext.mock(ETSApplicationUserDao.class);
	}
	
	@Test
	public void testGetUserByPrimaryKey() {
		//setup test data
	    final ETSApplicationUser applicationUserImpl = new ETSApplicationUserImpl();


		//set expectations
		mockingContext.checking(new Expectations() {
		   {
             one(mockedApplicationUserDao).findByPrimaryKey("1");
             will(returnValue(applicationUserImpl));
           }
		});

		ETSApplicationUser status = applicationUserService.getUserByPrimaryKey("1");
		Assert.assertNotNull(status);

		mockingContext.assertIsSatisfied();
	}	
}
