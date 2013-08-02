package org.ets.ereg.domain.interfaces.scheduling.response;

import org.ets.ereg.common.util.DistanceUnit;

public interface Distance {
	DistanceUnit getUnitOfMeasure();
	void setUnitOfMeasure(DistanceUnit unitOfMeasure);
	float getDistance();
	void setDistance(float distance);
}
