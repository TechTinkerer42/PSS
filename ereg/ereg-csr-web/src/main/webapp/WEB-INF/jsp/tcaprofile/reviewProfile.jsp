<%@ page import="org.ets.ereg.csr.web.form.profile.TCAProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<t:base title="ETS - eREG (Review)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Review and Submit</h1>
			</div>
		</div>
		<img src="${pageContext.request.contextPath}/commonweb/img/tca_profile_step_03.png">
		<br>
		<h2>SSPersonal Information</h2>

	</div>
	<!-- Form Canvas starts here -->
	<div class="formContainer">
		<div class="review_form">
			<div class="formRow">
				<form:form method="POST" modelAttribute="tcaProfileForm">
					<ul>

						<li><label>First or Given Name</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.firstName}" /></li>
						<div class="clear"></div>
						<li><label>Middle Initial</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.middleName}" /></li>
						<div class="clear"></div>
						<li><label>Last or Family Name</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.lastName}" /></li>
						<div class="clear"></div>
						<li><label>Date of Birth</label> <ct:dateTime part="date"
								value="${tcaProfileForm.tcaProfile.adminUser.dateOfBirth}" /></li>
						<div class="clear"></div>
						<li><label>Gender</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.gender.description}" />
						</li>
						<div class="clear"></div>
						<li><label>Email</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.email}" /></li>
						<div class="clear"></div>
						<li><label>State / Agency ID :</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.adminIdentificationStr}" /></li>
						<div class="clear"></div>


						<li><label>Phone</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneNumber}" />
							<c:if
								test="${ tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneExtension ne ''}">
								<c:out
									value="ext. ${tcaProfileForm.tcaProfile.adminUser.etsPhone.phoneExtension}" />
							</c:if></li>
						<div class="clear"></div>
						<li><label>Username</label> <c:out
								value="${tcaProfileForm.tcaProfile.adminUser.login}" /></li>

					</ul>
			</div>

		</div>
	</div>
	<div class="formButtonsContainer">
		<div class="row-fluid">
			<div class="span6" style="margin: 0 0 0 0;">
				<a class="submit" href="<c:url value='/tca/profile/account'/>">Back</a>
			</div>
			<div class="span6">
				<span class="right" style="margin: -4px 0px 0px 0px"><button class="submit" type="submit">Submit</button></span>
			</div>
		</div>
	</div>

	</form:form>
</t:base>
