<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<t:base title="ETS - eREG (Review Profile)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9"><h1>Review and Submit</h1></div>
		</div>
		<c:choose> 
			<c:when test="${eregUtils.isOAMAuthentication()}">
				<img src="${pageContext.request.contextPath}/commonweb/img/step4OAM.jpg">
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/commonweb/img/step5.jpg">
			</c:otherwise>
		</c:choose>
		
		
		<br>
		<h2>Personal Information</h2>
	</div>
	<form:form method="POST" modelAttribute="profileForm">
		<div class="formContainer">
			<div class="review_form">
				<div class="formRow">
					<ul>		
						<li>
							<label>First or Given Name</label>
							<c:out value="${profileForm.profile.customer.firstName}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Middle Initial</label>
							<c:out value="${profileForm.profile.customer.middleInitial}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Last or Family Name</label>
							<c:out value="${profileForm.profile.customer.lastName}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Date of Birth</label>
							<ct:dateTime part="date" value="${profileForm.profile.customer.dateOfBirth}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Gender</label>
							<c:out value="${profileForm.profile.customer.gender.description}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Social Security Number (Last 4 Digit)</label>
							<c:out value="${profileForm.profile.customer.socialSecurity}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Country/Location</label>
							<c:out value="${profileForm.profile.address.country.name}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Address Line 1</label>
							<c:out value="${profileForm.profile.address.addressLine1}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Address Line 2</label>
							<c:out value="${profileForm.profile.address.addressLine2}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>City</label>
							<c:out value="${profileForm.profile.address.city}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>State</label>
							<c:out value="${profileForm.profile.address.state.name}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>ZIP Code</label>
							<c:out value="${profileForm.profile.address.postalCode}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Primary Phone</label>
							<c:out value="${profileForm.profile.primaryPhone.phoneNumber}" />
							<c:if test="${ profileForm.profile.primaryPhone.phoneExtension ne ''}">
								<c:out value="ext. ${profileForm.profile.primaryPhone.phoneExtension}" />
							</c:if>
							<c:out value=" ${profileForm.profile.primaryPhone.phoneType.description}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Alternate Phone</label>
							<c:out value="${profileForm.profile.alternatePhone.phoneNumber}" />
							<c:if test="${ profileForm.profile.alternatePhone.phoneExtension ne ''}">
							</c:if>
							<c:out value=" ${profileForm.profile.alternatePhone.phoneType.description}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Email</label>
							<c:out value="${profileForm.profile.customer.emailAddress}" />
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="headContainer">
			<h2>Additional Information</h2>
		</div>
		<div class="formContainer">
			<div class="review_form">
				<div class="formRow">
					<ul>
						<li>
							<label>Ethnicity</label>
							<c:out value="${profileForm.profile.customer.ethnicityType.description}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Preferred Language for Test Taking</label>
							<c:out value="${profileForm.profile.customer.prfrdTstTakingLang.description}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Primary Speaking Language</label>
							<c:out value="${profileForm.profile.customer.prmrySpkngLang.description}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Military Member</label>
							<c:choose>
								<c:when test="${profileForm.profile.customer.militaryMemberShip == true}">
									Yes
								</c:when>
								<c:otherwise>
									No
								</c:otherwise>
							</c:choose>
						</li>
						<div class="clear"></div>
						<li>
							<label>Military Status</label>
							<c:out value="${profileForm.profile.customer.militaryStatus.description}" />
						</li>
						<div class="clear"></div>
						
					 <c:choose> 
                     	<c:when test="${eregUtils.isOAMAuthentication()}">
                    	 </c:when>
                	     <c:otherwise>
						<li>
							<label>User Name</label>
							<c:out value="${profileForm.profile.customer.username}" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Generated Password</label>
							<c:out value="${profileForm.profile.customer.password}" />
						</li>
            	         </c:otherwise>
        	         </c:choose>
					</ul>	
				</div>
			</div>
		</div>
		<div class="headContainer">
        <h2><spring:message code="backgroundInformation"/></h2>
        </div>
		<div class="formContainer">
           <div class="review_form">
	           <ul>
				 	<c:forEach var="demographicQuestion" items="${profileForm.profile.demographicQuestions}" varStatus="i">
						<c:if test="${demographicQuestion.answered}">
							<li>
								<strong>	${demographicQuestion.displayText}</strong></br>${demographicQuestion.getResponse()}<br/> 
							</li>
						</c:if>
					</c:forEach>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="bottomContainer">
			<div class="row-fluid">
				<div class="span6">
					<a class="submit" href="<c:url value='${prev_url}'/>">Back</a>
				</div>
				<div class="span6">
					<span class="right"><button class="submit" type="submit">
							<spring:message code="submit" />
						</button></span>
				</div>
			</div>
		</div>
	</form:form>
</t:base>