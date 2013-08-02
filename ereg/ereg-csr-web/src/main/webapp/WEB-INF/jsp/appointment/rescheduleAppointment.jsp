<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Reschedule Appointment)">
		<style>
		.css2 {
			background-color: green;
		}
		#modalStyle span {
			font-size:12px;
			display:block;
		}
		#modalStyle label {
			max-width:80px;
			margin:0 0 0 0;
			font-family:Arial;
			font-weight:bold;
			font-size:12px;
		}
		#timeList {
			font-size: 12px;
			width: 220px !important;
			height: 72px;
			border: none;
			background: transparent;
			readonly: true;
			border: 1px solid gray;
			margin: -10px 0 0 60px;
			text-align:left !important;
		}
		a.linkButton, button.linkButton {
			background-color: #BCBCBC;
			border: 1px solid #202020;
			border-bottom: 1px solid #5b992b;
			border-radius: 3px;
			color: #1B1B1B;
			padding: 1px 6px;
			text-align: center;
			text-shadow: 0 -1px 0 #191919
			font-size:10px;
		}
		a.linkButton:hover, button.linkButton:hover {
			opacity:.70; text-decoration:none;
			cursor: pointer; 
		}
		a.linkButton:active, button.linkButton:active {
			border: 1px solid #D1D1D1;
			box-shadow: 0 0 10px 5px #515151 inset; 		
		}
		.create_form li > span {
			color:black !important;
		}
		.create_form label {
			margin:-1px 0 0 0;
		}
		.AMPM {
			display:inline !important;
			height: 30px !important;
			max-width: 65px !important;
			padding: 5px 8px !important;
			width: 80% !important;
			margin:1.5px 0 0 0;
		}
		.supersmalltext {
			height: 14px !important;
			max-width: 50px !important;
			padding: 5px 8px;
			width: 80%;
		}
		.supersmalltextAMPM {
			height: 37px !important;
			max-width: 75px !important;
			padding: 5px 8px;
			width: 80%;
		}
	</style>
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>
				<spring:message code="rescheduleappointmentpage.heading" arguments="${appointmentForm.profile.customer.firstName}, ${appointmentForm.profile.customer.lastName}"/>
				</h1>
			</div>
		</div>
		<p>
		<spring:message code="rescheduleappointmentpage.instructions"	/>		
		</p>
		<br>
		<h2>Appointment Information</h2>
		<span class="required_notification"><span style="color: red">* </span>Required Information</span>		
	</div>
	<c:if test="${appointmentForm.accomodationFlag}">
		<span style="margin: 0 0 0 20px"><b>${appointmentForm.profile.customer.firstName}
				${appointmentForm.profile.customer.lastName} has one or more <a
				href="<ct:encode out="/secure/testtaker/accommodation/view?customerId=${appointmentForm.profile.customer.id}"/>">approved
					accommodations.</a> Select
				</b> Test Title, Date <b>and</b> Type <b>to see any applicable
				accommodations.</b>
		</span>
	</c:if>
	<div style="margin:0 0 0 20px">
		<c:set var="testCenterAddress" value="${appointmentForm.getAppointments().get(0).getBooking().getTestCenter().getOrganizationAddresses().iterator().next().getAddress()}"></c:set>
		<h6>Test Center: <c:out value="${appointmentForm.appointments[0].booking.testCenter.name}" /> (<a href="#">Change Test Center</a>)</h6>
		<span class="testCenterAdd"><c:out value="${testCenterAddress.addressLine1}, ${testCenterAddress.city}, ${testCenterAddress.state.name } ${testCenterAddress.zipFour }" /></span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form id="rescheduleAppointment" method="POST" modelAttribute="appointmentForm">
		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="create_form">
				<div class="formRow">
					<form:errors path="*" cssClass="errorblock" element="div" />
					<c:if test="${accommodationsChanged != null && accommodationsChanged}">					
						<div 
							class="attention">
							<spring:message code="accoChangeAttention" arguments="${appointmentForm.profile.customer.firstName}, ${appointmentForm.profile.customer.lastName}"/>
						</div>	
					</c:if>
					<t:dateModal />
					<div class="create_form">
						<t:appointmentInfoPanel index="0" source="reschedule"/>
						<input id="origTestCenter" type="hidden" value="<c:out value="${origTestCenter.id}" />" />
					</div>
				</div>
			</div> <!--  Form Canvas ends here -->
			<div id="confirmModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="confirmLabel" aria-hidden="true">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3 id="confirmLabel">Reschedule Confirmation</h3>
				</div>
				<div id="confirmModalBody" class="modal-body" title="Reschedule Confirmation">
					<p>
					<spring:message code="rescheduleappointmentpage.confirmation" />
					</p>
					<table style="border: 1px solid black; border-collapse:collapse; width: 90%;">
						<thead>
							<tr>
								<th></th><th>Original Appointment</th><th>New Appointment</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Test Title</td>
								<td><c:out value="${appointmentForm.appointments[1].booking.testVariation.test.testName}" /></td>
								<td><div id="newTestTitle"></div></td>
							</tr>
							<tr>
								<td>Test Date</td>
								<td>
									<ct:dateTime part="date" value="${appointmentForm.appointments[1].booking.appointmentDateTime}" />
								</td>
								<td><div id="newTestDate"></div></td>
							</tr>
							<tr>
								<td>Test Time</td>
								<td>
									<ct:dateTime part="time" value="${appointmentForm.appointments[1].booking.appointmentDateTime}" />
								</td>
								<td><div id="newTestTime"></div></td>
							</tr>
							<tr>
								<td>Test Center</td>
								<td><span id="oldTestCenter"><c:out value="${appointmentForm.appointments[1].booking.testCenter.name}" /></span></td>
								<td><div id="newTestCenter"></div></td>
							</tr>
							<tr>
								<td>Test Type</td>
								<td><c:out value="${appointmentForm.appointments[1].booking.testVariation.deliveryModeType.description}" /></td>
								<td><div id="newTestType"></div></td>
							</tr>
							<tr>
								<td>Accommodations</td>
								<td>
									<div id="oldAccommodationsDiv">
										<c:choose>
											<c:when test="${appointmentForm.appointments[index].booking.bookingAccommodations.size()>0}" > 
												<c:forEach  var="accommodation" items="${appointmentForm.appointments[index].booking.bookingAccommodations}" varStatus="status">
													${accommodation.accommodationType.description}<br>
												</c:forEach>
											</c:when>	
											<c:otherwise>
												None
											</c:otherwise>							
										 </c:choose>
									</div>
								</td>
								<td><div id="newAccommodationsDiv"></div></td>
							</tr>
							<tr>
								<td>Test Language</td>
								<td><c:out value="${appointmentForm.appointments[1].booking.testVariation.languageType.description}" /></td>
								<td><div id="newTestLanguage"></div></td>
							</tr>
							<tr>
								<td>Test Form</td>
								<td><c:out value="Form ${appointmentForm.appointments[1].baseForm.formCode}" /></td>
								<td><div id="newTestForm"></div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button class="submit" type="submit" style="float:right;">Yes</button>
					<!-- <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button> -->
					<a class="btn" onclick='$("#editSsnModal").modal("hide");'>No</a>
				</div>
			</div>
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01">
					<a href="<spring:url value="/secure/tcaScheduling/cancelAppointment" />"  class="submit">Cancel</a>
					<!-- <a href="#confirmModal" role="button" class="btn" data-toggle="modal">Save</a> -->
					<a id="saveAppointment" class="submit" style="float: right;">Save</a>
					<!-- <a onclick="prepareConfirmModal();">Save2</a> -->
					<!-- <button class="submit" type="submit" style="float:right;">Yes</button> -->
				</div>
			</div>	
		</div>
	</form:form>
	<script src="${pageContext.request.contextPath}/commonweb/js/scheduling.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#appointments0\\.booking\\.test").val($("#appointments0\\.testCode").val());
			
			$("#dateModal").dialog({
				modal: true,
				autoOpen: false,
				width:653,
				buttons: {
					Cancel: function() {
						$(this).dialog("close");
					},
					OK: function() {
						$('select#testTimeAMPM').siblings('.errorMessage').remove();
						if (!$('#testTimeHour').val().match(/^[0-1][0-9]$/) || !$('#testTimeMinute').val().match(/^[0-5][0-9]$/) || $('#testTimeAMPM').val() === "" || $('#testTimeAMPM').val() === null) {
							$('select#testTimeAMPM').after(getErrorMessage('Please enter correct test time.'));
							return;
						}
						holdSeat();
					}
				}
			});
			
			$('#dateSpan0').html(transformDate('MM d, yy', 'mm/dd/yy', $("[id='appointments0.booking.appointmentDateTime']").val()));
			$('#timeSpan0').html($('#appointments0\\.testTimeHour').val() + ":" + $('#appointments0\\.testTimeMinute').val() + " " + $('#appointments0\\.testTimeAMPM').val().toUpperCase());
			
			var currentDate = new Date();
			$("#startDateHidden").val(currentDate);
			
			addShowDatePickerHandler();
			
			$("#confirmModalBody").dialog({
				autoOpen: false,
				resizable: false,
				height: 560,
				width: 550,
				modal: true,
				buttons: {
					"Yes": function() {
						$("#rescheduleAppointment").submit();
					},
					"No": function() {
						$(this).dialog("close");
					}
				}
			});
			$("#saveAppointment").click(function() {
				prepareConfirmModal();
				$("#confirmModalBody").dialog("open");
			});
			$("#agency0").val($("#appointments0\\.agency").val());
			$("#agency0").change();
			$("#appointments0\\.testCenter").val($("#origTestCenter").val());
			var dateTimeHidden = $("#appointments0\\.booking\\.appointmentDateTime");
			if ("" != $(dateTimeHidden).val()) {
				$(dateTimeHidden).siblings("label").children("span").html($(dateTimeHidden).val());
				$(dateTimeHidden).siblings("label").css("display", "inline");
			}
			$("[id^=testCenter]").each(function() {
				$(this).siblings("input[type=hidden]").val($(this).val());
			});
			getAppointmentData(0,testAccommodationData +','+testFormData+',' +testLanguageData+',' +testDeliveryModeData);
			showHideTestTitleDependentElements(0);
			
		});
		
		var initialActiveAccommodationLoad = true;
		var oldAccommodationsLength = 0;
		var oldAccommodations = {};
		<c:if test="${appointmentForm.appointments[index].booking.bookingAccommodations.size()>0}" > 
			oldAccommodationsLength = ${appointmentForm.appointments[index].booking.bookingAccommodations.size()};
			<c:forEach  var="accommodation" items="${appointmentForm.appointments[index].booking.bookingAccommodations}" varStatus="status">
				oldAccommodations['${accommodation.accommodationType.description}'] = true;
			</c:forEach>
		</c:if>
				
		//constants
		var testAccommodationData = "testAccommodationData";
		var testFormData = "testFormData";
		var testLanguageData = "testLanguageData";
		var testDeliveryModeData = "testDeliveryModeData";
		var deliveryCodeCBT = "CBT";
		var deliveryCodePBT = "PBT";

		
		function getAppointmentData(index, requestFor){
			
			var varTestId = $("#appointments" + index + "\\.booking\\.testVariation\\.id\\.testId").val();
			var varTestDate = $("#appointments" + index + "\\.booking\\.appointmentDateTime").val().replace(/\//g, "-");
			var varDeliveryMode = $('#initialDeliveryMode0').val();
			if(varDeliveryMode == null){
				var varDeliveryMode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked").val();
			}
			var varTestCenterId = $("#origTestCenter").val();
			
//			alert(varTestId + "\n" + varTestDate + "\n" + varDeliveryMode + "\n" + varTestCenterId);
						
			$.get("new/info/getAppointmentData",
				  {
					testId: varTestId,
					testDate: varTestDate,		
					deliveryMode : varDeliveryMode,
					testCenterId : varTestCenterId,
					index: index
				  },
					
				  function(data) {
//					  alert(data);
					  var xmlDoc = $.parseXML( data ),
					  $xml = $( xmlDoc ),
					  $activeAppointmentData = $xml.find( testAccommodationData );
					
					  if(requestFor.indexOf(testAccommodationData) >= 0 ){
					  	
						  if($activeAppointmentData.text().length >25){
							  
				  				$("#appointments" + index + "\\.accommodationDiv").html('<li>');
				  				$("#appointments" + index + "\\.accommodationDiv").append('<label>Accommodations:</label>');
								$("#appointments" + index + "\\.accommodationDiv").append($activeAppointmentData.text());
								
								if(initialActiveAccommodationLoad){
								 	$("td[id^='accommodationDesc']" ).each( function(){
								 		if(!oldAccommodations[$.trim($(this).html())]){
								 			$(this).html('<b>'+$(this).html()+' &larr; New</b>') ;
								 		}
						    		});
								 	initialActiveAccommodationLoad = false;
								}
								
								$("#appointments" + index + "\\.accommodationDiv").append("<br>");
								$("#appointments" + index + "\\.accommodationDiv").append('</li>');
								$("#appointments" + index + "\\.accommodationDiv").show();
							  }else{
								$("#appointments" + index + "\\.accommodationDiv").hide();
								initialActiveAccommodationLoad = false;
							  }
							  
							  var accommodationsDiv = $("#newAccommodationsDiv");
							  accommodationsDiv.html("");
							  if( $activeAppointmentData.text().length >25){
								  
								 	$($activeAppointmentData.text().length >25).find( "td[id^='accommodationDesc']" ).each( function(){
								 		
								 		accommodationsDiv.append($(this).text() + '<br>');
						    		});
								  
							  	}else{
							  		accommodationsDiv.html("None");
							  	}
							  
					}
				  	
					if(requestFor.indexOf(testFormData) >= 0 ){
					  	$testFormData = $xml.find( testFormData );
					  	
						  $("#appointments" + index + "\\.baseForm").html($testFormData.text());
						  var options = $("#appointments" + index + "\\.baseForm>option");
						  var rand = Math.floor(Math.random() * options.length) + 1;
						  options.eq(rand).prop('selected', true);
						  if (null != $("#appointments" + index + "\\.baseFormId").val()) {
							  $("#appointments" + index + "\\.baseForm").val($("#appointments" + index + "\\.baseFormId").val());
						  }
					}
				  	
					if(requestFor.indexOf(testLanguageData) >= 0 ){
					  	$testLanguageData = $xml.find( testLanguageData );
					  		  
						$("#testLanguagesDiv"+ index).html($testLanguageData.text() + '<span class="testLang"></span>');
						
					}

					if(requestFor.indexOf(testDeliveryModeData) >= 0 ){
					  	$testDeliveryModeData = $xml.find( testDeliveryModeData );
					  		  
						$("#testDeliveryModesDiv"+index).html($testDeliveryModeData.text() + '<span class="testLang"></span>');
						$('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]').filter('[value="'+varDeliveryMode +'"]').attr('checked', true);
						
					}

						  
				 });
		}

		function showHideTestTitleDependentElements(index){
			var testId = $("#appointments" + index + "\\.booking\\.testVariation\\.id\\.testId").val();
			
			if(testId != null && testId != ""){
				$('#testTitleDependentContainerDiv'+index).show();
			}else{
				$('#testTitleDependentContainerDiv'+index).hide();
			}
			showHideDeliveryModeDateTimeDependentElements(index);
		}
		
		function showHideDeliveryModeDateTimeDependentElements(index ){
			
			var deliveryMode = $("#initialDeliveryMode"+index).val();
			if(deliveryMode == null){
				deliveryMode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked").val();
			}
			
			var testDate = $("#appointments" + index + "\\.booking\\.appointmentDateTime").val()

			
			if(deliveryMode != null && deliveryMode != '' && testDate != null && testDate != "" && $('#testTitleDependentContainerDiv'+index).css('display') != 'none'){
				$('#testDeliveryModeDateTimeDependentContainerDiv'+index).show();
				showHideForm(index);
			}else{
				$('#testDeliveryModeDateTimeDependentContainerDiv'+index).hide();				
			}
			showHideTestDateDependentElements(index);
		}
		
		function showHideForm(index){
			
			var varDeliveryMode = $("#initialDeliveryMode"+index).val();
			if(varDeliveryMode == null){
				varDeliveryMode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked").val();
			}

			if(varDeliveryMode == null || varDeliveryMode == deliveryCodeCBT){
				$('#appointments'+ index +'\\.formDiv').prop('disabled', true);
				$('#appointments'+ index +'\\.formDiv').hide();
				
			}else{
				$('#appointments'+ index +'\\.formDiv').prop('disabled', false);
				$('#appointments'+ index +'\\.formDiv').show();				
			}
		}


 		function showHideTestDateDependentElements(index){
			var testDate = $("#appointments" + index + "\\.booking\\.appointmentDateTime").val()
			if(testDate != null && testDate != "" && $('#testDateTimeContainerDiv'+index).css('display') != 'none'){
				$('#testDateTimeDependentContainerDiv'+index).show();
			}else{
				$('#testDateTimeDependentContainerDiv'+index).hide();
			}
		}
 
		
		function prepareConfirmModal() {
			$("#newTestTitle").html($("#testTitle0 option:selected").html().replace("(rescheduling)", ""));
			$("#newTestDate").html(transformDate('MM d, yy', 'mm/dd/yy', $("#appointments0\\.booking\\.appointmentDateTime").val()));
			if ($("#appointments0\\.testTimeHour").val != "" && $("#appointments0\\.testTimeMinute").val() != "" && $("#appointments0\\.testTimeAMPM").val() != "") {
				$("#newTestTime").html($("#appointments0\\.testTimeHour").val() + ":" + $("#appointments0\\.testTimeMinute").val() + " " + $("#appointments0\\.testTimeAMPM").val().toUpperCase());
			}
			/* if ($("input#appointments0\\.booking\\.testCenter").length == 0) {
				$("#newTestCenter").html($("#appointments0\\.booking\\.testCenter").html());
			} else {
				$("#newTestCenter").html($("#oldTestCenter").html());
			} */
			$("#newTestCenter").html('In dev');
			var testType = $("input:radio[name='appointments[0].booking.testVariation.id.deliveryModeCode']:checked").next('span').html();
			$("#newTestType").html(testType);
			var testLanguage = $("input:radio[name='appointments[0].booking.testVariation.id.languageCode']:checked").next('span').html();
			$("#newTestLanguage").html(testLanguage);
			$("#newTestForm").html($("#appointments0\\.baseForm option:selected").html());
		}

		function getTestCenters(index) {
			var agencyId = $("#agency" + index).val();
			$.get("../new/info/getTestCenters", 
					{ agencyId: agencyId }, 
					function(data) {
						$("#appointments" + index + "\\.booking\\.testCenter").html(data);
						$("#appointments" + index + "\\.booking\\.testCenter").val($("#origTestCenter").val());
					});
		}
		
		function findSeat() {
			waitForSeatResponse();
			
			var index = $('#indexHidden').val();

			var dateTimeHidden = $('#appointments' + index + '\\.booking\\.appointmentDateTime');
			var dateText = $('#dateSpan' + index).html();
			var timeText = $('#timeSpan' + index).html();
			if (dateText != '') {
				$("#lblDate").html(transformDate('MM d, yy', 'mm/dd/yy', $(dateTimeHidden).val()));
			} else {
				$("#lblDate").html('<i>(No date selected)</i>');
			}
			
			if (timeText != '') {
				$('#testTimeHour').val($('#appointments' + index + '\\.testTimeHour').val());
				$('#testTimeMinute').val($('#appointments' + index + '\\.testTimeMinute').val());
				$('#testTimeAMPM').val($('#appointments' + index + '\\.testTimeAMPM').val());
			} else {
				clearTime();
			}
			$('#timeList').html('');

			var testId = $('#testTitle'+ index).val();
			var dteTestStartDate = new Date($("#startDateHidden").val());
			var testStartDate = $.datepicker.formatDate('mm/dd/yy', dteTestStartDate);
			var dteTestEndDate = new Date(dteTestStartDate.getFullYear(), dteTestStartDate.getMonth() + 3, 0); // last day of month + 2
			var testEndDate = $.datepicker.formatDate('mm/dd/yy', dteTestEndDate);
			var deliveryMode = $('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked').val();
			var languageCode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.languageCode]:checked").val();
			
//			alert(testId + "\n" + testStartDate + "\n" + testEndDate + "\n" + deliveryMode + "\n" + languageCode);
			
			$.get("findSeatByAdmin", { 
				testId: testId,
				testStartDate: testStartDate,
				testEndDate: testEndDate,
				deliveryMode: deliveryMode,
				languageCode: languageCode
			}, function(data) {
//				alert(data);
				exitWaitingForSeatResponse();
				dateInfo = data;
				$("#datepicker").datepicker("destroy").datepicker({
					minDate: 0,
					numberOfMonths: 3,
					beforeShowDay: checkDates,
					onSelect: function(dateText, inst) {
						var index = $('#indexHidden').val();
						$('#appointments' + index + '\\.booking\\.appointmentDateTime').val(dateText);
						var dateDate = $.datepicker.parseDate('mm/dd/yy', dateText);
						$("#lblDate").html($.datepicker.formatDate('MM d, yy', dateDate));
						var timeMap = dateInfo[$.datepicker.formatDate('yymmdd', dateDate)]['timeMap'];
						for (hour in timeMap) {
							$('#dateTimeIndex').val(timeMap[hour]);
						}
						clearTime();
					},
					onChangeMonthYear: changeMonthYearHandler
				});
			});
		}
		
		function holdSeat() {
			waitForSeatResponse();
			
			var index = $('#indexHidden').val();
			var dateTimeIndex = $('#dateTimeIndex').val();
			var holdSeatDate = transformDate('yymmdd', 'MM d, yy', $('#lblDate').html());
			var holdSeatTime = $('#testTimeHour').val() + ":" + $('#testTimeMinute').val() + " " + $('#testTimeAMPM').val().toUpperCase();
			var deliveryMode = $('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked').val();
			var testId = $('#testTitle'+ index).val();
			var languageCode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.languageCode]:checked").val();
//			alert(index + "\n" + dateTimeIndex + "\n" + holdSeatDate + "\n" + holdSeatTime + "\n" + deliveryMode + "\n" + testId + "\n" + languageCode);
			$.get("holdSeatByAdmin", 
				{
					holdSeatDate : holdSeatDate,
					holdSeatTime : holdSeatTime,
					deliveryMode : deliveryMode,
					dateTimeIndex : dateTimeIndex,
					testId : testId,
					bookingIndex : index,
					languageCode : languageCode
				}, function(data) {
					if (data == true) {
						var index = $('#indexHidden').val();
						$('#dateSpan' + index).html($('#lblDate').html());
						$('#appointments' + index + '\\.testTimeHour').val($('#testTimeHour').val());
						$('#appointments' + index + '\\.testTimeMinute').val($('#testTimeMinute').val());
						$('#appointments' + index + '\\.testTimeAMPM').val($('#testTimeAMPM').val());
						$('#timeSpan' + index).html($('#testTimeHour').val() + ":" + $('#testTimeMinute').val() + " " + $('#testTimeAMPM').val().toUpperCase());
						getAppointmentData(index,testAccommodationData +','+testFormData);
                        showHideDeliveryModeDateTimeDependentElements(index);
						$('#dateModal').dialog("close");
					} else if (data == false) {
						exitWaitingForSeatResponse();
						$("#dateTimeError").html('<p style="color: red;">Sorry, the last seat for this time period has just been hold by another test taker. Please choose another time.</p>');
					} else {
						alert(data);
					}
				});
		}
		
		function addShowDatePickerHandler() {
			$(".showDatePicker").click(function() {
				$('#indexHidden').val(0);
				findSeat();
				$('#dateTimeError').html('');
				$("#dateModal").dialog("open");
			});
		}
		
		function clearTime() {
			$('#testTimeHour').val('');
			$('#testTimeMinute').val('');
			$('#testTimeAMPM').val('');
		}
	</script>
</t:base>