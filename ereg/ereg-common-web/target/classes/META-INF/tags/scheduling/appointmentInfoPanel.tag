<%@ attribute name="index" required="true" %>

<%@ attribute name="needForm"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:choose>
	<c:when test="${needForm == true}"><c:set var="form" value="appointmentForm." /></c:when>
	<c:otherwise><c:set var="form" value="" /></c:otherwise>
</c:choose>

<h3 class="accordionHead">Appointment <span class="accordionIndex">${index + 1}</span>&nbsp;&nbsp;&nbsp;&nbsp;
	<span class="appointmentSumary"></span>
	<a style="float:right; color: red; font-size:12px;" onclick="removeAppointment(${index})">
		<img src="${pageContext.request.contextPath}/commonweb/img/remove.png" style="margin:1px 0 2px 0;">&nbsp;&nbsp;Remove
	</a>
</h3>
<div class="accordionDiv">
	<form:hidden path="${form}appointments[${index}].booking.testCenter" value="${appointmentForm.testCenter.id}"/>
	<form:hidden path="${form}appointments[${index}].booking.testVariation.deliveryModeType" />
	<ul class="accordionUl">
		<li>
			<label><spring:message code="scheduling.label.testTitle" />:</label>
			<select id="testTitle${index}" onchange="$(this).next('input').val($(this).val());getReferenceData(event, ${index});" class="testName" required="required">
				<c:choose>
					<c:when test="${source == 'reschedule'}">
						<option value="${appointmentForm.reschedulingTest.testId}">${appointmentForm.reschedulingTest.testName} (rescheduling)</option>
					</c:when>
					<c:when test="${source ne 'reschedule'}">
						<option value="">Select</option>
					</c:when>
				</c:choose>
				<c:forEach items="${appointmentForm.tests}" var="testVO" varStatus="status">
					<c:choose>
						<c:when test="${testVO.status == 0}">
							<option value="${testVO.test.testId}">${testVO.test.testName}</option>
						</c:when>
						<c:when test="${testVO.status == 1}">
							<option value="${testVO.test.testId}" disabled="disabled">${testVO.test.testName} (Pending)</option>
						</c:when>
						<c:when test="${testVO.status == 2}">
							<option value="${testVO.test.testId}" disabled="disabled">${testVO.test.testName} (Scheduled)</option>
						</c:when>
					</c:choose>
				</c:forEach>
			</select>
			<form:errors path="${form}appointments[${index}].booking.testVariation.id.testId" cssClass="errors" />
			<form:hidden path="${form}appointments[${index}].booking.testVariation.id.testId"/>
		</li>
		<li class="hiddenOnLoad${index}" style="border-bottom:0; margin:4px 0 0 0; display: none;">
			<label style="margin:0 0 0 0;">Test Language:</label>
			<div class="testLanguage"></div>
			<form:errors path="${form}appointments[${index}].booking.testVariation.languageType" cssClass="errorMessage" />
		</li>
		<li class="hiddenOnLoad${index}" style="display: none; margin:20px 0 0 0;">
			<label>Test Date and Time:</label><span class="testTypeSpan"></span> <span class="dateSpan"></span> <span class="timeSpan"></span>
			<a class="showDatePicker linkButton">
				<img src="${pageContext.request.contextPath}/commonweb/img/calendar.png" style="margin:-3px 0 0 0;" />&nbsp;
				Select Date and Time<input type="hidden" class="panelIndex" value="${index}" />
			</a>
			<form:hidden path="${form}appointments[${index}].deliveryModeDescription" />
			<form:hidden path="${form}appointments[${index}].testDate" />
			<form:hidden path="${form}appointments[${index}].testTimeHour" />
			<form:errors path="${form}appointments[${index}].booking.testVariation.deliveryModeType" cssClass="errorMessage" />
			<form:errors path="${form}appointments[${index}].booking.appointmentDateTime" cssClass="errorMessage" />
		</li>
		
		<li>
			<div id="accommodationDiv${index}"></div>
		</li>
	</ul>
</div>