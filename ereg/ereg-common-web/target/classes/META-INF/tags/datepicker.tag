<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ attribute name="name" required="true" description="property name"%>
<%@ attribute name="minusDays" description="Restrict Minimum days"%>
<%@ attribute name="addMonths" description="Restrict Maximum months"%>
<%@ attribute name="addDays" description="Restrict maximum days"%>
<%@ attribute name="changeMonth" description="Show month drop down"%>
<%@ attribute name="changeYear" description="Show year drop down"%>
<%@ attribute name="numberOfMonths" description="Show Number of months"%>
<%@ attribute name="readonly" description="Text field read only"%>


<c:if test="${empty minusDays}" >
 <c:set var="minDate" value="minDate:null" />
</c:if>

<c:if test="${not empty minusDays}" >
 <c:set var="minDate" value="minDate:-${minusDays}" />
</c:if>

<c:if test="${empty addMonths && empty addDays}" >
 <c:set var="maxDate" value="maxDate:null" />
</c:if>

<c:if test="${not empty addDays && empty addMonths}" >
 <c:set var="maxDate" value="maxDate:'+${addDays}D'" />
</c:if>

<c:if test="${empty addDays && not empty addMonths}" >
 <c:set var="maxDate" value="maxDate:'+${addMonths}M'" />
</c:if>

<c:if test="${not empty addDays && not empty addMonths}" >
 <c:set var="maxDate" value="maxDate:'+${addMonths}M +${addDays}D'"/>
</c:if>


<c:if test="${empty changeMonth}" >
 <c:set var="changeMonth" value="false" />
</c:if>


<c:if test="${empty changeYear}" >
 <c:set var="changeYear" value="false" />
</c:if>

<c:if test="${empty numberOfMonths}" >
 <c:set var="numberOfMonths" value="null" />
</c:if>



<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
	$(function() {
	
		$("#${name}").datepicker(
		{
			${minDate},${maxDate},
			changeMonth: ${changeMonth},
			changeYear: ${changeYear},
			numberOfMonths: ${numberOfMonths}
		}
		);
		
	});
</script>

<p>
	<c:if test="${empty readonly}">
		<form:input path="${name}" id="${name}" />
	</c:if>
	<c:if test="${not empty readonly}" >
	<form:input path="${name}" id="${name}" readOnly="true" />
</c:if>
</p>
