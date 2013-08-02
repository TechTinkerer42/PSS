package org.ets.ereg.common.web.controller.order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoAdditionalFieldType;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.commerce.order.vo.AgencyVO;
import org.ets.ereg.commerce.order.vo.MembershipItemVO;
import org.ets.ereg.commerce.order.vo.PostOrderTransformer;
import org.ets.ereg.commerce.order.vo.ShoppingCartTransformer;
import org.ets.ereg.commerce.order.vo.ShoppingCartVO;
import org.ets.ereg.commerce.order.vo.TestItemVO;
import org.ets.ereg.common.business.service.RulesSetConfigurationService;
import org.ets.ereg.common.helpers.ThreadLocalFacade;
import org.ets.ereg.common.web.util.CommonWebMappingConstants;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(CommonWebMappingConstants.BASE_ORDER_DETAIL)
public class OrderDetailsController {

	private static Logger log = LoggerFactory
			.getLogger(OrderDetailsController.class);

	private static final String VIEW_ORDER_DETAILS = "order/orderdetails";
	private static final String VIEW_APPOINTMENT_DETAILS = "order/appointmentdetails";

	@Resource(name = "postOrderTransformer")
	private PostOrderTransformer postOrderTransformer;

	@Resource(name = "etsOrderService")
	private ETSOrderService orderService;

	@Resource(name = "bookingBusinessService")
	protected BookingBusinessService bookingBusinessService;

	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;

	@Resource(name = "rulesSetConfigurationService")
	RulesSetConfigurationService rsConfigService;
	

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String displayOrderDetailsByOrderNumber(
			@RequestParam("orderNumber") String orderNumber,
			@RequestParam("customerId") Long customerId, Model model) {

		log.info("Entering displayOrderDetailsByOrderNumber of ETSOrderDetailsController...");
		log.info("orderNumber - " + orderNumber);
		log.info("customerId - " + customerId);

		Order order = getOrder(orderNumber);

		if (order != null) {

			List<PaymentInfo> listPaymentInfo = order.getPaymentInfos();

			Map<String, String> listMap = null;
			if (listPaymentInfo != null && listPaymentInfo.size() > 0) {
				listMap = listPaymentInfo.get(0).getAdditionalFields();
			}

			String creditCardLastfourDigits = "";
			if (listMap != null && listMap.size() > 0) {

				log.info("PaymentInfoAdditionalFieldType.LAST_FOUR - "
						+ PaymentInfoAdditionalFieldType.LAST_FOUR.getType());
				creditCardLastfourDigits = listMap
						.get(PaymentInfoAdditionalFieldType.LAST_FOUR.getType());

			}
			ShoppingCartVO orderVO = postOrderTransformer.transform(order);
			
			List<AgencyVO> listAgencyVO = orderVO.getAgencies();
			
			for(AgencyVO agencyVO :listAgencyVO){
				
				List<TestItemVO> listTestItemVO = agencyVO.getTests();
				for(TestItemVO  testItemVO :listTestItemVO){
				log.info("testItemVO ID - "
						+ testItemVO.getTestFulfillmentGroupItem().getRetailPrice());
				
				log.info("booking - "
						+ testItemVO.getBooking());
				}
			}

			List<MembershipItemVO> listMembershipItemVO = orderVO
					.getMembershipItemsInCart();

			BigDecimal membershipAmount = new BigDecimal(0);

			if (listMembershipItemVO != null
					&& listMembershipItemVO.size() > 0
					&& listMembershipItemVO.get(0)
							.getMembershipDiscreteOrderitem() != null) {
				membershipAmount = listMembershipItemVO.get(0)
						.getMembershipDiscreteOrderitem().getPrice()
						.getAmount();
			}

			log.info("MemberVO size " + listMembershipItemVO.size());
			setAttributes(model, creditCardLastfourDigits,
					orderVO,membershipAmount, customerId);

		}

		return VIEW_ORDER_DETAILS;

	}

	private void setAttributes(Model model, String creditCardLastfourDigits,
			ShoppingCartVO orderVO,
			BigDecimal membershipAmount,  Long customerId) {
		model.addAttribute(Constant.MODEL_ATTR__CREDITCARD_LAST_FOUR_DIGITS,
				creditCardLastfourDigits);
		model.addAttribute(Constant.MODEL_ATTR_MEMBERSHIP_AMOUNT,
				membershipAmount);
		model.addAttribute(Constant.MODEL_ATTR_ORDER, orderVO);
		model.addAttribute(Constant.MODEL_ATTR_CUSTOMERID, customerId);
	}

	@RequestMapping(value = Constant.APPOINTMENT_DETAILS_PAGE, method = RequestMethod.GET)
	public String displayAppointmentDetailsByOrderNumber(
			@RequestParam("bookingId") Long bookingId,
			@RequestParam("customerId") Long customerId, Model model) {

		log.info("Entering displayAppointmentDetailsByOrderNumber of ETSOrderDetailsController...");
		log.info("Booking id from request - " + bookingId);
		log.info("Customer id from request - " + customerId);
		Booking booking = bookingBusinessService.getBookingById(bookingId);
		
		//log.info("Order getOrderNumber - " +  booking.getTestDiscreteOrderItem().getOrder().getOrderNumber());		
		//log.info("Modal - " +  booking.getTestCenter().getSchedulingType(ThreadLocalFacade.getProgramCode()).getCode());

		ProfileVO profile = profileBusinessService.readProfileById(customerId);
		model.addAttribute("booking", booking);
		model.addAttribute("profile", profile);
		model.addAttribute("mode", "SELF");
		model.addAttribute(Constant.MODEL_ATTR_CUSTOMERID, customerId);
		return VIEW_APPOINTMENT_DETAILS;

	}

	@RequestMapping(value = Constant.APPOINTMENT_DETAILS_INSTRUCTION_URL, method = RequestMethod.POST)
	public @ResponseBody
	String loadInstructionsUsingPost(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("Post - loadInstructions");
		String strFileContent = null;

		// TODO Get the below file from ereg hierarchy service String
		// val=rsConfigService.getConfigurationValue(4L, "ECOMM", "FILOC");
		String url = request.getRequestURL().toString();
		String testType = request.getParameter("testtype");
		log.info("URL -" + url + "testType - " + testType);

		String fileName = null;
		if (testType.equals("Computer")) {
			fileName = "instructions_computer.html";
		} else {
			fileName = "instructions_paper.html";
		}

		try {
			if (url.contains("localhost")) {
				strFileContent = readFileAsString("\\\\L-AF671890\\instruction_files_ereg\\"
						+ fileName);
			} else {
				strFileContent = readFileAsString("/export/Apps/jboss-ews-1.0/ereg/instructions/"
						+ fileName);
			}

		} catch (IOException e) {
			return null;
		}

		// TODO Incorporate below changes
		/*
		 * EregHierarchy eregHierarchy =
		 * rsConfigService.getProgramSpecificEregHierarchy("New York", "HSE");
		 * String
		 * fileLocation=rsConfigService.getConfigurationValue(eregHierarchy
		 * .getEregHierarchyIdentifier(), "ECOMM", "FLCBT");
		 */
		return strFileContent;
	}

	
	// TODO This method would be no more used once loadInstructionsUsingPost is
	// done using the hierarchy service getConfigurationValue
	private static String readFileAsString(String filePath)
			throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	private Order getOrder(String orderNumber) {
		return orderService.findOrderByOrderNumber(orderNumber);
	}

}
