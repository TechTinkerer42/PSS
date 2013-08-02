package org.ets.ereg.common.business.vo;

public class CodeValuePairVo {
	
	private Long id;
	private String code;
	private String value;
	
	
	
	
	public CodeValuePairVo(Long id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	public CodeValuePairVo(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	

}
