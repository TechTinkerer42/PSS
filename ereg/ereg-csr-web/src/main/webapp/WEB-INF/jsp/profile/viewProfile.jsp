<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>


<t:base title="ETS - eREG (View Profile)">
	<div class="headContainer ">
		<!--<div class="row">
			<div class="span9"><h1>View Profile</h1></div>
		</div>-->
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="profileFormView">
		<div class="formContainer">
			<div class="review_form">
				<div class="formRow">
					<h3>Personal Information</h3>

					<ul>
						<li><label>Test Taker ID:</label>
						<c:out value="${profileFormView.profile.custLinkageKey}" /></li>
						<div class="clear"></div>
						<li><label>First or Given Name :</label>
						<c:out value="${profileFormView.profile.customer.firstName}" /></li>
						<div class="clear"></div>
						<li><label>Middle Initial :</label>
						<c:out value="${profileFormView.profile.customer.middleInitial}" /></li>
						<div class="clear"></div>
						<li><label>Last or Family Name :</label>
						<c:out value="${profileFormView.profile.customer.lastName}" /></li>
						<div class="clear"></div>
						<li><label>Country/Location:</label>
						<c:out value="${profileFormView.profile.address.country.name}" /></li>
						<div class="clear"></div>
						<li><label>Address Line 1 :</label>
						<c:out value="${profileFormView.profile.address.addressLine1}" /></li>
						<div class="clear"></div>
						<li><label>Address Line 2 :</label>
						<c:out value="${profileFormView.profile.address.addressLine2}" /></li>
						<div class="clear"></div>
						<li><label>City :</label>
						<c:out value="${profileFormView.profile.address.city}" /></li>
						<div class="clear"></div>
						<li><label>State :</label>
						<c:out value="${profileFormView.profile.address.state.name}" /></li>
						<div class="clear"></div>
						<li><label>ZIP Code :</label>
						<c:out value="${profileFormView.profile.address.postalCode}" /></li>
						<div class="clear"></div>
						<li><label>Primary Phone :</label> <c:if
								test="${ profileFormView.profile.primaryPhone.country.isdCode ne  null}">
								<c:out
									value="${profileFormView.profile.primaryPhone.country.isdCode}-" />
							</c:if> <c:out
								value="${profileFormView.profile.primaryPhone.phoneNumber}" /> <c:if
								test="${ profileFormView.profile.primaryPhone.phoneExtension ne  null}">
								<c:out
									value="ext. ${profileFormView.profile.primaryPhone.phoneExtension}" />
							</c:if></li>
						<div class="clear"></div>
						<li><label>Primary Phone Type :</label>
						<c:if
								test="${ profileFormView.profile.primaryPhone.phoneType ne  null}">
								<c:out
									value="${profileFormView.profile.primaryPhone.phoneType.description}" />
							</c:if></li>
						<div class="clear"></div>
						<li><label>Alternate Phone :</label> <c:if
								test="${ profileFormView.profile.alternatePhone.country.isdCode ne  null}">
								<c:out
									value="${profileFormView.profile.alternatePhone.country.isdCode}-" />
							</c:if> <c:out
								value="${profileFormView.profile.alternatePhone.phoneNumber}" />
							<c:if
								test="${ profileFormView.profile.alternatePhone.phoneExtension ne  null}">
								<c:out
									value="ext. ${profileFormView.profile.alternatePhone.phoneExtension}" />
							</c:if></li>
						<div class="clear"></div>
						<li><label>Alternate Phone Type :</label> <c:if
								test="${ profileFormView.profile.alternatePhone.phoneType ne  null}">
								<c:out
									value="${profileFormView.profile.alternatePhone.phoneType.description}" />
							</c:if></li>
						<div class="clear"></div>
						<li><label>Email :</label>
						<c:out value="${profileFormView.profile.customer.emailAddress}" /></li>
						<div class="clear"></div>
						<li><h3>Additional Information</h3></li>
						<li><label>Date of Birth :</label>
						<ct:dateTime part="date"
								value="${profileFormView.profile.customer.dateOfBirth}" /></li>
						<div class="clear"></div>
						<li><label>Gender :</label>
						<c:out
								value="${profileFormView.profile.customer.gender.description}" /></li>
						<div class="clear"></div>
						<li><label>Ethnicity :</label>
						<c:out
								value="${profileFormView.profile.customer.ethnicityType.description}" /></li>
						<div class="clear"></div>
						<li><label>Preferred Language for Test Taking :</label>
						<c:out
								value="${profileFormView.profile.customer.prfrdTstTakingLang.description}" /></li>
						<div class="clear"></div>
						<li><label>Primary Speaking Language :</label>
						<c:out
								value="${profileFormView.profile.customer.prmrySpkngLang.description}" /></li>
						<div class="clear"></div>
						<!--<label>Military Member :</label><span><c:out value="${profileFormView.profile.customer.militaryMemberShip}" /></span><br />-->
						<li><label>Military Status :</label> <c:if
								test="${profileFormView.profile.customer.militaryStatus ne null}">
								<c:out
									value="${profileFormView.profile.customer.militaryStatus.description}" />
							</c:if></li>
						<div class="clear"></div>
						<li><label>Social Security Number (Last 4 Digit) :</label>
						<c:out value="${profileFormView.profile.customer.socialSecurity}" /></li>
						<div class="clear"></div>
						<!--<label>Generated Password :</label><span><c:out value="${profileFormView.profile.customer.password}" /></span><br />-->
					</ul>

					<div class="row-fluid"></div>

				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="span6" style="margin: 4px 0 0 0;">
				
					<c:if test="${not empty viewProfileBackUrl}">
						<c:choose>
						    <c:when test="${fn:containsIgnoreCase(viewProfileBackUrl, '/profile/review') or fn:containsIgnoreCase(viewProfileBackUrl, '/appointment/new/review')}">
							    <c:remove var="viewProfileBackUrl"/>
								<a class="othbutton" href="<ct:encode out="/secure/testtaker/view/?customerId=${profileFormView.profile.customer.id}" />">
									Go to Test Taker's Summary</a>&nbsp;&nbsp;&nbsp;&nbsp;
						    </c:when>
							    <c:when test="${fn:containsIgnoreCase(viewProfileBackUrl, '/profile/edit')}">
							    <c:remove var="viewProfileBackUrl"/>
							        <a class="submit" onclick="window.history.back();">Back</a>
						    </c:when>
						    <c:otherwise>
						       
						    </c:otherwise>
						</c:choose>			
					</c:if>
				</div>
				<sec:authorize access="hasRole('PERMISSION_UPDATE_CUSTOMER')">

					<div class="span6">
						<span class="right"><button class="submit" type="submit">Edit</button></span>
					</div>
				</sec:authorize>

			</div>
		</div>
	</form:form>
</t:base>