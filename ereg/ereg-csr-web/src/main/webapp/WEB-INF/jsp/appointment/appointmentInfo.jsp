<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<% request.setAttribute("pageName", "schedulePage");%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<t:base title="ETS - eREG (Schedule Appointment)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Schedule New Appointment for 
					<c:out value="${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName}" />
				</h1>
			</div>
		</div>
		<img src="${pageContext.request.contextPath}/commonweb/img/sch_appt_step_02.png">
		<br>
		<h2>Appointment Information</h2>
		<span class="required_notification"><span style="color: red; ">* </span>Required Information</span>
		<h6>Test Center: <span id="testCenterName"><c:out value="${appointmentForm.testCenter.name}" /></span>
			<c:choose>
				<c:when test="${fn:length(testCenters) == 0}">
					(<a id="changeTestCenterLink" href="#">Change Test Center</a>)
					<div id="changeTestCenterDiv">
						<p><spring:message code="scheduling.warning.changeTestCenter" /></p>
						<label>State/Agency: </label> <!-- scheduling.label.stateSlashAgency -->
						<select onchange="getTestCenters($(this).val());">
							<option value="">Select</option>
							<c:forEach items="${agencies}" var="agency">
								<option value="${agency.id}">${agency.name}</option>
							</c:forEach>
						</select>
						<label>Test Center: </label>
						<select id="testCenterSelect"></select>
					</div>
				</c:when>
				<c:when test="${fn:length(testCenters) > 1}">
					(Change Test Center:&nbsp;
					<select id="currentTestCenterId" onchange="changeTestCenter($(this).val());">
						<c:forEach items="${testCenters}" var="testCenter">
							<option value="${testCenter.id}">${testCenter.name}</option>
						</c:forEach>
					</select> )
				</c:when>
			</c:choose>
		</h6>
		<span id="testCenterAddress" class="testCenterAddress"><c:out value="${appointmentForm.testCenterAddress}" /></span>
		<input type="hidden" id="testCenterId" value="${appointmentForm.testCenter.id}">
	</div>
	<!-- Form Canvas starts here -->
	<!-- Start - DATEPICKER MODAL -->
	<div id="dateModal" title="Select Date and Time">
		<input type="hidden" id="indexHidden"/>
		<input type="hidden" id="startDateHidden"/>
		<ct:spinner divName="datepickerWaiting" width="200px" height="200px"></ct:spinner>
		<div id="datepicker" style="font-size:12px;"></div>
		<div class="create_form">
			<div id="modalStyle">
				<span id="dateTimeError"></span>
				<label>Date :</label><span id="lblDate"><i>(No date selected)</i></span>
				<div class="clear"></div>
				<label>Test Time:</label>
				<span>
					<input id="testTimeHour" class="supersmalltext" placeholder="hh" />
					<input id="testTimeMinute" class="supersmalltext" placeholder="mm" />
					<select id="testTimeAMPM" class="AMPM">
						<option value=""></option>
						<option value="am">AM</option>
						<option value="pm">PM</option>
					</select>
				</span>
			</div>
			<input type="hidden" id="dateTimeIndex" />
		</div>
	</div>
	<!-- End - DATEPICKER MODAL -->
	<form:form method="POST" modelAttribute="appointmentForm">
	
		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="create_form">
				<div class="formRow">
					<form:errors path="*" cssClass="errorblock" element="div" />
					<div class="create_form">
						<input type="hidden" id="appointmentCount" value="${appointmentForm.appointmentCount}" />
						<input type="hidden" id="oldAccordionHeader" />
						<c:if test="${appointmentForm.accomodationFlag}">
							<b>${appointmentForm.profile.customer.firstName}
								${appointmentForm.profile.customer.lastName} has one or more <a
								href="<ct:encode out="/secure/testtaker/accommodation/view?customerId=${appointmentForm.profile.customer.id}"/>">approved
									accommodations.</a> Select
							</b> Test Title, Date <b>and</b> Type <b>to see any applicable
								accommodations.</b>
						</c:if>
						<div id="accordion">
							<c:forEach items="${appointmentForm.appointments}" var="appointment" varStatus="status">
								<c:set var="index" value="${status.index}"/>
								<t:appointmentInfoPanel index="${index}" source="new" />
							</c:forEach>
						</div>
					</div>
					<br />
					<div class="row-fluid">
						<div class="ereg_span_01">
							<a id="addAnotherAppointment" style="text-decoration:none; cursor:pointer; cursor:hand;"><img style="opacity:0.7;" onMouseOver="this.style.opacity='1.0'" onMouseOut="this.style.opacity='0.7'" src="${pageContext.request.contextPath}/commonweb/img/add.png" style="margin:-4px 0 0 0;">&nbsp;&nbsp;Add another Appointment</a>
						</div>
					</div>	
				</div>
			</div> <!--  Form Canvas ends here -->
		</div>
		<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01">
					<button style="margin:0 0 3px 0;" type="submit" class="submit" name="goBack" onclick="bypassValidation();">&lt; Back</button>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="othbutton" href="<spring:url value="/secure/tcaScheduling/cancelAppointment" />" >Cancel</a>
					<button class="submit" type="submit" style="float: right;">Next &gt;</button>
				</div>
			</div>	
		</div>
	</form:form>
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
			height: 30px !important;
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
	<script src="${pageContext.request.contextPath}/commonweb/js/scheduling.js"></script>
	<script type="text/javascript">
	
		var dateInfo = new Array();
		
		function setHeaderIndice() {
			$("[id^=appointmentHead]").each(function(index) {
				$(this).children(".headerIndex").html(index + 1);
			});
		}
	
		function setHeaderTitles() {
			var indexOpen = $(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-accordion-icons.ui-accordion-header-active.ui-state-active.ui-corner-top").attr("id").match(/\d+/)[0];
			$("#accordion_header" + indexOpen).html("");
			$(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-accordion-icons.ui-corner-all").each(function() {
				var indexClose = $(this).attr("id").match(/\d+/)[0];
				var headerTitle = "";
				var testTitleSelect = $('#testTitle' + indexClose);
				if ("" != $(testTitleSelect).val()) {
					headerTitle = testTitleSelect.children("option[value=" + testTitleSelect.val() + "]").html();
					if ("" != $("#dateSpan" + indexClose).html()) {
						headerTitle += ", " + $("#dateSpan" + indexClose).html();
						if ("" != $("#appointments" + indexClose + "\\.testTimeHour").val() && "" != $("#appointments" + indexClose + "\\.testTimeMinute").val() && "" != $("#appointments" + indexClose + "\\.testTimeAMPM").val()) {
							headerTitle += ", " + $("#appointments" + indexClose + "\\.testTimeHour").val() + ":" + $("#appointments" + indexClose + "\\.testTimeMinute").val() + " " + $("#appointments" + indexClose + "\\.testTimeAMPM").val().toUpperCase();
						}
					}
				}
				$("#accordion_header" + indexClose).html(headerTitle);
			});
		}
		
		function requiredRadioButtonChange(target) {
			$(target).siblings("span.testLang").css('backgroundImage', "url('/ereg-csr-web/img/valid.png')");
		}

		$(function() {

			$("#accordion").accordion({
				heightStyle: "content",
				change: function() {
					var indexOpen = $(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-accordion-icons.ui-accordion-header-active.ui-state-active.ui-corner-top").attr("id").match(/\d+/)[0];
					$("#accordion_header" + indexOpen).html("");
					setHeaderTitles();
				}
			});
			
			setHeaderTitles();
			setHeaderIndice();
			
			$("#addAnotherAppointment").click(function() {
				var appointmentCount = $("#appointmentCount").val();
				appointmentCount++;
				if (!validateAppointments()) {
					return;
				}
				$.get("info/addAnotherAppointment", 
					{ appointmentCount: appointmentCount }, 
					function(data) { 
						var panelCount = $("#accordion>div").size();
						$("#accordion").append(data).accordion("destroy").accordion({
							heightStyle: "content",
							active: panelCount,
							change: function() {
								setHeaderTitles();
							}
						});
						setHeaderTitles();
						$("#appointmentCount").val(appointmentCount);
						setHeaderIndice();
						addShowDatePickerHandler();
						$(".radiocheck").change(function(e) {
							requiredRadioButtonChange(e.target);
						});
						$("[id^=testCenter]").each(function() {
							$(this).siblings("input[type=hidden]").val($(this).val());
						});
						  
						$("#appointments"+(appointmentCount)+"\\.agency").val($("#appointments0\\.agency").val());
						$("#appointments"+(appointmentCount)+"\\.testCenter").val($("#appointments0\\.testCenter").val());
						$("#initialDeliveryMode"+appointmentCount).val(null);
						$('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.languageCode]').filter('[value="'+prefferedLangCode +'"]').attr('checked', true);
						
					});
			});
			
			$("[id$=testId]").each(function() {
				$(this).siblings("select").val($(this).val());
				$(this).siblings("select").change();
			});
			
			$("[id$=booking\\.appointmentDateTime]").each(function() {
				if ("" != $(this).val()) {
					$(this).siblings('span.dateSpan').html(transformDate('MM d, yy', 'mm/dd/yy', $(this).val()));
					$(this).siblings('span.timeSpan').html($(this).siblings('input[id$=testTimeHour]').val() + ":" + $(this).siblings('input[id$=testTimeMinute]').val() + " " + $(this).siblings('input[id$=testTimeAMPM]').val().toUpperCase());
					//$(this).siblings("span").css("display", "inline");
				}
			});
			
			$("[id$=agency]").each(function() {
				$(this).siblings("select").val($(this).val());
				$(this).siblings("select").change();
			});
			
			$(".radiocheck").change(function(e) {
				requiredRadioButtonChange(e.target);
			});
			
			$('#currentTestCenterId').val($('#testCenterId').val());
			
			registerChangeTestCenterDialogEvent();
			
			$('#changeTestCenterLink').click(function(e) {
				$('#changeTestCenterDiv').dialog('open');
			});
			
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
			
			var currentDate = new Date();
			$("#startDateHidden").val(currentDate);
			
			addShowDatePickerHandler();
		});
		
		//constants
		var testAccommodationData = "testAccommodationData";
		var testFormData = "testFormData";
		var testLanguageData = "testLanguageData";
		var testDeliveryModeData = "testDeliveryModeData";
		var deliveryCodeCBT = "CBT";
		var deliveryCodePBT = "PBT";
		var prefferedLangCode = '${appointmentForm.profile.customer.prfrdTstTakingLang.code}';
		
		function getAppointmentData(index, requestFor){
			
			var varTestId = $('#testTitle'+ index).val();
			var varTestDate = $("#appointments" + index + "\\.booking\\.appointmentDateTime").val().replace(/\//g, "-");
			var initialDeliveryMode = $("#initialDeliveryMode" + index).val();
			var varDeliveryMode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked").val();
			if (varDeliveryMode == null) {
				varDeliveryMode = '';
			}
			var varLangCode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.languageCode]:checked").val();
			if(varLangCode == null || varLangCode ==''){
				varLangCode = prefferedLangCode;
			}
			var varTestCenterId = $("#testCenterId").val();
			//alert(varTestCenterId);
			$.get("info/getAppointmentData", 
				{
					testId: varTestId,
					testDate: varTestDate,
					deliveryMode : varDeliveryMode,
					testCenterId : varTestCenterId,
					index: index
				},
				function(data) {
					  
					var xmlDoc = $.parseXML( data ),
					$xml = $( xmlDoc ),
					$activeAppointmentData = $xml.find( testAccommodationData );
					$testDeliveryModeData = $xml.find( testDeliveryModeData );
					  	
					if(requestFor.indexOf(testAccommodationData) >= 0 ){
						if( $activeAppointmentData != null ){
					  		if($activeAppointmentData.text() != null){
					  			if($activeAppointmentData.text().length >25){
					  				$("#appointments" + index + "\\.accommodationDiv").html('<li>');
					  				$("#appointments" + index + "\\.accommodationDiv").append('<label>Accommodations:</label>');
									$("#appointments" + index + "\\.accommodationDiv").append($activeAppointmentData.text());
									$("#appointments" + index + "\\.accommodationDiv").append("<br>");
									$("#appointments" + index + "\\.accommodationDiv").append('</li>');
									$("#appointments" + index + "\\.accommodationDiv").show();
								}else{
									$("#appointments" + index + "\\.accommodationDiv").hide();
								}
					  		}
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
						
						showHideForm(index);
					}
				  	
					if(requestFor.indexOf(testLanguageData) >= 0 ){
					  	$testLanguageData = $xml.find( testLanguageData );
					  		  
						$("#testLanguagesDiv"+index).html($testLanguageData.text() + '<span class="testLang"></span>');
						$('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.languageCode]').filter('[value="'+varLangCode +'"]').attr('checked', true);
					}
					
					if(requestFor.indexOf(testDeliveryModeData) >= 0 ){
					  	$testDeliveryModeData = $xml.find( testDeliveryModeData );
					  	
						$("#testDeliveryModesDiv"+index).html($testDeliveryModeData.text() + '<span class="testLang"></span>');
						if(initialDeliveryMode != null && initialDeliveryMode != ''){
							$('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]').filter('[value="'+initialDeliveryMode +'"]').attr('checked', true);
						}else if(varDeliveryMode != null && varDeliveryMode != ''){
							$('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]').filter('[value="'+varDeliveryMode +'"]').attr('checked', true);
						}else{
							if($('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]').length == 1){
								$('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:first').attr('checked', true);;
							}else{
								$('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]').filter('[value="'+deliveryCodePBT +'"]').attr('checked', true);
							}
						}
						showHideForm(index);						
					}
						  
				 });
		}

		function showHideTestTitleDependentElements(index){
			var testId = $('#testTitle'+ index).val();
			
			if(testId != null && testId > 0){
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
			
		}
		
		function showHideForm(index){
			
			var varDeliveryMode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked").val();

			if(varDeliveryMode == null || varDeliveryMode == deliveryCodeCBT){
				$('#appointments'+ index +'\\.formDiv').prop('disabled', true);
				$('#appointments'+ index +'\\.formDiv').hide();
			}else{
				$('#appointments'+ index +'\\.formDiv').prop('disabled', false);
				$('#appointments'+ index +'\\.formDiv').show();				
			}
		}

		
		function removeAppointment(index) {
			var panelCount = $("#accordion>div").size();
			if (1 == panelCount) {
				alert("You cannot delete the last appointment.");
				return;
			}
			if (!confirm("Are you sure you want to delete this appointment?")) {
				return;
			}
			$.get("info/removeAppointment",
					{ index: index },
					function(data) {
						if ("OK" == data) {
							$("#appointmentHead" + index).remove();
							$("#appointmentDiv" + index).remove();
							$("#accordion").accordion("destroy").accordion({
								  heightStyle: "content",
								  change: function() {
									  setHeaderTitles();
								  }
								});
							setHeaderTitles();
							setHeaderIndice();
						}
					});
		}

		function bypassValidation() {
			$("[required]").removeAttr("required");
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
			$.get("../findSeatByAdmin", { 
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
//			alert(index + " " + dateTimeIndex + " " + holdSeatDate + " " + holdSeatTime + " " + deliveryMode + " " + testId);
			$.get("../holdSeatByAdmin", 
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
		
		function clearTime() {
			$('#testTimeHour').val('');
			$('#testTimeMinute').val('');
			$('#testTimeAMPM').val('');
		}
		
		function addShowDatePickerHandler() {
			$(".showDatePicker").click(function() {
				$('#indexHidden').val($(this).find('.panelIndex').val());
				findSeat();
				$('#dateTimeError').html('');
				$("#dateModal").dialog("open");
			});
		}
		
		function validateAppointments() {
			$('.errorMessage').remove();
			$('.errorblock').remove();
			var valid = true;
			var testTitles = [];
			var firstErrorPanel = null;
			$('[id^=appointmentDiv]').each(function(i) {
				var index = $(this).find('.panelIndex').val();
				if ($(this).find('select[id=appointments' + index + '\\.booking\\.test]').val() == '') {
					$(this).find('select[id=appointments' + index + '\\.booking\\.test]').after(getErrorMessage('Please select the test title.'));
					valid = false;
				} else {
					testTitles.push($(this).find('select[id=appointments' + index + '\\.booking\\.test]').val());
				}
				if ($(this).find('span[id=dateSpan' + index + ']').html() == '') {
					$(this).find('input[id=appointments' + index + '\\.testTimeAMPM]').after(getErrorMessage('Please select test date and time.'));
					valid = false;
				}
				if ($(this).find('input[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.deliveryModeCode]:checked').length == 0) {
					$(this).find('#testDeliveryModesDiv' + index).append(getErrorMessage('Please select a test type.'));					
					valid = false;
				}
				if ($(this).find('input:radio[name=appointments\\[' + index + '\\]\\.booking\\.testVariation\\.id\\.languageCode]:checked').length == 0) {
					$(this).find('#testLanguagesDiv' + index).append(getErrorMessage('Please select a test language.'));
					valid = false;
				}
				if (!valid && firstErrorPanel == null) {
					firstErrorPanel = i;
				}
			});
			var errorSummary = getErrorSummary();

			var testTitleSize = testTitles.length;
			if ($.unique(testTitles).length < testTitleSize) {
				$(errorSummary).append('Duplicate test found.<br />');
				valid = false;
			}
			if (!valid) {
				$('#accordion').before(errorSummary);
				$('#accordion').accordion({active: firstErrorPanel});
			}
			return valid;
		}
		
		function getErrorMessage(message) {
			return '<span class="errorMessage">' + message + '</span>';
		}
		
		function getErrorSummary() {
			var errorSummary = $('<div/>', {class: 'errorblock'});
			$('.errorMessage').each(function() {
				$(errorSummary).append($(this).html() + '<br />');
			})
			return errorSummary;
		}
		
		function transformDate(newPattern, oldPattern, dateText) {
			return $.datepicker.formatDate(newPattern, $.datepicker.parseDate(oldPattern, dateText));
		}

		function changeTestCenter(testCenterId) {
			$.getJSON('info/changeTestCenter',
				{ testCenterId : testCenterId },
				function(data) {
					switch(data.action) {
						case "update":
							$('#testCenterName').html(data.testCenterName);
							$('#testCenterAdd').html(data.address);
							break;
						case "refresh":
							window.location.reload();
							break;
						case "redirect":
							window.location.href = "../../appointment/new/info?testCenterId=" + testCenterId;
					}
				});
		}
		
	</script>
</t:base>