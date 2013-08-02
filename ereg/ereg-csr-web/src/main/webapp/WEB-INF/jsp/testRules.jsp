<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>


<t:base title="Test Rules">

	<script>
	
	$(document).ready(function() {
		
		
		$(".submitButton").live("click", function() {
			var form=$('form#searchForm').serialize();
		    var formAction = $("#searchForm").attr("action");
			 $.ajax({
			        url: formAction,
			        type: 'post',
			        dataType: 'json',
			        data: form,
			        success: function(data) {
			        	if(data.length>0){
			        		output = "<tbody>";
			        		for (i in data) {
			        			output += '<tr>';
			        			output += '<td>';
			        			output += data[i].formattedAppliedDate
			        			output += '</td>';
			        			output += '<td>';
			        			output += data[i].conflictRuleCode
			        			output += '</td>';
			        			output += '</tr>';
			        		}
			        		output += "</tbody>";
			        		 $("#searchResultsTable tbody").remove();
			        		 $("#searchResultsTable thead").after(output);
			        	}
			         },
			      });

		});
	});
		
	</script>
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Rules Test</h1>
			</div>
		</div>
	</div>

	<form:form method="post" modelAttribute="testRulesForm" action="search"
		id="searchForm" onsubmit="return false">
		<div class="formContainer">
			<div class="create_form">
			     <label for="testTakerId">Customer Id:</label>
			     <form:input path="customerId" size="10" id="customerId" />
			     <div class="clear"></div>
			      <label for="testCode">Test Code:</label>
			     <form:input path="testCode" size="20" id="testCode" />
			     <div class="clear"></div>
				<label for="startDate">Start Date:</label>
				<ct:datepicker name="startDate"> </ct:datepicker> 
				<div class="clear"></div>
			
				<label for="endDate">End Date:</label>
			<ct:datepicker name="endDate" readonly="true"> </ct:datepicker> 
						<div class="clear"></div>
					
					<div>
						<button type="submit" value="next" id="search"
							class="submitButton submit">Search</button>
					</div>
					
					
			</div>
		</div>	
					
       <table id="searchResultsTable" class="test">
				<thead>
					<tr>
						<th id="date">Applied Date</th>
						<th id="rule">Conflict Rule code</th>
				
					</tr>
				</thead>
			</table>

	</form:form>
</t:base>

