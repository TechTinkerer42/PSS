package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ReleaseSeatRequestValidator extends AbstractSchedulingRequestValidator{

	private final Validator heldSeatValidator;
	
	public ReleaseSeatRequestValidator(Validator heldSeatValidator) {
        if (heldSeatValidator == null) {
            throw new IllegalArgumentException(
              "The supplied [Held Seat Validator] is required and must not be null.");
        }
        if (!heldSeatValidator.supports(HeldSeat.class)) {
            throw new IllegalArgumentException(
              "The supplied [Held Seat Validator] must support the validation of [HeldSeat] instances.");
        }
        this.heldSeatValidator = heldSeatValidator;
    }
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ReleaseSeatRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		ReleaseSeatRequest releaseSeatRequest = (ReleaseSeatRequest)obj;
		try {
            errors.pushNestedPath("heldSeat");
            ValidationUtils.invokeValidator(this.heldSeatValidator, releaseSeatRequest.getHeldSeat(), errors);
        } finally {
            errors.popNestedPath();
        }
	}

}
