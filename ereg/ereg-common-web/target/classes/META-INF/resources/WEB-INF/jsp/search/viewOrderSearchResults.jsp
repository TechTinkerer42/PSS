<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>


<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>


<div class="headContainer">

	<c:if test="${empty viewOrderResult.results  && orderHistory eq  'Y'}">
		<p>
			<c:choose>
				<c:when test="${selectedViewOrder == 'RECENTORDER'}">
					<label>[Placeholder instructions. You have no order placed
						in Last 6 Months.To view orders from another year,select from the
						dropdown list above.]</label>

				</c:when>
				<c:otherwise>
					<label>[Placeholder instructions. You have no order placed
						in ${selectedViewOrder}.To view orders from another year,select
						from the dropdown list above.]</label>

				</c:otherwise>
			</c:choose>
		</p>



	</c:if>


	<c:if test="${not empty viewOrderResult.results}">

		<p>
			<label>[ Placeholder instructions. Click order number to view
				order details. ]</label>
		</p>
		<fieldset>
			<div class="create_form" tabindex="0" id="resultsPage">

				<%-- 	<ct:paginationheader currentPage="${viewOrderResult.currentPage}"
					pages="${viewOrderResult.pages}"
					totalRecordCount="${viewOrderResult.totalRecordCount}"
					rowperPage="${viewOrderSearchForm.rowperPage}"
					totalsPages="${viewOrderResult.totalsPages}"></ct:paginationheader>
 --%>

			</div>




			<table id="searchResultsTable" class="viewOrderTable">
				<thead>
					<tr>
						<th id="OrderNumber">Order Number</th>
						<th id="OderDate">Order Date</th>
						<th id="Items">Item(s)</th>
						<th id="TotalPrice">Total Price</th>

					</tr>
				</thead>
				<tbody>
					<form:form id="changeStatus" method="POST"
						modelAttribute="submitBookingForm" action="chgStatusAppForm"
						onsubmit="return false;">
					</form:form>

					<c:forEach items="${viewOrderResult.results}"
						var="viewOrderSearchResultVO" varStatus="i">
						<tr>
							<td><a
								href="<ct:encode out='/secure/orderdetails/view?orderNumber=${viewOrderSearchResultVO.orderNumber}&customerId=${customerIdStr}'/>">
									${viewOrderSearchResultVO.orderNumber} </a></td>
							<td>${viewOrderSearchResultVO.formattedOrderDate}</td>
							<td><c:forEach
									items="${viewOrderSearchResultVO.listAppointment}"
									var="appointment" varStatus="j">
									<b>Appointment:${appointment.testName}</b>
									</br>
						Test Date and Time:${appointment.appointmentDate} <br />
								</c:forEach></td>
							<td class="text" style="text-align: center">$${viewOrderSearchResultVO.orderTotal}</td>
						</tr>

					</c:forEach>



				</tbody>
			</table>

			<%-- 	<ct:paginationfooter currentPage="${viewOrderResult.currentPage}"
				pages="${viewOrderResult.pages}"
				totalRecordCount="${viewOrderResult.totalRecordCount}"
				totalsPages="${viewOrderResult.totalsPages}"></ct:paginationfooter>

 --%>

		</fieldset>

	</c:if>
</div>
