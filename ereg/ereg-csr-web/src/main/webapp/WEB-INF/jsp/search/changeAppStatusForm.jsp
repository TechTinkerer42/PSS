<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.domain.interfaces.model.booking.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
var handleOkAlreadyCheckedinFlag = false;

function getEventOutComeStatusDescription(eventOutComeStatusCode) {
	switch (eventOutComeStatusCode)
    {
	case 'NCI': 
		return "Not Checked In";
    	break;
	case 'CHI': 
		return "Checked In";
	    break;
	case 'CNT': 
		return "Could Not Test";
	    break;	   
	case 'NOS': 
		return "No Show";
	    break;	 
    }
}

function changeStatus(){
	var radios = document.getElementsByName('eventOutComeStatusCd');
	for (var i = 0, length = radios.length; i < length; i++) {
	    if (radios[i].checked && radios[i].value=='CHI') {
	    	 $('#formSpan').show();
	    }else{
	    	 $('#formSpan').hide();
	    }
	}	
}

$('.ui-dialog-title').html("Change Status");
<c:if test="${formNotAvailableFlag || formNotAvailableBeforeDateFlag || formNotAvailableForCBTDeliveryFlag}">
$('.ui-dialog-title').html("Information");
</c:if>

function handleOkAlreadyCheckedin() {
	//Close the current popup and call handleOk
	$( "#dialog-confirm-already-checkedin").dialog( "close" );
	handleOkAlreadyCheckedinFlag=true;	
	handleOk();
}
/*
function handleOk(){
	$( "#dialog-confirm-message").dialog( "close" );
}
*/
function handleOk(){
	
	<c:if test="${formNotAvailableFlag || formNotAvailableBeforeDateFlag || formNotAvailableForCBTDeliveryFlag}">
	$( "#dialog-confirm" ).dialog( "close" );
	</c:if>
	
	<c:if test="${formAvailableFlag}">
	
	
	var eligFlag=$('#eligFlag').val();
	var radios = document.getElementsByName('eventOutComeStatusCd');
	if($("#eligFlag").prop('checked') == false){
		for (var i = 0, length = radios.length; i < length; i++) {
		    if (radios[i].checked && radios[i].value=='CHI') {
		    	$('#errorDiv').removeClass('hidden').slideDown();
				return;
		    }
		}
		
		
	}

	if( document.getElementById('candidateCheckInStatus').value=='Checked In' && handleOkAlreadyCheckedinFlag==false) {
		handleOkAlreadyCheckedinFlag=true;		
		//alert("document.getElementById('candidateCheckInStatus').value=" + document.getElementById('candidateCheckInStatus').value);
        //$( "#dialog-confirm-already-checkedin" ).html("Are you sure you want to change the appointment status to "+$('input[name=eventOutComeStatusCd]:checked').val());
        $( "#dialog-confirm-already-checkedin" ).html("Are you sure you want to change the appointment status to "+getEventOutComeStatusDescription($('input[name=eventOutComeStatusCd]:checked').val()) +"?");        
       
        $( "#dialog-confirm-already-checkedin" ).dialog( "open" );
        $("#dialog-confirm-already-checkedin").dialog('option','width',450);
        $("#resultsPage").focus();
        return;		
	}
	
	var form=$('form#subform').serialize();
    var formAction = $("#subform").attr("action");
    //console.log(form);
    //return;
$.ajax({
    url: formAction,
    type: "post",
    data: form,
    success: function(data){
    	
    	var bookingId=data["bookingId"];
    	var statusDesc=data["statusDesc"];
    	var statusCd=data["statusCd"];
    	document.getElementById('candidateCheckInStatus').value=statusDesc;
    	//alert(statusDesc);
    	//alert(statusCd);
    	if(statusCd!=null){
    		$("#"+bookingId+"-status").removeClass();
    		$("#"+bookingId+"-status").addClass(statusCd);
    	}
    	$("#"+bookingId+"-status").text(statusDesc);
    	
    	$( "#dialog-confirm" ).dialog( "close" );
    },
    error:function(){
        
        $("#result").html('there is error while submit');
    }   
  }); 
  </c:if>
  
}

</script>


<style>
.ui-widget{

font-size:0.8em;
} 
element.style {
width:auto;
}
</style>

<c:if test="${formNotAvailableForCBTDeliveryFlag}">
<spring:message code="changeAccomidationStatus.message.formNotAvailableForCBTDeliveryFlag" />
<div id="formNotAvailableFlag" style="visibility: hidden">True</div>
<script>
//$( "#dialog-confirm" ).dialog.buttons.Cancel.attr("disabled", true);
</script>
</c:if>

<c:if test="${formNotAvailableBeforeDateFlag}">
<spring:message code="changeAccomidationStatus.message.formNotAvailableBeforeDateFlag" />
<div id="formNotAvailableFlag" style="visibility: hidden">True</div>
</c:if>
<c:if test="${formNotAvailableFlag}">
<spring:message code="changeAccomidationStatus.message.formNotAvailableFlag" />
<div id="formNotAvailableFlag" style="visibility: hidden">True</div>
</c:if>
<c:if test="${formAvailableFlag}">
<form:form method="POST" modelAttribute="submitBookingForm" action="confirmStatus" id="subform">

<div class="formContainer">
  <div class="create_form">
	<div class="formRow">
	<div id="errorDiv" class="hidden" style="color:#ff0000;">
	<spring:message code="changeAccomidationStatus.errorMessage.idValidity" />
	</div>
	Name:<c:out value="${submitBookingForm.candidateName }"/>
	<br/>
	Appointment Number:<c:out value="${submitBookingForm.appointmentNumber }"/>
	
	<form:hidden path="bookingId"/>
	<c:set var="NOT_CHECKED_IN" value="<%=EventOutComeStatus.NOT_CHECKED_IN%>" />
	<c:set var="COULD_NOT_TEST" value="<%=EventOutComeStatus.COULD_NOT_TEST%>" />
	<c:set var="NO_SHOW" value="<%=EventOutComeStatus.NO_SHOW%>" />
	<c:set var="CHECKED_IN" value="<%=EventOutComeStatus.CHECKED_IN%>" />

	<b>
	<label for="evntOutComeStatus">Select Status:</label></b><br/><br><br>
		<form:radiobutton path="eventOutComeStatusCd" value="<%=EventOutComeStatus.NOT_CHECKED_IN%>" id="NCI" name="rr" class="radiocheck" onchange="changeStatus();"/>Not Checked In
		<br>
		<form:radiobutton path="eventOutComeStatusCd" value="<%=EventOutComeStatus.COULD_NOT_TEST%>" id="CNT" name="rr" class="radiocheck" onchange="changeStatus();"/>Could not Test		
		<br>
		<form:radiobutton path="eventOutComeStatusCd" value="<%=EventOutComeStatus.NO_SHOW%>"  id="NOS" class="radiocheck" onchange="changeStatus();"/>No Show		
		<br>
		<form:radiobutton path="eventOutComeStatusCd" value="<%=EventOutComeStatus.CHECKED_IN%>"  id="CHI" class="radiocheck" onchange="changeStatus();" />Checked In
		
		<br>
		
  <c:choose>
 <c:when test="${submitBookingForm.eventOutComeStatusCd ne'CHI' }">
   <script>
        $('#formSpan').hide();
    </script>
 </c:when>
 <c:otherwise>
   <script>
      $('#formSpan').show();
      </script>
 </c:otherwise>
  </c:choose>
<span id="formSpan">
	<span style="border:2px solid black;   MARGIN: border: 2px solid black; margin: 2px 10% 10px 0px; height: 75px;" >
	<form:checkbox path="eligibilityFlag" cssStyle="width:5%" id="eligFlag" required="required"/> I have checked the test taker id and eligibility
	</span>
		<c:if test="${TestType eq 'PBT' }">
	<BLOCKQUOTE>
	<br/>
	<b><label for="form"> Test Form:</label></b>
	<form:select path="parentBaseFormId"  id="form" >
	<form:option value="">Select</form:option>
		<c:forEach items="${listOfForms}" var="listOfForms">
			<form:option value="${listOfForms.formID}">${listOfForms.formDesc}</form:option>
		</c:forEach>
	</form:select>
	<br>
	<b><label for="lang"> Test Language</label></b>
	<form:select path="baseFormLangCd"  id="lang" >
	<form:option value="">Select</form:option>
		<c:forEach items="${languages}" var="languages">
			<form:option value="${languages.code}">${languages.description}</form:option>
		</c:forEach>
	</form:select>
	<br></BLOCKQUOTE>
	</c:if>
</span>							
	</div>
  </div>
</div>
</form:form>
</c:if>


