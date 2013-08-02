<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Review Appointment)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Schedule New Appointment for 
					<c:out value="${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName}" />
				</h1>
			</div>
		</div>
		<img src="${pageContext.request.contextPath}/commonweb/img/sch_appt_step_03.png">
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
							<h3>Appointment Information</h3>
						</div>
						<c:forEach items="${appointmentForm.appointments}"
							var="appointment" varStatus="status">
							<div class="clear"></div>
							<h4>Appointment ${status.index + 1}</h4>
							<ul>
								<li><label>Test Title:</label> <c:out
										value="${appointment.booking.testVariation.test.testName}" /></li>
								<div class="clear"></div>
								<li><label>Test Date:</label>
									<c:out value="${booking.testCenter.globalTimeZone.code}" />
								 <ct:dateTime part="date"
										value="${appointment.booking.appointmentDateTime}" /></li>
								<div class="clear"></div>
								<li>
									<label>Test Time:</label>
									<ct:dateTime part="time" value="${appointment.booking.appointmentDateTime}" />
								</li>
								<div class="clear"></div>
								<li><label>Test Center:</label> <c:out
										value="${appointment.booking.testCenter.name}" /></li>
								<div class="clear"></div>
								<li><label>Test Type:</label> <c:out
										value="${appointment.booking.testVariation.deliveryModeType.description}" />
								</li>
								<div class="clear"></div>
								<li><label>Accommodations:</label>
									<c:if test="${appointment.booking.bookingAccommodations.size()>0}">
										<c:forEach  var="accommodation" items="${appointment.booking.bookingAccommodations}" varStatus="status">
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
								<li><label>Test Language:</label> <c:out
										value="${appointment.booking.testVariation.languageType.description}" />
								</li>
								<div class="clear"></div>
								<c:if test="${appointment.booking.form != null}">
									<li><label>Test Form:</label> <c:out
											value="${appointment.booking.form.formDesc}" /></li>
									<div class="clear"></div>
									<li>
										<label>Form Code:</label>
										<c:out value="${appointment.booking.form.formCode}" />
									</li>
									<div class="clear"></div>
								</c:if>
								<li>
									<label>Comments:</label> 
									<c:out value="${appointment.booking.comments}" />
								</li>
							</ul>
						</c:forEach>
					</div>
				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01">
					<button type="submit" class="submit" name="goBack">&lt;Back</button>
					<a class="othbutton" href="<ct:encode out="/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}" />">Cancel</a>
					<button class="submit" type="submit" style="float: right;">Submit</button>
					</span>
				</div>
			</div>
		</div>

	</form:form>
</t:base>