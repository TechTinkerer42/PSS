package org.ets.ereg.domain.interfaces.model.booking;

import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface EventOutComeStatus extends ReferenceEntityInterface {	
	public static final String NOT_CHECKED_IN = "NCI";
	public static final String NO_SHOW = "NOS";
	public static final String CHECKED_IN = "CHI";
	public static final String COULD_NOT_TEST = "CNT";
	public void setFormUsedFlag(Boolean formUsedFlag);
	public Boolean getFormUsedFlag();
}
