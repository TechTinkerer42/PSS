<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Account Recovery">

<!-- Form Canvas starts here -->
<div class="formContainer">




<div class="create_form">


<h1><spring:message code="user.accountRecovery"/></h1>
<c:url value="/public/signin" var="prev_url"/> 

<input type="radio"  value="username"  name="forgotCredentials" id="forgotUserName">Forgot Username
<input type="radio"  value="password"  name="forgotCredentials" id="forgotPassword">Forgot Password

<div id="formContent"> 

</div>





	
			</div>

</div> <!--  Form Canvas ends here -->

<script type="text/javascript">
$(document).ready(function () {
	
	
	
	
	
	 $('input[type=radio]').live('change', function() { 
		  var selectedVal = $("input:radio:checked").val();
          console.log(selectedVal);
		 
			$.ajax({
				url: window.location.protocol + "//" + window.location.host + "${pageContext.request.contextPath}/public/profile/accountrecovery",
				data: {forgotCredentials:selectedVal},
				success: 	function(data) {
					$("#formContent").html(data);
				},
				dataType: 'html'
				});
		 
		  });
});
</script>

</t:base>