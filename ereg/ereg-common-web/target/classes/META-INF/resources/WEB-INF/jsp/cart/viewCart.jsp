<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.common.business.util.Constant" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS-eREG(Cart)">

	
		
		
	
<div class="headContainer "> 
<div class="">
         <h1><spring:message code="reviewcart.header.title"/> </h1>

	<div  id="reviewCartDisclaimer"class="reviewCart"  
		<c:if test="${cartVO.totalQuantity == 0}">style="display:none;"</c:if>>
  
        <ul> 
            
         <spring:message code="reviewcart.header.disclaimar"/>
			<a href"><spring:message code="reviewcart.header.link.policies"/></a>
         </ul> 
	</div>




<!-- Form Canvas starts here -->

<div class="reviewCartContainer">

    
<c:url value="${action}" var="formAction"/> 

<c:url value="${actionBattery}" var="agencyAction"/> 		
	
<c:set var="currencyCode" value="<%=Constant.CURRENCY_CODE%>" scope="request"/>
<c:choose>
	<c:when test="${cartVO.totalQuantity != 0}"> <!-- non empty cart -->
	
	
	<strong><spring:message code="reviewcart.subheader"/></strong>
		
		 <c:forEach items="${cartVO.allAgenciesWithoutOpenBattery}" var="agencyWithoutBattery" varStatus="status">
		 
		<%--  <ct:viewCartWithBatteryOption agency="${agencyWithoutBattery}" cartVO="${cartVO}" currencyCode="${currencyCode}"/>  --%>
		 <!-- Below should go in Battery tag -->
		  <div style="background-color:#CEECF5; border:1px solid #81BEF7; padding:5px;">	 
		 	<form action="${agencyAction}" method="GET">
				<c:url		value="/secure/cart/add/battery"		var="addBatteryAction" />
				<c:url		value="/secure/cart/remove"		var="removeBatteryAction" />
				
				<strong>${agencyWithoutBattery.name} <spring:message code="reviewcart.agencyBatteryTitle"/></strong><br>				
				 <fmt:formatNumber value="${batteryProduct.defaultSku.listPrice.amount}" type="currency" 	currencyCode="${currencyCode}" var="batteryCost"/>
				 <spring:message code="reviewcart.discountMessage"   arguments="${batteryCost},${batteryProduct.batteryProductItems.size()},${agencyWithoutBattery.name},${batteryProduct.durationInMonths}"/>
				</br></br>
				
				
				<input type="radio" value="yes" onClick="addBattery(this.form,'${addBatteryAction}')"  <%-- <c:if  test="${cartVO.isBatteryInCart(agencyWithoutBattery.id)}">CHECKED</c:if> /> --%>
				<c:if  test="${ cartVO.getAgency(agencyWithoutBattery.id).batteryItem!=null}">CHECKED</c:if> />
				
				<fmt:formatNumber value="${batteryProduct.defaultSku.listPrice.amount/batteryProduct.batteryProductItems.size()}" type="currency" 	currencyCode="${currencyCode}"  var="testCostWithBattery"/>
				<spring:message code="reviewcart.yes.battery" arguments="${batteryCost},${batteryProduct.batteryProductItems.size()},${testCostWithBattery},${batteryProduct.getMaxReTakes()}"/>  
				
				 
				</br>
				
				<input type="radio"  value="no"  onClick="removeBattery(this.form,'${removeBatteryAction}')" <c:if  test="${!(cartVO.getAgency(agencyWithoutBattery.id).batteryItem!=null)}">CHECKED</c:if> />
				
				<fmt:formatNumber value="${cartVO.getAgency(agencyWithoutBattery.id).testPrice}" type="currency" 	currencyCode="${currencyCode}"  var="testPrice"/>
				<spring:message code="reviewcart.no.battery" arguments="${testPrice}"/>			
				 
				</br>
				
				<input type="hidden" name="agencyId" value="${agencyWithoutBattery.id}">
				
				
				 <c:if  test="${cartVO.getAgency(agencyWithoutBattery.id).batteryItem!=null}">
				<input type="hidden" id="batteryDiscereteOrderItemId" name="batteryDiscereteOrderItemId" value="${cartVO.getAgency(agencyWithoutBattery.id).batteryItem.batteryDiscreteOrderitem.id}">
				</c:if>
				
				
				
		 		
		 		<c:forEach items="${cartVO.getAgency(agencyWithoutBattery.id).tests}" var="itemTest" varStatus="status">	
		 		<c:choose>	
			 		 <c:when  test="${cartVO.getAgency(agencyWithoutBattery.id).batteryItem!=null}">
			 	 		  <ct:viewCartTestItem itemTest="${itemTest}" currencyCode="${currencyCode}" batteryItem="${cartVO.getAgency(agencyWithoutBattery.id).batteryItem}"/>  
			 	 		<%--  <ct:viewCartTestItem itemTest="${itemTest}" currencyCode="${currencyCode}"/>   --%>
			 	 	 </c:when>
			 	 	 <c:otherwise>
			 	 		<ct:viewCartTestItem itemTest="${itemTest}" currencyCode="${currencyCode}"/>  
			 	 	 </c:otherwise>
		 	 	</c:choose>
					
		 		</c:forEach>
		 	</form>
		 	
		 	
		 	</div > 
		 	</br>
		 </c:forEach>
		 
		 
		 <!--  Not included in battery , this should be inside teh agency itself -->
		
		<%--  <c:if test="${not empty  cartVO.testsChargedUnderOpenBattery}"> --%>
		<c:if test="${not empty  cartVO.tests}"> 
		
			 <c:forEach items="${ cartVO.tests}" var="testOutsideBattery" varStatus="status">
			 			
			 	 		<ct:viewCartTestItem itemTest="${testOutsideBattery}" currencyCode="${currencyCode}"/> 
			 </c:forEach>
			
		  </c:if>
				
				<!--  Battery Purchased Already -->
				
		 		
		 <c:forEach  items="${cartVO.testsWithPastBattery}" var="itemTest" varStatus="status">		
		 	<ct:viewCartTestItem itemTest="${itemTest}" currencyCode="${currencyCode}"/> 
		 </c:forEach>

        <c:forEach items="${cartVO.batteryItemsInCart}" var="itemBattery" varStatus="status">
            <ct:viewCartBatteryItem battery="${itemBattery}" currencyCode="${currencyCode}"/>
        </c:forEach>
		 
		 
		 <c:forEach items="${cartVO.membershipItemsInCart}" var="itemMember" varStatus="status">	
		 	 <ct:viewCartMemberShipItem membership="${itemMember}" currencyCode="${currencyCode}"/>   
		  </c:forEach>
		  
		
		<div class="cartTotalAmount"><p>
		<spring:message code="reviewcart.label.total.price"/>:<fmt:formatNumber value="${cartVO.totalAmount}" type="currency"
								currencyCode="${currencyCode}" />	</p></div>
						
						
						
		<div style="clear:both;"></div>
		
		<form id ="viewCart" action="${formAction}" method="POST">
		<c:if test="${cartVO.totalAmount != 0}"> 
		<input type="hidden" name="originatingSystem_code" value="${hopFields['originatingSystem_code']}"/>	            
	            <input type="hidden" name="originatingSystem_sessionId" value="${hopFields['originatingSystem_sessionId']}"/>
	            <input type="hidden" name="modeOfReceipt" value="${hopFields['modeOfReceipt']}"/>
	            <input type="hidden" name="context" value="${hopFields['context']}"/>
	            <input type="hidden" name="project" value="${hopFields['project']}"/>
	            <input type="hidden" name="invoiceHeader_merchantDescriptor" value="${hopFields['invoiceHeader_merchantDescriptor']}"/>
	            <input type="hidden" name="amount" value="${hopFields['amount']}"/>
	            <input type="hidden" name="currency" value="${hopFields['currency']}"/>
	            <input type="hidden" name="orderPage_timestamp" value="${hopFields['orderPage_timestamp']}"/>
	            <input type="hidden" name="merchantID" value="${hopFields['merchantID']}"/>
	            <input type="hidden" name="merchant_successURL" value="${hopFields['merchant_successURL']}"/>
	            <input type="hidden" name="merchant_rejectURL" value="${hopFields['merchant_rejectURL']}"/>
	            <input type="hidden" name="merchant_errorURL" value="${hopFields['merchant_errorURL']}"/>
	            <input type="hidden" name="merchant_timeoutURL" value="${hopFields['merchant_timeoutURL']}"/>
	            <input type="hidden" name="orderPage_signaturePublic" value="${hopFields['orderPage_signaturePublic']}"/>
	            <input type="hidden" name="orderPage_version" value="${hopFields['orderPage_version']}"/>
	            <input type="hidden" name="orderPage_serialNumber" value="${hopFields['orderPage_serialNumber']}"/>
	            <input type="hidden" name="orderPage_signedFields" value="${hopFields['orderPage_signedFields']}">
	            
	            
	         <%-- <c:if test="${cartVO.physicalShipment == true}">   --%>
	            <input type="hidden" name="shipTo_firstName" value="${customer.firstName}">
	            <input type="hidden" name="shipTo_lastName" value="${customer.lastName}">
	            <input type="hidden" name="shipTo_street1" value="${address.addressLine1}">
	            <input type="hidden" name="shipTo_city" value="${address.city}">
	            <input type="hidden" name="shipTo_state" value="${address.state.abbreviation}">
	            <input type="hidden" name="shipTo_postalCode" value="${address.postalCode}">
	            <input type="hidden" name="shipTo_country" value="${address.country.abbreviation}">		
		<%-- 	</c:if> --%>
		</c:if>
						
		<c:url value="/secure/appointment/new/info?testCenterId=${testCenterId}" var="prev_url"/>
								
		 <div class="row-fluid">
		    <div class="span6">&nbsp; &nbsp; <a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="reviewcart.button.new.appointment"/></a>
		    
										
		    
		</div>
	    <div class="span6"><span class="right">
	    <button class="submit" type="submit" id="nextButton" >
	    	<c:if test="${cartVO.totalAmount != 0}"> <spring:message code="reviewcart.button.checkout"/></c:if>
	    	<c:if test="${cartVO.totalAmount == 0}"> <spring:message code="submit"/></c:if>
	    </button></span> &nbsp; &nbsp;  &nbsp; &nbsp;  &nbsp; &nbsp;</div>
	    </div>
	
						
		</form>				
							
															
	
	</c:when>
	
	<c:otherwise><!--  empty cart -->	
		<spring:message code="reviewcart.emptyCartMessage"/>
	</c:otherwise>
	
</c:choose>

			




<script type="text/javascript">
	function addBattery(form,actionURL) {	
		
		 form.action=actionURL;
		 
			form.submit();
		

	}
	
	function removeBattery(form,actionURL) {	
		
		form.action=actionURL+"/"+document.getElementById('batteryDiscereteOrderItemId').value;
		form.submit();
			
		

	}

	</script>	
		
</div>
</div>
	</div>
	<!--  Form Canvas ends here -->



</t:base>