<%@ page import="org.ets.ereg.common.web.form.CustomerAccommodationsForm"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<script> 
  $(document).ready(function()
  {

	  //for table row
	  $("tr:even").css("background-color", "#EDF6FC");
	  $("tr:odd").css("background-color", "#fff");
  });
  

  function editAccommodation(){
 
	  var today = new Date();
	  var Tday = today.getDate();
	  var Tmonth = today.getMonth()+1; // Zero indexed
	  var Tyear = today.getFullYear();
	  var e = document.getElementById("day");
	 	var day = e.options[e.selectedIndex].value;
	 	
	 	var f = document.getElementById("month");
	 	var month = f.options[f.selectedIndex].value;
	 	
	 	var g = document.getElementById("year");
	 	var year = g.options[g.selectedIndex].value;

	    if(year < Tyear){
	        alert("'Expiration Date' must be after today!");
	    return false;
	    }
	    if((year == Tyear) && (month < Tmonth)){
	        alert("'Expiration Date' must be  after today!");
	    return false;
	    }
	    if((year == Tyear) && (month == Tmonth) && (day <= Tday)){
	        alert("'Expiration Date' must be after today!");
	    return false;
	    }
 	$( "#dialog-edit" ).dialog( "close" );
 	document.editPopupForm.submit();
 } 
  
  
 
  
  
 
  </script>
  <style>
  select.dobMonth {max-width:112px; *width:110px; width:100%;}
  select.dobDay {max-width:65px; *width:100px; width:100%;}
 select.accYear {max-width:85px; *width:80px; width:100%;}
  </style>
					<!-- Form Canvas starts here -->
					<c:url value="/secure/testtaker/accommodation/edit" var="editAction"/> 
					<div class="accomodationTest">
						<form:form name="editPopupForm" method="post" modelAttribute="accommodationsForm" action="${editAction}">
							<c:set var="ACCOMMODATION_TYPE_CODE_OTHER" value="<%=AccommodationType.ACCOMMODATION_TYPE_CODE_OTHER%>"/>
							
							<input id="customerId" name="customerId" type="hidden" value="${customerId}"/>
							
								<c:set var="i" value="0"/>
								<c:forEach items="${customerProgramAccommodations.customerTestAccommodations}" var="customerTestAccommodations">
								
								<c:forEach items="${customerTestAccommodations.customerDeliveryMethodAccommodations}" var="customerDeliveryMethodAccommodations">
								
								<c:forEach items="${customerDeliveryMethodAccommodations.customerAccommodations}" var="customerAccommodation">
								
								<c:out value="${customerTestAccommodations.testTitle}"/> (<c:out value="${customerDeliveryMethodAccommodations.deliveryMethod}"/> Test)
								<br>
								
								<label for="accommodation"><spring:message code="accommodations.label.accommodation"/>:</label><c:out value="${customerAccommodation.accommodation.description}" />
								<c:if test="${null != accommodationsForm.accommodations[i].accommodation.accommodationTypeValues && fn:length(accommodationsForm.accommodations[i].accommodation.accommodationTypeValues) > 0}">
									<form:select path="accommodations[${i}].accommodationTypeValue" required="required" multiple="false" class="dobMonth">
									<c:forEach items="${accommodationsForm.accommodations[i].accommodation.accommodationTypeValues}" var="accommodationTypeValue">
									<form:option value="${accommodationTypeValue.accommodationTypeValueIdentifier}">${accommodationTypeValue.label}</form:option>
									</c:forEach>
									</form:select>
								</c:if>
								<c:if test="${accommodationsForm.accommodations[i].accommodationTypeCode == ACCOMMODATION_TYPE_CODE_OTHER}">
									<form:textarea path="accommodations[${i}].accommodationTypeValue"/>
								</c:if>
							
								<div style="clear:btoh;"></div>
								<label for="expirationDate"><spring:message code="accommodations.label.expirationDate"/>:</label>
								<c:set var="monthIndex" value="1" />
								<form:select  id ="month" path="accommodations[${i}].expirationMonth" class="dobMonth">								
								<c:forEach items="${months}" var="month">
								<form:option value="${monthIndex}">${month}</form:option>
								<c:set var="monthIndex" value="${monthIndex+1}"/>
								</c:forEach>
								</form:select>
								<c:set var="dayIndex" value="1" />
								<form:select id="day" path="accommodations[${i}].expirationDay" class="dobDay">								
								<c:forEach items="${days}" var="day">
								<form:option value="${dayIndex}">${day}</form:option>
								<c:set var="dayIndex" value="${dayIndex+1}"/>
								</c:forEach>
								</form:select>
								<form:select id="year" path="accommodations[${i}].expirationYear" class="accYear">								
								<c:forEach items="${years}" var="year">
								<form:option value="${year}">${year}</form:option>
								</c:forEach>
								</form:select>
								
								<form:errors class="errorMessage" path="accommodations[${i}].expirationDate"/>
								
								<form:hidden path="accommodations[${i}].testId"/>
								<form:hidden path="accommodations[${i}].deliveryMethod"/>
								<form:hidden path="accommodations[${i}].accommodationTypeCode"/>
								<form:hidden path="accommodations[${i}].originalAccommodationTypeValue"/>
								<form:hidden path="accommodations[${i}].originalExpirationDate"/>
								<form:hidden path="accommodations[${i}].approvalDate"/>							
								
								
								</tr>
								<c:set var="i" value="${i+1}"/>
								
								</c:forEach>
								</c:forEach>
								</c:forEach>
								<form:hidden path="bulkEdit"/>	
							<%-- 	<form:hidden path="testCode"/>
							<form:hidden path="accommodationStatus"/>
							<form:hidden path="deliveryMode"/> --%>
								
</form:form>
</div>

