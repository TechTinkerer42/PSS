<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>

<t:base title="ETS - eREG (Update Profile)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9"><h1>Update Profile</h1></div>
		</div>
		<span class="required_notification">* Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="post" id="myForm" modelAttribute="profileForm"
		action="/ereg-csr-web/secure/profile/edit">

		<div class="formContainer">
			<div class="create_form">
				<div class="formRow">
						<h2>Personal Information</h2>	
					
					<div class="formRow">

						<ul>
									
							
						<style>
						tr, td, th {border:0;}
						</style>
						
							<table  style="border:0px;">

								<tr>
									<td>Test Taker ID:</td>
									<td><c:out
											value="${profileForm.profile.custLinkageKey}" /></td>
								<tr>
									<td>First Name</td>
									<td>
										<div id="firstName">
											<c:out value="${profileForm.profile.customer.firstName}" />
										</div>
									</td>
									<td></td>
									<td><form:errors path="<%=ProfileForm.FIRST_NAME%>"
											cssClass="error" /></td>
								</tr>
								<tr>
									<td>Middle Initial</td>
									<td>
										<div id="middleInitial">
											<c:out
												value="${profileForm.profile.customer.middleInitial}" />
										</div>
									</td>
									<td><form:errors path="<%=ProfileForm.MIDDLE_INITIAL%>"
											cssClass="error" /></td>
								</tr>

								<tr>
									<td>Last Name</td>
									<td>
										<div id="lastName">
											<c:out value="${profileForm.profile.customer.lastName}" />
										</div>
									</td>
									<td><sec:authorize
											access="hasRole('PERMISSION_UPDATE_CUSTOMER_RESTRICTED')">
											<a id="openEditNameModalButton"  class="othbutton"
												
												>Edit</a>
										</sec:authorize></td>
									<td><form:errors path="<%=ProfileForm.LAST_NAME%>"
											cssClass="error" /></td>
								</tr>
								<tr>
									<td>Date of Birth</td>
									<td>
										<div id="dateOfBirth">
											<ct:dateTime part="date"
												value="${profileForm.profile.customer.dateOfBirth}" />
										</div>
									</td>
									<td><sec:authorize
											access="hasRole('PERMISSION_UPDATE_CUSTOMER_RESTRICTED')">
											<a id="openEditDOBModalButton" role="button" class="othbutton"
												data-toggle="modal" 
												>Edit</a>
										</sec:authorize></td>
									<td><form:errors path="<%=ProfileForm.DATE_OF_BIRTH%>"
											cssClass="error" /></td>
								</tr>
								<tr>
									<td>Gender</td>
									<td><div id="gender">
											<c:out
												value="${profileForm.profile.customer.gender.description}" />
										</div></td>
									<td><sec:authorize
											access="hasRole('PERMISSION_UPDATE_CUSTOMER_RESTRICTED')">
											<a id="openEditGenderModalButton" role="button" class="othbutton"
												data-toggle="modal" 
												>Edit</a>
										</sec:authorize></td>
									<td><form:errors path="<%=ProfileForm.GENDER%>"
											cssClass="error" /></td>
								</tr>
								<tr>
									<td>Social Security Number</td>
									<td><div id="socialSecurity">
											<c:out
												value="${profileForm.profile.customer.socialSecurity}" />
										</div></td>
									<td><sec:authorize
											access="hasRole('PERMISSION_UPDATE_CUSTOMER_RESTRICTED')">
											<a id="openEditSSNModalButton" role="button" class="othbutton"
												data-toggle="modal" 
												>Edit</a>
										</sec:authorize></td>
									<td><form:errors
											path="<%=ProfileForm.SOCIAL_SECURITY%>" cssClass="error" /></td>
								</tr>
							</table>
							<%--  	<li>
<label>First Name</label>
<div id="firstName"><c:out value="${profileForm.profile.customer.firstName}" /></div>
</li>
<li>
<label>Middle Initial</label>
<div id="middleInitial"><c:out value="${profileForm.profile.customer.middleInitial}"  /></div>
</li>
<li>
<label>Last Name</label>
<div id="lastName"><c:out value="${profileForm.profile.customer.lastName}"  /></div>
<a href="#editNameModal" role="button" class="btn" data-toggle="modal">Edit</a>
</li>
<li>
<label>Date of Birth</label>
<div id="dateOfBirth"><c:out value="${profileForm.profile.customer.dateOfBirth}"/></div>
<a href="#editDobModal" role="button" class="btn" data-toggle="modal">Edit</a>
</li>
<li>
<label>Gender</label>
<div id="gender"><c:out value="${profileForm.profile.customer.gender.description}"/></div>
<a href="#editGenderModal" role="button" class="btn" data-toggle="modal">Edit</a>
<form:errors path="<%=ProfileForm.GENDER %>"/>
</li>
<li>
<label>Social Security Number</label>
<div id="socialSecurity"><c:out value="${profileForm.profile.customer.socialSecurity}" /></div>
<a href="#editSsnModal" role="button" class="btn" data-toggle="modal">Edit</a>
</li>
<sec:authorize access="hasRole('PERMISSION_UPDATE_CUSTOMER_RESTRICTED')">
<a href="#myModal" role="button" class="btn" data-toggle="modal">Edit</a>
</sec:authorize> --%>


							<ct:modalWindow id="editNameModal" buttonName="Save"
								buttonMethod="saveName" title="Edit Name">
							</ct:modalWindow>
							<div id="editNameModalBody">
								<form:label path="profile.customer.firstName">First or Given Name:</form:label>
								<input type="text" id="module.profile.customer.firstName" 
									required="required" />
								<form:errors path="profile.customer.firstName"/>
								<br />
								<form:label path="profile.customer.middleInitial">Middle Initial:</form:label>
								<input type="text" id="module.profile.customer.middleInitial" />
								
								<br />
								<form:label path="profile.customer.lastName">Last or Family Name:</form:label>
								<input type="text" id="module.profile.customer.lastName" 
									required="required" />
								<form:label path="" >Reason for change:</form:label>
								<form:textarea path="" required="required" id="module.profile.customer.lastName.comment"/>
							</div>
							<form:hidden path="profile.customer.firstName" />
							<form:hidden path="profile.customer.middleInitial" />
							<form:hidden path="profile.customer.lastName" />

							<ct:modalWindow id="editDOBModal" buttonName="Save"
								buttonMethod="saveDOB" title="Edit Date Of Birth">
							</ct:modalWindow>
							<div id="editDOBModalBody">
								<form:label path="profile.customer.dateOfBirth">Date of Birth:</form:label>
								<input type="text" id="module.profile.customer.dateOfBirth"
									required="required" />
								<br />
								<form:label path="">Reason for change:</form:label>
								<form:textarea path="" required="required" id="module.profile.customer.dateOfBirth.comment"/>
							</div>
							<form:hidden path="profile.customer.dateOfBirth" />

							<ct:modalWindow id="editGenderModal" buttonName="Save"
								buttonMethod="saveGender" title="Edit Gender">
							</ct:modalWindow>
							<div id="editGenderModalBody">
								<form:label path="profile.customer.gender">Gender:</form:label>
								<input type="radio" id="module.profile.customer.gender" name="module.profile.customer.gender" value="Male" 
									 />
								Male
								<input type="radio" id="module.profile.customer.gender" name="module.profile.customer.gender" value="Female"
									 />
								Female <br>
								<br />
								<form:label path="">Reason for change:</form:label>
								<form:textarea path="" id="module.profile.customer.gender.comment" required="required" />
							</div>
							<input type="hidden" id="profile.customer.gender" name="profile.customer.gender" value="${profileForm.profile.customer.gender.code}" />


							<ct:modalWindow id="editSSNModal" buttonName="Save"
								buttonMethod="saveSSN" title="Edit Social Security Number">
							</ct:modalWindow>
							<div id="editSSNModalBody">
								<form:label path="profile.customer.socialSecurity">Social Security Number:</form:label>
								<input type="text" id="module.profile.customer.socialSecurity" />
								<br />
								<form:label path="">Reason for change:</form:label>
								<form:textarea path="" id="module.profile.customer.socialSecurity.comment" required="required" />
							</div>
							<form:hidden path="profile.customer.socialSecurity" />

							<li><br /> <label for="country">Country</label> <form:select
									path="<%=ProfileForm.COUNTRY%>" required="required">
									<form:option value="">Select</form:option>
									<c:forEach items="${countries}" var="country">
										<form:option value="${country.abbreviation}">${country.name}</form:option>
									</c:forEach>
								</form:select> <form:errors path="<%=ProfileForm.COUNTRY%>"
									cssClass="error" /></li>
							<div class="clear"></div>
							<li><label for="addressLine1">Address Line 1</label> <form:input
									path="profile.address.addressLine1" size="30"
									id="addressLine1" required="required" /> <form:errors
									path="profile.address.addressLine1" /></li>
							<div class="clear"></div>
							<li><label for="addressLine2">Address Line 2</label> <form:input
									path="profile.address.addressLine2" size="30"
									id="addressLine2" /></li>
							<div class="clear"></div>
							<li><label for="city">City</label> <form:input
									path="<%=ProfileForm.CITY%>" size="36" required="required" />
								<form:errors path="<%=ProfileForm.CITY%>" cssClass="error" />
							</li>
							<div class="clear"></div>
							<li><label for="state">State</label> <form:select
									path="<%=ProfileForm.STATE%>" required="required">
									<form:option value="">Select</form:option>
									<c:forEach items="${states}" var="state">
										<form:option value="${state.abbreviation}">${state.name}</form:option>
									</c:forEach>
								</form:select> <form:errors path="<%=ProfileForm.STATE%>" cssClass="error" />
							</li>
							<div class="clear"></div>
							<li><label for="postalCode">Postal Code</label> <form:input
									path="profile.address.postalCode" size="30" id="postalCode"
									required="required" /> <form:errors
									path="profile.address.postalCode" /></li>
							<div style="clear: both;"></div>
							<br />
							<h5>Primary Phone</h5>
							<br />
							<li><label for="primaryPhoneCountryCode">Country
									Code</label> <form:select
									path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>"
									id="primaryPhoneCountryCode" required="required">
									<form:option value="">Select</form:option>
									<c:forEach items="${countries}" var="country">
										<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
									</c:forEach>
								</form:select> <form:errors
									path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>"
									cssClass="error" /></li>
							<div class="clear"></div>
							<li><label for="primaryPhoneNumber">Phone</label> <form:input
									path="profile.primaryPhone.phoneNumber" size="30"
									id="primaryPhoneNumber" required="required" /> <form:errors
									path="profile.primaryPhone.phoneNumber" /></li>
							<div class="clear"></div>
							<li><label for="primaryPhoneExtension">Extension</label>
								<form:input path="profile.primaryPhone.phoneExtension"
									size="30" id="primaryPhoneExtension" /> <form:errors
									path="profile.primaryPhone.phoneExtension" /></li>
							<div class="clear"></div>
							<li><c:set var="PHONE_TYPE_LANDLINE"
									value="<%=PhoneType.Landline%>" /> <c:set
									var="PHONE_TYPE_MOBILE" value="<%=PhoneType.Mobile%>" /> <label
								for="primaryPhoneType">Type</label> <c:forEach
									items="${phoneTypes}" var="phoneType">
									<c:if
										test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_LANDLINE)}'>
										<form:radiobutton
											path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE%>"
											value="${phoneType.code}" />${phoneType.description}
</c:if>
								</c:forEach> <c:forEach items="${phoneTypes}" var="phoneType">
									<c:if
										test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_MOBILE)}'>
										<form:radiobutton
											path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE%>"
											value="${phoneType.code}" />${phoneType.description}
</c:if>
								</c:forEach> <form:errors
									path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE%>" /></li>
							<div class="clear"></div>


							<br />
							<h5>Alternate Phone</h5>
							<br />
							<li><label for="alternatePhoneCountryCode">Country
									Code</label> <form:select
									path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE%>"
									id="alternatePhoneCountryCode">
									<form:option value="">Select</form:option>
									<c:forEach items="${countries}" var="country">
										<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
									</c:forEach>
								</form:select> <form:errors
									path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE%>"
									cssClass="error" /></li>
							<div class="clear"></div>
							<li><label for="alternatePhoneNumber">Phone</label> <form:input
									path="profile.alternatePhone.phoneNumber" size="30"
									id="alternatePhoneNumber" /> <form:errors
									path="profile.alternatePhone.phoneNumber" /></li>
							<div class="clear"></div>
							<li><label for="alternatePhoneExtension">Extension</label>
								<form:input path="profile.alternatePhone.phoneExtension"
									size="30" id="alternatePhoneExtension" /> <form:errors
									path="profile.alternatePhone.phoneExtension" /></li>
							<div class="clear"></div>
							<li><label for="alternatePhoneType">Type</label> <c:forEach
									items="${phoneTypes}" var="phoneType">
									<c:if
										test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_LANDLINE)}'>
										<form:radiobutton
											path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>"
											value="${phoneType.code}" />${phoneType.description}
</c:if>
								</c:forEach> <c:forEach items="${phoneTypes}" var="phoneType">
									<c:if
										test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_MOBILE)}'>
										<form:radiobutton
											path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>"
											value="${phoneType.code}" />${phoneType.description}
</c:if>
								</c:forEach> <form:errors
									path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE%>" /></li>
							<div class="clear"></div>
							<li><label for="emailAddress">Email</label> <form:input
									path="profile.customer.emailAddress" size="30"
									id="emailAddress" required="required" /> <form:errors
									path="profile.customer.emailAddress" /></li>
							<div class="clear"></div>

							<%-- <div class="clear"></div>
<li>
Social Security Number
<div id="socialSecurity"><c:out value="${profileForm.profile.customer.socialSecurity}" /></div>
<form:hidden path="profile.customer.socialSecurity"/>
</li> --%>
							<div class="clear"></div>

							<br />
							<h2>Additional Information</h2>
							<br />

							<%-- 	<li>
<c:set var="GENDER_MALE" value="<%=Gender.Male%>" />
<c:set var="GENDER_FEMALE" value="<%=Gender.Female%>" />
<c:set var="GENDER_PREFER_NOT_TO_ANSWER" value="<%=Gender.PreferNotToAnswer%>" />

Gender
<c:forEach items="${genders}" var="gender">
<c:if test='${gender.code.equalsIgnoreCase(GENDER_MALE)}'>
<form:radiobutton path="<%=ProfileForm.GENDER %>" value="${gender.code}"/>${gender.description}
</c:if>
<c:if test='${gender.code.equalsIgnoreCase(GENDER_FEMALE)}'>
<form:radiobutton path="<%=ProfileForm.GENDER %>" value="${gender.code}"/>${gender.description}
</c:if>
<c:if test='${gender.code.equalsIgnoreCase(GENDER_PREFER_NOT_TO_ANSWER)}'>
<form:radiobutton path="<%=ProfileForm.GENDER %>" value="${gender.code}"/>${gender.description}
</c:if>
</c:forEach>
<form:errors path="<%=ProfileForm.GENDER %>"/>

</li> --%>
							<div class="clear"></div>
							<li><label for="ethnicity">Ethnicity</label> <form:select
									path="<%=ProfileForm.ETHNICITY%>" id="ethnicity">
									<form:option value="">Select</form:option>
									<c:forEach items="${ethnicityTypes}" var="ethnicityType">
										<form:option value="${ethnicityType.code}">${ethnicityType.description}</form:option>
									</c:forEach>
								</form:select> <form:errors path="<%=ProfileForm.ETHNICITY%>"
									cssClass="error" /></li>
							<div class="clear"></div>
							<li><label for="preferredLanguage">Preferred
									Language for Test Taking</label> <form:select
									path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING%>"
									id="ethnicity" required="required">
									<form:option value="">Select</form:option>
									<c:forEach items="${languageTypes}" var="languageTypes">
										<form:option value="${languageTypes.code}">${languageTypes.description}</form:option>
									</c:forEach>
								</form:select> <form:errors
									path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING%>"
									cssClass="error" /></li>
							<div class="clear"></div>
							<li><label for="primaryLanguage">Primary speaking
									language</label> <form:select
									path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE%>"
									id="ethnicity">
									<form:option value="">Select</form:option>
									<c:forEach items="${languageTypes}" var="languageTypes">
										<form:option value="${languageTypes.code}">${languageTypes.description}</form:option>
									</c:forEach>
								</form:select> <form:errors
									path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE%>"
									cssClass="error" /></li>
							<div class="clear"></div>
							<li><label>Military
									Member</label> <form:radiobutton
									path="<%=ProfileForm.IS_MILITARY_MEMBER%>" value="true"
									onclick="$('[id$=militaryStatus]').attr('disabled', false);$('[id$=militaryStatus]').val('');" />
								Yes <form:radiobutton
									path="<%=ProfileForm.IS_MILITARY_MEMBER%>" value="false"
									onclick="$('[id$=militaryStatus]').attr('disabled', 'disabled');$('[id$=militaryStatus]').val('NOTAM');" />
								No <form:errors path="<%=ProfileForm.IS_MILITARY_MEMBER%>" />
							</li>
							<div class="clear"></div>
							<li><label for="militaryStatus">Status</label> <form:select
									path="<%=ProfileForm.MILITARY_STATUS%>" >
									<form:option value="">Select</form:option>
									<c:forEach items="${militaryStatusTypes}"
										var="militaryStatusType">
										<form:option value="${militaryStatusType.code}">${militaryStatusType.description}</form:option>
									</c:forEach>
								</form:select> <form:errors path="<%=ProfileForm.MILITARY_STATUS%>"
									cssClass="error" /></li>
							<div class="clear"></div>
						</ul>
						<form:hidden path="profile.customer.id" />
						<form:hidden path="profile.address.id" />
						<form:hidden path="profile.primaryPhone.id" />
						<form:hidden path="profile.alternatePhone.id" />
					</div>

				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01"">
					<a class="submit" onclick="window.history.back();">Back</a>
					<span class="right" style="margin: -6px 0 0 0;"><button class="submit" type="submit" >Submit</button></span>
				</div>
			</div>
		</div>
		<div id='displayNoneFormFields' style="height:0px; width:0px;display:none"></div>
	</form:form>
	<script>
		$(document).ready(function() {
			
			var militaryMembership = $('input[name$=militaryMemberShip]:checked').val(); 
			var militaryStatusComponent = $('[id$=militaryStatus]');
			if(militaryMembership == 'false'){
				militaryStatusComponent.attr('disabled', 'true');
				militaryStatusComponent.val('NOTAM');
			}
			
			$('[name=profile\\.customer\\.militaryMemberShip]').click(function(){
				var militaryMembership = $('input[name$=militaryMemberShip]:checked').val(); 
				if(militaryMembership == 'false')
					$('<input type="hidden" name="profile.customer.militaryStatus" id="hidden.profile.customer.militaryStatus" value="NOTAM"> ').appendTo('#myForm');
				else
					$('#hidden\\.profile\\.customer\\.militaryStatus').remove();
			});

	
		});

	
		$("#editNameModal").dialog({
			autoOpen : false,
		});

		$("#editNameModalBody").appendTo($("#editNameModal span:first"));

		$("#openEditNameModalButton").click(
				function() {
					$("#module\\.profile\\.customer\\.firstName").val(
							$.trim($("#firstName").html()));
					$("#module\\.profile\\.customer\\.middleInitial").val(
							$.trim($("#middleInitial").html()));
					$("#module\\.profile\\.customer\\.lastName").val(
							$.trim($("#lastName").html()));
					$("#editNameModal").dialog("open");
				});

		function saveName(evt) {

			var errorMessage = '';

			
			
			
			
			document.getElementById('module.profile.customer.firstName')
					.setCustomValidity("");

			document.getElementById('module.profile.customer.lastName')
					.setCustomValidity("");

			document.getElementById('module.profile.customer.lastName.comment')
					.setCustomValidity("");

			if (!document.getElementById('module.profile.customer.firstName').validity.valid) {
				document.getElementById('module.profile.customer.firstName')
						.setCustomValidity("<spring:message code="profile.create.validation.FirstNameRequired"/>");

				errorMessage = errorMessage +    "<spring:message code="profile.create.validation.FirstNameRequired"/>\n";

			}

			if (!document.getElementById('module.profile.customer.lastName').validity.valid) {
				document.getElementById('module.profile.customer.lastName')
						.setCustomValidity("<spring:message code="profile.create.validation.LastNameRequired"/>");

				errorMessage = errorMessage + "<spring:message code="profile.create.validation.LastNameRequired"/>\n";

			}

			if (!document.getElementById('module.profile.customer.lastName.comment').validity.valid) {
				document.getElementById('module.profile.customer.lastName.comment')
						.setCustomValidity("<spring:message code="reasonForChange.required"/>");

				errorMessage = errorMessage + "<spring:message code="reasonForChange.required"/>\n";

			}

			if (document.getElementById('module.profile.customer.firstName').validity.valid
					&& document.getElementById('module.profile.customer.lastName').validity.valid
					&& document
							.getElementById('module.profile.customer.lastName.comment').validity.valid) {

				$("#firstName")
						.html($("#module\\.profile\\.customer\\.firstName").val());
				$("#middleInitial").html(
						$("#module\\.profile\\.customer\\.middleInitial").val());
				$("#lastName").html($("#module\\.profile\\.customer\\.lastName").val());
				

				$("#profile\\.customer\\.firstName").val($("#module\\.profile\\.customer\\.firstName").val());
				$("#profile\\.customer\\.middleInitial").val($("#module\\.profile\\.customer\\.middleInitial").val());
				$("#profile\\.customer\\.lastName").val($("#module\\.profile\\.customer\\.lastName").val());
				
				$("#editNameModal").dialog("close");

			} else {

				alert(errorMessage);
			}
		}

		$("#editDOBModal").dialog({
			autoOpen : false,
		});

		$("#editDOBModalBody").appendTo($("#editDOBModal span:first"));

		$("#openEditDOBModalButton").click(
				function() {
					$("#module\\.profile\\.customer\\.dateOfBirth").val(
							$.trim($("#dateOfBirth").html()));
					$("#editDOBModal").dialog("open");
				});

		function saveDOB(evt) {

			var errorMessage = "";

			document.getElementById('module.profile.customer.dateOfBirth')
					.setCustomValidity("");

			document.getElementById('module.profile.customer.dateOfBirth.comment')
					.setCustomValidity("");

			if (!document.getElementById('module.profile.customer.dateOfBirth').validity.valid

			) {
				document.getElementById('module.profile.customer.dateOfBirth')
						.setCustomValidity("<spring:message code="profile.create.validation.dateOfBirthRequired"/>");

				errorMessage = errorMessage + "<spring:message code="profile.create.validation.dateOfBirthRequired"/>\n";

			}

			if (!document
					.getElementById('module.profile.customer.dateOfBirth.comment').validity.valid

			) {
				document.getElementById('module.profile.customer.dateOfBirth.comment')
						.setCustomValidity("<spring:message code="reasonForChange.required"/>");

				errorMessage = errorMessage + "<spring:message code="reasonForChange.required"/>";

			}

			if (document.getElementById('module.profile.customer.dateOfBirth').validity.valid
					&& document
							.getElementById('module.profile.customer.dateOfBirth.comment').validity.valid) {

				$("#dateOfBirth").html(
						$("#module\\.profile\\.customer\\.dateOfBirth").val());
				$("#editDOBModal").dialog("close");
				
				$("#profile\\.customer\\.dateOfBirth").val($("#module\\.profile\\.customer\\.dateOfBirth").val());

			} else {
				alert(errorMessage);

			}
		}

		$("#editGenderModal").dialog({
			autoOpen : false,
		});

		$("#editGenderModalBody").appendTo($("#editGenderModal span:first"));

		$("#openEditGenderModalButton").click(
				function() {
					$('input:radio[name=\'module.profile.customer.gender\']').val(
							[ $.trim($('#gender').html()) ]);
					$("#editGenderModal").dialog("open");
				});

		function saveGender(evt) {

			var errorMessage = "";

			document.getElementById('module.profile.customer.gender.comment')
					.setCustomValidity("");
			

			if (!document.getElementById('module.profile.customer.gender.comment').validity.valid) {
				document.getElementById('module.profile.customer.gender.comment')
						.setCustomValidity("<spring:message code="reasonForChange.required"/>");

				errorMessage = errorMessage + "<spring:message code="reasonForChange.required"/>\n";

			}

			if (document.getElementById('module.profile.customer.gender.comment').validity.valid) {

				$('#gender')
						.html(
								$(
										'input:radio[name=\'module.profile.customer.gender\']:checked')
										.val());

			}

			if (document.getElementById('module.profile.customer.gender.comment').validity.valid
					&& document
							.getElementById('module.profile.customer.gender.comment').validity.valid) {
				
				
				$("#profile\\.customer\\.gender").val($('input:radio[name=\'module.profile.customer.gender\']:checked').val().charAt(0));
				$("#editGenderModal").dialog("close");

			} else {

				alert(errorMessage);
			}
		}

		$("#editSSNModal").dialog({
			autoOpen : false,
		});

		$("#editSSNModalBody").appendTo($("#editSSNModal span:first"));

		$("#openEditSSNModalButton").click(
				function() {
					$("#module\\.profile\\.customer\\.socialSecurity").val(
							$.trim($("#socialSecurity").html()));
					$("#editSSNModal").dialog("open");
				});

		function saveSSN(evt) {

			var errorMessage = '';

			document.getElementById('module.profile.customer.socialSecurity')
					.setCustomValidity("");

			document.getElementById('module.profile.customer.socialSecurity.comment')
					.setCustomValidity("");

			if (!document.getElementById('module.profile.customer.socialSecurity').validity.valid) {
				document.getElementById('module.profile.customer.socialSecurity')
						.setCustomValidity("<spring:message code="profile.create.validation.socialSecurityRequired"/>");

				errorMessage = errorMessage + "<spring:message code="profile.create.validation.socialSecurityRequired"/>\n";

			}

			if (!document
					.getElementById('module.profile.customer.socialSecurity.comment').validity.valid) {
				document.getElementById(
						'module.profile.customer.socialSecurity.comment')
						.setCustomValidity("<spring:message code="reasonForChange.required"/>");

				errorMessage = errorMessage + "<spring:message code="reasonForChange.required"/>\n";

			}

			if (document.getElementById('module.profile.customer.socialSecurity').validity.valid
					&& document
							.getElementById('module.profile.customer.socialSecurity.comment').validity.valid) {

				$("#socialSecurity").html(
						$("#module\\.profile\\.customer\\.socialSecurity").val());
				
				$("#profile\\.customer\\.socialSecurity").val($("#module\\.profile\\.customer\\.socialSecurity").val());

				$("#editSSNModal").dialog("close");

			} else {

				alert(errorMessage);
			}
		}
	</script>
</t:base>