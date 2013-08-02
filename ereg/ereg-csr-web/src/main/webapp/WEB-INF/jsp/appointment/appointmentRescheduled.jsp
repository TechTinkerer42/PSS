<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Appointment Rescheduled)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Appointment Rescheduled for 
					<c:out value="${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName}" />
				</h1>
				<div class="ereg_span_01" style="float:right;">
					<a href=""><img src="${pageContext.request.contextPath}/commonweb/img/print.png" alt="ETS">Print</a>
				</div>
			</div>
		</div>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="appointmentForm">
		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="create_form">
				<div class="formRow">
					<div class="create_form">
						<h2>Appointment Information</h2>
						<div class="apt-sch-table">
							<table>
								<thead>
									<tr>
										<th>Appointment Number</th>
										<th>Test Title</th>
										<th>Date</th>
										<th>Test Center</th>
										<th>Time</th>
										<th>Test Type</th>
										<th>Test Form &amp; Language</th>
										<th>Accommodation</th>
										<th>Comments</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><a
											href="<ct:encode out="/secure/tcaScheduling/view/?bookingId=${booking.id}" />">
												<ct:format out="${booking.etsApptID}" />
										</a></td>
										<td><c:out value="${booking.testVariation.test.testName}" /></td>
										<td><ct:dateTime part="date"
												value="${booking.appointmentDateTime}" /></td>
										<td><c:out value="${booking.testCenter.name}" /></td>
										<td>
											<ct:dateTime part="time" value="${booking.appointmentDateTime}" />
										</td>
										<td><c:out
												value="${booking.testVariation.deliveryModeType.description}" /></td>
										<td>
											<c:choose>
												<c:when test="${booking.form != null}">
													<c:out value="${booking.form.formDesc}" />
													<c:out value=" ${booking.form.formCode}" />
												</c:when>
												<c:otherwise>
													<c:out value=" ${booking.testVariation.languageType.description}" />
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${booking.bookingAccommodations.size() >0}">
													Yes
												</c:when>
												<c:otherwise>No</c:otherwise>
											</c:choose>
										</td>
										<td><c:out value="${booking.comments}" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<br /> <br />

				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="span6">
					<a class="submit"
						href="<ct:encode out='/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}' />">Go
						to Test Taker's Summary</a>
				</div>
			</div>
		</div>
	</form:form>
</t:base>