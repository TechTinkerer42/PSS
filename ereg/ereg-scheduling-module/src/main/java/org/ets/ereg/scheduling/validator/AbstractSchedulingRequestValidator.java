package org.ets.ereg.scheduling.validator;

import org.ets.ereg.scheduling.request.AbstractSchedulingRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AbstractSchedulingRequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AbstractSchedulingRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		AbstractSchedulingRequest req = (AbstractSchedulingRequest)obj;
		if(null == req.getCustomer()){
			errors.rejectValue("customer","scheduling.schedulingReuqest.CustomerRequired");
		}else{				
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.id", "scheduling.schedulingReuqest.CustomerRequired");
		}
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(
//				errors, "requesterRoleTypeCode", "scheduling.schedulingReuqest.RequesterRoleTypeCodeRequired");
//		
//		
//		ValidationUtils.rejectIfEmptyOrWhitespace(
//			errors, "duration", "scheduling.schedulingReuqest.DurationRequired");				
		

	}

}
