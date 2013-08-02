<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@attribute name="testId" required="true"%>
<%@attribute name="backURL" required="true"%>
<%@attribute name="formDeliveryMode" required="true"%>
<%@attribute name="formStatus" required="true"%>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>


<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url value="${action}" var="action_url"/>

<c:url value="${view_action_url}" var="view_action_url"/>

 <script> 

  $(document).ready(function()
  {

 
	 $("table.accomodationTable tr").filter(function() { 
		 <sec:authorize access="hasRole('PERMISSION_MODIFY_ACCOMMODATION')">
		 return $(this).children("td").length == 7;
		 </sec:authorize>
		 <sec:authorize access="!hasRole('PERMISSION_MODIFY_ACCOMMODATION')">
		 	return $(this).children("td").length == 6;
		 </sec:authorize>
		}).filter(':odd').addClass('alt');

		 $("tr.alt td[rowspan]").each(function() {
		  $(this).parent().nextAll().slice(0, this.rowSpan - 1).addClass('alt');
		}); 

	 
	 
	  
	  //for table row
	/* $("tr:even").css("background-color", "#DAEDF5");
	  $("tr:odd").css("background-color", "#fff"); */
  });
  </script>

 <c:url value="/secure/testtaker/accommodation/add/popUp" var="add_url"/>
 <c:url value="/secure/testtaker/accommodation/viewBulkEdit" var="edit_url"/>    	
 
<form:form  id="accommodationForm" method="post" modelAttribute="viewAccommodationForm" >
<div class="accomodationTest">

<div class="accomodationFilter">


<div class="title">

<span><label for="testTitle"><spring:message code="accommodations.view.filter.label.TestTitle"/></label>
	<form:select path="${testId}"  onchange="callSubmit(this.form, '${view_action_url}',true)" >
		<form:option value=""><spring:message code="accommodations.view.testtitle.allTests"/></form:option>
		<c:forEach items="${tests}" var="test">
			<form:option value="${test.testId}">${test.testName}</form:option>
		</c:forEach>
	</form:select>
</span>
</div>
<div class="type">
	<span><label for="deliveryMode"><spring:message code="accommodations.view.filter.label.deliveryMode"/></label>
	<form:select path="${formDeliveryMode}" onChange="callSubmit(this.form,'${view_action_url}',true)">
		<form:option value=""><spring:message code="accommodations.view.allDeliveryMode"/></form:option>
		<c:forEach items="${testTypes}" var="testType">
			<form:option value="${testType.code}">${testType.description}</form:option>
		</c:forEach>
	</form:select>
	</span>
	</div>
	<div class="status">
	<span>
<label for="accommodationStatus"><spring:message code="accommodations.view.filter.label.accommodation.status"/></label>
	<form:select path="${formStatus}" onChange="callSubmit(this.form,'${view_action_url}',true)">
		<form:option value=""><spring:message code="accommodations.view.allStatus"/></form:option>
		<c:forEach items="${accommodationStatus}" var="status">
			<form:option value="${status.code}">${status.label}</form:option>
		</c:forEach>
	</form:select>
	
	</span>
	</div>
  	<span style="float:left;">

  	<span style="float:left;">
 		 <input  name="customerId" type="hidden" value="${customerId}"/>

	<%-- <button class="submit" type="submit" onclick="callSubmit('${}')"><spring:message code="accommodations.view.button.filter"/></button> --%>
	</span>
	
	</div>
	
	<div class="clear"></div>

<%-- </form:form> --%>
	 <c:set var="flag" value="true" />
					<c:if test="${empty accommodations}">
					
					<div class="noAccommodation-Block">
						<spring:message code="testtaker.accommodation.message"/>
						</div>
					</c:if>
					<c:if test="${not empty accommodations}"> 			
						<table class="accomodationTable">
							<thead>
								<tr>
									<th><spring:message code="accommodations.view.table.heading.testTitle"/></th>
									<th><spring:message code="accommodations.view.table.heading.testType"/></th>
									<th><spring:message code="accommodations.view.table.heading.accommodations"/></th>
									<th><spring:message code="accommodations.view.table.heading.lastModified"/></th>
									<th><spring:message code="accommodations.view.table.heading.expirationDate"/></th>
									<th><spring:message code="accommodations.view.table.heading.status"/></th>
									<sec:authorize access="hasRole('PERMISSION_MODIFY_ACCOMMODATION')">
									<th><spring:message code="accommodations.view.table.heading.action"/></th>
									</sec:authorize>
									
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${accommodations}" var="title" varStatus="i" >
							<c:if test="${true==flag}">	<tr> <c:set var="flag" value="false" /> </c:if>															
								<td rowspan="${title.testAccommodationCount}">${title.testTitle}</td>																
								<c:forEach items="${title.customerDeliveryMethodAccommodations}" var="delivery" varStatus="j" >
								 <c:if test="${true==flag}">	<tr> <c:set var="flag" value="false" /> </c:if>	
									<td rowspan="${delivery.accommodationCount}"><span class="${delivery.deliveryMethodCode}">${delivery.deliveryMethod}</span></td> 
									<c:forEach items="${delivery.customerAccommodations}" var="accmd" varStatus="k" >
										<c:if test="${true==flag}">	<tr> <c:set var="flag" value="false" /></c:if>
										<td>${accmd.accommodation.description} <c:if test="${ not empty accmd.accommodation.otherValueText}">(${accmd.accommodation.otherValueText})</c:if><c:if test="${ not empty accmd.accommodation.valueText}">(${accmd.accommodation.valueText})</c:if></td>
										<td><ct:dateTime part="date" value="${accmd.approvalDate}" /></td>
										<td><ct:dateTime part="date" value="${accmd.expirationDate}" /></td>										
										<td><c:if test="${accmd.activeAccommodation}"><span class="active"><spring:message code="accommodations.view.table.label.active"/></span></c:if><c:if test="${not accmd.activeAccommodation}"><span class="expired"><spring:message code="accommodations.view.table.label.expired"/></span></c:if></td>
										<sec:authorize access="hasRole('PERMISSION_MODIFY_ACCOMMODATION')">
																				
											<td ><a class="editModalWindow" href="<ct:encode out="/secure/testtaker/accommodation/edit/popUp?customerId=${customerId}&testId=${accmd.testId}&deliveryMode=${accmd.deliveryMethodCode}&accommodationTypeCode=${accmd.accommodation.accommodationTypeCode}" />"><spring:message code="accommodations.view.table.action.edit"/></a>									
										
										</sec:authorize>			
										<sec:authorize access="hasRole('PERMISSION_DELETE_ACCOMMODATION')">										 
										 	<a class="removeModalWindow" href="<ct:encode out="/secure/testtaker/accommodation/remove/popUp?customerId=${customerId}&testId=${accmd.testId}&deliveryMode=${accmd.deliveryMethodCode}&accommodationTypeCode=${accmd.accommodation.accommodationTypeCode}" />"><spring:message code="accommodations.view.table.action.remove"/></a>									
										 </td>
										</sec:authorize>
									</tr>
									<c:set var="flag" value="true" />
									
									</c:forEach>			
					
								</c:forEach>
							
							
							
							</c:forEach>
							</tbody>
							</table>
							</c:if>
	




<div class="clear"></div>
	<div class="accomodationBottom">
	<div class="leftDiv">
	<%-- <ct:encode out='/secure/testtaker/view?customerId=${customerId}'/> --%>
			<a class="submit" href="javascript:history.back();"><spring:message code="button.label.back"/></a>
	</div>
	
			<div class="rightDiv">
				<sec:authorize access="hasRole('PERMISSION_ADD_ACCOMMODATION')">
				<button  type="button" name="submitButton" value="Add Accommodations" class="submit modalWindow" onClick="callSubmit(this.form,'${add_url}',false)"><spring:message code="accommodations.view.table.action.addAccommodations"/></button>
				</sec:authorize>
				<sec:authorize access="hasRole('PERMISSION_MODIFY_ACCOMMODATION')">
				<button type="submit" class="submit" name="submitButton" value="Edit Accommodations"   onClick="callSubmit(this.form,'${edit_url}',true)"><spring:message code="accommodations.view.table.action.editAccommodations"/></button>
				</sec:authorize>			
			</div>
		</div>
			
</form:form>

	
</div>

			<div class="clear"></div>
					