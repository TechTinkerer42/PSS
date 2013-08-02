<%@ page import="org.ets.ereg.common.web.scheduling.form.AppointmentForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Not Found)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Customer Not Found</h1>
			</div>
		</div>
	</div>
	<!-- Form Canvas starts here -->
	<div class="formContainer">
		<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
		<div class="create_form">
			<p>There is no customer with this id.</p>
		</div> <!--  Form Canvas ends here -->
	</div>
</t:base>			