package org.ets.ereg.domain.interfaces.scheduling.request;

import java.math.BigDecimal;

import org.ets.ereg.domain.interfaces.scheduling.response.Distance;

public interface MapFindSeatRequest extends FindSeatRequest {

	Distance getSearchRadius();
	void setSearchRadius(Distance searchRadius);
	
	BigDecimal getLatitudeDegree();
	void setLatitudeDegree(BigDecimal latitudeDegree);

	BigDecimal getLongitudeDegree();
	void setLongitudeDegree(BigDecimal longitudeDegree);

}
