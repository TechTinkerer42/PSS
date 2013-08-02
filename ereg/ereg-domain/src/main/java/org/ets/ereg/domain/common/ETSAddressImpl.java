package org.ets.ereg.domain.common;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.ets.ereg.domain.interfaces.model.common.AddressType;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ETS_ADR")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ETSAddressImpl extends AddressImpl implements ETSAddress {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AddressTypeImpl.class, optional=false)
	@JoinColumn(name = "ADR_PRPS_CDE")
	private AddressType addressType;

	@Column(name = "PSTL_LN_3_ADR")
	private String addressLine3;


	@Column(name = "ST_PROV_NAM")
	private String stateProvinceText;

	@Column(name = "LTD_DEG", precision = 14, scale = 8)
	private BigDecimal latitudeDegree;

	@Column(name = "LNGTD_DEG", precision = 14, scale = 8)
	private BigDecimal longitudeDegree;
	
	@Column(name = "VERTEX_GEO_CDE", precision = 14, scale = 8)
	private Long vertexGeoCode;
	
	@Column(name="INTL_PSTL_ZIP")
	private String internationalPostalZipCode;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ecommerce.core.domain.ETSAddress#getAddressType()
	 */
	@Override
	public AddressType getAddressType() {
		return addressType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ets.ecommerce.core.domain.ETSAddress#setAddressType(org.ets.ecommerce
	 * .core.domain.AddressType)
	 */
	@Override
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ecommerce.core.domain.ETSAddress#getAddressLine3()
	 */
	@Override
	public String getAddressLine3() {
		return addressLine3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ets.ecommerce.core.domain.ETSAddress#setAddressLine3(java.lang.String
	 * )
	 */
	@Override
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ecommerce.core.domain.ETSAddress#getStateProvinceText()
	 */
	@Override
	public String getStateProvinceText() {
		return stateProvinceText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ets.ecommerce.core.domain.ETSAddress#setStateProvinceText(java.lang
	 * .String)
	 */
	@Override
	public void setStateProvinceText(String stateProvinceText) {
		this.stateProvinceText = stateProvinceText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ecommerce.core.domain.ETSAddress#getLatitudeDegree()
	 */
	@Override
	public BigDecimal getLatitudeDegree() {
		return latitudeDegree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ets.ecommerce.core.domain.ETSAddress#setLatitudeDegree(java.math.
	 * BigDecimal)
	 */
	@Override
	public void setLatitudeDegree(BigDecimal latitudeDegree) {
		this.latitudeDegree = latitudeDegree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ecommerce.core.domain.ETSAddress#getLongitudeDegree()
	 */
	@Override
	public BigDecimal getLongitudeDegree() {
		return longitudeDegree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ets.ecommerce.core.domain.ETSAddress#setLongitudeDegree(java.math
	 * .BigDecimal)
	 */
	@Override
	public void setLongitudeDegree(BigDecimal longitudeDegree) {
		this.longitudeDegree = longitudeDegree;
	}

	@Override
	public Long getVertexGeoCode() {
		return vertexGeoCode;
	}

	@Override
	public void setVertexGeoCode(Long vertexGeoCode) {
		this.vertexGeoCode = vertexGeoCode;
	}

	@Override
	public String getInternationalPostalZipCode() {
		return internationalPostalZipCode;
	}

	@Override
	public void setInternationalPostalZipCode(String internationalPostalZipCode) {
		this.internationalPostalZipCode = internationalPostalZipCode;
	}

	
}
