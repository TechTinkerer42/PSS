package org.ets.ereg.scheduling.vo;

import java.util.List;

import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;

public interface ExtendHoldSeatStore {

	/**
	 * This needs to be called whenever seat is held
	 * @param extendHoldSeatRequest
	 */
	public abstract void addExtendHold(
			ExtendHoldSeatRequest extendHoldSeatRequest);

	/**
	 * Thsi needs to be called whenever seat is released
	 * @param bookingId
	 */
	public abstract void removeExtendHold(String programCode, Long testId, String siteCode, String holdCode);

	public abstract List<ExtendHoldSeatInfo> getAllExtendHoldSeats();

	/**
	 *
	 * @param sessionId
	 */
	public abstract void removeExtendHoldsForCurrentSession();

	public void removeExtendHoldsForSession(String sessionId);

}