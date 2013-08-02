<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Update password">

<!-- Form Canvas starts here -->
<div class="formContainer">

<div class="create_form">

<h1><spring:message code="changePassword"/></h1>

<c:if test="${not empty  STATUS_MESSAGE}"> 
	<p style="color:red; margin:10px 0 0 0;">
		<spring:message code="${STATUS_MESSAGE}"/>
	</p>
</c:if>
	

<ct:encode out="/secure/testtaker/changePassword?customerId=${customerId}" var="actionUrl"/>
<form:form method="post" modelAttribute="profileForm" action="${actionUrl}">
<div class="formRow">
<ul>
	<li>
		<label for="newPassword"><spring:message code="newPassword"/></label>
		<form:password path="<%=ProfileForm.CANDIDATE_PASSWORD %>" size="30" id="newPassword" required="required"/>
		<form:errors path="<%=ProfileForm.CANDIDATE_PASSWORD %>"/>
	</li>
	
	<li>
		<label for="newPasswordConfirm"><spring:message code="newPasswordConfirm"/></label>
		<form:password path="<%=ProfileForm.PASSWORD_CONFIRM %>" size="30" id="newPasswordConfirm" required="required"/>
		<form:errors path="<%=ProfileForm.PASSWORD_CONFIRM %>"/>
	</li>
</ul>

 <div class="row-fluid">
    <div class="span6">
	<ct:encode out="/secure/testtaker/view?customerId=${customerId}" hrefCode="back" hrefStyleClass="submit"/>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="submit"/></button></span></div>
    </div>

</div>


</form:form>
			</div>

</div> <!--  Form Canvas ends here -->

</t:base>