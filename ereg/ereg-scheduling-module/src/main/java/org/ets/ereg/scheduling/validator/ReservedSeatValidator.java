package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class ReservedSeatValidator extends HeldSeatValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ReservedSeat.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		super.validate(obj, errors);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etsReservationID", "scheduling.reservedSeat.EtsReservationIDRequired");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etsAppointmentId", "scheduling.reservedSeat.EtsAppointmentIDRequired");		
	}

}
