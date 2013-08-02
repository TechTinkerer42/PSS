<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="org.ets.ereg.common.web.form.CustomerAccommodationsForm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">


 function removeAccommodation(){	
	$( "#dialog-remove" ).dialog( "close" );
	document.removePopupForm.submit();
} 

</script>

</head>
<body>
<div  > <c:if test="${has_appointment_scheduled == true}"><b><spring:message code="accommodations.scheduled.booking.warning"/></c:if></b></br><spring:message code="testtaker.accommodation.removal.label"/></br>
<c:url value="/secure/testtaker/accommodation/remove" var="removeAction"/> 
	<form:form name="removePopupForm" method="post" modelAttribute="viewAccommodationForm" action="${removeAction}" id="removeSubForm">
		<div class="formContainer">		
		
			${test.test.testName}(${test.programAccommodationDeliveryMode.deliveryModeType.description} Test)<br>
			<label for="accommodation"><spring:message code="accommodations.label.accommodation"/>:</label>
			<!-- Accommodation: -->
				${test.programAccommodationDeliveryMode.accommodationType.description}
				<c:if test="${not empty test.accommodationTypeValue.value}">(${test.accommodationTypeValue.label})</c:if><br>
				
			<!-- Expiration Date:  -->
			<label for="expirationDate"><spring:message code="accommodations.label.expirationDate"/>:</label><ct:dateTime part="date" value="${test.expiryDate}" />					
			
				<input  name="customerId" type="hidden" value="${customerId}"/>
				<input  name="testId" type="hidden" value="${testId}"/> 
				<input  name="accommodationTypeCode" type="hidden" value="${accommodationTypeCode}"/> 
				<input  name="deliveryMode" type="hidden" value="${deliveryMethodCode}"/>
			 
		</div>
	
	</form:form>
	</div>
</body>
</html>