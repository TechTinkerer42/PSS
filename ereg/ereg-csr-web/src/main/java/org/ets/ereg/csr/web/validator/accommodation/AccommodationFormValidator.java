package org.ets.ereg.csr.web.validator.accommodation;

import java.util.Date;

import org.ets.ereg.common.web.form.CustomerAccommodationsForm;
import org.springframework.validation.Errors;

public interface AccommodationFormValidator {
	void validateAddAccommodations(CustomerAccommodationsForm accommodationsForm, Date approvalDate, Errors errors);
	void validateUpdateAccommodations(CustomerAccommodationsForm accommodationsForm, Date approvalDate, Errors errors);
}
