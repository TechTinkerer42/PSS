<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Test Center - Detail">


<div class="headContainer ">
<div class="row">
<div class="span9"><h1>${testCenter.name}</h1></div>
</div>
</div>


<div class="formContainer">
<div class="create_form">
<c:set var="address" value=""/>
<c:if test="${null !=  testCenter.organizationAddresses && fn:length(testCenter.organizationAddresses) > 0}">
<!-- <ul>
<li><label></label></li>

</ul>
 -->


<table class="resultsTable">

<tr>
<td><spring:message code="address"/></td>
<td>
<c:forEach items="${testCenter.organizationAddresses}" var="organizationAddress" begin="0" end="0">
${organizationAddress.address.addressLine1}
<c:set var="address" value="${organizationAddress.address.addressLine1}"/>
<c:if test="${null != organizationAddress.address.addressLine2 && fn:length(organizationAddress.address.addressLine2) > 0}">
<br>${organizationAddress.address.addressLine2}
<c:set var="address" value="${address },${organizationAddress.address.addressLine2}"/>
</c:if>
<c:if test="${null != organizationAddress.address.addressLine3 && fn:length(organizationAddress.address.addressLine3) > 0}">
<br>${organizationAddress.address.addressLine3}
<c:set var="address" value="${address },${organizationAddress.address.addressLine3}"/>
</c:if>
<br>${organizationAddress.address.city}, ${organizationAddress.address.state.abbreviation} ${organizationAddress.address.postalCode}
<br>${organizationAddress.address.country.abbreviation}
<c:set var="address" value="${address },${organizationAddress.address.city}, ${organizationAddress.address.state.abbreviation} ${organizationAddress.address.postalCode} ${organizationAddress.address.country.abbreviation}"/>
</c:forEach>
</td>
</tr>
</c:if>
<c:if test="${null !=  testCenter.organizationPhones && fn:length(testCenter.organizationPhones) > 0}">
<tr>
<td><spring:message code="phoneNumber"/></td>
<td>
<c:forEach items="${testCenter.organizationPhones}" var="organizationPhone" begin="0" end="0">
<c:if test="${fn:length(organizationPhone.phone.phoneNumber) == 10}">
(${fn:substring(organizationPhone.phone.phoneNumber,0, 3)})&nbsp;${fn:substring(organizationPhone.phone.phoneNumber,3, 6)}-${fn:substring(organizationPhone.phone.phoneNumber,6, 10)}
</c:if>
<c:if test="${null != organizationPhone.phone.phoneExtension && fn:length(organizationPhone.phone.phoneExtension) > 0}">
&nbsp;x${organizationPhone.phone.phoneExtension}
</c:if>
</c:forEach>
</td>
</tr>
</c:if>
<c:if test="${null !=  testCenter.organizationAddresses && fn:length(testCenter.organizationAddresses) > 0}">
<c:forEach items="${testCenter.organizationAddresses}" var="organizationAddress" begin="0" end="0">
<c:if test="${null != organizationAddress.address.emailAddress && fn:length(organizationAddress.address.emailAddress) > 0}">
<tr>
<td><spring:message code="emailAddress"/>Email</td>
<td>
<a href="<c:out value='mailto:${organizationAddress.address.emailAddress}'/>">${organizationAddress.address.emailAddress}</a>
</td>
</tr>
</c:if>
</c:forEach>
</c:if>

<c:if test="${null != testCenter.webUrl && fn:length(testCenter.webUrl) > 0}">
<tr>
<td><spring:message code="website"/></td>
<td>
<a href="<c:out value='http://${testCenter.webUrl}'/>" target="${testCenter.name}">${testCenter.webUrl}</a>
</td>
</tr>
</c:if>
<c:if test="${null != testCenter.officeHourText && fn:length(testCenter.officeHourText) > 0}">
<tr>
<td><spring:message code="hoursOfOperation"/></td>
<td>
<c:out value="${testCenter.officeHourText}"/>
</td>
</tr>
</c:if>
<tr>
<td><spring:message code="testType"/></td>
<td>
<c:if test="${null != testCenter.testCenterDeliveryModes && fn:length(testCenter.testCenterDeliveryModes) > 0}">
<c:forEach items="${testCenter.testCenterDeliveryModes}" var="testCenterDeliveryMode" begin="0" end="0">
${testCenterDeliveryMode.deliveryMode.description}
</c:forEach>
<c:forEach items="${testCenter.testCenterDeliveryModes}" var="testCenterDeliveryMode" begin="1">
,&nbsp;${testCenterDeliveryMode.deliveryMode.description}
</c:forEach>
</c:if>
<c:if test="${null != testCenter.testCenterDeliveryModes && fn:length(testCenter.testCenterDeliveryModes) == 0}">
<spring:message code="unknown"/>
</c:if>
</td>
</tr>

<c:if test="${not empty distance}">
<tr>
<td><spring:message code="distance"/></td>
<td>
<fmt:formatNumber type="number" maxFractionDigits="1" value="${distance}" /> <spring:message code="miles"/>
</td>
</tr>
</c:if>

<c:if test="${null !=  testCenter.organizationAddresses && fn:length(testCenter.organizationAddresses) > 0}">
<tr>
<td><spring:message code="mapAndDirections"/></td>
<td>
<c:if test="${null != testCenter.specialDrivingDirection && fn:length(testCenter.specialDrivingDirection) > 0}">
<c:out value="${testCenter.specialDrivingDirection}"/>

</c:if>
<c:if test="${null != testCenter.specialOnSiteDirection && fn:length(testCenter.specialOnSiteDirection) > 0}">
<c:out value="${testCenter.specialOnSiteDirection}"/>

</c:if>

<a href="<c:out value='http://maps.google.com/maps?daddr=\'${address}\''/>" target="_blank"><spring:message code="mapAndDirections"/></a>
</td>
</tr>
</c:if>
<c:if test="${testCenter.accessRestricted}">
<tr>
<td><spring:message code="restricted"/></td>
<td>
<spring:message code="yes"/>
<c:if test="${null != testCenter.restrictedAccessReason}">
,&nbsp;${testCenter.restrictedAccessReason.description}
</c:if>
</td>
</tr>
</c:if>
</table>

</div></div>

<c:if test="${not empty distance}">
<div class="bottomContainer">

<a class="submit" onclick="window.history.back();"><spring:message code="backToSearchResults"/></a>
</div>
</c:if>
</t:base>