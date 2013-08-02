package org.ets.ereg.scheduling.vo;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;

public class TestCenterSearchCriteria {
	private Double latitudeDegree;
	private Double longitudeDegree;
	private Double distanceMile;
	private String testCenterName;
	private List<DeliveryModeType> testTypes;
	private ProgramType programType;


	public Double getLatitudeDegree() {
		return latitudeDegree;
	}
	
	public void setLatitudeDegree(Double latitudeDegree) {
		this.latitudeDegree = latitudeDegree;
	}
	
	public Double getLongitudeDegree() {
		return longitudeDegree;
	}
	
	public void setLongitudeDegree(Double longitudeDegree) {
		this.longitudeDegree = longitudeDegree;
	}
	
	public Double getDistanceMile() {
		return distanceMile;
	}
	
	public void setDistanceMile(Double distanceMile) {
		this.distanceMile = distanceMile;
	}
	
	public String getTestCenterName() {
		return testCenterName;
	}
	
	public void setTestCenterName(String testCenterName) {
		this.testCenterName = testCenterName;
	}
	
	public List<DeliveryModeType> getTestTypes() {
		return testTypes;
	}
	
	public void setTestTypes(List<DeliveryModeType> testTypes) {
		this.testTypes = testTypes;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}


}
