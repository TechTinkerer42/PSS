package org.ets.ereg.scheduling.request;

import org.ets.ereg.common.util.DistanceUnit;
import org.ets.ereg.domain.interfaces.scheduling.response.Distance;

public class DistanceImpl implements Distance {

	private DistanceUnit unitOfMeasure;
	private float distance;
	
	@Override
	public DistanceUnit getUnitOfMeasure() {
		return unitOfMeasure;
	}

	@Override
	public void setUnitOfMeasure(DistanceUnit unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@Override
	public float getDistance() {
		return distance;
	}

	@Override
	public void setDistance(float distance) {
		this.distance = distance;
	}

}
