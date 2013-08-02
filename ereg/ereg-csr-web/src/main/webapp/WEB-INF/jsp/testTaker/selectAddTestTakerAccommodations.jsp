<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="org.ets.ereg.common.web.form.CustomerAccommodationsForm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">


function addAccommodation(){	
	
	$( "#dialog-confirm" ).dialog( "close" );
	document.addPopupForm.submit();
	/* var form=$('form#addSubForm').serialize();
    var formAction = $("#addSubForm").attr("action");
    
	$.ajax({
	    url: formAction,
	    type: "post",
	    data: form,
	    success: function(data){
	    	
	    	var bookingId=data["bookingId"];
	    	var statusDesc=data["statusDesc"];
	    	$("#"+bookingId+"-status").text(statusDesc);
	    	
	    	$( "#dialog-confirm" ).dialog( "close" );
	    },
	    error:function(){
	        
	        $("#result").html('there is error while submit');
	    }   
	  });  */

	
}

</script>

</head>
<body>
<div  > Select Test Title and Type <br>
<c:url value="/secure/testtaker/accommodation/add" var="addAction"/> 
	<form:form name="addPopupForm" method="get" modelAttribute="viewAccommodationForm" action="${addAction}" id="addSubForm">
		<div class="formContainer">
		
			<label for="testTitle"><spring:message code="accommodations.view.filter.label.TestTitle"/></label>
				<%-- <form:select path="${testCode}" > --%>
				<form:select path="testId" >
					<%-- <form:option value=""><spring:message code="accommodations.view.testtitle.allTests"/></form:option> --%>
					<c:forEach items="${tests}" var="test">
						<form:option value="${test.testId}">${test.testName}</form:option>
					</c:forEach>
				</form:select>
			<label for="deliveryMode"><spring:message code="accommodations.view.filter.label.deliveryMode"/></label>
			
				<%-- <form:select path="${formDeliveryMode}" > --%>
				
				<form:select path="deliveryMode" >
					<%-- <form:option value=""><spring:message code="accommodations.view.allDeliveryMode"/></form:option> --%>
					<c:forEach items="${testTypes}" var="testType">
						<form:option value="${testType.code}">${testType.description}</form:option>
					</c:forEach>
				</form:select>		
				
				<input  name="customerId" type="hidden" value="${customerId}"/> 
		</div>
		
	</form:form>
	</div>
</body>
</html>