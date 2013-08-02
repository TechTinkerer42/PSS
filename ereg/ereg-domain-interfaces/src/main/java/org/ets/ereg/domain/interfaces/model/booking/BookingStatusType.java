package org.ets.ereg.domain.interfaces.model.booking;

import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface BookingStatusType extends ReferenceEntityInterface {
	public static final String RESERVED="RSRVD";
	public static final String CANCELED="CNCL";	
	public static final String HELD="HELD";
	public static final String PENDING_CANCELLATION="PCNCL";
	public static final String RELEASED="RLSD";
}
