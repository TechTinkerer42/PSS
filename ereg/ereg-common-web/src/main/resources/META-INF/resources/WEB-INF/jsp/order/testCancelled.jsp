<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.common.business.util.Constant"%>
<%@ page import="org.broadleafcommerce.common.money.Money"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="org.ets.ereg.common.web.form.CancelOrderForm"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS-eREG(Cancel Order)">


	<c:set var="currencyCode" value="<%=Constant.CURRENCY_CODE%>"
		scope="request" />


	<div class="headContainer ">

		<h1>
			<spring:message code="appointment.canceled.title" />
		</h1>


		<form method="POST" modelAttribute="CancelOrderForm">
	 	<div class="reviewformContainer">  
		 
			<fmt:formatNumber value="${CancelOrderForm.cancelTest.refundAmount.amount}" type="currency" currencyCode="${currencyCode}" var="refundAmt" />
			
			
			<ct:formatRegex out="${CancelOrderForm.cancelTest.testItem.testDiscreteOrderItem.firstBooking.etsApptID}" regexInputString="(?<!\\d)0+(?=\\d+)" regexOutputString=""/>
			
			<spring:message code="cancelAppointment.cancelled.message" arguments="${CancelOrderForm.cancelTest.testItem.testDiscreteOrderItem.firstBooking.etsApptID}" /></br>
			<ul>
			<spring:message code="test.cancelled.email.message"  /></br>
			
			
			<c:if test="${CancelOrderForm.cancelTest.refundAmount.amount.doubleValue() > 0}">
			
				 <spring:message code="test.cancelled.refund.message" arguments="${refundAmt }" /></br>	 
			</c:if>
			</ul>
		</div>


		<!-- Form Canvas starts here -->
			

		

			 <div class="row-fluid">
				<div class="span6">
					&nbsp; &nbsp; <c:url value="/secure/home" var="prev_url"/> 
<a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="home"></spring:message></a>				
				</div>
				
			</div>
		</form>
		<!--  Form Canvas ends here -->

	</div>




</t:base>