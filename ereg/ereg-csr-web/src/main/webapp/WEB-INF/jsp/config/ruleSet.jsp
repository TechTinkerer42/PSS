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

<form:form method="post" modelAttribute="configurationsForm" action="configurations/getConfigurationForHierarchy"  onsubmit="return false">
	<div class="headContainer ">
		<div class="row">
			<div class="span9"><h1>Rules/Parameters</h1></div>
		</div>
		<br>
		<c:forEach items="${hierarchys}" var="hierarchy" varStatus="loopstatus">
			<c:if test="${hierarchy.eregHierarchyIdentifier eq  configurationsForm.hierarchy}">
				<h3>Hierarchy Level: <a href=<c:url value='/secure/configurations?hierarchyId=${configurationsForm.hierarchy}'/>><c:out value="${hierarchy.hierarchyName}"></c:out></a></h3>
			</c:if>
		</c:forEach>
		<h3>Rule Set Category: <span class="nonLink">${ruleSetType}</span></h3>
		<div class="create_form">
			<div class="formRow" style="margin:8px -6px -31px;">
				<label id="configRole">Role:</label>
				<form:select path="roleName">
					<form:option value="">Please select a Role</form:option>
					<form:options items="${roleEnumList}" itemLabel="description" itemValue="code"></form:options>
				</form:select>
			</div>
		</div>
	</div>
	<!-- Form Canvas starts here -->
	<div class="formContainer">
		<div class="create_form">
			<div class="formRow">
				<input type="hidden" name="hierarchy" value="${configurationsForm.hierarchy}" id="hierarchy"/>
				<input type="hidden" name="ruleSetType" value="${configurationsForm.ruleSetType}"/>
				<c:choose>
					<c:when test="${ruleSet}">
						<c:forEach items="${originalRuleList}" var="originalRule" varStatus="i">
							<fieldset id="fieldset-${originalRule.rule.ruleCode}">
								<legend><sup><input type="checkbox" name="rules" id="${originalRule.rule.ruleCode}"  value="${originalRule.rule.ruleCode }" class="mcCbxRequired" ></sup>${originalRule.rule.ruleDescription}</legend>
								<c:forEach items="${originalRule.rule.ruleAllowableParameters}" var="rsParamValues">
									<input type="hidden" name="paramNames" value="${rsParamValues.ruleParameter.ruleParameterCd }"/>
									<label>${rsParamValues.ruleParameter.description }</label>:<input type="text" name="paramValues" value=""/>
								</c:forEach> 
							</fieldset> 
						</c:forEach>
						<c:forEach items="${targetRuleList}" var="targetRule" varStatus="i">
							<fieldset id="fieldset-${targetRule.rule.ruleCode}">
								<legend><sup><input class="check-now mcCbxRequired" type="checkbox" name="rules" id="${targetRule.rule.ruleCode}"  value="${targetRule.rule.ruleCode }" checked="checked" /></sup>${targetRule.rule.ruleDescription}</legend> 
								<c:forEach items="${targetRule.ruleSetConfigurationDetails}" var="configDetail">
									<c:forEach items="${configDetail.ruleSetConfigurationParameterValues}" var="rsParamValues">
										<c:if test="${configDetail.ruleSetConfiguration.ruleSetConfigurationIdentifier eq configIdentifier}">
											<input type="hidden" name="paramNames" value="${rsParamValues.ruleAllowableParameter.ruleParameterCd }"/>
											<label>${rsParamValues.ruleAllowableParameter.ruleParameter.description }:</label><input type="text" name="paramValues" value="${rsParamValues.ruleParameterValueText}"/>
										</c:if>
									</c:forEach> 
								</c:forEach>
							</fieldset>
							<br/>
						</c:forEach>
						<input type="hidden" name="ruleSelectedType" value="rule"/>
					</c:when>
					<c:otherwise>
						<c:forEach items="${targetRuleList}" var="targetRule" varStatus="i">
							<fieldset id="fieldset-${targetRule.rule.ruleCode}">
								<legend><h6><span style="display:none"><input class="check-now mcCbxRequired" type="checkbox" name="rules" id="${targetRule.rule.ruleCode}"  value="${targetRule.rule.ruleCode }" checked="checked"/></span>${targetRule.rule.ruleDescription}</h6></legend> 
								<c:forEach items="${targetRule.ruleSetConfigurationDetails}" var="configDetail">
									<c:forEach items="${configDetail.ruleSetConfigurationParameterValues}" var="rsParamValues">
										<c:choose>
											<c:when test="${NewConfiguration}">
												<input type="hidden" name="paramNames" value="${rsParamValues.ruleAllowableParameter.ruleParameterCd }"/>
												<label>${rsParamValues.ruleAllowableParameter.ruleParameter.description }:</label>
												<input type="text" name="paramValues" value=""/>
											</c:when>
											<c:otherwise>
												<c:if test="${configDetail.ruleSetConfiguration.ruleSetConfigurationIdentifier eq configIdentifier}">
													<input type="hidden" name="paramNames" value="${rsParamValues.ruleAllowableParameter.ruleParameterCd }"/>
													<label>${rsParamValues.ruleAllowableParameter.ruleParameter.description }:</label>
													<input type="text" name="paramValues" value="${rsParamValues.ruleParameterValueText}"/>
												</c:if>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
								</c:forEach>
							</fieldset>
							<br/>
						</c:forEach>
						<input type="hidden" name="ruleSelectedType" value="paramValues"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="formButtonsContainer">
		<div class="row-fluid">
			<div class="ereg_span_01" style="margin:0 20px;">
				<button type="submit" value="next" id="submit"  class="submitButton submit">Save</button>
			</div>
		</div>
	</div>
</form:form>
<script type="text/javascript">
	$('.mcCbxRequired').each(function() {
		var mcCbxCheck = $(this);
		if(!mcCbxCheck.is(':checked')) {
			$('#fieldset-'+this.id+' input:text').attr('disabled','disabled');      
		}
	});
	$('.mcCbxRequired').click(function(){
		var mcCbxCheck = $(this);
		if(mcCbxCheck.is(':checked')) {
			$('#fieldset-'+this.id+' input:text').removeAttr('disabled');      
		}else{
			$('#fieldset-'+this.id+' input:text').attr('disabled','disabled');      
		}
	});
	$("#submit").on('click', function () {
		var form = $('form#configurationsForm').serialize();
		var formAction = "${pageContext.request.contextPath}/secure/configurations/saveconfigurations";
		var target='ruleConfigurations';
		ajaxPost(formAction,form,target ,'POST',
		function(data) {
			alert("Successfully Completed Round Trip");
		}			
		);
	});
	//HAd to introduce this was not able to Bind the On change Event to the JQuery generated Code
	$('#rules').change(function(){
		$('#paramValsId').empty();
	});
</script>