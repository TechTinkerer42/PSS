<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Generate password page</title>
</head>
<body>
	<p>Generate New Password</p>
	<form:form method="get" action="generateNewPassword">
		<table>

			<tr>
				<td><label for="username">User Name:</label></td>


				<td><input type="text" id="user-name" name="user_name"
					disabled="disabled" value="demoUser" /></td>
			</tr>
			<tr>
				<td><label for="password">Password: </label></td>
				<td><c:out value="${generatedPasswordString}" /></td>
			</tr>
		</table>
		<table>
			<tr>
				<td></td>
				<td><button type="submit" value="submit">Generate</button></td>
			</tr>
		</table>

	</form:form>
</body>
</html>