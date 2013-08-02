package org.ets.ereg.csr.web.validator.appointment;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.ets.ereg.common.util.DateTimeUtil;
import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("tcaAppointmentValidator")
public class AppointmentFormValidatorImpl implements AppointmentFormValidator {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(AppointmentFormValidatorImpl.class);

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

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

	private void validateAppointment(
			AppointmentVO appointment,
			int i,
			Set<String> tests,
			Errors errors) {
		boolean isValidTest = validateTest(appointment, i, errors);
		boolean isValidTestDate = validateTestDate(appointment, i, errors);
		boolean isValidTestTime = validateTestTime(appointment, i, errors);
		boolean isValidTestCenter = validateTestCenter(appointment, i, errors);
		boolean isValidTestType = validateTestType(appointment, i, errors);
		boolean isValidTestLanguage = validateTestLanguage(appointment, i, errors);
		boolean isValidTestForm = validateForm(appointment, i, errors);
		if (isValidTest && isValidTestDate && isValidTestTime
				&& isValidTestCenter && isValidTestType && isValidTestLanguage
				&& isValidTestForm) {
			validateDupAppointment(appointment, i, tests, errors);
		}
	}

	public void validateAppointment(
			AppointmentVO appointment,
			Errors errors) {
		validateTest(appointment, 0, errors);
		validateTestDate(appointment, 0, errors);
		validateTestTime(appointment, 0, errors);
		validateTestCenter(appointment, 0, errors);
		validateTestType(appointment, 0, errors);
		validateTestLanguage(appointment, 0, errors);
		validateForm(appointment, 0, errors);
	}

	private boolean validateTest(AppointmentVO appointment, int i, Errors errors) {
		if ( null == appointment.getBooking().getTestVariation()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST, "schedulenewappointment.testTitleRequired");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateTestDate(AppointmentVO appointment, int i, Errors errors) {
		if (null == appointment.getBooking().getAppointmentDateTime()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_DATE_TIME, "schedulenewappointment.testDateRequired");
			return false;
		} else if (appointment.getBooking().getAppointmentDateTime().before(new DateTime().toDateMidnight().toDate())) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_DATE_TIME, "schedulenewappointment.testDateInvalid");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateTestTime(AppointmentVO appointment, int i, Errors errors) {
		if (null != appointment.getBooking().getTestVariation().getDeliveryModeType()) {
			if (GenericValidator.isBlankOrNull(appointment.getTestTimeHour()) ||
					GenericValidator.isBlankOrNull(appointment.getTestTimeMinute()) ||
					GenericValidator.isBlankOrNull(appointment.getTestTimeAMPM())) {
				errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_TIME, "schedulenewappointment.testTimeRequired");
				return false;
			} else {
				Date testDateTime = appointment.getBooking().getAppointmentDateTime();
				testDateTime = DateTimeUtil.getDateTime(testDateTime,
						appointment.getTestTimeHour(),
						appointment.getTestTimeMinute(),
						appointment.getTestTimeAMPM());
				if (testDateTime.compareTo(DateUtils.addHours(new Date(), 24)) < 0) {
					errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_TIME, "schedulenewappointment.testTime24Hours");
					return false;
				}
			}
		}
		if (!GenericValidator.isBlankOrNull(appointment.getTestTimeHour()) ||
				!GenericValidator.isBlankOrNull(appointment.getTestTimeMinute()) ||
				!GenericValidator.isBlankOrNull(appointment.getTestTimeAMPM())) {
			if (!appointment.getTestTimeHour().matches("(0?[0-9])|(1[0-2])") ||
					!appointment.getTestTimeMinute().matches("(0[0-9])|([1-5][0-9])")) {
				errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_TIME, "schedulenewappointment.testTimeInvalid");
				return false;
			} else if (GenericValidator.isBlankOrNull(appointment.getTestTimeAMPM())) {
				errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_TIME, "schedulenewappointment.testTimeAmpmrequired");
				return false;
			}
		}
		return true;
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
		if (null == appointment.getBooking().getTestVariation().getId().getDeliveryModeCode()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_TYPE, "schedulenewappointment.testTypeRequired");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateTestLanguage(AppointmentVO appointment, int i, Errors errors) {
		if (null == appointment.getBooking().getTestVariation().getId().getLanguageCode()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_LANGUAGE, "schedulenewappointment.testLanguageRequired");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateForm(AppointmentVO appointment, int i, Errors errors) {
		if (appointment.getBooking().getTestVariation().getId().getDeliveryModeCode().equals(DeliveryModeType.PBT) && null == appointment.getBooking().getForm()) {
			errors.rejectValue("appointments[" + i + "]." + AppointmentForm.TEST_FORM, "schedulenewappointment.testFormRequired");
			return false;
		} else {
			return true;
		}
	}

	private void validateDupAppointment(
			AppointmentVO appointment,
			int i,
			Set<String> tests,
			Errors errors) {
		if (tests.contains(appointment.getBooking().getTestVariation().getTest().getTestName())) {
			errors.reject("schedulenewappointment.testDuplicate");
		} else {
			tests.add(appointment.getBooking().getTestVariation().getTest().getTestName());
		}
	}

}
