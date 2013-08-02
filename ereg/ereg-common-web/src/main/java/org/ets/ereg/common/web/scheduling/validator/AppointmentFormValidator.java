package org.ets.ereg.common.web.scheduling.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("appointmentValidator")
public class AppointmentFormValidator implements Validator {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(AppointmentFormValidator.class);

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object arg0, Errors arg1) {

	}

	public void validateAppointmentInfo(AppointmentForm appointmentForm, Errors errors) {
		List<AppointmentVO> appointments = appointmentForm.getAppointments();
		Set<String> tests = new HashSet<String>();
		for (int i = 0; i < appointments.size(); i ++) {
			AppointmentVO appointment = appointments.get(i);
			if (!appointment.getDeletedFlag()) {
				validateAppointment(appointment, i, tests, errors);
			}
		}
	}

	private void validateAppointment(AppointmentVO appointment, int i, Set<String> tests, Errors errors) {
		boolean isValidTest = validateTest(appointment, i, errors);
		boolean isValidTestDateTime = validateTestDateTime(appointment, i, errors);
		boolean isValidTestCenter = validateTestCenter(appointment, i, errors);
		boolean isValidTestType = validateTestType(appointment, i, errors);
		boolean isValidTestLanguage = validateTestLanguage(appointment, i, errors);
		if (isValidTest && isValidTestDateTime && isValidTestCenter && isValidTestType && isValidTestLanguage) {
			validateDupAppointment(appointment, i, tests, errors);
		}
	}

	public void validateAppointment(AppointmentVO appointment, Errors errors) {
		validateTest(appointment, 0, errors);
		validateTestDateTime(appointment, 0, errors);
		validateTestCenter(appointment, 0, errors);
		validateTestType(appointment, 0, errors);
		validateTestLanguage(appointment, 0, errors);
	}

	private boolean validateTest(AppointmentVO appointment, int i, Errors errors) {
		if (null == appointment.getBooking().getTestVariation()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST, "schedulenewappointment.testTitleRequired");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateTestDateTime(AppointmentVO appointment, int i, Errors errors) {
		if (null == appointment.getBooking().getAppointmentDateTime()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_DATE_TIME, "schedulenewappointment.testDateRequired");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateTestCenter(AppointmentVO appointment, int i, Errors errors) {
		if (null == appointment.getBooking().getTestCenter()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_CENTER, "schedulenewappointment.testCenterRequired");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateTestType(AppointmentVO appointment, int i, Errors errors) {
		if (null == appointment.getBooking().getTestVariation().getDeliveryModeType()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_TYPE, "schedulenewappointment.testTypeRequired");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateTestLanguage(AppointmentVO appointment, int i, Errors errors) {
		if (null == appointment.getBooking().getTestVariation().getLanguageType().getCode() || null == appointment.getBooking().getTestVariation().getLanguageType().getCode()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_LANGUAGE, "schedulenewappointment.testLanguageRequired");
			return false;
		} else {
			return true;
		}
	}

	private void validateDupAppointment(AppointmentVO appointment, int i, Set<String> tests, Errors errors) {
		if (tests.contains(appointment.getBooking().getTestVariation().getTest().getTestName())) {
			errors.reject("schedulenewappointment.testDuplicate");
		} else {
			tests.add(appointment.getBooking().getTestVariation().getTest().getTestName());
		}
	}
}
