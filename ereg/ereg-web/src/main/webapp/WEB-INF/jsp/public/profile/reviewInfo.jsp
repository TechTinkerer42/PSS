<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<style>
.review-help1{display:block; height:80px; padding:10px; font-size:0.87em; font-family:arial; position:absolute; width:350px; border:1px solid #F0BC20; background:#FCF1D1; color:#000; margin-left:450px; margin-top:20px; }
.review-help2 {display:block; height:40px; padding:10px; font-size:0.87em; font-family:arial; position:absolute; width:350px; border:1px solid #F0BC20; background:#FCF1D1; color:#000; margin-left:450px; margin:130px 0 0 450px;}
</style>
<t:base title="Create Profile - Review">
<div class="headContainer ">

<div class="row">
<div class="span9">
<h1><spring:message code="reviewInformation"/></h1></div>
</div>


<!--  <h2><spring:message code="reviewAndSubmit"/></h2> -->
<p><spring:message code="reviewAndSubmit2"/></p>

<img src="${pageContext.request.contextPath}/commonweb/img/step5.jpg"><br><br>
<h2><spring:message code="personalInformation"/></h2>
</div>


 
<!-- Form Canvas starts here -->
<div class="formContainer">

<div class="review_form">

<c:url value="/public/profile/account" var="prev_url"/>
<ul>
<li><label><spring:message code="firstName"/>:</label> <c:out value="${profileForm.profile.customer.firstName}"/>

<div class="review-help1">
<spring:message code="profile.create.reviewinfo.nameConfirmation" />
</div>

<div class="review-help2">
<spring:message code="profile.create.reviewinfo.dateOfBirthAndGenderConfirmation" />
</div>

</li>
<div style="clear:both;"></div>
<li><label><spring:message code="middleInitial"/>:</label> <c:out value="${profileForm.profile.customer.middleInitial}"/></li>
<div style="clear:both;"></div>
<li><label><spring:message code="lastName"/>:</label> <c:out value="${profileForm.profile.customer.lastName}"/></li>
<div style="clear:both;"></div>
<li><label><spring:message code="dateOfBirth"/>:</label> <ct:dateTime part="date" value="${profileForm.profile.customer.dateOfBirth}" /></li>
<div style="clear:both;"></div>
<li><label><spring:message code="gender"/>:</label><c:out value="${profileForm.profile.customer.gender.description}" /></li>
<div style="clear:both;"></div>
<li><label><spring:message code="socialSecurity"/>:</label><c:out value="${profileForm.profile.customer.socialSecurity}"/> </li>
<div style="clear:both;"></div>
<li><label><spring:message code="emailAddress"/>:</label><c:out value="${profileForm.profile.customer.emailAddress}"/></li>
<div style="clear:both;"></div>
<li><label><spring:message code="country"/>:</label><c:out value="${profileForm.profile.address.country.name}"/></li>
<div style="clear:both;"></div>
<li><label><spring:message code="addressLine1"/>:</label><c:out value="${profileForm.profile.address.addressLine1}"/> </li>
<div style="clear:both;"></div>
<li><label><spring:message code="addressLine2"/>:</label><c:out value="${profileForm.profile.address.addressLine2}"/> </li>
<div style="clear:both;"></div>
<li><label><spring:message code="city"/>:</label><c:out value="${profileForm.profile.address.city}"/></li>
<div style="clear:both;"></div>
<li><label><spring:message code="state"/>:</label><c:out value="${profileForm.profile.address.state.name}"/></li>
<div style="clear:both;"></div>
<li><label><spring:message code="postalCode"/>:</label><c:out value="${profileForm.profile.address.postalCode}"/></li>
<div style="clear:both;"></div>
	<li><label><spring:message code="primaryPhone"/>:</label>
					<c:out value="${profileForm.profile.primaryPhone.phoneNumber}" />
					<c:if test="${ profileForm.profile.primaryPhone.phoneExtension != null  && profileForm.profile.primaryPhone.phoneExtension.length() > 0}">
						<c:out value="ext. ${profileForm.profile.primaryPhone.phoneExtension}" />
					</c:if>
	</li>
	
	<div style="clear:both;"></div>
	
<li><label><spring:message code="alternatePhone"/>:</label><c:out value="${profileForm.profile.alternatePhone.phoneNumber}" />
					<c:if test="${ profileForm.profile.alternatePhone.phoneExtension != null &&  profileForm.profile.alternatePhone.phoneExtension.length() > 0}">
						<c:out value="ext. ${profileForm.profile.alternatePhone.phoneExtension}" />
					</c:if> </li>

</ul>
</div>

</div>

<div class="headContainer">
<h2><spring:message code="additionalInformation"/></h2>
</div>
<div class="formContainer">
<x`div class="review_form">

<ul>
<li><label><spring:message code="ethnicity"/>:</label><c:out value="${profileForm.profile.customer.ethnicityType.description}" /> </li>
<div style="clear:both;"></div>
<li><label><spring:message code="preferredLanguage"/>:</label><c:out value="${profileForm.profile.customer.prfrdTstTakingLang.description}" /></li>
<div style="clear:both;"></div>
<li><label><spring:message code="primaryLanguage"/>:</label><c:out value="${profileForm.profile.customer.prmrySpkngLang.description}" /></li>
<div style="clear:both;"></div>

<li><label><spring:message code="militaryMember"/>:</label>
	<c:choose>
      <c:when test="${profileForm.profile.customer.militaryMemberShip}">
      <c:out value="${profileForm.profile.customer.militaryStatus.description}" />
      </c:when>
      <c:otherwise>No<br />
      </c:otherwise>
     </c:choose>
	</li>
</ul>
</div>

<div class="headContainer">
<h2><spring:message code="backgroundInformation"/></h2>
</div>
<div class="formContainer">
<div class="review_form">

<ul>
<c:forEach var="demographicQuestion" items="${profileForm.profile.demographicQuestions}" varStatus="i">
    	
	 	<!-- CheckBox-->
    	<c:if test="${demographicQuestion.answered}">
    	<li>
    	           	    
            		<strong>${demographicQuestion.displayText}</strong></br>${demographicQuestion.getResponse()} <br/> 
            		
   
		</li>
	    </c:if>	    
     
	 
     </c:forEach>
	 
     
   </ul>  
    
       
       
       
    
</div>

</div>


 
<form:form method="post" modelAttribute="profileForm" action="../profile/">



<div class="bottomContainer">

 <div class="row-fluid">
    <div class="span6"><c:url value="/public/profile/account" var="prev_url"/> 
<a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="back"/></a>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="submit"/></button></span></div>
    </div>

</div>
</form:form>

 <!--  Form Canvas ends here -->



</t:base>
