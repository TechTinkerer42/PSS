package org.ets.ereg.session.facade.shared.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.springframework.stereotype.Service;

@Service("applicationConfigurationBusinessService")
public class ApplicationConfigurationBusinessServiceImpl implements ApplicationConfigurationBusinessService{

	@Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService#findApplicationConfigurations(java.lang.String)
	 */
	@Override
	public List<ApplicationConfiguration> findApplicationConfigurations(String name) {
		return applicationConfigurationService.findApplicationConfigurations(name);
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService#findApplicationConfigurationValue(java.lang.String)
	 */
	@Override
	public String findApplicationConfigurationValue(String name) {
		return applicationConfigurationService.findApplicationConfigurationValue(name);
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService#isCountryTaxed(org.broadleafcommerce.profile.core.domain.Country)
	 */
	@Override
	public boolean isCountryTaxed(Country country) {
		return applicationConfigurationService.isCountryTaxed(country);
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService#doesCountryUseStateCode(org.broadleafcommerce.profile.core.domain.Country)
	 */
	@Override
	public boolean doesCountryUseStateCode(Country country) {
		return applicationConfigurationService.doesCountryUseStateCode(country);
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService#isCountryNotInternationalForShipping(org.broadleafcommerce.profile.core.domain.Country)
	 */
	@Override
	public boolean isCountryNotInternationalForShipping(Country country){
		return applicationConfigurationService.isCountryNotInternationalForShipping(country);
	}
	
	
	

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService#isSecurityEnabled(java.lang.String)
	 */
	@Override
	public boolean isSecurityEnabled(String serviceName) {
		return applicationConfigurationService.isSecurityEnabled(serviceName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService#getCachedAuthExpirationWindow(java.lang.String)
	 */
	@Override
	public long getCachedAuthExpirationWindow(String serviceName) {
		return applicationConfigurationService.getCachedAuthExpirationWindow(serviceName);
	}
	
	@Override
	public Integer getSystemValueByName(String name){
		
		List<ApplicationConfiguration> applicationConfigs = applicationConfigurationService.findApplicationConfigurations(name);
		if(applicationConfigs !=null) {
			for(ApplicationConfiguration applicationConfig : applicationConfigs) {
				return Integer.parseInt(applicationConfig.getValue());
			}
		}
		
		return 1000;
	}
	
}
