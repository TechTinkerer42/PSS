<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

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