<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>




	<div class = "formContainer">
			<c:if test="${empty customerResultVO.results}">
		
			
			  No Results Found 
			  
			
			</c:if>
			
			

<c:if test="${not empty customerResultVO.results}">
<fieldset>
	<div class="create_form" tabindex="0" id="resultsPage">
	
	
	<ct:paginationheader currentPage="${customerResultVO.currentPage}"	pages="${customerResultVO.pages}" totalRecordCount="${customerResultVO.totalRecordCount}" rowperPage="${customerSearchForm.rowperPage}" 	totalsPages="${customerResultVO.totalsPages}"></ct:paginationheader>
	
	</div>

	<table id="searchResultsTable"  style="width:100%">
	<thead>
<tr>
<th></th>
<th id="name">Name</th>
<th id="dateOfBirth">Date of Birth</th>
<sec:authorize access="!hasRole('ROLE_TEST_CENTER_ADMIN')"><th id="socialSecurity">SSN</th></sec:authorize>
<th id="address">Address</th>
<th id="accountStatus">Status</th>
</tr>
</thead>
		<tbody>
			
			<c:forEach items="${customerResultVO.results}" var="customerSearchResultVO" varStatus="i">

				<tr>					




					<td>${(i.count) + ( (customerResultVO.currentPage -1) * customerResultVO.rowsPerPage)}</td>
					<td style="width: 15%;">
                    <a href="<ct:encode out="/secure/testtaker/view/?customerId=${customerSearchResultVO.candidateId }" />"> <c:out value="${customerSearchResultVO.lastName}, ${customerSearchResultVO.firstName}  ${customerSearchResultVO.middleIntial}" /></a>
                    </td>
                    <td> 
                      <ct:dateTime part="date" value="${customerSearchResultVO.dateOfBirth}" />			
					</td>
					<sec:authorize access="!hasRole('ROLE_TEST_CENTER_ADMIN')">
					<td >
					<c:out value="${customerSearchResultVO.socialSecurity}"/>
					</td>
					</sec:authorize>
					<td>
					<c:out value="${customerSearchResultVO.addressLine1}"/>,<br>
					<c:out value="${customerSearchResultVO.city}"/>,<br>
					<c:out value="${customerSearchResultVO.state}"/>-<c:out value="${customerSearchResultVO.postalCode}"/>
					</td>
					<td>
					<c:choose>
					<c:when test="${customerSearchResultVO.deactivated==1}">
					In Active
					</c:when>
					<c:otherwise>
					Active
					</c:otherwise>
					</c:choose>
					</td>
                    
					

				</tr>

			</c:forEach>



		</tbody>
	</table>


	
	<ct:paginationfooter currentPage="${customerResultVO.currentPage}"	pages="${customerResultVO.pages}"	totalRecordCount="${customerResultVO.totalRecordCount}"	totalsPages="${customerResultVO.totalsPages}"></ct:paginationfooter>
	

</fieldset>
</c:if>
 </div>