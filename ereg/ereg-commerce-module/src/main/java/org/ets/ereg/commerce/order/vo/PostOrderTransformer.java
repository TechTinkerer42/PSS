package org.ets.ereg.commerce.order.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("postOrderTransformer")
public class PostOrderTransformer extends ShoppingCartTransformer {
	private static Logger log = LoggerFactory
			.getLogger(PostOrderTransformer.class);

	protected void addTest(ShoppingCartVO cartVO, TestItemVO testItem){
		
		if( isTestBelongsToBattery(cartVO.getCustomer(),testItem.getTestDiscreteOrderItem())){
					AgencyVO agencyVO = getAgencyVO(cartVO, testItem.getAgency()); 
					// add the test to the agency
					agencyVO.getTests().add(testItem);
				}else{
					// add the test to the shopping cart vo
					cartVO.getTests().add(testItem);
				}
			
		}
	
	protected void updateTestItemFlags(TestItemVO testItem){
		// flags are set for post order actions
	}

}
