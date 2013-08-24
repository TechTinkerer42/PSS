<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<%@page import="java.util.logging.Logger"%>
<%@page import="org.ets.pss.persistence.model.AsgndTsk"%>

<script>
	function setdiv( divid ){
		if(document.getElementById(divid).height == 100 ){
			document.getElementById(divid).height = 500;
		}else{
			document.getElementById(divid).height = 100;
		}
	}
</script>
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
					</ul>
				</div>
			</div>
			<jsp:include page="../common/myResources.jsp"></jsp:include>
		</div>
		<!-- Column1 Ends -->
		<!-- Column2 Starts -->
		<div class="column2">

		<div>
		<div id="rightSide" >


	
	<div id="documentsDiv" style="display: none"></div>
	<div id="linkdialog" title="Teachers Assesment Artifacts"
		style="display: none"></div>
		
		
	<div id="accordion" class="center">
		
		<div class="tasktitle" style="padding: 7px 5px 1px 10px; border-radius: 5px 5px 0 0; background: none repeat scroll 0 0 #888888; color: #ffffff; font-size: 1.5em"><span>${task.testName}</span></div>
		<div style="border: 1px solid #CCCCCC; padding: 10px 1px 1px 10px; ">
			
			<div class="content" >
				<div class="tasktitle" style="font-size: 1.3em"><span>${task.title}</span></div>
				<div class="taskstatus" style="padding: 10px 0 0 0;"><span>Status: New</span></div>
				<div class="taskinstr" style="padding: 20px 0px 0px 0px;"><span style="font-size: 1.2em">Task Overview</span><br><span>${task.instructions}</span></div>
				<div class="tasktitle2" style="padding: 20px 0px 0px 0px;"><span style="font-size: 1.2em; ">Task 1: Guiding Prompts</span><br><span>(Maximum of 7 pages)</span></div>
				<div class="steps" style="padding: 10px 0px 0px 0px;">
					<c:forEach var="step" varStatus="status" items="${task.stepDTOs}">
						<div class="step${status.count}" id="step${status.count}" style=" padding: 5px 0px 0px 0px;  ">
							<div class="steptitle" onclick="setdiv('step${status.count}');" style="border-radius: 5px 5px 0 0; background: none repeat scroll 0 0 #888877; width: 100%; cursor: pointer"><span style=" font-size: 1.2em; " >${step.title}</span></div>
							<div class="instructions"><span>${step.instructions}</span></div>
							<div class="prompts" style="padding: 20px 0px 0px 0px;" >
								<c:forEach var="prompt" varStatus="promtStatus" items="${step.promptDTOs}">
									<div class="activity" style="padding: 20px 0px 0px 0px;"><span style="font-size: 1.1em" >Activity:</span><span>${prompt.activity }</span></div>
									<div class="prompttitle" style="padding: 20px 0 0 0"><span style="font-size: 1.2em">${prompt.title}</span></div>
								
									<div class="prompt" style="padding: 20px 0 0 0"><span style="font-size: 1.2em">Guiding Prompts:</span><br></div>
									<div class="prompt">
										<ol type="a">
											<c:forEach var="guide" varStatus="guideCnt" items="${prompt.guides}">
												<li>${guide.value}</li>
											</c:forEach>
										</ol>
									</div>
									<div class="instructions">
										<span>${prompt.instructions}</span>
									</div>
								</c:forEach>
							</div>
							
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<div id="taskButtonsDiv" class="taskbuttonsdiv">
		<input type=button id="saveDraft" class="taskbuttons" value="Save Draft" />
		<input type=button id="submitTask" class="taskbuttons" value="Submit Task" />
	</div>



		</div>

				
			</div>
		</div>
	
	<script src="http://maps.google.com/maps/api/js?file=api&amp;v=3&amp;key=AIzaSyBElXakDy99jw9gMrUQ6Ik5DPvST6dJiwc&amp;sensor=false" type="text/javascript"></script>
	<script src='<spring:url htmlEscape="true" value="/commonweb/js/testcenter/search.js"/>'></script>
</t:base>