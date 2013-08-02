<%@ page import="org.ets.ereg.csr.web.form.profile.TCAProfileForm"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*" %>
<%@ page import="org.ets.ereg.csr.web.util.Constant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Update Profile)">
					<div class="headContainer ">
						<div class="row">
							<div class="span9"><h1>Update Profile</h1></div>
						</div>
						<span class="required_notification">* Required Information</span>
					</div>
					<!-- Form Canvas starts here -->
					<div class="formContainer">
						<div class="create_form">
							<div class="formRow">
<div class="formRow">
 
  <ul>
<br /><h2>Personal Information</h2><br />
 

 <div class="clear"></div>
	<li>
	<label for="firstName">First Name</label>
	<form:input path="tcaProfile.adminUser.firstName" size="30" id="firstName" required="required" />
	<form:errors path="tcaProfile.adminUser.firstName"/>
	</li>
	<div class="clear"></div>
	<li>
	<label for="middleInitial">Middle Initial</label>
	<form:input path="tcaProfile.adminUser.middleName" size="20" id="middleInitial"/>
	<form:errors path="tcaProfile.adminUser.middleName"/>
	</li>
	<div class="clear"></div>
	<li>
	<label for="lastName">Last Name</label>
	<form:input path="tcaProfile.adminUser.lastName" size="30" id="lastName" required="required" />
	<form:errors path="tcaProfile.adminUser.lastName"/>
	</li>
	<div class="clear"></div>
	<li>
	<label for="dateOfBirth">Date of Birth</label>

	<c:set var="monthIndex" value="1" />
	<form:select path="<%=Constant.MONTH_OF_BIRTH%>" required="required" class="dobMonth" width="1">
		<form:option value="">Month</form:option>
		<c:forEach items="${months}" var="month">
			<form:option value="${monthIndex}">${month}</form:option>
			<c:set var="monthIndex" value="${monthIndex+1}"/>
		</c:forEach>
	</form:select>
	<c:set var="dayIndex" value="1" />
	<form:select path="<%=Constant.DAY_OF_BIRTH%>" required="required" class="dobDay">
		<form:option value="">Day</form:option>
		<c:forEach items="${days}" var="day">
			<form:option value="${dayIndex}">${day}</form:option>
			<c:set var="dayIndex" value="${dayIndex+1}"/>
		</c:forEach>
	</form:select>
	<form:input path="<%=Constant.YEAR_OF_BIRTH%>" size="4" id="yearOfBirth" maxlength="4"  pattern="[0-9]{4}" required="required" class="dobYear"/>
	<form:errors class="errorMessage" path="<%=Constant.DATE_OF_BIRTH %>"/>
	</li>

  	<li>
  	
	<c:set var="GENDER_MALE" value="<%=Gender.Male%>" />
	<c:set var="GENDER_FEMALE" value="<%=Gender.Female%>" />
	<c:set var="GENDER_PREFER_NOT_TO_ANSWER" value="<%=Gender.PreferNotToAnswer%>" />

	
	<label for="gender">Gender</label>
	
	<c:forEach items="${genders}" var="gender">
		<c:if test='${gender.code.equalsIgnoreCase(GENDER_MALE)}'>
		<form:radiobutton path="<%=Constant.GENDER %>" value="${gender.code}" id="male" name="rr" class="radiocheck"/>
		
		${gender.description}
		</c:if>
		<c:if test='${gender.code.equalsIgnoreCase(GENDER_FEMALE)}' >
		<form:radiobutton path="<%=Constant.GENDER %>" value="${gender.code}" id="fmale" name="rr" class="radiocheck"/>${gender.description}

		</c:if>
		<c:if test='${gender.code.equalsIgnoreCase(GENDER_PREFER_NOT_TO_ANSWER)}'>
		<form:radiobutton path="<%=Constant.GENDER %>" value="${gender.code}"/>${gender.description}
		</c:if>
	</c:forEach>
	
	<form:errors  class="errorMessage" path="<%=Constant.GENDER %>"/>

	


	</li>
	
	<div class="clear"></div>
	<li>
	<label for="emailAddress">Email</label>
	<form:input path="tcaProfile.adminUser.email" size="30" id="emailAddress" required="required" />
	<form:errors path="tcaProfile.adminUser.email"/>
	</li>
	<li>
												<label>State / Agency ID</label>
												<form:input path="<%=Constant.IDENTIFICATION_TEXT%>"/>
												
											</li>
	<div style="clear:both;"></div>
	<br /><h2>Phone *</h2><br />
	<li>
	<label for="primaryPhoneCountryCode">Country Code</label>
	<form:select path="<%=Constant.PHONE_COUNTRY_CODE%>" id="primaryPhoneCountryCode" required="required" >
				<form:option value="">Select</form:option>
				<c:forEach items="${countries}" var="country">
					<form:option value="${country.abbreviation}">${country.isdCode} - ${country.name}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="<%=Constant.PHONE_COUNTRY_CODE%>" cssClass="error" />
	</li>
<div class="clear"></div>
	<li>
	<label for="primaryPhoneNumber">Phone</label>
	<form:input path="<%=Constant.PHONE_NUMBER%>" size="30" id="primaryPhoneNumber" required="required" />
	<form:errors path="<%=Constant.PHONE_NUMBER%>"/>
	</li>
<div class="clear"></div>
	<li>
	<label for="primaryPhoneExtension">Extension</label>
	<form:input path="<%=Constant.PHONE_EXTENSION%>" size="30" id="primaryPhoneExtension" />
	<form:errors path="<%=Constant.PHONE_EXTENSION%>"/>
	</li>
<div class="clear"></div>
	<li>
	<c:set var="PHONE_TYPE_LANDLINE" value="<%=PhoneType.Landline%>" />
	<c:set var="PHONE_TYPE_MOBILE" value="<%=PhoneType.Mobile%>" />
	<label for="primaryPhoneType">Type</label>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_LANDLINE)}'>
		<form:radiobutton path="<%=Constant.PHONE_TYPE %>" value="${phoneType.code}" id="landline" class="radiocheck" name="pp"/>
		${phoneType.description}
		</c:if>
	</c:forEach>
	<c:forEach items="${phoneTypes}" var="phoneType">
		<c:if test='${phoneType.code.equalsIgnoreCase(PHONE_TYPE_MOBILE)}'>
		<form:radiobutton path="<%=Constant.PHONE_TYPE %>" value="${phoneType.code}" id="mobile" class="radiocheck" name="pp"/>
		${phoneType.description}
		</c:if>
	</c:forEach>
	
	<form:errors class="errorMessage" path="<%=Constant.PHONE_TYPE %>"/>
	
	</li>
<div class="clear"></div>
 </div>

	<div class="clear"></div>

	</ul>
			<form:hidden path="tcaProfile.adminUser.id"/>
	</div>

</div>
						</div> <!--  Form Canvas ends here -->


				<div class="formButtonsContainer">
					<div class="row-fluid">
						<c:url value="/secure/tca/profile/" var="backUrl" />
						<div class="ereg_span_01">
							<a class="submit" href="<c:out value='${backUrl}'/>">Back</a> <span
								class="right" style="margin: -6px 0 0 0;"><button
									class="submit" type="submit">Update</button></span>
						</div>
					</div>

				</div>
			</form:form>
</t:base>