package org.ets.ereg.common.business.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.common.business.dao.ApplicationConfigurationDao;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository("applicationConfigurationService")
public class ApplicationConfigurationServiceImpl implements ApplicationConfigurationService {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfigurationServiceImpl.class);

	@Resource(name="applicationConfigurationDao")
	private ApplicationConfigurationDao applicationConfigurationDao;


	public void setApplicationConfigurationDao(
			ApplicationConfigurationDao applicationConfigurationDao) {
		this.applicationConfigurationDao = applicationConfigurationDao;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ApplicationConfigurationService#findApplicationConfigurations(java.lang.String)
	 */
	@Override
	public List<org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration> findApplicationConfigurations(String name) {
		return applicationConfigurationDao.findConfigurationsByName(name);
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ApplicationConfigurationService#findApplicationConfigurationValue(java.lang.String)
	 */
	@Override
	public String findApplicationConfigurationValue(String name) {
		List<ApplicationConfiguration> appConfigs = applicationConfigurationDao.findConfigurationsByName(name);
		if(appConfigs != null && appConfigs.size()>0){
			return appConfigs.get(0).getValue();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ApplicationConfigurationService#isCountryTaxed(org.broadleafcommerce.profile.core.domain.Country)
	 */
	@Override
	public boolean isCountryTaxed(Country country) {
		List<ApplicationConfiguration> applicationConfigs = applicationConfigurationDao.findConfigurationsByName("TaxedCountry");
		if(applicationConfigs !=null) {
			for(ApplicationConfiguration applicationConfig : applicationConfigs) {
				if(applicationConfig.getValue().equals(country.getAbbreviation())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ApplicationConfigurationService#doesCountryUseStateCode(org.broadleafcommerce.profile.core.domain.Country)
	 */
	@Override
	public boolean doesCountryUseStateCode(Country country) {
		List<ApplicationConfiguration> applicationConfigs = applicationConfigurationDao.findConfigurationsByName("StateCodeCountry");
		if(applicationConfigs !=null) {
			for(ApplicationConfiguration applicationConfig : applicationConfigs) {
				if(applicationConfig.getValue().equals(country.getAbbreviation())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ApplicationConfigurationService#isCountryNotInternationalForShipping(org.broadleafcommerce.profile.core.domain.Country)
	 */
	@Override
	public boolean isCountryNotInternationalForShipping(Country country){
		List<ApplicationConfiguration> applicationConfigs = applicationConfigurationDao.findConfigurationsByName("NotInternationalCountryForShipping");
		if(applicationConfigs !=null) {
			for(ApplicationConfiguration applicationConfig : applicationConfigs) {
				if(applicationConfig.getValue().equals(country.getAbbreviation())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ApplicationConfigurationService#isSecurityEnabled(java.lang.String)
	 */
	@Override
	public boolean isSecurityEnabled(String serviceName) {
		if(LOG.isDebugEnabled()){
			LOG.debug("serviceName: {}", serviceName);
		}
		List<ApplicationConfiguration> applicationConfigs = applicationConfigurationDao.findConfigurationsByName(serviceName+"_Security_Enabled");
		if(applicationConfigs !=null) {
			for(ApplicationConfiguration applicationConfig : applicationConfigs) {
				if(LOG.isDebugEnabled()){
					LOG.debug("applicationConfig.getValue(): {}",applicationConfig.getValue());
				}
				if(Boolean.parseBoolean(applicationConfig.getValue())) {
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ApplicationConfigurationService#getCachedAuthExpirationWindow(java.lang.String)
	 */
	@Override
	public long getCachedAuthExpirationWindow(String serviceName) {
		if(LOG.isDebugEnabled()){
			LOG.debug("serviceName: {}", serviceName);
		}
		List<ApplicationConfiguration> applicationConfigs = applicationConfigurationDao.findConfigurationsByName(serviceName+"_CacheAuthWindow");
		if(applicationConfigs !=null) {
			for(ApplicationConfiguration applicationConfig : applicationConfigs) {
				if(LOG.isDebugEnabled()){
					LOG.debug("applicationConfig.getValue(): {}", applicationConfig.getValue());
				}
				return Long.parseLong(applicationConfig.getValue());
			}
		}
		//return default - 10 min
		return 600000;
	}
}
