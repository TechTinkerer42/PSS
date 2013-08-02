package org.ets.ereg.shared.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CountryImpl;
import org.ets.ereg.common.business.dao.ApplicationConfigurationDao;
import org.ets.ereg.common.business.service.impl.ApplicationConfigurationServiceImpl;
import org.ets.ereg.domain.common.ApplicationConfigurationImpl;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestApplicationConfigurationServiceImpl {
	private static ApplicationConfigurationServiceImpl applicationConfigurationService = new ApplicationConfigurationServiceImpl();

	private static Mockery mockingContext = new Mockery();
	private static ApplicationConfigurationDao mockedApplicationConfigurationDao;

	@BeforeClass
	public static void setup() {
		setupMockObjects();
		applicationConfigurationService.setApplicationConfigurationDao(mockedApplicationConfigurationDao);
	}

	private static void setupMockObjects() {
		mockedApplicationConfigurationDao = mockingContext.mock(ApplicationConfigurationDao.class);
	}


	@Test
	public void testFindApplicationConfigurations() {
		//setup test data
		Country unitedStates = new CountryImpl();
		unitedStates.setAbbreviation("US");

		ApplicationConfiguration usConfig = new ApplicationConfigurationImpl();
		usConfig.setName("TaxedCountry");
		usConfig.setValue("US");

		final List<ApplicationConfiguration> taxedCountries = new ArrayList<ApplicationConfiguration>();
		taxedCountries.add(usConfig);

		//set expectations
		mockingContext.checking(new Expectations() {
		   {
             one(mockedApplicationConfigurationDao).findConfigurationsByName("TaxedCountry");
             will(returnValue(taxedCountries));
           }
		});

		mockingContext.checking(new Expectations() {
		   {
             one(mockedApplicationConfigurationDao).findConfigurationsByName("NonExisting");
             will(returnValue(null));
           }
		});

		List<ApplicationConfiguration> config =applicationConfigurationService.findApplicationConfigurations("TaxedCountry");
		Assert.assertNotNull(config);
		Assert.assertEquals(1, config.size());

		config =applicationConfigurationService.findApplicationConfigurations("NonExisting");
		Assert.assertNull(config);

		mockingContext.assertIsSatisfied();
	}

	@Test
	public void testIsCountryTaxed() {
		//setup test data
		Country unitedStates = new CountryImpl();
		unitedStates.setAbbreviation("US");

		Country canada = new CountryImpl();
		canada.setAbbreviation("CA");

		Country mexico = new CountryImpl();
		mexico.setAbbreviation("MX");

		ApplicationConfiguration usConfig = new ApplicationConfigurationImpl();
		usConfig.setName("TaxedCountry");
		usConfig.setValue("US");

		ApplicationConfiguration canadaConfig = new ApplicationConfigurationImpl();
		canadaConfig.setName("TaxedCountry");
		canadaConfig.setValue("CA");

		final List<ApplicationConfiguration> taxedCountries = new ArrayList<ApplicationConfiguration>();
		taxedCountries.add(usConfig);
		taxedCountries.add(canadaConfig);

		//set expectations
		mockingContext.checking(new Expectations() {
			 {
             exactly(3).of(mockedApplicationConfigurationDao).findConfigurationsByName("TaxedCountry");
              will(returnValue(taxedCountries));
           }
		});

		Assert.assertTrue(applicationConfigurationService.isCountryTaxed(unitedStates));
		Assert.assertTrue(applicationConfigurationService.isCountryTaxed(canada));
		Assert.assertFalse(applicationConfigurationService.isCountryTaxed(mexico));

		mockingContext.assertIsSatisfied();
	}

	@Test
	public void testDoesCountryUseStateCode() {
		//setup test data
		Country unitedStates = new CountryImpl();
		unitedStates.setAbbreviation("US");

		Country canada = new CountryImpl();
		canada.setAbbreviation("CA");

		ApplicationConfiguration usConfig = new ApplicationConfigurationImpl();
		usConfig.setName("StateCodeCountry");
		usConfig.setValue("US");

		final List<ApplicationConfiguration> stateCodeCountries = new ArrayList<ApplicationConfiguration>();
		stateCodeCountries.add(usConfig);

		//set expectations
		mockingContext.checking(new Expectations() {
			 {
             exactly(2).of(mockedApplicationConfigurationDao).findConfigurationsByName("StateCodeCountry");
              will(returnValue(stateCodeCountries));
           }
		});

		Assert.assertTrue(applicationConfigurationService.doesCountryUseStateCode(unitedStates));
		Assert.assertFalse(applicationConfigurationService.doesCountryUseStateCode(canada));

		mockingContext.assertIsSatisfied();
	}
}
