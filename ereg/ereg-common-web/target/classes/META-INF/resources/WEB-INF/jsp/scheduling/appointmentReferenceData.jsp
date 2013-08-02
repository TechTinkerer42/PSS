<%@ page language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	
<div id="testType">
	<c:forEach items="${deliveryModes}" var="deliveryMode" varStatus="status">
		<input type="radio" id="deliveryMode${status.index}" name="deliveryMode" value="${deliveryMode.code}" class="radiocheck" /><span>${deliveryMode.description}</span> 
	</c:forEach>
</div>
<div id="testLanguage">
	<c:forEach items="${languages}" var="language">
		<form:radiobutton path="appointmentForm.appointments[${index}].booking.testVariation.languageType" value="${language.code}" class="radiocheck"/><span>${language.description}</span>
	</c:forEach>
</div>