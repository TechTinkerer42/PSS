<%@ page import="org.ets.ereg.common.web.form.CustomerAccommodationsForm"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Add Accommodation)">
<script> 
  $(document).ready(function()
  {

	  //for table row
	  $("tr:even").css("background-color", "#EDF6FC");
	  $("tr:odd").css("background-color", "#fff");
  });
  </script>
<div class="headContainer ">
<h1>Add Accommodations For <c:out value="${firstName} ${lastName}" /></h1>
<h2><c:out value="${testTitle}"/> (<c:out value="${testType}"/>)</h2>
<!-- <span class="required_notification"><span style="color: red">* </span>Required Information</span> -->
</div>
<!-- Form Canvas starts here -->
<div class="accomodationFilter">
<form:form method="post" modelAttribute="accommodationsForm">
<c:set var="ACCOMMODATION_TYPE_CODE_OTHER" value="<%=AccommodationType.ACCOMMODATION_TYPE_CODE_OTHER%>"/>
<c:set var="accommodationGroupId" value="-1" />
<c:forEach items="${accommodations}" var="accommodation" varStatus="i">
<c:if test="${accommodationGroupId != accommodation.accommodationTypeGroup.accommodationGroup.accommodationGroupIdentifierNumber}">
<c:if test="${i.index > 0}">
</tbody>
</table>
</div>
</c:if>
<div>
<b><c:out value="${accommodation.accommodationTypeGroup.accommodationGroup.accommodationGroupDescription}"/></b>
<table class="accomodationTable">
<thead>
<tr>
<th>Accommodations</th>
<th>Expiration Date</th>
</tr>
</thead>
<tbody>
</c:if>
<c:if test="${!accommodationsForm.accommodations[i.index].existing}">
<tr>
<td>
<form:checkbox path="accommodations[${i.index}].accommodationTypeCode" value="${accommodation.code}"/> <c:out value="${accommodation.description}"/>
<c:if test="${null != accommodation.accommodationTypeValues && fn:length(accommodation.accommodationTypeValues) > 0}">
<form:select path="accommodations[${i.index}].accommodationTypeValue" required="required" multiple="false">
<c:forEach items="${accommodation.accommodationTypeValues}" var="accommodationTypeValue">
<form:option value="${accommodationTypeValue.accommodationTypeValueIdentifier}">${accommodationTypeValue.label}</form:option>
</c:forEach>
</form:select>
</c:if>
<c:if test="${accommodation.code == ACCOMMODATION_TYPE_CODE_OTHER}">
<form:textarea path="accommodations[${i.index}].accommodationTypeValue"/>
</c:if>
</td>
<td>
<c:set var="monthIndex" value="1" />
<form:select path="accommodations[${i.index}].expirationMonth" class="dobMonth">
<form:option value="">Month</form:option>
<c:forEach items="${months}" var="month">
<form:option value="${monthIndex}">${month}</form:option>
<c:set var="monthIndex" value="${monthIndex+1}"/>
</c:forEach>
</form:select>
<c:set var="dayIndex" value="1" />
<form:select path="accommodations[${i.index}].expirationDay" class="dobDay">
<form:option value="">Day</form:option>
<c:forEach items="${days}" var="day">
<form:option value="${dayIndex}">${day}</form:option>
<c:set var="dayIndex" value="${dayIndex+1}"/>
</c:forEach>
</form:select>
<form:select path="accommodations[${i.index}].expirationYear" class="accYear">
<form:option value="">Year</form:option>
<c:forEach items="${years}" var="year">
<form:option value="${year}">${year}</form:option>
</c:forEach>
</form:select>
<!-- 
<form:input path="accommodations[${i.index}].expirationYear" size="4" id="yearOfBirth" maxlength="4" pattern="[0-9]{4}" required="required" class="dobYear"/>
-->
<form:errors class="errorMessage" path="accommodations[${i.index}].expirationDate"/>
<form:hidden path="accommodations[${i.index}].originalAccommodationTypeValue"/>
<form:hidden path="accommodations[${i.index}].originalExpirationDate"/>
<form:hidden path="accommodations[${i.index}].existing"/>
</td>
</tr>
</c:if>
<c:if test="${accommodationsForm.accommodations[i.index].existing}">
<tr>
<td>
<form:checkbox path="accommodations[${i.index}].accommodationTypeCode" value="${accommodation.code}" disabled="true"/> <c:out value="${accommodation.description}"/>
<form:hidden path="accommodations[${i.index}].accommodationTypeCode"/>
<c:if test="${null != accommodation.accommodationTypeValues && fn:length(accommodation.accommodationTypeValues) > 0}">
<form:select path="accommodations[${i.index}].accommodationTypeValue" required="required" multiple="false" disabled="true">
<c:forEach items="${accommodation.accommodationTypeValues}" var="accommodationTypeValue">
<form:option value="${accommodationTypeValue.accommodationTypeValueIdentifier}">${accommodationTypeValue.label}</form:option>
</c:forEach>
</form:select>
<form:hidden path="accommodations[${i.index}].accommodationTypeValue"/>
</c:if>
<c:if test="${accommodation.code == ACCOMMODATION_TYPE_CODE_OTHER}">
<form:textarea path="accommodations[${i.index}].accommodationTypeValue"/>
</c:if>
</td>
<td>
<c:if test="${accommodation.code != ACCOMMODATION_TYPE_CODE_OTHER}">
<form:hidden path="accommodations[${i.index}].expirationMonth"/>
<form:hidden path="accommodations[${i.index}].expirationDay"/>
<form:hidden path="accommodations[${i.index}].expirationYear" size="4" id="yearOfBirth" maxlength="4" pattern="[0-9]{4}" required="required" class="dobYear"/>
<ct:dateTime part="date" value="${accommodationsForm.accommodations[i.index].expirationDate}" />
</c:if>
<c:if test="${accommodation.code == ACCOMMODATION_TYPE_CODE_OTHER}">
<c:set var="monthIndex" value="1" />
<form:select path="accommodations[${i.index}].expirationMonth" class="dobMonth">
<form:option value="">Month</form:option>
<c:forEach items="${months}" var="month">
<form:option value="${monthIndex}">${month}</form:option>
<c:set var="monthIndex" value="${monthIndex+1}"/>
</c:forEach>
</form:select>
<c:set var="dayIndex" value="1" />
<form:select path="accommodations[${i.index}].expirationDay" class="dobDay">
<form:option value="">Day</form:option>
<c:forEach items="${days}" var="day">
<form:option value="${dayIndex}">${day}</form:option>
<c:set var="dayIndex" value="${dayIndex+1}"/>
</c:forEach>
</form:select>
<form:select path="accommodations[${i.index}].expirationYear" class="accYear">
<form:option value="">Year</form:option>
<c:forEach items="${years}" var="year">
<form:option value="${year}">${year}</form:option>
</c:forEach>
</form:select>
<!-- 
<form:input path="accommodations[${i.index}].expirationYear" size="4" id="yearOfBirth" maxlength="4" pattern="[0-9]{4}" required="required" class="dobYear"/>
-->
<form:errors class="errorMessage" path="accommodations[${i.index}].expirationDate"/>
</c:if>
<form:hidden path="accommodations[${i.index}].originalAccommodationTypeValue"/>
<form:hidden path="accommodations[${i.index}].originalExpirationDate"/>
<form:hidden path="accommodations[${i.index}].existing"/>
</td>
</tr>
</c:if>
<c:set var="accommodationGroupId" value="${accommodation.accommodationTypeGroup.accommodationGroup.accommodationGroupIdentifierNumber}" />
</c:forEach>
<c:if test="${fn:length(accommodations) > 0}">
</tbody>
</table>
</div>
</c:if>
<div class="accomodationBottom">
	<div class="leftDiv">

<a class="submit" href="<ct:encode out='/secure/testtaker/accommodation/view?customerId=${accommodationsForm.customerId}'/>">Cancel</a>
</div>
	<div class="rightDiv">
<form:hidden path="accommodationStatus"/>
<button type="submit" value="save" id="save" class="submitButton submit">Save</button>
</div>
</div>
</form:form>
</div>
</t:base>
