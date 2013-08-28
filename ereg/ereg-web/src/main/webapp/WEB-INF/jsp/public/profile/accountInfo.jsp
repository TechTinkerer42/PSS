<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base title="Create Profile - Account Info">

<div class="headContainer ">


<div class="row">
<div class="span9"><h1><spring:message code="createAccount"/></h1></div>
</div>

<img src='<spring:url value="/commonweb/img/step4.jpg" htmlEscape="true"/>' alt=""><br><br>
  
             <h2><spring:message code="accountInformation"/></h2>
             <span class="required_notification"><spring:message code="requiredInformation"/></span>


</div>


 	<c:set var="STATUS_OK" value="<%=ProfileForm.STATUS_OK%>" />
	<c:set var="STATUS_KO" value="<%=ProfileForm.STATUS_KO%>" />
  	<c:if test="${profileForm.statusCode==STATUS_KO}">
		<div style="margin:20px;">
		<div class="errorblock">	
		<strong><i class="icon-exclamation-sign"></i><spring:message code="errors"/></strong><br/>		
			<br/>
		</div>
		</div>
 	</c:if>

<!-- Form Canvas starts here -->
<div class="formContainer">




<div class="create_form">
<c:url value="/public/profile/background" var="prev_url"/> 
<form:form method="post" modelAttribute="profileForm">

<div class="formRow">

 


<ul>
  
  
  <li>

	<label for="username" class="required"><spring:message code="username"/>:</label>
	<form:input path="<%=ProfileForm.USERNAME %>" size="30" id="username" required="required" />
	<form:errors class="errorMessage" path="<%=ProfileForm.USERNAME %>"/>
	<span class="form_hint">Hint: The User Name must be between 6 and 16 characters (letters and/or numbers only).</span>
</li>

<li>
	<label for="password" class="required"><spring:message code="password"/>:</label>
	<form:password path="<%=ProfileForm.PASSWORD %>" size="30" id="password" required="required" />
	<form:errors class="errorMessage" path="<%=ProfileForm.PASSWORD %>"/>
	<span class="form_hint">Hint: The password should have minimum 8 characters and maximum 16 characters, contain at least one uppercase letter (A through Z) and 3. contain at least one non-alphabetic character (for example,!, $, #, %) OR one number (0 through 9).</span>
	</li>


	<li>
	<label for="passwordConfirm" class="required"><spring:message code="passwordConfirm"/>:</label>
	<form:password path="<%= ProfileForm.PASSWORD_CONFIRM %>" size="30" id="passwordConfirm" required="required"/>
	<form:errors class="errorMessage" path="<%= ProfileForm.PASSWORD_CONFIRM %>"/>
	</li>

	<li>
	<label for="securityQuestion" class="required"><spring:message code="challengeQuestion"/>:</label>
	<form:select path="<%=ProfileForm.SECURITY_QUESTION %>" id="securityQuestion" required="required">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${challengeQuestions}" var="challengeQuestion">
			<form:option value="${challengeQuestion.id.toString()}">${challengeQuestion.question}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.SECURITY_QUESTION %>"/>
	</li>

	<li>
	<label for="securityAnswer"><spring:message code="challengeAnswer"/></label>
	<form:input path="<%=ProfileForm.SECURITY_ANSWER %>" size="30" id="securityAnswer" maxLength="25" required="required"/>
	<form:errors class="errorMessage" path="<%=ProfileForm.SECURITY_ANSWER %>"/>

	</li>

   <li>
       <h2><spring:message code="termsAndConditions"/></h2>
   </li>

   <li>
   
   <div id="terms-conditions">
   I Agree to the <a href="http://www.ets.org/legal/terms/" target="_blank"><b>Terms and Conditions</b></a> governing use and access to ETS websites and to the ETS <b><a href="http://www.ets.org/legal/privacy/" target="_blank">Privacy and Security Policy</a></b>.
 <br><br>

<!--

<div class="newcheckbox">
<c:set var="AGREE_TERMS" value="<%=ProfileForm.AGREE_TERMS%>" />
<input type="checkbox" id="c1" name="${AGREE_TERMS}" />
            <label for="c1"><span></span><strong>I agree to the term and conditions.</strong></label>

</div>
-->

<div class="termsCheckbox">
<form:checkbox path="<%=ProfileForm.AGREE_TERMS%>" id="termsConditions" name="cc"/> 

<strong><spring:message code="agreeTerms"/></strong>  <span class="checkConditions"></span></div>
<h2><form:errors class="errorMessage" path="<%=ProfileForm.AGREE_TERMS %>"/></h2>

   </div>


   </li>


	</ul>
	

	
	

 <div class="row-fluid">
    <div class="span6"><a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="back"/></a>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="next"/></button></span></div>
    </div>


	
	

	</div>









</form:form>

</div>

</div>


</t:base>