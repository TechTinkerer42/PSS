package org.ets.ereg.domain.interfaces.scheduling.response;

import java.util.List;

public interface AvailabilityStatus {

	boolean isAvailable();
	List<NonAvailabilityReason> getNonAvailabilityReasons();
	void setNonAvailabilityReasons(List<NonAvailabilityReason> nonAvailabilityReasons);
    NonAvailabilityReason getNonAvailabilityReason();

}
