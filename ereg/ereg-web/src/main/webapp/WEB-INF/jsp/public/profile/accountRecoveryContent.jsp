<%@ page import="org.ets.ereg.web.profile.form.ProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:choose>


<c:when test="${!empty messageList }">
<ul>
<c:forEach var="msg" items="${ messageList}">
<c:choose>
<c:when test="${msg eq 'Email message sent.' }">
<li style="color:green;"><c:out value="${ msg}"/></li>
</c:when>
<c:otherwise>
<li><c:out value="${ msg}"/></li>
</c:otherwise>
</c:choose>
</c:forEach></ul>

</c:when>
<c:otherwise>
<span id="statusMessages" style="color:red;"></span>
<form:form method="post" modelAttribute="profileForm" action="${formaction}" id="submitAccounRecoveryForm" onsubmit="return false;">
<div class="formRow">


	<ul>
	<c:choose>
	<c:when test="${forgotUsername }">
	<li>
	<label for="emailAddress"><spring:message code="emailAddress"/></label>
	<form:input path="<%=ProfileForm.EMAIL_ADDRESS %>" size="30" id="emailAddress"  required="required" />
	<form:errors class="errorMessage" path="<%=ProfileForm.EMAIL_ADDRESS %>"/>
	</li>
	</c:when>
	<c:otherwise>
	<c:if test="${customerInfo== null }">
	<li>
	<label for="username"><spring:message code="username"/></label>
	<form:input path="<%=ProfileForm.USERNAME %>" size="30" id="username" required="required" />
	<form:errors class="errorMessage" path="<%=ProfileForm.USERNAME %>"/>
	<span><span id="getUserInfo" class="getUserInfo submit" style="">get user details</span></span>
	</li> 
<span id="userDetails" ></span>
</c:if>
	</c:otherwise>
	</c:choose>
	
	<c:if test="${customerInfo!= null }">
	<li>
	<label for="answer">${customerInfo.challengeQuestion.question}</label>
	<form:input path="profile.customer.challengeAnswer" size="30" id="answer" required="required" /></li>
	<li><label for="password"><spring:message code="newPassword"/></label>
	<form:password path="<%=ProfileForm.PASSWORD %>" size="30" id="password" required="required" />
	<form:errors class="errorMessage" path="<%=ProfileForm.PASSWORD %>"/>
	<span class="form_hint">Hint: The password should have minimum 8 characters and maximum 16 characters. It should contains atleast 1 capital letter and a symbol or a digit</span>
	
	</li>
	<li>
	<label for="passwordConfirm"><spring:message code="newPasswordConfirm"/></label>
	<form:password path="<%= ProfileForm.PASSWORD_CONFIRM %>" size="30" id="passwordConfirm" required="required"/>
	<form:errors class="errorMessage" path="<%= ProfileForm.PASSWORD_CONFIRM %>"/>
	</li>
	<input type="hidden" name="profile.customer.id" value="${customerInfo.id}"/>
	</c:if>
	
	
</ul>

<c:if test="${customerInfo== null }">
 <div class="row-fluid">
    <div class="span6">
	<c:url value="/secure/home" var="prev_url"/>
	<a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="back"/></a>
</div>
    <div class="span6"><span class="right">
    <c:choose>
    <c:when test="${forgotUsername }">
    <button class="submit" type="submit" id="submitButton"><spring:message code="submit"/></button>
    </c:when>
    <c:otherwise>
     <button class="submit" type="submit" id="submitButton" disabled="disabled"><spring:message code="submit"/></button>
    </c:otherwise></c:choose>
   
    
    
    </span></div>
    </div>
</c:if>
</div>


</form:form>
</c:otherwise>
</c:choose>

<c:if test="${formaction!=null }">
<script type="text/javascript">
$(document).ready(function () {
	
	$("#username").change(function(){
		 // console.log("The text has been changed.");
		  
		  
		});
	
$(".getUserInfo").on('click', function () {
	//console.log("entering the get user info");
	var usrnam=$("#username").val();
	$.ajax({
		url: window.location.protocol + "//" + window.location.host + "${pageContext.request.contextPath}/public/profile/getUserInfo",
		data: {username:usrnam},
		success: 	function(data) {
			$("#userDetails").html(data);
			$("#submitButton").prop('disabled', false);
		},
		dataType: 'html'
		});
		
	});
    $('form:first *:input[type!=hidden]:first').focus();
	$("#submitButton").on('click', function () {
		var form = $('form#submitAccounRecoveryForm').serialize();
		var formAction = $("#submitAccounRecoveryForm").attr("action");
		var target='statusMessages';
		ajaxPost(formAction,form,target ,'POST',
		function(data) {
			//console.log(data);
		
			$("#statusMessages").html(data);
		}			
		);
});
	});
</script></c:if>

