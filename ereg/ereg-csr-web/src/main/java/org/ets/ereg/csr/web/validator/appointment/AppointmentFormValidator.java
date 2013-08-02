package org.ets.ereg.csr.web.validator.appointment;

import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface AppointmentFormValidator extends Validator {
	
	void validateAppointmentInfo(AppointmentForm appointmentForm, Errors errors);
	
	void validateAppointment(AppointmentVO appointment, Errors errors);

}
