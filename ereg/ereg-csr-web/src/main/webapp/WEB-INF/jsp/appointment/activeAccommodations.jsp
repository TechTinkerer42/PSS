<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<c:if test="${appointmentForm.appointments[index].booking.bookingAccommodations.size()>0}">
	<table id="accommodationTable" border="1px">
		<thead>
			<tr>
				<th>${deliveryMode} Accommodation</th>
				<th>Expiration Date</th>					
			</tr>
		</thead>
		<c:forEach  var="accommodation" items="${appointmentForm.appointments[index].booking.bookingAccommodations}" varStatus="status">
			<tr>
				<td id="accommodationDesc${status.index}">${accommodation.accommodationType.description}
					<c:if test="${null != accommodation.accommodationTypeValue}">
						<c:out value=" - ${accommodation.accommodationTypeValue.label}" />
					</c:if>
					<c:if test="${null != accommodation.othrAcmdtnTypTxt}">
						<c:out value=" - ${accommodation.othrAcmdtnTypTxt}" />
					</c:if>
					
				</td>
				<td><ct:dateTime part="date" value="${accommodation.expirationDate}" /></td>
			</tr>
		</c:forEach>
	</table>
</c:if>