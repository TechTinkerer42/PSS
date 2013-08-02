<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base title="ETS - eREG (Search for Test Takers)">
<link href='<spring:url htmlEscape="true" value="/commonweb/css/jquery-ui.css"/>' rel="stylesheet">
<script src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-ui.js"/>'></script>
<script src='<spring:url htmlEscape="true" value="/js/ereg_csr_testTakerRoster.js"/>'></script>
	<!--<script>
		$(function() {
			MaskedInput({
			  elm: document.getElementById('dateOf'),
			  format: 'MM/DD/YYYY',
			  separator: '\/',
			  typeon: 'MDY'
			});
			MaskedInput({
			  elm: document.getElementById('pho'),
			  format: '(000) 000-0000',
			  separator: '()- '
			});
		});
	</script>-->
	
<sec:authentication property="principal" var="user" scope="page" />
	
		<div class="row">
			<div class="span9"><h1><spring:message code="search.candidateSearch.title" /></h1></div>
		</div>
			

	<div class="formContainer" id="tabs">
	 <ul>
	 <li><a href="#tabs-1">
	 <spring:message code="search.candidateSearch.tab1.title" />
	  </a></li>
	<li><a href="#tabs-2">
	<spring:message code="search.candidateSearch.tab2.title" />
	</a></li>
	</ul>
	
	
	<!--<span id="errorSpan"></span>-->
	<!-- Form Canvas starts here -->
	<!-- Start - SEARCH CANDIDATE WITH ID -->
	<div id="tabs-2">
	<form:form method="post" modelAttribute="customerSearchForm" action="search" id="displaySequenceIdSearchForm" onsubmit="return false">
		<div class="toggleMap"></div><span style="padding:2px 0; margin:2px; display:inline-block; font-size: 0.91em;"><b>Search Criteria</b></span>
		<!-- <span class="required_notification"><strong> -->
		<br>
		<span><strong>
		<spring:message code="search.appointmentSearch.displayInstructions" />
		</strong></span>
				<span class="errorSpanAppointmentSearch required_notification">
	</span>
		<div class="formContainer searchCriteriaPanel">
			<div class="create_form" style="max-height: 155px;">
				<div class="formRow">
					<label for="lastName"> Last Name</label>
					<form:input path="lastName" size="30" id="lastName" required="required"/>
					<br /> <br />
					<label for="testTakerId">Test Taker ID:</label>
					<form:input path="testTakerId" size="30" id="testTakerId" maxlength="8" />
					<br /> <br />
					<label for="appointmentNumber">Appointment Number</label>
					<form:input path="appointmentNumber" cssClass="numeric-only"/>
				</div>
					<form:input type="hidden" path="pageNo"  />
					<form:input type="hidden" path="action"  />
					<form:input type="hidden" path="rowperPage"  />  
					<input type="hidden" name="formType" id="formType" value="appointmentSearch">
			</div>
		<div class="formButtonsContainer">
			<button type="submit" value="next" id="displaySequenceIdSearch"
						class="submitButton submit">Search</button>
		</div>
		</div>
	</form:form>
		<div id="displaySequenceIdSearchFormResultsTable" style="border: none;"></div>
	</div>

	
	<div id="tabs-1">
	<!-- End - SEARCH CANDIDATE WITH ID -->

	<form:form method="post" modelAttribute="customerSearchForm" action="search" id="searchForm" onsubmit="return false">
	<div class="toggleMap"></div><span style="padding:2px 0; margin:2px; display:inline-block; font-size: 0.91em;"><b>Search Criteria</b></span>
	<!-- <span class="required_notification"><strong> -->
	<br>
	<span><strong>
		<c:if test="${user.hasCSRRole()}">
		<spring:message code="search.candidateSearch.displayInstructionsCSR" />
		</c:if>
		<c:if test="${user.hasTCARole()}">
		<spring:message code="search.candidateSearch.displayInstructionsTCA" />		
		</c:if>	
	</strong></span>
	<span class="errorSpan required_notification">
	</span>
	<div class="errorDiv"></div>
		<div class="formContainer searchCriteriaPanel">
			<div class="create_form">
				<div class="formRow">
					<label for="firstName"> First Name</label>
					<form:input path="firstName" size="30" id="firstName" />
					<br>
					<label for="lastName"> Last Name</label>
					<form:input path="lastName" size="30" id="lastName" />
					<br>
					<label for="middleInitial"> Middle Initial</label>
					<form:input path="middleInitial" size="30" maxlength="1" id="middleInitial" class="smalltext"/>
					<br>
					<label for="ssn"> SSN</label>
					<form:input path="ssn" size="4" maxlength="4" id="ssn" class="smalltext"/>
					<br>
					<label for="dateOfBirth"> Date of Birth</label>
					<form:input path="dateOfBirth" size="10" maxlength="10" id="dateOfBirth" onfocus="mskDateFunction()" class="smalltext" />
					<span class="form_hint"><spring:message code="search.candidateSearch.dobHint" /></span>
					<br>
					<label for="email"> Email</label>
					<form:input path="email" size="30" id="email" />
					<br>
					<label for="country">Country</label>
					<form:select path="country">
					<form:option value=""></form:option>
						<c:forEach items="${countries}" var="country">
							<form:option value="${country.abbreviation}">${country.name}</form:option>
						</c:forEach>
					</form:select>
					<br>
					<label for="city">City</label>
					<form:input path="city" size="30" id="city" />
					<br>
					<label for="state">State</label>
					<form:select path="state">
					<form:option value=""></form:option>
						<c:forEach items="${states}" var="state">
							<form:option value="${state.abbreviation}">${state.name}</form:option>
						</c:forEach>
					</form:select>
					<br>
					<label for="postalCode">Postal Code</label>
					<form:input path="postalCode" size="30" id="postalCode" class="smalltext" />
					<br>
					<label for="phone">Phone</label>
					<form:input path="phone" size="30" id="phone" class="smalltext"/>
					<span class="form_hint"><spring:message code="search.candidateSearch.phoneHint" /></span>

				</div>
			</div>
			<div id="searching" class="hidden">
					<form:input type="hidden" path="pageNo" />
					<form:input type="hidden" path="action"  />
					<form:input type="hidden" path="rowperPage"  />
					<input type="hidden" name="formType" id="formType" value="candidateSearch">  
			</div>
					<div class="formButtonsContainer">
			<button type="submit" value="next" id="search"  class="submitButton submit">Search</button>
		</div>
		</div>
	<div id="searchFormResultsTable" style="border: none;"></div>
		
	</form:form>
	</div>
	
	
	</div>
	
	<script type="text/javascript">
	$(function() {
	$('.numeric-only').live('keypress',function(e){
		var k = e.which;
	    /* numeric inputs can come from the keypad or the numeric row at the top */
	    if ( (k < 48 || k > 57)) {
	        e.preventDefault();
	        return false;
	    }
	});
	});
	
	 $(function() {
		 $( "#tabs" ).tabs({
		 collapsible: true
		 });
		 });
	
	</script>

</t:base>
		
		
		
		

		
		
		
		