<%@ page import="org.ets.ereg.csr.web.util.Constant"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.Gender"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.PhoneType"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS - eREG (Personal Info)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Create Account</h1>
			</div>
		</div>
		<img src="${pageContext.request.contextPath}/commonweb/img/tca_profile_step_01.png">
		<br>
		<h2>Personal Information</h2>
		<span class="required_notification">* Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="tcaProfileForm">
		<div class="formContainer">
			<div class="create_form">
				<div class="formRow">
					<form:errors path="*" cssClass="errorblock" element="div" />
					<div class="create_form">
						<ul>
							<li><label>First or Given Name</label> <form:input
									path="<%=Constant.FIRST_NAME%>" required="required" /> <form:errors
									path="<%=Constant.FIRST_NAME%>" /> <span class="form_hint">Please
									make sure that you enter your name exactly as it is shown
									(excluding accent) on the <b>primary identification (ID)
										document.<br>
							</span></li>
							<div class="clear"></div>


							<li><label>Middle Initial</label> <form:input
									path="<%=Constant.MIDDLE_INITIAL%>" class="smalltext" /> <form:errors
									path="<%=Constant.MIDDLE_INITIAL%>" /></li>
							<div class="clear"></div>

							<li><label>Last or Family Name</label> <form:input
									path="<%=Constant.LAST_NAME%>" required="required" /> <form:errors
									path="<%=Constant.LAST_NAME%>" /></li>
							<li><label for="dateOfBirth">Date of Birth</label> <c:set
									var="monthIndex" value="1" /> <form:select
									path="<%=Constant.MONTH_OF_BIRTH%>" required="required"
									class="dobMonth" width="1">
									<form:option value="">Month</form:option>
									<c:forEach items="${months}" var="month">
										<form:option value="${monthIndex}">${month}</form:option>
										<c:set var="monthIndex" value="${monthIndex+1}" />
									</c:forEach>
								</form:select> <c:set var="dayIndex" value="1" /> <form:select
									path="<%=Constant.DAY_OF_BIRTH%>" required="required"
									class="dobDay">
									<form:option value="">Day</form:option>
									<c:forEach items="${days}" var="day">
										<form:option value="${dayIndex}">${day}</form:option>
										<c:set var="dayIndex" value="${dayIndex+1}" />
									</c:forEach>
								</form:select> <form:input path="<%=Constant.YEAR_OF_BIRTH%>" size="4"
									id="yearOfBirth" maxlength="4" pattern="[0-9]{4}"
									required="required" class="dobYear" /> <form:errors
									class="errorMessage" path="<%=Constant.DATE_OF_BIRTH%>" /></li>

							<li><c:set var="GENDER_MALE" value="<%=Gender.Male%>" /> <c:set
									var="GENDER_FEMALE" value="<%=Gender.Female%>" /> <c:set
									var="GENDER_PREFER_NOT_TO_ANSWER"
									value="<%=Gender.PreferNotToAnswer%>" /> <label for="gender">Gender</label>

								<c:forEach items="${genders}" var="gender">
									<c:if test='${gender.code.equalsIgnoreCase(GENDER_MALE)}'>
										<form:radiobutton path="<%=Constant.GENDER %>"
											value="${gender.code}" id="male" name="rr" class="radiocheck" />
									
									${gender.description}
									</c:if>
									<c:if test='${gender.code.equalsIgnoreCase(GENDER_FEMALE)}'>
										<form:radiobutton path="<%=Constant.GENDER %>"
											value="${gender.code}" id="fmale" name="rr"
											class="radiocheck" />${gender.description}
							
									</c:if>
									<c:if
										test='${gender.code.equalsIgnoreCase(GENDER_PREFER_NOT_TO_ANSWER)}'>
										<form:radiobutton path="<%=Constant.GENDER %>"
											value="${gender.code}" />${gender.description}
									</c:if>
								</c:forEach> <span class="radiostarcolor"></span> <form:errors
									class="errorMessage" path="<%=Constant.GENDER%>" /></li>


							<div style="clear: both"></div>


							<div class="clear"></div>
							<li><label>Email</label> <form:input
									path="<%=Constant.EMAIL_ADDRESS%>" required="required" /> <form:errors
									path="<%=Constant.EMAIL_ADDRESS%>" /></li>
							<div class="clear">
								<li><label>State / Agency ID</label> <form:input
										path="<%=Constant.IDENTIFICATION_TEXT%>" /> <span
									class="form_hint">Please enter State ID or Agency ID<br>
								</span></li>
								<div class="clear">

									</br>
									<h2>Phone *</h2>
									</br>


									<div class="clear"></div>
									<li><label>Country Code</label> <form:select
											path="<%=Constant.PHONE_COUNTRY_CODE%>"
											id="primaryPhoneCountryCode" required="required">
											<form:option value="">Select</form:option>
											<c:forEach items="${countries}" var="country">
												<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
											</c:forEach>
										</form:select> <form:errors path="<%=Constant.PHONE_COUNTRY_CODE%>" /></li>
									<li><label>Phone</label> <form:input
											path="<%=Constant.PHONE_NUMBER%>" required="required" /> <form:errors
											path="<%=Constant.PHONE_NUMBER%>" /></li>
									<li><label>Extension</label> <form:input
											path="<%=Constant.PHONE_EXTENSION%>" class="smalltext" /> <form:errors
											path="<%=Constant.PHONE_EXTENSION%>" /></li>
									<li><c:set var="PHONE_TYPE_LANDLINE"
											value="<%=PhoneType.Landline%>" /> <c:set
											var="PHONE_TYPE_MOBILE" value="<%=PhoneType.Mobile%>" /> <label
										for="primaryPhoneType">Type</label> <c:forEach
											items="${phoneTypes}" var="phoneType">
											<c:if
												test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_LANDLINE)}'>
												<form:radiobutton path="<%=Constant.PHONE_TYPE %>"
													value="${phoneType.code}" id="landline" class="radiocheck"
													name="pp" />
									${phoneType.description}
									</c:if>
										</c:forEach> <c:forEach items="${phoneTypes}" var="phoneType">
											<c:if
												test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_MOBILE)}'>
												<form:radiobutton path="<%=Constant.PHONE_TYPE %>"
													value="${phoneType.code}" id="mobile" class="radiocheck"
													name="pp" />
									${phoneType.description}
									</c:if>
										</c:forEach> <span class="radiophonecolor"></span> <form:errors
											class="errorMessage" path="<%=Constant.PHONE_TYPE%>" /> </span></li>
									<div class="clear"></div>
								</div>
							</div>
						</ul>
					</div>

				</div>

			</div>
			<!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="span6">
					<!-- <a class="submit" href="../home">Back</a> -->
				</div>
				<div class="span6">
					<span class="right"><button class="submit" type="submit">Next</button></span>
				</div>
			</div>
		</div>
	</form:form>
</t:base>
