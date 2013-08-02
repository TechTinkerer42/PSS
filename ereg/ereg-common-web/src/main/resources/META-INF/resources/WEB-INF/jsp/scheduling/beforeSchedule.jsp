<%@ page import="org.ets.ereg.common.web.util.Constant" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="loggedInUserName" value="<%= org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName() %>"/>
<c:set var="customerUserName" value="<%= org.broadleafcommerce.profile.web.core.CustomerState.getCustomer().getUsername() %>"/>
<c:set var="anonymousUserName" value="<%= org.ets.ereg.common.web.util.Constant.ANONYMOUS_USER %>"/> 
<c:choose>

	<c:when test="${loggedInUserName == null|| loggedInUserName == anonymousUserName || loggedInUserName == customerUserName}">
		<c:set var="isCustomer" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="isCustomer" value="false"/>
	</c:otherwise>
	
</c:choose>
<t:base title="Before You Schedule">
<script>
	function submitForm() {
		
		if ($("#eligilibityRequirements").is(':checked') 
				<c:if test="${isCustomer == 'true'}">
					&& $("#testingPolicies").is(':checked')
				</c:if>) {
			$('#beforeScheduleForm').submit();
		} else {
			$('#errorSummary').css('display', 'block');
			$('#errorSummary').html("Please agree to the terms and conditions before continue.");
		}
	}
</script>
<style>
	h2{
		margin:0 0 0 21px !important;
	}
	.para {
		margin:0 0 0 21px !important;
	}
</style>
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1><spring:message code="appointment.before.schedule" arguments="HiSET" /></h1>
				<p><spring:message code="scheduling.before.message.part1" arguments="HiSET" /></p>
			</div>
		</div>
	</div>

	<form:form id="beforeScheduleForm" method="POST">
		
		<input type="hidden" name="skipProfile" value="<c:choose><c:when test="${skipProfile == 'true' }">true</c:when><c:otherwise>false</c:otherwise>	</c:choose>" />
			
	
	<div id="errorSummary" class="errorblock" style="display: none;"></div>
	<h2><spring:message code="scheduling.before.title.eligilibityRequirements" /></h2>
	<div class="formContainer">
		<spring:url value="" var="eligibilityRequirementsUrl" />
		<spring:url value="/public/testcenter/search/${globalContextCustomer.currentProgramCode}" var="findTestCenterUrl" />
		<p><spring:message code="scheduling.before.message.part2" arguments="${eligibilityRequirementsUrl},${findTestCenterUrl}" /></p>
		<p>
			<input type="checkbox" id="eligilibityRequirements">
			<b>
				<c:choose>
					<c:when test="${isCustomer == 'true'}">
						<spring:message code="scheduling.before.message.part3" />
					</c:when>
					<c:otherwise>
						<spring:message code="scheduling.before.message.part3.csr" arguments="HiSET" />
					</c:otherwise>
				</c:choose>
			</b>
		</p>
		
	</div>
	
	<c:if test="${isCustomer == 'true'}">
		<h2><spring:message code="scheduling.before.title.testingPolicies" arguments="HiSET" /></h2>
		<div class="formContainer">
			<spring:url value="TBD" var="informationBulletinUrl" />
			<c:set value="HiSET" var="programName" />
			<spring:url value="" var="termsAndConditionsUrl" />
			<spring:url value="" var="privacyAndSecurityPolicyUrl" />
			<p><spring:message code="scheduling.before.message.part4" arguments="${informationBulletinUrl},${programName},${termsAndConditionsUrl},${privacyAndSecurityPolicyUrl}" /></p>
			<p><input type="checkbox" id="testingPolicies"><spring:message code="scheduling.before.message.part5" arguments="HiSET" />
				</p>
		</div>
	</c:if>
	
	<h2><spring:message code="scheduling.before.title.disablitiesOrHealthRelatedNeeds" /></h2>
	<spring:url value="" var="testAccommodations" />
	<c:set value="XX" var="weeksBefore" />
	<div class="para">
		<p>
			<c:choose>
				<c:when test="${isCustomer == 'true'}">
					<spring:message code="scheduling.before.message.part6" arguments="${eligibilityRequirementsUrl},${weeksBefore}" />
				</c:when>
				<c:otherwise>
					<spring:message code="scheduling.before.message.part6.csr" arguments="${eligibilityRequirementsUrl},${weeksBefore}" />
				</c:otherwise>
			</c:choose>
		</p>
	</div>
	
	<div class="bottomContainer">
		<a class="submit" onclick="window.history.back();"><spring:message code="appointment.back" /></a>
		<a class="submit"><spring:message code="cancel" /></a>
		<span style="float: right;"><a id="submitForm" class="submit" onclick="submitForm();"><spring:message code="appointment.next" /></a></span>
	</div>
	</form:form>
</t:base>