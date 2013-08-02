<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Personal Information)">
	<script>
	$(function() {
		MaskedInput({
		  elm: document.getElementById(''),
		  format: 'MM/DD/YYYY',
		  separator: '\/',
		  typeon: 'MDY'
		});
		MaskedInput({
		  elm: document.getElementById(''),
		  format: '(000) 000-0000',
		  separator: '()- '
		});
	});
	
	
	$(function() {
		$(".radiocheck").change(function() {

	   });
	});

	
	$(function mskDateFunction(txtDate){
		{
		  var currVal = txtDate;
		  if(currVal == '')
			return false;
		  
		  var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		  var dtArray = currVal.match(rxDatePattern);
		 
		  if (dtArray == null)
			 return false;
		  
		  dtMonth = dtArray[1];
		  dtDay= dtArray[3];
		  dtYear = dtArray[5];
		 
		  if (dtMonth < 1 || dtMonth > 12)
			  return false;
		  else if (dtDay < 1 || dtDay> 31)
			  return false;
		  else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31)
			  return false;
		  else if (dtMonth == 2)
		  {
			 var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
			 if (dtDay> 29 || (dtDay ==29 && !isleap))
				  return false;
		  }
		  return $("#profile\\.customer\\.dateOfBirth").attr("required", "true");
		}
	});
	</script>
	<div class="headContainer ">
		<div class="row">
			<div class="span9"><h1>Create Account</h1></div>
		</div>
		<c:choose> 
			<c:when test="${eregUtils.isOAMAuthentication()}">
				<img src="${pageContext.request.contextPath}/commonweb/img/step1OAM.jpg">
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/commonweb/img/step1.jpg">
			</c:otherwise>
		</c:choose>
		<br>
		<h2>Personal Information</h2>
		<span class="required_notification">* Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="profileForm">					
		<div class="formContainer">
			<div class="create_form">
				<div class="formRow">
					<form:errors path="*" cssClass="errorblock" element="div" />
					<div id="frontEndErrors"  class="errorblock" style="display:none;">
						
					</div>
					<div class="create_form">
						<ul>
							<li>
								<label>First or Given Name</label>
								<form:input path="<%=ProfileForm.FIRST_NAME%>" required="required" onblur="$(this).val($(this).val().trim());"/>
								<form:errors path="<%=ProfileForm.FIRST_NAME%>"	/>
								<span class="form_hint">
								<spring:message code="profile.create.validation.FirstNameHint" />
								</span>
							</li>
							<div class="clear"></div>
							
							
							<li>
								<label>Middle Initial</label>
								<form:input path="<%=ProfileForm.MIDDLE_INITIAL%>" class="smalltext"/>
								<form:errors path="<%=ProfileForm.MIDDLE_INITIAL%>"	/>
							</li>
							<div class="clear"></div>
							
							<li>
								<label>Last or Family Name</label>
								<form:input path="<%=ProfileForm.LAST_NAME%>"  required="required" onblur="$(this).val($(this).val().trim());"/>
								<form:errors path="<%=ProfileForm.LAST_NAME%>" />
							</li>
							<li>
								<label>Date of Birth</label>
								<form:input path="<%=ProfileForm.DATE_OF_BIRTH%>" onfocus="mskDateFunction()" required="required" class="smalltext" />
								<form:errors path="<%=ProfileForm.DATE_OF_BIRTH%>" />
								<span class="form_hint">
								<spring:message code="profile.create.validation.dateOfBirthHint" />								
								</span>
							</li>
							<li>
								<label>Gender</label>
								<form:radiobutton path="<%=ProfileForm.GENDER %>" value="M" id="csr_RM" class="radiocheck"/>
								 Male
								<form:radiobutton path="<%=ProfileForm.GENDER %>" value="F" id="csr_RF" class="radiocheck"/>
								Female
								<span class="radiostarcolor"></span>
								<form:errors path="<%=ProfileForm.GENDER%>" cssClass="error" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Social Security<br />Number (Last 4 Digits)</label>
								<form:input path="<%=ProfileForm.SOCIAL_SECURITY%>" class="smalltext" maxlength="4"/>
								<form:errors path="<%=ProfileForm.SOCIAL_SECURITY%>" />
								<span class="form_hint">
								<spring:message code="profile.create.validation.socialSecurityHint"/>
								</span>
							</li>
							<div class="clear"></div>
							<li>
								<label>Email</label>
								<form:input path="<%=ProfileForm.EMAIL_ADDRESS%>"  required="required" />
								<form:errors path="<%=ProfileForm.EMAIL_ADDRESS%>" />
							</li>
							<li></li>
							<li>
								<label>Country/Location</label>
								<form:select path="<%=ProfileForm.COUNTRY%>" required="required">
									<form:options items="${countries}" itemValue="abbreviation" itemLabel="name"/>
								</form:select>
								<form:errors path="<%=ProfileForm.COUNTRY%>" />
							</li>
							<li>
								<label>Address Line 1</label>
								<form:input path="<%=ProfileForm.ADDRESS_LINE1%>"  required="required" />
								<form:errors path="<%=ProfileForm.ADDRESS_LINE1%>" />
							</li>
							<li>
								<label>Address Line 2</label>
								<form:input path="<%=ProfileForm.ADDRESS_LINE2%>" />
								<form:errors path="<%=ProfileForm.ADDRESS_LINE2%>" />
							</li>
							<li>
								<label>City</label>
								<form:input path="<%=ProfileForm.CITY%>"  required="required" />
								<form:errors path="<%=ProfileForm.CITY%>" />
							</li>
							<li>
								<label>State</label>
								<form:select path="<%=ProfileForm.STATE%>" required="required" >
									<form:option value="">Select</form:option>
									<form:options items="${states}" itemValue="abbreviation" itemLabel="name"/>
								</form:select>
								<form:errors path="<%=ProfileForm.STATE%>" />
							</li>
							<li>
								<label>ZIP Code</label>
								<form:input path="<%=ProfileForm.POSTAL_CODE%>"  required="required" class="smalltext" />
								<form:errors path="<%=ProfileForm.POSTAL_CODE%>" />
							</li>
							
								<h2>Primary Phone *</h2>
							
							
							<div class="clear"></div>
							<li>
								<label>Country Code</label>
								<form:select path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>" id="primaryPhoneCountryCode" required="required" >
									<form:option value="">Select</form:option>
									<c:forEach items="${countries}" var="country">
										<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
									</c:forEach>
								</form:select>
								<form:errors path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>" />
							</li>
							<li>
								<label>Phone</label>
								<form:input path="<%=ProfileForm.PRIMARY_PHONE_PHONE_NUMBER%>" required="required" />
								<form:errors path="<%=ProfileForm.PRIMARY_PHONE_PHONE_NUMBER%>" />
								<span class="form_hint">
								<spring:message code="profile.create.validation.phoneHint" />
								</span>
							</li>
							<li>
								<label>Extension</label>
								<form:input path="<%=ProfileForm.PRIMARY_PHONE_EXTENSION%>" class="smalltext"/>
								<form:errors path="<%=ProfileForm.PRIMARY_PHONE_EXTENSION%>" />
							</li>
							<li>
								<label>Type</label>
								<form:radiobutton path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>" value="L"/>
								Landline
								<form:radiobutton path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>" value="M"/>
								Mobile
								<form:errors path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE%>" />
							</li>
							<div class="clear"></div>
							
								<h2>Alternate Phone</h2>
							
							<div class="clear"></div>
							<li>
								<label>Country Code</label>
								<form:select path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE%>" id="alternatePhoneCountryCode" >
									<form:option value="">Select</form:option>
									<c:forEach items="${countries}" var="country">
										<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
									</c:forEach>
					``			</form:select>
								<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE%>" />
							</li>
							<li>
								<label>Phone</label>
								<form:input path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER%>" />
								<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER%>" />
							</li>
							<li>
								<label>Extension</label>
								<form:input path="<%=ProfileForm.ALTERNATE_PHONE_EXTENSION%>" class="smalltext" />
								<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_EXTENSION%>" />
							</li>
							<li>
								<label>Type</label>
								<form:radiobutton path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>" value="L"/>
								Landline
								<form:radiobutton path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>" value="M"/>
								Mobile
								<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE%>" />
							</li>	
						</ul>
					</div>
				</div>
			</div> 
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01"">
					<a class="submit" href="home">Back</a>
					<span class="right" style="margin: -6px 0 0 0;"><button class="submit" type="submit" id="nextButton" >Next</button></span>
				</div>
			</div>
		</div>
	</form:form>
</t:base>