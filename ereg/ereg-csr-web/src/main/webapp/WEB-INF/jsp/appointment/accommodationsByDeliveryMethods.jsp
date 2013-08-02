<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<c:out value="${accommodationsByDeliverMethod.size()}"/>
<c:if test="${accommodationsByDeliverMethod.size()>0}">
	<c:forEach var="accommodationByDeliverMethod" items="${accommodationsByDeliverMethod}" varStatus="keyStatus">
		<table>
			<thead>
				<tr><th colspan="2">${accommodationByDeliverMethod.key} Accommodations</th></tr>
				<tr><th>Description</th><!-- <th>Expiration Date</th> --></tr>
			</thead>
			<c:forEach  var="accommodation" items="${accommodationByDeliverMethod.value}" varStatus="status">
					<tr><td>${accommodation.description}</td>
					<%--<td> <ct:dateTime part="date" value="${accommodation.expiryDate}" /> </td>--%></tr>
			</c:forEach>
		</table>
		<c:if test="${!keyStatus.last}">
			<br>
		</c:if>
	</c:forEach>	
</c:if>