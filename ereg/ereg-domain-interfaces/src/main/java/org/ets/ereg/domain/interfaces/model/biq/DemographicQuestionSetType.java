package org.ets.ereg.domain.interfaces.model.biq;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface DemographicQuestionSetType extends ReferenceEntityInterface,
		Serializable {
	public static String BIQ_TYPE_PROFILE="PRFL";
	public static String BIQ_TYPE_TEST="TST";

}
