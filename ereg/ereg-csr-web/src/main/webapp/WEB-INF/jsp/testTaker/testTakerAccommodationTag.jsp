<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ page import="org.ets.ereg.common.web.form.CustomerAccommodationsForm" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url value="${action}" var="action_url"/>
<form:form method="get" modelAttribute="viewAccommodationForm" action="${action_url}">
<div class="formContainer">

<label for="testTitle"><spring:message code="accommodations.view.filter.label.TestTitle"/></label>
	<form:select path="<%=CustomerAccommodationsForm.TEST_ID%>" >
		<form:option value=""><spring:message code="accommodations.view.testtitle.allTests"/></form:option>
		<c:forEach items="${tests}" var="test">
			<form:option value="${test.testId}">${test.testName}</form:option>
		</c:forEach>
	</form:select>
<label for="testTitle"><spring:message code="accommodations.view.filter.label.deliveryMode"/></label>
	<form:select path="<%=CustomerAccommodationsForm.DELIVERY_MODE%>" >
		<form:option value=""><spring:message code="accommodations.view.allDeliveryMode"/></form:option>
		<c:forEach items="${testTypes}" var="testType">
			<form:option value="${testType.code}">${testType.code}</form:option>
		</c:forEach>
	</form:select>

<label for="testTitle"><spring:message code="accommodations.view.filter.label.accommodation.status"/></label>
	<form:select path="<%=CustomerAccommodationsForm.ACCOMMODATION_STATUS%>" >
		<form:option value=""><spring:message code="accommodations.view.allStatus"/></form:option>
		<c:forEach items="${accommodationStatus}" var="status">
			<form:option value="${status.code}">${status.code}</form:option>
		</c:forEach>
	</form:select>
	

 <input  name="customerId" type="hidden" value="${customerId}"/> 
	
	<button class="submit" type="submit"><spring:message code="accommodations.view.button.filter"/></button>
	
	
					<c:if test="${empty accommodations}">
						<spring:message code="testtaker.accommodation.message"/>
					</c:if>
					<c:if test="${not empty accommodations}"> 			
						<table border="1px">
							<thead>
								<tr>
									<th>Test Title</th>
									<th>Test Type</th>
									<th>Accommodations</th>
									<th>Last Modified</th>
									<th>Expiration Date</th>
									<th>Status</th>
									
									<sec:authorize access="hasRole('ROLE_SSD_CSR')">
									<th>Actions</th>
									</sec:authorize>
									
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${accommodations}" var="title" varStatus="i" >
							<tr>
								<td rowspan="${title.testAccommodationCount}">${title.testTitle}</td>
								<c:forEach items="${title.customerDeliveryMethodAccommodations}" var="delivery" varStatus="j" >
									<td rowspan="${delivery.accommodationCount }">${delivery.deliveryMethod}</td>
									<c:forEach items="${delivery.customerAccommodations}" var="accmd" varStatus="k" >
										<td >${accmd.accommodation.description}</td>
										<td >${accmd.approvalDate}</td>
										<td >${accmd.expirationDate}</td>
										<td >ACTIVE</td>
										<sec:authorize access="hasRole('ROLE_SSD_CSR')">
										<td ><a class="modalWindow" href="abc?testCode=${accmd.accommodation.description}">edit</a> <a href="">remove</a></td>
										</sec:authorize>
									</tr>	
									</c:forEach>							
					
								</c:forEach>
							
							
							
							</c:forEach>
							</tbody>
							</table>
							</c:if>
	
	
</form:form>
</div>

					