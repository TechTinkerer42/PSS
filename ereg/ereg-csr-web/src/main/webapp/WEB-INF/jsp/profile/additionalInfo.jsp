<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Additional Info)">
	<script>
		$(function() {
			$("#profile\\.customer\\.militaryMemberShip1").click(function() {
				$("#militaryStatus").val("");
				$("#militaryStatus").change();
				$("#militaryStatus").removeAttr("disabled");
			});
			$("#militaryStatus").change(function() {
				$("#profile\\.customer\\.militaryStatus").val($(this).val());
			});
			if ($("#profile\\.customer\\.militaryStatus").val() != "") {
				$("#militaryStatus").val($("#profile\\.customer\\.militaryStatus").val());
			}
			$("#profile\\.customer\\.militaryMemberShip2").click(function() {
				$("#militaryStatus").val("NOTAM");
				$("#militaryStatus").change();
				$("#militaryStatus").attr("disabled", "disabled");
			});
			if ($("#profile\\.customer\\.militaryMemberShip2").is(":checked")) {
				$("#militaryStatus").val("NOTAM");
				$("#militaryStatus").change();
				$("#militaryStatus").attr("disabled", "disabled");
			}
		});
	</script>
	<div class="headContainer ">
		<div class="row">
			<div class="span9"><h1>Create Profile</h1></div>
		</div>
		<c:choose> 
			<c:when test="${eregUtils.isOAMAuthentication()}">
				<img src="${pageContext.request.contextPath}/commonweb/img/step2OAM.jpg">
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/commonweb/img/step2.jpg">
			</c:otherwise>
		</c:choose>
		<br>
		<h2>Additional Information</h2>
		<span class="required_notification">* Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="POST" modelAttribute="profileForm">
		<div class="formContainer">
			<div class="create_form">
				<div class="formRow">
					<form:errors path="*" cssClass="errorblock" element="div" />
					<ul>
						<li>
							<label>Ethnicity</label> 
							<form:select path="<%=ProfileForm.ETHNICITY%>" id="ethnicity">
								<form:option value="">Select</form:option>
								<c:forEach items="${ethnicityTypes}" var="ethnicityType">
									<form:option value="${ethnicityType.code}">${ethnicityType.description}</form:option>
								</c:forEach>
							</form:select> <form:errors path="<%=ProfileForm.ETHNICITY%>" cssClass="error" />
						</li>
						<li>
							<label>Preferred Language<br />for Test Taking</label> 
							<form:radiobutton path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING%>" value="EN" class="prefLang"/> 
							English 
							<form:radiobutton path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING%>" value="ES" class="prefLang"/> 
							Spanish 
							<form:errors path="<%=ProfileForm.PREFERRED_LANGUAGE_FOR_TEST_TAKING%>" cssClass="error" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Primary Speaking<br />Language</label> 
							<form:select path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE%>">
								<form:option value="">Select</form:option>
								<form:options items="${languageTypes}" itemValue="code" itemLabel="description" />
							</form:select> 
							<form:errors path="<%=ProfileForm.PRIMARY_SPEAKING_LANGUAGE%>" cssClass="error" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Military Member</label>
							<form:radiobutton path="<%=ProfileForm.IS_MILITARY_MEMBER%>" value="true" onclick="$('[id$=militaryStatus]').val('');" class="militarySts" />
							Yes 
							<form:radiobutton path="<%=ProfileForm.IS_MILITARY_MEMBER%>" value="false" onclick="$('[id$=militaryStatus]').val('NOTAM');" class="militarySts"/>
							No 
							<form:errors path="<%=ProfileForm.IS_MILITARY_MEMBER%>" cssClass="error" />
						</li>
						<div class="clear"></div>
						<li>
							<label>Military Status</label> 
							<select id="militaryStatus">
								<option value="">Select</option>
								<c:forEach items="${militaryStatusTypes}" var="militaryStatusType">
									<option value="${militaryStatusType.code}">${militaryStatusType.description}</option>
								</c:forEach>
							</select>
							<form:hidden path="<%=ProfileForm.MILITARY_STATUS%>"/>
							<form:errors path="<%=ProfileForm.MILITARY_STATUS%>" cssClass="error" />
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01"">
					<a class="submit" href="<c:url value='/secure/profile/'/>">Back</a>
					<span class="right" style="margin: -6px 0 0 0;"><button class="submit" type="submit">Next</button></span>
				</div>
			</div>
		</div>
	</form:form>
</t:base>				