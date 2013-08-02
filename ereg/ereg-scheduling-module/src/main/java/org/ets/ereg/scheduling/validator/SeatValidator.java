package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SeatValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (Seat.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Seat seat = (Seat)obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testVariation.id.testId", "scheduling.holdSeatRequest.Seat.Test.TestIdRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testVariation.id.languageCode", "scheduling.holdSeatRequest.Seat.Test.LanguageCodeRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testVariation.id.deliveryModeCode", "scheduling.holdSeatRequest.Seat.Test.DeliveryModeCodeRequired");
		/*ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "deliveryModeCode", "scheduling.holdSeatRequest.DeliveryModeCodeRequired");*/

		if( (null==seat.getTestCenter()) || (null==seat.getTestCenter().getId()) ){
			errors.rejectValue("testCenter", "scheduling.holdSeatRequest.TestCenterRequired");
		}
	}

}
