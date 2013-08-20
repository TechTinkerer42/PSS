<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Duplicate Account">

<!-- Form Canvas starts here -->
<div class="formContainer">




<div class="create_form">






<c:if test="${not empty error}">
		<div class="errorblock">			
			${error} <br/>
		</div>
	</c:if>
	
	
	<form method='POST' action=${actionValue}> 

 
<ul>
<li><h1><spring:message code="duplicateAccount"/></h1></li>
<li><spring:message code="duplicateAccountDetail1"/><br><spring:message code="duplicateAccountDetail2"/></li>
<li><h2><spring:message code="returningUser"/></h2></li>
<li><label for="username"><spring:message code="username"/>:</label>
<input type='text' name=${username} value='' required="required" id="username">
</li>

<li><label for="password"><spring:message code="password"/>:</label>
<input type='password' name=${password} required="required" id="password"/>
</li>

<li></li>





</ul>



<div class="row-fluid">
<div class="span6"><a href="<c:out value='${prev_url}'/>" class="submit"><spring:message code="back"/></a>
</div>
<div class="span6"><span class="right"><button type="submit" name="submit" class="submit"><spring:message code="signIn"/></button></span></div>
</div>
<a href="/ereg-web/public/profile/accountrecovery"><spring:message code="forgotUsername"/></a> | <a href="/ereg-web/public/profile/accountrecovery"><spring:message code="forgotPassword"/></a><br>
<p></p>

		
		</form>
 

 <c:url value="/profile/" var="prev_url"/> 
<p>If you want to modify your Personal Information,click Back to return to previous page.<br>If you need any help,contact[xxxx] or email [xxx]</p>
		
			</div>

</div> <!--  Form Canvas ends here -->

</t:base>