package org.ets.ereg.commerce.order.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.broadleafcommerce.core.order.domain.Order;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShoppingCartVO {
	private static Logger log = LoggerFactory.getLogger(ShoppingCartVO.class);
	
	private Order order;
	

	private ETSCustomer customer;
	/**
	 * agencies supporting battery
	 */
	private List<AgencyVO> agencies = new ArrayList<AgencyVO>();
	//private List<BatterySubscription> openBatteries =new ArrayList<BatterySubscription>();
	private List<TestItemVO> tests = new ArrayList<TestItemVO>();
	//private List<MembershipItemVO> membership=new ArrayList<MembershipItemVO>();
    //private List<BatteryItemVO> batteryItems=new ArrayList<BatteryItemVO>();
	private BigDecimal totalAmount;
	private int totalQuantity;
	
	
	public AgencyVO getAgency(Long agencyId){
		for(AgencyVO agency : agencies){
			if(agency.getAgency().getId().equals(agencyId)){
				return agency;
			}
		}
		return null;
	}
	
	
	
	public List<Booking> getAllBookings(){
		List<Booking> bookings=new ArrayList<Booking>();
		for(AgencyVO agency : agencies){
			bookings.addAll(agency.getAllBookings());
		}
		for(TestItemVO testItem:this.getTests()){			
			bookings.add(testItem.getBooking());
		}
		return bookings;
	}
	
	public List<TestItemVO> getTests() {
		return tests;
	}
	public void setTests(List<TestItemVO> tests) {
		this.tests = tests;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
    public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	/*public boolean isPhysicalShipment() {
		for (TestItemVO t : tests) {
			if ((ETSFulfillmentType.PHYSICAL_PICKUP.equals(t
					.getTestFulfillmentGroupItem().getFulfillmentGroup()
					.getType()))
					|| (ETSFulfillmentType.PHYSICAL_PICKUP_OR_SHIP.equals(t
							.getTestFulfillmentGroupItem()
							.getFulfillmentGroup().getType()))
							|| (ETSFulfillmentType.PHYSICAL_SHIP.equals(t
									.getTestFulfillmentGroupItem()
									.getFulfillmentGroup().getType()))) {
				return true;
			}
			
			

		}
		return false;
	}*/
	
	/**
	 * This returns the list of agencies which does not have an open battery.
	 * Open battery is a non-expired and non-consumed battery purchased in the past.
	 * @return
	 */
	public Set<Agency> getAllAgenciesWithoutOpenBattery(){
		Set<Agency> agenciesWithoutBattery=new HashSet<Agency>();
		
		for(AgencyVO agencyVO: agencies){
			if(agencyVO.getBatterySubscription() == null){
				agenciesWithoutBattery.add(agencyVO.getAgency());
			}
		}
		return agenciesWithoutBattery;
	}
	
	/**
	 * This method returns the tests which will use a battery purchased in the past.
	 */
	public List<TestItemVO> getTestsWithPastBattery(){
		List<TestItemVO> testsWithPastBattery = new ArrayList<TestItemVO>();
		for(AgencyVO agencyVO: agencies){
			if(agencyVO.getBatterySubscription() != null){
				testsWithPastBattery.addAll(agencyVO.getTests());
			}
		}
		return testsWithPastBattery;
	}
	
	/*public int getMaxRetakes(BatteryProduct batteryProduct){
		return batteryProduct.getTotalTakes() -1;
	}*/
	
	
	/**
	 * This returns the list of tests which has an open battery but cannot be used for this test.
	 * @return
	 */
	/*public List<TestItemVO> getTestsChargedUnderOpenBattery(){
		List<TestItemVO> testsChargedUnderOpenBattery = new ArrayList<TestItemVO>();
		for(TestItemVO test:tests){
			if(test.getEligibleBattery() == null && !test.canPresentBatteryOption(openBatteries)){
				testsChargedUnderOpenBattery.add(test);
			}
		}
		return testsChargedUnderOpenBattery;
	}*/
	
	// This can be implemented by identify tests where agency has battery subscript and test item does not have battery subscription
	/*public List<TestItemVO> getAllTestsNotPresentingBatteryOption(){
		Set<Agency> allAgenciesWithoutBattery=getAllAgenciesWithoutOpenBattery();
		List<TestItemVO> testsWithoutBatteryAgencies=new ArrayList<TestItemVO>();
		List<TestItemVO> testsNotInWithoutBatteryAgencies=new ArrayList<TestItemVO>();
		for(Agency agency:allAgenciesWithoutBattery){
			testsWithoutBatteryAgencies.addAll(getTestsForAgency(agency.getId().toString()));
		}
		if(testsWithoutBatteryAgencies.isEmpty()){
			return tests;
		}else{
			for(TestItemVO test:tests){
				if(!testsWithoutBatteryAgencies.contains(test))
					testsNotInWithoutBatteryAgencies.add(test);
			}
			return testsNotInWithoutBatteryAgencies;
		}
	}*/

	
	/*private Set<Agency> getAllAgenciesForTestsIncart() {
		Set<Agency> allAgencies= new HashSet<Agency>();
		for(TestItemVO test:tests){
			Agency agency = test.getAgency();
			if(agency != null){
				allAgencies.add(agency);
			}
		}
		return allAgencies;
	}*/
	

	public List<TestItemVO> getTestsForAgency(String agencyId){
		List<TestItemVO> agencySpecificTests=new ArrayList<TestItemVO>();
		log.debug("Getting tests for all agency's with id:{}",agencyId);
		for(TestItemVO test:tests){
			log.debug("Agency Id of the test:{}",test.getAgency());
			if(test.getAgency() != null && test.getAgency().getId().equals(Long.parseLong(agencyId))){
				agencySpecificTests.add(test);
			}
		}
		return agencySpecificTests;
	}
	
	
	/*public TestItemVO getOneTestFromAgency(String agencyId){
		List<TestItemVO> testsFromAgency=getTestsForAgency(agencyId);
		return testsFromAgency.isEmpty()?null: testsFromAgency.get(0);
	}*/
	
	
	/*public BatteryDiscreteOrderItem getBatteryDiscreteOrderItem(String agencyId){
		for(BatteryItemVO batteryItem:batteryItems){
			if(batteryItem.getAgency().getId().equals(Long.parseLong(agencyId))){
				return batteryItem.getBatteryDiscreteOrderitem();
			}
		}
		return null;
	}*/
	
	
	/*public Long getBatteryDiscreteOrderItemId(String agencyId){
		for(BatteryItemVO batteryItem:batteryItems){
			if(batteryItem.getAgency().getId().equals(Long.parseLong(agencyId))){
				return batteryItem.getBatteryDiscreteOrderitem().getId();
			}
		}
		return null;
	}*/
	
	/*public BatteryItemVO getBatteryrItem(String agencyId){
		for(BatteryItemVO batteryItem:batteryItems){
			if(batteryItem.getAgency().getId().equals(Long.parseLong(agencyId))){
				return batteryItem;
			}
		}
		return null;
	}*/
	
	/*public boolean isBatteryInCart(String agencyId){
		 return (null==getBatteryDiscreteOrderItem(agencyId))? false:true;
	}*/
	
	public ETSCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ETSCustomer customer) {
		this.customer = customer;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<AgencyVO> getAgencies() {
		return agencies;
	}

	public void setAgencies(List<AgencyVO> agencies) {
		this.agencies = agencies;
		
	}

	public List<MembershipItemVO> getMembershipItemsInCart() {
		List<MembershipItemVO> memberships=new ArrayList<MembershipItemVO>();
		 for(AgencyVO agency:agencies){
			 if(agency.getMembershipItem()!=null){
				 memberships.add(agency.getMembershipItem());
			 }
		 }
		 return memberships;
	}
	
	public List<BatteryItemVO> getBatteryItemsInCart(){
		List<BatteryItemVO> batteriesInCart=new ArrayList<BatteryItemVO>();
		for(AgencyVO agency : agencies){
			if(agency.getBatteryItem()!=null){
				batteriesInCart.add(agency.getBatteryItem());
			}
		}
		return batteriesInCart;
	}

}
