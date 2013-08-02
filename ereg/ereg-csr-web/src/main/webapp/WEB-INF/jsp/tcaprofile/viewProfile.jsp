<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<t:base title="ETS - eREG (View Profile)">
	<div class="headContainer ">
		<!--<div class="row">
							<div class="span9"><h1>View Profile</h1></div>
						</div>-->
	</div>

	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="tcaProfileForm">

		<div class="formContainer">
			<div class="review_form">
				<div class="formRow">
					<div>
						<h3>
						<spring:message code="profile.tca.view.heading" />
						</h3>
					</div>
					<ul>
						<li><label>First or Given Name :</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.firstName}" /></li>
						<div class="clear"></div>
						<li><label>Middle Initial :</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.middleName}" /></li>
						<div class="clear"></div>
						<li><label>Last or Family Name :</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.lastName}" /></li>
						<div class="clear"></div>
						<li><label>Date of Birth :</label> <ct:dateTime part="date"
								value="${tcaProfileForm.tcaProfile.adminUser.dateOfBirth}" /></li>
						<div class="clear">
							<li><label>Gender :</label> <c:out
									value="${tcaProfileForm.tcaProfile.adminUser.gender.description}" /></li>
							<div class="clear"></div>
							<li><label>Email :</label> <c:out
									value="${tcaProfileForm.tcaProfile.adminUser.email}" /></li>
							<div class="clear"></div>
							<li><label>State / Agency ID :</label> <c:out
									value="${tcaProfileForm.tcaProfile.adminUser.adminIdentificationStr}" /></li>
							<div class="clear"></div>
							<li><label>Phone :</label> <c:if
									test="${ tcaProfileForm.tcaProfile.adminUser.etsPhone.country.isdCode ne  null}">
									<c:out
										value="${tcaProfileForm.tcaProfile.adminUser.etsPhone.country.isdCode}-" />
								</c:if> <c:out
									value="${tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneNumber}" />
								<c:if
									test="${ tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneExtension ne  null}">
									<c:out
										value="ext. ${tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneExtension}" />
								</c:if></li>
							<div class="clear"></div>
							<li><label>Phone Type :</label> <c:if
									test="${ tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneType ne  null}">
									<c:out
										value="${tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneType.description}" />
								</c:if></li>
							<div class="clear"></div>
					</ul>

				</div>
			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<c:url value="/secure/home" var="backUrl" />
				<div class="ereg_span_01"">
					<a class="submit" href="<c:out value='${backUrl}'/>">Cancel</a> <span
						class="right" style="margin: -4px 0 0 0;"><button
							class="submit" type="submit" style="float: right;">Edit</button></span>
				</div>
			</div>
		</div>
	</form:form>
</t:base>