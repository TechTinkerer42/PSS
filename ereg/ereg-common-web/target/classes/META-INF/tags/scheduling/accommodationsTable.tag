<%@ tag language="java" pageEncoding="ISO-8859-1" description="Accommodations Table"%>

<%@ attribute name="accommodations" required="true" type="java.util.List"%>
<%@ attribute name="deliveryMode" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<table id="accommodationTable">
		<thead>
			<tr>
				<th>${deliveryMode} Accommodation</th>
				<th>Expiration Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${accommodations}" var="accommodation" varStatus="status">
				<tr>
					<td id="accommodationDesc${status.index}">
						${accommodation.accommodationType.description}
						<c:if test="${null != accommodation.accommodationTypeValue}">
							<c:out value=" - ${accommodation.accommodationTypeValue.label}" />
						</c:if> 
						<c:if test="${null != accommodation.otherAccommodationTypeText}">
							<c:out value=" - ${accommodation.otherAccommodationTypeText}" />
						</c:if>
					</td>
					<td>
						<ct:dateTime part="date" value="${accommodation.expiryDate}" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>