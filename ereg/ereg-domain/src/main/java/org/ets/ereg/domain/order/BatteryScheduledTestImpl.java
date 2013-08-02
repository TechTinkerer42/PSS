package org.ets.ereg.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferImpl;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BTTRY_SCHDLD_TST")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Battery Scheduled Test")
public class BatteryScheduledTestImpl implements BatteryScheduledTest {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "BatteryScheduledTestId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "BatteryScheduledTestId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "BatteryScheduledTestImpl", allocationSize = 50)
    @Column(name = "BTTRY_SCHDLD_TST_ID")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BatterySubscriptionImpl.class)
    @JoinColumn(name = "BTTRY_SBSCRPTN_ID", nullable = false)
	private BatterySubscription batterySubscription;
	
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TestDiscreteOrderItemImpl.class)
    @JoinColumn(name = "ORDER_ITEM_ID", nullable = false)
	private TestDiscreteOrderItem testDiscreteOrderItem;
	
	@Column(name = "RDMD_DTE")
	private Date dateRedeemed;
	
	@Column(name = "INACTV_FLG", nullable = false, columnDefinition = "char(1) default 'N'")
    @Type(type="yes_no")
	private Boolean inactive = false;
	
	@Column(name = "INACTV_RSN_TXT")
	private String inactiveReason;
	
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = OfferImpl.class, optional = true)
    @JoinColumn(name = "OFFER_ID", nullable = true)
	private Offer appliedOffer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BatterySubscription getBatterySubscription() {
		return batterySubscription;
	}

	public void setBatterySubscription(BatterySubscription batterySubscription) {
		this.batterySubscription = batterySubscription;
	}

	public TestDiscreteOrderItem getTestDiscreteOrderItem() {
		return testDiscreteOrderItem;
	}

	public void setTestDiscreteOrderItem(TestDiscreteOrderItem testDiscreteOrderItem) {
		this.testDiscreteOrderItem = testDiscreteOrderItem;
	}

	public Date getDateRedeemed() {
		return dateRedeemed;
	}

	public void setDateRedeemed(Date dateRedeemed) {
		this.dateRedeemed = dateRedeemed;
	}

	public Boolean getInactive() {
		return inactive;
	}

	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}

	public String getInactiveReason() {
		return inactiveReason;
	}

	public void setInactiveReason(String inactiveReason) {
		this.inactiveReason = inactiveReason;
	}

	public Offer getAppliedOffer() {
		return appliedOffer;
	}

	public void setAppliedOffer(Offer appliedOffer) {
		this.appliedOffer = appliedOffer;
	}

}
