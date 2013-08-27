<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Login">

<div class="headContainer ">

<c:choose> 
	<c:when test="${not empty globalContextCustomer.currentProgramCode}">
		<h1><spring:message code="login.welcome.${globalContextCustomer.currentProgramCode}"/></h1>
		<p><spring:message code="login.display.message.${globalContextCustomer.currentProgramCode}"/></p>
	</c:when>
	<c:otherwise>
		<h1><spring:message code="login.welcome"/></h1>
		<p><spring:message code="login.display.message"/></p>
	</c:otherwise>
</c:choose> 

	          

</div>

<!-- Form Canvas starts here -->
<div class="formContainer">

<div class="create_form">

<div class="row-fluid">

 <div class="span12">
  
<c:if test="${not empty error}">
		<div class="errorblock">	
		<strong><i class="icon-exclamation-sign"></i><spring:message code="errors"/></strong><br/>		
			${error} <br/>
		</div>
	</c:if>
  </div>
</div>

<div class="row-fluid">
  
  <!-- New User Block Starts -->
  <div class="span6">
  <div class="newuser">
  <h2><spring:message code="login.newuser"/></h2>
 <p><spring:message code="login.newuser.message" arguments="${globalContextCustomer.currentProgramShortDescription}"/>
 
 </p>

 <FORM  ACTION='<c:url value='/public/profile/' />'>

<button type="submit" name="submit" class="submit"><spring:message code="login.button.createAccount"/></button>

</FORM>
  
  </div>
  
  </div>

  <!-- New User Block Ends -->
  
    <!-- Returning User Block Starts -->
  
  <div class="span6">
  
  <div class="returninguser">
	
	<form method='POST' action="${actionValue}"> 

 
<h2><spring:message code="login.returnuser"/></h2>

<%-- <p><spring:message code="login.returnuser.message" arguments="${globalContextCustomer.currentProgramShortDescription}"/></p> --%>
<label for="username" class="required"><spring:message code="login.username"/>:</label>
<input type='text' name=${username} value='' required="required" id="username">

<c:set var="AUTH_MECHANISM" value="<%=ApplicationConfiguration.AUTH_MECHANISM_OAM %>" />

 <c:if test="${authMechanism==AUTH_MECHANISM}">
	<input type="hidden" name=${lockpage} value=${lockpageValue}/>
	<input type="hidden" name=${expiredpwdpage} value=${expiredpwdpageValue}/>
 </c:if>
 
<br>

<label for="password" class="required"><spring:message code="login.password"/>:</label>
<input type='password' name=${password} required="required" id="password"/>



<div class="row-fluid">
<div class="span9"><span style="margin-left:125px;"><button type="submit" class="submit" name="submit"><spring:message code="signIn"/></button></span>
</div>
</div>
<span style="margin-left:125px;"><a href="${forgotUserName}"><spring:message code="login.forgotusername.link.display"/></a> | <a href="${forgotPassword}""><spring:message code="login.forgotpassword.link.display"/></a></span>
<br>
<p></p>

		
		</form>


  
  </div>
  
  </div>


    <!-- Returning User Block Ends -->

</div>




	

	
		
			</div>

</div> <!--  Form Canvas ends here -->

</t:base>
