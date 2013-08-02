<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.common.business.util.Constant"%>
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
			<spring:message code="cancelAppointment.header.title" />
		</h1>


		
	 	

		<!-- Form Canvas starts here -->



	

		<form method="POST" modelAttribute="CancelOrderForm">
		
		
		<div class="reviewformContainer">  
		 	<c:set var="currencyCode" value="<%=Constant.CURRENCY_CODE%>"	scope="request" />
		 	<c:set value="${CancelOrderForm.cancelTest.testItem}" var="itemTest" />
		 	
			<fmt:formatNumber value="${CancelOrderForm.cancelTest.refundAmount.amount}" type="currency" currencyCode="${currencyCode}" var="refundAmt" />
			<spring:message code="cancelAppointment.confirmation" /></br>
			<%-- <c:when test="${empty CancelOrderForm.cancelTest.batterySubscription}">  --%>
			<c:if  test="${ CancelOrderForm.cancelTest.batterySubscription==null}">	
				<spring:message code="canceltest.refund.message" arguments="XXXX" />			
			
			</c:if>
			<c:if  test="${ CancelOrderForm.cancelTest.batterySubscription!=null}">
			<spring:message code="canceltest.inBattery.refund.message" arguments="${itemTest.eligibleBattery.agency.name},${itemTest.eligibleBattery.endDate},XX" /></br><a href=""><spring:message code="canceltest.inBattery.batteryStatus" /></a>	
			</c:if>
			
			</br><a href=""><spring:message code="canceltest.refund.link.policies" /></a>
			
		</div>
		
		
			
			<c:if  test="${ CancelOrderForm.cancelTest.batterySubscription==null}">	
				<ct:viewCartTestItem itemTest="${itemTest}" currencyCode="${currencyCode}" />
			</c:if>
			
			<c:if  test="${ CancelOrderForm.cancelTest.batterySubscription!=null}">	
			  <ct:viewCartTestItem itemTest="${itemTest}" currencyCode="${currencyCode}" batteryItem="${CancelOrderForm.cancelTest.batterySubscription}"/>
			</c:if>  

			<c:url value="" var="prev_url" />

			 <div class="row-fluid">
				<div class="span6">
					&nbsp; &nbsp; <a class="submit" onclick="history.go(-1);return false"><spring:message code="no" /></a>					
				</div>
				<div class="span6">
					<span class="right">
						<button class="submit" type="submit" id="nextButton">
							<spring:message code="yes" />
						</button>
					</span>
				</div>
			</div>
		</form>
		<!--  Form Canvas ends here -->

	</div>




</t:base>