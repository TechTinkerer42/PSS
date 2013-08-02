package org.ets.ereg.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.broadleafcommerce.profile.core.domain.CountryImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;

@Entity
@Table(name="ETS_CNTRY")
@PrimaryKeyJoinColumn(name="CNTRY_CDE", referencedColumnName="ABBREVIATION")
public class ETSCountryImpl extends CountryImpl implements ETSCountry {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="ISD_CDE")
    private String isdCode; //ISD code
	
	@Column(name="ISO3_CNTRY_CDE")
	private String isO3CountryCode;

	@Override
	public String getIsdCode() {
		return isdCode;
	}

	@Override
	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	@Override
	public String getIsO3CountryCode() {
		return isO3CountryCode;
	}

	@Override
	public void setIsO3CountryCode(String isO3CountryCode) {
		this.isO3CountryCode = isO3CountryCode;
	}
}
