package org.ets.ereg.profile.accommodation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.profile.accommodation.vo.CustomerAccommodationVO;
import org.ets.ereg.profile.accommodation.vo.CustomerProgramAccommodationsVO;

public interface CustomerAccommodationService {

	List<CustomerAccommodationTest> getActiveAccommodations(Long customerId,
			Date expirationDate);

	List<CustomerAccommodationTest> getAllAccommodations(Long customerId);

	List<CustomerAccommodationTest> getActiveAccommodationsForTest(
			Long customerId, Long testId, String programCode,
			Date expirationDate);

	Map<String,List<CustomerAccommodationTest>> getActiveAccommodationsForTestByDeliveryMethod(
			Long customerId, Long testId, String programCode,
			Date expirationDate);

	void saveCustomerAccommodation(
			CustomerAccommodationTest customerAccommodations);

	void deleteCustomerAccommodation(
			CustomerAccommodationTest customerAccommodations);

	boolean hasApprovedActiveAccommodations(Long customerId, Date expirationDate);

	void deleteCustomerAccommodation(Long customerId, String programCode,
			Long testId, String accommodationTypeCode,String deliveryModeCode);

	List<Test> getTestsWithoutAccommodations(Long customerId,
			String programCode, String deliveryModeCode);

	List<AccommodationType> getAllNotApprovedAccommodations(Long customerId,
			String programCode, String deliveryModeCode);

	void addAccommodations(List<CustomerAccommodationVO> customerAccommodations);
	void updateAccommodations(List<CustomerAccommodationVO> customerAccommodations);

	CustomerAccommodationTest getAccommodation(Long customerId, String programCode,
			Long testId, String accommodationTypeCode,String deliveryModeCode);

	CustomerProgramAccommodationsVO getAllAccommodations(Long customerId,
			String programCode, Long testId,
			String deliveryModeCode,AccommodationStatus accommodationStatus,Date testDate);

	CustomerProgramAccommodationsVO getAllAccommodations(Long customerId,
			String programCode, Long testId,
			String deliveryModeCode,AccommodationStatus accommodationStatus);

    List<CustomerAccommodationTest> getAllActiveAccommodationsByDeliveryMode(
            Long customerId, String programCode, Long testId,
            String deliveryModeCode, Date expirationDate);

    CustomerProgramAccommodationsVO getCustomerAccommodation(Long customerId, String programCode,
			Long testId, String accommodationTypeCode,String deliveryModeCode);


}
