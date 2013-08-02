<%@ tag language="java" pageEncoding="ISO-8859-1" description="Appointment Information Table"%>

<%@ attribute name="bookings" required="true" type="java.util.List"%>
<%@ attribute name="mode" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<table>
	<thead>
		<tr>
			<th>Appointment Number</th>
			<th>Test Title</th>
			<c:choose>
			<c:when test="${mode == 'CSR'}">
				<th>Date</th>
				<th>Test Center</th>
				<th>Time</th>
				<th>Test Type</th>
				<th>Test Form &amp; Language</th>
				<th>Accommodation</th>
				<th>Comments</th>
			</c:when>
			<c:when test="${mode == 'SELF'}">
				<th>Test Center</th>
				<th>Date and Time</th>
				<th>Test Type</th>
				<th>Test Language</th>
			</c:when>
			</c:choose>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${bookings}" var="booking" varStatus="status">
			<tr>
				<td>
					<c:choose>
						<c:when test="${mode == 'CSR'}">
							<a href="<ct:encode out="/secure/tcaScheduling/view/?bookingId=${booking.id}" />">
								<ct:format out="${booking.etsApptID}" />
					 		</a> 
						</c:when>
						<c:when test="${mode == 'SELF'}">
							<a href="<ct:encode out="/secure/appointment/view/?bookingId=${booking.id}" />">
								<ct:format out="${booking.etsApptID}" />
					 		</a>
					 	</c:when>
					 </c:choose> 
			 	</td>
				<td><c:out value="${booking.testVariation.test.testName}" /></td>
				<c:choose>
				<c:when test="${mode == 'CSR'}">
					<td>
						<ct:dateTime part="date" value="${booking.appointmentDateTime}" />
					</td>
					<td><c:out value="${booking.testCenter.name}" /></td>
					<td>
						<ct:dateTime part="time" value="${booking.appointmentDateTime}" />
					</td>
					<td><c:out value="${booking.testVariation.deliveryModeType.description}" /></td>
					<td>
						<c:if test="${booking.form != null}"><c:out value="${booking.form.formDesc}" /></c:if>
						<c:if test="${booking.form == null}"><c:out value="${booking.testVariation.languageType.description}" /></c:if>
					</td>
					<td><c:choose>
						<c:when test="${booking.bookingAccommodations.size() >0}">Yes</c:when>
						<c:otherwise>No</c:otherwise>
					</c:choose></td>
					<td><c:out value="${booking.comments}" /></td>
				</c:when>
				<c:when test="${mode == 'SELF'}">
					<td>${booking.testCenter.name}</td>
					<td>
						<ct:dateTime part="dateTime" value="${booking.appointmentDateTime}" />
					</td>
					<td><c:out value="${booking.testVariation.deliveryModeType.description}" /></td>
					<td>${booking.testVariation.languageType.description}</td>
				</c:when>
				</c:choose>
			</tr>
		</c:forEach>
	</tbody>
</table>