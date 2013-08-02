<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.booking.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="validationErrors">

<c:choose>
	<c:when test="${errors.size()>1}" > 
		<ul>
		<c:forEach items="${errors}" var="error">
		<li>
		${errors.size()}
		<spring:message code="${error}" />
		</li>
		</c:forEach>
		</ul>
	</c:when>	
	<c:otherwise>
		<spring:message code="${errors[0]}" />
	</c:otherwise>							
 </c:choose>
</div>