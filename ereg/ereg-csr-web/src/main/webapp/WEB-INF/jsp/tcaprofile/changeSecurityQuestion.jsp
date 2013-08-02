<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.csr.web.util.Constant"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS - eREG (Edit Security Questions)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Update Profile</h1>
			</div>
		</div>

		<br>
		<h2>
		<spring:message code="profile.tca.changeSecurityQuestion.heading" />		
		</h2>
		<span class="required_notification">* Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="post" modelAttribute="tcaProfileForm">
		<div class="formContainer">
			<div class="create_form">
				<h1>Security Question</h1>
				<c:url value="/profile/" var="prev_url" />
				<div class="formRow">
					<ul>
						<li><label for="password">Password</label> <form:password
								path="<%=Constant.OLD_PASSWORD %>" size="30" id="password"
								required="required" /> <form:errors
								path="<%=Constant.OLD_PASSWORD %>" /></li>
						<li><label for="securityQuestion">Security Question</label> <form:select
								path="<%=Constant.SECURITY_QUESTION %>" id="securityQuestion"
								required="required">
								<form:option value="">Select</form:option>
								<c:forEach items="${challengeQuestions}" var="challengeQuestion">
									<form:option value="${challengeQuestion.id.toString()}">${challengeQuestion.question}</form:option>
								</c:forEach>
							</form:select> <form:errors class="errorMessage"
								path="<%=Constant.SECURITY_QUESTION %>" /></li>

						<li><label for="securityAnswer">Answer</label> <form:input
								path="<%=Constant.SECURITY_ANSWER %>" size="30"
								id="securityAnswer" required="required" /> <form:errors
								class="errorMessage" path="<%=Constant.SECURITY_ANSWER %>" /></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<c:url value="/secure/home" var="prev_url" />
				<div class="ereg_span_01"">
					<a class="submit" href="<c:out value='${prev_url}'/>">Cancel</a> <span
						class="right" style="margin: -4px 0 0 0;"><button
							class="submit" type="submit">Submit</button></span>
				</div>
			</div>
		</div>
	</form:form>
</t:base>