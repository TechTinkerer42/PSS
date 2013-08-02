package org.ets.ereg.commerce.order.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

/**
 * This is a value object to represent battery information for a given agency.
 * Any test product which is not associated to a battery product would go in the ShoppingCartVO directly. 
 * @author LPRAKASH
 *
 */
public class AgencyVO {

	private Agency agency;
	private List<TestItemVO> tests = new ArrayList<TestItemVO>();
	private BatteryItemVO batteryItem;
	private BatterySubscription batterySubscription;
	private MembershipItemVO membershipItem;
	
	public AgencyVO(Agency agency){
		this.agency = agency;
	}
	
	public List<Booking> getAllBookings(){
		List<Booking> bookings=new ArrayList<Booking>();
		for(TestItemVO testItem:this.getTests()){			
			bookings.add(testItem.getBooking());
		}
		return bookings;
	}
	
	public BatteryProduct getBatteryProduct(){
		TestItemVO firstTest = tests.get(0);
		if(firstTest != null){
			return firstTest.getBatteryProduct();
		}
		return null;
	}
	
	public BigDecimal getTestPrice(){
		TestItemVO firstTest = tests.get(0);
		if(firstTest != null){
			return firstTest.getPrice();
		}
		return null;
	}

	public List<TestItemVO> getTests() {
		return tests;
	}
	public void setTests(List<TestItemVO> tests) {
		this.tests = tests;
	}
	public BatteryItemVO getBatteryItem() {
		return batteryItem;
	}
	public void setBatteryItem(BatteryItemVO batteryItem) {
		this.batteryItem = batteryItem;
	}
	public BatterySubscription getBatterySubscription() {
		return batterySubscription;
	}
	public void setBatterySubscription(BatterySubscription batterySubscription) {
		this.batterySubscription = batterySubscription;
	}
	public MembershipItemVO getMembershipItem() {
		return membershipItem;
	}
	public void setMembershipItem(MembershipItemVO membershipItem) {
		this.membershipItem = membershipItem;
	}
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
}
