<%@ page import="org.ets.ereg.common.business.util.ProgramContextHolder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="${pageContext.request.contextPath}/commonweb/js/scheduling.js"></script>

<spring:message code="scheduling.label.scheduleNewAppointment" var="labelScheduleNewAppointment" />
<c:set var="programCode" value="<%=ProgramContextHolder.getProgramCode()%>"/>;
<t:base title="${labelScheduleNewAppointment}">
	<style>
		/*.css1 {
			background-color: red;
		}*/

		.css2 {
			background-color: green;
		}
		#modalStyle span {
			font-size:12px;
		}
		#modalStyle label {
			max-width:60px;
			margin:-2.5px 0 0 0;
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
		.acdn {
			background-color:fff0e0;
			border:1px solid #db7e06;
			margin:15px 156px;
			width:64.5%;
			font-family:arial, helvetica, sans-serif !important;
			font-size:12px; font-weight:bold;
		}
		.acdn li{
			margin: 0 40px !important;
			padding: 5px !important;
		}
		.acdnError {
			margin:15px 156px !important;
			width:64.5%;
		}
		.create_form li:last-child {
			border-bottom: none !important;
		}
		.batteryMessage {
			padding:5px 5px;
			margin:5px 0;
			background-color:e8f3f9;
			border:1px solid #0099ff;
			font-family:arial, helvetica, sans-serif !important;
			font-size:12px;
		}
		.batteryMessage a {
			color:#54488c; 
			text-decoration:underline; 
			font-weight:bold;
		}
		.batteryStatusList th{
			width:250px;
			font-size:13px;
			font-family:arial, helvetica, sans-serif !important;
		}
		.batteryStatusList td{
			width:250px;
			font-size:12px;
			font-family:arial, helvetica, sans-serif !important;
			font-weight:normal;
			padding:5px;
		}
	</style>
	<script>
		var dateInfo = new Array();
		
		function setHeaderIndice() {
			$('.accordionIndex').each(function(index) {
				$(this).html(index + 1);
			});
		}
		
		function setHeaderTitles() {
			$('.accordionHead.ui-accordion-header.ui-helper-reset.ui-state-default.ui-accordion-icons.ui-accordion-header-active.ui-state-active.ui-corner-top .appointmentSumary').html('');
			$(".accordionHead.ui-accordion-header.ui-helper-reset.ui-state-default.ui-accordion-icons.ui-corner-all").each(function() {
				var indexClose = $(this).next('.accordionDiv:first').find('.panelIndex').val();
				var headerTitle = "";
				var testTitleSelect = $('#testTitle' + indexClose);
				if ("" != $(testTitleSelect).val()) {
					headerTitle = $(testTitleSelect).find(":selected").text();
					if ("" != $('[class=hiddenOnLoad' + indexClose +'] .dateSpan').html()) {
						headerTitle += ", " + $('[class=hiddenOnLoad' + indexClose +'] .dateSpan').html() + " " + $('[class=hiddenOnLoad' + indexClose +'] .timeSpan').html();
					}
				}
				$(this).find('.appointmentSumary').html(headerTitle);
			});
		}
		
		$(function() {
			$(".accordionDiv").each(function() {
				if ($(this).find('[id$=deliveryModeType]').val() != "" && $(this).find('[id$=deliveryModeType]').val() != "null") {
					var testTitleSelect = $(this).find('[id^=testTitle]');
					$(testTitleSelect).val($(testTitleSelect).next('input').val());
					$(testTitleSelect).change();
					$(this).find('.testTypeSpan').html($(this).find('[id$=deliveryModeDescription]').val());
					$(this).find('.dateSpan').html($(this).find('[id$=testDate]').val());
					$(this).find('.timeSpan').html($(this).find('[id$=testTimeHour]').val());
				}
			});
			
			setHeaderIndice();
			setHeaderTitles();
			
			$("#dateModal").dialog({
				modal: true,
				autoOpen: false,
				width:653,
				buttons: {
					Cancel: function() {
						$(this).dialog("close");
					},
					OK: function() {
						$("[id$=deliveryModeType]").val($("[name$=deliveryMode]").val());
						holdSeat();
					}
				}
			});
			
			$("#batteryStatusModal").dialog({
				modal: true,
				autoOpen: false,
				width:800,
				buttons: {
					OK : function() {
						$(this).dialog("close");
					},
				}
			});

			
			var currentDate = new Date();
			$("#startDateHidden").val(currentDate);
			
			addShowDatePickerHandler();
			
			$("#accordion").accordion({
				clearStyle: true,
				change: setHeaderTitles
			});
			
			$("#addAnotherAppointment").click(function() {
				var appointmentCount = $("#appointmentCount").val();
				appointmentCount++;
				if (validateAppointments()) {
					$.get("info/addAnotherAppointment", 
							  { index : appointmentCount }, 
							  function(data) { 
								  var panelCount = $("#accordion>div").size();
								  $("#accordion").append(data).accordion("destroy").accordion({
									  heightStyle: "content",
									  active: panelCount,
									  change: setHeaderTitles
									});
								  setHeaderIndice();
								  setHeaderTitles();
								  $("#appointmentCount").val(appointmentCount);
								  addShowDatePickerHandler();
							  });
				}
			});
		});

		$("[id$=testId]").each(function() {
			$(this).siblings("select").val($(this).val());
			$(this).siblings("select").change();
		});
		
		function addShowDatePickerHandler() {
			$(".showDatePicker").click(function() {
				$('#indexHidden').val($(this).find('.panelIndex').val());
				
				findSeat();
				$("#dateTimeError").html('');
				$("#dateModal").dialog("open");
				
				$("#timeList").change(function() {
					$("#lblHour").html($(this).find(":selected").text());
					$("#hourText").val($(this).find(":selected").text());
					$("#hourIndex").val($(this).val());
				});
				
				$("[name=deliveryMode]").change(function() {
					findSeat();
				});
				
			});
		}

		function getReferenceData(e, index) {
			var testId = $(e.target).val();
			if (testId == null || index == null) {
				return;
			}
			$.get("info/reference", 
					{ 
					  testId : testId,
					  index : index 
					},
					function(data) {
						$("#testType").html($(data).filter("#testType").html());
						$('[name=deliveryMode]:first').prop('checked', 'checked');
						$(e.target).parents("ul").find(".testLanguage").html($(data).filter("#testLanguage").html());
						$(e.target).parents("ul").find("[class=hiddenOnLoad" + index + "]").show();
					});
		}
		
		
		var batteryEndDate = null;
		var remainingBatteryTest = {};
		<c:if test="${batterySubscription != null }">
			batteryEndDate = new Date(<c:out value="${batterySubscription.endDate.time}" />);
		</c:if>
		
		<c:forEach items="${batteryRemainingTestTake}" var="test" varStatus="testStatus" >
			<c:if test="${test.value['NUMBER_REMAINING'] > 0 }" >
		 		remainingBatteryTest['<c:out value="${test.key.testId}" />'] = true;
		 	</c:if>
		</c:forEach>

		var programCode = '${programCode}';
		
		
		function findSeat() {
			waitForSeatResponse();
			
			var index = $('#indexHidden').val();
			var testId = $('#testTitle'+ index).val();
			var dateText = $('[class=hiddenOnLoad' + index +'] .dateSpan').html();
			
			var hourText = $('[class=hiddenOnLoad' + index +'] .timeSpan').html();
			
			if (dateText != '') {
				$("#lblDate").html(dateText);
			} else {
				$("#lblDate").html('<i>(No date selected)</i>');
			}
			if (hourText != '') {
				$("#lblHour").html(hourText);
			} else {
				$("#lblHour").html('<i>(No time selected)</i>');
			}
			$('#timeList').html('');
			
			var dteTestStartDate = new Date($("#startDateHidden").val());
			var testStartDate = $.datepicker.formatDate('mm/dd/yy', dteTestStartDate);
			var dteTestEndDate = new Date(dteTestStartDate.getFullYear(), dteTestStartDate.getMonth() + 3, 0); // last day of month + 2
			var testEndDate = $.datepicker.formatDate('mm/dd/yy', dteTestEndDate);			
			var deliveryMode = $('[name=deliveryMode]:checked').val();
			var languageCode = $("input:radio[name=appointments\\[" + index + "\\]\\.booking\\.testVariation\\.languageType]:checked").val();
			
			$.get("../findSeat", { 
				testId: testId,
				testStartDate: testStartDate,
				testEndDate: testEndDate,
				deliveryMode: deliveryMode,
				languageCode: languageCode,
				encodable: false
			}, function(data) {
				exitWaitingForSeatResponse();
				dateInfo = data;
				$("#datepicker").datepicker("destroy").datepicker({
					minDate: 0,
					defaultDate: dteTestStartDate,
					numberOfMonths: 3,
					beforeShowDay: checkDates,
					onSelect: function(dateText, inst) {
						var strDate = transformDate('yymmdd', 'mm/dd/yy', dateText);
						var testDate = $.datepicker.parseDate('mm/dd/yy', dateText);
						$("#lblDate").html($.datepicker.formatDate('MM d, yy', testDate));
						
						if(batteryEndDate != null && testDate > batteryEndDate && remainingBatteryTest[testId]){
							$("#batteryWarning").show();
						}else{
							$("#batteryWarning").hide();
						}
						$("#lblHour").html('<i>(No time selected)</i>');
						$("#timeList").empty();
						for (var h in dateInfo[strDate]['timeMap']) {
							$("#timeList").append('<option value="' + dateInfo[strDate]['timeMap'][h] + '" >' + h + '</option>');
						}
					},
					onChangeMonthYear: changeMonthYearHandler
				});
			});
		}
		
		function holdSeat() {
			waitForSeatResponse();
			
			var hourIndex = $("#hourIndex").val();
			var index = $('#indexHidden').val();
			var testId = $('#testTitle'+ index).val();
			var deliveryMode = $('input:radio[name=deliveryMode]:checked').val();
			var testDate = transformDate('yymmdd', 'MM d, yy', $('#lblDate').html());
				
//			alert(hourIndex + "\n" + index + "\n" + testCode + "\n" + deliveryMode + "\n" + testDate);
			
			$.get("../holdSeat", 
				{
					dateTimeIndex : hourIndex,
					bookingIndex : index,
					deliveryMode : deliveryMode,
					testId : testId,
					testDate : testDate
				}, function(data) {
					if ($(data).filter('#holdSeatSuccess').val() == "true") {
						var testTypeDescription = $("[name=deliveryMode]:checked").next("span:first").html();
						$('[class=hiddenOnLoad' + index +'] .testTypeSpan').html(testTypeDescription);
						$('#appointments' + index + '\\.deliveryModeDescription').val(testTypeDescription);
						var testDateString = $("#lblDate").html();
						$('[class=hiddenOnLoad' + index +'] .dateSpan').html(testDateString);
						$('#appointments' + index + '\\.testDate').val(testDateString);
						var testTimeHour = $("#hourText").val();
						$('[class=hiddenOnLoad' + index +'] .timeSpan').html(testTimeHour);
						$('#appointments' + index + '\\.testTimeHour').val(testTimeHour);
						$('#accommodationDiv' + index).html($(data).filter('#accommodationDiv').html());
						$('#appointments' + index + '\\.booking\\.deliveryModeType').val(deliveryMode);
						$("#dateModal").dialog("close");
					} else if ($(data).filter('#holdSeatSuccess').val() == "false") {
						exitWaitingForSeatResponse();
						$("#dateTimeError").html('<p style="color: red;">Sorry, the last seat for this time period has just been hold by another test taker. Please choose another time.</p>');
					} else {
						alert(data);
					}
				});
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
							$('#appointments' + index + '\\.booking\\.testCenter').parent().prev('h3').remove();
							$('#appointments' + index + '\\.booking\\.testCenter').parent().remove();
							$("#accordion").accordion("destroy").accordion({
								  heightStyle: "content",
								  change: setHeaderTitles
								});
							setHeaderTitles();
							setHeaderIndice();
						}
					});
		}
		
		function validateAppointments() {
			$('.errorMessage').remove();
			$('.errorblock').remove();
			var valid = true;
			var testTitles = [];
			var firstErrorPanel = null;
			var index = $('#indexHidden').val();
			$('.accordionDiv').each(function(i) {
				if ($(this).find('select[id^=testTitle]').val() == '') {
					$(this).find('select[id^=testTitle]').after(getErrorMessage('Please select the test title.'));
					valid = false;
				} else {
					testTitles.push($(this).find('select[id^=testTitle]').val());
				}
				if ($(this).find('input[id$=testDate]').val() == '') {
					$(this).find('input[id$=testTimeHour]').after(getErrorMessage('Please select test date and time.'));
					valid = false;
				}
				if ($(this).find('input[id$=deliveryModeDescription]').val() == '') {
					$(this).find('input[id$=testTimeHour]').after(getErrorMessage('Please select a test type.'));
					valid = false;
				}
				if ($(this).find('input[name$=languageType]:checked').length == 0) {
					$(this).find('input[name$=languageType]:last').next('span:first').after(getErrorMessage('Please select a test language.'));
					valid = false;
				}
				if ($(this).find('[name=nonaccommodation]').length > 0 && $(this).find('[name=nonaccommodation]:checked').length == 0) {
					$(this).find('#accommodationDiv' + index).append('<span class="acdnError">' + getErrorMessage('Please agree to schedule without accommodations before proceeding.') + '<span>');
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

		function changeTestCenterRedirect(){
			$( "#dialog-change" ).dialog( "close" );
	        document.forms["changeTestCenter"].submit();
		}
		
		$(function() {
			if ($('#dialog-change').length > 0) {
				$('a.changeTestCenter').on('click', function(e) {
					e.preventDefault();
					var data="Are you sure? You will lose all appointments on this screen.";			
					$("#dialog-change").html(data);
					$("#dialog-change").dialog("open");
					$("#dialog-change").dialog('option', 'width', 450);
					return false;
	
				});
			} else if ($('#changeTestCenterDiv').length > 0) {
				registerChangeTestCenterDialogEvent();
				$('a.changeTestCenter').click(function(e) {
					$('#changeTestCenterDiv').dialog('open');
				});
			}
		});
		
		function transformDate(newPattern, oldPattern, dateText) {
			return $.datepicker.formatDate(newPattern, $.datepicker.parseDate(oldPattern, dateText));
		}
		
	</script>
	
	<c:choose>
		<c:when test="${role == 'csr'}">
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
		<c:when test="${role == 'customer'}">
			<ct:modalWindow id="dialog-change" buttonName="Change" cancelButtonName="Cancel"
				buttonMethod="changeTestCenterRedirect" title="Change Test Center"></ct:modalWindow>
			<c:url value="/public/testcenter/search/${globalContextCustomer.currentProgramCode}" var = "changeTestCenterUrl"/>
			<form:form id="changeTestCenter" method="GET" action="${changeTestCenterUrl}"></form:form>
		</c:when>
	</c:choose>
			
	<div class="headContainer">
		<div class="row">
			<div class="span9"><h1>${labelScheduleNewAppointment}</h1></div>
		</div>
		<img src="${pageContext.request.contextPath}/commonweb/img/sch_appt_ftt_step_01.png">
		<br>
		<h2><spring:message code="scheduling.label.appointmentInformation" /></h2>
		<span class="required_notification">* <spring:message code="scheduling.label.requiredInformation" /></span>
		<fmt:formatDate var="formattedEndDate" value="${batterySubscription.endDate}" pattern="MMMM dd, yyyy"/>
		<h6><spring:message code="scheduling.label.testCenter"  />: <span id="testCenterName"><c:out value="${appointmentForm.testCenter.name}" /></span> ( <a class="changeTestCenter" href="#" >Change Test Center</a> )</h6>
		<span id="testCenterAddress" class="testCenterAddress"><c:out value="${appointmentForm.testCenterAddress}" /></span>
		<c:if test="${batterySubscription != null }">
			<div class="batteryMessage">
				<spring:message code="scheduling.message.batterySubscription" arguments="${batterySubscription.agency.name};${formattedEndDate};${formattedEndDate}" argumentSeparator=";"/> : 
				<c:forEach items="${batteryRemainingTestTake}" var="test" varStatus="testStatus" >
					 <c:if test="${test.value['NUMBER_REMAINING'] > 0 }" >
					 	<c:out value="${test.key.testName}" /><c:if test="${!testStatus.last}" >, </c:if>
					 </c:if>
				</c:forEach>
				<label><a href="#" onclick="$('#batteryStatusModal').dialog('open');"><spring:message code="scheduling.link.viewBatteryStatus" /></a></label>
			</div>
		</c:if>
		<c:if test="${appointmentForm.accomodationFlag}">
			<div class="batteryMessage">
				<b><c:choose>
					<c:when test="${role == 'csr'}">
						${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName}
						<ct:encode out="/secure/testtaker/accommodation/view?customerId=${appointmentForm.profile.customer.id}" var="customerAccommodationUrl"/>
					</c:when>
					<c:when test="${role == 'customer'}">
						You
						<c:url value="/secure/testtaker/accommodations/view" var="customerAccommodationUrl"/>
					</c:when>
				</c:choose></b>
				<spring:message code="scheduling.message.approvedAccommodationsAlert" arguments="${customerAccommodationUrl}"/>
			</div>
		</c:if>
	</div>
	<!-- Form Canvas starts here -->
	<!-- Start - DATEPICKER MODAL -->
	<div id="dateModal" title="Select Date and Time">
		<input type="hidden" id="indexHidden"/>
		<input type="hidden" id="startDateHidden"/>
		<div class="create_form">
			<div id="modalStyle">
				<label>Test Type:</label>
				<span id="testType"></span>
			</div>
		</div>
		<div id="batteryWarning" class="batteryMessage" style="display:none">
			<spring:message code="scheduling.message.datepicker.batterySubscription" arguments="${formattedEndDate}" argumentSeparator=";"/>
		</div>
		<ct:spinner divName="datepickerWaiting" width="200px" height="200px"></ct:spinner>
		<div id="datepicker" style="font-size:12px;"></div>
		<div class="create_form">
			<div id="modalStyle">
				<span id="dateTimeError"></span>
				<label>Date :</label><span id="lblDate"><i>(No date selected)</i></span>
				<div class="clear"></div>
				<label>Time :</label><span id="lblHour"><i>(No time selected)</i></span>
				<div class="clear"></div>
				<select id="timeList" class="hourList" size="3"></select>
			</div>
			<input type="hidden" id="hourIndex" />
			<input type="hidden" id="hourText" />
		</div>
	</div>
	<!-- End - DATEPICKER MODAL -->
	<!-- Start - BATTEY MODAL -->
	<div id="batteryStatusModal" class="batteryStatusList" title="Battery Status">
		<div align="center">
			<table style="width:100%">
				<tr>
					<th><spring:message code="scheduling.message.batteryStatus.testTitle"/></th>
					<th><spring:message code="scheduling.message.batteryStatus.remainingRetakeTitle"/></th>
					<th><spring:message code="scheduling.message.batteryStatus.appointmentNumber"/></th>
				</tr>
	 			<c:forEach items="${batteryRemainingTestTake}" var="test" >
				<tr>
					<td>${test.key.testName}</td>
					<td>${test.value['NUMBER_REMAINING']}</td>
					<c:forEach items="${test.value['BOOKINGS']}" var="booking" >
						<td>${booking.etsApptID}</td>	
					</c:forEach>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<!-- End - BATTEY MODAL -->
	<form:form method="POST" modelAttribute="appointmentForm" onsubmit="return validateAppointments();">
	<div class="formContainer">
		<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
		<div class="create_form">
			<div class="formRow">
				<form:errors path="*" cssClass="errorblock" element="div" />
				<input type="hidden" id="appointmentCount" value="${appointmentForm.appointmentCount}" />
				<div id="accordion">
					<c:forEach items="${appointmentForm.appointments}" var="appointment" varStatus="status">
						<c:set var="index" value="${status.index}"/>
						<ct:appointmentInfoPanel index="${index}" />
					</c:forEach>
				</div>
				<br/>
				<div class="row-fluid">
					<div class="ereg_span_01">
						<a id="addAnotherAppointment" style="text-decoration:none; cursor:pointer; cursor:hand;"><img style="opacity:0.7; margin:-5px 0 0 0;" onMouseOver="this.style.opacity='1.0'" onMouseOut="this.style.opacity='0.7'" src="${pageContext.request.contextPath}/commonweb/img/add.png">&nbsp;&nbsp;Add another Appointment</a>
					</div>
				</div>	
			</div>
		</div> <!--  Form Canvas ends here -->
	</div>
	<div class="formButtonsContainer">
		<a class="submit" style="margin:0 -100px 0 0;" href="<spring:url value="/secure/home" />" >Cancel</a>
		<button class="submit" type="submit" style="float:right; margin:-10px 20px 0 0;">Next &gt;</button>
	</div>
	</form:form>
</t:base>