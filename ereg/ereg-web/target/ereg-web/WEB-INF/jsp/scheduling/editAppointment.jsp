<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<t:base title="ETS - eREG (Username and Password)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9"><h1><spring:message code="scheduling.label.editAppointment" /></h1></div>
		</div>
		<img src='<spring:url htmlEscape="true" value="/img/sch_appt_ftt_step_01.png"/>'><br>
		<span class="required_notification">* <spring:message code="scheduling.label.requiredInformation" /></span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="appointmentForm"></form:form>
	<div class="formContainer">
		<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
		<div class="create_form">
			<div class="formRow">
			
			</div>
		</div> <!--  Form Canvas ends here -->
	</div>
	<div class="formButtonsContainer">
		<a class="othbutton" style="margin:0 -100px 0 0;" href="<spring:url value="/secure/appointment/cancelAppointment" />" >Cancel</a>
		<button class="submit" type="submit" style="float:right; margin:-10px 20px 0 0;">Next &gt;</button>
	</div>

</t:base>