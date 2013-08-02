<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:base title="ETS - eREG (View Appointment)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Appointment Information of 
					<c:out value="${appointmentForm.profile.customer.firstName} ${appointmentForm.profile.customer.lastName}" />
				</h1>
			</div>
		</div>
		<br>
		<h2>Appointment Information</h2>
		<span class="required_notification"></span>
	</div>
	
	<!-- Form Canvas starts here -->
	<div class="formContainer">
		<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
		<div class="review_form">
			<div class="formRow">
				<form:form method="POST" modelAttribute="booking">
					<c:if	test="${accommodationsChanged != null && accommodationsChanged}">
						<div class="attention" >
							<spring:message code="accoChangeAttention" arguments="${appointmentForm.profile.customer.firstName}, ${appointmentForm.profile.customer.lastName}"/>
						</div>
					</c:if>
					<div class="review_form">
						<h2>Appointment Details</h2>
							<br />							
						<ul>
							<li><label><b>Appointment #:</b></label>
							<b><ct:format out="${booking.etsApptID}" /></b></li>
							<div class="clear"></div>
							<li>
								<label>Test Taker Name:</label>
								<c:out value="${booking.custFirstName} ${booking.custLastName}  " /><ct:encode out="/secure/profile/viewProfile?profileId=${appointmentForm.profile.customer.id}" hrefText="View Profile" hrefTarget="_blank"/>
							</li>
							<div class="clear"></div>
							<li>
								<label>Test Title:</label>
								<c:out value="${booking.testVariation.test.testName}" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Test Date:</label>
								<ct:dateTime part="date" value="${booking.appointmentDateTime}" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Test Time:</label>
								<ct:dateTime part="time" value="${appointment.booking.appointmentDateTime}" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Test Center:</label>
								<c:out value="${booking.testCenter.name}" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Test Type:</label>
								<c:out value="${booking.testVariation.deliveryModeType.description}" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Accommodations:</label>
								<c:forEach  var="accommodation" items="${booking.bookingAccommodations}" varStatus="status">
									<c:out value="${accommodation.accommodationType.description}"/>
									<c:if test="${null != accommodation.accommodationTypeValue}">
										<c:out value=" - ${accommodation.accommodationTypeValue.label}" />
									</c:if>
									<c:if test="${null != accommodation.othrAcmdtnTypTxt}">
										<c:out value=" - ${accommodation.othrAcmdtnTypTxt}" />
									</c:if>
									<br/>
								</c:forEach>
							</li>
							<div class="clear"></div>
							<li>
								<label>Test Language:</label>
								<c:out value="${booking.testVariation.languageType.description}" />
							</li>
							<div class="clear"></div>
							<li>
								<label>Test Form:</label>
								<span id="formDivId"><c:out value="Form ${booking.form.formCode}" /></span>
								<c:set var="CBT" value='<%=org.ets.ereg.domain.interfaces.model.common.DeliveryModeType.CBT%>'/>
								<c:set var="NCI" value='<%=org.ets.ereg.domain.interfaces.model.booking.EventOutComeStatus.NOT_CHECKED_IN%>'/>
								<c:if test="${booking.testVariation.deliveryModeType.code == CBT }">
									<c:if test="${booking.evntOutComeStatus.code == NCI  }">
										<ct:canManageTestCenter testCenterId="${booking.testCenter.id}">
											<a id="openEditFormModalButton"  class="othbutton">Edit</a>
										</ct:canManageTestCenter>
									</c:if>
								</c:if>
							</li>
							<div class="clear"></div>
							<li>
								<label>Comments:</label>
								<span id="commentDiv"><c:out value="${booking.comments}" />  </span>
								<sec:authorize access="hasRole('PERMISSION_ADD_APPOINTMENT_COMMENT')">
									<a id="openEditCommentModalButton"  class="othbutton">Edit</a>
								</sec:authorize>
							</li>
							<div class="clear"></div>
							<li>
								<label>Check In Status:</label>
								<c:out value="${booking.evntOutComeStatus.description}" />
							</li>
						</ul>
					</div>
					<!-- <div class="row-fluid">
						<div class="span6"><span class="right"><button class="submit" type="submit">Edit</button></span></div>
					</div> -->	
				</form:form>
			</div>
		</div> <!--  Form Canvas ends here -->
	</div>
	<div class="formButtonsContainer">
			<div class="row-fluid">
				<div class="ereg_span_01">
					<c:if test="${not empty backUrl}">
						<c:if test="${fn:containsIgnoreCase(backUrl, 'testtaker/view') or fn:containsIgnoreCase(backUrl, '/appointment/new/review')}">
							<a class="othbutton" href="<ct:encode out="/secure/testtaker/view/?customerId=${appointmentForm.profile.customer.id}" />">
								Go to Test Taker's Summary</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>					
					</c:if>
					
					<c:if test="${booking.evntOutComeStatus.code ne 'CHI' && hasRescheduleAccess}">
						<a class="othbutton" href="<ct:encode out="/secure/tcaScheduling/reschedule?bookingId=${booking.id}" />"><spring:message code="testtaker.summary.reschedule.link"/></a>
						<a class="othbutton" href="<ct:encode out="/secure/tcaScheduling/cancel?bookingId=${booking.id}" />"><spring:message code="testtaker.summary.cancel.link"/></a>
					</c:if>
					
					</span>
				</div>
			</div>
		</div>
		
	<ct:modalWindow id="editFormModel" buttonName="Save"
		buttonMethod="saveForm" title="Edit Form">
	</ct:modalWindow>
	<div id="editFormModelBody">
		<label>Form:</label> 
		<select id="formSelectId" required="required">
			<option value="">Select</option>
		</select>
	</div>
	
	<ct:modalWindow id="editCommentModel" buttonName="Save"
		buttonMethod="saveComment" title="Edit Comment">
	</ct:modalWindow>
	<div id="editCommentModelBody">
		<label>Comment:</label> 
		<textarea rows="4" cols="50" id="commentTAId"></textarea>
	</div>
	
	
	<script>
	
	//edit Form 
	$('#editFormModelBody').appendTo($("#editFormModel span:first"));

	$("#editFormModel").dialog({
		autoOpen : false,
	});
	
	//TODO encode all that is written later
	$("#openEditFormModalButton").click(

		function() {
			
			var varTestId = <c:out value="${booking.testVariation.test.testId}" />;
			var varTestDate = '<fmt:formatDate pattern="MM-dd-yyyy" value="${booking.appointmentDateTime}" timeZone="${booking.testCenter.globalTimeZone.code}" />';
			var testFormData = "testFormData";

			$.get('<spring:url value="/secure/tcaScheduling/getForms"/>', {
				testId: varTestId,
				testDate: varTestDate,
			}, function(data) {
				
				var xmlDoc = $.parseXML( data ),
				$xml = $( xmlDoc ),				
				$testFormData = $xml.find( testFormData );
				$("#editFormModel #formSelectId").html($testFormData.text());
				//$("#editFormModel #formSelectId").append(data);
				$("#editFormModel").dialog("open");

			});
		}
	);
	
	function saveForm() {
	
		var varBookingId = ${booking.id};
		var varFormId = $("#formSelectId").val(); 
		var varFormDesription = $("#formSelectId option:selected").text();
		
		$.get('<spring:url value="/secure/tcaScheduling/saveForm"/>', {
			bookingId: varBookingId,
			formId: varFormId
		}, function(data) {
			//No error happened
			
			$("#formDivId").html(varFormDesription);
			$("#editFormModel").dialog("close");
		});
		
	}
	
	//edit comment portion
	$('#editCommentModelBody').appendTo($("#editCommentModel span:first"));

	$("#editCommentModel").dialog({
		autoOpen : false,
	});
	
	//TODO encode all that is written later
	$("#openEditCommentModalButton").click(function() {
		$("#editCommentModel").dialog("open");
	});
	
	function saveComment() {
	
		var varBookingId = ${booking.id};
		var varComment = $("#commentTAId").val(); 
		
		$.get('<spring:url value="/secure/tcaScheduling/saveComment"/>', {
			bookingId: varBookingId,
			comment: varComment
		}, function(data) {
			
			$("#commentDiv").html($("#commentDiv").html() + '<br>' + varComment);
			$("#editCommentModel").dialog("close");
		});
		
	}

	</script>
</t:base>