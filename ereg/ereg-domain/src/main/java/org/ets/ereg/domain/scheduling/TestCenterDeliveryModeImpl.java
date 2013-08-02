package org.ets.ereg.domain.scheduling;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterDeliveryMode;
import org.ets.ereg.domain.interfaces.model.scheduling.id.TestCenterDeliveryModeId;

@Entity
@Table(name = "TST_CNTR_DLVY_MDE")
public class TestCenterDeliveryModeImpl implements TestCenterDeliveryMode {
	
	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	private TestCenterDeliveryModeId testCenterDeliveryModeId = new TestCenterDeliveryModeId();
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=TestCenterImpl.class)
	@JoinColumn(name = "TST_CNTR_ID_NO", nullable = false, insertable = false, updatable = false)
	private TestCenter testCenter;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=DeliveryModeTypeImpl.class)
	@JoinColumn(name = "DLVY_MDE_CDE", nullable = false, insertable = false, updatable = false)
	private DeliveryModeType deliveryMode; 
	
	@Override
	public TestCenterDeliveryModeId getId() {
		return testCenterDeliveryModeId;
	}

	@Override
	public void setId(TestCenterDeliveryModeId id) {
		this.testCenterDeliveryModeId = id;
	}

	@Override
	public DeliveryModeType getDeliveryMode() {
		return deliveryMode;
	}

	@Override
	public void setDeliveryMode(DeliveryModeType deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Override
	public TestCenter getTestCenter() {
		return testCenter;
	}

	@Override
	public void setTestCenter(TestCenter testCenter) {
		this.testCenter = testCenter;
	}
}
