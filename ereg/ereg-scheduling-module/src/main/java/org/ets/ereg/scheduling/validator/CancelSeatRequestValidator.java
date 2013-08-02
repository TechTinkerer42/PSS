package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CancelSeatRequestValidator extends AbstractSchedulingRequestValidator{

	private final Validator reservedSeatValidator;
	
	public CancelSeatRequestValidator(Validator reservedSeatValidator) {
        if (reservedSeatValidator == null) {
            throw new IllegalArgumentException(
              "The supplied [Reserved Seat Validator] is required and must not be null.");
        }
        if (!reservedSeatValidator.supports(ReservedSeat.class)) {
            throw new IllegalArgumentException(
              "The supplied [Reserved Seat Validator] must support the validation of [ReservedSeat] instances.");
        }
        this.reservedSeatValidator = reservedSeatValidator;
    }
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CancelSeatRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		super.validate(obj, errors);
		CancelSeatRequest cancelSeatRequest = (CancelSeatRequest)obj;
			try {
            errors.pushNestedPath("reservedSeat");
            ValidationUtils.invokeValidator(this.reservedSeatValidator, cancelSeatRequest.getReservedSeat(), errors);
        } finally {
            errors.popNestedPath();
        }
	}

}
