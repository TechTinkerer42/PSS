<%@tag description="test" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	
	
	<script src='<spring:url value="/js/jquery-1.8.3.min.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/js/selectivizr-min.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/js/jquery.validate.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/js/validation.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/js/bootstrap.min.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/js/misc.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/js/biq.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/js/jquery.h5validate.js" htmlEscape="true"/>'></script>
	<script src='<spring:url value="/commonweb/js/commontest.js" htmlEscape="true"/>'></script>
	<link href='<spring:url value="/css/bootstrap.css" htmlEscape="true"/>' rel="stylesheet" media="screen">
	
	<link href='<spring:url value="/css//bootstrap-responsive.css" htmlEscape="true"/>' rel="stylesheet" media="screen">
	<link href='<spring:url value="/css/style.css" htmlEscape="true"/>' rel="stylesheet" media="screen">
	<link href='<spring:url value="/css/form.css" htmlEscape="true"/>' rel="stylesheet" media="screen">
	<link href='<spring:url value="/commonweb/css/commontest.css" htmlEscape="true"/>' rel="stylesheet" media="screen">
	<title>${title} </title>
</head>


<body class="ets-org">
	<div class="container">

		<div class="profileFormContainer">
		
		<!-- header part -->
		<t:header/>



		<div class="center">
		<p>This is content in the test tag>>>></p>
			<jsp:doBody/>
		</div>
		<!-- footer part -->
		<t:footer/>

		</div>
	</div>
</body>

</html>