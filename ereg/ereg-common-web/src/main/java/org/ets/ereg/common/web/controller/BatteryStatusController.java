package org.ets.ereg.common.web.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.commerce.order.util.BatteryStatusEnum;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.ets.ereg.domain.interfaces.model.catalog.TestSku;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/secure/batterystatus")
public class BatteryStatusController {
	
	
    @Resource(name = "etsBatterySubscriptionService")
    private ETSBatterySubscriptionService etsBatterySubscriptionService;

    private String getBatteryStatusPage(){
    	
    	return "";
    }
    
    private String getBatteryTestInfo(){
    	
    	return "/battery/testInfo";
    }

    
    @RequestMapping(value= "/open", method = RequestMethod.GET)
	public String displayOpenBatteries(Model model){
		
		 model.addAttribute(BatteryStatusEnum.OPEN.getCode(),etsBatterySubscriptionService.readAllOpenBatteriesForCustomer(CustomerState.getCustomer()));
		 
		 return getBatteryStatusPage();
	}	
	
    @RequestMapping(value= "/close", method = RequestMethod.GET)
	public String displayClosedBatteries(Model model){
		
		model.addAttribute(BatteryStatusEnum.CLOSED.getCode(),etsBatterySubscriptionService.readAllClosedBatteriesForCustomer(CustomerState.getCustomer()));
		
		return getBatteryStatusPage();
	}	
	
    @RequestMapping(value= "/cancelled", method = RequestMethod.GET)
	public String displayCancelledBatteries(){
		return getBatteryStatusPage();
	}
	
    @RequestMapping(value= "/all", method = RequestMethod.GET)
	public String displayBatteriesByStatus(Model model){
    	model.addAttribute("BATTERIES",etsBatterySubscriptionService.readAllBatteriesForCustomerByStatus(CustomerState.getCustomer()));
		return getBatteryStatusPage();
	}
    
    @RequestMapping(value= "/scheduledbatterytests", method = RequestMethod.GET)
	public String displayScheduledBatteryTests(Long subscriptionId, Model model){
		
		model.addAttribute("TESTS",etsBatterySubscriptionService.readAllScheduledTestsForBattery(subscriptionId));
		
		return getBatteryStatusPage();
	}

    @RequestMapping(value= "/batterytestinfo", method = RequestMethod.GET)
	public String displayRemainingBatteryTests(Long agencyId, Model model){
		
       BatterySubscription batterySubscription =
        		etsBatterySubscriptionService.readOpenBatteryForCustomerAndAgency(
        				CustomerState.getCustomer(),
        				agencyId);

	    model.addAttribute("batteryTestInfo", etsBatterySubscriptionService.batteryTestInfo(batterySubscription));

        model.addAttribute("batterySubscription",batterySubscription);
		
		return getBatteryTestInfo();
	}

    
    
/*    @RequestMapping(value= "/batterytests", method = RequestMethod.GET)
	public String displayBatteryTests(Model model){
		
		Map<BatteryStatusEnum,BatterySubscription> batterySubscriptionByStatus = new HashMap<BatteryStatusEnum,BatterySubscription>();
		
		model.addAttribute("BATTERIES",etsBatterySubscriptionService.readAllBatteriesForCustomerByStatus(CustomerState.getCustomer()));
		
		return getBatteryStatusPage();
	}
*/

}
