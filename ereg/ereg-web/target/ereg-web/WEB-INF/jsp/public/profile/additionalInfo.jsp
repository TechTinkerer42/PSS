<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base title="Create Profile - Additional Info">


<div class="headContainer ">
<div class="row">
<div class="span9"><h1><spring:message code="createAccount"/></h1></div>
</div>


<p><spring:message code="additionalInfo.message"/></p>

  <img src='<spring:url value="/commonweb/img/step2.jpg" htmlEscape="true"/>' alt=""><br><br>
</div>




<div class="headContainer">
             <h2><spring:message code="additionalInformation"/></h2>
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




<div class="create_form">
<c:url value="/public/profile/" var="prev_url"/> 
<form:form method="post" modelAttribute="profileForm">









 <ul>

	<li>
	<label for="ethnicity"><spring:message code="ethnicity"/></label>
	<form:select path="<%=ProfileForm.ETHNICITY %>" id="ethnicity">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${ethnicityTypes}" var="ethnicityType">
			<form:option value="${ethnicityType.code}">${ethnicityType.description}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.ETHNICITY %>"/>
	</li>
<div style="clear:both;"></div>
	<li>
	<c:set var="ENGLISH" value="<%=LanguageType.English%>" />
	<c:set var="SPANISH" value="<%=LanguageType.Spanish%>" />
	<label for="preferredLanguage"><spring:message code="preferredLanguage"/></label>
	<c:forEach items="${languageTypes}" var="languageType">
		<c:if test='${languageType.code.equalsIgnoreCase(ENGLISH)}'>
		<form:radiobutton path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING %>" value="${languageType.code}" id="lEnglish" name="les" class="radiocheck"/>
		${languageType.description}
		</c:if>
		<c:if test='${languageType.code.equalsIgnoreCase(SPANISH)}'>
		<form:radiobutton path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING %>" value="${languageType.code}" id="lSpanish" name="les" class="radiocheck"/>
         ${languageType.description}</label>
		</c:if>
	</c:forEach>
	<span class="languageRadio"></span>
	<form:errors class="errorMessage" path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING %>"/>

	</li>
<div style="clear:both;"></div>
	<li>
	<label for="primaryLanguage"><spring:message code="primaryLanguage"/></label>
	<form:select path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE %>" id="primaryLanguage">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${languageTypes}" var="languageType">
			<form:option value="${languageType.code}">${languageType.description}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE %>"/>
	</li>
	<div style="clear:both;"></div>
	<li>
	<label for="militaryMembership"><spring:message code="militaryMember"/></label>
	
	<form:radiobutton path="<%=ProfileForm.IS_MILITARY_MEMBER %>" value="true" id="mmy"  class="radiocheck"/>
	Yes
	<form:radiobutton path="<%=ProfileForm.IS_MILITARY_MEMBER %>" value="false" id="mmn"  class="radiocheck"/>
	No

	<span class="militaryRadio"></span>
	<form:errors class="errorMessage" path="<%=ProfileForm.IS_MILITARY_MEMBER %>"/>
	
	</li>
	<div style="clear:both;"></div>
	<li>
	<label for="militaryStatus"><spring:message code="militaryStatus"/></label>
	<form:select path="<%=ProfileForm.MILITARY_STATUS %>" id="militaryStatus" disabled="disabled">
		<form:option value=""><spring:message code="select"/></form:option>
		<c:forEach items="${militaryStatusTypes}" var="militaryStatusType">
			<form:option value="${militaryStatusType.code}">${militaryStatusType.description}</form:option>
		</c:forEach>
	</form:select>
	<form:errors class="errorMessage" path="<%=ProfileForm.MILITARY_STATUS %>"/>
	</li>
	
</ul>
	


 <div class="row-fluid">
    <div class="span6"><a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="back"/></a>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="next"/></button></span></div>
    </div>




</form:form>

</div> <!--  Form Canvas ends here -->





</div>

</t:base>