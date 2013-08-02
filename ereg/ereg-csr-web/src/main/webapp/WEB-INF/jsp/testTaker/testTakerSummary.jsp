<%@ page import="org.ets.ereg.common.web.scheduling.form.AppointmentForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS - eREG (Test Taker Summary)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>
					<spring:message code="testtaker.summary.display"/> <c:out value="${firstName} ${lastName}" />
				</h1>
				<p style="color:red; margin:10px 0 0 0;">${STATUS_MESSAGE}</p>		
			</div>
		</div>
	</div>	
	
	<!-- homeContainer Starts -->
	<div id="homeContainer">
		<!-- Column1 Starts -->
		<div class="column1">
			<div class="block">
				<h3>
					<c:out value="${firstName} ${lastName}" />
				</h3>
				<spring:message code="testtaker.summary.testTakerID" />
				# ${testTakerId}
				<div class="content">
					<ul>
						<sec:authorize access="hasRole('PERMISSION_UPDATE_CUSTOMER')">
							<li><ct:encode out="/secure/profile/editCustomerProfile?customerId=${customerIdStr}" encodable = "true" hrefCode="testtaker.summary.personal.link.display"/></li>
						</sec:authorize> 
						<sec:authorize access="hasRole('PERMISSION_VIEW_BIQ')">
							<li><ct:encode out="/secure/testtaker/viewBackground?customerId=${customerIdStr}" hrefCode="testtaker.summary.backgroundinfo.display"/></li>
						</sec:authorize>
						<sec:authorize access="hasRole('PERMISSION_RESET_PASSWORD')">
						<li>
							<ct:encode out='${candidatePasswordChng}?customerId=${customerIdStr}' hrefCode="testtaker.summary.changepassword.link.display"/>
						</li>

						</sec:authorize>
						<sec:authorize access="hasRole('PERMISSION_VIEW_ACCOMMODATION')">
							<li><ct:encode out="/secure/testtaker/accommodation/view?customerId=${customerIdStr}" hrefText="Accommodations" /></li>
						</sec:authorize>
					</ul>
				</div>
			</div>
			<jsp:include page="../common/myResources.jsp"></jsp:include>
		</div>
		<!-- Column1 Ends -->
		<!-- Column2 Starts -->
		<div class="column2">
			<div class="block">

				<h3>
					<spring:message code="testtaker.summary.tests" arguments="HiSET" />
				</h3>
				<div class="content">
					<span class="homePageh2"><spring:message
							code="testtaker.summary.uncomingTest.header"
							arguments="${appointments.size()}" /></span>


					<c:if test="${empty appointments }">
						<spring:message code="testtaker.summary.no.upcoming.test" />
					</c:if>
					<c:if test="${not empty appointments }">
						<table class="accommodationSummery">
							<thead>
								<tr>
									<th><spring:message
											code="testtaker.summary.futuretest.test.appt.number" /></th>
									<th><spring:message
											code="testtaker.summary.futuretest.test.title" /></th>
									<th><spring:message
											code="testtaker.summary.futuretest.test.center" /></th>
									<th><spring:message
											code="testtaker.summary.futuretest.test.date" /></th>
									<th><spring:message
											code="testtaker.summary.futuretest.test.time" /></th>
									<th><spring:message
											code="testtaker.summary.futuretest.test.type" /></th>
									<th><spring:message
											code="testtaker.summary.futuretest.test.language" /></th>
									<th><spring:message
											code="testtaker.summary.futuretest.test.action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${appointments}" var="appointment"
									varStatus="status">
									<tr>
										<td><a
											href="<ct:encode out="/secure/tcaScheduling/view/?bookingId=${appointment.booking.id}" />">
												<ct:format out="${appointment.booking.etsApptID}" />
										</a></td>
										<td><c:out
												value="${appointment.booking.testVariation.test.testName}" /></td>
										<td><c:out value="${appointment.booking.testCenter.name}" />
										</td>
										<td>
											<ct:dateTime part="date" value="${appointment.booking.appointmentDateTime}" />
										</td>
										<td>
											
											<ct:dateTime part="time" value="${appointment.booking.appointmentDateTime}" />

											</td>
										<td>
											<c:out value="${appointment.booking.testVariation.deliveryModeType.description}" />	
										</td>
										<td>
											<c:choose>
												<c:when test="${appointment.booking.form != null }">	
													<c:out value="${appointment.booking.form.formDesc}" />
												</c:when>
												<c:otherwise>
													<c:out value="${appointment.booking.testVariation.languageType.description}"/>	
												</c:otherwise>
											</c:choose>	
										</td>
										<td><c:if test="${appointment.editableFlag}">
												<c:if
													test="${appointment.booking.evntOutComeStatus.code ne 'CHI'}">
													<spring:url value="/secure/tcaScheduling/reschedule?bookingId=${appointment.booking.id}&encodable=false" var="reschedule_url"></spring:url>
													<%-- <ct:encode out="/secure/tcaScheduling/reschedule?bookingId=${appointment.booking.id}" var="reschedule_url" /> --%>
													<a
														href="${reschedule_url}"><spring:message
															code="testtaker.summary.reschedule.link" /></a>
													<a
														href="<ct:encode out="/secure/tcaScheduling/cancel?bookingId=${appointment.booking.id}" />"><spring:message
															code="testtaker.summary.cancel.link" /></a>
												</c:if>
												<a
													onclick="alert('This functionality is under development.');"><spring:message
														code="testtaker.summary.checkin.link" /></a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
				<%--  <div style="width:97%; margin:0 auto; border:1px dotted #ccc; height:100px; text-align:center; padding-top:50px;"><spring:message code="home.testhistory.heading"/></div>  --%>
				<br>
				<div class="content">
					<a href=""><spring:message code="testtaker.summary.allTests"
						arguments="HiSET" /></a> <br> <br> 
					<c:if test="${canScheduleAppointment}">
						<%-- <a class="submit" href="<ct:encode out="/secure/tcaScheduling/newappointment?customerId=${customerIdStr}&testCenterId=1" />"><spring:message code="testtaker.summary.appt.link"/></a>--%>						
						<a id="scheduleAppointmentButton" class="submit" href=""><spring:message code="testtaker.summary.appt.link" /></a>
					</c:if>
				</div>
			</div>

			<div class="block">
				<h3>
					<spring:message code="testtaker.summary.findTestCenter.header" />
				</h3>
				<div class="content">
					<ul>
						<lh>
						<span class="homePageh2">Test Centers</span></lh>
						<lh>
						<form id="searchFormHome" method="get"
							action="TBD/ereg-web/public/testcenter/search/hse">
							<input type="text" name="cityStateOrZipCode" width="150" /> <input
								type="hidden" name="latitudeDegree" id="latitudeDegree">
							<input type="hidden" name="longitudeDegree" id="longitudeDegree">
							<button class="submit" type="button" id="searchButtonHome">Search</button>
						</form>
						</lh>
						<li><a href="<c:url value='/secure/customersearch/search' />"><spring:message
									code="testtaker.summary.findTestCenter" /></a><br></li>
					</ul>
				</div>
			</div>
			<div class="block">
				<h3><spring:message code="home.orders.header" arguments="HiSET"/></h3>
					<div class="content">
						<ul>
							<li>
									<a href="<ct:encode out='/secure/viewordersearch/search?customerId=${customerIdStr}' />"><spring:message code="home.orders"/></a><br>
							</li>
						</ul>
					</div>	
			</div>
			<div class="block">
				<h3>
					<spring:message code="testtaker.summary.scores.header" />
				</h3>
				<div class="content">
					<a href="<c:url value='' />"><spring:message
							code="testtaker.summary.scores" /></a><br>
				</div>
			</div>
		</div>
		<!-- Column2 Ends -->
		<a class="submit"
			href="<spring:url value="${TestTakerSummary.backButtonLink} "/>"><spring:message
				code="testtaker.summary.back.button" /></a>
	</div>

	<!------------Select Test Center Model Start------------>
	<ct:modalWindow id="selectTCModal" buttonName="Submit"
		buttonMethod="scheduleAppointment" title="Select Test Center">
	</ct:modalWindow>
	
	<div id="selectTestCentersDiv">
		<form id="scheduleAppointmentFormId" method="post" action="<spring:url value="/secure/tcaScheduling/new"/>">
			<sec:authorize access="hasRole('PERMISSION_SCHEDULE_ALL_AGENCIES')">
				<label>State/Agency:</label> 
				<select id="agencySelectId" onchange="getTestCenters($(this).val());" required="required">
					<option value="">Select</option>
				</select>
			</sec:authorize>
			<input type="hidden" name="customerId" value="${customerIdStr}"/>
			<label>Test Center:</label> 
			<select id="testCenterSelectId" name="testCenterId" required="required"></select>
		</form>
	</div>

	<script>
		$('#selectTestCentersDiv').appendTo($("#selectTCModal span:first"));

		$("#selectTCModal").dialog({
			autoOpen : false,
		});

		$('#scheduleAppointmentButton').click(function(e) {

			e.preventDefault();
			<sec:authorize access="hasRole('PERMISSION_SCHEDULE_ALL_AGENCIES')"> 
				$.get('<spring:url value="/secure/tcaScheduling/getAllAgencies"/>', {
	
				}, function(data) {
					$("#selectTCModal #agencySelectId").html('<option value="">Select</option>');
					$("#selectTCModal #agencySelectId").append(data);
					$("#selectTCModal").dialog("open");
	
				});
				
			</sec:authorize>
			<sec:authorize access="!hasRole('PERMISSION_SCHEDULE_ALL_AGENCIES')">
				$.get('<spring:url value="/secure/tcaScheduling/tcaTestCenters"/>', {
					
				}, function(data) {
					
					var optionCount = data.match(/<option/g);  
					
					if(optionCount.length == 0){
						alert("No Test Center is assigned to you.");									
					}else if(optionCount.length == 1){
						$("#selectTCModal #testCenterSelectId").append(data);
						scheduleAppointment();				
					}else{
						$("#selectTCModal #testCenterSelectId").html('<option value="">Select</option>');
						$("#selectTCModal #testCenterSelectId").append(data);
						$("#selectTCModal").dialog("open");
					}
	
				});

			</sec:authorize>
		});

		function getTestCenters(agencyId) {
			
			if (agencyId == "") {
				return;
			}
			
			$.get(
				'<spring:url value="/secure/tcaScheduling/new/info/getTestCenters"/>',
				{
					agencyId : agencyId
				}, function(data) {
					
					var testCenterSelect = $("#testCenterSelectId");
					$(testCenterSelect).html('<option value="">Select</option>');
					$(testCenterSelect).append(data);
				});
		}
		
		function scheduleAppointment(testCenterId) {
			
			var form = $("#scheduleAppointmentFormId");
		    form.submit();		    
		}
	</script>

	<!------------Select Test Center Model End------------>
	

	<!-- homeContainer Ends -->
</t:base>