package org.ets.ereg.scheduling.response;

import java.util.Collections;
import java.util.List;

import org.ets.ereg.domain.interfaces.scheduling.response.AvailabilityStatus;
import org.ets.ereg.domain.interfaces.scheduling.response.NonAvailabilityReason;

public class AvailabilityStatusImpl implements AvailabilityStatus {


	private List<NonAvailabilityReason> nonAvailabilityReasons = Collections.emptyList();

	@Override
	public boolean isAvailable() {

		if(nonAvailabilityReasons.isEmpty()){
			return true;
		}

		return false;
	}

	@Override
	public List<NonAvailabilityReason> getNonAvailabilityReasons() {

		return nonAvailabilityReasons;
	}

	@Override
    public NonAvailabilityReason getNonAvailabilityReason() {
	    NonAvailabilityReason resultNonAvailabilityReason = null;
	    List<NonAvailabilityReason> nonAvailabilityReasons = getNonAvailabilityReasons();
	    for (NonAvailabilityReason nonAvailabilityReason : nonAvailabilityReasons) {
	        if(resultNonAvailabilityReason == null){
	            resultNonAvailabilityReason = nonAvailabilityReason;
	        }
	        else if(nonAvailabilityReason.getPriority() > resultNonAvailabilityReason.getPriority()){
	            resultNonAvailabilityReason = nonAvailabilityReason;
	        }
        }
        return resultNonAvailabilityReason;
    }

	@Override
	public void setNonAvailabilityReasons(
			List<NonAvailabilityReason> nonAvailabilityReasons) {

		this.nonAvailabilityReasons = nonAvailabilityReasons;
	}

}

