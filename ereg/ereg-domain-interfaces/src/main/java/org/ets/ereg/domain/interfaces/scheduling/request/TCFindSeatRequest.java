package org.ets.ereg.domain.interfaces.scheduling.request;

import java.util.List;


public interface TCFindSeatRequest  extends FindSeatRequest{	
	
	List<Long> getTestCenterIds();
	void setTestCenterId(Long testCenterId);
	void setTestCenterIds(List<Long> testCenterIds);
	
}
