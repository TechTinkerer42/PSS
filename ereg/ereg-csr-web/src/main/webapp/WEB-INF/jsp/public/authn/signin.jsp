<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Sign In)">
	<div class="headContainer ">
		<br>
		<h2>Login for Administrators</h2>
		<span class="required_notification">* Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<div class="formContainer">
		<div class="create_form">
			<div class="formRow">
				<form:form method="POST" action='${actionValue}'>
					<div class="formContainer">
						<div class="create_form">
							<div class="formRow">
								<c:if test="${not empty error}">
								<div class="errorblock">
									${error} <br />
								</div>
								</c:if>
								<div class="create_form">
									<ul>
										<li>
											<label for="username">User Name:</label> <input type='text' name=${username} value='' required="required" id="username">
										</li>
										<li>
											<label for="password">Password:</label> <input type='password' name=${password} required="required" id="password" />
										</li>
									</ul>
								</div>
								<p></p>
							</div>
						</div>
					</div>
					<c:set var="AUTH_MECHANISM" value="<%=ApplicationConfiguration.AUTH_MECHANISM_OAM %>" />

					 <c:if test="${authMechanism==AUTH_MECHANISM}">
						<input type="hidden" name=${lockpage} value=${lockpageValue}/>
						<input type="hidden" name=${expiredpwdpage} value=${expiredpwdpageValue}/>
					 </c:if>
					<div class="formButtonsContainer">
						<span style="margin: 10px 0 10px 158px; display: block; clear: both;">
							<button type="submit" name="submit" class="submit">Sign In</button>
						</span>
						<br>
				<span style="margin-left:125px;">
					<a href="${forgotUserName}"><spring:message code="login.forgotusername.link.display"/></a> | 
					<a href="${forgotPassword}""><spring:message code="login.forgotpassword.link.display"/></a>
				</span>
						
						<br>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<!-- Form Canvas ends here -->
</t:base>