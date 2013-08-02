package org.ets.ereg.domain.interfaces.model.common;

import org.broadleafcommerce.profile.core.domain.Country;

public interface ETSCountry extends Country {
	String getIsdCode();
	void setIsdCode(String isdCode);
	
	String getIsO3CountryCode();
	void setIsO3CountryCode(String isO3CountryCode);
}
