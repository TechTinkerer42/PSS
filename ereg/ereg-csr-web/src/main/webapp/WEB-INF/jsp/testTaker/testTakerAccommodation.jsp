<%@ page import="org.ets.ereg.common.web.scheduling.form.AppointmentForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Accommodation)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>
					Test Taker Summary - <c:out value="${firstName} ${lastName}" />
				</h1>
			</div>
		</div>
		<br>
		<h2>Accommodations</h2>
		<span class="required_notification"><span style="color: red">* </span>Required Information</span>
	</div>
	<!-- Form Canvas starts here -->
	<div class="formContainer">
		<table>
			<thead>
				<tr>
					<th>Test Title</th>
					<th>Accommodations</th>
					<th>Expiration Date</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${activeAccommodations}" var="accommodation" varStatus="status" >
					<tr>
						<td>
							<c:out value="${accommodation.test.testName}" />
						</td>
						<td>
							<c:out value="${accommodation.accommodationType.description}" />
							<c:if test="${null != accommodation.accommodationTypeValue}">
								<c:out value=" - ${accommodation.accommodationTypeValue.value}" />
							</c:if>
						</td>
						<td>
							<ct:dateTime part="date" value="${accommodation.expiryDate}" />
						</td>
						<td>
							<a href="<spring:url value="/secure/testtaker/accommodation/remove?customerId=${customerId}&programCode=${accommodation.test.programType.code}&testId=${accommodation.test.testId}&accommodationTypeCode=${accommodation.accommodationType.code}" />">Remove</a>
							<%-- <a href="<ct:encode out="/secure/testtaker/accommodation/remove?customerId=${customerId}&programCode=${accommodation.test.programType.code}&testCode=${accommodation.test.testId}&accommodationTypeCode=${accommodation.accommodationType.code}" />">Remove</a> --%>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</t:base>