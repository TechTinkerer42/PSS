<%@ page import="org.ets.ereg.common.web.form.CustomerAccommodationsForm"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Edit Accommodation)">
<script> 
  $(document).ready(function()
  {

 
	 $("table.editAccomodationTable tr").filter(function() { 
		  return $(this).children("td").length == 5;
		}).filter(':even').addClass('alt');

		 $("tr.alt td[rowspan]").each(function() {
		  $(this).parent().nextAll().slice(0, this.rowSpan - 1).addClass('alt');
		}); 

	 
	 
	  
	  //for table row
	/* $("tr:even").css("background-color", "#DAEDF5");
	  $("tr:odd").css("background-color", "#fff"); */
  });
  </script>
					<!-- Form Canvas starts here -->
					<div class="accomodationFilter">
<c:url value="/secure/testtaker/accommodation/edit" var="editAction"/>    
<h1>Edit Accommodations For <c:out value="${firstName} ${lastName}" /></h1>
					
					<span><strong>Test Titles:</strong>
					<c:out value="${testTitle}"/> 
					<strong>Test Type:</strong>
					<c:out value="${testType}"/>  
					<strong>Accommodation Status:</strong> 
					<c:out value="${accommodationStatus}"/></span>
					</div>
					<div class="accomodationTest">
						<form:form method="post" modelAttribute="accommodationsForm" action="${editAction}">
							<c:set var="ACCOMMODATION_TYPE_CODE_OTHER" value="<%=AccommodationType.ACCOMMODATION_TYPE_CODE_OTHER%>"/>
							<table  class="editAccomodationTable">
								<thead>
									<tr>
										<th>Test Title</th>
										<th>Test Type</th>
										<th>Accommodations</th>
										<th>Expiration Date</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
								<c:set var="i" value="0"/>
								<c:forEach items="${customerProgramAccommodations.customerTestAccommodations}" var="customerTestAccommodations">
								<c:set var="testRowSpan" value="${customerTestAccommodations.testAccommodationCount}"/>
								<c:forEach items="${customerTestAccommodations.customerDeliveryMethodAccommodations}" var="customerDeliveryMethodAccommodations">
								<c:set var="deliveryRowSpan" value="${customerDeliveryMethodAccommodations.accommodationCount}"/>
								<c:forEach items="${customerDeliveryMethodAccommodations.customerAccommodations}" var="customerAccommodation">
								<tr>
								<c:if test="${testRowSpan > 0}">
								<td rowspan="${testRowSpan}"><c:out value="${customerTestAccommodations.testTitle}"/></td>
								<c:set var="testRowSpan" value="0"/>
								</c:if>
								<c:if test="${deliveryRowSpan > 0}">
								<td rowspan="${deliveryRowSpan}"><c:out value="${customerDeliveryMethodAccommodations.deliveryMethod}"/></td>
								<c:set var="deliveryRowSpan" value="0"/>
								</c:if>
								<td>
								<c:out value="${customerAccommodation.accommodation.description}" />
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
								<!-- 
								<c:if test="${accommodation.code == ACCOMMODATION_TYPE_CODE_OTHER}">
									<form:textarea path="accommodations[${i}].accommodationTypeValue"/>
								</c:if>
								<c:if test="${null != customerAccommodation.accommodation.valueId}">
								(<c:out value="${customerAccommodation.accommodation.valueText}" />)
								</c:if>
								-->
								</td>
								<td>
								<c:set var="monthIndex" value="1" />
								<form:select path="accommodations[${i}].expirationMonth" class="dobMonth">
								<form:option value="">Month</form:option>
								<c:forEach items="${months}" var="month">
								<form:option value="${monthIndex}">${month}</form:option>
								<c:set var="monthIndex" value="${monthIndex+1}"/>
								</c:forEach>
								</form:select>
								<c:set var="dayIndex" value="1" />
								<form:select path="accommodations[${i}].expirationDay" class="dobDay">
								<form:option value="">Day</form:option>
								<c:forEach items="${days}" var="day">
								<form:option value="${dayIndex}">${day}</form:option>
								<c:set var="dayIndex" value="${dayIndex+1}"/>
								</c:forEach>
								</form:select>
								<form:select path="accommodations[${i}].expirationYear" class="accYear">
								<form:option value="">Year</form:option>
								<c:if test="${!customerAccommodation.activeAccommodation && accommodationsForm.accommodations[i].expirationYear != years[0]}">
								<form:option value="${accommodationsForm.accommodations[i].expirationYear}">${accommodationsForm.accommodations[i].expirationYear}</form:option>
								</c:if>
								<c:forEach items="${years}" var="year">
								<form:option value="${year}">${year}</form:option>
								</c:forEach>
								</form:select>
								<!-- 
								<form:input path="accommodations[${i}].expirationYear" size="4" id="yearOfBirth" maxlength="4" pattern="[0-9]{4}" required="required" class="dobYear"/>
								-->
								<form:errors class="errorMessage" path="accommodations[${i}].expirationDate"/>
								</td>
								<td>
								<c:if test="${customerAccommodation.activeAccommodation}">
								<span class="active"><c:out value="Active"/></span>
								</c:if>
								<c:if test="${!customerAccommodation.activeAccommodation}">
								<span class="expired"><c:out value="Expired"/></span>
								</c:if>
								</td>
								<form:hidden path="accommodations[${i}].testId"/>
								<form:hidden path="accommodations[${i}].deliveryMethod"/>
								<form:hidden path="accommodations[${i}].accommodationTypeCode"/>
								<form:hidden path="accommodations[${i}].originalAccommodationTypeValue"/>
								<form:hidden path="accommodations[${i}].originalExpirationDate"/>
								<form:hidden path="accommodations[${i}].approvalDate"/>
								
								<!-- 
								<form:hidden path="accommodations[${i}].accommodationTypeValue"/>
								-->
								</tr>
								<c:set var="i" value="${i+1}"/>
								</c:forEach>
								</c:forEach>
								</c:forEach>
								</tbody>
							</table>

							
							<div class="clear"></div>
	<div class="accomodationBottom">
	<div class="leftDiv">
							
							<!-- <button type="button" value="cancel" id="save"  class="submitButton submit" onClick="history.back()">Cancel</button> -->
							<a class="submit" href="<ct:encode out='/secure/testtaker/accommodation/view?customerId=${accommodationsForm.customerId}'/>"><spring:message code="button.label.back"/></a>
							</div>
			<div class="rightDiv"><button type="submit" value="save" id="save"  class="submitButton submit">Save</button>
							</div></div>
							 <form:hidden path="customerId"/> 
							<form:hidden path="bulkEdit"/>
						 	<form:hidden path="testId"/>
							<form:hidden path="accommodationStatus"/>
							<form:hidden path="deliveryMode"/> 
						</form:form>
		</div>
</t:base>
