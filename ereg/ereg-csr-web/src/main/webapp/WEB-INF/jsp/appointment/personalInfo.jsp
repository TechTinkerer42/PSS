<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Personal Info)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>
				<spring:message code="schedulenewappointmentpage.heading" arguments="${appointmentForm.profile.customer.firstName}, ${appointmentForm.profile.customer.lastName}"/>
				</h1>
			</div>
		</div>
		<img src="${pageContext.request.contextPath}/commonweb/img/sch_appt_step_01.png">
		<br>
		<h2>Personal Information</h2>  <sec:authorize access="hasRole('PERMISSION_UPDATE_CUSTOMER')">(<a href="<spring:url value="/secure/tcaScheduling/editProfile"/>">Edit</a>)</sec:authorize>
		<span class="required_notification"></span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="appointmentForm" action="personal">
		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="review_form">
				<div class="formRow">
					<div class="review_form">
						<ul>
							<li><label>First or Given Name</label> <c:out
									value="${appointmentForm.profile.customer.firstName}" /></li>
							<div class="clear"></div>
							<li><label>Middle Initial</label> <c:out
									value="${appointmentForm.profile.customer.middleInitial}" />
							</li>
							<div class="clear"></div>
							<li><label>Last or Family Name</label> <c:out
									value="${appointmentForm.profile.customer.lastName}" /></li>
							<div class="clear"></div>
							<li><label>Date of Birth</label> <ct:dateTime part="date"
									value="${appointmentForm.profile.customer.dateOfBirth}" />
							</li>
							<div class="clear"></div>
							<li><label>Gender</label> <c:out
									value="${appointmentForm.profile.customer.gender.description}" />
							</li>
							<div class="clear"></div>
							<li><label>Email:</label> <c:out
									value="${appointmentForm.profile.customer.emailAddress}" />
							</li>
							<div class="clear"></div>
							<li><label>Country/Location</label> <c:out
									value="${appointmentForm.profile.address.country.name}" />
							</li>
							<div class="clear"></div>
							<li><label>Address Line 1</label> <c:out
									value="${appointmentForm.profile.address.addressLine1}" />
							</li>
							<div class="clear"></div>
							<li><label>Address Line 2</label> <c:out
									value="${appointmentForm.profile.address.addressLine2}" />
							</li>
							<div class="clear"></div>
							<li><label>City</label> <c:out
									value="${appointmentForm.profile.address.city}" /></li>
							<div class="clear"></div>
							<li><label>State</label> <c:out
									value="${appointmentForm.profile.address.state.name}" /></li>
							<div class="clear"></div>
							<li><label>ZIP Code</label> <c:out
									value="${appointmentForm.profile.address.postalCode}" /></li>
							<div class="clear"></div>
							<li>
								<label>Primary Phone</label>
								<ct:phone phoneNumber="${appointmentForm.profile.primaryPhone.phoneNumber}" 
									countryAbbr="${appointmentForm.profile.primaryPhone.country.abbreviation}" 
									extension="${appointmentForm.profile.primaryPhone.phoneExtension}" 
									phoneType="${appointmentForm.profile.primaryPhone.phoneType.description}" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Alternate Phone</label>
								<ct:phone phoneNumber="${appointmentForm.profile.alternatePhone.phoneNumber}" 
									countryAbbr="${appointmentForm.profile.alternatePhone.country.abbreviation}" 
									extension="${appointmentForm.profile.alternatePhone.phoneExtension}" 
									phoneType="${appointmentForm.profile.alternatePhone.phoneType.description}" />
							</li>
						</ul>
					</div>
					<br /> <br />

				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01"">
					<a class="othbutton" href="<ct:encode out="/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}" />">Cancel</a>
					<button class="submit" type="submit" style="float: right;margin:-7px 0 0 0">Next
								&gt;</button>
				</div>
			</div>
		</div>					
	</form:form>
</t:base>