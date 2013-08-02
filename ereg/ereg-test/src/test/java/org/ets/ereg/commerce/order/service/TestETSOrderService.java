package org.ets.ereg.commerce.order.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.checkout.service.CheckoutService;
import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentInfoImpl;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.ets.ereg.commerce.common.BaseTest;
import org.ets.ereg.commerce.order.dto.AddTestRequestDTO;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.booking.HeldBookingImpl;
import org.ets.ereg.domain.common.GlobalTimeZoneImpl;
import org.ets.ereg.domain.interfaces.commerce.order.AddTestRequest;
import org.ets.ereg.domain.interfaces.commerce.order.ETSTestItemRequest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.common.GlobalTimeZone;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationRoleType;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.scheduling.id.TestCenterProgramId;
import org.ets.ereg.domain.interfaces.model.test.TestStatusType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;
import org.ets.ereg.domain.organization.OrganizationRoleTypeImpl;
import org.ets.ereg.domain.scheduling.AgencyImpl;
import org.ets.ereg.domain.scheduling.TestCenterImpl;
import org.ets.ereg.domain.scheduling.TestCenterProgramImpl;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.domain.test.TestStatusTypeImpl;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.ets.ereg.profile.model.dao.ETSCustomerDao;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;


public class TestETSOrderService extends BaseTest {

	@Resource(name="etsOrderService")
	private ETSOrderService etsOrderService;
	
	@Resource(name="etsCustomerDao")
	private ETSCustomerDao etsCustomerDao;	
	
	
	@Resource(name="blCheckoutService")
	public CheckoutService checkoutService;
	
    @Test
    @Transactional
	public void tesAddTest(){		
		
		ETSCustomer customer = (ETSCustomer) etsCustomerDao.readCustomerById(1L);
		
		Order order = etsOrderService.findCartForCustomer(customer);		
		if(order==null){
			order = etsOrderService.createNewCartForCustomer(customer);
		}
				
		AddTestRequest addTestRequest = new AddTestRequestDTO();
		addTestRequest.setOrderId(order.getId());
		addTestRequest.setPriceOrder(true);
	
		ETSTestItemRequest etsTestItemRequest = new ETSTestItemRequest();
		etsTestItemRequest.setBooking(getBooking());
	
		etsTestItemRequest.getBooking().getTestTakerTestId().setCustomer(customer);
		
		etsTestItemRequest.setProductId(2L);
		etsTestItemRequest.setOrderItemId(1L);
		etsTestItemRequest.setQuantity(1);
		etsTestItemRequest.getItemAttributes().put("TEST_TYPE", "Paper");
		
		addTestRequest.setTestItemRequest(etsTestItemRequest);
		
		try{			
		 order = etsOrderService.addTest(addTestRequest);
		} catch (AddToCartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		
		
		PaymentInfo paymentInfo = getPaymentInfo(order);
			
		Map<PaymentInfo,Referenced> paymentInfoMap = new HashMap<PaymentInfo, Referenced>();
		paymentInfoMap.put(paymentInfo, paymentInfo.createEmptyReferenced());
		
		 try {
			checkoutService.performCheckout(order,paymentInfoMap);
		} catch (CheckoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		
		
		Assert.assertTrue(order.getOrderItems().size()==1);
	}
   
    
    @Test   
	public void testBulkAddTest(){
    	
    	ETSCustomer customer = (ETSCustomer) etsCustomerDao.readCustomerById(1L);
		
		Order order = etsOrderService.findCartForCustomer(customer);		
		if(order==null){
			order = etsOrderService.createNewCartForCustomer(customer);
		}
		
		List<Order> ordrers  = Collections.emptyList();
		try{			
			ordrers = etsOrderService.bulkAddTest(getTestRequests(order));
		} catch (AddToCartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue(ordrers.size()==2);
		
    }
    
    @Test
    /**
     * In order to test this case successfully,
     * this test needs to run after the testAddTest or testBulkAddTest
     * made the below method transactional get out of error
     * org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
     * org.broadleafcommerce.core.order.domain.OrderImpl.orderItems, no session or session was closed
     */
    @Transactional
   	public void testGetTestsFromCart(){
       	
       	ETSCustomer customer = (ETSCustomer) etsCustomerDao.readCustomerById(1L);
   		
   		Order order = etsOrderService.findCartForCustomer(customer);
   		
   		Assert.assertTrue(order!=null);
   		
   		List<OrderItem> ordrerItems = etsOrderService.getTestsFromCart(order);
   		Assert.assertTrue(ordrerItems.size()==2);
   		
     }
    
    @Test
    @Transactional
	public void testClearCart(){
       	
       	ETSCustomer customer = (ETSCustomer) etsCustomerDao.readCustomerById(1L);
   		
   		Order order = etsOrderService.findCartForCustomer(customer);
   		
   		Assert.assertTrue(order!=null);   		
   		try {
			etsOrderService.clearCart(order);
		} catch (RemoveFromCartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PricingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
		
    private List<AddTestRequest> getTestRequests(Order order){
    
    	List<AddTestRequest> addTestRequests = new ArrayList<AddTestRequest>();
    	
		AddTestRequest addTestRequest = new AddTestRequestDTO();
		addTestRequest.setOrderId(order.getId());
		addTestRequest.setPriceOrder(true);
	
		ETSTestItemRequest etsTestItemRequest = new ETSTestItemRequest();
		etsTestItemRequest.setBooking(getBooking());
	
		etsTestItemRequest.setProductId(2L);
		etsTestItemRequest.setOrderItemId(1L);
		etsTestItemRequest.setQuantity(1);
		etsTestItemRequest.getItemAttributes().put("TEST_TYPE", "Paper");
		
		addTestRequest.setTestItemRequest(etsTestItemRequest);
		
		addTestRequests.add(addTestRequest);
		
		
		AddTestRequest addTestRequest1 = new AddTestRequestDTO();
		addTestRequest1.setOrderId(order.getId());
		addTestRequest1.setPriceOrder(true);
	
		ETSTestItemRequest etsTestItemRequest1 = new ETSTestItemRequest();
		etsTestItemRequest1.setBooking(getBooking());
	
		etsTestItemRequest1.setProductId(2L);
		etsTestItemRequest1.setOrderItemId(2L);
		etsTestItemRequest1.setQuantity(1);
		etsTestItemRequest1.getItemAttributes().put("TEST_TYPE", "Computer");		
		addTestRequest1.setTestItemRequest(etsTestItemRequest1);		
		addTestRequests.add(addTestRequest1);
		
		return addTestRequests;
    }
    	
	private Booking getBooking(){		
		Booking booking = new BookingImpl();		
		booking.setAppointmentDateString("");
		booking.setHeldBooking(getHeldBooking(booking));
		booking.setTestTakerTestId(getTestTakerTest(booking));
		booking.setAppointmentDateTime(Calendar.getInstance().getTime());
		booking.setTestVariation(getTestVariation());
		booking.setTimeZoneOffsetQuantity(-5F);
		booking.setTestCenter(getTestCenter());
		return booking;
	}	
	
	private HeldBooking getHeldBooking(Booking booking){
		HeldBooking heldBooking = new HeldBookingImpl();		
		heldBooking.setHoldDuration("30");
		heldBooking.setHoldSeatId("1234");
		heldBooking.setHoldSourceDesc("ereg");
		heldBooking.setLabCode("1");
		heldBooking.setSeatCode("seat");
		heldBooking.setSiteCode("site");		
		heldBooking.setBooking(booking);
		return heldBooking;
	}
	
	private TestCenter getTestCenter(){
		TestCenter testCenter = new TestCenterImpl();
		testCenter.setId(9L);
		testCenter.setActive(true);
		testCenter.setAccessRestricted(false);
		
		GlobalTimeZone timeZone = new GlobalTimeZoneImpl();
		timeZone.setCode("EST");
		timeZone.setDescription("Eastern Time");
		timeZone.setDisplayable(true);
		
		testCenter.setGlobalTimeZone(timeZone);
		testCenter.setExternalTestCenterId("STN15075B");
		
		
		TestCenterProgram testCenterProgram = new TestCenterProgramImpl();
		TestCenterProgramId id = new TestCenterProgramId();
		id.setProgramCode("HSE");
		id.setTestCenterIdId(9L);
		testCenterProgram.setId(id);
		
		Agency agency= new AgencyImpl();
		agency.setActive(true);
		agency.setId(1000L);
		agency.setName("agency");
		OrganizationRoleType roleType = new OrganizationRoleTypeImpl();
		roleType.setCode("AGNCY");
		agency.setRoleType(roleType);
		
		testCenterProgram.setAgency(agency);
		
		
		
		return testCenter;
		
	}
	
	private TestVariation getTestVariation(){
		TestVariation testVariation = new TestVariationImpl();
		TestVarianceId testVarianceId = new TestVarianceId();
		testVarianceId.setDeliveryModeCode("PBT");
		testVarianceId.setTestId(1L);
		testVarianceId.setLanguageCode("EN");
		
		testVariation.setId(testVarianceId);
		return testVariation;
	}
	
	private TestTakerTest getTestTakerTest(Booking booking){
		TestTakerTest testTakerTest = new TestTakerTestImpl();		
		TestStatusType statusType = new TestStatusTypeImpl();
		statusType.setCode("SCDL");
		
		testTakerTest.setTestStatusCode(statusType);
		Set<Booking> bookings = new HashSet<Booking>();
		bookings.add(booking);
		testTakerTest.setBookings(bookings);
		
		return testTakerTest;
	}
	
	private PaymentInfo getPaymentInfo(Order order){
		
		PaymentInfo paymentInfo = new PaymentInfoImpl();
		paymentInfo.setAddress(order.getCustomer().getCustomerAddresses().get(0).getAddress());
		paymentInfo.setReferenceNumber("1");
		
		Map<String,String[]> requestParamMap = new HashMap<String,String[]>();
		requestParamMap.put("pageContext", new String[]{"customerFacing"});
		
		
		requestParamMap.put("billTo_lastName", new String[]{"Pitt"});
		requestParamMap.put("billTo_phoneNumber", new String[]{"1231231234"});
		
		requestParamMap.put("card_expirationYear", new String[]{"2019"});
		requestParamMap.put("shipTo_city", new String[]{"Flushing"});
		requestParamMap.put("amount", new String[]{"15.00"});
		
		requestParamMap.put("card_expirationYear", new String[]{"MsNYFyv4VZssoQJMQVmRRyQ6AeI="});
		requestParamMap.put("billTo_postalCode", new String[]{"11354"});
		requestParamMap.put("billTo_city", new String[]{"Flushing"});
		
		requestParamMap.put("ccCaptureService_reconciliationID", new String[]{"10981596"});
		requestParamMap.put("src_target", new String[]{"customerFacing"});
		requestParamMap.put("billTo_street1", new String[]{"35-01 Union Street"});
		
		requestParamMap.put("orderPage_serialNumber", new String[]{"3642480403712130706433"});
		requestParamMap.put("accountNumberMask", new String[]{"4111110000001111"});
		requestParamMap.put("shipTo_lastName", new String[]{"Pitt"});
		
		requestParamMap.put("paySubscriptionCreateReply_subscriptionID", new String[]{"9997000036158893"});
		requestParamMap.put("orderPage_timestamp", new String[]{"1369411732319"});
		requestParamMap.put("ccCaptureService_authRequestToken", new String[]{"Ahj/7wSRkIRdl7aj+aD0IiGLBy4YtXLZP2cGhjZwFP2cGhjZ9IGbWGJEMmkmWLr4Fn8QJyMhCLsvbUfzQegAjT9z"});
		
		
		requestParamMap.put("merchant_errorURL", new String[]{"http://localhost:8080/ereg-web/secure/cybersource/error"});
		requestParamMap.put("project", new String[]{"12345"});
		requestParamMap.put("src_system_msg_id", new String[]{"BA7F6851443E3A3BB21963BE9719817C_1"});
		
		requestParamMap.put("ccAuthReply_authorizationCode", new String[]{"888888"});
		requestParamMap.put("shipTo_firstName", new String[]{"Brad"});
		requestParamMap.put("reconciliationID", new String[]{"10981596"});
		
		requestParamMap.put("cvnDeclineCodes", new String[]{"S,D,N"});
		requestParamMap.put("merchant_rejectURL", new String[]{"http://localhost:8080/ereg-web/secure/cybersource/decline"});
		requestParamMap.put("billTo_country", new String[]{"US"});
		
		requestParamMap.put("cpsGeneratedReconId", new String[]{"true"});
		requestParamMap.put("ccCaptureService_authRequestID", new String[]{"3694117787940176056442"});
		requestParamMap.put("ccCaptureUsd_amount", new String[]{"15.00"});
		
		requestParamMap.put("shipTo_street1", new String[]{"35-01 Union Street"});
		requestParamMap.put("orderPage_version", new String[]{"4"});
		requestParamMap.put("reasonCode", new String[]{"100"});
		
		requestParamMap.put("modeOfAuthorization", new String[]{"R"});
		requestParamMap.put("purchaseTotals_grandTotalAmount", new String[]{"15.00"});
		requestParamMap.put("decision", new String[]{"ACCEPT"});
		
		requestParamMap.put("currency", new String[]{"USD"});
		requestParamMap.put("purchaseTotals_currency", new String[]{"USD"});
		requestParamMap.put("reasonMessage", new String[]{"The request processed successfully"});
		requestParamMap.put("shipTo_country", new String[]{"US"});
		
		requestParamMap.put("originatingSystem_sessionId", new String[]{"BA7F6851443E3A3BB21963BE9719817C_1"});
		requestParamMap.put("complete", new String[]{"true"});
		requestParamMap.put("invoiceHeader_merchantDescriptorContact", new String[]{"609-734-1112"});
		
		requestParamMap.put("submit", new String[]{"submit1"});
		requestParamMap.put("merchant_timeoutURL", new String[]{"http://localhost:8080/ereg-web/secure/cybersource/error"});
		requestParamMap.put("card_expirationMonth", new String[]{"02"});
		
		requestParamMap.put("invoiceHeader_merchantDescriptor", new String[]{"ETS*This is the produc"});
		requestParamMap.put("billTo_state", new String[]{"NY"});
		requestParamMap.put("merchant_successURL", new String[]{"http://localhost:8080/ereg-web/secure/cybersource/success"});		
		requestParamMap.put("shipTo_postalCode", new String[]{"11354"});
		requestParamMap.put("originatingSystem_code", new String[]{"ERS"});
		
		
		requestParamMap.put("hopType", new String[]{"CFHOP"});
		requestParamMap.put("requestID", new String[]{"3694117803720176056442"});
		requestParamMap.put("card_last4Digits", new String[]{"1111"});		
		requestParamMap.put("src_system", new String[]{"ERS"});
		requestParamMap.put("billTo_firstName", new String[]{"Brad"});
		
		requestParamMap.put("methodOfPayment", new String[]{"Visa"});
		requestParamMap.put("ccCaptureReply_requestDateTime", new String[]{"2013-05-24T16:09:40Z"});
		requestParamMap.put("signedFields", new String[]{"amount,originatingSystem_code,orderPage_serialNumber,originatingSystem_sessionId,purchaseTotals_currency"});		
		requestParamMap.put("card_cardType", new String[]{"001"});
		requestParamMap.put("ccAuthReply_authorizedDateTime", new String[]{"2013-05-24T16:09:40Z"});
		
		
		requestParamMap.put("modeOfReceipt", new String[]{"N"});		
		requestParamMap.put("shipTo_state", new String[]{"NY"});
		
		
		paymentInfo.setRequestParameterMap(requestParamMap);
		
		
		order.getPaymentInfos().add(paymentInfo);
		paymentInfo.setOrder(order);			
		
		paymentInfo.setType(PaymentInfoType.getInstance(PaymentInfoType.CREDIT_CARD.getType()));
			
		paymentInfo.setAmount(new Money(15.00));
		
		return paymentInfo;

	}
	
}
