<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Appointment Canceled)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Appointment Canceled for 
					<c:out value="${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName}" />
				</h1>
			</div>
		</div>
	</div>
	<!-- Form Canvas starts here -->
	<div class="formContainer">
		<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
		<div class="create_form">
			<p>[Appointment # <b><ct:format out="${appointmentForm.appointmentNumber}" /></b> has been canceled for 
			<c:out value="${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName}" />. 
			An email has been sent to the test taker's email address.]</p>
		</div> <!--  Form Canvas ends here -->
	</div>
	
	<div class="formButtonsContainer">
		<a class="submit" href="<ct:encode out="/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}" />">Go to Test Taker's Summary</a>
	</div>
</t:base>