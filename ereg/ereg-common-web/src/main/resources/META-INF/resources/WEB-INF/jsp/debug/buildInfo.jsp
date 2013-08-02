<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.util.jar.Manifest"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.jar.Attributes"%>
<%@page import="org.apache.commons.collections.MapUtils"%>
<%@page import="java.util.jar.Attributes.Name"%>
<%@page import="org.springframework.web.context.support.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Debug: Build Info</title>
<style type="text/css">
table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 1px;
	padding: 5px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.gridtable td {
	border-width: 1px;
	padding: 3px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}

table.linkstable {
	font-family: verdana, arial, sans-serif;
	font-size: 15px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.linkstable  th {
	border-width: 1px;
	padding: 5px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.linkstable  td {
	border-width: 1px;
	padding: 3px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
</head>
<body>
	<div>
		<p>
			<a href=<c:url value='/secure/debug' />>Back to debug home</a>
		</p>
		<h1>Build Information</h1>
		<table class="gridtable">
			<tr>
				<th>Name</th>
				<th>Value</th>
			</tr>
			<tr>
				<td>METAINF.MF Location</td>
				<td><%=getServletContext().getRealPath("/META-INF/MANIFEST.MF")%>
				</td>
			</tr>
			<%
				InputStream inputStream = getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
				Manifest manifest = new Manifest(inputStream);
				final Attributes attrs = manifest.getMainAttributes();
				for (Object name : attrs.keySet()) {
			%>
			<tr>
				<td><%=((Attributes.Name) name).toString()%></td>
				<td><%=attrs.getValue(((Attributes.Name) name).toString())%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</body>
</html>