function changeTestCenter(testCenterId) {
	$.getJSON('info/changeTestCenter',
		{ testCenterId : testCenterId },
		function(data) {
			switch(data.action) {
				case "update":
					$('#testCenterName').html(data.testCenterName);
					$('#testCenterAddress').html(data.address);
					break;
				case "refresh":
					window.location.reload();
					break;
				case "redirectToTCA":
					window.location = "../../tcaScheduling/new/info";
					break;
				case "redirectToCM":
					window.location.href = "../../appointment/beforeSchedule?testCenterId=" + testCenterId+"&skipProfile=true";
			}
		});
}

function getTestCenters(agencyId) {
	if (agencyId == "") {
		return;
	}
	$.get("info/getTestCenters", 
		{ agencyId: agencyId }, 
		function(data) {
			$('#testCenterSelect').html("<option value=''>Select</option>" + data);
		});
}

function checkDates(date) {
	var strDate = $.datepicker.formatDate('yymmdd', date);
	if (dateInfo[strDate] == null) {
		return [false, ""];
	} else if (typeof dateInfo[strDate]['nonAvailabilityText'] == "string") {
		return [false, "", dateInfo[strDate]['nonAvailabilityText']];
	} else {
		return [true, ""];
	}
}

function changeMonthYearHandler(year, month, inst) {
	var newStartDate = new Date($('#startDateHidden').val());
	newStartDate.setMonth(month - 1);
	newStartDate.setYear(year);
	newStartDate.setDate(1);
	var currentDate = new Date();
	if (currentDate > newStartDate) {
		newStartDate = currentDate;
	}
	$('#startDateHidden').val(newStartDate);
	findSeat();
}

function transformDate(newPattern, oldPattern, dateText) {
	return $.datepicker.formatDate(newPattern, $.datepicker.parseDate(oldPattern, dateText));
}

function waitForSeatResponse() {
	$('#datepicker').hide();
	$('#datepickerWaiting').show();
}

function exitWaitingForSeatResponse() {
	$('#datepicker').show();
	$('#datepickerWaiting').hide();
}

function registerChangeTestCenterDialogEvent() {
	$('#changeTestCenterDiv').dialog({
		modal: true,
		autoOpen: false,
		width:300,
		title: "Change Test Center",
		buttons: {
			"Change": function() {
				changeTestCenter($('#testCenterSelect').val());
				$(this).dialog("close");
			},
			Cancel: function() {
				$(this).dialog("close");
			}
		}
	});
}