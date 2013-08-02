<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="itemTest" required="true" type="org.ets.ereg.commerce.order.vo.TestItemVO"%>
<%@ attribute name="batteryItem" required="false" type="org.ets.ereg.commerce.order.vo.BatteryItemVO"%>
<%@ attribute name="currencyCode" required="true"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

	<div class="reviewformContainer">
<c:url value="/secure/cart/remove/" var="prev_url"/>
			<table class="itemcartTable">			
			 	<c:if test="${(not empty batteryItem) and  (not ( batteryItem.isTestWithinBatteryDuration(itemTest.booking.appointmentDateTime)) )}"><spring:message code="reviewcart.outside.battery.expiration.message"/></c:if>	 
						
						<tr> 
						<td ><strong><spring:message code="reviewcart.label.appointment"/>:</strong></td>
						<td><strong>[${itemTest.booking.testVariation.test.testName}]</strong><a href="">(<u><spring:message code="reviewcart.link.edit"/></u>)</a></td>
						
						<td><a href="${prev_url}${itemTest.testFulfillmentGroupItem.orderItem.id}"><i class="icon-remove"></i><spring:message code="reviewcart.link.remove"/></a></td>
						</tr>						
						
						 <tr>
						 <td><spring:message code="reviewcart.label.testcenter"/>:</td><td colspan=2>${itemTest.booking.testCenter.name }</td></tr>
						 <tr>
						 <td><spring:message code="reviewcart.label.language"/>:</td><td>${itemTest.booking.testVariation.languageType.description}</td> </tr>
						<tr><td><spring:message code="reviewcart.label.datentime"/>:</td>
				 	 			<td colspan=2>
				 	 				<ct:dateTime part="date" value="${itemTest.booking.appointmentDateTime}"  />
									<ct:dateTime part="time" value="${itemTest.booking.appointmentDateTime}" />		
								 </td></tr>
				 	 			
				 	 			 <tr><td>${itemTest.booking.testVariation.deliveryModeType.description}</td><td><strong><spring:message code="reviewcart.label.test.price"/>:
							  	<c:if  test="${itemTest.testDiscreteOrderItem.averageAdjustmentValue.amount >0}">
							  	 	<del> <fmt:formatNumber value="${itemTest.testDiscreteOrderItem.sku.retailPrice.amount}" type="currency" 	currencyCode="${currencyCode}" /></del>
							  	 </c:if>
						  	 
						  	  <fmt:formatNumber value="${itemTest.testDiscreteOrderItem.sku.retailPrice.amount -itemTest.testDiscreteOrderItem.averageAdjustmentValue.amount}" type="currency" 	currencyCode="${currencyCode}" /></strong></td></tr>
						  	 	
					</table>
					

	</div>
			<div class="clear"></div>
			
					