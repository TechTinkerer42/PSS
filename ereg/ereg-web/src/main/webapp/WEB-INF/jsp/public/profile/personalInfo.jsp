<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Create Profile - Personal Information">

<div class="headContainer ">

<div class="row">
<div class="span9"><h1><spring:message code="createAccount"/></h1></div>
</div>


<p><spring:message code="personalInfo.message"/></p>



  <img src='<spring:url value="/commonweb/img/step1.jpg" htmlEscape="true"/>' alt=""><br><br>
  
             <h2><spring:message code="personalInformation"/></h2>
             <span class="required_notification"><spring:message code="requiredInformation"/></span>

</div>


<p >
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
</p>
<!-- Form Canvas starts here -->
<div class="formContainer">


<script>
$(document).ready(function () {
    $('form').h5Validate();
});
</script>


<div class="create_form">
<form:form method="post" modelAttribute="profileForm" action="personal">



<div class="formRow">
 
 
  
 
<div>

 <ul>
 
 <li>
	<label for="firstName" class="required"><spring:message code="firstName"/></label> 
	<form:input path="<%=ProfileForm.FIRST_NAME%>" size="30" id="firstName" required="required" />
	<form:errors class="errorMessage"  path="<%=ProfileForm.FIRST_NAME%>"/>
	<!--<span class="form_hint">
	<spring:message code="profile.create.validation.FirstNameHint" />
	</span>-->
	</li>
	
	
	<li>
	
	<label for="middleInitial"><spring:message code="middleInitial"/></label>
	<form:input path="<%=ProfileForm.MIDDLE_INITIAL%>" size="1" id="middleInitial" class="smalltext"/>
	</li>
	
	<li>
	<label for="lastName" class="required"><spring:message code="lastName"/></label>
	<form:input path="<%=ProfileForm.LAST_NAME%>" size="30" id="lastName" required="required"/>
	<form:errors class="errorMessage" path="<%=ProfileForm.LAST_NAME%>"/>
	</li>
	
	<li>
	<label for="dateOfBirth" class="required"><spring:message code="dateOfBirth"/></label>
	<!--
	<script>DateInput('orderdate', true, 'DD-MON-YYYY')</script>
	<form:input path="<%=ProfileForm.DATE_OF_BIRTH %>" size="10" id="dateOfBirth" required="required"/>-->
	<c:set var="monthIndex" value="1" />
	<form:select path="<%=ProfileForm.MONTH_OF_BIRTH%>" required="required" class="dobMonth">
		<form:option value=""><spring:message code="month"/></form:option>
		<c:forEach items="${months}" var="month">
			<form:option value="${monthIndex}">${month}</form:option>
			<c:set var="monthIndex" value="${monthIndex+1}"/>
		</c:forEach>
	</form:select>
	<c:set var="dayIndex" value="1" />
	<form:select path="<%=ProfileForm.DAY_OF_BIRTH%>" required="required" class="dobDay">
		<form:option value=""><spring:message code="day"/></form:option>
		<c:forEach items="${days}" var="day">
			<form:option value="${dayIndex}">${day}</form:option>
			<c:set var="dayIndex" value="${dayIndex+1}"/>
		</c:forEach>
	</form:select>
	<form:input path="<%=ProfileForm.YEAR_OF_BIRTH%>" size="4" id="yearOfBirth" maxlength="4"  pattern="[0-9]{4}" required="required" class="dobYear"/>
	<form:errors class="errorMessage" path="<%=ProfileForm.DATE_OF_BIRTH %>"/>
	</li>

  	<li>
  	
	<c:set var="GENDER_MALE" value="<%=Gender.Male%>" />
	<c:set var="GENDER_FEMALE" value="<%=Gender.Female%>" />
	<c:set var="GENDER_PREFER_NOT_TO_ANSWER" value="<%=Gender.PreferNotToAnswer%>" />

	
	<label for="gender" class="required"><spring:message code="gender"/></label>
	
	<c:forEach items="${genders}" var="gender">
		<c:if test='${gender.code.equalsIgnoreCase(GENDER_MALE)}'>
		<form:radiobutton path="<%=ProfileForm.GENDER %>" value="${gender.code}" id="male" name="rr" />
		
		${gender.description}
		</c:if>
		<c:if test='${gender.code.equalsIgnoreCase(GENDER_FEMALE)}' >
		<form:radiobutton path="<%=ProfileForm.GENDER %>" value="${gender.code}" id="fmale" name="rr" />${gender.description}

		</c:if>
		<c:if test='${gender.code.equalsIgnoreCase(GENDER_PREFER_NOT_TO_ANSWER)}'>
		<form:radiobutton path="<%=ProfileForm.GENDER %>" value="${gender.code}"/>${gender.description}
		</c:if>
	</c:forEach>
	<form:errors  class="errorMessage" path="<%=ProfileForm.GENDER %>"/>

	


	</li>
	

<div style="clear:both"></div>
	
	<li>
	<label for="socialSecurity"><spring:message code="socialSecurity"/> <br> <spring:message code="socialSecurityLast4"/></label>
	<form:input path="<%=ProfileForm.SOCIAL_SECURITY %>" size="10" id="socialSecurity"  maxlength="4"  pattern="[0-9]{4}" class="smalltext"/>
	<form:errors class="errorMessage" path="<%=ProfileForm.SOCIAL_SECURITY %>"/>
	<span class="form_hint">
	<spring:message code="profile.create.validation.socialSecurityHint"/>
	</span>
	</li>
	

<div style="clear:both;"></div>
	
	<li>
	<label for="emailAddress" class="required"><spring:message code="emailAddress"/></label>
	<form:input path="<%=ProfileForm.EMAIL_ADDRESS %>" size="30" id="emailAddress" class="h5-email" required="required"/>
	<form:errors class="errorMessage" path="<%=ProfileForm.EMAIL_ADDRESS %>"/>
	<span class="form_hint">Email format: example@ets.org
	</span>
	</li>




	<li>
	<label for="country" class="required"><spring:message code="country"/></label>
	<form:select path="<%=ProfileForm.COUNTRY%>" required="required">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${countries}" var="country">
			<form:option value="${country.abbreviation}">${country.name}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.COUNTRY%>"/>
	</li>
	
	<li>
	<label for="addressLine1" class="required"><spring:message code="addressLine1"/></label>
	<form:input path="<%=ProfileForm.ADDRESS_LINE1%>" size="30" id="addressLine1" required="required" />
	<form:errors class="errorMessage" path="<%=ProfileForm.ADDRESS_LINE1%>"/>
	</li>
	<li>
	<label for="addressLine2" ><spring:message code="addressLine2"/></label>
	<form:input path="<%=ProfileForm.ADDRESS_LINE2%>" size="30" id="addressLine2" />
	</li>
	
	<li>
	<label for="city" class="required"><spring:message code="city"/></label>
	<form:input path="<%=ProfileForm.CITY%>" size="30" id="city" required="required"/>
	<form:errors class="errorMessage" path="<%=ProfileForm.CITY%>"/>
	</li>
	
	<li>
	<label for="state" class="required"><spring:message code="state"/></label>
	<form:select path="<%=ProfileForm.STATE%>" required="required">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${states}" var="state">
			<form:option value="${state.abbreviation}">${state.name}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.STATE%>"/>
	</li>
	
	<li>
	<label for="postalCode" class="required"><spring:message code="postalCode"/></label>
	<form:input path="<%=ProfileForm.POSTAL_CODE%>" size="30" id="postalCode" class="smalltext" maxlength="5"  pattern="[0-9]{5}" required="required"/>
	<form:errors class="errorMessage" path="<%=ProfileForm.POSTAL_CODE%>"/>
</li>
	<h2><spring:message code="primaryPhone"/></h2>
	
	<li>
	<label for="primaryPhoneCountryCode" class="required"><spring:message code="phoneCountryCode"/></label>
	<form:select path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>" id="primaryPhoneCountryCode" required="required">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${countries}" var="country">
			<form:option value="${country.abbreviation}">${country.name} - ${country.isdCode}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>"/>
	</li>
	<li>
	<label for="primaryPhoneNumber" class="required"><spring:message code="phoneNumber"/></label>
	<form:input path="<%=ProfileForm.PRIMARY_PHONE_PHONE_NUMBER%>" size="30" maxlength="10"  pattern="[0-9]{10}" id="primaryPhoneNumber" required="required" />
	<form:errors  class="errorMessage"  path="<%=ProfileForm.PRIMARY_PHONE_PHONE_NUMBER%>"/>
	</li>
	<li>
	<label for="primaryPhoneExtension" ><spring:message code="phoneExtension"/></label>
	<form:input path="<%=ProfileForm.PRIMARY_PHONE_EXTENSION %>" size="30" id="primaryPhoneExtension"  class="smalltext" />
	<form:errors path="<%=ProfileForm.PRIMARY_PHONE_EXTENSION %>"/>
	</li>
	<li>
	
	<c:set var="PHONE_TYPE_LANDLINE" value="<%=PhoneType.Landline%>" />
	<c:set var="PHONE_TYPE_MOBILE" value="<%=PhoneType.Mobile%>" />
	<label for="primaryPhoneType" class="required"><spring:message code="phoneType"/></label>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_LANDLINE)}'>
		<form:radiobutton path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>" value="${phoneType.code}" id="landline" name="pp"/>
		${phoneType.description}
		</c:if>
	</c:forEach>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_MOBILE)}'>
		<form:radiobutton path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>" value="${phoneType.code}" id="mobile"  name="pp"/>
		${phoneType.description}
		</c:if>
	</c:forEach>
	
	<form:errors class="errorMessage" path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>"/>
	</span>
	</li>



	<div style="clear:both;"></div>
	
	<h2><spring:message code="alternatePhone"/></h2>
	<li>
	<label for="alternatePhoneCountryCode"><spring:message code="phoneCountryCode"/></label>
	<form:select path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE %>" id="alternatePhoneCountryCode">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${countries}" var="country">
			<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE %>"/>
	</li>
	<li>
	<label for="alternatePhoneNumber"><spring:message code="phoneNumber"/></label>
	<form:input path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER %>" size="30" id="alternatePhoneNumber" />
	<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER %>"/>
	</li>
	
	<li>
	<label for="alternatePhoneExtension"><spring:message code="phoneExtension"/></label>
	<form:input path="<%=ProfileForm.ALTERNATE_PHONE_EXTENSION %>" size="30" id="alternatePhoneExtension"  class="smalltext" />
	<form:errors  class="errorMessage" path="<%=ProfileForm.ALTERNATE_PHONE_EXTENSION %>"/>
	</li>
	<li>
	<label for="alternatePhoneType"><spring:message code="phoneType"/></label>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_LANDLINE)}'>
			<form:radiobutton path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>" value="${phoneType.code}"/>${phoneType.description}
		</c:if>
	</c:forEach>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_MOBILE)}'>
			<form:radiobutton path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>" value="${phoneType.code}"/>${phoneType.description}
		</c:if>
	</c:forEach>
	<form:errors class="errorMessage" path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>"/>
	</li>
	
	
	



	</ul>
	</div>

</div>

 <div class="row-fluid">
    <div class="span6"><c:url value="/public/signin/" var="prev_url"/> 
<a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="back"/></a>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="next"/></button></span></div>
    </div>


</form:form>
</div>
</div> <!--  Form Canvas ends here --> 



</t:base>
