package org.ets.ereg.scheduling.mock;

import java.util.List;

import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatInfo;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;

public class MockExtendHoldSeatStoreImpl implements ExtendHoldSeatStore {

	@Override
	public void addExtendHold(ExtendHoldSeatRequest extendHoldSeatRequest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeExtendHold(String programCode, Long testId,
			String siteCode, String holdCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ExtendHoldSeatInfo> getAllExtendHoldSeats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeExtendHoldsForCurrentSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeExtendHoldsForSession(String sessionId) {
		// TODO Auto-generated method stub

	}

}
