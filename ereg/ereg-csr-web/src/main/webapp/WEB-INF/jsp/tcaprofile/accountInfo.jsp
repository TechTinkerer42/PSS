<%@ page import="org.ets.ereg.csr.web.util.Constant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS - eREG (Account Info)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Create Profile</h1>
			</div>
		</div>
		<img
			src="${pageContext.request.contextPath}/commonweb/img/tca_profile_step_02.png">
		<br>
		<h2>Username and Password</h2>
		<span class="required_notification">* Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="tcaProfileForm">
		<div class="formContainer">
			<div class="create_form">
				<div class="formRow">

					<ul>
						<li><label>Username</label> <form:input
								path="<%=Constant.USERNAME%>" required="required" /> <form:errors
								path="<%=Constant.USERNAME%>" /></li>
						<li><label for="password">Password</label> <form:password
								path="<%=Constant.PASSWORD%>" size="30" id="password"
								required="required"
								placeholder="6-16 characters, case sensitive" /> <form:errors
								class="errorMessage" path="<%=Constant.PASSWORD%>" /></li>

						<li><label for="passwordConfirm">Re-type Password</label> <form:password
								path="<%=Constant.PASSWORD_CONFIRM%>" size="30"
								id="passwordConfirm" required="required"
								placeholder="6-16 characters, case sensitive" /> <form:errors
								class="errorMessage" path="<%=Constant.PASSWORD_CONFIRM%>" /></li>

						<li><label for="securityQuestion">Security Question</label> <form:select
								path="<%=Constant.SECURITY_QUESTION%>" id="securityQuestion"
								required="required">
								<form:option value="">Select</form:option>
								<c:forEach items="${challengeQuestions}" var="challengeQuestion">
									<form:option value="${challengeQuestion.id.toString()}">${challengeQuestion.question}</form:option>
								</c:forEach>
							</form:select> <form:errors class="errorMessage"
								path="<%=Constant.SECURITY_QUESTION%>" /></li>

						<li><label for="securityAnswer">Answer</label> <form:input
								path="<%=Constant.SECURITY_ANSWER%>" size="30"
								id="securityAnswer" required="required" /> <form:errors
								class="errorMessage" path="<%=Constant.SECURITY_ANSWER%>" /></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="span6">
					<a class="submit" href="<c:url value='/tca/profile'/>">Back</a>
				</div>
				<div class="span6">
					<span class="right" style="margin: -4px 0px 0px 0px"><button class="submit" type="submit">Next</button></span>
				</div>
			</div>
		</div>
	</form:form>
</t:base>