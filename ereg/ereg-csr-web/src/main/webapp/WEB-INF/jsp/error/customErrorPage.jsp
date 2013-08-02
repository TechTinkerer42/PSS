<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (No Permission)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Permission Denied</h1>
			</div>
		</div>
	</div>
	<div class="formContainer">
		<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
		<div class="create_form">
			<br/>
			<h3>Message : You don't have permission to access this page</h3>
			<br/>
			<a href='<spring:url htmlEscape="true" value='/secure/home' />'>Back</a><br>
		</div> <!--  Form Canvas ends here -->
	</div>
</t:base>		
