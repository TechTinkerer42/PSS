<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>


<t:base title="ETS - eREG (Appointment Scheduled)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>New Appointment(s) Scheduled for 
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
						<h2>Personal Information</h2>
						<div class="apt-sch-table">
							<ct:personalInfoTable profile="${appointmentForm.profile}" mode="CSR" />
						</div>
						<br /> <br />
						<h2>Appointment Information</h2>
						<div class="apt-sch-table">
							<ct:appointmentInfoTable bookings="${bookings}" mode="CSR" />
						</div>
					</div>
				</div>
			</div>
			<!--  Form Canvas ends here -->


		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="span6">
					<a class="submit"
						href="<ct:encode out="/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}" />">Go
						to Test Taker's Summary</a>
				</div>
			</div>
		</div>

	</form:form>
</t:base>