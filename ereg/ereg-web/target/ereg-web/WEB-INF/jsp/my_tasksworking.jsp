<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if IE 9]>    <html class="no-js lt-ie10" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js" lang="en"> <!--<![endif]-->

<t:base title="Home Page">
	<div class="headContainer">
		<div class="row">
			<div class="span9">
				<h1><spring:message code="home.welcome.heading" arguments="${globalContextCustomer.currentProgramShortDescription}"/> </h1>
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
						<li><a href="<c:url value='/secure/testtaker/accommodations/view' />"><spring:message code="home.accommodations"/></a></li>
					</ul>
				</div>
			</div>
			<jsp:include page="../common/myResources.jsp"></jsp:include>
		</div>
		<!-- Column1 Ends -->
		<!-- Column2 Starts -->
		<div class="column2">
			<div  class="block" <c:if test="${all_biq_answered}">style="display:none;"</c:if>>
				<h3><spring:message code="home.notifocation.heading"/></h3>
				<c:if test="${!all_biq_answered}">
					<div style="width:100%; margin:0 auto; border:0px dotted #ccc; padding:0; background:#FFEEE2">
						<div class="content">
							<spring:message code="home.background.message.part1"/> <a href='<c:url value='/secure/profile/background/update' />'><spring:message code="home.background.message.part2"/></a> <spring:message code="home.background.message.part3"/> 
						</div>
					</div>
				</c:if>
			</div>
			<div class="rounded">
				<h2 class="shaded like-h3">	My Assessments</h2>
                <h3>Pre-Service Teacher Assessments: Exit Assessments</h3>

                <p><a href="/ereg-web/pss/artifact/">Upload/Manage My Artifacts</a></p>	
								
				
				<table style=" width: 98%; text-align:center;">
					<tr>
					<th>Task</th>
					<th>Deadline</th>
					<th>Status</th>
					<th>Last Saved</th>
					</tr>
					<c:if test="${!empty userTasks}">
     					<c:forEach var="ob" varStatus="status" items="${userTasks}">
    						<tr>
						        												<td><c:choose>
														<c:when test="${ob.showAsLink == 'true'}">
															<a href="<c:url value="/pss/task/goto?taskId=${ob.taskId}"/>"><c:out value="${ob.name}" />
																<c:if test="${ob.status == 'IN PROGRESS'}"><!-- -View/Edit Task --></c:if></a>
														</c:when>
														<c:when test="${ob.showAsLink == 'true'}">
															<c:set var="tStatus" scope="session" value="${ob.status}"/>
															<a href="<c:url value="/pss/task/goto?taskId=${ob.taskId}"/>"><c:out value="${ob.name}" />
																<c:if test="${tStatus == 'READY'}"><!-- -View/Edit Task --></c:if></a>
																<c:out value="${tStatus}"/>
														</c:when>
														<c:when test="${ob.showAsLink == 'false'}"><c:out value="${ob.name}" /></c:when>														
													</c:choose>
												</td>
						        <%-- <td><c:out value="${ob.deadline}"/></td> --%>
						        <td><fmt:formatDate  dateStyle="long" timeStyle="short"  value="${ob.deadline}" /></td>						        
						        <td><c:out value="${ob.status}"/></td>
						        <td><fmt:formatDate  dateStyle="long" timeStyle="short"  value="${ob.lastSaved}" /></td>
						        <%-- <td><c:out value="${ob.lastSaved}"/></td> --%>
    						</tr>
             			</c:forEach>
           			</c:if>
				</table>


					<!-- <div style="margin-top:10px">
						<p>
							<a href="">View All My Assessments</a>
						</p>
					</div> -->
				
		

				
			</div>
		</div>
	
	
</t:base>