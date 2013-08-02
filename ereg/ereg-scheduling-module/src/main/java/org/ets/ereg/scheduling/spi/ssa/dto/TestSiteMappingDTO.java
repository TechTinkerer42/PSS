package org.ets.ereg.scheduling.spi.ssa.dto;

import java.io.Serializable;
import java.util.List;

/**
 * TestSiteMappingDTO
 *
 * @version	1.0 Apr 14, 2011
 * @author 	Venkat Shanmugam
 * 
 * @history
 * Apr 14, 2011 VS Initial Creation
 *
 **/
public class TestSiteMappingDTO implements Serializable {
	
	private static final long serialVersionUID = 8187479764157231507L;

	private String siteCode;
	
	private List<String> testCode;

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public List<String> getTestCode() {
		return testCode;
	}

	public void setTestCode(List<String> testCode) {
		this.testCode = testCode;
	}
	
	
}
