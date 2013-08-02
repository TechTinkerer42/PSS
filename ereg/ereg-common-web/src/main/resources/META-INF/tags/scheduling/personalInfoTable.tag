<%@ tag language="java" pageEncoding="ISO-8859-1" description="Personal Information Table"%>
<%@ attribute name="profile" required="true" type="org.ets.ereg.profile.vo.ProfileVO"%>
<%@ attribute name="mode" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<table id="personalInfo">
	<thead>
		<tr>
			<th>Last, First M.I.</th>
			<th>Date of Birth</th>
			<th>Gender</th>
			<th>Address</th>
			<th>Contact Information</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				<c:choose>
					<c:when test="${mode == 'CSR'}"><c:set var="viewProfileUrl" value="/secure/profile/viewProfile?profileId=${profile.customer.id}" /></c:when>
					<c:when test="${mode == 'SELF'}"><c:set var="viewProfileUrl" value="/secure/profile" /></c:when>
				</c:choose>
				<a href="<ct:encode out="${viewProfileUrl}" />">
					<c:out value="${profile.customer.lastName}, ${profile.customer.firstName}" />
					<c:if test="${profile.customer.middleInitial ne null}">
						<c:out value="${profile.customer.middleInitial}" />
					</c:if>
				</a>
			</td>
			<td>
				<ct:dateTime part="date" value="${profile.customer.dateOfBirth}" />
			</td>
			<td>
				<c:out value="${profile.customer.gender.description}" />
			</td>
			<td>
				<c:out value="${profile.address.addressLine1}" /><br />
				<c:if test="${profile.address.addressLine2 ne null}">
					<c:out value=" ${profile.address.addressLine2}" /><br />
				</c:if>
				<c:out value="${profile.address.city}" /><br />
				<c:out value="${profile.address.state.name}, ${profile.address.postalCode}" />
			</td>
			<td>
				<c:out value="Primary Phone: ${profile.primaryPhone.phoneNumber}" />
				<c:if test="${profile.primaryPhone.phoneExtension ne NULL}">
					<c:out value="ext. ${profile.primaryPhone.phoneExtension}" />
				</c:if>
				<br />
				<c:if test="${profile.alternatePhone.phoneNumber ne null}">
					<c:out value="Alternate Phone: ${profile.alternatePhone.phoneNumber}" />
					<c:if test="${profile.alternatePhone.phoneExtension ne NULL}">
						<c:out value="ext. ${profile.alternatePhone.phoneExtension}" />
					</c:if>
					<br />
				</c:if> 
				<c:out value="Email: ${profile.customer.emailAddress}" />
			</td>
		</tr>
	</tbody>
</table>