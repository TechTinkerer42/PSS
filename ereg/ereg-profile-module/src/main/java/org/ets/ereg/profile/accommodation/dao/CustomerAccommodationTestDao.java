package org.ets.ereg.profile.accommodation.dao;

import java.util.Date;
import java.util.List;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.domain.accommodation.CustomerAccommodationTestImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.test.Test;

public interface CustomerAccommodationTestDao extends Dao<CustomerAccommodationTest>{
		List<CustomerAccommodationTest> getActiveAccommodations(Long customerId,Date testDate);
		List<CustomerAccommodationTest> getAllAccommodations(Long customerId);
		List<CustomerAccommodationTest> getActiveAccommodationsForTest(Long customerId,Long testId,
																					String programCode,
																					Date testDate);

		List<Test> getTestsWithoutAccommodations(Long customerId,
														String programCode, String deliveryModeCode);

		List<AccommodationType> getAllNotApprovedAccommodations(Long customerId,
													String programCode, String deliveryModeCode);

		List<CustomerAccommodationTestImpl> getAllAccommodations(Long customerId,
				String programCode, Long testId,
				String deliveryModeCode,AccommodationStatus accommodationStatus,Date testDate);

        List<CustomerAccommodationTest> getAllActiveAccommodationsByDeliveryMode(
                Long customerId, String programCode, Long testId,
                String deliveryModeCode, Date expirationDate);

        CustomerAccommodationTest getCustomerProgramAccommodationTest(Long customerId,String programCode,Long testId,
				String accommodationTypeCode,String deliveryModeCode);

}
