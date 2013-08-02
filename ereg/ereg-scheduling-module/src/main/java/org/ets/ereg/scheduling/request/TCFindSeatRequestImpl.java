package org.ets.ereg.scheduling.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;

public class TCFindSeatRequestImpl extends FindSeatRequestImpl implements TCFindSeatRequest {

	private List<Long> testCenterIds = Collections.emptyList();
	
	@Override
	public List<Long> getTestCenterIds() {
		return testCenterIds;
	}

	@Override
	public void setTestCenterId(Long testCenterId) {
		testCenterIds = new ArrayList<Long>();
		testCenterIds.add(testCenterId);
	}

	@Override
	public void setTestCenterIds(List<Long> testCenterIds) {
		this.testCenterIds = testCenterIds;		
	}

}
