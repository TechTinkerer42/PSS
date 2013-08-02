package org.ets.ereg.common.business.vo;

import java.io.Serializable;

public class ProgramColumnRuleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String entityName;
	private String propertyName;
	private String programCode;
	private boolean isDisplayable;
	private boolean isRequired;
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public boolean isDisplayable() {
		return isDisplayable;
	}
	public void setDisplayable(boolean isDisplayable) {
		this.isDisplayable = isDisplayable;
	}
	public boolean isRequired() {
		return isRequired;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	
}
