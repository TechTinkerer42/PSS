
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>jQuery UI Accordion - Default functionality</title>
<!-- Jquery UI -->
<script type="text/javascript" src="<c:url value="/resources/jquery-ui-1.10.3/themes/base/jquery-ui.css"/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/jquery-1.9.1.js'/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/ui/jquery-ui.js'/>"></script>

<script>
	$(document)
			.ready(
					function() {


					});
	
</script>

<style>
.center {
	margin-left: auto;
	margin-right: auto;
	width: 70%;
}

.right {
	position: absolute;
	top: 20px;
	right: 0px;
	width: 150px;
}

div.one
{	
	width:200px;
	height:40px;
	border-style:solid;	
 	border-width:1px;
	border-radius: 4px;
	background-color:#f0f0f0;
	margin-left: auto;
	margin-right: auto;	
	text-align:center;
}

div.two
{
	width:200px;
	height:200px;
	border-radius: 4px;
	border-style:solid;	
	border-width:1px;
	margin-bottom:25px;
}

div.three
{	
	width:700px;
	height:40px;
	border-style:solid;
/*   	border-top-style:none;
	border-right-style:solid;
	border-bottom-style:none;
	border-left-style:solid; 
 */ 	
 	border-width:1px;
	border-radius: 4px;
	background-color:#f0f0f0;
	margin-left: auto;
	margin-right: auto;	
	text-align:left;
}

div.four
{
	width:700px;	
	border-radius: 4px;
	border-style:solid;	
	border-width:1px;
	margin-bottom:25px;	
	
}

div.five
{
	padding:10px 10px 10px 10px;
	
}

table
{ 
 border-collapse:collapse;
}
table,th, td
{
border: 1px solid black;
padding:5px 5px 5px 5px;
}

</style>
</head>
<body>

	<div id="pssHomeContainer" class="center">
		<div id="pssTitle" style="font-weight:bold;color:#606060;"><h2><a href="<c:url value="/pss/task/myPage"/>">Portfolio System</a></h2></div>
	</div>
</body>

</html>
