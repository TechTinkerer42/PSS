package org.ets.ereg.session.facade.shared.service;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;

public interface ApplicationConfigurationBusinessService {

	/**
	 * Method to get list of ApplicationConfigurtion domain object by using configuration name
	 *
	 * @param name
	 * @return
	 */
	public List<ApplicationConfiguration> findApplicationConfigurations(String name);

	/**
	 * Method to get ApplicationConfigurtion domain object by using configuration name
	 *
	 * @param name
	 * @return
	 */
	public String findApplicationConfigurationValue(String name);

	/**
	 * Method returns true if country is taxed
	 *
	 * @param country
	 * @return
	 */
	public boolean isCountryTaxed(Country country);

	/**
	 * Method returns true if country use state code
	 *
	 * @param country
	 * @return
	 */
	public boolean doesCountryUseStateCode(Country country);

	/**
	 * Method returns true if country is not for shipping
	 *
	 * @param country
	 * @return
	 */
	public boolean isCountryNotInternationalForShipping(Country country);

	/**
	 * Method returns true if security is enabled
	 *
	 * @param serviceName
	 * @return
	 */
	public boolean isSecurityEnabled(String serviceName);

	/**
	 *
	 * @param serviceName
	 * @return
	 */
	public long getCachedAuthExpirationWindow(String serviceName);
	/**
	 *
	 * @param pageName
	 * @return
	 */
	public Integer getSystemValueByName(String name);

}
