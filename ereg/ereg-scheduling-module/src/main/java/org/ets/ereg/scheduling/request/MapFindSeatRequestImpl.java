package org.ets.ereg.scheduling.request;

import java.math.BigDecimal;

import org.ets.ereg.domain.interfaces.scheduling.request.MapFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.Distance;

public class MapFindSeatRequestImpl extends FindSeatRequestImpl implements MapFindSeatRequest {

	private Distance searchRadius;	
	private BigDecimal latitudeDegree;
	private BigDecimal longitudeDegree;

	@Override
	public Distance getSearchRadius() {
		return searchRadius;
	}

	@Override
	public void setSearchRadius(Distance searchRadius) {
		this.searchRadius = searchRadius;
	}

	@Override
	public BigDecimal getLatitudeDegree() {
		return latitudeDegree;
	}

	@Override
	public void setLatitudeDegree(BigDecimal latitudeDegree) {
		this.latitudeDegree = latitudeDegree;
	}

	@Override
	public BigDecimal getLongitudeDegree() {
		return longitudeDegree;
	}

	@Override
	public void setLongitudeDegree(BigDecimal longitudeDegree) {
		this.longitudeDegree = longitudeDegree;
	}

}
