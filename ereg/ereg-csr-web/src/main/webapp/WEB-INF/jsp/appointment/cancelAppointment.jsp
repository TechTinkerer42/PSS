<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Cancel Appointment)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>
				<spring:message code="cancelappointmentpage.heading" arguments="${appointmentForm.profile.customer.firstName}, ${appointmentForm.profile.customer.lastName}"/>
				</h1>
			</div>
		</div>
		<br>
		<h3> 
		<spring:message code="cancelappointmentpage.confirmationMessage" arguments="${appointmentForm.profile.customer.firstName}, ${appointmentForm.profile.customer.lastName}"
		/>?</h3>
		
		<span class="required_notification"></span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="booking"
		id="cancelAppointment">

		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="review_form">
				<div class="formRow">
					<c:set var="booking"
						value="${appointmentForm.appointments[0].booking}" />
					<div class="review_form">
						<h2>Appointment Details</h2>
						<br/>
						<ul>
							<li><label><b>Appointment #:</b></label> <b>
							<ct:format out="${booking.etsApptID}" /></b></li>
							<div class="clear"></div>
							<%-- <li>
									<label>Test Taker Name:</label>
									<c:out value="${booking.custFirstName} ${booking.custLastName}  " /><a target="_blank" href="<spring:url value="/secure/profile/${appointmentForm.profile.customer.id}" />">View Profile</a>
								</li>
								<div class="clear"></div> --%>
							<li><label>Test Title:</label> <c:out
									value="${booking.testVariation.test.testName}" /></li>
							<div class="clear"></div>
							<li><label>Test Date:</label> <ct:dateTime part="date"
									value="${booking.appointmentDateTime}" /></li>
							<div class="clear"></div>
							<li><label>Test Time:</label> <ct:dateTime part="time" value="${booking.appointmentDateTime}" />
							</li>
							<div class="clear"></div>
							<li><label>State/Agency</label>  
									<c:out value="${booking.testCenter.getAgency(booking.testVariation.test.programType.code).name}" /> </li>
							<div class="clear"></div>
							<li><label>Test Center:</label> <c:out
									value="${booking.testCenter.name}" /></li>
							<div class="clear"></div>
							<li><label>Test Type:</label> <c:out
									value="${booking.testVariation.deliveryModeType.description}" /></li>
							<div class="clear"></div>
							<li><label>Test Language:</label> <c:out
									value="${booking.testVariation.languageType.description}" /></li>
							<div class="clear"></div>
							<li><label>Accommodations:</label>
								<c:if test="${booking.bookingAccommodations.size()>0}">
									<c:forEach  var="accommodation" items="${booking.bookingAccommodations}" varStatus="status">
										<c:out value="${accommodation.accommodationType.description}"/>
										<c:if test="${null != accommodation.accommodationTypeValue}">
											<c:out value=" - ${accommodation.accommodationTypeValue.label}" />
										</c:if>
										<c:if test="${null != accommodation.othrAcmdtnTypTxt}">
											<c:out value=" - ${accommodation.othrAcmdtnTypTxt}" />
										</c:if>
										<br/>
									</c:forEach>
								</c:if>
							</li>
							<div class="clear"></div>
							<c:if test="${booking.form != null }">	
								<li><label>Test Form:</label> <c:out
										value="Form ${booking.form.formCode}" /></li>
								<div class="clear"></div>
							</c:if>
							<li><label>Comments:</label> <c:out
									value="${booking.comments}" /></li>
						</ul>
					</div>
					<!-- <div class="row-fluid">
							<div class="span6"><span class="right"><button class="submit" type="submit">Edit</button></span></div>
						</div> -->

				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">

			<a class="submit" onclick="$('form#cancelAppointment').submit();">Yes</a>
			<a class="submit right"
				href="<ct:encode out="/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}" />">No</a>
		</div>
	</form:form>
</t:base>