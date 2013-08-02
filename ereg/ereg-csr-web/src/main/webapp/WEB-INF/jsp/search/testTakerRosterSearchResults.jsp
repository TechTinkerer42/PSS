<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<style>
table.test {
	border: 1px solid #424242;
	text-align: left;
	width: 920px !important;
}
button.linkButton {
    border: none;
    display: inline;
    margin: 0em;
    padding: 0em;
    outline: none;
    outline-offset: 0em;
    box-shadow:none;
    /* Look like a link */
    background: none;
    color: #0088CC;
    cursor: pointer;
    font: inherit;
    text-decoration: none;
    text-shadow: none;
}
/* Remove extra space inside buttons in Firefox */
button::-moz-focus-inner {
    border: none;
    padding: 0em;
}

.CHI{
color: green;
}
.CNT{
color: brown;
}
.NCI{
color: black;
}
.NOS{
color: red;
}
</style>

<script type="text/javascript">
function toolTip(id, val) {

	$(id).tooltip({
		content : '<label> ' + val + '</label>',
		items : 'span'
	}).off("mouseover").on("click", function() {
		$(this).tooltip("open");
		return false;
	})
}

$(function(){
    $('button.modalWindow').on('click', function(){
        var tr = $(this).closest('tr');
        var bookingId=tr.find('input[name="bookingId"]').val();
        var name=tr.find('input[name="candidateName"]').val();
        var appointmentNumber=tr.find('input[name="appointmentNumber"]').val();
        var candidateCheckInStatus=tr.find('input[name="candidateCheckInStatus"]').val();
        var candidateDeliveryModeDesc=tr.find('input[name="candidateDeliveryModeDesc"]').val();
  
        var formAction = $("#changeStatus").attr("action");
        var form=$("form#changeStatus").serialize()+"&bookingId="+bookingId+"&appointmentNumber="+appointmentNumber+"&candidateName="+name+"&candidateDeliveryModeDesc="+candidateDeliveryModeDesc;
        

        $.ajax({
            url: formAction,
            type: "post",
            data: form,
            success: function(data){
                //alert("success");
    			//console.log(data);
    			if(data.indexOf('formNotAvailableFlag') !== -1) {
    				$("#dialog-confirm-message").html(data);
                    $("#dialog-confirm-message").dialog( "open" );
                    $("#dialog-confirm-message").dialog('option','width',450);
                    $("#resultsPage").focus(); 
    			} else {
    				$( "#dialog-confirm").html(data);
                    $( "#dialog-confirm").dialog( "open" );
                    $("#dialog-confirm").dialog('option','width',450);
                    $("#resultsPage").focus(); 
    			}
                
            },
            error:function(){
                
                $("#result").html('there is error while submit');
            }   
          }); 
        
        
        
    
    });
});




</script>

	<div class = "formContainer">
			<c:if test="${empty testTakerRosterResultVO.results}">
		
			
			  No Results Found 
			  
			
			</c:if>
			
			

<c:if test="${not empty testTakerRosterResultVO.results}">
<fieldset>
	<div class="create_form" tabindex="0" id="resultsPage">
	
	<ct:paginationheader currentPage="${testTakerRosterResultVO.currentPage}" pages="${testTakerRosterResultVO.pages}" totalRecordCount="${testTakerRosterResultVO.totalRecordCount}" rowperPage="${testTakerRosterSearchForm.rowperPage}" totalsPages="${testTakerRosterResultVO.totalsPages}"></ct:paginationheader>
		
	
	</div>

	<table id="searchResultsTable"  style="width:100%">
		<thead>
			<tr>
				<th id="id">No</th>
				<th id="testtakerInfo">Test Taker Information</th>
				<th id="testInfo">Test Information</th>
				<th id="accomidation">Accommodation</th>
				<th id="chkinStatus">Check in Status</th>
				<th id="action">Action</th>
			</tr>
		</thead>
		<tbody>
			<form:form id="changeStatus" method="POST"
				modelAttribute="submitBookingForm" action="chgStatusAppForm"
				onsubmit="return false;">
			</form:form>
			
			<c:if test="${empty testTakerRosterResultVO.results}">
			  <tr><td colspan="6" align="center"> No Results Found </td></tr>
			</c:if>
			
			<c:forEach items="${testTakerRosterResultVO.results}"
				var="testTakerRosterSearchResultVO" varStatus="i">

				<tr>
					<form>
						<input type="hidden" name="bookingId"
							value="${testTakerRosterSearchResultVO.bookingId }" /> <input
							type="hidden" name="appointmentNumber" value="<ct:format out="${testTakerRosterSearchResultVO.appointmentNumber}" />" /> <input
							type="hidden" name="candidateName"
							value="${testTakerRosterSearchResultVO.lastName}, ${testTakerRosterSearchResultVO.firstName}" />
							<input 	type="hidden" name="candidateCheckInStatus" id="candidateCheckInStatus" value="${testTakerRosterSearchResultVO.eventOtcmSatusTypDesc}" />
							<input 	type="hidden" name="candidateDeliveryModeDesc" id="candidateDeliveryModeDesc" value="${testTakerRosterSearchResultVO.deliveryModeDesc}" />
					</form>
					
					




					<td style="width:5%">${(i.count) + ( (testTakerRosterResultVO.currentPage -1) *
						testTakerRosterResultVO.rowsPerPage)}</td>
					<td>
                     <a href="<ct:encode out="/secure/testtaker/view?customerId=${testTakerRosterSearchResultVO.customerId }" />"><c:out value="${testTakerRosterSearchResultVO.lastName}, ${testTakerRosterSearchResultVO.firstName} " /></a><br> Date of Birth:<ct:dateTime part="date" value="${testTakerRosterSearchResultVO.dateOfBirth}" /><br>${testTakerRosterSearchResultVO.phoneNumber}<br>${testTakerRosterSearchResultVO.emailAddress}
								
					</td>
					<td style="width:30%">
					<b><c:out value="${testTakerRosterSearchResultVO.testName}"/></b><br>
						AppointmentNumber:	
												<a href="<ct:encode out="/secure/tcaScheduling/view/?bookingId=${testTakerRosterSearchResultVO.bookingId }" />">
							<ct:format out="${testTakerRosterSearchResultVO.appointmentNumber}" /></a>
											
											<br>Test Date & Time:<ct:dateTime part="dateTime" value="${testTakerRosterSearchResultVO.appointmentDate}" /><br>${testTakerRosterSearchResultVO.deliveryModeDesc}<br>Test
						Form:${testTakerRosterSearchResultVO.formDesc}</td>
					 <td style="width:5%">
							<c:choose>
    							<c:when test="${testTakerRosterSearchResultVO.accomodation  eq 'Yes'}">
							
							<c:forEach items="${testTakerRosterSearchResultVO.listAccomodationDesc}" var="desc" varStatus="j">
							<c:choose>
    							<c:when test="${ (j.count) eq 1}">
									<c:set var="accDesc" value="${desc}"/>
							</c:when>
   								 <c:otherwise>
									<c:set var="accDesc" value="${accDesc} <br> ${desc}"/>
								</c:otherwise>
							</c:choose>
							
							</c:forEach>
							 <a href="javascript:toolTip('#tooltipClick${(i.count)}','${accDesc}');" >
									<span id='tooltipClick${(i.count)}'>
										${testTakerRosterSearchResultVO.accomodation}</span>
							</a>
							</c:when>
   								 <c:otherwise>
									${testTakerRosterSearchResultVO.accomodation}
								</c:otherwise>
							</c:choose>
							
							
							</td>
					
					<c:forEach items="${checkInStatus}" var="checkin">
					<c:if test="${checkin.description eq  testTakerRosterSearchResultVO.eventOtcmSatusTypDesc}">
					<c:set var="key" value="${checkin.code}"/>
					</c:if>
					</c:forEach>
					
					
					<td id="${testTakerRosterSearchResultVO.bookingId }-status" style="width:15%" class="${key}">${testTakerRosterSearchResultVO.eventOtcmSatusTypDesc}</td>
                     <td><button type="submit" name="submitButton" value="Change Status" class="modalWindow linkButton">Change Status</button></td>
				</tr>

			</c:forEach>



		</tbody>
	</table>
	
	<ct:paginationfooter currentPage="${testTakerRosterResultVO.currentPage}" pages="${testTakerRosterResultVO.pages}" totalRecordCount="${testTakerRosterResultVO.totalRecordCount}" totalsPages="${testTakerRosterResultVO.totalsPages}"></ct:paginationfooter>

	

</fieldset>
</c:if>
 </div>
