<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Username and Password)">
<div class="headContainer ">
	<div class="row">
		<div class="span9"><h1>Create Profile</h1></div>
	</div>
	<img src="${pageContext.request.contextPath}/commonweb/img/step4.jpg">
	<br>
	<h2>Username and Password</h2>
	<span class="required_notification">* Required Information</span>
</div>
<!-- Form Canvas starts here -->
<form:form method="POST" modelAttribute="profileForm">
	<div class="formContainer">
		<div class="create_form">
			<div class="formRow">
				<ul>
					<li>
						<label>Username</label>
						<form:input path="<%=ProfileForm.USERNAME%>" required="required" />
						<form:errors path="<%=ProfileForm.USERNAME%>" />
					</li>
					<li>
						<label>Password</label>
						<label id="password" style="max-width: 260px; width:100%; display:block; float:left;">
							<c:out value="${profileForm.profile.customer.password}" />
						</label>
						<%-- <form:input readonly="true" path="<%=ProfileForm.PASSWORD%>" required="required" style="display: none;" onchange="alert('here');" />
						<form:hidden path="<%=ProfileForm.PASSWORD%>" onchange="alert('Here');" /> --%>
						<button name="generatePassword" type="submit" class="submit" value="Generate Password">Generate Password</button>
						<form:errors path="<%=ProfileForm.PASSWORD%>" />
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="formButtonsContainer">
		<div class="row-fluid">
			<div class="span6"><a class="othbutton" href="<c:url value='/secure/profile/background'/>">Back</a></div>
			<div class="span6"><span class="right" style="margin:-5px 0px 0px 0px;"><button class="submit" type="submit">Next</button></span></div>
		</div>	
	</div>
</form:form>
</t:base>
				