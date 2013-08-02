package org.ets.ereg.scheduling.validator;

import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class HoldSeatRequestValidator extends AbstractSchedulingRequestValidator{

	private final Validator seatValidator;
	
	public HoldSeatRequestValidator(Validator seatValidator) {
        if (seatValidator == null) {
            throw new IllegalArgumentException(
              "The supplied [Seat Validator] is required and must not be null.");
        }
        if (!seatValidator.supports(Seat.class)) {
            throw new IllegalArgumentException(
              "The supplied [Validator] must support the validation of [Seat] instances.");
        }
        this.seatValidator = seatValidator;
    }
	
	@Override
	public boolean supports(Class<?> clazz) {		
		return HoldSeatRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		HoldSeatRequest holdSeatRequest = (HoldSeatRequest)obj;		
		if(null == holdSeatRequest.getSeat()){
			errors.rejectValue("seat","scheduling.holdSeatRequest.SeatRequired");
		}else{
			try {
	            errors.pushNestedPath("seat");
	            ValidationUtils.invokeValidator(this.seatValidator, holdSeatRequest.getSeat(), errors);
	        } finally {
	            errors.popNestedPath();
	        }
		}		
		
	}

}
