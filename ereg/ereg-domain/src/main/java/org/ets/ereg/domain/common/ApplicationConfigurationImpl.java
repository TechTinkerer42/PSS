package org.ets.ereg.domain.common;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ETS_APLCTN_CNFGN")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="etsStandardElements")
public class ApplicationConfigurationImpl implements ApplicationConfiguration{

	@Id
	@GeneratedValue(generator = "ConfigId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "ConfigId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "ApplicationConfiguration", allocationSize = 50)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAM", nullable = false, length = 100)
	private String name;

	@Column(name = "VAL", nullable = false, length = 1000)
	private String value;


	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration#getId()
	 */
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration#getValue()
	 */
	public String getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
