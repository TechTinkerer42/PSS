package org.ets.ereg.domain.interfaces.model.scheduling;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.scheduling.id.TestCenterDeliveryModeId;

public interface TestCenterDeliveryMode extends Serializable {
	TestCenterDeliveryModeId getId();
	void setId(TestCenterDeliveryModeId id);
	DeliveryModeType getDeliveryMode();
	void setDeliveryMode(DeliveryModeType deliveryMode);
	TestCenter getTestCenter();
	void setTestCenter(TestCenter testCenter);
}
