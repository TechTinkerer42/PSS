<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-1.8.3.min.js"/>'></script>
<script
	src='<spring:url htmlEscape="true" value="/commonweb/js/misc.js"/>'></script>
<script>
	var pathURL = window.location.pathname.split('/');
	function loadInstructionText() {
		$.ajax({
			type : 'post',
			//url : "/ereg-web/secure/orderDetails/loadInstructions",			
			url : window.location.protocol + "//" + window.location.host + "/"
					+ pathURL[1] + "/secure/orderdetails/loadinstructions",
			data : "testtype=" + "${booking.deliveryModeType.description}",
			dataType : 'text',
			async : true,
			success : function(data) {
				$("#instructionId").html(data);
				var dataInstructions = $("#instructionId").html();
			},
			error : function(e) {
				//alert('Error: ' + e);
			}
		});

	}
	$(document).ready(function() {
		loadInstructionText();
	});
</script>
<title>Appointment Confirmation</title>

<t:base title="Appointment Details">
	<div>
		<h1><span style="margin:0 28px;">Appointment Confirmation </span><p style="float:right; font-size:12px;">
			<input type="button" onClick="window.print()" value="Print" />
		</p></h1>	
	</div>
	<div class="appointConfirmationContainer">

		<h3>Personal Information</h3>
		<ct:personalInfoTable profile="${profile}" mode="${mode}" />

	</div>
	<script>
		
	</script>
	<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Appointment
		Information</h3>

	<%-- <ct:appointmentInfoDetails booking="${booking}" mode="${mode}"  displayAppointmentConfirmation = "true" displayTestCenterAddress="true" displayTestPrice = "true" testprice="0.01" /> --%>
	<ct:appointmentInfoDetails booking="${booking}" mode="${mode}"
		customerIdStr="${customerIdStr}"
		displayAppointmentConfirmation="false" displayTestCenterAddress="true"
		displayTestPrice="false" />

	<p class="text">
	<div class="instructiontext appointmentdetailstextborderradius" style="padding:0px"
		id="instructionId"></div>
	<br>

	<a class="submit noprint" style="margin: 5px 20px;" href="#"
		onclick="history.go(-1);return false"><spring:message
			code="button.label.back" /></a>
	<br>
	<div class="space-line"></div>
	<br>


</t:base>