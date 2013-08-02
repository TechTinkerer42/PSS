<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>

<t:base title="ETS - eREG (Profile Created)">
<div class="formContainer">
	<div class="create_form">
		<h2>Create Profile</h2>
		<hr>
		<h3>Account Created</h3>
		<hr>
		<h3>		
		<spring:message code="profile.create.accountcreated.successMessagewithuser" />
		<c:out value="${username}" />
		</h3>
		<br>
		<spring:message code="login.username" />:
		<c:out value="${userID}" />
		<br>
		<br>
		 <div class="row-fluid">
		<div class="span6"><a class="submit" href="<ct:encode out="/secure/testtaker/view?customerId=${profileForm.profile.customer.id}" />" >View Test taker </a></div>
		<div class="span6"><a class="submit" href="<c:url value='/secure/home' />"> CSR Home </a></div>
		</div>
		<br>
	</div>
</div>	
</t:base>