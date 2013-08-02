package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class HeldSeatValidator extends SeatValidator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return HeldSeat.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {	
		super.validate(obj, errors);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "holdCode", "scheduling.heldSeat.HoldCodeRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "holdDuration", "scheduling.heldSeat.HoldDurationRequired");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "holdExpiration", "scheduling.heldSeat.HoldExpirationRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "holdSource", "scheduling.heldSeat.HoldSourceRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "seatCode", "scheduling.heldSeat.SeatCodeRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "labCode", "scheduling.heldSeat.LabCodeRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siteCode", "scheduling.heldSeat.SiteCodeRequired");		
	}

}
