<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*"%>
<%@ page import="org.ets.ereg.csr.web.util.Constant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:base title="ETS - eREG (Test Taker Roster Search)">
	<link
		href='<spring:url htmlEscape="true" value="/commonweb/css/jquery-ui.css"/>'
		rel="stylesheet">
	<script
		src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-ui.js"/>'></script>
	<script
		src='<spring:url htmlEscape="true" value="/js/ereg_csr_testTakerRoster.js"/>'></script>


	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Test Taker Roster</h1>
			</div>
		</div>
		<span class="required_notification">* Required Information</span>
	</div>
	<form:form method="post" modelAttribute="testTakerRosterSearchForm"
		action="search" id="searchForm" onsubmit="return false">
		<div class="formContainer">
			<div class="create_form">
				<div Class="toggleMap"></div>
				<span style="padding: 2px 0; margin: 2px; display: inline-block;"><b>Search
						Criteria</b></span>
				<div class="formRow searchCriteriaPanel">
					<div class="clear"></div>
					<sec:authentication property="principal" var="user" scope="page" />
					<c:if test="${user.hasCSRRole()}">

						<label for="stateAgency"><span style="color: #ff0000;">*</span>State/Organization:</label>
						<form:select path="agencyId" class="stateAgency"
							required="required" onchange="javascript:loadTestCenter();">
							<form:option value="">Select</form:option>
							<c:forEach var="agency" items="${agencies}">
								<form:option value="${agency.id}">${agency.name}</form:option>
							</c:forEach>
						</form:select>
						</br>
						<label for="testCenterName"><span style="color: #ff0000;">*</span>Test
							Center Name:</label>
						<form:select path="testCenterId" class="testCenterName"
							required="required">
							<form:option value="">Select</form:option>
						</form:select>
					</c:if>
					
					<c:if test="${user.hasTCARole()}">
						<label for="testCenterName"><span style="color: #ff0000;">*</span>Test
							Center Name:</label>
						<c:choose>
							<c:when test="${testCenterNames.size() eq  1}">
								<c:forEach var="entry" items="${testCenterNames}">
									<label for="testCenterId">${entry.value}</label>
									<form:input type="hidden" path="testCenterId" id="testCenterId"
										value="${entry.key}" />
								</c:forEach>
							</c:when>
							<c:otherwise>

								<form:select path="testCenterId" class="testCenterName"
									required="required">
									<form:option value="">Select</form:option>
									<c:forEach var="entry" items="${testCenterNames}">
										<form:option value="${entry.key}">${entry.value}</form:option>
									</c:forEach>
								</form:select>
							</c:otherwise>

						</c:choose>
					</c:if>

					<br /> <br /> <label for="appointmentDateFrom"><span
						style="color: #ff0000;">*</span>Appointment Date:</label><span
						style="float: left; margin: 4px 0 0 0;">From:</span>
					<c:set var="monthIndex" value="1" />
					<form:select path="appointmentMonthFrom" required="required"
						class="dobMonth" width="1">
						<form:option value="">Month</form:option>
						<c:forEach items="${months}" var="month">
							<form:option value="${monthIndex}">${month}</form:option>
							<c:set var="monthIndex" value="${monthIndex+1}" />
						</c:forEach>
					</form:select>
					<c:set var="dayIndex" value="1" />
					<form:select path="appointmentDayFrom" required="required"
						class="dobDay">
						<form:option value="">Day</form:option>
						<c:forEach items="${days}" var="day">
							<form:option value="${dayIndex}">${day}</form:option>
							<c:set var="dayIndex" value="${dayIndex+1}" />
						</c:forEach>
					</form:select>
					<form:input path="appointmentYearFrom" size="4"
						id="appointmentYearFrom" maxlength="4" pattern="[0-9]{4}"
						required="required" class="dobYear" />
					<%-- <form:errors class="errorMessage" path="<%=Constant.DATE_OF_BIRTH %>"/> --%>
					<br />
					<label for="appointmentDateTo"></label><span
						style="float: left; margin: 4px 0 0 0;">To:&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<c:set var="monthIndex" value="1" />
					<form:select path="appointmentMonthTo" required="required"
						class="dobMonth" width="1">
						<form:option value="">Month</form:option>
						<c:forEach items="${months}" var="month">
							<form:option value="${monthIndex}">${month}</form:option>
							<c:set var="monthIndex" value="${monthIndex+1}" />
						</c:forEach>
					</form:select>
					<c:set var="dayIndex" value="1" />
					<form:select path="appointmentDayTo" required="required"
						class="dobDay">
						<form:option value="">Day</form:option>
						<c:forEach items="${days}" var="day">
							<form:option value="${dayIndex}">${day}</form:option>
							<c:set var="dayIndex" value="${dayIndex+1}" />
						</c:forEach>
					</form:select>
					<form:input path="appointmentYearTo" size="4"
						id="appointmentYearTo" maxlength="4" pattern="[0-9]{4}"
						required="required" class="dobYear" />
					<%-- <form:errors class="errorMessage" path="<%=Constant.DATE_OF_BIRTH %>"/> --%>
					<br /> <label for="checkInStatus">Check In Status:</label>
					<c:forEach items="${checkInStatus}" var="checkInStatus"
						varStatus="i">
						<form:checkbox path="selectedCheckInStatusCodes[${i.index}]"
							value="${checkInStatus.code}" />
						<c:out value="${checkInStatus.description}" />
					</c:forEach>
					<div class="clear"></div>
					<label for=testId>Test Title:</label>
					<form:select path="testId" onchange="javascript:loadFormCode();">
						<form:option value="">Select
						</form:option>
						<c:forEach items="${TestList}" var="test">
							<form:option value="${test.testId}">${test.testName}</form:option>
						</c:forEach>
					</form:select>
					<br /> <label for="testFormCode">Test Form:</label>
					<form:select path="testFormCode" class="testForm">
						<form:option value="">Select</form:option>
					</form:select>
					<br /> <label for="testTpye">Test Type:</label>
					<c:forEach items="${deliveryModeTypes}" var="deliveryModeType"
						varStatus="i">
						<form:checkbox path="selectedDeliveryModeType[${i.index}]"
							value="${deliveryModeType.code}" />
						<c:out value="${deliveryModeType.description}" />
					</c:forEach>
					<ct:modalWindow title="Change Status" id="dialog-confirm"
						buttonMethod="handleOk" buttonName="OK" cancelButtonName="Cancel"></ct:modalWindow>
					<ct:modalConfirmationWindow title="Information" id="dialog-confirm-message"
						buttonMethod="handleOkConfirmation" buttonName="OK"></ct:modalConfirmationWindow>	
					<ct:modalWindow title="Confirmation Screen" id="dialog-confirm-already-checkedin"
						buttonMethod="handleOkAlreadyCheckedin" buttonName="Yes"  cancelButtonName="No"></ct:modalWindow>	
					<div class="clear"></div>
					<label for="acccomodations">Accommodations:</label>
					<c:forEach items="${accomodations}" var="accomodations"
						varStatus="i">
						<form:checkbox path="selectedAccomodations[${i.index}]"
							value="${accomodations.key}" />
						<c:out value="${accomodations.value}" />
					</c:forEach>
					<div class="clear"></div>
					<form:input type="hidden" path="pageNo" id="pageNo" />
					<form:input type="hidden" path="action" id="action" />
					<form:input type="hidden" path="rowperPage" id="rowperPage" />
					<div>
						<button type="submit" value="next" id="search"
							class="submitButton submit">Search</button>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<script>
			var appointmentDefaultDate = new Date();
			$("#appointmentYearFrom").val(appointmentDefaultDate.getFullYear());
			$("#appointmentMonthFrom").val(
					appointmentDefaultDate.getMonth() + 1);
			$("#appointmentDayFrom").val(appointmentDefaultDate.getDate());

			$("#appointmentYearTo").val(appointmentDefaultDate.getFullYear());
			$("#appointmentMonthTo").val(appointmentDefaultDate.getMonth() + 1);
			$("#appointmentDayTo").val(appointmentDefaultDate.getDate());
		</script>
		<div id="searchResultsTable_length" class="dataTables_length">
			<div id="searchFormResultsTable" style="border: none;"></div>
		</div>

	</form:form>
</t:base>