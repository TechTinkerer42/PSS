package org.ets.ereg.shared.dao;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ETSApplicationUserDao;
import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestETSApplicationUserDaoImpl {

	@Resource(name="ETSApplicationUserDao")
	private ETSApplicationUserDao etsApplicationUserDao;
	
	@Test
	public void testFindByPrimaryKeyEIASUserMatch()
	{
		ETSApplicationUser test = etsApplicationUserDao.findByPrimaryKey("1");
		Assert.assertEquals("1", test.getUserID());
		Assert.assertEquals("NISHANT", test.getUserName());
		Assert.assertEquals("PASS", test.getPassword());
	}
	
	@Test
	public void testFindByPrimaryKeyEIASUserNoMatch()
	{
		ETSApplicationUser test = etsApplicationUserDao.findByPrimaryKey("2");

		Assert.assertNull(test);
	}
}
