package org.ets.ereg.scheduling.validator;

import java.util.List;

import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.springframework.validation.Errors;


public class TCFindSeatRequestValidator extends FindSeatRequestValidator {

	@Override
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		TCFindSeatRequest tcFindSeatRequest = (TCFindSeatRequest)obj;		
		List<Long> testCenterIds = tcFindSeatRequest.getTestCenterIds();
		if( (null==testCenterIds) || (testCenterIds.isEmpty()) ||  (null==testCenterIds.get(0))){
			errors.rejectValue("testCenterIds","scheduling.findSeatRequest.TestCenterRequired");
		}	
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return TCFindSeatRequestValidator.class.isAssignableFrom(clazz);
	}
}
