<%@ page import="org.ets.ereg.csr.web.form.profile.TCAProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS - eREG (Profile Created)">
	<div class="formContainer">
		<div class="create_form">
			<h2>Create Profile</h2>
			<hr>
			<h3>Account Created</h3>
			<hr>
			<h3>
			<spring:message code="profile.create.accountcreated.successMessage" />
			</h3>
			<c:url value="/secure/tca/profile/" var="baseUrl" />
			<%-- <a href='<c:out value="${baseUrl}${tcaProfileForm.tcaProfile.adminUser.id}" />'>View Profile</a> --%>
			<br>
			<form:form method="POST" modelAttribute="tcaProfileForm">
				<ul>
					<div class="clear"></div>
					<spring:message code="profile.tca.activationMessage" />					
					<div class="clear"></div>
					<a
						href="mailto:?subject=HSET TCA approval request&body=Dear <Please enter the name of your State/
						Agency Administrator>, %0A%0AI have created my initial TCA
						account. Please approve my request.%0A%0AMy info is as follows:
						%0A &#x1d5e1;&#x1d5ee;&#x1d5fa;&#x1d5f2;: <c:out
							value="${tcaProfileForm.tcaProfile.adminUser.firstName}" /> <c:out
							value="${tcaProfileForm.tcaProfile.adminUser.lastName}" />%0A
						&#x1d5d8;&#x1d5fa;&#x1d5ee;&#x1d5f6;&#x1d5f9;
						&#x1d5d4;&#x1d5f1;&#x1d5f1;&#x1d5ff;&#x1d5f2;&#x1d600;&#x1d600;: <c:out
							value="${tcaProfileForm.tcaProfile.adminUser.email}" />%0A
						&#x1d5e3;&#x1d5f5;&#x1d5fc;&#x1d5fb;&#x1d5f2;
						&#x1d5e1;&#x1d602;&#x1d5fa;&#x1d5ef;&#x1d5f2;&#x1d5ff;: <c:if
							test="${ tcaProfileForm.tcaProfile.adminUser.etsPhone.country.isdCode ne  null && !(empty tcaProfileForm.tcaProfile.adminUser.etsPhone.country.isdCode)}">
							<c:out
								value="${tcaProfileForm.tcaProfile.adminUser.etsPhone.country.isdCode}-" />
						</c:if> <c:out
							value="${tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneNumber}" />
						<c:if
							test="${ tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneExtension ne  null && !(empty tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneExtension) }">
							<c:out
								value="ext. ${tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneExtension}" />
						</c:if> %0A &#x1d5db;&#x1d5e6;&#x1d5d8;&#x1d5e7;
						&#x1d5e8;&#x1d600;&#x1d5f2;&#x1d5ff;&#x1d5fb;&#x1d5ee;&#x1d5fa;&#x1d5f2;:
						<c:out value="${tcaProfileForm.tcaProfile.adminUser.login}" />
						%0A &#x1d5e7;&#x1d5f2;&#x1d600;&#x1d601;
						&#x1d5d6;&#x1d5f2;&#x1d5fb;&#x1d601;&#x1d5f2;&#x1d5ff;: <Please
							enter your Test Center Name>%0A%0A%0A Thanks, %0A <c:out
							value="${tcaProfileForm.tcaProfile.adminUser.firstName}" /> <c:out
							value="${tcaProfileForm.tcaProfile.adminUser.lastName}" />">Mail
						State Admin
					</a>
				</ul>
			</form:form>
		</div>
	</div>
	<!--  Form Canvas ends here -->
	<div class="formButtonsContainer">
		<a href="/ereg-csr-web/secure/home" class="submit"><spring:message code="home" /></a>
	</div>

</t:base>
