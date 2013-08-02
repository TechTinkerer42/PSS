package org.ets.ereg.scheduling.response;

import org.ets.ereg.domain.interfaces.scheduling.response.SchedulingResponse;

public class AbstractSchedulingResponse implements SchedulingResponse {
	
	private boolean successful;

	@Override
	public boolean isSuccessful() {
		return successful;
	}
	
	@Override
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
}
