<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ attribute name="value" required="true" type="java.util.Date"%>
<%@ attribute name="part" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<c:choose>
	<c:when test="${part == 'date'}">
		<spring:message code="datePattern" var="pattern" />
	</c:when>
	<c:when test="${part == 'dateWithDayOfWeek'}">
		<spring:message code="dateWithDayOfWeekPattern" var="pattern" />
	</c:when>
	<c:when test="${part == 'time'}">
		<spring:message code="timePattern" var="pattern" />
	</c:when>
	<c:otherwise>
		<spring:message code="dateTimePattern" var="pattern" />
	</c:otherwise>
</c:choose>

<c:if test="${not empty value}">
	<fmt:formatDate pattern="${pattern}" value="${value}" />
</c:if>