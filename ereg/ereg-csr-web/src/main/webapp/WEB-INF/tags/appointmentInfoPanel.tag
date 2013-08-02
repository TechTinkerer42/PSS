<%@ tag description="Appointment Panel" %>
<%@ attribute name="index" required="true" %>
<%@ attribute name="ajax" %>
<%@ attribute name="source"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:choose>
	<c:when test="${ajax == true}"><c:set var="form" value="appointmentForm." /></c:when>
	<c:when test="${ajax ne true}"><c:set var="form" value="" /></c:when>
</c:choose>
<c:choose>
	<c:when test="${source == 'reschedule'}">
		<h3 id="appointmentHead${index}">Appointment ${appointmentForm.appointments[0].booking.etsApptID}</h3>
	</c:when>
	<c:when test="${source == 'new'}">
		<h3 id="appointmentHead${index}">Appointment <span class="headerIndex"></span> &nbsp;&nbsp;&nbsp;<span id="accordion_header${index}"></span> <span style="float:right;"><a onclick="removeAppointment(${index})"><font color="red" size="1"><img src="${pageContext.request.contextPath}/commonweb/img/remove.png">&nbsp;&nbsp;Remove</font></a></span></h3>
	</c:when>
</c:choose>
<div id="appointmentDiv${index}">
	<ul>
		<li>	
			<label><spring:message code="scheduling.label.testTitle" />:</label>
			<select id="testTitle${index}" onchange="$(this).siblings('input:first').val($(this).val());getAppointmentData(${index},testAccommodationData +','+testFormData+',' +testLanguageData +',' +testDeliveryModeData);showHideTestTitleDependentElements(${index});"  class="testName" required="required">
				<c:choose>
					<c:when test="${source == 'reschedule'}">
						<option value="${appointmentForm.reschedulingTest.test.testId}">${appointmentForm.reschedulingTest.test.testName} (rescheduling)</option>
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
		<div id="testTitleDependentContainerDiv${index}" style="display:none;">
			<li>
				<label>Test Type:</label>
				<!--TODO create generic style class  -->
	 				<%-- <input type="hidden" id="rescheduleInitialDeliveryMode" value="${appointmentForm.appointments[0].booking.form.deliveryModeType.code}"> --%>
	 				<div id="testDeliveryModesDiv${index}" >
						<%-- <input type="hidden" id="initialDeliveryMode${index}" value="${appointmentForm.appointments[index].booking.testVariation.deliveryModeType.code}"  class="radiocheck" />
	 					 --%>
 							<form:radiobutton path="${form}appointments[${index}].booking.testVariation.id.deliveryModeCode" value="${appointmentForm.appointments[index].booking.testVariation.id.deliveryModeCode}" checked="checked" />
 							
	 					<span class="testLang"></span>
	 				</div>
				<form:errors path="${form}appointments[${index}].booking.testVariation.id.deliveryModeCode" cssClass="errors" />
			</li>
			<div class="clear"></div>
			<li>
				<label>Test Language:</label>
				<div id="testLanguagesDiv${index}" >
					<div style="display:none">
						<form:radiobutton path="${form}appointments[${index}].booking.testVariation.id.languageCode" value="${appointmentForm.appointments[index].booking.testVariation.id.languageCode}" checked="checked" />
					</div>
					<span class="testLang"></span>
				</div>
				<form:errors path="${form}appointments[${index}].booking.testVariation.languageType" cssClass="errors" />
			</li>
			<div class="clear"></div>
			<li style="border-bottom:none;">
				<label>Test Date:</label>
				<%-- <label style="max-width:75px; display: none;"><span id="lblTestDate${index}"></span></label> --%>
				<form:hidden path="${form}appointments[${index}].booking.appointmentDateTime" />
				<span id="dateSpan${index}" class="dateSpan"></span> <span id="timeSpan${index}" class="timeSpan"></span>
				<a class="showDatePicker linkButton">
					<img src="${pageContext.request.contextPath}/commonweb/img/calendar.png" style="margin:-4px 0 0 0;">&nbsp;&nbsp;Select Date and Time
					<input type="hidden" class="panelIndex" value="${index}" />
				</a>
				<div class="datepicker" style="float: right;"></div>
				<form:hidden path="${form}appointments[${index}].testTimeHour" />
				<form:hidden path="${form}appointments[${index}].testTimeMinute" />
				<form:hidden path="${form}appointments[${index}].testTimeAMPM" />
				<form:errors path="${form}appointments[${index}].booking.appointmentDateTime" cssClass="errors" />
			</li>
		</div>	
		<div class="clear"></div>
		<div id="testDeliveryModeDateTimeDependentContainerDiv${index}" style="display:none;">
			<div id="appointments${index}.accommodationDiv"
					style="display: none;">
				<li>
					<label>Accommodations:</label>
					<jsp:include page="activeAccommodations.jsp"></jsp:include>
				</li>	
			</div>
			<div class="clear"></div>
			</div>
			<div id="appointments${index}.formDiv" style="display:none;">
				<li>
					<label>Test Form:</label>
					<form:select path="${form}appointments[${index}].baseForm">
						<form:options items="${testForms}" />
					</form:select>
					<form:hidden path="${form}appointments[${index}].baseFormId" />
				</li>
				
				<div class="clear"></div>
			<li>
				<label>Comments:</label>
				<form:textarea path="${form}appointments[${index}].booking.comments"/>
			</li>
		</div>
	</ul>
</div>

