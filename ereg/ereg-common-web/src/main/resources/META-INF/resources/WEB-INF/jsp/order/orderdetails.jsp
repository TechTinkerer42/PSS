<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%@ page import="org.ets.ereg.domain.interfaces.model.booking.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-1.8.3.min.js"/>'></script>
<script
	src='<spring:url htmlEscape="true" value="/commonweb/js/misc.js"/>'></script>
<title>Appointment Confirmation</title>

<t:base title="Order Details">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Order Details</h1>
			</div>
		</div>
	</div>


	<p style="margin: 0 25px;">
		<label><b>
			<spring:message code="order.detail.number" arguments=" ${orderVO.order.orderNumber} " />
			| <spring:message code="order.detail.date" />
			: <ct:dateTime part="dateWithDayOfWeek" value="${orderVO.order.submitDate}" />
		</b></label>
	</p>


	
	



	<c:forEach var="agencyVo" items="${orderVO.agencies}">
		<div class="base-layer">
			<p style="margin: 0 25px;">
				<label><b>
					<spring:message code="view.order.battery.label" arguments=" ${agencyVo.agency.name} " /></b>
					<br><spring:message code="view.order.battery.instructions" arguments=" ${agencyVo.agency.name} " />
					<ct:dateTime part="dateWithDayOfWeek" value="${agencyVo.batterySubscription.endDate}"/>.
					
				</label>
			</p>
				<c:forEach var="test" items="${agencyVo.tests}">
				<ct:appointmentInfoDetails booking="${test.booking}" mode="${mode}"
					customerIdStr="${customerIdStr}" displayAppointmentLabel="true"
					displayAppointmentConfirmation="true"
					displayTestCenterAddress="false" displayTestPrice="true" strikeAmt="true"
					testprice="${test.testFulfillmentGroupItem.retailPrice}" />
			</c:forEach>
		
		</div>
	
	</c:forEach>

	
	<c:forEach var="test" items="${orderVO.tests}">
		<ct:appointmentInfoDetails booking="${test.booking}" mode="${mode}"
			customerIdStr="${customerIdStr}" displayAppointmentLabel="true"
			displayAppointmentConfirmation="true"
			displayTestCenterAddress="false" displayTestPrice="true"
			testprice="${test.testFulfillmentGroupItem.retailPrice}" />
	</c:forEach>
	
	
	
		<c:forEach var="agencyVo" items="${orderVO.agencies}">
		<div id="orderDetailsFooter"
			class="appointmentdetailsoutsideborderradius" style="border: 0;">
			<div class="table-row">
				<p class="text" style="text-align: right">
					<b><spring:message code="view.order.battery.fee.label" />
						: $ ${agencyVo.batterySubscription.batteryDiscreteOrderItem.price}</b>
				</p>
			</div>
		</div>

	</c:forEach>
	
	<c:if test="${memberShipAmount != 0}">
		<div id="orderDetailsFooter"
			class="appointmentdetailsoutsideborderradius" style="border: 0;">
			<div class="table-row">
				<p class="text" style="text-align: right">
					<b><spring:message code="view.order.member.registration.label" />
						: $ ${memberShipAmount}</b>
				</p>
			</div>
		</div>

	</c:if>
	<div id="orderDetailsFooter"
		class="appointmentdetailsoutsideborderradius"
		style="background: #FFFFCC">
		<div class="table-row">


			<p class="text" style="text-align: right">
				<b><spring:message code="reviewcart.label.total.price" /> : $
					${orderVO.order.total}</b>
			</p>
			<p class="text" style="text-align: right">
				<c:if test="${creditCardLastFourDigits ne ''}">
					<spring:message code="view.order.credicard.label" />
				( xxxx-xxxx-xxxx-${creditCardLastFourDigits})
			</c:if>
			</p>
		</div>
	</div>
	<div class="space-line"></div>
	<p style="margin: 25px;">
		<a class="submit"
			href="<ct:encode out='/secure/viewordersearch/search?customerId=${customerIdStr}' />"><spring:message
				code="button.label.back" /></a>
	</p>
	<br>


</t:base>