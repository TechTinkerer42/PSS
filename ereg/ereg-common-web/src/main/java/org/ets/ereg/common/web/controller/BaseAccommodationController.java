package org.ets.ereg.common.web.controller;

import java.util.List;

import javax.annotation.Resource;


import org.ets.ereg.common.business.service.AccommodationsService;
import org.ets.ereg.common.business.service.ProgramService;
import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.profile.accommodation.service.CustomerAccommodationService;
import org.ets.ereg.profile.accommodation.vo.CustomerTestAccommodationsVO;


public class BaseAccommodationController {

	@Resource(name = "accommodationsService")
	private AccommodationsService accommodationService;

	@Resource(name = "customerAccommodationService")
	private CustomerAccommodationService customerAccommodationService;


	@Resource(name = "programService")
	private ProgramService programService;

	public List<DeliveryModeType> getAllTestTypes() {

		return accommodationService
				.getAccommodationDeliveryMethods(ProgramContextHolder
						.getProgramCode());
	}


	public List<Test> getAllTests() {
		return programService
				.getAllTests(ProgramContextHolder.getProgramCode());
	}

	public AccommodationStatus[] getAllAccomodationStatus() {
		return AccommodationStatus.values();
	}


	public List<CustomerTestAccommodationsVO> getAccommodations(
			Long customerIdLong, String programCode, Long testId,
			String deliveryMode, AccommodationStatus accommodationStatus) {
		return customerAccommodationService
				.getAllAccommodations(customerIdLong,
						programCode,
						testId,
						deliveryMode,
						accommodationStatus).getCustomerTestAccommodations();

	}
}
