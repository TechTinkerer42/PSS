<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<t:base title="Update Profile">

<div class="headContainer">

<div class="row">
<div class="span9"><h1><spring:message code="updateProfile"/></h1></div>
</div>

 <h2><spring:message code="personalInformation"/></h2>
             <span class="required_notification"><spring:message code="requiredInformation"/></span>

</div>

<!-- Form Canvas starts here -->
<div class="formContainer">




<div class="create_form">



<form:form method="post" modelAttribute="profileForm">
<div class="formRow">
 	<c:set var="STATUS_OK" value="<%=ProfileForm.STATUS_OK%>" />
	<c:set var="STATUS_KO" value="<%=ProfileForm.STATUS_KO%>" />
  	<c:if test="${profileForm.statusCode==STATUS_KO}">
 		<c:out value="${profileForm.statusMessage}"/>
 	</c:if>
  <ul>
  
   <li><div id="testTakerId"><label><spring:message code="testTakerId"/>:</label>
 <span class="label2"><c:out value="${profileForm.profile.custLinkageKey}" /></span></div></li>
 
 
	<li>
	<div id="firstName"><label><spring:message code="firstName"/> </label> <span class="label2"><c:out value="${profileForm.profile.customer.firstName}"  /> </span></div>
	</li>

	<li>
	<div id="middleInitial"><label><spring:message code="middleInitial"/></label> <span class="label2"><c:out value="${profileForm.profile.customer.middleInitial}"  /></span></div>
	</li>

	<li>
	<div id="lastName"><label><spring:message code="lastName"/> </label> <span class="label2"><c:out value="${profileForm.profile.customer.lastName}"  /></span></div>
	</li>
	
	<li>
	<div id="dateOfBirth"><label><spring:message code="dateOfBirth"/> </label> <span class="label2"><ct:dateTime part="date" value="${profileForm.profile.customer.dateOfBirth}" /></span></div>
	</li>
	
	<li>
	<div id="gender"><label><spring:message code="gender"/> </label> <span class="label2"><c:out value="${profileForm.profile.customer.gender.description}"/></span></div>
	</li>
	
	<div style="clear:both;"></div>

	<li>
	<label for="country"><spring:message code="country"/></label>
	<form:select path="<%=ProfileForm.COUNTRY%>" required="required">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${countries}" var="country">
			<form:option value="${country.abbreviation}">${country.name}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.COUNTRY%>"/>
	</li>

	<li>
	<label for="addressLine1"><spring:message code="addressLine1"/></label>
	<form:input path="<%=ProfileForm.ADDRESS_LINE1%>" size="30" id="addressLine1" required="required"/>
	<form:errors path="<%=ProfileForm.ADDRESS_LINE1%>"/>
	</li>

	<li>
	<label for="addressLine2"><spring:message code="addressLine2"/></label>
	<form:input path="<%=ProfileForm.ADDRESS_LINE2%>" size="30" id="addressLine2" />
	</li>

	<li>
	<label for="city"><spring:message code="city"/></label>
	<form:input path="<%=ProfileForm.CITY%>" size="30" id="city" required="required"/>
	<form:errors path="<%=ProfileForm.CITY%>"/>
	</li>

	<li>
	<label for="state"><spring:message code="state"/></label>
	<form:select path="<%=ProfileForm.STATE%>" required="required">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${states}" var="state">
			<form:option value="${state.abbreviation}">${state.name}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.STATE%>"/>
	</li>

	<li>
	<label for="postalCode"><spring:message code="postalCode"/></label>
	<form:input path="<%=ProfileForm.POSTAL_CODE%>" size="30" id="postalCode" class="smalltext"/>
	<form:errors path="<%=ProfileForm.POSTAL_CODE%>"/>
</li>

<li>
	<label for="socialSecurity"><spring:message code="socialSecurity"/></label>
	<form:input path="<%=ProfileForm.SOCIAL_SECURITY %>" size="10" id="socialSecurity" class="smalltext"/>
	<form:errors path="<%=ProfileForm.SOCIAL_SECURITY %>"/>
	</li>
	<li>
	<label for="emailAddress"><spring:message code="emailAddress"/></label>
	<form:input path="<%=ProfileForm.EMAIL_ADDRESS %>" size="30" id="emailAddress" required="required" />
	<form:errors path="<%=ProfileForm.EMAIL_ADDRESS %>"/>
	</li>



<div style="clear:both;"></div>
	<h2><spring:message code="primaryPhone"/></h2>
	<li>
	<label for="primaryPhoneCountryCode"><spring:message code="phoneCountryCode"/></label>
	<form:select path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>" id="primaryPhoneCountryCode">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${countries}" var="country">
			<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.PRIMARY_PHONE_COUNTRY_CODE%>"/>
	</li>

	<li>
	<label for="primaryPhoneNumber"><spring:message code="phoneNumber"/></label>
	<form:input path="<%=ProfileForm.PRIMARY_PHONE_PHONE_NUMBER%>" size="30" id="primaryPhoneNumber" />
	<form:errors path="<%=ProfileForm.PRIMARY_PHONE_PHONE_NUMBER%>"/>
	</li>

	<li>
	<label for="primaryPhoneExtension"><spring:message code="phoneExtension"/></label>
	<form:input path="<%=ProfileForm.PRIMARY_PHONE_EXTENSION %>" size="30" id="primaryPhoneExtension" class="smalltext" />
	<form:errors path="<%=ProfileForm.PRIMARY_PHONE_EXTENSION %>"/>
	</li>
	<c:set var="PHONE_TYPE_LANDLINE" value="<%=PhoneType.Landline%>" />
	<c:set var="PHONE_TYPE_MOBILE" value="<%=PhoneType.Mobile%>" />
	</li>
	<li>
	<label for="primaryPhoneType"><spring:message code="phoneType"/></label>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_LANDLINE)}'>
		<form:radiobutton path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>" value="${phoneType.code}"/>${phoneType.description}
		</c:if>
	</c:forEach>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_MOBILE)}'>
		<form:radiobutton path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>" value="${phoneType.code}"/>${phoneType.description}
		</c:if>
	</c:forEach>
	<form:errors path="<%=ProfileForm.PRIMARY_PHONE_PHONE_TYPE %>"/>
	</li>

<div style="clear:both"></div>

	<h2><spring:message code="alternatePhone"/></h2>
	<li>
	<label for="alternatePhoneCountryCode"><spring:message code="phoneCountryCode"/></label>
	<form:select path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE %>" id="alternatePhoneCountryCode">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${countries}" var="country">
			<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_COUNTRY_CODE %>"/>
	</li>
	<li>
	<label for="alternatePhoneNumber"><spring:message code="phoneNumber"/></label>
	<form:input path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER %>" size="30" id="alternatePhoneNumber" />
	<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_NUMBER %>"/>
	</li>

	<li>
	<label for="alternatePhoneExtension"><spring:message code="phoneExtension"/></label>
	<form:input path="<%=ProfileForm.ALTERNATE_PHONE_EXTENSION %>" size="30" id="alternatePhoneExtension" class="smalltext"/>
	<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_EXTENSION %>"/>
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
	<form:errors path="<%=ProfileForm.ALTERNATE_PHONE_PHONE_TYPE %>"/>
	</li>


<div style="clear:both"></div>


	
	


	<h2><spring:message code="additionalInformation"/></h2>

	
<div style="clear:both"></div>
	<li>
	<label for="ethnicity"><spring:message code="ethnicity"/></label>
	<form:select path="<%=ProfileForm.ETHNICITY %>" id="ethnicity">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${ethnicityTypes}" var="ethnicityType">
			<form:option value="${ethnicityType.code}">${ethnicityType.description}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.ETHNICITY %>"/>
	</li>

	<li>
	<c:set var="ENGLISH" value="<%=LanguageType.English%>" />
	<c:set var="SPANISH" value="<%=LanguageType.Spanish%>" />
	<label for="preferredLanguage">* <spring:message code="preferredLanguage"/></label>
	<c:forEach items="${languageTypes}" var="languageType">
		<c:if test='${languageType.code.equalsIgnoreCase(ENGLISH)}'>
		<form:radiobutton path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING %>" value="${languageType.code}"/>${languageType.description}
		</c:if>
		<c:if test='${languageType.code.equalsIgnoreCase(SPANISH)}'>
		<form:radiobutton path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING %>" value="${languageType.code}"/>${languageType.description}
		</c:if>
	</c:forEach>
	<form:errors path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING %>"/>
	</li>

<div style="clear:both"></div>

	<li>
	<label for="primaryLanguage"><spring:message code="primaryLanguage"/></label>
	<form:select path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE %>" id="primaryLanguage">
		<form:option value="">Select</form:option>
		<c:forEach items="${languageTypes}" var="languageType">
			<form:option value="${languageType.code}">${languageType.description}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE %>"/>
	</li>

<div style="clear:both"></div>
	<li>
	<label for="militaryMembership">* <spring:message code="militaryMember"/></label>
	<form:radiobutton path="<%=ProfileForm.IS_MILITARY_MEMBER %>" value="true"/>Yes
	<form:radiobutton path="<%=ProfileForm.IS_MILITARY_MEMBER %>" value="false"/>No
	<form:errors path="<%=ProfileForm.IS_MILITARY_MEMBER %>"/>
	</li>

<div style="clear:both"></div>
	<li>
	<label for="militaryStatus"><spring:message code="militaryStatus"/></label>
	<form:select path="<%=ProfileForm.MILITARY_STATUS %>" id="militaryStatus">
		<form:option value="">Select</form:option>
		<c:forEach items="${militaryStatusTypes}" var="militaryStatusType">
			<form:option value="${militaryStatusType.code}">${militaryStatusType.description}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="<%=ProfileForm.MILITARY_STATUS %>"/>
	</li>

	</ul>
	
	</div>



 <div class="row-fluid">
    <div class="span6">
	<c:url value="/secure/home" var="prev_url"/>
	<a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="cancel"/></a>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="submit"/></button></span></div>
    </div>



</form:form>
</div>

</div> <!--  Form Canvas ends here -->

</t:base>