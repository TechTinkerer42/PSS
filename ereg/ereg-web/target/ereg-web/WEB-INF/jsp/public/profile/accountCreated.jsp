<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Create Profile - Account Created">
<div class="headContainer ">

<p></p>
<h1><spring:message code="accountCreated"/></h1>
<p><spring:message code="accountCreatedDetail"/><br><br>

Next Step:<br><br>


<b>.Prepare for the test</b><br>
Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. <br><br>


<b>.Schedule your test</b><br>
Lorem ipsum dolor sit amet, <a href="?">find the nearest test center</a> to schedule an appointment.



</p>



<a href="/ereg-web/secure/home" class="submit"><spring:message code="goToMyHomePage"/></a>

</div>


</t:base>