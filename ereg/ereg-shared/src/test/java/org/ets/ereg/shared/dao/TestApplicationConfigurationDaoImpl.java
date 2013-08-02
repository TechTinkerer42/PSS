package org.ets.ereg.shared.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.ets.ereg.common.business.dao.ApplicationConfigurationDao;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestApplicationConfigurationDaoImpl {
	@Resource(name="applicationConfigurationDao")
	private ApplicationConfigurationDao applicationConfigurationDao;
	
	@Test
	public void testSearchByName() {
		List<ApplicationConfiguration> config =applicationConfigurationDao.findConfigurationsByName("TaxedCountry");
		Assert.assertNotNull(config);
		Assert.assertEquals(1, config.size());
		
		config =applicationConfigurationDao.findConfigurationsByName("StateCodeCountry");
		Assert.assertNotNull(config);
		Assert.assertEquals(2, config.size());
		
		config =applicationConfigurationDao.findConfigurationsByName("NonExisting");
		Assert.assertNotNull(config);
		Assert.assertEquals(0, config.size());
	}
}
