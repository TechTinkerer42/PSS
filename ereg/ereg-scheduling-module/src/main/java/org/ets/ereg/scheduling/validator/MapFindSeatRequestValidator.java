package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.request.MapFindSeatRequest;
import org.springframework.validation.Errors;

public class MapFindSeatRequestValidator extends FindSeatRequestValidator {

	@Override
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		MapFindSeatRequest mapFindSeatRequest = (MapFindSeatRequest)obj;			
		if(null==mapFindSeatRequest.getLatitudeDegree()){
			errors.rejectValue("latitudeDegree","scheduling.findSeatRequest.LatitudeDegreeRequired");
		}
		
		if(null==mapFindSeatRequest.getLongitudeDegree()){
			errors.rejectValue("longitudeDegree","scheduling.findSeatRequest.LongitudeDegreeRequired");
		}
		
		if(null == mapFindSeatRequest.getSearchRadius()){
			errors.rejectValue("searchRadius","scheduling.findSeatRequest.SearchRadiusRequired");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MapFindSeatRequestValidator.class.isAssignableFrom(clazz);
	}

}
