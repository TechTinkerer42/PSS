<%@tag description="Base template for eReg CSR Web" %>

<%@attribute name="title" required="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<!-- Start - METADATA -->
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- End - METADATA -->
		<!-- Start - ALL STYLESHEETS -->
		<link href='<spring:url htmlEscape="true" value="/commonweb/css/bootstrap.css"/>' rel="stylesheet" media="screen, print">
		<link href='<spring:url htmlEscape="true" value="/commonweb/css/bootstrap-responsive.css"/>' rel="stylesheet">
		<link href='<spring:url htmlEscape="true" value="/commonweb/css/jquery-ui.css"/>' rel="stylesheet">
		<link href='<spring:url htmlEscape="true" value="/css/ereg_csr_stylesheet.css"/>' rel="stylesheet" media="screen, print">
		<link href='<spring:url htmlEscape="true" value="/commonweb/css/style.css"/>' rel="stylesheet" media="screen, print">
		<link href='<spring:url htmlEscape="true" value="/commonweb/css/form.css"/>' rel="stylesheet" media="screen, print">
		<link href='<spring:url htmlEscape="true" value="/commonweb/css/calendarview.css"/>' rel="stylesheet">
        <link href='<spring:url htmlEscape="true" value="/commonweb/css/datepicker.css"/>' rel="stylesheet" type="text/css">
      	<link href='<spring:url value="/commonweb/css/print.css" htmlEscape="true"/>' rel="stylesheet" media="print">
		<link href='<spring:url value="/commonweb/css/orderdetails.css" htmlEscape="true"/>' rel="stylesheet" media="screen, print">
		
		<!-- End - ALL STYLESHEETS -->
		<!-- Start - ALL JAVASCRIPTS AND IT'S LIBRARIES -->
		<script src='<spring:url htmlEscape="true" value="/js/ereg_csr_js.js"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/commonweb/js/bootstrap.js"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/commonweb/js/bootstrap.min.js"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/js/jquery-1.8.3.js"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-1.8.3.min.js"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-ui.js"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/commonweb/js/jquery.validate.js"/>'></script>
		<script src='<spring:url value="/commonweb/js/biq.js" htmlEscape="true"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/commonweb/js/validation.js"/>'></script>
		<script type="text/javascript" src='<spring:url htmlEscape="true" value="/commonweb/js/datepicker.js"/>'></script>
		<script src='<spring:url htmlEscape="true" value="/commonweb/js/misc.js"/>'></script>
		<script src='<spring:url value="/commonweb/js/selectivizr-min.js" htmlEscape="true"/>'></script>
		<script src='<spring:url value="/commonweb/js/jquery.h5validate.js" htmlEscape="true"/>'></script>
		<title>${title}</title>
	</head>

	<body class="ets-org">
		<div class="container">
			<div class="profileFormContainer">
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