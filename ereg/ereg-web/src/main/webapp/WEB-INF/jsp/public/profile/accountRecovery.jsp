<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Account Recovery">
<style>
.submit, button.submit {
   background: -moz-linear-gradient(center top , #1982D3, #1F95F0) repeat scroll 0 0 transparent;
    border: medium none;
    border-radius: 3px 3px 3px 3px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
    color: white;
    cursor: pointer;
    display: inline-block;
    font-family: "Grotesk URW Regular",Arial,Helvetica,sans-serif;
    font-size: 13px;
    font-size-adjust: 0.45;
    font-style: normal;
    font-weight: normal;
    line-height: 14px;
    min-width: 48px;
    outline: medium none;
    padding: 8px 26px;
    text-align: center;
    text-decoration: none;
    text-shadow: 0 1px 1px rgba(0, 0, 0, 0.3);
    text-transform: uppercase;
}

</style>

<!-- Form Canvas starts here -->
<div class="formContainer">




<div class="create_form">


<h1><spring:message code="user.accountRecovery"/></h1>
<c:url value="/public/signin" var="prev_url"/> 
<span id="statusMessages" ></span>
<input type="radio"  value="username"  name="forgotCredentials" id="forgotUserName">Forgot Username
<input type="radio"  value="password"  name="forgotCredentials" id="forgotPassword">Forgot Password

<div id="formContent" style="display:none;"> 
<form:form method="post" modelAttribute="profileForm" action="submitEmail" id="submitAccounRecoveryForm" onsubmit="return false;">
<ul>
<li>
	<label for="emailAddress"><spring:message code="emailAddress"/></label>
	<form:input path="<%=ProfileForm.EMAIL_ADDRESS %>" size="30" id="emailAddress"  required="required" />
	<form:errors class="errorMessage" path="<%=ProfileForm.EMAIL_ADDRESS %>"/>
</li>
</ul>
<form:hidden path="scenario"/>
 <div class="row-fluid">
    <div class="span6">
	<c:url value="/secure/home" var="prev_url"/>
	<a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="back"/></a>
</div>
    <div class="span6"><span class="right">
    <button class="submit" type="submit" id="submitButton"><spring:message code="submit"/></button> 
    </span></div>
    </div>

</form:form>
</div>





	
			</div>

</div> <!--  Form Canvas ends here -->

<script type="text/javascript">
$(document).ready(function () {
	 $('input[type=radio]').live('change', function() { 
		  var selectedVal = $("input:radio:checked").val();
		  $('#formContent').show();
         console.log(selectedVal);
		 $('#scenario').val(selectedVal);
         
		 
		  });
	
	
	$("#submitButton").on('click', function () {
		var form = $('form#submitAccounRecoveryForm').serialize();
		var formAction = $("#submitAccounRecoveryForm").attr("action");
		var target='statusMessages';
		ajaxPost(formAction,form,target ,'POST',
		function(data) {
			console.log(data.results);
			console.log("Errors Present:"+data.errors);
			$('#statusMessages').text('');
			if(data.errors)
			 $('#statusMessages').append('<ul style="color:red;">');
			else
				$('#statusMessages').append('<ul>');	
		 for (var i in data.results) {
                      $('#statusMessages').append( '<li>'+data.results[i]+'</li>' );
         }
		 $('#statusMessages').append('</ul>');

		
		},null,'JSON');
	
	

});
});
</script>

</t:base>