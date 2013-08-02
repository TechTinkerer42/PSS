<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<appointmentData> 
	<c:if test="${appointmentForm.appointments[index].booking.bookingAccommodations != null && appointmentForm.appointments[index].booking.bookingAccommodations.size()>0}">

	
		<testAccommodationData><![CDATA[
			<table id="accommodationTable" border="1px">
				<thead>
					<tr>
						<th>${deliveryMode} Accommodation</th>
						<th>Expiration Date</th>
					</tr>
				</thead>
				<c:forEach var="accommodation"
					items="${appointmentForm.appointments[index].booking.bookingAccommodations}"
					varStatus="status">
					<tr>
						<td id="accommodationDesc${status.index}">${accommodation.accommodationType.description}
							<c:if test="${null != accommodation.accommodationTypeValue}">
								<c:out value=" - ${accommodation.accommodationTypeValue.label}" />
							</c:if> <c:if test="${null != accommodation.othrAcmdtnTypTxt}">
								<c:out value=" - ${accommodation.othrAcmdtnTypTxt}" />
							</c:if>
		
						</td>
						<td><ct:dateTime part="date"
								value="${accommodation.expirationDate}" /></td>
					</tr>
				</c:forEach>
			</table>	]]>
		</testAccommodationData>

	</c:if> 

	<c:if test="${availableTestForms != null && availableTestForms.size()>0}">
		<testFormData> <![CDATA[
			<c:forEach items="${availableTestForms}" var="testForm" varStatus="status">
				<option value="${testForm.formID}">Form ${testForm.formCode}</option>
			</c:forEach> 
		]]>
		</testFormData>
	</c:if>
	<c:if test="${languages != null && languages.size()>0}">
		<testLanguageData><![CDATA[
			<c:forEach items="${languages}" var="language">
				<form:radiobutton path="appointmentForm.appointments[${index}].booking.testVariation.id.languageCode" value="${language.code}" class="radiocheck" required="required"/><span>${language.description}</span> 
			</c:forEach>]]>
		</testLanguageData>
	</c:if>	
	<c:if test="${deliveryModes != null && deliveryModes.size()>0}">
		<testDeliveryModeData><![CDATA[
			<c:forEach items="${deliveryModes}" var="deliveryMode">
				<form:radiobutton path="appointmentForm.appointments[${index}].booking.testVariation.id.deliveryModeCode" value="${deliveryMode.code}" class="radiocheck" onclick="getAppointmentData(${index}, testAccommodationData );showHideDeliveryModeDateTimeDependentElements(${index});showHideForm(${index})" required="required"/><span>${deliveryMode.description}</span> 
			</c:forEach>]]>
		</testDeliveryModeData>
	</c:if>	
	
 </appointmentData>



