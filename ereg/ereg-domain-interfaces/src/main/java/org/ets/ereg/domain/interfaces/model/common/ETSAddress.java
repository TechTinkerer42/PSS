package org.ets.ereg.domain.interfaces.model.common;

import java.math.BigDecimal;

import org.broadleafcommerce.profile.core.domain.Address;

public interface ETSAddress extends Address {
	public static final String HomeAddressName = "home";
	
	public abstract AddressType getAddressType();

	public abstract void setAddressType(AddressType addressType);

	public abstract String getAddressLine3();

	public abstract void setAddressLine3(String addressLine3);

	public abstract String getStateProvinceText();

	public abstract void setStateProvinceText(String stateProvinceText);

	public abstract BigDecimal getLatitudeDegree();

	public abstract void setLatitudeDegree(BigDecimal latitudeDegree);

	public abstract BigDecimal getLongitudeDegree();

	public abstract void setLongitudeDegree(BigDecimal longitudeDegree);
	
	public abstract Long getVertexGeoCode();

	public abstract void setVertexGeoCode(Long vertexGeoCode);
		
	String getInternationalPostalZipCode();	
	void setInternationalPostalZipCode(String internationalPostalZipCode);
	
}
