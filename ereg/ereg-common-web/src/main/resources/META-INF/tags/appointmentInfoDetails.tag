<%@ tag language="java" pageEncoding="ISO-8859-1"
	description="Appointment Information Details"%>
<%@ attribute name="booking" required="true"
	type="org.ets.ereg.domain.booking.BookingImpl"%>
<%@ attribute name="mode" required="false"%>
<%@ attribute name="displayAppointmentConfirmation" required="false"%>
<%@ attribute name="displayTestCenterAddress" required="false"%>
<%@ attribute name="displayTestPrice" required="false"%>
<%@ attribute name="testprice" required="false"%>
<%@ attribute name="displayAppointmentLabel" required="false"%>
<%@ attribute name="customerIdStr" required="true"%>
<%@ attribute name="strikeAmt" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- TAG -- Loop Starts -->
<div class="appointmentdetailsoutsideborderradius"
	id="appointmentDetailstoPrint">
	<div class="table-row">
		<div class="left-container-ticket13" style="width: 40%">
			<p class="text">
				<b> <c:choose>
						<c:when test="${displayAppointmentLabel=='true'}">
									<spring:message code="view.appointmentdetails.appointmentlabel" />:
								</c:when>
					</c:choose> ${booking.testVariation.test.testName}
				</b>
			</p>
		</div>
		<div class="left-container-ticket23" style="width: 33%">
			<p class="text">&nbsp;</p>
		</div>
		<div class="right-container-ticket13" style="width: 25%">
			<p class="text" style="text-align: right">Status:
				${booking.getEvntOutComeStatus().getDescription()}</p>
		</div>
		<div class="space-line"></div>

		<div class="left-container-ticket13">
			<p class="text"><spring:message code="view.appointmentdetails.appointmentnumber.label" />:</p>
		</div>
		<div class="left-container-ticket23">
			<p class="text"><ct:format out="${booking.etsApptID}" /></p>			
		</div>

		<!-- Do not display for rescheduled and canceled appointments Start-->
		<%--TODO Make use of BookingStatusType but page import doesn't work in tags
				<%@ import="org.ets.ereg.domain.interfaces.model.booking.*" %>
					<c:set var="RESERVED" value="<%=BookingStatusType.RESERVED%>" />
					<c:set var="CANCELED" value="<%=BookingStatusType.CANCELED%>" />
					<c:set var="HELD" value="<%=BookingStatusType.HELD%>" />
					<c:set var="PENDING_CANCELLATION" value="<%=BookingStatusType.PENDING_CANCELLATION%>" />
					<c:set var="RELEASED" value="<%=BookingStatusType.RELEASED%>" />
					
				--%>
		<c:choose>
			<c:when
				test="${booking.getBookingStatusType().getCode() != 'CNCL' || booking.getBookingStatusType().getCode() != 'PCNCL'}">

				<div class="right-container-ticket13">
					<p class="text" style="text-align: right">

						<c:choose>
							<%-- <c:when test="${displayAppointmentConfirmation == 'true'}"> --%>

							<c:when test="${displayAppointmentConfirmation == 'true'}">
								<a
									href="<ct:encode out="/secure/orderdetails/appointmentdetails/?bookingId=${booking.id}&customerId=${customerIdStr}" />">
									<spring:message code="view.appointmentdetails.appointmentconfirmationlink.text" /> </a>
							</c:when>
						</c:choose>

					</p>
				</div>
				<div class="space-line"></div>
					<c:set var="STATE_AGENCY" value="${booking.testVariation.test.programType.code}" />			
				<div class="left-container-ticket13">
					<p class="text"><spring:message code="view.appointmentdetails.stateagency.label" />:</p>
				</div>
				<div class="left-container-ticket23">
					<p class="text">
						${booking.testCenter.getAgency(STATE_AGENCY).name}</p>
				</div>
				<!-- Do not show Reschedule link if there are accommodations except for CSR -->
				<!-- TODO get below roles from Constant file, cannot import in a tag so need to come with alternative -->
				<sec:authorize access="hasRole('PERMISSION_MODIFY_APPOINTMENT')">
					<div class="right-container-ticket13">
						<p class="text" style="text-align: right">
							<a
								href="<ct:encode out="/secure/rescheduleappointment?bookingId=${booking.id}&customerId=${customerIdStr}" />">
								<spring:message code="view.appointmentdetails.appointmentreschedulelink.text" /> </a>
						</p>
					</div>
				</sec:authorize>

				<sec:authorize access="!hasRole('PERMISSION_MODIFY_APPOINTMENT')">
					<c:if test="${empty booking.getBookingAccommodations()}">
						<div class="right-container-ticket13">
							<p class="text" style="text-align: right">
								<a
									href="<ct:encode out="/secure/rescheduleappointment?bookingId=${booking.id}&customerId=${customerIdStr}" />">
									<spring:message code="view.appointmentdetails.appointmentreschedulelink.text" /> </a>
							</p>
						</div>
					</c:if>
				</sec:authorize>
				<div class="space-line"></div>

				<div class="left-container-ticket13">
					<p class="text"><spring:message code="view.appointmentdetails.testcenter.label" />:</p>
				</div>
				<div class="left-container-ticket23">
					<p class="text">
						<a target="_blank"
							href="/ereg-web/public/testcenter/${booking.testCenter.id}/?latitudeDegree=${booking.testCenter.getFirstAddress().getLatitudeDegree().toString()}&longitudeDegree=${booking.testCenter.getFirstAddress().getLongitudeDegree().toString()}">
							${booking.testCenter.name} </a> <a target="_blank"
							href="http://maps.google.com/maps?daddr=${booking.testCenter.getFirstAddress().addressLine1}, 
							${booking.testCenter.getFirstAddress().city} ,
							${booking.testCenter.getFirstAddress().state.name}
							${booking.testCenter.getFirstAddress().postalCode},
							${booking.testCenter.getFirstAddress().country.abbreviation}">(
							Map )</a>
					</p>
				</div>
				<!-- Do not show cancel link if there are accommodations except for CSR-->
				<sec:authorize access="hasRole('PERMISSION_MODIFY_APPOINTMENT')">
					<div class="right-container-ticket13">
						<p class="text" style="text-align: right">
							<a
								href="<ct:encode out="/secure/cancelappointment?bookingId=${booking.id}&customerId=${customerIdStr}" />">
								<spring:message code="view.appointmentdetails.appointmentcancellink.text" /> </a>
						</p>
					</div>
				</sec:authorize>

				<sec:authorize access="!hasRole('PERMISSION_MODIFY_APPOINTMENT')">
					<c:if test="${empty booking.getBookingAccommodations()}">
						<div class="right-container-ticket13">
							<p class="text" style="text-align: right">
								<a
									href="<ct:encode out="/secure/cancelappointment?bookingId=${booking.id}&customerId=${customerIdStr}" />">
									<spring:message code="view.appointmentdetails.appointmentcancellink.text" /> </a>
							</p>
						</div>
					</c:if>
				</sec:authorize>

				<div class="space-line"></div>

				<!-- Test Center Address Start -->

				<c:choose>
					<c:when test="${displayTestCenterAddress == 'true'}">
						<div class="left-container-ticket13">
							<p class="text"></p>
						</div>
						<div class="left-container-ticket23">
							<p class="text"><spring:message code="view.appointmentdetails.testcenter.address.label" />:
								${booking.testCenter.getFirstAddress().addressLine1},
								${booking.testCenter.getFirstAddress().city} ,
								${booking.testCenter.getFirstAddress().state.name}
								${booking.testCenter.getFirstAddress().postalCode},
								${booking.testCenter.getFirstAddress().country.abbreviation}</p>
						</div>
						<div class="right-container-ticket13">
							<p class="text"></p>
						</div>
						<div class="space-line"></div>

						<div class="left-container-ticket13">
							<p class="text"></p>
						</div>
						<div class="left-container-ticket23">
							<p class="text"><spring:message code="view.appointmentdetails.testcenter.phone.label" />:
								${booking.testCenter.getOrganizationPhones().iterator().next().getPhone().phoneNumber}</p>
						</div>
						<div class="right-container-ticket13">
							<p class="text"></p>
						</div>
						<div class="space-line"></div>

						<div class="left-container-ticket13">
							<p class="text"></p>
						</div>
						<div class="left-container-ticket23">
							<p class="text"><spring:message code="view.appointmentdetails.testcenter.directions.label" />:
								${booking.testCenter.getSpecialOnSiteDirection()}</p>
						</div>
						<div class="right-container-ticket13">
							<p class="text"></p>
						</div>
					</c:when>
				</c:choose>
				<!-- Test Center Address ends -->
				<div class="space-line"></div>

				<div class="left-container-ticket13">
					<p class="text"><spring:message code="view.appointmentdetails.testdateandtime.label" />:</p>
				</div>
				<div class="left-container-ticket23">
					<p class="text">
						<ct:dateTime part="dateWithDayOfWeek" value="${booking.appointmentDateTime}" /> - <ct:dateTime part="time" value="${booking.appointmentDateTime}" />
					</p>
				</div>
				<div class="right-container-ticket13">
					<p class="text"></p>
				</div>
				<div class="space-line"></div>

				<div class="left-container-ticket13">
					<p class="text"></p>
				</div>
				<div class="left-container-ticket23">
						<p class="text">${booking.testVariation.deliveryModeType.description}	</p>
				</div>
				<div class="right-container-ticket13">
					<p class="text"></p>
				</div>
				<div class="space-line"></div>

				<div class="left-container-ticket13">
					<p class="text"><spring:message code="view.appointmentdetails.testlanguage.label" />:</p>
				</div>
				<div class="left-container-ticket23">
						<p class="text">${booking.testVariation.languageType.description}</p>
				</div>
				<div class="right-container-ticket13">
					<p class="text"></p>
				</div>
				<div class="space-line"></div>

				<c:if test="${not empty booking.getBookingAccommodations()}">
					<div class="left-container-ticket13">
						<p class="text"><spring:message code="view.appointmentdetails.accomodations.label" />:</p>
					</div>
					<div class="left-container-ticket23">

						<table border=2
							summary="This table shows the accommodation details information">
							<tr>
								<th align="center" id="accommodation"><spring:message code="view.appointmentdetails.accomodationstableheader1.label" /></th>
								<th align="center" id="expirationdate"><spring:message code="view.appointmentdetails.accomodationstableheader2.label" /></th>
							</tr>

							<c:forEach items="${booking.getBookingAccommodations()}"
								var="bookingAccommodation">
								<tr>
									<td align="left" headers="accommodation">${bookingAccommodation.getAccommodationType().getDescription()}</td>
									<td align="left" headers="expirationdate">
										<ct:dateTime part="date" value="${bookingAccommodation.getExpirationDate()}" />
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div class="right-container-ticket13">
						<p class="text"></p>
					</div>
					<div class="space-line"></div>
				</c:if>
			</c:when>
		</c:choose>
		<!-- Do not display for rescheduled and canceled appointments End -->
		<!-- TODO Show the Test Price displayTestPrice -->
		<c:choose>
			<c:when test="${displayTestPrice=='true'}">
				<div class="left-container-ticket13">
					<p class="text"></p>
				</div>
				<div class="left-container-ticket23">
					<p class="text">&nbsp;</p>
				</div>
				<div class="right-container-ticket13">
					<p class="text" style="text-align: right">Test Price: $
					<c:choose>
						<c:when test="${strikeAmt=='true'}">
					
								<Strike>${testprice}</Strike>	
						
						</c:when>
						<c:otherwise>
							${testprice}
						</c:otherwise>
					</c:choose>		
						
						</p>
				</div>
				<div class="space-line"></div>
			</c:when>
		</c:choose>
	</div>
</div>
<!-- TAG -- Loop Ends -->
