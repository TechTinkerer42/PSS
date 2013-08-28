<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<t:base title="Home Page">
	<div class="headContainer">
		<div class="row">
			<div class="span9">
				<h1><spring:message code="home.welcome.heading" arguments="Missouri Performance Assessments"/> </h1>
				<p style="color:red; margin:10px 0 0 0;">${STATUS_MESSAGE}</p>
			</div>
		</div>
	</div>
	<!-- homeContainer Starts -->
	<div id="homeContainer">
		<!-- Column1 Starts -->
		<div class="column1">
			<div class="block">
				<h3>${customer.firstName} ${customer.lastName}</h3>
				<div class="content">
					<spring:message code="home.testtakerId.label"/> # ${testTakerId}
				</div>
			</div>
			<div class="block">
				<h3><spring:message code="home.profile.header"/></h3>
				<div class="content">
					<ul>
						<li><a href=<c:url value='/secure/profile/' />><spring:message code="home.personal.link.display"/></a></li>
						<li><a href='<c:url value='${changePassword}' />'><spring:message code="home.changepassword.link.display"/></a></li>
						<li><a href='<c:url value='${changeSecurityQuestion}' />'><spring:message code="home.securityq.link.display"/></a></li>
						<li><a href='<c:url value='/secure/profile/background/update' />'><spring:message code="home.backgroundinfo.display"/></a></li>
					</ul>
				</div>
			</div>
			<jsp:include page="../common/myResources.jsp"></jsp:include>
		</div>
		<!-- Column1 Ends -->
		<!-- Column2 Starts -->
		<div class="column2">
			<div  class="block" <c:if test="${all_biq_answered}">style="display:none;"</c:if>>				
				<h3>Important Message</h3>
				<c:if test="${!all_biq_answered}">
					<div style="width:100%; margin:0 auto; border:0px dotted #ccc; padding:0; background:#FFEEE2">
						<div class="content">
							<spring:message code="enterCode.errorMsg"/> 
						</div>
					</div>
				</c:if>
			</div>
			<div class="block">
				<h3>My Missouri Performance Assessments</h3>
				


		<div id="rightSide" style="margin-left: 25px">
			<div id="myAssesments" class="four">
				
				<div class="five">
					<form:form method="post" commandName="newtasks" action="/ereg-web/pss/task/addNewTasks">
					
					<div class="content">	
						<span class="homePageh2">Enter Assessment Code</span>
					</div> 
					<div>
						<form:input path="milStsCde" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<form:errors class="errorMessage" path="milStsCde"/>
						<input type="submit" value="Begin Your Assessment" />
					</div>  
					</form:form>  
				</div>
			</div>

		</div>

				
			</div>
		</div>
	
	<script src="http://maps.google.com/maps/api/js?file=api&amp;v=3&amp;key=AIzaSyBElXakDy99jw9gMrUQ6Ik5DPvST6dJiwc&amp;sensor=false" type="text/javascript"></script>
	<script src='<spring:url htmlEscape="true" value="/commonweb/js/testcenter/search.js"/>'></script>
</t:base>