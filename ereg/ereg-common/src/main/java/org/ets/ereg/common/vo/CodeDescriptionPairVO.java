package org.ets.ereg.common.vo;

import java.io.Serializable;

public class CodeDescriptionPairVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String description;

	public CodeDescriptionPairVO() {

	}

	public CodeDescriptionPairVO(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
