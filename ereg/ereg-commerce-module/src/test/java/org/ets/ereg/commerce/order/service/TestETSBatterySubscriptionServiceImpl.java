package org.ets.ereg.commerce.order.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.ets.ereg.commerce.order.dao.BatteryScheduledTestDao;
import org.ets.ereg.commerce.order.dao.BatterySubscriptionDao;
import org.ets.ereg.commerce.order.util.BatteryStatusEnum;
import org.ets.ereg.domain.catalog.BatteryProductImpl;
import org.ets.ereg.domain.catalog.BatteryProductItemImpl;
import org.ets.ereg.domain.catalog.ETSProductImpl;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.order.BatteryDiscreteOrderItemImpl;
import org.ets.ereg.domain.order.BatteryScheduledTestImpl;
import org.ets.ereg.domain.order.BatterySubscriptionImpl;
import org.ets.ereg.domain.order.TestDiscreteOrderItemImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/*@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })*/

@RunWith(JMock.class)
public class TestETSBatterySubscriptionServiceImpl {
	
	private static Mockery mockingContext = new Mockery();

	private static ETSBatterySubscriptionService mockedEtsBatterySubscriptionService = new ETSBatterySubscriptionServiceImpl();    
    
/*	private static CustomerService mockCustomerService;
	
	private static BatteryScheduledTestDao mockBatteryScheduledTestDao;*/
	
	private static BatterySubscriptionDao mockedBatterySubscriptionDao;

	private static BatteryScheduledTestDao mockedBatteryScheduledTestDao;
    
    /*@Resource(name="agencyService")
	private Agency agencyService;*/
    
    
    @BeforeClass
	public static void setup() {
    	
    	
    	mockedBatterySubscriptionDao = mockingContext.mock(BatterySubscriptionDao.class);
    	mockedBatteryScheduledTestDao = mockingContext.mock(BatteryScheduledTestDao.class);
    	
    	mockedEtsBatterySubscriptionService.setBatterySubscriptionDao(mockedBatterySubscriptionDao);
    	mockedEtsBatterySubscriptionService.setBatteryScheduledTestDao(mockedBatteryScheduledTestDao);

    }
    
	@Test
	public void testReadAllClosedBatteriesForCustomer(){
    	
    	
    	final Customer customer = new CustomerImpl();
    	
    	//Set expected battery subscription 
    	List<BatterySubscription> expectedBatterySubscriptions = new ArrayList<BatterySubscription>();
    	
    	//set return battery subscription
    	final List<BatterySubscription> batterySubscriptions = new ArrayList<BatterySubscription>();
    	BatterySubscription batterySubscription = new BatterySubscriptionImpl();
    	//subscriptionId to compare with actual
    	batterySubscription.setId(1L);
    	
    	
    	BatteryDiscreteOrderItem batteryDiscreteOrderItem = new BatteryDiscreteOrderItemImpl();
    	BatteryProductImpl batteryProductImpl = new BatteryProductImpl();
    	batteryDiscreteOrderItem.setProduct(batteryProductImpl);
    	List<BatteryProductItem> batteryProductItems = new ArrayList<BatteryProductItem>();
    	BatteryProductItem batteryProductItem = new BatteryProductItemImpl();
    	//setting testid of test in subscriptions
    	batteryProductItem.setId(1L);    	
    	batteryProductItems.add(batteryProductItem);
    	batteryProductImpl.setBatteryProductItems(batteryProductItems);
    	
    	Calendar yesterday = Calendar.getInstance();
    	yesterday.add(Calendar.DATE, -1);
    	Calendar tomorrow = Calendar.getInstance();
    	tomorrow.add(Calendar.DATE, 1);
    	Calendar today = Calendar.getInstance();
    	Calendar yearBefore = Calendar.getInstance();
    	yearBefore.add(Calendar.YEAR, -1);
    	
    	//Set battery expiration date
    	batteryDiscreteOrderItem.setEndDate(today.getTime());
    	batteryDiscreteOrderItem.setTotalTakes(3);
    	batterySubscription.setBatteryDiscreteOrderItem(batteryDiscreteOrderItem);
    	//Set total takes for each test this subscriptions
    	batterySubscription.getBatteryProduct().setTotalTakes(3);
    	Sku defaultSku = new SkuImpl();
    	batterySubscription.getBatteryProduct().setDefaultSku(defaultSku);
    	batterySubscription.getBatteryProduct().setActiveStartDate(yearBefore.getTime());
    	batterySubscription.getBatteryProduct().setActiveEndDate(tomorrow.getTime());
    	//add to dao returned subscriptions
    	batterySubscriptions.add(batterySubscription);    	
    	//above battery subscription is expected
    	expectedBatterySubscriptions.add(batterySubscription);
    	
    	
    	
    	
    	//Return scheduled tests
    	List<BatteryScheduledTest> tempBatteryScheduledTests = new ArrayList<BatteryScheduledTest>(10);
    	BatteryScheduledTest batteryScheduledTest = new BatteryScheduledTestImpl();
    	batteryScheduledTest.setId(1L);
    	tempBatteryScheduledTests.add(batteryScheduledTest);
    	final List<BatteryScheduledTest> batteryScheduledTests = tempBatteryScheduledTests;
    	
    	
    	mockingContext.checking(new Expectations() {
			{
				 oneOf(mockedBatterySubscriptionDao).readBatterySubscriptionsForCustomer(customer);
				 will(returnValue(batterySubscriptions));
		    } });
    	
    	mockingContext.checking(new Expectations() {
			{
				 never(mockedBatteryScheduledTestDao).readScheduledTestsForBatterySubscription(new BatterySubscriptionImpl());
				 will(returnValue(batteryScheduledTests));
		    } });
    	
    	List<BatterySubscription> actualBatterySubscriptions  = mockedEtsBatterySubscriptionService.readAllClosedBatteriesForCustomer(customer);

    	Assert.assertTrue(expectedBatterySubscriptions.equals(actualBatterySubscriptions));
    	
    	mockingContext.assertIsSatisfied();
    	
	}
	
	
	
	@Test
	public void testReadAllExpiredBatteriesForCustomer(){
    	
    	
    	final Customer customer = new CustomerImpl();
    	
    	//Set expected battery subscription 
    	List<BatterySubscription> expectedBatterySubscriptions = new ArrayList<BatterySubscription>();
    	
    	//set return battery subscription
    	final List<BatterySubscription> batterySubscriptions = new ArrayList<BatterySubscription>();
    	BatterySubscription batterySubscription = new BatterySubscriptionImpl();
    	//subscriptionId to compare with actual
    	batterySubscription.setId(1L);
    	
    	BatteryDiscreteOrderItem batteryDiscreteOrderItem = new BatteryDiscreteOrderItemImpl();
    	
    	Calendar yesterday = Calendar.getInstance();
    	yesterday.add(Calendar.DATE, -1);
    	
    	//Set battery expiration date
    	batteryDiscreteOrderItem.setEndDate(yesterday.getTime());
    	batterySubscription.setBatteryDiscreteOrderItem(batteryDiscreteOrderItem);
    	
    	//add to dao returned subscriptions
    	batterySubscriptions.add(batterySubscription);    	
    	//above battery subscription is expected
    	expectedBatterySubscriptions.add(batterySubscription);
    	
    	mockingContext.checking(new Expectations() {
		{
			 oneOf(mockedBatterySubscriptionDao).readBatterySubscriptionsForCustomer(customer);
			 will(returnValue(batterySubscriptions));
	    } });
    	
    	
    	List<BatterySubscription> actualBatterySubscriptions  = mockedEtsBatterySubscriptionService.readAllExpiredBatteriesForCustomer(customer);
    	Assert.assertTrue(expectedBatterySubscriptions.equals(actualBatterySubscriptions));
    	
    	mockingContext.assertIsSatisfied();
    	
	}

	
	@Test
	public void testReadAllBatteriesForCustomerByStatus(){
    	
    	
    	final Customer customer = new CustomerImpl();
    	
    	
    	Calendar yesterday = Calendar.getInstance();
    	yesterday.add(Calendar.DATE, -1);
    	Calendar tomorrow = Calendar.getInstance();
    	tomorrow.add(Calendar.DATE, 1);
    	Calendar today = Calendar.getInstance();
    	Calendar yearBefore = Calendar.getInstance();
    	yearBefore.add(Calendar.YEAR, -1);
    	
    	//Set expected battery subscription 
    	Map<String,List<BatterySubscription>> expectedBatterySubscriptionsByStatus = new HashMap<String,List<BatterySubscription>>();
    	
    	//set return battery subscription
    	final List<BatterySubscription> batterySubscriptions = new ArrayList<BatterySubscription>();
    	final List<BatterySubscription> expiredBatterySubscriptions = new ArrayList<BatterySubscription>();
    	BatterySubscription batterySubscription = new BatterySubscriptionImpl();
    	//subscriptionId to compare with actual
    	batterySubscription.setId(1L);
    	
    	
    	BatteryDiscreteOrderItem batteryDiscreteOrderItem = new BatteryDiscreteOrderItemImpl();
    	BatteryProductImpl batteryProductImpl = new BatteryProductImpl();
    	batteryDiscreteOrderItem.setProduct(batteryProductImpl);
    	List<BatteryProductItem> batteryProductItems = new ArrayList<BatteryProductItem>();
    	BatteryProductItem batteryProductItem = new BatteryProductItemImpl();
    	//setting testid of test in subscriptions
    	batteryProductItem.setId(1L);    	
    	batteryProductItems.add(batteryProductItem);
    	batteryProductImpl.setBatteryProductItems(batteryProductItems);
    	
    	//Set battery expiration date
    	batteryDiscreteOrderItem.setEndDate(yesterday.getTime());
    	batteryDiscreteOrderItem.setTotalTakes(3);
    	batterySubscription.setBatteryDiscreteOrderItem(batteryDiscreteOrderItem);
    	//Set total takes for each test this subscriptions
    	batterySubscription.getBatteryProduct().setTotalTakes(3);
    	Sku defaultSku = new SkuImpl();
    	batterySubscription.getBatteryProduct().setDefaultSku(defaultSku);
    	batterySubscription.getBatteryProduct().setActiveStartDate(yearBefore.getTime());
    	batterySubscription.getBatteryProduct().setActiveEndDate(tomorrow.getTime());
    	//add to dao returned subscriptions
    	expiredBatterySubscriptions.add(batterySubscription);    	
    	//above battery subscription is expected
    	batterySubscriptions.addAll(expiredBatterySubscriptions);
    	expectedBatterySubscriptionsByStatus.put(BatteryStatusEnum.EXPIRED.getCode(),expiredBatterySubscriptions);
    	
    	
    	//do open battery now 
    	final List<BatterySubscription> openBatterySubscriptions = new ArrayList<BatterySubscription>();
    	batterySubscription = new BatterySubscriptionImpl();
    	//subscriptionId to compare with actual
    	batterySubscription.setId(1L);
    	
    	
    	batteryDiscreteOrderItem = new BatteryDiscreteOrderItemImpl();
    	batteryProductImpl = new BatteryProductImpl();
    	batteryDiscreteOrderItem.setProduct(batteryProductImpl);
    	batteryProductItems = new ArrayList<BatteryProductItem>();
    	batteryProductItem = new BatteryProductItemImpl();
    	//setting testid of test in subscriptions
    	batteryProductItem.setId(1L);
    	ETSProductImpl etsProductImpl = new ETSProductImpl();
    	etsProductImpl.setId(1L);
    	batteryProductItem.setEligibleProduct(etsProductImpl);
    	batteryProductItems.add(batteryProductItem);
    	batteryProductImpl.setBatteryProductItems(batteryProductItems);
    	
   	
    	//Set battery expiration date
    	batteryDiscreteOrderItem.setEndDate(tomorrow.getTime());
    	batteryDiscreteOrderItem.setTotalTakes(4);
    	batterySubscription.setBatteryDiscreteOrderItem(batteryDiscreteOrderItem);
    	//Set total takes for each test this subscriptions
    	batterySubscription.getBatteryProduct().setTotalTakes(4);
    	defaultSku = new SkuImpl();
    	batterySubscription.getBatteryProduct().setDefaultSku(defaultSku);
    	batterySubscription.getBatteryProduct().setActiveStartDate(yearBefore.getTime());
    	batterySubscription.getBatteryProduct().setActiveEndDate(tomorrow.getTime());
    	//add to dao returned subscriptions
    	openBatterySubscriptions.add(batterySubscription);    	
    	//above battery subscription is expected
    	batterySubscriptions.addAll(openBatterySubscriptions);
    	expectedBatterySubscriptionsByStatus.put(BatteryStatusEnum.OPEN.getCode(),openBatterySubscriptions);
    	
    	
    	
    	//do close battery now 
    	final List<BatterySubscription> closeBatterySubscriptions = new ArrayList<BatterySubscription>();
    	batterySubscription = new BatterySubscriptionImpl();
    	//subscriptionId to compare with actual
    	batterySubscription.setId(1L);
    	
    	
    	batteryDiscreteOrderItem = new BatteryDiscreteOrderItemImpl();
    	batteryProductImpl = new BatteryProductImpl();
    	batteryDiscreteOrderItem.setProduct(batteryProductImpl);
    	batteryProductItems = new ArrayList<BatteryProductItem>();
    	batteryProductItem = new BatteryProductItemImpl();
    	//setting testid of test in subscriptions
    	batteryProductItem.setId(1L);
    	etsProductImpl = new ETSProductImpl();
    	etsProductImpl.setId(1L);
    	batteryProductItem.setEligibleProduct(etsProductImpl);
    	batteryProductItems.add(batteryProductItem);
    	batteryProductImpl.setBatteryProductItems(batteryProductItems);
    	
   	
    	//Set battery expiration date
    	batteryDiscreteOrderItem.setEndDate(tomorrow.getTime());
    	batteryDiscreteOrderItem.setTotalTakes(3);
    	batterySubscription.setBatteryDiscreteOrderItem(batteryDiscreteOrderItem);
    	//Set total takes for each test this subscriptions
    	batterySubscription.getBatteryProduct().setTotalTakes(3);
    	defaultSku = new SkuImpl();
    	batterySubscription.getBatteryProduct().setDefaultSku(defaultSku);
    	batterySubscription.getBatteryProduct().setActiveStartDate(yearBefore.getTime());
    	batterySubscription.getBatteryProduct().setActiveEndDate(tomorrow.getTime());
    	//add to dao returned subscriptions
    	closeBatterySubscriptions.add(batterySubscription);    	
    	//above battery subscription is expected
    	batterySubscriptions.addAll(closeBatterySubscriptions);
    	expectedBatterySubscriptionsByStatus.put(BatteryStatusEnum.CLOSED.getCode(),closeBatterySubscriptions);
    	expectedBatterySubscriptionsByStatus.put(BatteryStatusEnum.CANCELED.getCode(),new ArrayList<BatterySubscription>());
    	

    	
    	//Return scheduled tests
    	List<BatteryScheduledTest> closedBatteryScheduledTests = new ArrayList<BatteryScheduledTest>(10);
    	BatteryScheduledTest batteryScheduledTest = new BatteryScheduledTestImpl();
    	batteryScheduledTest.setId(1L);
    	TestDiscreteOrderItem testDiscreteOrderItem = new TestDiscreteOrderItemImpl();
    	ETSProduct etsProduct = new ETSProductImpl();
    	etsProduct.setId(1L);
    	testDiscreteOrderItem.setProduct(etsProduct);
    	batteryScheduledTest.setTestDiscreteOrderItem(testDiscreteOrderItem);
    	closedBatteryScheduledTests.add(batteryScheduledTest);
    	
    	batteryScheduledTest = new BatteryScheduledTestImpl();
    	batteryScheduledTest.setId(1L);
    	testDiscreteOrderItem = new TestDiscreteOrderItemImpl();
    	etsProduct = new ETSProductImpl();
    	etsProduct.setId(1L);
    	testDiscreteOrderItem.setProduct(etsProduct);
    	batteryScheduledTest.setTestDiscreteOrderItem(testDiscreteOrderItem);
    	closedBatteryScheduledTests.add(batteryScheduledTest);
    	
    	batteryScheduledTest = new BatteryScheduledTestImpl();
    	batteryScheduledTest.setId(1L);
    	testDiscreteOrderItem = new TestDiscreteOrderItemImpl();
    	etsProduct = new ETSProductImpl();
    	etsProduct.setId(1L);
    	testDiscreteOrderItem.setProduct(etsProduct);
    	batteryScheduledTest.setTestDiscreteOrderItem(testDiscreteOrderItem);
    	closedBatteryScheduledTests.add(batteryScheduledTest);
    	
    	final List<BatteryScheduledTest> daosClosedbatteryScheduledTests = closedBatteryScheduledTests;
    	
    	mockingContext.checking(new Expectations() {
			{
				 oneOf(mockedBatterySubscriptionDao).readBatterySubscriptionsForCustomer(customer);
				 will(returnValue(batterySubscriptions));
				 
				 for(final BatterySubscription batterySubscription:batterySubscriptions){
					 
					 mockingContext.checking(new Expectations() {
							{
								ignoring(mockedBatteryScheduledTestDao).readScheduledTestsForBatterySubscription(batterySubscription);;
								//oneOf(mockedBatteryScheduledTestDao).readScheduledTestsForBatterySubscription(batterySubscription);
								 will(returnValue(daosClosedbatteryScheduledTests));
						    } });
				 }
		    } 
		});
    	
    	
    	
    	Map<String,List<BatterySubscription>> actualBatterySubscriptions  = mockedEtsBatterySubscriptionService.readAllBatteriesForCustomerByStatus(customer);

        Assert.assertTrue(actualBatterySubscriptions.equals(expectedBatterySubscriptionsByStatus));
        
    	mockingContext.assertIsSatisfied();
    	
	}
	

}
