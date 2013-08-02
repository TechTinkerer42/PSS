<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="Order Search">
	<link
		href='<spring:url htmlEscape="true" value="/commonweb/css/jquery-ui.css"/>'
		rel="stylesheet">
	<script
		src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-ui.js"/>'></script>
	<script
		src='<spring:url htmlEscape="true" value="/commonweb/js/vieworder.js"/>'></script>

	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>View Orders</h1>
			</div>
		</div>
	</div>
	<form:form method="post" modelAttribute="viewOrderSearchForm"
		action="search" id="searchForm" onsubmit="return false">
		<c:choose>
			<c:when test="${orderHistory eq 'N'}">
				<p>
					<label><spring:message code="view.order.history"
							arguments="HiSET" /></label>
				</p>

			</c:when>
			<c:otherwise>

				<div class="create_form">
					<div class="clear"></div>
					<label for="viewOrder">&nbsp;&nbsp;&nbsp;<spring:message
							code="view.order.label" />:
					</label>
					<form:select path="viewOrder" class="viewOrder"
						onchange="javascript:loadSearchResults();">
						<form:option value="">Select</form:option>
						<c:forEach var="entry" items="${viewOrders}">
							<form:option value="${entry.key}">${entry.value}</form:option>
						</c:forEach>
					</form:select>
					<div class="clear"></div>

					<form:input type="hidden" path="pageNo" id="pageNo" />
					<form:input type="hidden" path="action" id="action" />
					<form:input type="hidden" path="rowperPage" id="rowperPage" />
					<form:input type="hidden" path="customerId" id="customerId"
						value="${customerIdStr}" />

					<div class="clear"></div>
				</div>
			</c:otherwise>
		</c:choose>


		<div id="searchResultsTable_length" class="viewOrderTable">

			<div id="searchFormResultsTable" style="border: none;"></div>
		</div>
		</br></br>
		
			<c:choose>
			<c:when test="${displayHomeButton eq 'true'}">
		
				<a href="<c:url value='/secure/home' />"
						class="submit"><spring:message code="goToMyHomePage" /></a>
			</c:when>
			<c:otherwise>
		
				<a class="othbutton" href="<ct:encode out="/secure/testtaker/view/?customerId=${customerIdStr}" />">
								Go to Test Taker's Summary</a>
			
						
			
			</c:otherwise>
		</c:choose>
			
	
	</form:form>
</t:base>