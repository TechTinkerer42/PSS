package org.ets.ereg.csr.web.validator.accommodation;

import java.util.Date;

import org.ets.ereg.common.web.form.CustomerAccommodationsForm;
import org.ets.ereg.common.web.form.CustomerAccomodationFormEntry;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("accommodationValidator")
public class AccommodationFormValidatorImpl implements
		AccommodationFormValidator {
	private static final String ACCOMMODATION_EXPIRATION_DATE_REQUIRED = "accommodation.expirationDate.required";
	private static final String ACCOMMODATION_EXPIRATION_DATE_INVALID = "accommodation.expirationDate.invalid";

	@Override
	public void validateAddAccommodations(
			CustomerAccommodationsForm customerAccommodationsForm, Date approvalDate, Errors errors) {
		int i = 0;
		for(CustomerAccomodationFormEntry accommodationEntry: customerAccommodationsForm.getAccommodations()){
			if(!accommodationEntry.isExisting() && (null != accommodationEntry.getAccommodationTypeCode())){
				if(null == accommodationEntry.getExpirationDate()){
					errors.rejectValue("accommodations["+i+"].expirationDate", ACCOMMODATION_EXPIRATION_DATE_REQUIRED);
				}
				else if(approvalDate.after(accommodationEntry.getExpirationDate())){
					errors.rejectValue("accommodations["+i+"].expirationDate", ACCOMMODATION_EXPIRATION_DATE_INVALID);
				}
			}
			i++;
		}

	}
	
	@Override
	public void validateUpdateAccommodations(
			CustomerAccommodationsForm customerAccommodationsForm, Date approvalDate, Errors errors) {
		int i = 0;
		for(CustomerAccomodationFormEntry accommodationEntry: customerAccommodationsForm.getAccommodations()){
			if(null != accommodationEntry.getAccommodationTypeCode()){
				boolean changed = false;
				if(null == accommodationEntry.getOriginalAccommodationTypeValue() && null == accommodationEntry.getAccommodationTypeValue()){
					changed = false;
				}
				else if(null == accommodationEntry.getOriginalAccommodationTypeValue() || null == accommodationEntry.getAccommodationTypeValue()){
					changed = true;
				}
				else{
					changed = !accommodationEntry.getOriginalAccommodationTypeValue().equals(accommodationEntry.getAccommodationTypeValue().trim());
				}
				if(changed){
					if(null == accommodationEntry.getExpirationDate()){
						errors.rejectValue("accommodations["+i+"].expirationDate", ACCOMMODATION_EXPIRATION_DATE_REQUIRED);
					}
					else if(approvalDate.after(accommodationEntry.getExpirationDate())){
						errors.rejectValue("accommodations["+i+"].expirationDate", ACCOMMODATION_EXPIRATION_DATE_INVALID);
					}
				}
				else{
					if(null == accommodationEntry.getExpirationDate()){
						errors.rejectValue("accommodations["+i+"].expirationDate", ACCOMMODATION_EXPIRATION_DATE_REQUIRED);
					}
					else if(accommodationEntry.getOriginalExpirationDate().compareTo(accommodationEntry.getExpirationDate()) != 0){
						if(approvalDate.after(accommodationEntry.getExpirationDate())){
							errors.rejectValue("accommodations["+i+"].expirationDate", ACCOMMODATION_EXPIRATION_DATE_INVALID);
						}
					}
				}
			}
			i++;
		}

	}

}
