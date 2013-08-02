<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Update Security Info">

<!-- Form Canvas starts here -->
<div class="formContainer">




<div class="create_form">
<h1><spring:message code="changeSecurityQuestion"/></h1>
<c:url value="/profile/" var="prev_url"/> 
<form:form method="post" modelAttribute="profileForm">
<div class="formRow">
 	<c:set var="STATUS_OK" value="<%=ProfileForm.STATUS_OK%>" />
	<c:set var="STATUS_KO" value="<%=ProfileForm.STATUS_KO%>" />
  	<c:if test="${profileForm.statusCode==STATUS_KO}">
 		<c:out value="${profileForm.statusMessage}"/>
 	</c:if>
	<ul>
	<li>
	<label for="password"><spring:message code="password"/></label>
	<form:password path="<%=ProfileForm.OLD_PASSWORD %>" size="30" id="password" required="required" />
	<form:errors path="<%=ProfileForm.OLD_PASSWORD %>"/>
	</li>
	<li>
	<label for="securityQuestion"><spring:message code="challengeQuestion"/></label>
	<form:select path="<%=ProfileForm.SECURITY_QUESTION %>" id="securityQuestion">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${challengeQuestions}" var="challengeQuestion">
			<form:option value="${challengeQuestion.id.toString()}">${challengeQuestion.question}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.SECURITY_QUESTION %>"/>
	</li>
	<li>
	<label for="challengeAnswer"><spring:message code="challengeAnswer"/></label>
	<form:input path="<%=ProfileForm.SECURITY_ANSWER %>" size="30" id="challengeAnswer" required="required"/>
	<form:errors path="<%=ProfileForm.SECURITY_ANSWER %>"/>
	</li>
	</ul>

	
 <div class="row-fluid">
    <div class="span6">
	<c:url value="/secure/home" var="prev_url"/>
	<a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="back"/></a>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="submit"/></button></span></div>
    </div>

</div>
</form:form>




	
			</div>

</div> <!--  Form Canvas ends here -->

</t:base>