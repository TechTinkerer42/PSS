<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>

<t:base title="ETS - eREG (Sign In)">
	<script type="text/javascript">
		$(function() {
			$('a.openModal').on('click', function(e) {
				e.preventDefault();			
				var data="Are you really want to bypass the duplicate check?";			
				$("#dialog-confirm").html(data);
				$("#dialog-confirm").dialog("open");
				$("#dialog-confirm").dialog('option', 'width', 450);			
	
				return false;
	
			});
	
		});
	
		function overrideProfile(){
			$( "#dialog-confirm" ).dialog( "close" );
			document.forms["duplicateAccount"].submit();
		} 
	</script>
	
	<ct:modalWindow id="dialog-confirm" buttonName="Override" cancelButtonName="Cancel"
		buttonMethod="overrideProfile" title="Override profile"></ct:modalWindow>
	<div class="formContainer">
		<form:form id="duplicateAccount" method="POST" modelAttribute="profileForm">
			<p>Placeholder: The administrator would get a list of duplicate accounts that are already in the system. They can verify information with the customer based on the information, and make a decision.</p>
			<ul>
				<!-- <li>Placeholder: candidate 1 information</li>
				<li>Placeholder: candidate 2 information</li> -->
				<c:forEach items="${dupCustomers}" var="dupCustomer">
					<li><c:out value="Candidate ID: ${dupCustomer.candidateId}, Name: ${dupCustomer.firstName} ${dupCustomer.lastName}" /><a class="btn" target="_blank" href="<ct:encode out='/secure/profile/viewProfile?profileId=${dupCustomer.candidateId}' />">View</a></li>
				</c:forEach>
			</ul>
			<div class="row-fluid">
				<div class="span6" style="margin:40px 0 0 0;"><a class="submit" href="<c:url value='/secure/profile/'/>">Back</a></div>
				<div class="span6" style="margin:40px 0 0 0;"><span class="right"><a class="submit openModal" id="openModal">Override</a></span></div>
			</div>
		</form:form>
	</div>
</t:base>