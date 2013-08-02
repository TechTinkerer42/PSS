package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class FindSeatRequestValidator extends AbstractSchedulingRequestValidator  {

	@Override
	public void validate(Object obj, Errors errors) {

			super.validate(obj, errors);
			FindSeatRequest findSeatRequest = (FindSeatRequest)obj;

			if(null == findSeatRequest.getSearchFromDate()){
				errors.rejectValue("searchFromDate","scheduling.findSeatRequest.SearchFromDateRequired");
			}

			if(null == findSeatRequest.getSearchToDate()){
				errors.rejectValue("searchToDate","scheduling.findSeatRequest.SearchToDateRequired");
			}

			ValidationUtils.rejectIfEmptyOrWhitespace(
					errors, "deliveryModeCode", "scheduling.findSeatRequest.DeliveryModeCodeRequired");

			if(null == findSeatRequest.getTestVariation()){
				errors.rejectValue("testVariation","scheduling.findSeatRequest.TestVariationRequired");
			} else if(null == findSeatRequest.getTestVariation().getId()){
                errors.rejectValue("testVariation.id","scheduling.findSeatRequest.TestVariationIdRequired");
            }
			/*else{
				ValidationUtils.rejectIfEmpty(errors, "testId", "scheduling.findSeatRequest.Test.TestCodeRequired");
			}*/
	 }

	/**
	 * This validator validates FindSeatRequest and any subclass of FindSeatRequest also.
	 */
	@Override
	public boolean supports(Class<?> clazz) {

		return FindSeatRequest.class.isAssignableFrom(clazz);
	}

}
