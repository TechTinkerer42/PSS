<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Sub Form Selection)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1> 
					<spring:message code="schedulenewappointmentpage.heading" arguments="${appointmentForm.profile.customer.firstName}, ${appointmentForm.profile.customer.lastName}"/>
				</h1>
			</div>
		</div>
		<img src="${pageContext.request.contextPath}/commonweb/img/sch_appt_step_02.png">
		<br>
		<h2>Review and Submit</h2>
		<span class="required_notification"></span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="appointmentForm">

		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="review_form">
				<div class="formRow">
					<div class="review_form">
						<div>
							<h3>Appointment Information - Select Accommodated Test Sub Form</h3>
						</div>
						<c:forEach items="${appointmentForm.appointments}" var="appointment" varStatus="status">
							<c:if test="${fn:length(appointment.subForms) gt 0}">
								<div class="clear"></div>
								<h4>Appointment ${status.index + 1}</h4>
								<ul>
									<li>
										<p>
											${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName} is approved for 2 or more test form accommodations for ${appointment.booking.form.test.testName}. <br /> 
											Please select the accommodated test form that will be used for this appointment.
										</p>
									</li>
									<li>
										<label>Sub Form:</label>
										<form:select path="appointments[${status.index}].booking.form" required="required">
											<option value="">Select</option>
											<form:options items="${appointment.subForms}" itemValue="formID" itemLabel="FormDesc" />
										</form:select>
									</li>
									<div class="clear"></div>
									<li>
										<label>Test Title:</label>
										<c:out value="${appointment.booking.testVariation.test.testName}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Test Date:</label>
										<ct:dateTime part="date" value="${appointment.booking.appointmentDateTime}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Test Time:</label>
										<ct:dateTime part="time" value="${appointment.booking.appointmentDateTime}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Test Center:</label>
										<c:out value="${appointment.booking.testCenter.name}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Test Type:</label>
										<c:out value="${appointment.booking.testVariation.deliveryModeType.description}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Test Language:</label>
										<c:out value="${appointment.booking.testVariation.languageType.description}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Test Form:</label>
										<c:out value="${appointment.baseForm.formCode}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Form Code:</label>
										<c:out value="${appointment.booking.form.formCode}" />
									</li>
									<div class="clear"></div>
									<li>
										<label>Comments:</label> 
										<c:out value="${appointment.booking.comments}" />
									</li>
								</ul>
							</c:if>
						</c:forEach>
					</div>
					
					<%-- <div class="row-fluid">
						<div class="ereg_span_01">
							<a href="<ct:encode out="/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}" />">( Cancel )</a>
						</div>
					</div> --%>

				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01">
					<a class="submit" href="<spring:url value="/secure/tcaScheduling/new/info" />">&lt; Back</a>
					<a class="othbutton" href="<spring:url value="/secure/tcaScheduling/cancelAppointment" />" >Cancel</a>
					<button class="submit" type="submit" style="float: right;">Next &gt;</button>
				</div>
			</div>
		</div>

	</form:form>
</t:base>