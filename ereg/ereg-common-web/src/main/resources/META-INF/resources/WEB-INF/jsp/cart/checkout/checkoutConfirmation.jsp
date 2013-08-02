<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>


<t:base title="ETS-eREG(Cart-Checkout Confirmation)">
<div class="headContainer ">              
</div>


<!-- Form Canvas starts here -->
<div class="appointConfirmationContainer">
<h3>Appointment(s) Scheduled</h3>
<div class="appointmentAlert"><p>Your appointment(s) has been scheduled.An email confirmation will also be sent to your email address on file.</p></div>

<h4>Personal Information</h4>

 <ct:personalInfoTable profile="${profile}" mode="${mode}"/>

<h4>Appointment Information</h4>
 <ct:appointmentInfoTable bookings="${bookings}" mode="${mode}"/>  <br>
 
<c:url value="/secure/home" var="prev_url"/> 
<a class="submit" href="<c:out value='${prev_url}'/>">Go to My ${globalContextCustomer.currentProgramShortDescription} Home</a>
	
</div>
</t:base>