<%@tag description="The base template page for eReg Web" %>
<%@attribute name="title" required="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	
	
	<script src='<spring:url value="/commonweb/js/jquery-1.8.3.min.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/selectivizr-min.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/jquery.validate.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/jquery-ui.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/validation.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/bootstrap.min.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/misc.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/biq.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/jquery.h5validate.js" htmlEscape="true"/>'></script>
	<link href='<spring:url value="/commonweb/css/bootstrap.css" htmlEscape="true"/>' rel="stylesheet" media="screen">
	
	<link href='<spring:url value="/commonweb/css/bootstrap-responsive.css" htmlEscape="true"/>' rel="stylesheet" media="screen, print">
	<link href='<spring:url value="/commonweb/css/jquery-ui.css" htmlEscape="true"/>' rel="stylesheet" media="screen, print">
	<link href='<spring:url value="/commonweb/css/style.css" htmlEscape="true"/>' rel="stylesheet" media="screen, print">
	<link href='<spring:url value="/commonweb/css/form.css" htmlEscape="true"/>' rel="stylesheet" media="screen, print">
	<link href='<spring:url value="/commonweb/css/print.css" htmlEscape="true"/>' rel="stylesheet" media="print">
	<link href='<spring:url value="/commonweb/css/orderdetails.css" htmlEscape="true"/>' rel="stylesheet" media="screen, print">
	<link href='<spring:url value="/resources/css/pssscreen.css" htmlEscape="true"/>' rel="stylesheet" media="screen, print"> 
	
		<title>${title} </title>
</head>


<body class="hset-org ${globalContextCustomer.currentProgramCode}">
	
		
		 <div id="wrapper-center">
			<div id="wrapper-content"> 
			
			<!-- <div class="container">

		<div class="profileFormContainer"> -->
		<!-- header part -->
		<t:header/>



		<div class="center">
			<jsp:doBody/>
		</div>
		<!-- footer part -->
		<t:footer/>

		</div>
	</div>
</body>

</html>