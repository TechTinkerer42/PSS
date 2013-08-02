package org.ets.ereg.session.facade.test.commerce.service;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.ets.ereg.commerce.order.dto.AddTestRequestDTO;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.domain.interfaces.commerce.order.AddTestRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(defaultRollback = true)
//@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestETSOrderService {

	private static Logger logger = LoggerFactory.getLogger(TestETSOrderService.class);
	
//	private static Mockery mockingContext = new Mockery();
	
//	@Resource(name="etsOrderService")
//	private ETSOrderService etsOrderService; // = new ETSOrderServiceImpl();
	
//	@BeforeClass
//	public static void setup() {
//		setupMockObjects();		
//	}
	
//	private static void setupMockObjects() {
//		
//	}
	
//	@Test
//	public void tesAddTest(){
//		
//		AddTestRequest addTestRequest = new AddTestRequestDTO();
//		try {
//			etsOrderService.addTest(addTestRequest);
//		} catch (AddToCartException e) {
//			// TODO Auto-generated catch block
//			
//		}
//	}
}
