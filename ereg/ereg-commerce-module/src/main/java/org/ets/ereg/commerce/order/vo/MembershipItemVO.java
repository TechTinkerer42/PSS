package org.ets.ereg.commerce.order.vo;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;


public class MembershipItemVO {
	FulfillmentGroupItem  membershipFulfillmentGroupItem;//FullfilmentGroupItem

	public MembershipDiscreteOrderItem getMembershipDiscreteOrderitem() {
		return (MembershipDiscreteOrderItem)membershipFulfillmentGroupItem.getOrderItem();
	}

	/*public void setMembershipDiscreteOrderitem(
			FulfillmentGroupItem membershipDiscreteOrderitem) {
		this.membershipDiscreteOrderitem = membershipDiscreteOrderitem;
	}*/
	
	public MembershipItemVO(FulfillmentGroupItem membershipFulfillmentGroupItem) {
		this.membershipFulfillmentGroupItem=membershipFulfillmentGroupItem;		
	}

	public FulfillmentGroupItem getMembershipFulfillmentGroupItem() {
		return membershipFulfillmentGroupItem;
	}

	public void setMembershipFulfillmentGroupItem(
			FulfillmentGroupItem membershipFulfillmentGroupItem) {
		this.membershipFulfillmentGroupItem = membershipFulfillmentGroupItem;
	}
	
	public Agency getAgency(){
		return getMembershipDiscreteOrderitem().getAgency();
	}
}
